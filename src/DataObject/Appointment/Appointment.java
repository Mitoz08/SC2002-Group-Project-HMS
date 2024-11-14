package DataObject.Appointment;

import DataObject.Prescription.*;


import java.util.Date;

/**
 * A class that stores the information of an appointment
 * <l>
 *     <li> When created, it stores the timing and people involved in the appointment</li>
 *     <li> When completed, it additionally stores the prescription and outcome of the appointment</li>
 * </l>
 */
public class Appointment implements Comparable<Appointment> {

    // Private attributes
    private APT_STATUS status;
    private String nameOfApt;
    private int patientID;
    private String patientName;
    private int doctorID;
    private String doctorName;
    private Date appointmentTime;
    private String notes;
    private PrescriptionList prescriptionList;
    private String appointmentID;

    private static int lastID = 0;
    public static final int IDLength = 10;
    public static final String IDPrefix = "APT";

    // Constructor
    /**
     * Constructs an {@code Appointment} object with given input (Used for creating a new appointment)
     * @param nameOfApt     The type of Service
     * @param patientID     The ID of the patient
     * @param patientName   The name of the patient
     * @param doctorID      The ID of the doctor
     * @param doctorName    The name of the doctor
     * @param date          The date and time of the appointment
     *
     * @see PrescriptionList PrescriptionList Class
     * @see #AppointmentIDGenerator() Method to obtain unique ID
     */
    public Appointment(String nameOfApt, int patientID, String patientName, int doctorID, String doctorName, Date date) {
        this.status = APT_STATUS.PENDING;
        this.nameOfApt = nameOfApt;
        this.patientID = patientID;
        this.patientName = patientName;
        this.doctorID = doctorID;
        this.doctorName = doctorName;
        this.appointmentTime = date;
        this.notes = "Empty";
        this.prescriptionList = new PrescriptionList();
        this.appointmentID = AppointmentIDGenerator();
    }

    /**
     * Constructs an {@code Appointment} object with given input (Used for loading in from file)
     * @param status            Status of the appointment enum {@code APT_STATUS} (PENDING,ONGOING,COMPLETED)
     * @param nameOfApt         The type of Service
     * @param patientID         The ID of the patient
     * @param patientName       The name of the patient
     * @param doctorID          The ID of the doctor
     * @param doctorName        The name of the doctor
     * @param date              The date and time of the appointment
     * @param notes             The notes or diagnosis of the appointment
     * @param prescriptionList  The list of all the {@code Prescription} given
     * @param appointmentID     The unique ID of the appointment
     *
     * @see PrescriptionList PrescriptionList Class
     */
    public Appointment(APT_STATUS status, String nameOfApt, int patientID, String patientName, int doctorID, String doctorName, Date date, String notes, PrescriptionList prescriptionList, String appointmentID) {
        this.status = status;
        this.nameOfApt = nameOfApt;
        this.patientID = patientID;
        this.patientName = patientName; //To be added, maybe a database function that returns name when given the ID
        this.doctorID = doctorID;
        this.doctorName = doctorName; //To be added, maybe a database function that returns name when given the ID
        this.appointmentTime = date;
        this.notes = notes;
        this.prescriptionList = prescriptionList;
        this.appointmentID = appointmentID;
    }

    // Public methods
    // Getters

    /**
     * Gets the Status of the {@code Appointment}
     * @return the Status of the {@code Appointment}
     */
    public APT_STATUS getStatus() {return this.status;}

    /**
     * Gets the Name/Service of the {@code Appointment}
     * @return the Name/Service of the {@code Appointment}
     */
    public String getNameOfApt() {return this.nameOfApt;}

    /**
     * Gets the patient's ID for the {@code Appointment}
     * @return the patient's ID for the {@code Appointment}
     */
    public int getPatientID() {return this.patientID;}

    /**
     * Gets the patient's name for the {@code Appointment}
     * @return the patient's name for the {@code Appointment}
     */
    public String getPatientName() {return this.patientName;}

    /**
     * Gets the doctor's ID for the {@code Appointment}
     * @return the doctor's ID for the {@code Appointment}
     */
    public int getDoctorID() {return this.doctorID;}

    /**
     * Gets the doctor's name for the {@code Appointment}
     * @return the doctor's name for the {@code Appointment}
     */
    public String getDoctorName() {return this.doctorName;}

    /**
     * Gets the {@code Appointment} date and time
     * @return the {@code Appointment} date and time
     */
    public Date getAppointmentTime() {return this.appointmentTime;}

    /**
     * Gets the {@code Appointment} notes
     * @return the {@code Appointment} notes
     */
    public String getNotes() {return this.notes; }

    /**
     * Gets the {@code PrescriptionList} of the {@code Appointment}
     * @return the {@code PrescriptionList} of the {@code Appointment}
     */
    public PrescriptionList getPrescriptionList() {return this.prescriptionList; }

    /**
     * Gets the unique ID of the {@code Appointment}
     * @return the unique ID of the {@code Appointment}
     */
    public String getAppointmentID() {return this.appointmentID;}

    // Setters

    /**
     * Sets the Status of the {@code Appointment}
     * @param value the new Status
     */
    public void setStatus(int value) { this.status = APT_STATUS.values()[value];}

    /**
     * Sets the notes of the {@code Appointment}
     * @param notes the new notes for the {@code Appointment}
     */
    public void setNotes(String notes) { this.notes = notes; }


    /**
     * Used at the start to reload the last unused ID
     * @param newlastID Last unused ID from the previous run
     */
    public static void setLastID(int newlastID) {lastID = newlastID;}

    /**
     * Used at the end to save the last unused ID
     * @return  Last unused ID from current run
     */
    public static int getLastID() {return lastID;}

    /**
     * Prints out a formatted appointment block with either {@code patientName}/{@code doctorName} base on the boolean {@code Patient} input
     *
     * @param Patient {@code true} - prints out doctor's name, {@code false} - prints out patient's name
     */
    public void print(boolean Patient) {
        System.out.println("┌─────────────────────────────┐");
        System.out.printf("│%-8s:%-20s│\n", "APT ID", this.appointmentID);
        System.out.printf("│%-8s:%-20s│\n", "Status", this.status);
        System.out.printf("│%-8s:%-20s│\n", "Event", this.nameOfApt);
        if (Patient)
            System.out.printf("│%-8s:%-20s│\n", "Doctor", this.doctorName);
        else
            System.out.printf("│%-8s:%-20s│\n", "Patient", this.patientName);
        System.out.printf("│%-8s:%02d-%02d-%04d%-10s│\n", "Date", this.appointmentTime.getDate(), this.appointmentTime.getMonth()+1, this.appointmentTime.getYear()+1900, "");
        System.out.printf("│%-8s:%02d:%02d%-15s│\n", "Time", this.appointmentTime.getHours(), this.appointmentTime.getMinutes(), "");
        if (!this.notes.equals("Empty")) System.out.printf("│%-8s:%-20s│\n", "Notes", this.notes);
        System.out.println("└─────────────────────────────┘");
    }

    /**
     * Prints out a formatted appointment block with either {@code patientName}/{@code doctorName} base on the boolean input
     * <p>
     *     Indented to include the index
     * </p>
     *
     * @param Patient {@code true} - prints out doctor's name, {@code false} - prints out patient's name
     * @param index prints {@code index} beside the appointment block
     */
    public void print(boolean Patient, int index) {
        System.out.printf("%3d)┌─────────────────────────────┐\n", index);
        System.out.printf("    │%-8s:%-20s│\n", "APT ID", this.appointmentID);
        System.out.printf("    │%-8s:%-20s│\n", "Status", this.status);
        System.out.printf("    │%-8s:%-20s│\n", "Event", this.nameOfApt);
        if (Patient)
            System.out.printf("    │%-8s:%-20s│\n", "Doctor", this.doctorName);
        else
            System.out.printf("    │%-8s:%-20s│\n", "Patient", this.patientName);
        System.out.printf("    │%-8s:%02d-%02d-%04d%-10s│\n", "Date", this.appointmentTime.getDate(), this.appointmentTime.getMonth()+1, this.appointmentTime.getYear()+1900, "");
        System.out.printf("    │%-8s:%02d:%02d%-15s│\n", "Time", this.appointmentTime.getHours(), this.appointmentTime.getMinutes(), "");
        if (!this.notes.equals("Empty")) System.out.printf("    │%-8s:%-20s│\n", "Notes", this.notes);
        System.out.println("    └─────────────────────────────┘");
    }

    /**
     * Prints out a formatted appointment block of all the data
     */
    public void print() {
        System.out.println("┌─────────────────────────────┐");
        System.out.printf("│%-8s:%-20s│\n", "APT ID", this.appointmentID);
        System.out.printf("│%-8s:%-20s│\n", "Status", this.status);
        System.out.printf("│%-8s:%-20s│\n", "Event", this.nameOfApt);
        System.out.printf("│%-8s:%-20s│\n", "Doctor", this.doctorName);
        System.out.printf("│%-8s:%-20s│\n", "Patient", this.patientName);
        System.out.printf("│%-8s:%02d-%02d-%04d%-10s│\n", "Date", this.appointmentTime.getDate(), this.appointmentTime.getMonth()+1, this.appointmentTime.getYear()+1900, "");
        System.out.printf("│%-8s:%02d:%02d%-15s│\n", "Time", this.appointmentTime.getHours(), this.appointmentTime.getMinutes(), "");
        if (!this.notes.equals("Empty")) System.out.printf("│%-8s:%-20s│\n", "Notes", this.notes);
        System.out.println("└─────────────────────────────┘");
    }

    /**
     * Prints out a formatted appointment block of all the data
     * <p>
     *     Indented to include the index
     * </p>
     *
     * @param index prints {@code index} beside the appointment block
     */
    public void print(int index) {
        System.out.printf("%3d)┌─────────────────────────────┐\n",index);
        System.out.printf("    │%-8s:%-20s│\n", "APT ID", this.appointmentID);
        System.out.printf("    │%-8s:%-20s│\n", "Status", this.status);
        System.out.printf("    │%-8s:%-20s│\n", "Event", this.nameOfApt);
        System.out.printf("    │%-8s:%-20s│\n", "Doctor", this.doctorName);
        System.out.printf("    │%-8s:%-20s│\n", "Patient", this.patientName);
        System.out.printf("    │%-8s:%02d-%02d-%04d%-10s│\n", "Date", this.appointmentTime.getDate(), this.appointmentTime.getMonth()+1, this.appointmentTime.getYear()+1900, "");
        System.out.printf("    │%-8s:%02d:%02d%-15s│\n", "Time", this.appointmentTime.getHours(), this.appointmentTime.getMinutes(), "");
        if (!this.notes.equals("Empty")) System.out.printf("    │%-8s:%-20s│\n", "Notes", this.notes);
        System.out.println("    └─────────────────────────────┘");
    }

    /**
     * To return the specific time slot of the appointment
     * @return <l>
     *     <li>10AM - 11AM: returns 0</li>
     *     <li>11AM - 12PM: returns 1</li>
     *     <li> 1PM -  2PM: returns 2</li>
     *     <li> 2PM -  3PM: returns 3</li>
     *     <li> 3PM -  4PM: returns 4</li>
     * </l>
     */
    public int getTimeSlot() {
        return switch (appointmentTime.getHours()) {
            case 10 -> 0;
            case 11 -> 1;
            case 13 -> 2;
            case 14 -> 3;
            case 15 -> 4;
            default -> -1;
        };
    }

    /**
     * Used to get {@code Date} object of the {@code Appointment} at 00:00:00 for comparison
     *
     * @return the date of the appointment with time set to 00:00:00
     */
    public Date getDate() {
        Date date = new Date(appointmentTime.getYear(), appointmentTime.getMonth(), appointmentTime.getDate());
        return  date;
    }

    /**
     * Compares the current appointment's time with the argument appointment's time
     *
     * @param o the object to be compared.
     *
     * @return <l>
     *     <li> 0 - if {@code Date} are equal.</il>
     *     <li> less than 0 - if object's {@code Date} is before the argument's {@code Date}.</il>
     *     <li> more than 0 - if object's {@code Date} is after the argument's {@code Date}.</il>
     * </l>
     */
    @Override
    public int compareTo(Appointment o) {
        return this.appointmentTime.compareTo(o.appointmentTime);
    }

    public boolean isPrescribed() {
        for (Prescription o: prescriptionList) {
            if (!o.isPrescribed()) return false;
        }
        return true;
    }

    /**
     * Creates a new {@code Date} object with the given {@code date} and time with reference to the {@code TimeSlot}
     *
     * @param date date of the appointment
     * @param TimeSlot 0 - 10AM-11AM, 1 - 11AM-12PM, 2 - 1PM-2PM, 3 - 2PM-3PM, 4 - 3PM-4PM
     * @return the specific date and time
     */
    public static Date createDate(Date date, int TimeSlot) {
        int[] slot = new int[] {10,11,13,14,15};
        date.setHours(slot[TimeSlot]);
        return date;
    }

    // Private methods

    /**
     * Creates a unique ID for {@code Appointment} with a prefix and length
     * <l>
     *      <li> {@code IDPrefix} for the prefix of the ID</li>
     *      <li> {@code IDLength} for the maximum length of the ID</li>
     * </l>

     *
     * @return
     */
    private static String AppointmentIDGenerator() {
        StringBuilder str = new StringBuilder();
        String IDStr = String.valueOf(lastID);

        str.append(IDPrefix);
        for (int i = 0; i < IDLength-IDPrefix.length()-IDStr.length(); i++) {
            str.append('0');
        }
        str.append(IDStr);
        lastID++;
        return str.toString();
    }
}
