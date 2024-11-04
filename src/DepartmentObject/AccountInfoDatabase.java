package DepartmentObject;

import HumanObject.Administrator.Administrator;
import HumanObject.BasePerson;
import HumanObject.Doctors.Doctors;
import HumanObject.Patient.Patient;
import HumanObject.Pharmacist.Pharmacist;
import HumanObject.ROLE;
import InputHandler.Input;
import ObjectUI.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class AccountInfoDatabase {

    public static UserInfoDatabase database;
    public static Pharmacy pharmacy;

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


    public static BaseUI login(){
        BaseUI baseUI;
        String username;
        String password;
        ROLE role;
        while (true) {
            username = Input.ScanString("Username:");
            password = Input.ScanString("Password:");
            role = ROLE.values()[Input.ScanInt("1. Patient\n2.Doctor\n3.Pharmacist\n4.Administrator\nEnter your role:") -1 ];
            if (!verify(username, password, role)){
                System.out.println("Wrong Username/Password... Try again");
                continue;
            }
            System.out.println("Login successful.");
            break;
        }
        // Determine which to choose
        int ID = 0;


        switch (role) {
            case PATIENT:
                for (Patient p : database.getPatients()) {
                    if (p.getID() == ID) return (BaseUI) new PatientUI(database, pharmacy, p);
                }
                break;
            case DOCTOR:
                for (Doctors d : database.getDoctors()) {
                    if (d.getID() == ID) return (BaseUI) new DoctorUI(database, pharmacy, d);
                }
                break;
            case PHARMACIST:
                for (Pharmacist p : database.getPharmacists()) {
                    if (p.getID() == ID) return (BaseUI) new PharmacistUI(database, pharmacy, p);
                }
                break;
            case ADMINISTRATOR:
                for (Administrator a : database.getAdministrators()) {
                    if (a.getID() == ID) return (BaseUI) new AdminUI(database, pharmacy, a);
                }
                break;
        }

        return null;
    }

    private static boolean verify(String username, String Password, ROLE role) {

        return false;
    }

    public static boolean addNewAccount(String username, String userID){
        // PA0001,Username,Password

        return false;
    }



}
