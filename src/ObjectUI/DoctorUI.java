package ObjectUI;

import DataObject.PharmacyObjects.MedicineData;
import DataObject.PharmacyObjects.MedicineRequest;
import DataObject.Prescription.Prescription;
import HumanObject.Doctor.Doctor;
import DataObject.Appointment.Appointment;
import HumanObject.Patient.Patient;
import HumanObject.ROLE;
import InputHandler.Input;


import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
/**
 * DoctorUI class represents the user interface for doctor-related functionalities.
 * It allows the doctor to interact with the system to view and update patient records,
 * set availability, manage appointments, and more.
 */
public class DoctorUI implements BaseUI {

    /** The doctor associated with this user interface, represented by the {@code Doctor} class. */
    private Doctor doctor;

    /** The user's choice input, used to determine actions within the user interface. */
    private int choice;

    /**
     * Constructs a DoctorUI instance for a specific doctor.
     * Displays a menu of actions that the doctor can perform, such as viewing or updating records,
     * setting availability, and managing appointments. The interface runs in a loop until the doctor chooses to log out.
     *
     * @param doctor the Doctors object representing the doctor using this interface
     */
    public DoctorUI(Doctor doctor) {
        this.doctor = doctor;

        do {
            Input.ClearConsole();
            System.out.println("Doctor UI \n" +
                    "1: View Patient Medical Record\n" +
                    "2: Update Patient Medical Records \n" +
                    "3: View Personal Schedule \n" +
                    "4: Set Availability for Appointments \n" +
                    "5: Accept or Decline Appointment Requests \n" +
                    "6: View Upcoming Appointments  \n" +
                    "7: Record Appointment Outcome \n" +
                    "8: Logout");
            choice = Input.ScanInt("Choose an option:");

            switch (choice) {
                case 1:
                    viewPatient();
                    break;
                case 2:
                    updatePatientMR();
                    break;
                case 3:
                    viewSchedule();
                    break;
                case 4:
                    setAvailability();
                    break;
                case 5:
                    aptReq();
                    break;
                case 6:
                    viewOngoingAPT();
                    break;
                case 7:
                    recordAptOutcome();
                    break;
                default:
                    break;
            }
        } while (choice < 8);
    }


    /**
     * Displays the list of ongoing patients under the doctor's care and allows the doctor to view a selected patient's medical record.
     * If no patients are under the doctor's care, a message is displayed.
     * Otherwise, the doctor can enter a patient ID to view the corresponding medical record and appointment history.
     */
    public void viewPatient() {
        Input.ClearConsole();
        int flag = 0;
        ArrayList<Integer> list = new ArrayList<>();

        // Loop through the doctor's ongoing appointments and display each patient's ID and name.
        for (Appointment apt : doctor.getOngoingApt()) {
            System.out.println("Patient ID: " + apt.getPatientID() + ", Patient Name: " + apt.getPatientName());
            list.add(apt.getPatientID());
            flag = 1;
        }

        // If no patients are available, display a message and exit the method.
        if (flag == 0) {
            System.out.println("There are no patients under you now.");
        } else {
            int choice;

            // Prompt the doctor to enter a patient ID until a valid ID from the ongoing appointments is provided.
            while (true) {
                choice = Input.ScanInt("Enter the ID of the patient you want to view");
                if (!list.contains(choice)) {
                    System.out.println("Invalid ID");
                    continue;
                }
                break;
            }

            // Retrieve and display the selected patient's medical record.
            Patient patient = (Patient) database.getPerson(choice, ROLE.PATIENT);
            patient.printMedicalRecord();

            // Display the patient's completed appointment history with this doctor.
            for (Appointment apt : doctor.getCompletedApt()) {
                if (apt.getPatientID() == choice) {
                    apt.print(false);
                }
            }

            // Wait for user input before returning to the main menu.
            Input.ScanString("Enter to continue...");
        }
    }

    /**
     * Updates the medical record of a patient by allowing the doctor to edit the latest consultation notes.
     * This method displays ongoing appointments and prompts the doctor to select a patient to update their record.
     * If the selected patient has previous completed appointments, the doctor is prompted to edit the latest notes.
     *
     * Function Flow:
     * 1. Displays ongoing appointments for the doctor.
     * 2. Prompts the doctor to enter a patient ID.
     * 3. If the patient has past appointments, asks if the doctor wants to edit the latest notes.
     * 4. Updates the notes if confirmed by the doctor.
     */
    public void updatePatientMR() { // Change check test case 10
        Input.ClearConsole();
        int flag = 0;
        // Display ongoing appointments
        for (Appointment apt : doctor.getOngoingApt()) {
            System.out.println("Patient ID: " + apt.getPatientID() + ", Patient Name: " + apt.getPatientName());
            flag = 1;
        }
        if(flag==1) {
            Patient patient;
            while (true) {
                int choice = Input.ScanInt("Enter the Patient ID");
                patient = (Patient) database.getPerson(choice, ROLE.PATIENT);
                if (patient == null) {
                    System.out.println("Invalid input.");
                    continue;
                }
                break;
            }
            patient.printMedicalRecord();
            if (patient.getCompleted().getCount() == 0) {
                Input.ScanString("No past appointments notes to edit.\nEnter to continue...");
                return;
            }
            boolean b = Input.ScanBoolean("Do you want to edit the latest notes?");
            if (b) {
                String notes = Input.ScanString("Enter Consultation notes:");
                patient.getCompleted().getAppointment(0).setNotes(notes);
                Input.ScanString("Notes edited\nEnter to continue...");
            }
        }
    }

    /**
     * Displays the doctor's availability schedule for the upcoming week.
     * Clears the console, then prints the first week's time slots using the doctor's
     * `printFirstWeekTimeSlot` method. Waits for user input before returning to the main menu.
     */
    public void viewSchedule() {
        Input.ClearConsole();
        doctor.printFirstWeekTimeSlot();
        Input.ScanString("Enter to continue...");
    }

    /**
     * Allows the doctor to modify their availability for a specific date and time.
     * The method first displays the current week's schedule, then prompts the doctor
     * to select a date and time slot to update. It also verifies the chosen slot's availability status
     * and ensures there are no conflicts with existing appointments before updating the availability.
     */
    public void setAvailability() {
        Input.ClearConsole();
        viewSchedule();
        System.out.println("Which date and time do you want to change your availability?");

        // Prompt the doctor to select a future date to modify
        Date date = Input.ScanFutureDate("Choose the date");
        int time;
        // Prompt the doctor to select a time slot
        boolean dayCheck = false;
        do {
            time = Input.ScanInt("Choose the timing:\n" +
                    "1) 10AM-11AM\n" +
                    "2) 11AM-12PM\n" +
                    "3) 1PM-2PM\n" +
                    "4) 2PM-3PM\n" +
                    "5) 3PM-4PM\n") - 1;
            if (time <= 4 && time >= 0) {
                dayCheck = true;
                break;
            }
            System.out.println("THat is not the right option!\n");
        }
        while(!dayCheck);

        // Prompt the doctor to set the availability to either "Not Available" or "Available"
        int choice = Input.ScanInt("What do you want to change your availability to?\n" +
                "1) Not Available\n" +
                "2) Available\n");

        // Check if there's an existing time slot entry for the given date
        if (doctor.getTimeSlot(date) == null) {
            // If no entry exists, create one if setting to "Not Available"
            if (choice == 1) {
                doctor.addTimeSlot(date, time);
            } else {
                System.out.println("Timing is already available");
            }
        }
        else {
            // If an entry exists, check and update based on the chosen availability
            if (choice == 1) { // Setting to "Not Available"
                if (doctor.getTimeSlot(date)[time]) {
                    System.out.println("Slot is already unavailable");
                } else {
                    doctor.addTimeSlot(date, time);
                }
            }
            else if (choice == 2) { // Setting to "Available"
                if (doctor.getTimeSlot(date)[time]) {
                    // Check for ongoing appointments to prevent conflicts
                    for (Appointment apt : doctor.getPendingApt()) {
                        if (apt.getDate().equals(date) && apt.getTimeSlot() == time) {
                            System.out.println("Cancelling pending appointment: " + apt.getAppointmentID());
                            database.docAcceptApt(apt,false);
                            return;
                        }
                    }
                    for (Appointment apt : doctor.getOngoingApt()) {
                        if (apt.getDate().equals(date) && apt.getTimeSlot() == time) {
                            System.out.println("There is an ongoing appointment; you are unavailable at that timing.");
                            return;
                        }
                    }
                    doctor.removeTimeSlot(date, time);
                } else {
                    doctor.removeTimeSlot(date, time); // Set the slot to available
                }
            }
        }
    }


    /**
     * Allows the doctor to manage pending appointment requests by viewing the list of pending appointments,
     * selecting an appointment ID, and choosing to accept or decline the appointment.
     * If no pending appointments are available, a message is displayed to indicate this.
     */
    public void aptReq() {
        Input.ClearConsole();
        int flag = 0;

        // Display the list of pending appointments for the doctor
        for (Appointment apt : this.doctor.getPendingApt()) {
            System.out.println("Patient ID: " + apt.getPatientID() + ", Patient Name: " + apt.getPatientName() + ", Appointment ID: " + apt.getAppointmentID());
            flag = 1;
        }

        // If there are pending appointments, prompt the doctor to accept or decline a specific appointment
        if (flag == 1) {
            flag = 0;
            String choice;
            do {
                choice = Input.ScanString("Enter appointment ID to accept/decline");
                for (Appointment apt : this.doctor.getPendingApt()){
                    if(choice.equals(apt.getAppointmentID())){
                        flag = 1;
                        break;
                    }
                }
                if(flag != 1) {
                    System.out.println("Invalid Appointment ID. Please enter a valid ID");
                }
            }while(flag != 1);

            for (Appointment apt : doctor.getPendingApt()) {
                if (apt.getAppointmentID().equals(choice)) {
                    int option;
                    do {
                        option = Input.ScanInt("Please confirm that this is the appointment you want?\n" + "1. Yes\n" + "2. No\n");
                        if (option  != 1 && option != 2)
                            System.out.println("Invalid input. Please enter 1 or 2.");
                    } while (option != 1 && option != 2);

                    if (option == 2) {
                        // Decline the appointment
                        database.docAcceptApt(apt, false);
                    } else if (option == 1) {
                        // Accept the appointment
                        database.docAcceptApt(apt, true);
                    }
                }
            }
        } else {
            System.out.println("There are currently no pending appointments");
        }
    }

    /**
     * Displays the list of ongoing appointments for the doctor.
     * If there are ongoing appointments, each appointment's patient ID, patient name, and appointment ID are displayed.
     * If there are no ongoing appointments, a message is shown to indicate this.
     */
    public void viewOngoingAPT() {
        Input.ClearConsole();
        int flag = 0;

        // Loop through the doctor's ongoing appointments and display each appointment's details
        for (Appointment apt : this.doctor.getOngoingApt()) {
            System.out.println("Patient ID: " + apt.getPatientID() + ", Patient Name: " + apt.getPatientName() + ", Appointment ID: " + apt.getAppointmentID());
            flag = 1;
        }

        // If no ongoing appointments are found, display a message indicating this
        if (flag == 0) {
            System.out.println("There are currently no ongoing appointments");
        }
    }


    /**
     * Records the outcome of an ongoing appointment by allowing the doctor to add prescriptions,
     * enter consultation notes, and mark the appointment as completed.
     * The method prompts the doctor to add medicines to the patient's prescription
     * if desired, then finalizes the appointment with consultation notes.
     * If there are no ongoing appointments, it displays a message and exits.
     */
    public void recordAptOutcome() {
        Input.ClearConsole();

        // Retrieve the first ongoing appointment
        Appointment apt = doctor.getOngoingApt().getAppointment(0);

        // Check if there are any ongoing appointments
        if (apt == null) {
            System.out.println("There are no ongoing appointments to record outcome.");
            return;
        }

        // Prompt doctor to decide if a prescription is needed
        boolean choice = Input.ScanBoolean("Do you want to give a prescription");

        // Retrieve available medicines from the pharmacy
        HashMap<Integer, MedicineData> temp = pharmacy.getMedicine();

        // Loop to add medicines to the prescription list if doctor chooses to prescribe
        while (choice && !temp.isEmpty()) {
            for (Map.Entry<Integer, MedicineData> o : temp.entrySet()) {
                System.out.println(o.getValue().getIDString() + ": " + o.getValue().getName());
            }

            // Create a new prescription and retrieve medicine ID from the pharmacy
            Prescription prescription = new Prescription();
            int ID = pharmacy.convertNameToID(prescription.getMedicineName());

            // Remove the selected medicine from available options
            temp.remove(ID);

            // Add the prescription to the appointment's prescription list
            apt.getPrescriptionList().addPrescription(prescription);

            // Check if more medicines are desired for the prescription
            if (temp.isEmpty()) break;
            choice = Input.ScanBoolean("Do you still want to prescribe any more medicine.");
        }

        // Create a medicine request for the appointment's prescription list
        MedicineRequest req = new MedicineRequest(apt.getPatientID(), apt.getDoctorID(), apt.getAppointmentID());
        pharmacy.requestMedicine(req);

        // Prompt doctor to enter consultation notes
        apt.setNotes(Input.ScanString("Enter Consultation notes"));

        // Mark the appointment as complete in the database
        database.completeApt(apt);
    }
}

