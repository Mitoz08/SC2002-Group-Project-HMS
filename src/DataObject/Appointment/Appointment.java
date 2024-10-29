package DataObject.Appointment;

import DataObject.Prescription.*;
import Serialisation.DataSerialisation;

import java.util.Date;
import java.util.Scanner;

/**
 * A class that stores the information of an appointment
 */
public class Appointment implements Comparable<Appointment> {

    // Private attributes
    private APT_STATUS status;
    private String nameOfApt;
    private int patientID;
    private String patientName;
    private int doctorID;
    private String doctorname;
    private Date appointmentTime;
    private String notes;
    private PrescriptionList prescriptionList;
    private String appointmentID;

    private static int lastID = 0;
    private static final int IDLength = 10;
    private static final String IDPrefix = "APT";
    // Constructor

    /**
     * Creates appointment object and prompts input
     */
    public Appointment() {
        Scanner sc = new Scanner(System.in);

        this.status = APT_STATUS.PENDING;
        System.out.println("Enter name of appointment: ");
        this.nameOfApt = sc.nextLine();
        System.out.print("Enter patient's hosptial ID: ");
        this.patientID = sc.nextInt();
        sc.nextLine();
        this.patientName = "patientName"; //To be added, maybe a database function that returns name when given the ID
        System.out.print("Enter doctors's hosptial ID: ");
        this.doctorID = sc.nextInt();
        sc.nextLine();
        this.doctorname = "doctorName"; //To be added, maybe a database function that returns name when given the ID
        System.out.print("Enter YYYY-MM-DD-HH-MM: ");
        this.appointmentTime = DataSerialisation.DeserialiseDate(sc.nextLine());
        this.notes = "Empty";
        this.prescriptionList = new PrescriptionList();
        this.appointmentID = AppointmentIDGenerator();
    }

    /**
     * Creates appointment object with given input
     *
     * @param status           PENDING, APPORVED, REJECTED, CANCELLED, COMPLETED
     * @param nameOfApt        Name of the appointment
     * @param patientID
     * @param doctorID
     * @param date             date object  (e.g. Date(YYYY,MM,DD,HH,MM)
     * @param notes
     * @param prescriptionList List of all the prescription
     */
    public Appointment(APT_STATUS status, String nameOfApt, int patientID, int doctorID, Date date, String notes, PrescriptionList prescriptionList) {
        this.status = status;
        this.nameOfApt = nameOfApt;
        this.patientID = patientID;
        this.patientName = "patientName"; //To be added, maybe a database function that returns name when given the ID
        this.doctorID = doctorID;
        this.doctorname = "doctorName"; //To be added, maybe a database function that returns name when given the ID
        this.appointmentTime = date;
        this.notes = notes;
        this.prescriptionList = prescriptionList;
        this.appointmentID = AppointmentIDGenerator();
    }

    /**
     * Creates appointment object with given input
     *
     * @param status           PENDING, APPORVED, REJECTED, CANCELLED, COMPLETED
     * @param nameOfApt        Name of the appointment
     * @param patientID
     * @param doctorID
     * @param date             date object  (e.g. Date(YYYY,MM,DD,HH,MM)
     * @param notes
     * @param prescriptionList List of all the prescription
     * @param appointmentID Appointment ID loaded from file
     */
    public Appointment(APT_STATUS status, String nameOfApt, int patientID, int doctorID, Date date, String notes, PrescriptionList prescriptionList, String appointmentID) {
        this.status = status;
        this.nameOfApt = nameOfApt;
        this.patientID = patientID;
        this.patientName = "patientName"; //To be added, maybe a database function that returns name when given the ID
        this.doctorID = doctorID;
        this.doctorname = "doctorName"; //To be added, maybe a database function that returns name when given the ID
        this.appointmentTime = date;
        this.notes = notes;
        this.prescriptionList = prescriptionList;
        this.appointmentID = appointmentID;
    }

    // Public methods
    public APT_STATUS getStatus() {return this.status;}
    public String getNameOfApt() {return this.nameOfApt;}
    public int getPatientID() {return this.patientID;}
    public String getPatientName() {return this.patientName;}
    public int getDoctorID() {return this.doctorID;}
    public String getDoctorname() {return this.doctorname;}
    public Date getAppointmentTime() {return this.appointmentTime;}
    public String getNotes() {return this.notes; }
    public PrescriptionList getPrescriptionList() {return this.prescriptionList; }
    public String getAppointmentID() {return this.appointmentID;}

    /**
     * Used at the start to reload the last unused ID
     * @param newlastID
     */
    public static void setLastID(int newlastID) {lastID = newlastID;}

    /**
     * Used at the end to save the last unused ID
     * @return
     */
    public static int getLastID() {return lastID;}

    /**
     * Prints out a formatted appointment block
     * With either PatientID/DoctorID base on the boolean input
     *
     * @param Patient
     */
    public void print(boolean Patient) {
        System.out.println("______________________________");
        System.out.printf("|%-8s:%-20s|\n", "APT ID", this.appointmentID);
        System.out.printf("|%-8s:%-20s|\n", "Status", this.status);
        System.out.printf("|%-8s:%-20s|\n", "Event", this.nameOfApt);
        if (Patient)
            System.out.printf("|%-8s:%03d%-17s|\n", "Doctor", this.doctorID, ""); // Need to change to name of doctor once classes are made
        else
            System.out.printf("|%-8s:%04d%-16s|\n", "Patient", this.patientID, ""); // Need to change to name of doctor once classes are made
        System.out.printf("|%-8s:%02d-%02d-%04d%-10s|\n", "Date", this.appointmentTime.getDate(), this.appointmentTime.getMonth(), this.appointmentTime.getYear(), "");
        System.out.printf("|%-8s:%02d:%02d%-15s|\n", "Time", this.appointmentTime.getHours(), this.appointmentTime.getMinutes(), "");
        if (!this.notes.equals("Empty")) System.out.printf("|%-8s:%-20s|\n", "Notes", this.notes);
        System.out.println("______________________________");
    }

    /**
     * Prints out a formatted appointment block of all the data
     */
    public void print() {
        System.out.println("______________________________");
        System.out.printf("|%-8s:%-20s|\n", "APT ID", this.appointmentID);
        System.out.printf("|%-8s:%-20s|\n", "Status", this.status);
        System.out.printf("|%-8s:%-20s|\n", "Event", this.nameOfApt);
        System.out.printf("|%-8s:%03d%-17s|\n", "Doctor", this.doctorID, ""); // Need to change to name of doctor once classes are made
        System.out.printf("|%-8s:%04d%-16s|\n", "Patient", this.patientID, ""); // Need to change to name of doctor once classes are made
        System.out.printf("|%-8s:%02d-%02d-%04d%-10s|\n", "Date", this.appointmentTime.getDate(), this.appointmentTime.getMonth(), this.appointmentTime.getYear(), "");
        System.out.printf("|%-8s:%02d:%02d%-15s|\n", "Time", this.appointmentTime.getHours(), this.appointmentTime.getMinutes(), "");
        if (!this.notes.equals("Empty")) System.out.printf("|%-8s:%-20s|\n", "Notes", this.notes);
        System.out.println("______________________________");
    }

    /**
     * Prints its prescriptions
     */
    public void printPrescription() {this.prescriptionList.print();}

    /**
     * Compares the current appointment with the argument appointment
     * It returns the value 0 if the argument Date is equal to this Date.
     * It returns a value less than 0 if this Date is before the Date argument.
     * It returns a value greater than 0 if this Date is after the Date argument.
     *
     * @param o the object to be compared.
     * @return
     */
    @Override
    public int compareTo(Appointment o) {
        return this.appointmentTime.compareTo(o.appointmentTime);
    }

    // Private methods

    public static String AppointmentIDGenerator() {
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
