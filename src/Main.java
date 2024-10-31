import DataObject.Appointment.Appointment;
import DepartmentObject.Pharmacy;
import HumanObject.Pharmacist.Pharmacist;
import InputHandler.Input;
import ObjectUI.PharmacistUI;
import Serialisation.DataSerialisation;

import java.util.Date;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

//        Prescription p = DataSerialisation.DeserialisePrescription("0-Medicine-10");
//        p.print();
//        System.out.println(DataSerialisation.SerialisePrescription(p));
//
//        PrescriptionList list = DataSerialisation.DeserialisePrescriptionList("1-Medicine2-10/0-Medicine2-10");
//        list.print();
//        System.out.println(DataSerialisation.SerialisePrescriptionList(list));
//
//        Appointment apt = DataSerialisation.DeserialiseAppointment("APT0001/3/Chemo/1001/001/2024-08-21-16-00/Empty/0-MedicineName1-10/0-MedicineName2-10");
//        apt.print();
//        System.out.println(DataSerialisation.SerialiseAppointment(apt));
        Pharmacist pharmacist = new Pharmacist("Tim", new Date(1000,10,1),true);
        Pharmacy pharmacy = new Pharmacy();
        PharmacistUI pharmacistUI = new PharmacistUI(pharmacy, pharmacist);

    }
}