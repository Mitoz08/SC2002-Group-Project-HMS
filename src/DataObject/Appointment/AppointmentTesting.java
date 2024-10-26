package DataObject.Appointment;

public class AppointmentTesting {
    public static void run() {
        System.out.println();
        System.out.println("Appointment Test");
        Appointment apt1 = new Appointment("0/Chemo/1001/001/2024-05-21-16-00/Empty/0-MedicineName1-10/0-MedicineName2-10");
        Appointment apt2 = new Appointment("0/Chemo/1001/001/2024-05-21-13-00/Empty/0-MedicineName1-10/0-MedicineName2-10");
        Appointment apt3 = new Appointment("0/Chemo/1001/001/2024-05-22-13-00/Empty/0-MedicineName1-10/0-MedicineName2-10");

        AppointmentList list1 = new AppointmentList(false);
        list1.addAppointment(apt1);
        list1.addAppointment(apt2);
        list1.addAppointment(apt3);

        System.out.println("Printing sorted list\n");
        list1.print(false);

        System.out.println("Removing index 1");
        list1.removeAppointment(1);

        System.out.println("Printing sorted list\n");
        list1.print(false);

        System.out.println("Printing prescription of appointment 0");
        list1.getAppointment(0).printPrescription();

        list1.DataSave();

    }
}
