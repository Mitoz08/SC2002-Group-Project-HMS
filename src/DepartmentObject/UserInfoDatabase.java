package DepartmentObject;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

import DataObject.Appointment.Appointment;
import DataObject.Appointment.AppointmentList;
import DataObject.Prescription.PrescriptionList;
import HumanObject.Administrator.Administrator;
import HumanObject.BasePerson;
import HumanObject.Doctors.Doctors;
import HumanObject.Patient.Contact;
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
    private AppointmentList[] allAppointments; //0-Pending , 1- Ongoing, 2- Completed


    public UserInfoDatabase(ArrayList<Patient> patients, ArrayList<Doctors> doctors, ArrayList<Administrator> administrators, ArrayList<Pharmacist> pharmacists, AppointmentList[] allAppointments){

        this.patients = patients;
        this.doctors = doctors;
        this.administrators = administrators;
        this.allAppointments = allAppointments;

    }
    public UserInfoDatabase(){
        this.patients = new ArrayList<Patient>();
        this.doctors = new ArrayList<Doctors>();
        this.administrators = new ArrayList <Administrator>();
        this.pharmacists = new ArrayList<Pharmacist>();
        this.allAppointments = new AppointmentList[3];

        for (int i = 0; i < 3; i++) allAppointments[i] = new AppointmentList(true);
        ArrayList<String> temp = new ArrayList<>();

        int ID;
        String Name;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date DOB;
        Boolean Gender;
        String bloodType;
        Contact contact;



        try{
            BufferedReader reader = new BufferedReader(new FileReader("HMS.txt"));
            String line;
            while ((line = reader.readLine()) != null){
                temp = UserInfoDatabase.parseData(line); //Gets an ArrayList<String> of userInfo which has been already decrypted
                System.out.println(temp);
                String role = temp.get(0); //Check the role and create BasePerson Accordingly

                switch(role){
                    case "PA":
                        ID = Integer.parseInt(temp.get(1));
                        Name = temp.get(2);
                        DOB = DataSerialisation.DeserialiseDate(temp.get(3));
                        Gender = Boolean.valueOf(temp.get(4));
                        bloodType = temp.get(5);
                        String[] split = temp.get(6).split("|");
                        String email = split[0];
                        String contactNo = split[1];
                        Contact contactPat = new Contact(email, contactNo);
                        Patient tempPat = DataSerialisation.createPatient(ID,Name,DOB,Gender,bloodType,contactPat);
                        this.patients.add(tempPat);
                        System.out.println("Added patient");
                        break;
                    case "DR":

                        ID = Integer.parseInt(temp.get(1));
                        Name = temp.get(2);
                        DOB = DataSerialisation.DeserialiseDate(temp.get(3));
                        Gender = Boolean.valueOf(temp.get(4));
                        Doctors tempDoc = DataSerialisation.createDoctor(ID,Name,DOB,Gender);
                        this.doctors.add(tempDoc);
                        break;
                    case "PH":
                        ID = Integer.parseInt(temp.get(1));
                        Name = temp.get(2);
                        DOB = DataSerialisation.DeserialiseDate(temp.get(3));
                        Gender = Boolean.valueOf(temp.get(4));
                        Pharmacist tempPharm = DataSerialisation.createPharmacist(ID, Name, DOB, Gender); // creates a new Pharmacist
                        this.pharmacists.add(tempPharm); //adds it into the ArrayList<Pharmacists>
                        break;

                    case "AD":
                        ID = Integer.parseInt(temp.get(1));
                        Name = temp.get(2);
                        DOB = DataSerialisation.DeserialiseDate(temp.get(3));
                        Gender = Boolean.valueOf(temp.get(4));
                        Administrator tempAdmin = DataSerialisation.createAdministrator(ID, Name, DOB, Gender);
                        this.administrators.add(tempAdmin);
                        break;
                }
            }

        }catch(IOException e){
            e.printStackTrace();
        }
    }
    //This class method is to decrypt the string and return an ArrayList<String> which are the user info
    private static ArrayList<String> parseData(String string){
        String decrypt = DataEncryption.decipher(string);
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
    public ArrayList<Patient> getPatients(){return this.patients;}
    public AppointmentList[] getAllAppointments(){return this.allAppointments;}

    public BasePerson getPerson(int id, ROLE role){

        BasePerson notReal = null;
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

        BasePerson notReal = null;
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
        int docName = apt.getDoctorID();
        Doctors foundDoc = null;
        for (Doctors doc: this.doctors){
            if (doc.getID() == (docName)){
                foundDoc = doc;
            }
        }
        if (foundDoc == null){
            System.out.println("Doctor is not found in the database");
            return;
        }
        foundDoc.getPendingApt().addAppointment(apt);// saves it to foundDoc



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
        foundPat.getPending().addAppointment(apt);//saves it to foundPat
        return;

    }


    public void cancelApt(Appointment toCancelApt){
        //0-Pending , 1- Ongoing, 2-Completed

        //Cancel appointments that are only found in Ongoing
        int i=0;
        AppointmentList temp = null;
        int flagFound = 0;
        for (Appointment apt: this.allAppointments[1]){
            if (apt.getAppointmentID().equals(toCancelApt.getAppointmentID())){
                flagFound=1; // found in Ongoing appointments
                this.allAppointments[1].removeAppointment(i); // remove appointment in Ongoing AppointmentList
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
        temp = foundDoc.getOngoingApt();
        for (Appointment apt : temp){
            if (apt.getAppointmentID().equals(toCancelApt.getAppointmentID())){
                foundDoc.getOngoingApt().removeAppointment(i);
                break;
            }
            i++;

        }


        //To remove the appointment inside the Ongoing for Patients
        i=0;
        String patName = toCancelApt.getPatientName();
        Patient foundPat = null;
        for (Patient pat: this.patients){
            if (pat.getName().equals(patName)){
                foundPat = pat;
            }
        }
        if (foundPat == null){
            System.out.println("The patient was not found in the database, possibly not in the list of Ongoing Appointments");
            return;
        }
        temp = foundPat.getOngoing();
        for (Appointment apt: temp){
            if (apt.getAppointmentID().equals(toCancelApt.getAppointmentID())){
                foundPat.getOngoing().removeAppointment(i);
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
        int i=0;
        AppointmentList temp = null;
        //0-Pending , 1- Ongoing, 2-Completed

        //Accept/Reject appointments that are in Pending

        int flagFound = 0;
        for (Appointment apt: this.allAppointments[0]){
            if (apt.getAppointmentID().equals(acceptApt.getAppointmentID())){
                flagFound=1; // found in Pending appointments
                this.allAppointments[0].removeAppointment(i); // remove appointment in Pending AppointmentList
                if (accept){
                    this.allAppointments[1].addAppointment(acceptApt); // add the appointment in Ongoing AppointmentList
                }
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
        temp = foundDoc.getPendingApt();
        for (Appointment apt : temp){
            if (apt.getAppointmentID().equals(acceptApt.getAppointmentID())){
                foundDoc.getPendingApt().removeAppointment(i); //remove appointment in Pending AppointmentList
                if (accept){
                    foundDoc.getOngoingApt().addAppointment(acceptApt);//add appointment in Ongoing AppointmentList
                }

                break;
            }
            i++;

        }


        //To remove the appointment inside the Ongoing for Patients
        i=0;
        String patName = acceptApt.getPatientName();
        Patient foundPat = null;
        for (Patient pat: this.patients){
            if (pat.getName().equals(patName)){
                foundPat = pat;
            }
        }
        if (foundPat == null){
            return;
        }
        temp = foundPat.getPending();
        for (Appointment apt: temp){
            if (apt.getAppointmentID().equals(acceptApt.getAppointmentID())){
                foundPat.getPending().removeAppointment(i); //remove appointment in Pending AppointmentList
                if (accept){
                    foundPat.getOngoing().addAppointment(acceptApt);//add appointment in Ongoing AppointmentList
                }

                break;
            }
            i++;
        }
        if (!accept){
            acceptApt = null; //PUTS IN GARBAGE DISPOSAL IF REJECTED
        }
        return;
    }



    private static String serialiseDataPatient(int id, String name, Date DOB, Boolean Gender, String BloodType, Contact contact, ArrayList<String> DoctorAssigned, AppointmentList onGoingAptList, AppointmentList completeAptList, AppointmentList pendingAptList, PrescriptionList prescripList){
        String temp;
        StringBuilder str = new StringBuilder();
        str.append("PA").append("*");
        str.append(id).append("*");
        str.append(name).append("*");
        temp = DataSerialisation.SerialiseDate(DOB);
        str.append(temp).append("*");
        str.append(Gender).append("*");
        str.append(BloodType).append("*");
        str.append(contact.getEmail()).append("*");
        str.append(contact.getContactNumber()).append("*");
        return str.toString();

    }

    private static String serialiseDataStaff(ROLE role, int id, String name, Date DOB, Boolean Gender){
        String temp;
        StringBuilder str = new StringBuilder();
        String roleStr = "";
        ROLE roleToStr = role;
        switch(roleToStr){
            case ROLE.ADMINISTRATOR:
                roleStr = "AD";
                break;
            case ROLE.DOCTOR:
                roleStr = "DR";
                break;
            case ROLE.PHARMACIST:
                roleStr = "PH";
                break;
        }
        str.append(roleToStr).append("*");
        str.append(id).append("*");
        str.append(name).append("*");
        temp = DataSerialisation.SerialiseDate(DOB);
        str.append(temp).append("*");
        str.append(Gender).append("*");
        return str.toString();

    }
    //is used by administrator to addStaff and fireStaff
    public void updateUserInfoDatabaseFile(BasePerson basePerson){

    }




}
