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

        //0-Pending , 1- Ongoing, 2-Completed
        //Add the Appointment apt to pending
        this.allAppointments[0].addAppointment(apt);

        //To add the appointment inside Ongoing for Doctors
        String docName = apt.getDoctorname();
        Doctors foundDoc = null;
        for (Doctors doc: this.doctors){
            if (doc.getName().equals(docName)){
                foundDoc = doc;
            }
        }
        if (foundDoc == null){
            System.out.println("Doctor is not found in the database");
            return;
        }
        //foundDoc.getPending().addAppointment(apt);// saves it to foundDoc



        //To add the appointment inside the Ongoing for Patients
        String patName = apt.getPatientName();
        Patient foundPat = null;
        for (Patient pat: this.patients){
            if (pat.getName().equals(patName)){
                foundPat = pat;
            }
        }
        if (foundPat == null){
            System.out.println("Patient is not found in the database");
            return;
        }
        //foundPat.getPending().addAppointment(apt);//saves it to foundPat
        return;

    }
    public void cancelApt(Appointment toCancelApt){
        //0-Pending , 1- Ongoing, 2-Completed
        //Reschedules appointments that are only found in Ongoing
        int i=0;
        AppointmentList temp = null;
        int flagFound = 0;
        for (Appointment apt: this.allAppointments[1]){
            if (apt.getAppointmentID().equals(toCancelApt.getAppointmentID())){
                flagFound=1; // found in Pending appointments
                allAppointments[1].removeAppointment(i); // remove appointment in Pending AppointmentList
            }
            i++;
        }
        if (flagFound == 0){
            System.out.println("The Appointment is not found in the List of Ongoing Appointments");
            return;
        }


        //To remove the appointment inside Ongoing for Doctors
        i=0;
        String docName = toCancelApt.getDoctorname();
        Doctors foundDoc = null;
        for (Doctors doc: this.doctors){
            if (doc.getName().equals(docName)){
                foundDoc = doc;
            }
        }
        if (foundDoc == null){
            System.out.println("The doctor was not found in the database, possibly not in the list of Ongoing Appointments ");
            return;
        }
        //temp = foundDoc.getOngoing();
        for (Appointment apt : temp){
            if (apt.getAppointmentID().equals(toCancelApt.getAppointmentID())){
                //foundDoc.getOngoing().removeAppointment(i);
                break;
            }
            i++;

        }


        //To remove the appointment inside the Ongoing for Patients
        i=0;
        String patName = toCancelApt.getPatientName();
        Patient foundPat = new Patient();
        for (Patient pat: this.patients){
            if (pat.getName().equals(patName)){
                foundPat = pat;
            }
        }
        if (foundPat == null){
            System.out.println("The patient was not found in the database, possibly not in the list of Ongoing Appointments");
            return;
        }
        //temp = foundPat.getOngoing();
        for (Appointment apt: temp){
            if (apt.getAppointmentID().equals(toCancelApt.getAppointmentID())){
                //foundPat.getOngoing().removeAppointment(i);
                break;
            }
            i++;
        }


        toCancelApt = null; //FOR GARBAGE DISPOSAL
        return;
    }
    public void rescheduleApt(Appointment newApt, Appointment oldApt){

        cancelApt(oldApt); //oldApt goes to Garbage Disposal
        scheduleApt(newApt); // newApt goes in to Pending

        return;

    }
    public void docAcceptApt(Appointment acceptApt, Boolean accept){
        int i;
        AppointmentList temp = null;

        // AppointmentList request. AppointmentList Cancel
        if (accept){
            //APT_STATUS goes ONGOING
        }
        else if (!accept){
            //0-Pending , 1- Ongoing, 2-Completed
            //Reschedules appointments that are only found in Ongoing
            i=0;
            int flagFound = 0;
            for (Appointment apt: this.allAppointments[0]){
                if (apt.getAppointmentID().equals(acceptApt.getAppointmentID())){
                    flagFound=1; // found in Pending appointments
                    allAppointments[0].removeAppointment(i); // remove appointment in Pending AppointmentList
                }
                i++;
            }
            if (flagFound == 0){
                System.out.println("The Appointment is not found in the Request List of Appointments");
                return;
            }


            //To remove the appointment inside Ongoing for Doctors
            i=0;
            String docName = acceptApt.getDoctorname();
            Doctors foundDoc = null;
            for (Doctors doc: this.doctors){
                if (doc.getName().equals(docName)){
                    foundDoc = doc;
                }
            }
            if (foundDoc == null){
                System.out.println("Check if this is the correct doctor");
                return;
            }
            //temp = foundDoc.getPending();
            for (Appointment apt : temp){
                if (apt.getAppointmentID().equals(acceptApt.getAppointmentID())){
                    //foundDoc.getPending().removeAppointment(i);
                    break;
                }
                i++;

            }


            //To remove the appointment inside the Ongoing for Patients
            i=0;
            String patName = acceptApt.getPatientName();
            Patient foundPat = new Patient();
            for (Patient pat: this.patients){
                if (pat.getName().equals(patName)){
                    foundPat = pat;
                }
            }
            if (foundPat == null){
                return;
            }
            //temp = foundPat.getPending();
            for (Appointment apt: temp){
                if (apt.getAppointmentID().equals(acceptApt.getAppointmentID())){
                    //foundPat.getPending().removeAppointment(i);
                    break;
                }
                i++;
            }
            acceptApt = null;

        }

        return;
    }



    /* updateFile is used by administrator to addStaff and fireStaff
    public static void updateFile(){

    }


    private static String serialiseDataPatient(ROLE role, int id, String name, Date DOB, Boolean Gender, String BloodType,Contact contact, ArrayList<String> DoctorAssigned, AppointmentList onGoingAptList, AppointmentList completeAptList, AppointmentList pendingAptList, PrescriptionList prescripList){
        String temp;
        StringBuilder str = new StringBuilder();
        str.append(role).append("*");
        str.append(id).append("*");
        str.append(name).append("*");
        temp = DataSerialisation.SerialiseDate(DOB);
        str.append(temp).append("*");
        str.append(Gender).append("*");
        str.append(BloodType).append("*");
        //str.append(contact.getEmail()).append("*");
        //str.append(contact.getPassword()).append("*");
        for (String doctor: DoctorAssigned){
            str.append(doctor).append("|");
        }
        str.append("*");
        for (Appointment apt: onGoingAptList){
            temp = DataSerialisation.SerialiseAppointment(apt);
            str.append(temp).append("|");
        }
        str.append("*");
        for (Appointment apt: completeAptList){
            temp = DataSerialisation.SerialiseAppointment(apt);
            str.append(temp).append("|");
        }
        str.append("*");
        for (Appointment apt: pendingAptList){
            temp = DataSerialisation.SerialiseAppointment(apt);
            str.append(temp).append("|");
        }
        str.append("*");
        for (Prescription pre: prescripList){
            temp = DataSerialisation.SerialisePrescription(pre);
            str.append(pre).append("|");
        }
        str.append("*");
        return str.toString();

    }
    private static String serialiseDataDoctors(ROLE role, int id, String name, Date DOB, Boolean Gender, AppointmentList onGoingAptList, AppointmentList completeAptList, AppointmentList pendingAptList){
        String temp;
        StringBuilder str = new StringBuilder();
        str.append(role).append("*");
        str.append(id).append("*");
        str.append(name).append("*");
        temp = DataSerialisation.SerialiseDate(DOB);
        str.append(temp).append("*");
        str.append(Gender).append("*");
        for (Appointment apt: onGoingAptList){
            temp = DataSerialisation.SerialiseAppointment(apt);
            str.append(temp).append("|");
        }
        str.append("*");
        for (Appointment apt: completeAptList){
            temp = DataSerialisation.SerialiseAppointment(apt);
            str.append(temp).append("|");
        }
        for (Appointment apt: pendingAptList){
            temp = DataSerialisation.SerialiseAppointment(apt);
            str.append(temp).append("|");
        }
        str.append("*");

        return str.toString();

    }

    private static String serialiseDataAdminAndPharma(ROLE role, int id, String name, Date DOB, Boolean Gender){
        String temp;
        StringBuilder str = new StringBuilder();
        str.append(role).append("*");
        str.append(id).append("*");
        str.append(name).append("*");
        temp = DataSerialisation.SerialiseDate(DOB);
        str.append(temp).append("*");
        str.append(Gender).append("*");
        return str.toString();

    }

     */



}
