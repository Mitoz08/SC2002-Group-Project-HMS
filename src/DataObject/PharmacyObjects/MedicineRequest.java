package DataObject.PharmacyObjects;

/**
 * The MedicineRequest class represents a request for medicine in a pharmacy system.
 * Each request includes details about the patient, doctor, appointment, and approval status.
 */
public class MedicineRequest {

    // Private
    private int patientID;
    private int doctorID;
    private String appointmentID;
    private boolean approved;
    private int pharmacistID;

    // Public

     /**
     * Constructs a new MedicineRequest with the specified patient, doctor, and appointment details.
     * The request is initialized as not approved, and no pharmacist is assigned.
     *
     * @param patientID    the ID of the patient making the request
     * @param doctorID     the ID of the doctor associated with the request
     * @param appointmentID the appointment ID associated with the request
     */
    public MedicineRequest(int patientID, int doctorID, String appointmentID) {
        this.patientID = patientID;
        this.doctorID = doctorID;
        this.appointmentID = appointmentID;
        this.approved = false;
        this.pharmacistID = -1;
    }

    /**
     * Constructs a new MedicineRequest with specified patient, doctor, appointment, approval status, and pharmacist details.
     * Used when loading from file.
     *
     * @param patientID    the ID of the patient making the request
     * @param doctorID     the ID of the doctor associated with the request
     * @param appointmentID the appointment ID associated with the request
     * @param approved     the approval status of the request
     * @param pharmacistID the ID of the pharmacist who approved the request
     */
    public MedicineRequest (int patientID, int doctorID, String appointmentID, boolean approved, int pharmacistID) {
        this.patientID = patientID;
        this.doctorID = doctorID;
        this.appointmentID = appointmentID;
        this.approved = approved;
        this.pharmacistID = pharmacistID;
    }

    /**
     * Approves the request and assigns a pharmacist ID.
     *
     * @param pharmacistID the ID of the pharmacist who approved the request
     */
    public void ApproveRequest(int pharmacistID) {
        this.approved = true;
        this.pharmacistID = pharmacistID;
    }

    public int getPatientID() {return this.patientID;}
    public int getDoctorID() {return this.doctorID;}
    public String getAppointmentID() {return this.appointmentID;}
    public boolean isApproved() {return this.approved;}
    public int getPharmacistID() {return this.pharmacistID;}

    /**
     * Prints a formatted block of the medicine request.
     */
    public void print() {
        System.out.printf("______________________________\n");
        System.out.printf("|%-10s:%-18s|\n", "APT ID", this.appointmentID);
        System.out.printf("|%-10s:%-18d|\n", "PatientID", this.patientID);
        System.out.printf("|%-10s:%-18d|\n", "DoctorID", this.doctorID);
        System.out.println("______________________________");
    }

    /**
     * Prints a formatted summary of the medicine request with an index.
     *
     * @param index the index to display before the summary
     */
    public void print(int index) {
        System.out.printf("%2d)______________________________\n", index);
        System.out.printf("   |%-10s:%-18s|\n", "APT ID", this.appointmentID);
        System.out.printf("   |%-10s:%-18d|\n", "PatientID", this.patientID);
        System.out.printf("   |%-10s:%-18d|\n", "DoctorID", this.doctorID);
        System.out.println("   ______________________________");
    }
}
