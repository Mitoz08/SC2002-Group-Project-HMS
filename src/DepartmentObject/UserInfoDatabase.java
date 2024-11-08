package DepartmentObject;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Scanner;

import DataObject.Appointment.*;
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



//        try{
//            BufferedReader reader = new BufferedReader(new FileReader("HMS.txt"));
//            String line;
//            while ((line = reader.readLine()) != null){
//                temp = UserInfoDatabase.parseData(line); //Gets an ArrayList<String> of userInfo which has been already decrypted
//                System.out.println(temp);
//                String role = temp.get(0); //Check the role and create BasePerson Accordingly
//
//                switch(role){
//                    case "PA":
//                        ID = Integer.parseInt(temp.get(1));
//                        Name = temp.get(2);
//                        DOB = DataSerialisation.DeserialiseDate(temp.get(3));
//                        Gender = Boolean.valueOf(temp.get(4));
//                        bloodType = temp.get(5);
//                        String[] split = temp.get(6).split("|");
//                        String email = split[0];
//                        String contactNo = split[1];
//                        Contact contactPat = new Contact(email, contactNo);
//                        Patient tempPat = DataSerialisation.createPatient(ID,Name,DOB,Gender,bloodType,contactPat);
//                        this.patients.add(tempPat);
//                        System.out.println("Added patient");
//                        break;
//                    case "DR":
//
//                        ID = Integer.parseInt(temp.get(1));
//                        Name = temp.get(2);
//                        DOB = DataSerialisation.DeserialiseDate(temp.get(3));
//                        Gender = Boolean.valueOf(temp.get(4));
//                        Doctors tempDoc = DataSerialisation.createDoctor(ID,Name,DOB,Gender);
//                        this.doctors.add(tempDoc);
//                        break;
//                    case "PH":
//                        ID = Integer.parseInt(temp.get(1));
//                        Name = temp.get(2);
//                        DOB = DataSerialisation.DeserialiseDate(temp.get(3));
//                        Gender = Boolean.valueOf(temp.get(4));
//                        Pharmacist tempPharm = DataSerialisation.createPharmacist(ID, Name, DOB, Gender); // creates a new Pharmacist
//                        this.pharmacists.add(tempPharm); //adds it into the ArrayList<Pharmacists>
//                        break;
//
//                    case "AD":
//                        ID = Integer.parseInt(temp.get(1));
//                        Name = temp.get(2);
//                        DOB = DataSerialisation.DeserialiseDate(temp.get(3));
//                        Gender = Boolean.valueOf(temp.get(4));
//                        Administrator tempAdmin = DataSerialisation.createAdministrator(ID, Name, DOB, Gender);
//                        this.administrators.add(tempAdmin);
//                        break;
//                }
//            }
//
//        }catch(IOException e){
//            e.printStackTrace();
//        }
//        testRun();
//        loadFile();
    }

    public void endUserInfoDatabase() {
        saveFile();
    }

    public void testRun() {
        Patient P = new Patient(1001,"John", new Date(99,1,21), true, "O+", new Contact("John@gmail.com", "91234590"));
        this.patients.add(P);
        P = new Patient(1002,"May", new Date(103,0,11), false, "AB-", new Contact("May@outlook.com", "81736531"));
        this.patients.add(P);
        Patient.setLastID(1003);

        Doctors D = new Doctors(1001,"Ben", new Date(85,10,23), true);
        this.doctors.add(D);
        D = new Doctors(1002,"Fae", new Date(70,0,8), false);
        this.doctors.add(D);
        Doctors.setLastID(1003);

        Administrator A = new Administrator(1001, "Summer", new Date(90,11,31), false);
        this.administrators.add(A);
        A = new Administrator(1002, "Alfred", new Date(99,0,4), true);
        this.administrators.add(A);
        Administrator.setLastID(1003);

        Pharmacist PH = new Pharmacist(1001, "Pharah", new Date(99,3,5), false);
        this.pharmacists.add(PH);
        PH = new Pharmacist(1002, "Winston", new Date(65,4,22), true);
        this.pharmacists.add(PH);
        Pharmacist.setLastID(1003);
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
        switch (role){
            case PATIENT:
                for (Patient pat: this.patients){
                    if (pat.getID() == id){
                        return pat;
                    }
                }
                System.out.println("Patient not found in dataBase");
                break;
            case DOCTOR:
                for (Doctors doc: this.doctors){
                    if (doc.getID() == id){
                        return doc;
                    }
                }
                System.out.println("Doctor not found in database");
                break;
            case ADMINISTRATOR:
                for (Administrator ad: this.administrators){
                    if (ad.getID() == id){
                        return ad;
                    }
                }
                System.out.println("Administrator not found in database");
                break;
            case PHARMACIST:
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
        int docID = apt.getDoctorID();
        Doctors foundDoc = null;
        for (Doctors doc: this.doctors){
            if (doc.getID() == (docID)){
                foundDoc = doc;
            }
        }
        if (foundDoc == null){
            System.out.println("Doctor is not found in the database");
            return;
        }
        foundDoc.getPendingApt().addAppointment(apt);// saves it to foundDoc



        //To add the appointment inside the Ongoing for Patients
        int patID= apt.getPatientID();
        Patient foundPat = null;
        for (Patient pat: this.patients){
            if (pat.getID() == patID){
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
        for (Appointment apt: this.allAppointments[0]){
            if (apt.getAppointmentID().equals(toCancelApt.getAppointmentID())){
                flagFound=1; // found in Pending appointments
                this.allAppointments[0].removeAppointment(i); // remove appointment in Pending AppointmentList
            }
            i++;
        }
        i = 0;
        for (Appointment apt: this.allAppointments[1]){
            if (apt.getAppointmentID().equals(toCancelApt.getAppointmentID())){
                flagFound=1; // found in Ongoing appointments
                this.allAppointments[1].removeAppointment(i); // remove appointment in Ongoing AppointmentList
            }
            i++;
        }
        if (flagFound == 0){
            System.out.println("The Appointment is not found in the List of Pending/Ongoing Appointments");
            return;
        }


        //To remove the appointment inside Ongoing for Doctors
        i=0;
        int docID = toCancelApt.getDoctorID();
        Doctors foundDoc = null;
        for (Doctors doc: this.doctors){
            if (doc.getID() == docID){
                foundDoc = doc;
            }
        }
        if (foundDoc == null){
            System.out.println("The doctor was not found in the database, possibly not in the list of Ongoing Appointments ");
            return;
        };

        for (Appointment apt : foundDoc.getPendingApt()){
            if (apt.getAppointmentID().equals(toCancelApt.getAppointmentID())){
                foundDoc.getPendingApt().removeAppointment(i);
                break;
            }
            i++;

        }
        i = 0;
        for (Appointment apt : foundDoc.getOngoingApt()){
            if (apt.getAppointmentID().equals(toCancelApt.getAppointmentID())){
                foundDoc.getOngoingApt().removeAppointment(i);
                break;
            }
            i++;

        }


        //To remove the appointment inside the Ongoing for Patients
        i=0;
        int patID = toCancelApt.getPatientID();
        Patient foundPat = null;
        for (Patient pat: this.patients){
            if (pat.getID() == patID){
                foundPat = pat;
            }
        }
        if (foundPat == null){
            System.out.println("The patient was not found in the database, possibly not in the list of Ongoing Appointments");
            return;
        }

        for (Appointment apt: foundPat.getPending()){
            if (apt.getAppointmentID().equals(toCancelApt.getAppointmentID())){
                foundPat.getPending().removeAppointment(i);
                break;
            }
            i++;
        }
        i = 0;
        for (Appointment apt: foundPat.getOngoing()){
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

    public void completeApt(Appointment completedApt) {
        completedApt.setStatus(2);
        int i=0;
        AppointmentList temp = null;
        int flagFound = 0;
        for (Appointment apt: this.allAppointments[1]){
            if (apt.getAppointmentID().equals(completedApt.getAppointmentID())){
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
        int docID = completedApt.getDoctorID();
        Doctors foundDoc = null;
        for (Doctors doc: this.doctors){
            if (doc.getID() == docID){
                foundDoc = doc;
            }
        }
        if (foundDoc == null){
            System.out.println("The doctor was not found in the database, possibly not in the list of Ongoing Appointments ");
            return;
        };
        i = 0;
        for (Appointment apt : foundDoc.getOngoingApt()){
            if (apt.getAppointmentID().equals(completedApt.getAppointmentID())){
                foundDoc.getOngoingApt().removeAppointment(i);
                break;
            }
            i++;

        }


        //To remove the appointment inside the Ongoing for Patients
        int patID = completedApt.getPatientID();
        Patient foundPat = null;
        for (Patient pat: this.patients){
            if (pat.getID() == patID){
                foundPat = pat;
            }
        }
        if (foundPat == null){
            System.out.println("The patient was not found in the database, possibly not in the list of Ongoing Appointments");
            return;
        }
        i = 0;
        for (Appointment apt: foundPat.getOngoing()){
            if (apt.getAppointmentID().equals(completedApt.getAppointmentID())){
                foundPat.getOngoing().removeAppointment(i);
                break;
            }
            i++;
        }

        allAppointments[2].addAppointment(completedApt);
        foundDoc.getCompletedApt().addAppointment(completedApt);
        foundPat.getCompleted().addAppointment(completedApt);
    }

    public void docAcceptApt(Appointment acceptApt, Boolean accept){
        int i=0;
        AppointmentList temp = null;
        //0-Pending , 1- Ongoing, 2-Completed

        //Accept/Reject appointments that are in Pending

        int flagFound = 0;
        for (Appointment apt: this.allAppointments[0]){
            if (apt.getAppointmentID() == acceptApt.getAppointmentID()){
                flagFound=1; // found in Pending appointments
                this.allAppointments[0].removeAppointment(i); // remove appointment in Pending AppointmentList
                if (accept){
                    apt.setStatus(1);
                    this.allAppointments[1].addAppointment(acceptApt); // add the appointment in Ongoing AppointmentList
                    System.out.println("The appointment has been accepted\n");
                }
            }
            i++;
        }
        if (flagFound == 0){
            System.out.println("The Appointment is not found in the Request List of Appointments\n");
            return;
        }


        //To remove the appointment inside Ongoing for Doctors
        i=0;
        int docID = acceptApt.getDoctorID();
        Doctors foundDoc = null;
        for (Doctors doc: this.doctors){
            if (doc.getID() == docID){
                foundDoc = doc;
            }
        }
        if (foundDoc == null){
            System.out.println("Check if this is the correct doctor\n");
            return;
        }
        temp = foundDoc.getPendingApt();
        for (Appointment apt : temp){
            if (apt.getAppointmentID().equals(acceptApt.getAppointmentID())){
                foundDoc.getPendingApt().removeAppointment(i); //remove appointment in Pending AppointmentList
                if (accept){
                    foundDoc.getOngoingApt().addAppointment(acceptApt);//add appointment in Ongoing AppointmentList
                    System.out.println("The appointment has been added into the Ongoing List for Doctor\n");
                }

                break;
            }
            i++;

        }


        //To remove the appointment inside the Ongoing for Patients
        i=0;
        int patID = acceptApt.getPatientID();
        Patient foundPat = null;
        for (Patient pat: this.patients){
            if (pat.getID() == patID){
                foundPat = pat;
            }
        }
        if (foundPat == null){
            System.out.println("Check if this is the correct Patient\n");
            return;
        }
        temp = foundPat.getPending();
        for (Appointment apt: temp){
            if (apt.getAppointmentID().equals(acceptApt.getAppointmentID())){
                foundPat.getPending().removeAppointment(i); //remove appointment in Pending AppointmentList
                if (accept){
                    foundPat.getOngoing().addAppointment(acceptApt);//add appointment in Ongoing AppointmentList
                    System.out.println("The appointment has been added to the Ongoing list for Patient");
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
            case ADMINISTRATOR:
                roleStr = "AD";
                break;
            case DOCTOR:
                roleStr = "DR";
                break;
            case PHARMACIST:
                roleStr = "PH";
                break;
        }
        str.append(roleStr).append("*");
        str.append(id).append("*");
        str.append(name).append("*");
        temp = DataSerialisation.SerialiseDate(DOB);
        str.append(temp).append("*");
        str.append(Gender).append("*");
        return str.toString();

    }
    //is used by administrator to addStaff and fireStaff
    public void addToUserInfoDatabaseFile(BasePerson basePerson){

        ROLE role = basePerson.getRole();
        int ID = basePerson.getID();
        String Name = basePerson.getName();
        Date DOB = basePerson.getDOB();
        Boolean Gender = basePerson.getGender();

        String normal = UserInfoDatabase.serialiseDataStaff(role, ID, Name, DOB, Gender);
        String encrypted = DataEncryption.cipher(normal);

        try{
            BufferedWriter writer = new BufferedWriter(new FileWriter("HMS.txt",true));
            writer.write(encrypted + "\n");
            writer.close();
        }catch (IOException e){
            System.out.println("The file was not updated\n");
        }
        //MUST HAVE A LOGIC THAT ADDS THE ACCOUNT INTO DATABASE

    }

    public void toRemoveFromUserDatabaseFile(BasePerson basePerson){
        ArrayList<String> fileToArrayList = new ArrayList<String>();
        ArrayList<String> temp = new ArrayList<String>();
        String line;

        int ID= basePerson.getID();
        String name = basePerson.getName();


       try{
           BufferedReader reader = new BufferedReader(new FileReader("HMS.txt"));
           while ((line = reader.readLine()) != null){
               fileToArrayList.add(line);
           }
           reader.close();
       }catch(IOException e){
           System.out.println("The file was not read\n");
       }
       //This for loop finds the String that needs to be removed
       for (String str: fileToArrayList){
           temp = UserInfoDatabase.parseData(str);
           if (Integer.parseInt(temp.get(1)) == ID && temp.get(2).equals(name)){
               fileToArrayList.remove(temp);
           }
       }
       //This next one creates and overwrites the file HMS.txt
        try{
            BufferedWriter writer = new BufferedWriter(new FileWriter("HMS.txt"));
            for (String str: fileToArrayList){
                writer.write(str + "\n");
            }
            writer.close();
        }catch(IOException e){
            System.out.println("The file was not updated");
        }

        //MUST ALSO HAVE A LOGIC THAT REMOVES THE ACCOUNT FROM ACCOUNTINFODATABASE
    }

    public void loadFile() {
        File savefile = new File("HMS.txt");
        Scanner file;
        try {
            file = new Scanner(savefile);
            loadAccount(file);
            file.close();
        } catch (Exception e) {
            System.out.println("Error reading HMS.txt");
            return;
        } finally {
//            System.out.println("Finish load function");
        }

        savefile = new File("APT.txt");
        try {
            file = new Scanner(savefile);
            loadAppointment(file);
            file.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error reading APT.txt");
            return;
        } finally {
//            System.out.println("Finish load function");
        }
    }

    private void loadAccount(Scanner fileReader) {
        while (fileReader.hasNextLine()){
            String text = fileReader.nextLine();
            String[] temp = text.split("&");
            switch (temp[0]) {
                case "PA":
                    patients.add(DataSerialisation.DeserialisePatient(temp[1]));
                    break;
                case "DR":
                    doctors.add(DataSerialisation.DeserialiseDoctor(temp[1]));
                    break;
                case "PH":
                    pharmacists.add(DataSerialisation.DeserialisePharmacist(temp[1]));
                    break;
                case "AD":
                    administrators.add(DataSerialisation.DeserialiseAdministrator(temp[1]));
                    break;
                case "Static":
                    String[] textArray = temp[1].split("/");
                    int index = 0;
                    Patient.setLastID(Integer.parseInt(textArray[index++]));
                    Doctors.setLastID(Integer.parseInt(textArray[index++]));
                    Pharmacist.setLastID(Integer.parseInt(textArray[index++]));
                    Administrator.setLastID(Integer.parseInt(textArray[index++]));
                    break;
            }
        }
    }

    private void loadAppointment(Scanner fileWriter) {
        while (fileWriter.hasNextLine()) {
            Appointment apt = DataSerialisation.DeserialiseAppointment(DataEncryption.decipher(fileWriter.nextLine()));
            Patient patient = (Patient) getPerson(apt.getPatientID(),ROLE.PATIENT);
            Doctors doctor = (Doctors) getPerson(apt.getDoctorID(),ROLE.DOCTOR);
            switch (apt.getStatus()) {
                case PENDING:
                    patient.getPending().addAppointment(apt);
                    doctor.getPendingApt().addAppointment(apt);
                    allAppointments[0].addAppointment(apt);
                    break;
                case ONGOING:
                    patient.getOngoing().addAppointment(apt);
                    doctor.getOngoingApt().addAppointment(apt);
                    allAppointments[1].addAppointment(apt);
                    break;
                case COMPLETED:
                    patient.getCompleted().addAppointment(apt);
                    doctor.getCompletedApt().addAppointment(apt);
                    allAppointments[2].addAppointment(apt);
                    break;
                default:
                    break;
            }
        }
    }

    private void saveFile() {
        File savefile = new File("HMS.txt");
        FileWriter file;
        try {
            file = new FileWriter(savefile);
            saveAccount(file);
            file.close();
        } catch (Exception e) {
            System.out.println("Error writing into HMS.txt");
        }

        savefile = new File("APT.txt");

        try {
            file = new FileWriter(savefile);
            saveAppointment(file);
            file.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error writing into APT.txt");
        }

    }

    private void saveAccount(FileWriter fileWriter) throws IOException {

        for (Patient P: patients) fileWriter.write(DataEncryption.cipher(DataSerialisation.SerialisePatient(P)) + "\n");

        for (Doctors D: doctors) fileWriter.write(DataEncryption.cipher(DataSerialisation.SerialisedDoctor(D)) + "\n");

        for (Pharmacist P : pharmacists) fileWriter.write(DataEncryption.cipher(DataSerialisation.SerialisedPharmacist(P)) + "\n");

        for (Administrator A: administrators) fileWriter.write(DataEncryption.cipher(DataSerialisation.SerialisedAdministrator(A)) + "\n");

        String[] textArray = new String[] {String.valueOf(Patient.getLastID()), String.valueOf(Doctors.getLastID()), String.valueOf(Pharmacist.getLastID()),
                String.valueOf(Administrator.getLastID())};
        String text = "Static&" + DataSerialisation.convertStringArraytoString(textArray, "/");
        fileWriter.write(DataEncryption.cipher(text));
    }

    private void saveAppointment(FileWriter fileWriter) throws  IOException {

        for (AppointmentList list: allAppointments) for (Appointment apt: list) fileWriter.write(DataEncryption.cipher(DataSerialisation.SerialiseAppointment(apt)) + "\n");

    }
}
