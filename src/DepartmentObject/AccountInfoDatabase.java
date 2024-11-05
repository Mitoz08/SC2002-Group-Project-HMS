package DepartmentObject;


import HumanObject.Patient.Patient;
import InputHandler.Input;
import ObjectUI.*;
import Serialisation.DataEncryption;
import Serialisation.DataSerialisation;

import java.io.*;
import java.util.*;

public class AccountInfoDatabase {

    private String fileName;

    public AccountInfoDatabase() {
        this.fileName = "Login.txt";
//        addNewAccount("Admin", "AD1005");
//        testRun();
    }

    private void testRun()
    {
        addNewAccount("John", "PA1001");
        addNewAccount("May", "PA1002");
        addNewAccount("Ben", "DR1003");
        addNewAccount("Fae", "DR1004");
        addNewAccount("Summer", "AD1005");
        addNewAccount("Alfred", "AD1006");
        addNewAccount("Pharah", "PH1007");
        addNewAccount("Winston", "PH1008");
    }
    public String login(){
        String username;
        String password;
        String UserID;
        while (true) {
            username = Input.ScanString("Username:").trim();
            char[] pass = System.console().readPassword("Password: ");
            password = String.copyValueOf(pass);
//            password = Input.ScanString("Password:").trim();
            UserID = verify(username, password);
            if (UserID == null){
                System.out.println("Wrong Username/Password... \nTry again");
                continue;
            }
            System.out.println("Login successful.");
            return UserID;
        }
    }

    public boolean changePassword() {
        String username;
        String oldPass;
        String UserID;
        while (true) {
            username = Input.ScanString("Username:").trim();
            oldPass = Input.ScanString("Password:").trim();
            UserID = verify(username, oldPass);
            if (UserID == null){
                System.out.println("Wrong Username/Password... \nTry again");
                continue;
            }
            String newPass = Input.ScanString("Enter your new password:");
            return addNewPassword(username,oldPass,newPass);
        }
    }

    private String verify(String username, String Password) {
        String[] Encrypted = new String[] {DataEncryption.SHA3(username), DataEncryption.SHA3(Password)};
        int slot = hashValue(Encrypted[0]);
        File file = new File(fileName);
        String UserID = null;
        Scanner fileReader = null;
        ArrayList<String> textLine = new ArrayList<>();
        try {
            fileReader = new Scanner(file);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error reading Login.txt");
        }
        int i = 0;
        while (fileReader.hasNextLine()) {
            textLine.add(fileReader.nextLine());
            i++;
        }
        while (i < slot) {
            textLine.add(fileReader.nextLine());
            i++;
        }

        String data = textLine.get(slot-1);
        String[] dataArray = data.split("/");
        for (int j = 0; j < dataArray.length; j += 3) {
            if (Encrypted[0].equals(dataArray[j]) && Encrypted[1].equals(dataArray[j+1])){
                UserID = DataEncryption.decipher(dataArray[j+2], slot);
            }
        }
        return UserID;
    }

    public boolean addNewAccount(String username, String userID){
        // PA0001,Username,Password
        String[] Encrypted = new String[] {DataEncryption.SHA3(username), DataEncryption.SHA3("Password"), ""};
        int slot = hashValue(Encrypted[0]);
        Encrypted[2] = DataEncryption.cipher(userID, slot);
        updateFile(Encrypted,slot,0);
        return true;
    }

    public boolean addNewPassword(String username, String oldPass, String newPass) {
        String[] Encrypted = new String[] {DataEncryption.SHA3(username), DataEncryption.SHA3(oldPass),
                DataEncryption.SHA3(newPass)};
        int slot = hashValue(Encrypted[0]);
        updateFile(Encrypted,slot,1);
        return true;
    }

    private boolean updateFile (String[] Encrypted, int slot, int mode) {
        File file = new File(fileName);
        Scanner fileReader = null;
        ArrayList<String> textLine = new ArrayList<>();
        try {
         fileReader = new Scanner(file);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error reading Login.txt");
        }

        while (fileReader.hasNextLine()) {
            textLine.add(fileReader.nextLine());
        }

        for (int i = textLine.size(); i < slot; i++) {
            textLine.add("");
        }

        if (mode == 0) {
            String prevText = textLine.get(slot-1);
            String addText = DataSerialisation.convertStringArraytoString(Encrypted, "/");
            if (prevText.isEmpty()) {
                textLine.remove(slot-1);
                textLine.add(slot-1, addText);
            } else {
                textLine.remove(slot-1);
                textLine.add(slot-1, prevText + "/" + addText);
            }
        } else if (mode == 1) {
            String prevText = textLine.get(slot-1);
            String[] prevTextArray = prevText.split("/");
            for (int i = 0; i < prevTextArray.length; i +=3) {
                if (prevTextArray[i].equals(Encrypted[0]) && prevTextArray[i+1].equals(Encrypted[1])){
                    prevTextArray[i+1] = Encrypted[2];
                    break;
                }
            }
            textLine.remove(slot-1);
            textLine.add(slot-1, DataSerialisation.convertStringArraytoString(prevTextArray, "/"));
        }
        fileReader.close();
        try {
            FileWriter fileWriter = new FileWriter(file);
            for (String s : textLine) {
                fileWriter.write(s + "\n");
            }
            fileWriter.close();
        } catch (Exception e){
            e.printStackTrace();
            System.out.println("Error writing Login.txt");
            return false;
        }
        return true;
    }

    private int hashValue(String text) {
        int hashValue = 53;
        int value = 0;
        for (int i = 0; i < text.length(); i++) {
            char ch = text.charAt(i);
            if (ch >= '0' && ch <= '9'){
                value += ch - '0';
            }
        }
        return value % hashValue + 1;
    }
}
