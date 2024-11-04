import DepartmentObject.Pharmacy;
import DepartmentObject.UserInfoDatabase;
import HumanObject.Patient.Patient;
import ObjectUI.PatientUI;
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
        System.out.println(database.getPatients());
        Patient patient = database.getPatients().getFirst();

        PatientUI ui = new PatientUI(database,pharmacy,patient);
    }
}