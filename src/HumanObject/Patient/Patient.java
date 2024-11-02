package HumanObject.Patient;

import DataObject.Appointment.AppointmentList;
import DataObject.Prescription.PrescriptionList;
import HumanObject.BasePerson;
import HumanObject.ROLE;

import java.util.ArrayList;
import java.util.Date;

public class Patient extends BasePerson {

    private static int lastID=0;

    private String bloodType;
    // private Contact contact; Person who is doing Patient class to create Contact class
    private ArrayList<String> doctorAssigned;
    private AppointmentList Ongoing;
    private AppointmentList Completed;
    private PrescriptionList Medicine;

    /*THIS IS A SPECIFIC CONSTRUCTOR TO INITIALISE FROM THE TXT FILE
    public Patient(int ID, String Name, Date DOB, Boolean Gender, String bloodType, Contact contact) {
        super(lastID++, Name, DOB, Gender);
        this.bloodType = bloodType;
        this.contact = contact;
        this.role = ROLE.PATIENT;
    }
     */
}

