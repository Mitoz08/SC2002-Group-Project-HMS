package DataObject.PharmacyObjects;

public class MedicineRequest {

    // Private
    private int patientID;
    private int doctorID;
    private String appointmentID;
    private boolean approved;
    private int pharmacistID;

    // Public

    public MedicineRequest(int patientID, String appointmentID) {
        this.patientID = patientID;
        this.appointmentID = appointmentID;
    }

    public void ApproveRequest(int pharmacistID) {
        this.approved = true;
        this.pharmacistID = pharmacistID;
    }

    public void print() {
        System.out.println("______________________________");
        System.out.printf("|%-10s:%-18s|\n", "APT ID", this.appointmentID);
        System.out.printf("|%-10s:%-18d|\n", "PatientID", this.patientID);
        System.out.printf("|%-10s:%-18d|\n", "DoctorID", this.doctorID);
        System.out.println("______________________________");
    }
}
