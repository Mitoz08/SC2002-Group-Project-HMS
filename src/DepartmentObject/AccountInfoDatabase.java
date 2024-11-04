package DepartmentObject;

import HumanObject.Administrator.Administrator;
import HumanObject.BasePerson;
import HumanObject.Doctors.Doctors;
import HumanObject.Patient.Patient;
import HumanObject.Pharmacist.Pharmacist;
import HumanObject.ROLE;
import InputHandler.Input;
import ObjectUI.*;
import Serialisation.DataEncryption;
import Serialisation.DataSerialisation;

import java.io.*;
import java.util.*;

public class AccountInfoDatabase {

    private String fileName;

    // will be called in the start of the main function this inputs can be gathered by reading the HMSAccount.txt first

//    Account info comes with username,Password,role,id KIV TO DELETE
//    private String[] verifyLogin(String username, String Password){
//        String line;
//        String[] empty = new String[4];
//        String[] parts = new String[4];
//        try{
//            BufferedReader reader = new BufferedReader(new FileReader("HMSAccount.txt"));
//            while ((line = reader.readLine()) != null){
//                parts = line.split("[,]");
//                if (parts[0].equals(username) && parts[1].equals(Password)){
//                    return parts;
//                }
//            }
//        }catch(IOException e){
//            e.printStackTrace();
//        }
//        return empty;
//    }
    public AccountInfoDatabase() {
        this.fileName = "Login.txt";
    }


    public BaseUI login(){
        BaseUI baseUI;
        String username;
        String password;
        String UserID;
        //ROLE role;
        while (true) {
            username = Input.ScanString("Username:");
            password = Input.ScanString("Password:");
            //role = ROLE.values()[Input.ScanInt("1. Patient\n2.Doctor\n3.Pharmacist\n4.Administrator\nEnter your role:") -1 ];
            UserID = verify(username, password);
            if (UserID == null){
                System.out.println("Wrong Username/Password... \nTry again");
                continue;
            }
            System.out.println("Login successful.");
            break;
        }
        // Determine which to choose
        int ID = 0;


//        switch (role) {
//            case PATIENT:
//                for (Patient p : database.getPatients()) {
//                    if (p.getID() == ID) return (BaseUI) new PatientUI(database, pharmacy, p);
//                }
//                break;
//            case DOCTOR:
//                for (Doctors d : database.getDoctors()) {
//                    if (d.getID() == ID) return (BaseUI) new DoctorUI(database, pharmacy, d);
//                }
//                break;
//            case PHARMACIST:
//                for (Pharmacist p : database.getPharmacists()) {
//                    if (p.getID() == ID) return (BaseUI) new PharmacistUI(database, pharmacy, p);
//                }
//                break;
//            case ADMINISTRATOR:
//                for (Administrator a : database.getAdministrators()) {
//                    if (a.getID() == ID) return (BaseUI) new AdminUI(database, pharmacy, a);
//                }
//                break;
//        }

        return null;
    }

    private String verify(String username, String Password) {
        String[] Encrypted = new String[] {DataEncryption.SHA3(username), DataEncryption.SHA3(Password)};
        int slot = hashValue(Encrypted[0]);
        File file = new File(fileName);
        String UserID = null;
        Scanner fileReader = null;
        ArrayList<String> textLine = new ArrayList<>();
        try {
            fileReader = new Scanner(fileName);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error reading Login.txt");
        }
        int i = 0;
        while (i < slot) {
            textLine.add(fileReader.nextLine());
            i++;
        }

        String data = textLine.getLast();
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
         fileReader = new Scanner(fileName);
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
                if (prevTextArray[i] == Encrypted[0] && prevTextArray[i+1] == Encrypted[1]){
                    prevTextArray[i+1] = Encrypted[2];
                    break;
                }
            }
            textLine.remove(slot-1);
            textLine.add(slot-1, DataSerialisation.convertStringArraytoString(prevTextArray, "/"));
        }
        fileReader.close();
        try {
            FileWriter fileWriter = new FileWriter(fileName);
            for (String s : textLine) {
                fileWriter.write(s + "\n");
            }
            fileWriter.close();
        } catch (Exception e){
            e.printStackTrace();
            System.out.println("Error writing Login.txt");
        }


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
        return value % hashValue;
    }
}
