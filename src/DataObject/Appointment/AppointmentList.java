package DataObject.Appointment;

class AppointmentNode {
    Appointment appointment;
    AppointmentNode nextNode;
}

public class AppointmentList {

    // Private attributes
    private AppointmentNode headRef;
    private AppointmentNode tailRef;
    private int count;

    // Constructor
    // Default

    // Public methods
    public int getCount() {return count;}

    public void print(boolean Patient) {
        AppointmentNode curRef = this.headRef;
        while (curRef != null) {
            curRef.appointment.print(Patient);
            curRef = curRef.nextNode;
        }
    }

    public void addAppointment(Appointment appointment) {
        AppointmentNode insert = new AppointmentNode();
        insert.appointment = appointment;

        if (this.headRef == null) {
            this.headRef = insert;
            this.tailRef = insert;
        } else {
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
        }
        this.count++;
    }

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

    public Appointment findAppointment(int index) { // Takes index and returns the appointment object (Assuming no duplicate)
        if (index >= count) return null;
        AppointmentNode curRef = this.headRef;
        while (index > 0) {
            curRef = curRef.nextNode;
            index--;
        }
        return curRef.appointment; // Return null if target medicine not prescribed
    }

    public void DataSave() {
        AppointmentNode curRef = headRef;
        while (curRef != null) {
            System.out.println(curRef.appointment.getDataSave());
            curRef = curRef.nextNode;
        }
    }
}
