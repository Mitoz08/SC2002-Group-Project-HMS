package DepartmentObject;

import java.io.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

import DataObject.Appointment.Appointment;
import DataObject.Appointment.AppointmentList;
import DataObject.Prescription.Prescription;
import DataObject.Prescription.PrescriptionList;
import HumanObject.Administrator.Administrator;
import HumanObject.BasePerson;
import HumanObject.Doctors.Doctors;
import HumanObject.Patient.Patient;
import HumanObject.Pharmacist.Pharmacist;
import HumanObject.ROLE;
import Serialisation.DataEncryption;
import Serialisation.DataSerialisation;


public class UserInfoDatabase {

    private ArrayList<Patient> patients;
    private ArrayList<Doctors> doctors;
    private ArrayList<Administrator> administrators;
    private ArrayList<Pharmacist> pharmacists;
    private AppointmentList[] allAppointments; //1-Pending , 2- Accepted, 3-Rejected, 4-Ongoing


    public UserInfoDatabase(ArrayList<Patient> patients, ArrayList<Doctors> doctors, ArrayList<Administrator> administrators, ArrayList<Pharmacist> pharmacists, AppointmentList[] allAppointments){

        this.patients = patients;
        this.doctors = doctors;
        this.administrators = administrators;
        this.allAppointments = allAppointments;

    }
    public UserInfoDatabase(){

    }
    /*addToFile is used when someone creates a new user
    public static void addToFile(){
        Scanner sc = new Scanner(System.in);

        String encrypt //TO PUT INTO FILE
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("HMS.txt", true));
            writer.write(encrypt + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return;
    }
     */

    //Class method to read file and turn it into an ArrayList to be used
    private ArrayList<String> readFile(){
        int i=0;
        ArrayList<String>strArray = new ArrayList<>();
        try {
            BufferedReader reader = new BufferedReader((new FileReader("HMS.txt")));
            String line;
            while ((line = reader.readLine()) != null){
                strArray.add(i++, line);
            }
            reader.close();
        }catch (IOException e) {
            e.printStackTrace();
        }
        return strArray;

    }
    private void updateFile(){

    }

    public BasePerson getPerson(int id, ROLE role){

        BasePerson notReal = new BasePerson();
        String roleS;
        roleS = role.toString();
        switch (roleS){
            case "PA":
                for (Patient pat: this.patients){
                    if (pat.getID() == id){
                        return pat;
                    }
                }
                System.out.println("Patient not found in dataBase");
                break;
            case "DR":
                for (Doctors doc: this.doctors){
                    if (doc.getID() == id){
                        return doc;
                    }
                }
                System.out.println("Doctor not found in database");
                break;
            case "AD":
                for (Administrator ad: this.administrators){
                    if (ad.getID() == id){
                        return ad;
                    }
                }
                System.out.println("Administrator not found in database");
                break;
            case "PH":
                for (Pharmacist ph: this.pharmacists){
                    if (ph.getID() == id){
                        return ph;
                    }
                }
                break;
            default:
                System.out.println("Unexpected input try again");
                break;

        }
        return notReal;

    }
    public BasePerson getPerosn(String name, ROLE role){

        BasePerson notReal = new BasePerson();
        String roleS;
        roleS = role.toString();
        switch (roleS){
            case "PA":
                for (Patient pat: this.patients){
                    if (pat.getName().equals(name)){
                        return pat;
                    }
                }
                System.out.println("Patient not found in dataBase");
                break;
            case "DR":
                for (Doctors doc: this.doctors){
                    if (doc.getName().equals(name)){
                        return doc;
                    }
                }
                System.out.println("Doctor not found in database");
                break;
            case "AD":
                for (Administrator ad: this.administrators){
                    if (ad.getName().equals(name)){
                        return ad;
                    }
                }
                System.out.println("Administrator not found in database");
                break;
            case "PH":
                for (Pharmacist ph: this.pharmacists){
                    if (ph.getName().equals(name)){
                        return ph;
                    }
                }
                break;
            default:
                System.out.println("Unexpected input try again");
                break;

        }
        return notReal;

    }

    public void getStaffList() {
        for (Doctors doc: this.doctors){
            System.out.println("Name: "+ doc.getName() + ", ID: " + doc.getID() + ", Role: " + doc.getRole());
        }
        for (Pharmacist ph: this.pharmacists){
            System.out.println("Name: "+ ph.getName() + ", ID: " + ph.getID() + ", Role: " + ph.getRole());
        }
        for (Administrator ad: this.administrators){
            System.out.println("Name: "+ ad.getName() + ", ID: " + ad.getID() + ", Role: " + ad.getRole());
        }
        return;
    }

    public void scheduleApt(Appointment apt){
        //patientID and doctorID are found in the apt

    }
    public void rescheduleApt(Appointment newApt, Appointment oldApt){

    }
    public void cancelApt(Appointment toCancelApt){

    }


    /*
    serialiseData (for Patients) will have the following String
    serialiseData is being called in updateFile()
    There are 11 parts of info in this string
    Role*id*Name*DOB*Gender*BloodType*Contact|Contact*doctor|doctor|doctor|...*apt|apt|apt|...|apt*apt|apt|apt|apt|...|apt*pre|pre|pre|...|pre|*

    private String serialiseData(ROLE role, int id, String name, Date DOB, Boolean Gender, String BloodType, String[] Contact, ArrayList<String> DoctorAssigned, AppointmentList onGoingAptList, AppointmentList completeAptList, PrescriptionList prescripList){
        StringBuilder str = new StringBuilder();
        str.append(role).append("*");
        str.append(id).append("*");
        str.append(name).append("*");
        str.append(DOB).append("*");
        str.append(Gender).append("*");
        str.append(BloodType).append("*");
        for (String contact: Contact){
            str.append(contact).append("|");
        }
        str.append("*");
        for (String doctor: DoctorAssigned){
            str.append(doctor).append("|");
        }
        str.append("*");
        for (Appointment apt: onGoingAptList){
            str.append(apt).append("|");
        }
        str.append("*");
        for (Appointment apt: completeAptList){
            str.append(apt).append("|");
        }
        str.append("*");
        for (Prescription pre: prescripList){
            str.append(pre).append("|");
        }
        str.append("*");
        return str.toString();

    }
     */
    /*serialiseData (for doctors)  will have the following string
    There are 8 parts to the string
    Role*id*Name*DOB*Gender*BloodType*apt|apt|apt|...|apt*apt|apt|apt|apt|...|apt*availability|availability|...availability|*
    private static String serialiseData(ROLE role, int id, String name, Date DOB, Boolean Gender, AppointmentList onGoingAptList, AppointmentList completeAptList){
        StringBuilder str = new StringBuilder();
        str.append(role).append("*");
        str.append(id).append("*");
        str.append(name).append("*");
        str.append(DOB).append("*");
        str.append(Gender).append("*");
        for (Appointment apt: onGoingAptList){
            String aptTemp = DataSerialisation.SerialiseAppointment(apt);
            str.append(aptTemp).append("|");
        }
        str.append("*");
        for (Appointment apt: completeAptList){
            String aptTemp = DataSerialisation.SerialiseAppointment(apt);
            str.append(aptTemp).append("|");
        }
        str.append("*");

        Have to add the boolean availability 7x5 matrix

        str.append("*");
        return str.toString();

    }

    serialiseData (for administrators and pharmacists) will have the following string
    Role*id*Name*DOB*Gender*
    private static String serialiseData(ROLE role, int id, String name, Date DOB, Boolean Gender){
        StringBuilder str = new StringBuilder();
        str.append(role).append("*");
        str.append(id).append("*");
        str.append(name).append("*");
        str.append(DOB).append("*");
        str.append(Gender).append("*");
        return str.toString();

    }
     */


}
