package DepartmentObject;

import java.io.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Scanner;

import DataObject.Appointment.APT_STATUS;
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
    private AppointmentList[] allAppointments; //0-Pending , 1- Approved, 2-Rejected, 3-Cancelled, 4- Completed


    public UserInfoDatabase(ArrayList<Patient> patients, ArrayList<Doctors> doctors, ArrayList<Administrator> administrators, ArrayList<Pharmacist> pharmacists, AppointmentList[] allAppointments){

        this.patients = patients;
        this.doctors = doctors;
        this.administrators = administrators;
        this.allAppointments = allAppointments;

    }
    public UserInfoDatabase(){
        ArrayList<Patient> patients = new ArrayList<Patient>();
        ArrayList<Doctors> doctors = new ArrayList<Doctors>();
        ArrayList<Administrator> administrators = new ArrayList <Administrator>();
        ArrayList<Pharmacist> pharmacists = new ArrayList<Pharmacist>();
        AppointmentList[] allAppointments = new AppointmentList[5];
        ArrayList<String> temp = new ArrayList<>();

        int ID;
        String Name;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date DOB;
        Boolean Gender;
        String bloodType;
        //Contact contact;

        try{
            BufferedReader reader = new BufferedReader(new FileReader("HMS.txt"));
            String line;
            while ((line = reader.readLine()) != null){
                temp = UserInfoDatabase.parseData(line); //Gets an ArrayList<String> of userInfo which has been already decrypted
                String role = temp.get(0); //Check the role and create BasePerson Accordingly

                switch(role){
                    case "PA":
                        ID = Integer.parseInt(temp.get(1));
                        Name = temp.get(2);
                        DOB = DataSerialisation.DeserialiseDate(temp.get(3));
                        Gender = Boolean.valueOf(temp.get(4));
                        bloodType = temp.get(5);
                        // contact
                        Patient tempPat = DataSerialisation.createPatient();
                        patients.add(tempPat);
                        break;
                    case "DR":

                        ID = Integer.parseInt(temp.get(1));
                        Name = temp.get(2);
                        DOB = DataSerialisation.DeserialiseDate(temp.get(3));
                        Gender = Boolean.valueOf(temp.get(4));
                        Doctors tempDoc = DataSerialisation.createDoctor(ID,Name,DOB,Gender);
                        doctors.add(tempDoc);
                        break;
                    case "PH":
                        ID = Integer.parseInt(temp.get(1));
                        Name = temp.get(2);
                        DOB = DataSerialisation.DeserialiseDate(temp.get(3));
                        Gender = Boolean.valueOf(temp.get(4));
                        Pharmacist tempPharm = DataSerialisation.createPharmacist(ID, Name, DOB, Gender); // creates a new Pharmacist
                        pharmacists.add(tempPharm); //adds it into the ArrayList<Pharmacists>
                        break;

                    case "AD":
                        ID = Integer.parseInt(temp.get(1));
                        Name = temp.get(2);
                        DOB = DataSerialisation.DeserialiseDate(temp.get(3));
                        Gender = Boolean.valueOf(temp.get(4));
                        Administrator tempAdmin = DataSerialisation.createAdministrator(ID, Name, DOB, Gender);
                        administrators.add(tempAdmin);
                        break;
                }
            }

        }catch(IOException e){
            e.printStackTrace();
        }

    }
    //This class method is to decrypt the string and return an ArrayList<String> which are the user info
    private static ArrayList<String> parseData(String string){
        String decrypt = DataEncryption.Decrypt(string);
        ArrayList<String> userInfo = new ArrayList<String>(Arrays.asList(decrypt.split("\\*")));
        return userInfo;
    }

    //The following are the getter functions
    public ArrayList<Doctors> getDoctors(){
        return this.doctors;
    }
    public ArrayList<Pharmacist> getPharmacists(){
        return this.pharmacists;
    }
    public ArrayList<Administrator> getAdministrators(){
        return this.administrators;
    }
    public ArrayList<Patient> getPatients(){
        return this.patients;
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
    public BasePerson getPerson(String name, ROLE role){

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


    public void scheduleApt(Appointment apt){
        //This function is for the
        //0-Pending , 1- Approved, 2-Rejected, 3-Cancelled, 4- Completed

        //To add the appointment inside Ongoing for Doctors
        String docName = apt.getDoctorname();
        Doctors foundDoc; // = new Doctors();
        for (Doctors doc: this.doctors){
            if (doc.getName().equals(docName)){
                foundDoc = doc;
            }
        }
        //To add the appointment inside the Ongoing for Patients
        String patName = apt.getPatientName();
        Patient foundPat = new Patient();
        for (Patient pat: this.patients){
            if (pat.getName().equals(patName)){
                foundPat = pat;
            }
        }



    }
    public void rescheduleApt(Appointment newApt, Appointment oldApt){




    }
    /*
    public void cancelApt(Appointment toCancelApt){
        APT_STATUS aptStatus = toCancelApt.getStatus();
        if (aptStatus.equals(APT_STATUS.APPORVED)){

            //APT_STATUS set to cancelled
            // looks through APPROVED status
            for (Appointment apt: allAppointments[1]){
                if (apt.getAppointmentID().equals(toCancelApt.getAppointmentID()){
                    //Remove apt from the AppointmentList
                    //set apt status to cancel
                }
            }

        }
        return;



    }

     */



    /* updateFile is used by administrator to addStaff and fireStaff
    public static void updateFile(){

    }

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
