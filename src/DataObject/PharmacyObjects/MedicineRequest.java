package DataObject.PharmacyObjects;

public class MedicineRequest {

    // Private
    private int patientID;
    private int doctorID;
    private String appointmentID;
    private boolean approved;
    private int pharmacistID;

    // Public

    public MedicineRequest(int patientID, int doctorID, String appointmentID) {
        this.patientID = patientID;
        this.doctorID = doctorID;
        this.appointmentID = appointmentID;
        this.approved = false;
        this.pharmacistID = -1;
    }

    public MedicineRequest (int patientID, int doctorID, String appointmentID, boolean approved, int pharmacistID) {
        this.patientID = patientID;
        this.doctorID = doctorID;
        this.appointmentID = appointmentID;
        this.approved = approved;
        this.pharmacistID = pharmacistID;
    }

    public void ApproveRequest(int pharmacistID) {
        this.approved = true;
        this.pharmacistID = pharmacistID;
    }

    public int getPatientID() {return this.patientID;}
    public int getDoctorID() {return this.doctorID;}
    public String getAppointmentID() {return this.appointmentID;}
    public boolean isApproved() {return this.approved;}
    public int getPharmacistID() {return this.pharmacistID;}

    public void print() {
        System.out.printf("______________________________\n");
        System.out.printf("|%-10s:%-18s|\n", "APT ID", this.appointmentID);
        System.out.printf("|%-10s:%-18d|\n", "PatientID", this.patientID);
        System.out.printf("|%-10s:%-18d|\n", "DoctorID", this.doctorID);
        System.out.println("______________________________");
    }

    public void print(int index) {
        System.out.printf("%2d)______________________________\n", index);
        System.out.printf("   |%-10s:%-18s|\n", "APT ID", this.appointmentID);
        System.out.printf("   |%-10s:%-18d|\n", "PatientID", this.patientID);
        System.out.printf("   |%-10s:%-18d|\n", "DoctorID", this.doctorID);
        System.out.println("   ______________________________");
    }
}
