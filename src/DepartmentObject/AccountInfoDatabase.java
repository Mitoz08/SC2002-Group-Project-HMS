package DepartmentObject;

import HumanObject.BasePerson;
import HumanObject.ROLE;
import InputHandler.Input;
import Serialisation.DataEncryption;
import Serialisation.DataSerialisation;

import java.io.*;
import java.util.*;

public class AccountInfoDatabase {

    private String fileName;

    public AccountInfoDatabase() {
        this.fileName = "Login.txt";
//        addNewAccount("Admin", "AD1005");
        testRun();
    }

    private void testRun()
    {
        addNewAccount("Johnathan", "PA1001", ROLE.PATIENT);
        addNewAccount("May", "PA1002", ROLE.PATIENT);
        addNewAccount("Benjamin", "DR1001", ROLE.DOCTOR);
        addNewAccount("Fae Wong", "DR1002", ROLE.DOCTOR);
        addNewAccount("Summer", "AD1001", ROLE.ADMINISTRATOR);
        addNewAccount("Alfred", "AD1002", ROLE.ADMINISTRATOR);
        addNewAccount("Pharah", "PH1001", ROLE.PHARMACIST);
        addNewAccount("Winston", "PH1002", ROLE.PHARMACIST);
    }
    public String login(){
        String username;
        String password;
        String UserID;
        while (true) {
            username = Input.ScanString("Username:").trim();
//            char[] pass = System.console().readPassword("Password: ");
//            password = String.copyValueOf(pass);
            password = Input.ScanString("Password:").trim();
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

        if (i < slot) return null;

        String data = textLine.get(slot-1);
        String[] dataArray = data.split("/");
        for (int j = 0; j < dataArray.length; j += 3) {
            if (Encrypted[0].equals(dataArray[j]) && Encrypted[1].equals(dataArray[j+1])){
                UserID = DataEncryption.decipher(dataArray[j+2], slot);
            }
        }
        return UserID;
    }

    public boolean addNewAccount(String name, String UserID, ROLE role){
        // PA0001,Username,Password
        String username = usernameGenerator(name, Integer.parseInt(UserID.substring(2)), role);
        String[] Encrypted = new String[] {DataEncryption.SHA3(username), DataEncryption.SHA3("Password"), ""};
        int slot = hashValue(Encrypted[0]);
        Encrypted[2] = DataEncryption.cipher(UserID, slot);
        updateFile(Encrypted,slot,0);
        System.out.println("This is your username: " + username + ". With the default password as \"Password\"");
        return true;
    }

    public boolean addNewAccount(BasePerson person){
        // PA0001,Username,Password
        String username = usernameGenerator(person.getName(), person.getID(), person.getRole());
        String[] Encrypted = new String[] {DataEncryption.SHA3(username), DataEncryption.SHA3("Password"), ""};
        int slot = hashValue(Encrypted[0]);
        Encrypted[2] = DataEncryption.cipher(person.getStrID(), slot);
        updateFile(Encrypted,slot,0);
        Input.ScanString("This is your username: " + username + ". With the default password as \"Password\""+"\nEnter to continue...");
        return true;
    }

    public boolean addNewPassword(String username, String oldPass, String newPass) {
        String[] Encrypted = new String[] {DataEncryption.SHA3(username), DataEncryption.SHA3(oldPass),
                DataEncryption.SHA3(newPass)};
        int slot = hashValue(Encrypted[0]);
        updateFile(Encrypted,slot,1);
        return true;
    }

    public boolean removeAccount (String username, String userID) {
        String[] Encrypted = new String[] {DataEncryption.SHA3(username), ""};
        int slot = hashValue(Encrypted[0]);
        Encrypted[1] = DataEncryption.cipher(userID,slot);
        updateFile(Encrypted,slot,2);
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

        if (mode == 0) { // Adding new account
            String prevText = textLine.get(slot-1);
            String addText = DataSerialisation.convertStringArraytoString(Encrypted, "/");
            if (prevText.isEmpty()) {
                textLine.remove(slot-1);
                textLine.add(slot-1, addText);
            } else {
                textLine.remove(slot-1);
                textLine.add(slot-1, prevText + "/" + addText);
            }
        } else if (mode == 1) { // Changing password
            String prevText = textLine.get(slot-1);
            String[] prevTextArray = prevText.split("/");
            for (int i = 0; i < prevTextArray.length; i += 3) {
                if (prevTextArray[i].equals(Encrypted[0]) && prevTextArray[i+1].equals(Encrypted[1])){
                    prevTextArray[i+1] = Encrypted[2];
                    break;
                }
            }
            textLine.remove(slot-1);
            textLine.add(slot-1, DataSerialisation.convertStringArraytoString(prevTextArray, "/"));
        } else if (mode == 2) { // Removing account
            String prevText = textLine.get(slot-1);
            String[] prevTextArray = prevText.split("/");
            List<String> temp = Arrays.asList(prevTextArray);
            for (int i = 0; i < temp.size(); i += 3) {
                if (temp.get(i).equals(Encrypted[0]) && temp.get(i+2).equals(Encrypted[1])) {
                    temp.remove(i);
                    temp.remove(i);
                    temp.remove(i);
                }
            }
            prevTextArray = new String[temp.size()];
            for (int i = 0; i < prevTextArray.length; i++) prevTextArray[i] = temp.removeFirst();
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

    private String usernameGenerator(String name, int ID, ROLE role) {
        String username = "";

        switch (role){
            case PATIENT:
                username = username + "PA_";
                break;

            case DOCTOR:
                username = username + "DR_";
                break;

            case PHARMACIST:
                username = username + "PH_";
                break;

            case ADMINISTRATOR:
                username = username + "AD_";
                break;
        }

        String newName = name.replaceAll("\\s+", "").toUpperCase();
        if (newName.length() < 4) {
            newName = newName + "0000";
        }
        newName = newName.substring(0,4);
        username = username + newName + String.valueOf(ID);

        return username;
    }

}
