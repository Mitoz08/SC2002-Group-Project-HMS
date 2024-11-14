package HumanObject.Patient;

import DataObject.Appointment.Appointment;
import DataObject.Appointment.AppointmentList;
import DataObject.Prescription.PrescriptionList;
import HumanObject.BasePerson;
import HumanObject.ROLE;
import DataObject.Appointment.AppointmentList;

import java.util.ArrayList;
import java.util.Date;

/**
 * Represents a patient in a hospital management system, storing personal details, medical
 * history, and appointment records.
 * <p>
 * This class handles the management of a patient's medical records, appointments,
 * and prescriptions. It includes methods for printing medical records, accessing
 * personal information, and managing appointments (pending, ongoing, and completed).
 * </p>
 * <p>
 * The patient class extends {@link BasePerson} and adds additional attributes
 * related to the patient's health and medical interactions.
 * </p>
 */
public class Patient extends BasePerson {

    private static int lastID=0;

    private String bloodType;
    private Contact contact;
    private ArrayList<String> doctorAssigned;
    private AppointmentList Pending;
    private AppointmentList Ongoing;
    private AppointmentList Completed;
    private PrescriptionList Medicine;

    //This constructor is used to initialise from TXT file
    /**
     * Constructs a new {@code Patient} with the specified ID, name, date of birth,
     * gender, blood type, and contact information.
     * <p>
     * This constructor is used when
     * loading patient data from an external text file source.
     * </p>
     *
     *
     * @param ID         The unique identifier of the patient.
     * @param Name       The name of the patient.
     * @param DOB        The date of birth of the patient.
     * @param Gender     The gender of the patient, where {@code true} represents male
     *                   and {@code false} represents female.
     * @param bloodType  The blood type of the patient.
     * @param contact    The {@code Contact} information associated with the patient.
     *
     * <p>
     * This constructor initializes the following fields:
     * <ul>
     *   <li>{@code role} - Set to {@code ROLE.PATIENT} by default.</li>
     *   <li>{@code Pending} - Holds pending appointments, initialized to {@code true}.</li>
     *   <li>{@code Ongoing} - Holds ongoing appointments, initialized to {@code true}.</li>
     *   <li>{@code Completed} - Holds completed appointments, initialized to {@code false}.</li>
     * </ul>
     * </p>
     */
    public Patient(int ID, String Name, Date DOB, Boolean Gender, String bloodType, Contact contact){
        super(ID,Name, DOB, Gender);
        this.bloodType = bloodType;
        this.contact = contact;
        this.role = ROLE.PATIENT;
        this.Pending = new AppointmentList(true);
        this.Ongoing = new AppointmentList(true);
        this.Completed = new AppointmentList(false);
        this.Medicine = new PrescriptionList();
    }

    //This constructor is used to add to TXT file
    /**
     * Constructs a new {@code Patient} with the specified name, date of birth, gender,
     * blood type, and contact information. This constructor automatically assigns a
     * unique ID by incrementing {@code lastID}, ensuring each patient has a distinct identifier.
     * <p>
     * This version of the constructor is used when creating new patient records
     * for saving to a text file.
     * </p>
     *
     * @param Name       The name of the patient.
     * @param DOB        The date of birth of the patient.
     * @param Gender     The gender of the patient, where {@code true} represents male
     *                   and {@code false} represents female.
     * @param bloodType  The blood type of the patient.
     * @param contact    The {@code Contact} information associated with the patient.
     *
     * <p>
     * This constructor initializes three {@code AppointmentList} objects:
     * <ul>
     *   <li>{@code Pending} - Manages pending appointments, initialized to {@code true}.</li>
     *   <li>{@code Ongoing} - Manages ongoing appointments, initialized to {@code true}.</li>
     *   <li>{@code Completed} - Manages completed appointments, initialized to {@code false}.</li>
     * </ul>
     * </p>
     */
    public Patient(String Name, Date DOB, Boolean Gender, String bloodType, Contact contact) {
        super(lastID++, Name, DOB, Gender);
        this.bloodType = bloodType;
        this.contact = contact;
        this.role = ROLE.PATIENT;
        this.Pending = new AppointmentList(true);
        this.Ongoing = new AppointmentList(true);
        this.Completed = new AppointmentList(false);
        this.Medicine = new PrescriptionList();
    }

    /**
     * Prints the medical record of a patient, displaying relevant patient details.
     * This includes the patient's ID, name, gender, date of birth, contact number,
     * email, blood type, and completion status.
     * <p>
     * The patient's gender is displayed as "Male" if {@code gender} is {@code true},
     * otherwise it is displayed as "Female". The completion status is printed by
     * calling the {@code print} method on the {@code Completed} object with a
     * {@code true} parameter, which indicates detailed printing of the completion status.
     * </p>
     * <p>
     * This method does not return any values and outputs all information to the
     * standard output stream.
     * </p>
     *
     * @see #getID() for retrieving the patient's ID
     * @see #getName() for retrieving the patient's name
     * @see #getDOB() for retrieving the patient's date of birth
     * @see #getBloodType() for retrieving the patient's blood type
     * @see Contact#getContactNumber() for retrieving the patient's contact number
     * @see Contact#getEmail() for retrieving the patient's email address
     * @see AppointmentList#print(boolean) for printing the completion status in the AppointmentList class
     */
    public void printMedicalRecord(){
        System.out.println("Patient ID: " +  this.getID());
        System.out.println("Patient Name: " + this.getName());
        String gender = this.gender? "Male" : "Female";
        System.out.println("Patient Gender: " + gender);
        System.out.println("Date of Birth: " + this.getDOB());
        System.out.println("Patient Contact Number: " + this.getContact().getContactNumber());
        System.out.println("Patient Email: " + this.getContact().getEmail());
        System.out.println("Patient Blood Type: " + this.getBloodType());
        if (this.Completed.getCount() == 0){
            System.out.println("No past diagnosis!");
        }
        else {
            for (Appointment apt : this.getCompleted()) {
                System.out.println(apt.getAppointmentID() + ": " + apt.getNotes());
            }
        }
    }

    // Getter and Setter methods


    /**
     * Gets the blood type of the patient.
     *
     * @return the blood type of the patient
     */
    public String getBloodType(){
        return this.bloodType;
    }

    /**
     * Gets the contact information of the patient.
     *
     * @return The {@code Contact} object containing the patient's contact details.
     */
    public Contact getContact() {return this.contact;}

    /**
     * Gets the list of doctors assigned to the patient.
     *
     * @return A list of doctor names assigned to the patient.
     */
    public ArrayList<String> getDoctorAssigned() {return this.doctorAssigned;}

    /**
     * Gets the list of ongoing appointments for the patient.
     *
     * @return The {@code AppointmentList} object containing ongoing appointments.
     */
    public AppointmentList getOngoing() {return this.Ongoing;}

    /**
     * Gets the list of completed appointments for the patient.
     *
     * @return The {@code AppointmentList} object containing completed appointments.
     */
    public AppointmentList getCompleted() {return this.Completed;}

    /**
     * Gets the list of pending appointments for the patient.
     *
     * @return The {@code AppointmentList} object containing pending appointments.
     */
    public AppointmentList getPending() {return this.Pending;}

    /**
     * Gets the list of medications or prescriptions for the patient.
     *
     * @return The {@code PrescriptionList} object containing the patient's prescriptions.
     */
    public PrescriptionList getMedicine() {return this.Medicine;}

    /**
     * Sets the blood type of the patient.
     *
     * @param bloodType The blood type to set for the patient.
     */
    public void setBloodType(String bloodType) {this.bloodType = bloodType;}

    /**
     * Sets the contact information for the patient.
     *
     * @param contact The {@code Contact} object containing the new contact details.
     */
    public void setContact(Contact contact) {this.contact = contact;}

    /**
     * Sets the list of doctors assigned to the patient.
     *
     * @param doctorAssigned The list of doctor names to assign to the patient.
     */
    public void setDoctorAssigned(ArrayList<String> doctorAssigned) {this.doctorAssigned = doctorAssigned;}

    /**
     * Sets the list of ongoing appointments for the patient.
     *
     * @param ongoing The {@code AppointmentList} object containing ongoing appointments.
     */
    public void setOngoing(AppointmentList ongoing) {this.Ongoing = ongoing;}

    /**
     * Sets the list of pending appointments for the patient.
     *
     * @param pending The {@code AppointmentList} object containing pending appointments.
     */
    public void setPending(AppointmentList pending) {this.Pending = pending;}

    /**
     * Sets the list of completed appointments for the patient.
     *
     * @param completed The {@code AppointmentList} object containing completed appointments.
     */
    public void setCompleted(AppointmentList completed) {this.Completed = completed;}

    /**
     * Sets the list of medications or prescriptions for the patient.
     *
     * @param medicine The {@code PrescriptionList} object containing the patient's prescriptions.
     */
    public void setMedicine(PrescriptionList medicine) {this.Medicine = medicine;}

    /**
     * Sets the last used ID for the patients.
     *
     * @param ID The ID value to set as the last used patient ID.
     */
    public static void setLastID(int ID) {lastID = ID;}

    /**
     * Gets the last used ID for the patients.
     *
     * @return The last used patient ID.
     */
    public static int getLastID() {return lastID;}
}

