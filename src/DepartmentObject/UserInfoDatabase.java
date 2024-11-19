package DepartmentObject;

import java.io.*;
import java.util.*;

import DataObject.Appointment.*;
import DataObject.PharmacyObjects.MedicineData;
import DataObject.PharmacyObjects.MedicineRequest;
import DataObject.PharmacyObjects.RestockRequest;
import DataObject.Prescription.Prescription;
import HumanObject.Administrator.Administrator;
import HumanObject.BasePerson;
import HumanObject.Doctor.Doctor;
import HumanObject.Patient.Contact;
import HumanObject.Patient.Patient;
import HumanObject.Pharmacist.Pharmacist;
import HumanObject.ROLE;
import InputHandler.Input;
import ObjectUI.BaseUI;
import Serialisation.DataEncryption;
import Serialisation.DataSerialisation;

/**
 * The {@code UserInfoDatabase} class manages the user's information for the Hospital Management System
 * <p>
 *     An instance of this class works off of one database (one text file), allowing
 *     the Hospital Management System to include multiple UserInfoDatabase department if needed.
 * </p>
 * <p>
 *     This class uses the {@code BasePerson} class and its derived classes to store information for each user in the hospital
 * </p>
 *
 * @see BasePerson The Base class for all the users
 */
public class UserInfoDatabase {

    /** The ArrayList storing the patient object. */
    private ArrayList<Patient> patients;

    /** The ArrayList storing the doctor object. */
    private ArrayList<Doctor> doctors;

    /** The ArrayList storing the administrator object. */
    private ArrayList<Administrator> administrators;

    /** The ArrayList storing the pharmacist object. */
    private ArrayList<Pharmacist> pharmacists;

    /** The Array storing 3 AppointmentList (Pending, Ongoing and Completed AppointmentList). */
    private AppointmentList[] allAppointments; //0-Pending , 1- Ongoing, 2- Completed

    /** The HMS file name to read and write to. */
    private String HMSFileName;

    /** The APT file name to read and write to. */
    private String APTFileName;


    /**
     * Constructs a UserInfoDatabase that initialises an empty ArrayList of Patients, Doctors, Administrators and Pharmacists
     * Also initialises a List of AppointmentList (Index = 0 refer to Pending, Index = 1 refer to Ongoing and Index = 2 refer to Completed)
     * The AppointmentList for each is then initialised to be true (Dates are arranged in chronological order)
     */
    public UserInfoDatabase(){
        this.patients = new ArrayList<Patient>();
        this.doctors = new ArrayList<Doctor>();
        this.administrators = new ArrayList <Administrator>();
        this.pharmacists = new ArrayList<Pharmacist>();
        this.allAppointments = new AppointmentList[3];

        for (int i = 0; i < 3; i++) allAppointments[i] = new AppointmentList(true);
        ArrayList<String> temp = new ArrayList<>();

    }

    /**
     * Sets the HMS file for the {@code UserInfoDatabase} instance to read from
     * @param fileName file to read from
     */
    public void setHMSFileName(String fileName) {
        this.HMSFileName = fileName;
    }

    /**
     * Sets the APT file for the {@code UserInfoDatabase} instance to read from
     * @param fileName file to read from
     */
    public void setAPTFileName(String fileName) {
        this.APTFileName = fileName;
    }
    /**
     * Method use at the end of the main function call to save information on appointments and Staff/ Patient.
     *  Saves the information into respective files APT.TXT and HMS.txt
     */
    public void endUserInfoDatabase() {
        saveFile();
    }

    /**
     * A method used to test run the program by initialising the default medicine
     */
    public void testRun() {
        Patient P = new Patient(1001,"John", new Date(99,1,21), true, "O+", new Contact("John@gmail.com", "91234590"));
        this.patients.add(P);
        P = new Patient(1002,"May", new Date(103,0,11), false, "AB-", new Contact("May@outlook.com", "81736531"));
        this.patients.add(P);
        Patient.setLastID(1003);

        Doctor D = new Doctor(1001,"Ben", new Date(85,10,23), true, new HashMap<>());
        this.doctors.add(D);
        D = new Doctor(1002,"Fae", new Date(70,0,8), false, new HashMap<>());
        this.doctors.add(D);
        Doctor.setLastID(1003);

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


    /**
     * Gets the ArrayList of {@code Doctor}
     * @return ArrayList of {@code Doctor}
     */
    public ArrayList<Doctor> getDoctors(){return this.doctors;}

    /**
     * Gets the ArrayList of {@code Pharmacist}
     * @return ArrayList of {@code Pharmacist}
     */
    public ArrayList<Pharmacist> getPharmacists(){return this.pharmacists;}

    /**
     * Gets the ArrayList of {@code Administrator}
     * @return ArrayList of {@code Administrator}
     */
    public ArrayList<Administrator> getAdministrators(){return this.administrators;}

    /**
     * Gets the ArrayList of {@code Patient}
     * @return ArrayList of {@code Patient}
     */
    public ArrayList<Patient> getPatients(){return this.patients;}

    /**
     * Gets the Array of {@code AppointmentList}
     * @return Array of {@code AppointmentList}
     */
    public AppointmentList[] getAllAppointments(){return this.allAppointments;}

    /**
     * Getter to get a BasePerson based given the BasePerson ID and ROLE
     * Gets the ROLE and uses the switch case to loop through the correct ArrayList of Staff or Patient
     * Prints a statement when the Person is not found and returns null
     * @param id ID of the user
     * @param role role of the user
     * @return the person with the correct {@code role} and {@code ID}
     */
    public BasePerson getPerson(int id, ROLE role){

        BasePerson notReal = null;
        switch (role) {
            case PATIENT:
                for (Patient pat : this.patients) {
                    if (pat.getID() == id) {
                        return pat;
                    }
                }
                System.out.println("Patient not found in dataBase");
                break;
            case DOCTOR:
                for (Doctor doc : this.doctors) {
                    if (doc.getID() == id) {
                        return doc;
                    }
                }
                System.out.println("Doctor not found in database");
                break;
            case ADMINISTRATOR:
                for (Administrator ad : this.administrators) {
                    if (ad.getID() == id) {
                        return ad;
                    }
                }
                System.out.println("Administrator not found in database");
                break;
            case PHARMACIST:
                for (Pharmacist ph : this.pharmacists) {
                    if (ph.getID() == id) {
                        return ph;
                    }
                }
                break;
            default:
                System.out.println("No such role exists in the System");
                break;

        }
        return notReal;
    }

    /**
     * Finds the person with the correct {@code role} and {@code ID}
     * @param UserID String ID of the user
     * @return the person with the correct {@code role} and {@code ID}
     */
    public BasePerson getPerson (String UserID) {
        String role = UserID.substring(0,2);
        int ID = Integer.parseInt(UserID.substring(2));
        switch (role) {
            case "PA":
                return this.getPerson(ID, ROLE.PATIENT);
            case "DR":
                return this.getPerson(ID, ROLE.DOCTOR);
            case "PH":
                return this.getPerson(ID, ROLE.PHARMACIST);
            case "AD":
                return this.getPerson(ID, ROLE.ADMINISTRATOR);
        }
        return null;
    }

    /**
     * Getter to get a BasePerson based given the BasePerson Name and ROLE
     * Gets the ROLE and uses the switch case to loop through the correct ArrayList of Staff or Patient
     * Prints a statement when the Person is not found and returns null
     * @param name Name of the User
     * @param role Role of the user
     * @return the person with the correct {@code role} and {@code name}
     */
    public BasePerson getPerson(String name, ROLE role){

        BasePerson notReal = null;
        switch (role) {
            case PATIENT:
                for (Patient pat : this.patients) {
                    if (pat.getName().equals(name)) {
                        return pat;
                    }
                }
                System.out.println("Patient not found in dataBase");
                break;
            case DOCTOR:
                for (Doctor doc : this.doctors) {
                    if (doc.getName().equals(name)) {
                        return doc;
                    }
                }
                System.out.println("Doctor not found in database");
                break;
            case ADMINISTRATOR:
                for (Administrator ad : this.administrators) {
                    if (ad.getName().equals(name)) {
                        return ad;
                    }
                }
                System.out.println("Administrator not found in database");
                break;
            case PHARMACIST:
                for (Pharmacist ph : this.pharmacists) {
                    if (ph.getName().equals(name)) {
                        return ph;
                    }
                }
                break;
            default:
                System.out.println("No such role exists in the system");
                break;

        }
        return notReal;

    }



    /**
     * Schedules an appointment for a patient and assigns it to the appropriate doctor and patient appointment lists.
     * This method handles appointments in three states: Pending (0), Ongoing (1), and Completed (2).
     * The appointment is added to the pending list for both the doctor and patient involved in the appointment.
     *
     * @param apt The {@code Appointment} object to be scheduled. If {@code apt} is null, the method returns without action.
     *
     * If the doctor or patient specified in the appointment is not found in the database, a message is printed and the method exits.
     * Otherwise, the following steps are taken:
     *
     * <ul>
     *   <li>The appointment is added to the global pending appointments list (index 0 in {@code allAppointments}).</li>
     *   <li>The doctor's schedule is updated to include the specified date and timeslot for this appointment.</li>
     *   <li>The appointment is added to both the doctor and patient’s pending appointment lists.</li>
     * </ul>
     */
    public void scheduleApt(Appointment apt){
        //0-Pending , 1- Ongoing, 2-Completed
        if (apt == null) return;
        //Add the Appointment apt to pending
        this.allAppointments[0].addAppointment(apt);

        //To add the appointment inside Ongoing for Doctors
        int docID = apt.getDoctorID();
        Doctor foundDoc =  (Doctor) getPerson(docID, ROLE.DOCTOR);
        if (foundDoc == null){
            System.out.println("Doctor is not found in the database");
            return;
        }
        foundDoc.addTimeSlot(apt.getDate(),apt.getTimeSlot());
        foundDoc.getPendingApt().addAppointment(apt);// saves it to foundDoc



        //To add the appointment inside the Ongoing for Patients
        int patID= apt.getPatientID();
        Patient foundPat = (Patient) getPerson(patID, ROLE.PATIENT);
        if (foundPat == null){
            System.out.println("Patient is not found in the database");
            return;
        }
        foundPat.getPending().addAppointment(apt);//saves it to PendingList for foundPat
        return;

    }

    /**
     * Cancels an appointment for both the patient and doctor associated with the appointment.
     * The method searches for the appointment in the Pending and Ongoing lists, removing it from all necessary locations.
     *
     * <p>The method performs the following operations:</p>
     * <ul>
     *   <li>Searches for the appointment in the global Pending (index 0) and Ongoing (index 1) appointment lists.
     *       If found, it is removed from the list.</li>
     *   <li>If the appointment is not found in either Pending or Ongoing lists, a message is printed to indicate this.</li>
     *   <li>Removes the appointment from the doctor's Pending and Ongoing lists, and updates the doctor's timeslot availability.</li>
     *   <li>Removes the appointment from the patient's Pending and Ongoing lists.</li>
     * </ul>
     *
     * <p>If either the doctor or patient associated with the appointment is not found,
     * a message is printed indicating that the cancellation could not be completed.</p>
     *
     * @param toCancelApt The {@code Appointment} object to be canceled. If {@code toCancelApt} is null, the method returns without action.
     */
    public void cancelApt(Appointment toCancelApt){
        //0-Pending , 1- Ongoing, 2-Completed
        if (toCancelApt == null) return;
        //Cancel appointments that are only found in Pending
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
        //Cancel appointments that are only found in Ongoing
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


        //To remove the appointment inside Ongoing/ Pending for Doctors and patients
        i=0;
        int docID = toCancelApt.getDoctorID();
        Doctor foundDoc = (Doctor) getPerson(docID, ROLE.DOCTOR);
        if (foundDoc == null){
            System.out.println("The doctor was not found in the database, not in the list of Pending/Ongoing Appointments ");
            return;
        };
        foundDoc.removeTimeSlot(toCancelApt.getDate(),toCancelApt.getTimeSlot());
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

        i=0;
        int patID = toCancelApt.getPatientID();
        Patient foundPat = (Patient)getPerson(patID, ROLE.PATIENT);
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

    /**
     * Reschedules an appointment by canceling the old appointment and scheduling a new one.
     * The method first calls {@code cancelApt} to remove the old appointment and then calls {@code scheduleApt}
     * to add the new appointment to the Pending list.
     *
     * <p>If {@code oldApt} is successfully canceled, it is marked for garbage disposal, and {@code newApt}
     * is scheduled as a pending appointment.</p>
     *
     * @param newApt The new {@code Appointment} object to be scheduled in place of the old appointment.
     * @param oldApt The {@code Appointment} object to be canceled.
     */
    public void rescheduleApt(Appointment newApt, Appointment oldApt){

        if (oldApt == null){
            System.out.println("There is no appointment to be cancelled in the first place");
            return;
        }
        cancelApt(oldApt); //oldApt goes to Garbage Disposal
        scheduleApt(newApt); // newApt goes in to Pending

        return;

    }

    /**
     * Marks an appointment as completed and updates the relevant lists for both the doctor and patient involved.
     * The method moves the appointment from the Ongoing list to the Completed list.
     *
     * <p>The method performs the following operations:</p>
     * <ul>
     *   <li>Sets the status of the appointment to Completed (status code 2).</li>
     *   <li>Searches for the appointment in the global Ongoing list (index 1) and removes it.</li>
     *   <li>If the appointment is not found in the Ongoing list, a message is printed indicating this, and the method exits.</li>
     *   <li>Removes the appointment from the doctor's Ongoing list. If the doctor is not found, a message is printed.</li>
     *   <li>Removes the appointment from the patient's Ongoing list. If the patient is not found, a message is printed.</li>
     *   <li>Adds the appointment to the global Completed list (index 2), as well as the doctor’s and patient’s Completed lists.</li>
     * </ul>
     *
     * @param completedApt The {@code Appointment} object to be marked as completed.
     */
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
            System.out.println("The Appointment is not found in the List of Ongoing Appointments"); // Never found completedApt in the list of all Appointments
            return;
        }

        //To remove the appointment inside Ongoing for Doctors
        int docID = completedApt.getDoctorID();
        Doctor foundDoc = (Doctor) getPerson(docID, ROLE.DOCTOR);
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
        Patient foundPat = (Patient) getPerson(patID, ROLE.PATIENT);
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

    /**
     * Allows a doctor to accept or reject an appointment request from the Pending list.
     * If the appointment is accepted, it is moved from the Pending list to the Ongoing list
     * for both the doctor and patient. If rejected, the appointment is removed from Pending
     * and marked for garbage disposal.
     *
     * <p>The method performs the following steps:</p>
     * <ul>
     *   <li>Checks if the appointment is in the Pending list (index 0) and removes it.</li>
     *   <li>If the appointment is accepted:
     *       <ul>
     *         <li>Sets the appointment status to Ongoing (status code 1).</li>
     *         <li>Adds the appointment to the global Ongoing list (index 1), as well as the doctor’s and patient’s Ongoing lists.</li>
     *         <li>Prints confirmation messages upon successful addition to the lists.</li>
     *       </ul>
     *   </li>
     *   <li>If the appointment is rejected:
     *       <ul>
     *         <li>The appointment is removed from the Pending lists of the doctor, patient, and global lists.</li>
     *         <li>The appointment is set to null to mark it for garbage disposal.</li>
     *       </ul>
     *   </li>
     *   <li>If the appointment or doctor/patient is not found, appropriate messages are printed.</li>
     * </ul>
     *
     * @param acceptApt The {@code Appointment} object representing the appointment request.
     * @param accept A {@code Boolean} indicating whether the appointment is accepted (true) or rejected (false).
     */
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
        Doctor foundDoc = (Doctor)getPerson(docID,ROLE.DOCTOR);
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
        Patient foundPat = (Patient) getPerson(patID, ROLE.PATIENT);
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
            foundDoc.removeTimeSlot(acceptApt.getDate(),acceptApt.getTimeSlot());
            acceptApt = null; //PUTS IN GARBAGE DISPOSAL IF REJECTED
        }
        return;
    }

    /**
     * Registers a new patient by collecting personal and medical information and adding the patient
     * to the system's patient list.
     *
     * <p>This method performs the following steps:</p>
     * <ul>
     *   <li>Prompts the user to enter the patient's full name, date of birth, gender, and blood type.</li>
     *   <li>Initializes a new {@code Contact} object for the patient’s contact details.</li>
     *   <li>Creates a new {@code Patient} object with the collected information.</li>
     *   <li>Adds the newly created patient to the list of registered patients.</li>
     * </ul>
     *
     * @return The newly registered {@code Patient} object.
     */
    public Patient registerPatient() {
        System.out.println("Registering as Patient.");
        String Name = Input.ScanString("Full name:");
        Date DOB = Input.ScanDate("Date of birth");
        boolean Gender = Input.ScanBoolean("Are you a male?");
        String BloodType = Input.ScanString("What is your blood type:");
        Contact contact = new Contact();
        Patient patient = new Patient(Name, DOB, Gender, BloodType, contact);
        this.patients.add(patient);
        return patient;
    }

    /**
     * Loads data from saved files into the system by reading from "HMS.txt" and "APT.txt".
     *
     * <p>This method performs the following actions:</p>
     * <ul>
     *   <li>Attempts to open "HMS.txt" to load account data by calling {@code loadAccount(Scanner file)}.</li>
     *   <li>If "HMS.txt" cannot be read, it prints an error message and exits the method.</li>
     *   <li>Attempts to open "APT.txt" to load appointment data by calling {@code loadAppointment(Scanner file)}.</li>
     *   <li>If "APT.txt" cannot be read, it prints an error message and exits the method.</li>
     * </ul>
     * <p>Both file reading operations close the {@code Scanner} after reading.</p>
     */
    public void loadFile() {
        File savefile = new File(HMSFileName);
        Scanner file;
        try {
            file = new Scanner(savefile);
            loadAccount(file);
            file.close();
        } catch (Exception e) {
            System.out.println("Error reading HMS file");
            return;
        } finally {
//            System.out.println("Finish load function");
        }
        savefile = new File(APTFileName);
        try {
            file = new Scanner(savefile);
            loadAppointment(file);
            file.close();
        } catch (Exception e) {
            System.out.println("Error reading APT file");
            return;
        } finally {
//            System.out.println("Finish load function");
        }
    }

    /**
     * Loads account data from a file by reading and deserializing each line.
     *
     * <p>This method iterates over each line in the file and processes it based on a specific identifier:</p>
     * <ul>
     *   <li><b>"PA":</b> Deserializes and adds a {@code Patient} object to the list of patients.</li>
     *   <li><b>"DR":</b> Deserializes and adds a {@code Doctor} object to the list of doctors.</li>
     *   <li><b>"PH":</b> Deserializes and adds a {@code Pharmacist} object to the list of pharmacists.</li>
     *   <li><b>"AD":</b> Deserializes and adds an {@code Administrator} object to the list of administrators.</li>
     *   <li><b>"Static":</b> Sets the last ID values for {@code Patient}, {@code Doctor}, {@code Pharmacist}, and {@code Administrator} classes.</li>
     * </ul>
     *
     * @param fileReader The {@code Scanner} object used to read account data from the file.
     */
    private void loadAccount(Scanner fileReader) {
        while (fileReader.hasNextLine()){
            String text = fileReader.next();
            text = DataEncryption.decipher(text);;
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
                    Doctor.setLastID(Integer.parseInt(textArray[index++]));
                    Pharmacist.setLastID(Integer.parseInt(textArray[index++]));
                    Administrator.setLastID(Integer.parseInt(textArray[index++]));
                    return;
            }
        }
    }

    /**
     * Loads appointment data from a file by reading, decrypting, and deserializing each line.
     *
     * <p>This method performs the following actions:</p>
     * <ul>
     *   <li>Reads each line from the file, decrypts the data, and deserializes it into an {@code Appointment} object.</li>
     *   <li>Retrieves the associated {@code Patient} and {@code Doctor} for each appointment using {@code getPerson}.</li>
     *   <li>Based on the appointment's status, adds the appointment to the appropriate lists:</li>
     *   <ul>
     *     <li><b>PENDING:</b> Adds to the pending lists for both the patient and doctor, and to the global pending list {@code allAppointments[0]}.</li>
     *     <li><b>ONGOING:</b> Adds to the ongoing lists for both the patient and doctor, and to the global ongoing list {@code allAppointments[1]}.</li>
     *     <li><b>COMPLETED:</b> Adds to the completed lists for both the patient and doctor, and to the global completed list {@code allAppointments[2]}.</li>
     *   </ul>
     * </ul>
     *
     * @param fileWriter The {@code Scanner} object used to read appointment data from the file.
     */
    private void loadAppointment(Scanner fileWriter) {
        while (fileWriter.hasNextLine()) {
            String decrypted = DataEncryption.decipher(fileWriter.next());
            if (decrypted.substring(0,7).equals("Static&")) {
                int lastID = Integer.valueOf(decrypted.substring(7));
                Appointment.setLastID(lastID);
                return;
            }
            Appointment apt = DataSerialisation.DeserialiseAppointment(decrypted);
            Patient patient = (Patient) getPerson(apt.getPatientID(),ROLE.PATIENT);
            Doctor doctor = (Doctor) getPerson(apt.getDoctorID(),ROLE.DOCTOR);
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
                    for (Prescription p : apt.getPrescriptionList()) {
                        if (p.isPrescribed())patient.getMedicine().addPrescription(p,0);
                    }
                    doctor.getCompletedApt().addAppointment(apt);
                    allAppointments[2].addAppointment(apt);
                    break;
                default:
                    break;
            }
        }
    }

    /**
     * Saves the current state of the system by writing account and appointment data to files.
     *
     * <p>This method performs the following actions:</p>
     * <ul>
     *   <li>Creates or overwrites "HMS.txt" to save account information using the {@code saveAccount} method.</li>
     *   <li>Creates or overwrites "APT.txt" to save appointment information using the {@code saveAppointment} method.</li>
     *   <li>Catches and handles any exceptions during file operations, printing error messages to notify if a file could not be written.</li>
     * </ul>
     *
     * <p>Each file operation is closed after writing to ensure data integrity and avoid resource leaks.</p>
     */
    private void saveFile() {
        File savefile = new File(HMSFileName);
        FileWriter file;
        try {
            file = new FileWriter(savefile);
            saveAccount(file);
            file.close();
        } catch (Exception e) {
            System.out.println("Error writing into HMS file");
        }

        savefile = new File(APTFileName);

        try {
            file = new FileWriter(savefile);
            saveAppointment(file);
            file.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error writing into APT file");
        }

    }


    /**
     * Saves the current state of the system by writing account to files.
     *
     * <p>This method performs the following actions:</p>
     * <ul>
     *   <li>Loops through each ROLE of Patients, Doctors, Pharmacist and Administrators.</li>
     *   <li>For each ROLE, convert the user information into an encrypted String and write it to file</li>
     * </ul>
     */
    private void saveAccount(FileWriter fileWriter) throws IOException {

        for (Patient P: patients) fileWriter.write(DataEncryption.cipher(DataSerialisation.SerialisePatient(P)) + "\n");
        for (Doctor D: doctors) fileWriter.write(DataEncryption.cipher(DataSerialisation.SerialiseDoctor(D)) + "\n");
        for (Pharmacist P : pharmacists) fileWriter.write(DataEncryption.cipher(DataSerialisation.SerialisePharmacist(P)) + "\n");
        for (Administrator A: administrators) fileWriter.write(DataEncryption.cipher(DataSerialisation.SerialiseAdministrator(A)) + "\n");
        String[] textArray = new String[] {String.valueOf(Patient.getLastID()), String.valueOf(Doctor.getLastID()), String.valueOf(Pharmacist.getLastID()),
                String.valueOf(Administrator.getLastID())};
        String text = "Static&" + DataSerialisation.convertStringArraytoString(textArray, "/");
        fileWriter.write(DataEncryption.cipher(text));
    }

    /**
     * Saves the current state of the system by Appointments to files.
     *
     * <p>This method performs the following actions:</p>
     * <ul>
     *   <li>Loops through all appointments.</li>
     *   <li>For each appointment, convert the appointment info into an encrypted String and write it to file</li>
     * </ul>
     */
    private void saveAppointment(FileWriter fileWriter) throws  IOException {

        for (AppointmentList list: allAppointments) for (Appointment apt: list) fileWriter.write(DataEncryption.cipher(DataSerialisation.SerialiseAppointment(apt)) + "\n");
        fileWriter.write(DataEncryption.cipher("Static&" + String.valueOf(Appointment.getLastID())));
    }
}
