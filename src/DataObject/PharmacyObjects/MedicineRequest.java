package DataObject.PharmacyObjects;

/**
 * The MedicineRequest class represents a request for medicine in a pharmacy system.
 * Each request includes details about the patient, doctor, appointment, and approval status.
 */
public class MedicineRequest {

    // Private
    /** The ID of patient receiving the medicine. */
    private int patientID;

    /** The ID of doctor prescribing the medicine. */
    private int doctorID;

    /** The ID of the Appointment to refer to. */
    private String appointmentID;

    /** The boolean variable storing the status of the request. */
    private boolean approved;

    /** The ID of pharmacist fulfilling the request. */
    private int pharmacistID;


    // Constructors
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

    // Getters

    /**
     * Gets the patient's ID of the request
     * @return the patient's ID of the request
     */
    public int getPatientID() {return this.patientID;}

    /**
     * Gets the doctor's ID of the request
     * @return the doctor's ID of the request
     */
    public int getDoctorID() {return this.doctorID;}

    /**
     * Gets the {@code Appointment} ID of the request
     * @return the {@code Appointment} ID of the request
     */
    public String getAppointmentID() {return this.appointmentID;}

    /**
     * Gets the {@code boolean} variable {@code approved} of the request
     * @return the {@code boolean} variable {@code approved} of the request
     */
    public boolean isApproved() {return this.approved;}

    /**
     * Gets the pharmacist's ID of the request (Pharmacist who approved the request)
     * @return the pharmacist's ID of the request (Pharmacist who approved the request)
     */
    public int getPharmacistID() {return this.pharmacistID;}

    /**
     * Approves the request and assigns a pharmacist ID.
     *
     * @param pharmacistID the ID of the pharmacist who approved the request
     */
    public void ApproveRequest(int pharmacistID) {
        this.approved = true;
        this.pharmacistID = pharmacistID;
    }

    /**
     * Prints a formatted block of the medicine request.
     * <p>Prints:</p>
     * <l>
     *     <li> Appointment ID</li>
     *     <li> Patient ID</li>
     *     <li> Doctor ID</li>
     * </l>
     */
    public void print() {
        System.out.printf("┌─────────────────────────────┐\n");
        System.out.printf("│%-10s:%-18s│\n", "APT ID", this.appointmentID);
        System.out.printf("│%-10s:%-18s│\n", "PatientID", "PA"+ this.patientID);
        System.out.printf("│%-10s:%-18s│\n", "DoctorID", "DR" + this.doctorID);
        System.out.println("└─────────────────────────────┘");
    }

    /**
     * Prints a formatted block of the medicine request with an index.
     * <p>Prints:</p>
     * <l>
     *     <li> Appointment ID</li>
     *     <li> Patient ID</li>
     *     <li> Doctor ID</li>
     * </l>
     *
     * @param index the index to display before the block
     */
    public void print(int index) {
        System.out.printf("%2d)┌─────────────────────────────┐\n", index);
        System.out.printf("   │%-10s:%-18s│\n", "APT ID", this.appointmentID);
        System.out.printf("   │%-10s:%-18s│\n", "PatientID","PA"+  this.patientID);
        System.out.printf("   │%-10s:%-18s│\n", "DoctorID","DR"+  this.doctorID);
        System.out.println("   └─────────────────────────────┘");
    }
}
