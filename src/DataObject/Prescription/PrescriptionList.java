package DataObject.Prescription;

class PrescriptionNode {
    Prescription prescription;
    PrescriptionNode nextNode;
}

/**
 * A linked list of prescription that stores in ascending order
 */
public class PrescriptionList {

    // Private attributes
    private PrescriptionNode headRef;
    private PrescriptionNode tailRef;
    private int count;

    // Constructor
    // Default constructor is fine

    // Public methods
    public int getCount() {return count;}

    /**
     * Prints out all the prescription in its list
     */
    public void print(){
        PrescriptionNode curRef = this.headRef;
        while (curRef != null) {
            curRef.prescription.print();
            curRef = curRef.nextNode;
        }
    }

    /**
     * Adds prescription into the list in ascending order
     * @param prescription
     */
    public void addPrescription(Prescription prescription) {
        PrescriptionNode insert = new PrescriptionNode();
        insert.prescription = prescription;

        if (this.headRef == null) {
            this.headRef = insert;
            this.tailRef = insert;
        } else {
            PrescriptionNode curRef = headRef;
            if (prescription.compareTo(curRef.prescription) < 0) { // If it should be in spot 0
                insert.nextNode = curRef;
                this.headRef = insert;
                return;
            }
            while (curRef.nextNode != null && prescription.compareTo(curRef.nextNode.prescription) > 0) curRef = curRef.nextNode; // Traverse to the n-1 node to insert the n node
            if (curRef.nextNode == null) this.tailRef = insert;
            insert.nextNode = curRef.nextNode;
            curRef.nextNode = insert;
        }
        this.count++;
    }

    /**
     * Removes the prescription by index starting from 0
     * @param index
     * @return
     */
    public int removePrescription(int index){ // Remove by index return 0 if successful -1 if unsuccessful
        if (count == 0 || index < 0 || index >= count) return -1;
        if (index == 0) {
            this.headRef = this.headRef.nextNode;
            this.count--;
            return 0;
        }
        PrescriptionNode curRef = this.headRef;
        for (int i = 1; i < index; i++) {
            curRef = curRef.nextNode;
        }
        curRef.nextNode = curRef.nextNode.nextNode;
        this.count--;
        return 0;
    }

    /**
     * Find and returns prescription based on the medicine name
     * @param medicineName
     * @return
     */
    public Prescription getPrescription(String medicineName) { // Takes medicine name and returns the prescription object (Assuming no duplicate)
        PrescriptionNode curRef = this.headRef;
        while (curRef != null) {
            if (curRef.prescription.getMedicineName().compareTo(medicineName) == 0) return curRef.prescription;
            curRef = curRef.nextNode;
        }
        return null; // Return null if target medicine not prescribed
    }

    /**
     * Find and returns prescription based on the index starting from 0
     * @param index
     * @return
     */
    public Prescription getPrescription(int index) { // Takes index and returns the prescription object (Assuming no duplicate)
        if (index >= count) return null;
        PrescriptionNode curRef = this.headRef;
        while (index > 0) {
            curRef = curRef.nextNode;
            index--;
        }
        return curRef.prescription;
    }

    /**
     * Convert list to string by concatenating each prescription separated by the delimiter
     * @param delimiter
     * @return
     */
    public String DataSave(String delimiter){
        String output = "";
        PrescriptionNode curRef = headRef;
        while (curRef != null) {
            output += curRef.prescription.getDataSave();
            curRef = curRef.nextNode;
            if (curRef == null) break;
            output += delimiter;
        }
        return output;
    }

}
