package ObjectUI;

import DataObject.Appointment.Appointment;
import DataObject.PharmacyObjects.MedicineRequest;
import DataObject.Prescription.PrescriptionList;
import HumanObject.Doctor.Doctor;
import HumanObject.Patient.Patient;
import HumanObject.ROLE;
import InputHandler.Input;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * The {@code PatientUI} class provides a command-line user interface for patients to
 * manage personal information, appointments, and medical records. It interacts with
 * a {@code Patient} instance to perform various patient-related operations.
 *
 * <p>This class allows patients to:
 * <ul>
 *   <li>View medical records and update personal information</li>
 *   <li>Schedule, reschedule, and cancel appointments</li>
 *   <li>View available appointment slots, scheduled appointments and outcomes of past appointments</li>
 * </ul>
 * </p>
 */
public class PatientUI implements BaseUI {

    /** The patient associated with this user interface, represented by the {@code Patient} class.*/
    private Patient patient;

    /** The appointment associated with this patient, represented by the {@code Appointment} class.*/
    private Appointment apt;

    /**
     * Constructs a {@code PatientUI} instance for the specified {@code Patient}.
     * It presents a menu that allows the patient to perform various operations
     * relating to their medical records and appointments.
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
                    Input.ScanString("Press enter to continue...\n");
//                    patient = (Patient) database.getPerson(patient.getID(), ROLE.PATIENT);
                    break;
                case 2: // Update Patient's personal information
                    updateContact();
                    break;
                case 3:
                    viewAvailableAppointments();
                    break;
                case 4:
                    database.scheduleApt(scheduleApt());
                    break;
                case 5:
                    rescheduleApt();
                    break;
                case 6:
                    database.cancelApt(cancelApt());
                    break;
                case 7:
                    if (patient.getOngoing().getCount() == 0) {
                        System.out.println("No ongoing appointment");
                        Input.ScanString("Press enter to continue...\n");
                        break;
                    }
                    System.out.println("Here are your scheduled appointments");
                    patient.getOngoing().print(true);
                    Input.ScanString("Press enter to continue...\n");
                    break;
                case 8:
                    if (patient.getCompleted().getCount() == 0) {
                        System.out.println("No completed appointment");
                        Input.ScanString("Press enter to continue...\n");
                        break;
                    }
                    System.out.println("Here are your completed appointments");
                    patient.getCompleted().print(true);
                    Input.ScanString("Press enter to continue...\n");
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
     * If no doctors are available, the method terminates.
     */
    public void viewAvailableAppointments(){
        boolean check;
        Input.ClearConsole();
        check = false;
        System.out.println("Which timing do you want to choose?");

        Date date;
        int timeSlot = 0;
        while (true) {
            date = Input.ScanFutureDate("Choose the date");

            Calendar c = Calendar.getInstance();
            Date today = c.getTime();
            if (date.equals(new Date(today.getYear(), today.getMonth(), today.getDate()))) { //Checking if input date is today
                if (today.getTime() >= 15) {
                    System.out.println("No more appointment slot for today.\nPlease choose another day");
                    continue;
                }
                int maxChoice = 1;
                while (true) {
                    System.out.println("Choose the timing:");
                    int hour = today.getHours() < 10 ? 1 : today.getHours();
                    switch (hour) {
                        case 1:
                            System.out.println(maxChoice++ + ") 10AM-11AM");
                        case 10:
                            System.out.println(maxChoice++ + ") 11AM-12PM");
                        case 11,12:
                            System.out.println(maxChoice++ + ") 1PM-2PM");
                        case 13:
                            System.out.println(maxChoice++ + ") 2PM-3PM");
                        case 14:
                            timeSlot = Input.ScanInt(maxChoice + ") 3PM-4PM\n")-1;
                            break;
                        default:
                            break;
                    }
                    if (timeSlot < 0 || timeSlot > maxChoice - 1) {
                        System.out.println("Incorrect input.");
                        continue;
                    }
                    break;
                }
                timeSlot += (5 - maxChoice); // Offset + Choice
            } else {
                while (true) {
                    timeSlot = Input.ScanInt("Choose the timing:\n" +
                            "1) 10AM-11AM\n" +
                            "2) 11AM-12PM\n" +
                            "3) 1PM-2PM\n" +
                            "4) 2PM-3PM\n" +
                            "5) 3PM-4PM\n") - 1;
                    if (timeSlot < 0 || timeSlot > 4) {
                        System.out.println("Incorrect input.");
                        continue;
                    }
                    break;
                }

            }
            break;
        }
        ArrayList<Doctor> doctorsArrayList = database.getDoctors();
        for (Doctor doctor : doctorsArrayList) {
            Boolean[] availability = doctor.getTimeSlot(date);
            if (availability == null || !availability[timeSlot]) {
                System.out.println(doctor.getName() + " is available during this timeslot.");
                check = true;
            }
        }
        if (!check) {
            System.out.println("No doctors available at this timeslot, please pick another!");
            Input.ScanString("Press enter to continue...\n");
            return;
        }
        Input.ScanString("Press enter to continue...\n");
    }

    /**
     * Schedules a new appointment for the patient.
     * <p>
     * First asks patient to choose an available time slot. Then checks
     * for any existing pending or ongoing appointments at the chosen slot, and if the slot is
     * available, allows the patient to select a doctor and book the appointment. If this process succeeds,
     * appointment is added into the database.
     * </p>
     *
     */
    public Appointment scheduleApt() {
        Input.ClearConsole();
        boolean check;
        Input.ClearConsole();
        check = false;
        System.out.println("Which timing do you want to choose?");
        Date date;
        Date requestDate;
        int timeSlot = 0;
        do {
            while (true) {
                date = Input.ScanFutureDate("Choose the date");

                Calendar c = Calendar.getInstance();
                Date today = c.getTime();
                if (date.equals(new Date(today.getYear(), today.getMonth(), today.getDate()))) { //Checking if input date is today
                    if (today.getTime() >= 15) {
                    System.out.println("No more appointment slot for today.\nPlease choose another day");
                    continue;
                    }
                    int maxChoice = 1;
                    while (true) {
                        System.out.println("Choose the timing:");
                        int hour = today.getHours() < 10 ? 1 : today.getHours();
                        switch (hour) {
                            case 1:
                                System.out.println(maxChoice++ + ") 10AM-11AM");
                            case 10:
                                System.out.println(maxChoice++ + ") 11AM-12PM");
                            case 11,12:
                                System.out.println(maxChoice++ + ") 1PM-2PM");
                            case 13:
                                System.out.println(maxChoice++ + ") 2PM-3PM");
                            case 14:
                                timeSlot = Input.ScanInt(maxChoice + ") 3PM-4PM\n") - 1;
                                break;
                            default:
                                break;
                        }
                        if (timeSlot < 0 || timeSlot > maxChoice - 1) {
                            System.out.println("Incorrect input.");
                            continue;
                        }
                        break;
                    }
                    timeSlot += (5 - maxChoice); // Offset + Choice
                } else {
                    while (true) {
                        timeSlot = Input.ScanInt("Choose the timing:\n" +
                                "1) 10AM-11AM\n" +
                                "2) 11AM-12PM\n" +
                                "3) 1PM-2PM\n" +
                                "4) 2PM-3PM\n" +
                                "5) 3PM-4PM\n") - 1;
                        if (timeSlot < 0 || timeSlot > 4) {
                            System.out.println("Incorrect input.");
                            continue;
                        }
                        break;
                    }
                }
                break;
            }

            requestDate = Appointment.createDate(date, timeSlot);
            for (Appointment apt : patient.getPending()) {
                if (apt.getAppointmentTime().equals(requestDate)) {
                    System.out.println("A pending appointment already exist at that slot. Please book again.");
                    Input.ScanString("Press enter to continue...\n");
                    return null;
                }
            }
            for (Appointment apt : patient.getOngoing()) {
                if (apt.getAppointmentTime().equals(requestDate)) {
                    System.out.println("An ongoing appointment already exist at that slot. Please book again.");
                    Input.ScanString("Press enter to continue...\n");
                    return null;
                }
            }

            ArrayList<Doctor> doctorsArrayList = database.getDoctors();
            for (Doctor doctor : doctorsArrayList) {
                Boolean[] availability = doctor.getTimeSlot(date);
                if (availability == null || !availability[timeSlot]) {
                    System.out.println(doctor.getID() + ": " + doctor.getName() + " is available during this timeslot");
                    check = true;
                }
            }
            if (!check) {
                System.out.println("No doctors available at this timeslot, please book again.");
                Input.ScanString("Press enter to continue...\n");
                return null;
            }
        }
        while(!check);
        String service = Input.ScanString("What service are you booking for?\n");
        do {
            int doctorID = Input.ScanInt("Enter the doctor ID: \n");
            Doctor doctor = (Doctor) database.getPerson(doctorID, ROLE.DOCTOR);
            if (doctor == null) {
                System.out.println("Invalid Doctor ID. Please try again");
                continue;
            }
            apt = new Appointment(service, patient.getID(),patient.getName(), doctor.getID(), doctor.getName() ,requestDate);
            apt.print();
            Input.ScanString("Appointment created\nPress enter to continue...");
            return apt;
        } while(true);
    }

    /**
     * Reschedules an existing appointment for a patient.
     * <p>
     * First checks if the patient has any ongoing or pending appointments.
     * If no appointments are available, the method terminates.
     * If appointments are available, it prompts the user to select an appointment to cancel via {@link #cancelApt()}.
     * After successfully canceling the existing appointment, it prompts the user to schedule a new appointment via {@link #scheduleApt()}.
     * If either the cancellation or new scheduling process is canceled or fails, the method terminates.
     * If both processes succeed, the appointment is rescheduled in the database.
     * </p>
     */
    public void rescheduleApt(){
        Input.ClearConsole();
        if (patient.getOngoing().getCount()==0 && patient.getPending().getCount() == 0) {
            System.out.println("No existing appointments to reschedule.");
            Input.ScanString("Press enter to continue...\n");
            return;
        }

        Appointment cancel = cancelApt();
        if (cancel == null) {
            System.out.println("Reschedule cancelled as existing appointment was not cancelled");
            Input.ScanString("Press enter to continue...\n");
            return;
        }

        Appointment add = scheduleApt();
        if (add == null) {
            System.out.println("Reschedule cancelled as new appointment was not created");
            Input.ScanString("Press enter to continue...\n");
            return;
        }
        database.rescheduleApt(add,cancel);
    }

    /**
     * Reschedules an existing appointment for a patient.
     * <p>
     * First checks if the patient has any ongoing or pending appointments. If no appointment is available,
     * the method terminates.
     * Cancels an existing appointment by displaying the patient's ongoing and pending appointments,
     * allowing them to choose which to cancel. The patient is prompted to confirm the cancellation
     * before proceeding. If there are no existing appointments, the method terminates.
     * </p>
     */

    public Appointment cancelApt(){
        Input.ClearConsole();
        if (patient.getOngoing().getCount()==0 && patient.getPending().getCount() == 0) {
            System.out.println("No existing appointments to cancel.");
            Input.ScanString("Press enter to continue...\n");
            return null;
        }
        boolean check;
        Appointment apt = null;
        do {
            check = false;
            int last = patient.getPending().print(true);
            patient.getOngoing().print(true,last);
            int index = Input.ScanInt("Enter the index of the appointment you wish to delete or -1 to exit\n");

            if(index == -1){
                return null;
            }
            if (index <= 0 || index > patient.getPending().getCount() + patient.getOngoing().getCount()) {
                System.out.println("Invalid index. Please enter a valid number.");
                continue;
            }
            if (index <= patient.getPending().getCount() ) apt = patient.getPending().getAppointment(index-1);
            else apt = patient.getOngoing().getAppointment(index - 1);
            Input.ClearConsole();
            if (apt == null) {
                System.out.println("No appointment found. Please enter the correct index");
                continue;
            }
            int yn;
            do {
                yn = Input.ScanInt("Please confirm that this is the appointment you want to cancel?\n" + "1. Yes\n" + "2. No\n");
                if (yn != 1 && yn != 2)
                    System.out.println("Invalid input. Please enter 1 or 2.");
            }
            while (yn != 1 && yn != 2);

            if (yn == 1){
                check = true;
            }
        } while(!check);
        return apt;
    }
}
