package ObjectUI;

import DataObject.Appointment.Appointment;
import DataObject.PharmacyObjects.MedicineRequest;
import DataObject.Prescription.PrescriptionList;
import HumanObject.Doctors.Doctors;
import HumanObject.Patient.Patient;
import HumanObject.ROLE;
import InputHandler.Input;
import HumanObject.Patient.ContactChecker;

import java.util.ArrayList;

/**
 * The {@code PatientUI} class provides a command-line user interface for patients to
 * manage personal information, appointments, and medical records. It interacts with
 * a {@code Patient} instance to perform various patient-related operations.
 *
 * <p>This class allows patients to:
 * <ul>
 *   <li>View and update personal information</li>
 *   <li>Schedule, reschedule, and cancel appointments</li>
 *   <li>View available appointment slots and outcomes of past appointments</li>
 * </ul>
 * </p>
 */
public class PatientUI implements BaseUI {
    private Patient patient;
    private Appointment apt;

    /**
     * Constructs a {@code PatientUI} instance for the specified {@code Patient}.
     * It presents a menu that allows the patient to perform various operations
     * such as viewing and updating information, scheduling appointments, and
     * viewing medical records.
     *
     * @param patient the {@code Patient} for whom the UI is created
     */
    public PatientUI(Patient patient) {
        this.patient = patient;
        int choice;
        do {
            Input.ClearConsole();
            System.out.println("Patient UI \n" +
                    "1: View Medical Records\n" +
                    "2: Update Personal Information\n" +
                    "3: View Available Appointment Slots\n" +
                    "4: Schedule an Appointment\n" +
                    "5: Reschedule an Appointment\n" +
                    "6: Cancel an Appointment\n" +
                    "7: View Scheduled Appointments\n" +
                    "8: View Past Appointment Outcome Records\n" +
                    "9: Logout");
            choice = Input.ScanInt("Choose an option:");
            Input.ClearConsole();

            int index;
            MedicineRequest request;
            PrescriptionList list;

            switch (choice) {
                case 1: // View patient's own medical record
                    patient.printMedicalRecord();
                    Input.ScanString("Press enter to continue\n");
//                    patient = (Patient) database.getPerson(patient.getID(), ROLE.PATIENT);
                    break;
                case 2: // Update Patient's personal information
                    updateContact();
                    break;
                case 3:
                    viewAvailableAppointments();
                    break;
                case 4:
                    scheduleAppointment();
                    break;
                case 5:
                    rescheduleApt();
                    break;
                case 6:
                    cancelApt();
                    break;
                case 7:
                    if (patient.getOngoing().getCount() == 0) {
                        System.out.println("No ongoing appointment");
                        Input.ScanString("Press enter to continue\n");
                        break;
                    }
                    System.out.println("Here are your scheduled appointments");
                    patient.getOngoing().print(true);
                    Input.ScanString("Press enter to continue\n");
                    break;
                case 8:
                    if (patient.getCompleted().getCount() == 0) {
                        System.out.println("No completed appointment");
                        Input.ScanString("Press enter to continue\n");
                        break;
                    }
                    System.out.println("Here are your completed appointments");
                    patient.getCompleted().print(true);
                    Input.ScanString("Press enter to continue\n");
                    break;
                case 9:
                    break;
                default:
                    System.out.println("Not the right option. Please select correctly");
                    break;
            }
        }
        while (choice != 9);

    }

    /**
     * Allows the patient to update their contact information, including email address
     * and contact number, based on their selection from a provided menu.
     */
    public void updateContact(){
        int C2Choice;
        do{
            Input.ClearConsole();
            C2Choice = Input.ScanInt("What do you want to update?\n"+
                    "1. Email Address\n" +
                    "2. Contact Number\n"+
                    "3. Go back\n");
            switch (C2Choice) {
                case 1:
                    Input.ClearConsole();
                    patient.getContact().setEmail();
                    break;

                case 2:
                    Input.ClearConsole();
                    patient.getContact().setContactNumber();
                    break;

                case 3:
                    break;
                default:
                    System.out.println("That is the wrong input, try again\n");
                    break;
            }
        }
        while (C2Choice != 3);
        Input.ClearConsole();
    }

    /**
     * Displays available appointment slots for the patient based on the availability of doctors.
     * Allows the patient to select a day and time slot, checking which doctors are available.
     * If no doctors are available, prompts the user to select another slot.
     */
    public void viewAvailableAppointments(){
        int []dateSlot = new int[2];
        boolean check;
        do {
            Input.ClearConsole();
            check = false;
            System.out.println("Which timing do you want to choose?\n");
            dateSlot[0] = Input.ScanInt("Choose the day: \n" +
                    "1) Monday\n" +
                    "2) Tuesday\n" +
                    "3) Wednesday\n" +
                    "4) Thursday\n" +
                    "5) Friday\n" +
                    "6) Saturday\n" +
                    "7) Sunday\n") - 1;
            dateSlot[1] = Input.ScanInt("Choose the timing:\n" +
                    "1) 10AM-11AM\n" +
                    "2) 11AM-12PM\n" +
                    "3) 1PM-2PM\n" +
                    "4) 2PM-3PM\n" +
                    "5) 3PM-4PM\n") - 1;
            ArrayList<Doctors> doctorsArrayList = database.getDoctors();
            for (Doctors doctor : doctorsArrayList) {
                if (doctor.getAvailability()[dateSlot[0]][dateSlot[1]]) {
                    System.out.println(doctor.getName() + " is available during this timeslot\n");
                    check = true;
                }
            }
            if (!check) {
                System.out.println("No doctors available at this timeslot, please pick another!\n");
                Input.ScanString("Press enter to continue\n");
            }
        } while(!check);
        Input.ScanString("Press enter to continue\n");
    }

    /**
     * Schedules a new appointment for the patient by allowing them to choose an available time slot.
     * Checks for any existing pending or ongoing appointments at the chosen slot, and if the slot is
     * available, allows the patient to select a doctor and book the appointment.
     */
    public void scheduleAppointment(){
        int []dateSlot = new int[2];
        boolean check;
        Input.ClearConsole();
        check = false;
        System.out.println("Which timing do you want to choose?\n");
        dateSlot[0] = Input.ScanInt("Choose the day: \n" +
                "1) Monday\n" +
                "2) Tuesday\n" +
                "3) Wednesday\n" +
                "4) Thursday\n" +
                "5) Friday\n" +
                "6) Saturday\n" +
                "7) Sunday\n") - 1;
        dateSlot[1] = Input.ScanInt("Choose the timing:\n" +
                "1) 10AM-11AM\n" +
                "2) 11AM-12PM\n" +
                "3) 1PM-2PM\n" +
                "4) 2PM-3PM\n" +
                "5) 3PM-4PM\n") - 1;

        for (Appointment apt : patient.getPending()) {
            if (apt.getAppointmentTime().equals(Appointment.createDate(dateSlot[0], dateSlot[1]))){
                System.out.println("A pending appointment already exist at that slot.");
                Input.ScanString("Press enter to continue\n");
                return;
            }
        }
        for (Appointment apt : patient.getOngoing()) {
            if (apt.getAppointmentTime().equals(Appointment.createDate(dateSlot[0], dateSlot[1]))){
                System.out.println("An ongoing appointment already exist at that slot.");
                Input.ScanString("Press enter to continue\n");
                return;
            }
        }

        ArrayList<Doctors> doctorsArrayList = database.getDoctors();
        for (Doctors doctor : doctorsArrayList) {
            if (doctor.getAvailability()[dateSlot[0]][dateSlot[1]]) {
                System.out.println(doctor.getID() + ": " + doctor.getName() + " is available during this timeslot\n");
                check = true;
            }
        }
        if (!check) {
            System.out.println("No doctors available at this timeslot, please pick another!\n");
            Input.ScanString("Press enter to continue\n");
        }
        while(!check);

        String service = Input.ScanString("What service are you booking for?\n");
        int doctorID = Input.ScanInt("Enter the doctor ID: \n");
        Doctors doctor = (Doctors) database.getPerson(doctorID, ROLE.DOCTOR);
        doctor.toggleAvailability(dateSlot,false);
        apt = new Appointment(service, patient.getID(),patient.getName(), doctor.getID(), doctor.getName() ,dateSlot);
        database.scheduleApt(apt);
        apt.print();
    }

    /**
     * Reschedules an existing appointment by first scheduling a new appointment and then canceling
     * the previous one. This operation ensures the patient does not have conflicting appointments.
     * If there are no existing appointments, the method informs the patient accordingly.
     */
    public void rescheduleApt(){
        if (patient.getOngoing().getCount()==0 && patient.getPending().getCount() == 0) {
            System.out.println("No existing appointments to reschedule.");
            Input.ScanString("Press enter to continue\n");
            return;
        }
        scheduleAppointment();
        cancelApt();
    }

    /**
     * Cancels an existing appointment by displaying the patient's ongoing and pending appointments,
     * allowing them to choose which to cancel. The patient is prompted to confirm the cancellation
     * before proceeding. If there are no existing appointments, the method informs the patient.
     */

    public void cancelApt(){
        if (patient.getOngoing().getCount()==0 && patient.getPending().getCount() == 0) {
            System.out.println("No existing appointments to cancel.");
            Input.ScanString("Press enter to continue\n");
            return;
        }
        boolean check;
        Appointment apt;
        do {
            check = false;
            int last = patient.getPending().print(true);
            patient.getOngoing().print(true,last);
            int index = Input.ScanInt("Enter the index of the appointment you wish to delete or -1 to exit\n");
            if(index == -1){
                return;
            }
            if (index <= patient.getPending().getCount() ) apt = patient.getPending().getAppointment(index-1);
            else apt = patient.getOngoing().getAppointment(index - 1);
            Input.ClearConsole();
            if (apt == null) {
                System.out.println("No appointment found. Please enter the correct index");
                continue;
            };
            int yn = Input.ScanInt("Please confirm that this is the appointment you want?\n" + "1. Yes\n" + "2. No\n");
            if (yn == 1){
                check = true;
            }
        } while(!check);
        database.cancelApt(apt);
    }
}
