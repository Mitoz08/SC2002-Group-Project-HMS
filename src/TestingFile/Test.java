package TestingFile;

import DataObject.Appointment.Appointment;
import DataObject.Prescription.Prescription;
import DataObject.Prescription.PrescriptionList;
import DepartmentObject.AccountInfoDatabase;
import DepartmentObject.Pharmacy;
import DepartmentObject.UserInfoDatabase;
import HumanObject.Pharmacist.Pharmacist;
import HumanObject.Patient.Patient;
import HumanObject.Doctors.Doctors;
import HumanObject.Administrator.Administrator;
import InputHandler.Input;
import ObjectUI.*;
import Serialisation.DataSerialisation;

import java.awt.*;
import java.util.Date;

public class Test {

    public static void Run1() {
        Prescription p = DataSerialisation.DeserialisePrescription("0-Medicine-10");
        p.print();
        System.out.println(DataSerialisation.SerialisePrescription(p));

        PrescriptionList list = DataSerialisation.DeserialisePrescriptionList("1-Medicine2-10/0-Medicine2-10");
        list.print();
        System.out.println(DataSerialisation.SerialisePrescriptionList(list));

        Appointment apt = DataSerialisation.DeserialiseAppointment("APT0001/3/Chemo/1001/001/2024-08-21-16-00/Empty/0-MedicineName1-10/0-MedicineName2-10");
        apt.print();
        System.out.println(DataSerialisation.SerialiseAppointment(apt));
    }

    public static void Run2() {
        Pharmacist pharmacist = new Pharmacist("Tim", new Date(1000,10,1),true);
        Pharmacy pharmacy = new Pharmacy();
//        PharmacistUI pharmacistUI = new PharmacistUI(pharmacy, pharmacist);
    }

    public static void Run3() {
//        UserInfoDatabase database = new UserInfoDatabase();
        Pharmacy pharmacy = new Pharmacy();
        //UserInfoDatabase database = nullnew UserInfoDatabase();
        Pharmacist pharmacist = new Pharmacist("Tim", new Date(1000,10,1),true);
        PharmacistUI pharmacistUI = new PharmacistUI(database,pharmacy, pharmacist);
        pharmacy.endPharmacy();
    }

    public static void Run4() {
        UserInfoDatabase database = new UserInfoDatabase();
        Pharmacy pharmacy = new Pharmacy();
        AccountInfoDatabase accountInfoDatabase = new AccountInfoDatabase();

        int choice;

        do {
            System.out.println("Welcome to HMS\n1. Login\n2. Change password\n3. Exit program");
            choice = Input.ScanInt("Choose one option:");
            switch (choice) {
                case 1:
                    String UserID = accountInfoDatabase.login();
                    String role = UserID.substring(0,2);
                    int ID = Integer.parseInt(UserID.substring(2));
                    switch (role) {
                        case "PA":
                            for (Patient p : database.getPatients()) {
                                if (p.getID() == ID) new PatientUI(database, pharmacy, p);
                            }
                            break;
                        case "DR":
                            for (Doctors d : database.getDoctors()) {
                                if (d.getID() == ID) new DoctorUI(database, pharmacy, d);
                            }
                            break;
                        case "PH":
                            for (Pharmacist p : database.getPharmacists()) {
                                if (p.getID() == ID) new PharmacistUI(database, pharmacy, p);
                            }
                            break;
                        case "AD":
                            for (Administrator a : database.getAdministrators()) {
                                if (a.getID() == ID) new AdminUI(database, pharmacy, a);
                            }
                            break;
                    }
                    break;
                case 2:
                    accountInfoDatabase.changePassword();
                    break;
                default:
                    break;
            }

        } while (choice < 3);
        pharmacy.endPharmacy();
    }
}
