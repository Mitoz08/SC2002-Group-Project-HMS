package TestingFile;

import DataObject.Appointment.Appointment;
import DataObject.Prescription.Prescription;
import DataObject.Prescription.PrescriptionList;
import DepartmentObject.Pharmacy;
import DepartmentObject.UserInfoDatabase;
import HumanObject.Pharmacist.Pharmacist;
import ObjectUI.PharmacistUI;
import Serialisation.DataSerialisation;

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
        Pharmacy pharmacy = new Pharmacy();
        UserInfoDatabase database = null; //new UserInfoDatabase();
        Pharmacist pharmacist = new Pharmacist("Tim", new Date(1000,10,1),true);
        PharmacistUI pharmacistUI = new PharmacistUI(database,pharmacy, pharmacist);
        pharmacy.endPharmacy();
    }
}
