package DataObject.Appointment;


import java.util.Iterator;

/**
 * A node object storing an {@code Appointment} and reference to the next {@code AppointmentNode}
 * <P> Used for implementing a LinkedList</P>
 */
class AppointmentNode {
    /** The {@code Appointment} stored in the node. */
    Appointment appointment;

    /** The next node of the linked list. */
    AppointmentNode nextNode;
}

/**
 * A class that stores the {@code Appointment} in a LinkedList structure
 *
 * @see Appointment
 */
public class AppointmentList implements Iterable<Appointment> {

    // Private attributes

    /** The head node of the linked list. */
    private AppointmentNode headRef;

    /** The tail node of the linked list. */
    private AppointmentNode tailRef;

    /** The number of node in the linked list. */
    private int count;

    /** The boolean variable to determine whether appointments are stored in ascending or descending. */
    private boolean ascending;

    // Constructor
    /**
     * Constructs a LinkedList of sorted {@code Appointment} objects
     * @param ascending if {@code true} - sorts in ascending, otherwise descending
     */
    public AppointmentList(boolean ascending) {
        this.ascending = ascending;
    }

    // Public methods
    // Getters

    /**
     * Gets the number of {@code Appointment} in the list
     * @return the number of {@code Appointment} in the list
     */
    public int getCount() {return count;}
    /**
     * Gets the reference to the first {@code AppointmentNode} in the list
     * @return the reference to the first {@code AppointmentNode} in the list
     */
    public AppointmentNode getHeadRef() {return headRef;}

    /**
     * Prints out every {@code Appointment} object in the list using print function from {@code Appointment}
     *
     * @param Patient {@code true} - prints out doctor's name, {@code false} - prints out patient's name
     *
     * @see Appointment#print(boolean, int)  Appointment print function
     * @return the index of the {@code Appointment}
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

    /**
     * Prints out every {@code Appointment} object in the list using print function from {@code Appointment}
     * <p> Starting from {@code startIndex} </p>
     *
     * @param Patient {@code true} - prints out doctor's name, {@code false} - prints out patient's name
     * @param startIndex the index of the first {@code Appointment} in the List
     * @see Appointment#print(boolean, int)  Appointment print function
     * @return the index of the {@code Appointment}
     */
    public int print(boolean Patient, int startIndex) {
        AppointmentNode curRef = this.headRef;
        if (curRef == null) return 1;
        int index = startIndex;
        while (curRef != null) {
            curRef.appointment.print(Patient,index++);
            curRef = curRef.nextNode;
        }
        return index;
    }

    /**
     * Adds appointment into the list in ascending/descending order
     * @param appointment {@code Appointment} to be added
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
     * @param index the index of the {@code Appointment} to remove
     * @return {@code 0} if successful, {@code -1} if unsuccessful
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
     * Find and returns {@code Appointment} based on the {@code index} given
     * @param index {@code index} of the {@code Appointment}
     * @return {@code Appointment} at {@code index}
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

    /**
     * Used for iterating through the LinkedList
     * @return the Iterator Object
     */
    @Override
    public Iterator<Appointment> iterator() {
        return new AppointmentIterator(this);
    }
}

/**
 * Implements the {@code Iterator} interface for easy iteration using for-loop
 * <p>
 *     Example - {@code for(Appointment apt : list)}
 * </p>
 */
class AppointmentIterator implements Iterator<Appointment> {
    AppointmentNode current;

    /**
     * Constructs the Iterator
     * @param list
     */
    public AppointmentIterator(AppointmentList list) {
        current = list.getHeadRef();
    }

    /**
     * Checks if there is a next {@code AppointmentNode}
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
    public Appointment next() {
        Appointment output = current.appointment;
        current = current.nextNode;
        return output;
    }
}
