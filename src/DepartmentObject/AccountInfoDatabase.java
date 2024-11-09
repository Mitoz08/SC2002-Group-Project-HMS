package DepartmentObject;

import HumanObject.BasePerson;
import HumanObject.ROLE;
import InputHandler.Input;
import Serialisation.DataEncryption;
import Serialisation.DataSerialisation;

import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AccountInfoDatabase {

    private String fileName;
    private static String passwordRegex =   "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&-+=()]).{8,20}$";

    public AccountInfoDatabase() {
        this.fileName = "Login.txt";
//        addNewAccount("Admin", "AD1005");
    }

    public void testRun()
    {
        try {
            FileWriter fileWriter = new FileWriter(new File(fileName));
            fileWriter.close();
        } catch (Exception e) {
            System.out.println("Error writing into Login.txt");
        }
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
            username = Input.ScanString("Username:").replaceAll("\\s+", "");
            if (username.equals("-1")) return null;
//            char[] pass = System.console().readPassword("Password: ");
//            password = String.copyValueOf(pass);
            password = Input.ScanString("Password:").replaceAll("\\s+", "");
            if (password.equals("-1")) return null;
            UserID = verify(username, password);
            if (UserID == null){
                System.out.println("Wrong Username/Password... \nTry again");
                continue;
            }
            System.out.println("Login successful.");
            if (password.equals("Password")) {
                System.out.println("Please change you default password");
                Input.ScanString("Enter to continue...");
                String newPass = checkPassword();
                addNewPassword(username,password,newPass);
            }
            return UserID;
        }
    }

    public boolean changePassword() {
        String username;
        String oldPass;
        String UserID;
        while (true) {
            username = Input.ScanString("Username:").replaceAll("\\s+", "");
            oldPass = Input.ScanString("Password:").replaceAll("\\s+", "");
            UserID = verify(username, oldPass);
            if (UserID == null){
                System.out.println("Wrong Username/Password... \nTry again");
                continue;
            }
            String newPass = checkPassword();
//            String newPass = Input.ScanString("new password:");
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
        System.out.println("This is your username: " + username + ". With the default password as \"Password\"");
        System.out.println("Account successfully created.");
        Input.ScanString("Enter to continue...");
        return true;
    }

    public boolean addNewPassword(String username, String oldPass, String newPass) {
        String[] Encrypted = new String[] {DataEncryption.SHA3(username), DataEncryption.SHA3(oldPass),
                DataEncryption.SHA3(newPass)};
        int slot = hashValue(Encrypted[0]);
        updateFile(Encrypted,slot,1);
        return true;
    }

    public boolean removeAccount (BasePerson person) {
        String[] Encrypted = new String[] {person.getStrID()};
        updateFile(Encrypted,-1,2);
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
            for (int index = 0; index < textLine.size(); index++) {
                String s = textLine.get(index);
                if (s.isEmpty()) continue;
                String[] prevTextArray = s.split("/");
                ArrayList<String> temp = new ArrayList<>(Arrays.asList(prevTextArray));
                for (int i = 0; i < temp.size(); i += 3) {
                    int hashValue = hashValue(temp.get(i));
                    String ID = DataEncryption.decipher(temp.get(i+2),hashValue);
                    if (ID.equals(Encrypted[0])) {
                        temp.remove(i);
                        temp.remove(i);
                        temp.remove(i);
                        prevTextArray = new String[temp.size()];
                        for (int j = 0; j < prevTextArray.length; j++) prevTextArray[j] = temp.removeFirst();
                        textLine.remove(index);
                        textLine.add(index, DataSerialisation.convertStringArraytoString(prevTextArray, "/"));
                    }
                }

            }
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

    public static String checkPassword() {
        Pattern p = Pattern.compile(passwordRegex);
        Input.ClearConsole();
        System.out.println("Password requirements:\n" +
                "8 - 14 characters long\n" +
                "Contains a digits\n" +
                "Contains upper and lower case\n" +
                "Contains special character like !@#$%^&*()");
        while (true) {
            String pass = Input.ScanString("Enter password:").replaceAll("\\s+", "");
            Matcher m = p.matcher(pass);
            if (!m.matches()) {
                System.out.println("Requirements not met.");
                continue;
            }
            String confirmPass = Input.ScanString("Confirm password:").replaceAll("\\s+", "");
            if (!pass.equals(confirmPass)) {
                System.out.println("Password does not match try again.");
                continue;
            }
            return pass;
        }
    }

}
