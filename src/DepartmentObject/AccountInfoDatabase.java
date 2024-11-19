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

/**
 * The {@code AccountInfoDatabase} class handles the login of the Hospital Management System.
 * <p>
 *     An instance of this class works off of one database (one text file), allowing
 *     the Hospital Management System to include multiple login systems for different application if needed.
 * </p>
 *
 * <p>
 *     {@code Username} and {@code Password} is obtained and passed into this class which is then encrypted to
 *     match with the encrypted account details in the database and returns the {@code UniqueID} of that user.
 * </p>
 *
 * <p>
 *     New account is given the default {@code Password} of "Password" and will be prompted to change their
 *     {@code Password} on their first login.
 * </p>
 */
public class AccountInfoDatabase {

    /** The file name to read and write from. */
    private String fileName;

    /** The requirement of the Password. */
    private static String passwordRegex =   "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&*()]).{8,20}$";

    /**
     * Sets the file name or the {@code database} for the instance to read from and write to
     * @param fileName the text file to be read or written
     */
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    /**
     * A method used to test run the program by resetting the {@code database} and reinitialising the default
     * data
     */
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

    /**
     * Login method that takes in the {@code Username} and {@code Password} of the user and
     * verify the details.
     * <p>
     *     If it is the first time logging in, it prompts the user to change their password
     * </p>
     * @return the {@code UniqueID} of the user
     */
    public String login(){
        String username;
        String password;
        String UserID;
        while (true) {
            username = Input.ScanString("Username:").replaceAll("\\s+", "");
            if (username.equals("-1")) return null;
//            char[] pass = System.console().readPassword("Password: ");
//            password = String.copyValueOf(pass);
//            password = password.replaceAll("\\s+", "");
            password = Input.ScanString("Password:").replaceAll("\\s+", "");
            if (password.equals("-1")) return null;
            UserID = verify(username, password);
            if (UserID == null){
                System.out.println("Wrong Username/Password... \nTry again");
                continue;
            }
            System.out.println("Login successful.");
            if (password.equals("Password")) {
                System.out.println("Please change your default password");
                Input.ScanString("Enter to continue...");
                String newPass = checkPassword();
                addNewPassword(username,password,newPass);
            }
            return UserID;
        }
    }

    /**
     * This method takes in the {@code Username} and {@code Password} of the user and verify the details.
     * and prompts a {@code Password} change when user successfully login.
     * @return {@code true} when it successfully changes the password
     *
     * @see #addNewPassword(String, String, String) for Updating the Password
     */
    public boolean changePassword() {
        String username;
        String oldPass;
        String UserID;
        while (true) {
            username = Input.ScanString("Username:").replaceAll("\\s+", "");
//            char[] pass = System.console().readPassword("Password: ");
//            oldPass = String.copyValueOf(pass);
//            oldPass = password.replaceAll("\\s+", "");
            oldPass = Input.ScanString("Password:").replaceAll("\\s+", "");
            UserID = verify(username, oldPass);
            if (UserID == null){
                if (!Input.ScanBoolean("Wrong Username/Password... \nTry again?")) {
                    return false;
                }
                continue;
            }
            String newPass = checkPassword();
            Input.ScanString("Successfully changed password.\nEnter to continue...");
            return addNewPassword(username,oldPass,newPass);
        }
    }

    /**
     * Verifies the given {@code username} and {@code Password} by checking its encrypted text with the {@code database}
     * and returns the {@code UniqueID} of the user.
     * @param username the given {@code username}
     * @param Password the given {@code Password}
     * @return the {@code UniqueID} of the user if correct {@code username} and {@code Password} is given, null otherwise
     */
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

    /**
     * For adding new account
     * <p>
     * This method encrypts the {@code Username} and {@code UserID} to be passed into {@code updateFile()}
     * function to update the database
     * </p>
     * @param Username {@code Username} to be added
     * @param UserID {@code UserID} to be added
     * @param role the role of the User
     * @return {@code true} when successfully added an account
     *
     * @see #updateFile(String[], int, int) To see how the data is added into the file
     */
    public boolean addNewAccount(String Username, String UserID, ROLE role){
        // PA0001,Username,Password
        String username = usernameGenerator(Username, Integer.parseInt(UserID.substring(2)), role);
        String[] Encrypted = new String[] {DataEncryption.SHA3(username), DataEncryption.SHA3("Password"), ""};
        int slot = hashValue(Encrypted[0]);
        Encrypted[2] = DataEncryption.cipher(UserID, slot);
        if(updateFile(Encrypted,slot,0)){
            System.out.println("This is your username: " + username + ". With the default password as \"Password\"");
            return true;
        }
        return false;
    }

    /**
     * For adding new account
     * <p>
     * This method encrypts the {@code Username} and {@code UserID} to be passed into {@code updateFile()}
     * function to update the database
     * </p>
     * @param person the object to be added
     * @return {@code true} when successfully added an account
     *
     * @see #updateFile(String[], int, int) To see how the data is added into the file
     */
    public boolean addNewAccount(BasePerson person){
        // PA0001,Username,Password
        String username = usernameGenerator(person.getName(), person.getID(), person.getRole());
        String[] Encrypted = new String[] {DataEncryption.SHA3(username), DataEncryption.SHA3("Password"), ""};
        int slot = hashValue(Encrypted[0]);
        Encrypted[2] = DataEncryption.cipher(person.getStrID(), slot);
        if(updateFile(Encrypted,slot,0)) {
            System.out.println("This is your username: " + username + ". With the default password as \"Password\"");
            System.out.println("Account successfully created.");
            Input.ScanString("Enter to continue...");
            return true;
        }
        return false;
    }

    /** For updating new password
     * <p>
     * This method encrypts the {@code Username} , {@code oldPass} and {@code newPass} to be passed into {@code updateFile()}
     * function to update the database
     * </p>
     * @param username {@code Username} of the user
     * @param oldPass the old password
     * @param newPass the new password
     * @return {@code true} when successfully edited a password
     * @see #updateFile(String[], int, int) To see how the data is updated into the file
     */
    public boolean addNewPassword(String username, String oldPass, String newPass) {
        String[] Encrypted = new String[] {DataEncryption.SHA3(username), DataEncryption.SHA3(oldPass),
                DataEncryption.SHA3(newPass)};
        int slot = hashValue(Encrypted[0]);
        if(updateFile(Encrypted,slot,1)) return true;
        return false;
    }
    /** For removing account details
     * <p>
     * This method passes in the {@code UnserID} to be deleted from the database
     * </p>
     * @param person Person to be deleted
     * @return {@code true} when successfully deleted an account
     */
    public boolean removeAccount (BasePerson person) {
        String[] Encrypted = new String[] {person.getStrID()};
        updateFile(Encrypted,-1,2);
        return true;
    }

    /**
     * Updates the database (text file) based on the given inputs of {@code mode}
     * <l>
     *     <li>0 - Adds new account</li>
     *     <li>1 - Changes the password</li>
     *     <li>2 - Removes the account</li>
     * </l>
     * @param Encrypted Data to be written or read from the file
     * @param slot line of the file to read and write
     * @param mode what the function should do
     * @return
     */
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
            try {
                textLine.add(fileReader.nextLine());
            } catch (NoSuchElementException e) {
                break;
            }
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

    /**
     * Takes the encrypted SHA-256 string and converts it into an integer
     * @param text SHA-256 string
     * @return an integer
     */
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

    /**
     * Creates a new username using the users {@code name},{@code ID} and {@code role}
     * {@code role} prefix:
     * <l>
     *     <li>PA_ for {@code Patient}</li>
     *     <li>DR_ for {@code Doctor}</li>
     *     <li>PH_ for {@code Pharmacist}</li>
     *     <li>AD_ for {@code Administrator}</li>
     * </l>
     * @param name Full name of user
     * @param ID their unique ID
     * @param role their role (PATIENT/DOCTOR/PHARMACIST/ADMINISTRATOR)
     * @return A unique ID String
     */
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

    /**
     * Checks for a new password and making sure it meets the requirements of:
     * <l>
     *     <li>8 - 14 characters long</li>
     *     <li>Contains digit</li>
     *     <li>Contains upper and lower case</li>
     *     <lI>Contains special characters {e.g. ! @ # $ % ^ & * ( ) }</lI>
     * </l>
     * @return the new password to be changed to
     */
    public static String checkPassword() {
        Pattern p = Pattern.compile(passwordRegex);
        Input.ClearConsole();
        System.out.println("Password requirements:\n" +
                "8 - 14 characters long\n" +
                "Contains digits\n" +
                "Contains upper and lower case\n" +
                "Contains special character like {e.g. ! @ # $ % ^ & * ( ) }");
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
