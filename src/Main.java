import DepartmentObject.Pharmacy;
import DepartmentObject.UserInfoDatabase;
import HumanObject.Administrator.Administrator;
import HumanObject.Doctors.Doctors;
import HumanObject.Patient.Patient;
import HumanObject.Pharmacist.Pharmacist;
import ObjectUI.AdminUI;
import ObjectUI.DoctorUI;
import ObjectUI.PatientUI;
import ObjectUI.PharmacistUI;
import TestingFile.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class Main {
    public static void main(String[] args) {
//        Test.Run2();
//        Test.Run3();

        UserInfoDatabase database = new UserInfoDatabase();
        Pharmacy pharmacy = new Pharmacy();
        Patient patient = database.getPatients().getFirst();
        Doctors doctor = database.getDoctors().getFirst();
        Administrator admin = database.getAdministrators().getFirst();
        Pharmacist pharma = database.getPharmacists().getFirst();

        //PatientUI patui = new PatientUI(database,pharmacy,patient);
        //DoctorUI docUI = new DoctorUI(database,pharmacy,doctor);
        //PharmacistUI pharmUI = new PharmacistUI(database,pharmacy,pharma);
        AdminUI adminUI = new AdminUI(database,pharmacy,admin);
    }
}