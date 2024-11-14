package DataObject.Prescription;

import DataObject.Appointment.Appointment;

import java.util.Iterator;

/**
 * A class that stores the {@code Prescription} in a LinkedList structure
 *
 * @see Prescription
 */
class PrescriptionNode {
    Prescription prescription;
    PrescriptionNode nextNode;
}

/**
 * A linked list of prescription that stores in ascending order
 */
public class PrescriptionList implements Iterable<Prescription> {

    // Private attributes
    private PrescriptionNode headRef;
    private PrescriptionNode tailRef;
    private int count;

    // Constructor
    // Default constructor is fine

    // Public methods
    /**
     * Gets the number of {@code Prescription} in the list
     * @return the number of {@code Prescription} in the list
     */
    public int getCount() {return count;}

    /**
     * Gets the reference to the first {@code PrescriptionNode} in the list
     * @return the reference to the first {@code PrescriptionNode} in the list
     */
    public PrescriptionNode getHeadRef(){ return headRef;}

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
     * Adds {@code Prescription} into the list in ascending order
     * @param prescription {@code Prescription} to be added
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
     * Adds {@code Prescription} into the list by index
     * <l>
     *     <li>when {@code index} > {@code count} - adds to that index </li>
     *     <li>when {@code index} < 0 or >= {@code count}  adds to the end</li>
     * </l>
     * @param prescription {@code Prescription} to be added
     */
    public void addPrescription(Prescription prescription, int index) {
        PrescriptionNode insert = new PrescriptionNode();
        insert.prescription = prescription;

        if (this.headRef == null) {
            this.headRef = insert;
            this.tailRef = insert;
        } else {
            if (index == 0) {
                insert.nextNode = this.headRef;
                this.headRef = insert;
            } else if (index >= this.count || index < 0) {
                this.tailRef.nextNode = insert;
                insert.nextNode = this.tailRef;
            } else {
                PrescriptionNode curRef = headRef;
                while (index-- > 1) curRef = curRef.nextNode; // Traverse to the n-1 node to insert the n node
                if (curRef.nextNode == null) this.tailRef = insert;
                insert.nextNode = curRef.nextNode;
                curRef.nextNode = insert;
            }
        }
        this.count++;
    }

    /**
     * Removes the {@code Prescription} by index starting from 0
     * @param index {@code index} to be removed
     * @return the {@code Prescription} at that index
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
     * Find and returns {@code Prescription} based on the medicine name
     * @param medicineName medicine name to be searched
     * @return {@code Prescription} with the given name
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
     * @param index {@code index} to be returned
     * @return {@code Prescription} at that index
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
     * Used for iterating through the LinkedList
     * @return the {@code Iterator} object
     */
    @Override
    public Iterator iterator() {
        return new PrescriptionIterator(this);
    }
}

/**
 * Implements the {@code Iterator} interface for easy iteration using for-loop
 * <p>
 *     Example - {@code for(Prescription prescription : list)}
 * </p>
 */
class PrescriptionIterator implements Iterator<Prescription> {
    PrescriptionNode current;

    /**
     * Constructs the Iterator
     * @param list
     */
    PrescriptionIterator (PrescriptionList list) {
        current = list.getHeadRef();
    }

    /**
     * Checks if there is a next {@code PrescriptionNode}
     * @return
     */
    @Override
    public boolean hasNext() {
        return current != null;
    }

    /**
     * To iterate through the list
     * @return
     */
    @Override
    public Prescription next() {
        Prescription output = current.prescription;
        current = current.nextNode;
        return output;
    }
}