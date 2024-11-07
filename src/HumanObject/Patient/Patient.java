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
    private Contact contact;
    private String email;
    private ArrayList<String> doctorAssigned;
    private AppointmentList Pending;
    private AppointmentList Ongoing;
    private AppointmentList Completed;
    private PrescriptionList Medicine;

    //This constructor is used to initialise from TXT file
    public Patient(int ID, String Name, Date DOB, Boolean Gender, String bloodType, Contact contact){
        super(ID,Name, DOB, Gender);
        this.bloodType = bloodType;
        this.contact = contact;
        this.role = ROLE.PATIENT;
        this.Pending = new AppointmentList(true);
        this.Ongoing = new AppointmentList(true);
        this.Completed = new AppointmentList(false);
    }

    //This constructor is used to add to TXT file
    public Patient(String Name, Date DOB, Boolean Gender, String bloodType, Contact contact) {
        super(lastID++, Name, DOB, Gender);
        this.bloodType = bloodType;
        this.contact = contact;
        this.role = ROLE.PATIENT;
        this.Pending = new AppointmentList(true);
        this.Ongoing = new AppointmentList(true);
        this.Completed = new AppointmentList(false);
    }

    public void printMedicalRecord(){
        System.out.println("Patient ID: " +  this.getID());
        System.out.println("Patient Name: " + this.getName());
        String gender = this.gender? "Male" : "Female";
        System.out.println("Patient Gender: " + gender);
        System.out.println("Date of Birth: " + this.getDOB());
        System.out.println("Patient Contact Number: " + this.getContact().getContactNumber());
        System.out.println("Patient Email: " + this.getContact().getEmail());
        System.out.println("Patient Blood Type: " + this.getBloodType());
        this.Completed.print(true);
    }

    public String getBloodType(){
        return this.bloodType;
    }

    public Contact getContact(){
        return this.contact;
    }

    public ArrayList<String> getDoctorAssigned(){
        return this.doctorAssigned;
    }

    public AppointmentList getOngoing(){
        return this.Ongoing;
    }

    public AppointmentList getCompleted(){
        return this.Completed;
    }

    public AppointmentList getPending(){
        return this.Pending;
    }

    public PrescriptionList getMedicine(){
        return this.Medicine;
    }

    public void setBloodType(String bloodType){
        this.bloodType = bloodType;
    }

    public void setContact(Contact contact){
        this.contact = contact;
    }

    public void setDoctorAssigned(ArrayList<String> doctorAssigned){
        this.doctorAssigned = doctorAssigned;
    }

    public void setOngoing(AppointmentList ongoing){
        this.Ongoing = ongoing;
    }

    public void setPending(AppointmentList ongoing){
        this.Pending = Pending;
    }

    public void setCompleted(AppointmentList completed){
        this.Completed = completed;
    }

    public void setMedicine(PrescriptionList medicine){
        this.Medicine = medicine;
    }

    public static void setLastID(int ID) {lastID = ID;}

    public static int getLastID() {return lastID;}
}

