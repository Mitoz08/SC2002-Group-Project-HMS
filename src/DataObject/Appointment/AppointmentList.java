package DataObject.Appointment;

import DataObject.Prescription.Prescription;

import java.util.Iterator;

class AppointmentNode {
    Appointment appointment;
    AppointmentNode nextNode;
}

/**
 * A linked list of appointments
 */
public class AppointmentList implements Iterable<Appointment> {

    // Private attributes
    private AppointmentNode headRef;
    private AppointmentNode tailRef;
    private int count;
    private boolean ascending;

    // Constructor

    /**
     * Creates AppointmentList object sorted in ascending if argument is true
     * @param ascending
     */
    public AppointmentList(boolean ascending) {
        this.ascending = ascending;
    }

    // Public methods
    public int getCount() {return count;}
    public AppointmentNode getHeadRef() {return headRef;}

    /**
     * Prints out every appointment in the list
     * With either PatientID/DoctorID base on the boolean input
     * @param Patient
     */
    public int print(boolean Patient) {
        AppointmentNode curRef = this.headRef;
        if (curRef == null) return 1;
        int index = 1;
        while (curRef != null) {
            curRef.appointment.print(Patient,index++);
            curRef = curRef.nextNode;
        }
        return index;
    }


    public int print(boolean Patient, int lastIndex) {
        AppointmentNode curRef = this.headRef;
        if (curRef == null) return 1;
        int index = lastIndex;
        while (curRef != null) {
            curRef.appointment.print(Patient,index++);
            curRef = curRef.nextNode;
        }
        return index;
    }

    /**
     * Adds appointment into the list in ascending/descending order
     * @param appointment
     */
    public void addAppointment(Appointment appointment) {
        this.count++;
        AppointmentNode insert = new AppointmentNode();
        insert.appointment = appointment;

        if (this.headRef == null) {
            this.headRef = insert;
            this.tailRef = insert;
        } else if (ascending){
            AppointmentNode curRef = headRef;
            if (appointment.compareTo(curRef.appointment) < 0) { // If it should be in spot 0
                insert.nextNode = curRef;
                this.headRef = insert;
                return;
            }
            while (curRef.nextNode != null && appointment.compareTo(curRef.nextNode.appointment) > 0) curRef = curRef.nextNode; // Traverse to the n-1 node to insert the n node
            if (curRef.nextNode == null) this.tailRef = insert;
            insert.nextNode = curRef.nextNode;
            curRef.nextNode = insert;
        } else {
            AppointmentNode curRef = headRef;
            if (appointment.compareTo(curRef.appointment) > 0) { // If it should be in spot 0
                insert.nextNode = curRef;
                this.headRef = insert;
                return;
            }
            while (curRef.nextNode != null && appointment.compareTo(curRef.nextNode.appointment) < 0) curRef = curRef.nextNode; // Traverse to the n-1 node to insert the n node
            if (curRef.nextNode == null) this.tailRef = insert;
            insert.nextNode = curRef.nextNode;
            curRef.nextNode = insert;
        }
    }

    /**
     * Removes the prescription by index starting from 0
     * @param index
     * @return
     */
    public int removeAppointment(int index){ // Remove by index return 0 if successful -1 if unsuccessful
        if (count == 0 || index < 0 || index >= count) return -1;
        if (index == 0) {
            this.headRef = this.headRef.nextNode;
            this.count--;
            return 0;
        }
        AppointmentNode curRef = this.headRef;
        for (int i = 1; i < index; i++) {
            curRef = curRef.nextNode;
        }
        curRef.nextNode = curRef.nextNode.nextNode;
        this.count--;
        return 0;
    }

    /**
     * Find and returns prescription based on the medicine name
     * @param index
     * @return
     */
    public Appointment getAppointment(int index) { // Takes index and returns the appointment object (Assuming no duplicate)
        if (index >= this.count) return null;
        AppointmentNode curRef = this.headRef;
        while (index > 0) {
            curRef = curRef.nextNode;
            index--;
        }
        return curRef.appointment;
    }

    @Override
    public Iterator<Appointment> iterator() {
        return new AppointmentIterator(this);
    }
}

class AppointmentIterator implements Iterator<Appointment> {
    AppointmentNode current;

    public AppointmentIterator(AppointmentList list) {
        current = list.getHeadRef();
    }

    @Override
    public boolean hasNext() {
        return current != null;
    }

    @Override
    public Appointment next() {
        Appointment output = current.appointment;
        current = current.nextNode;
        return output;
    }
}
