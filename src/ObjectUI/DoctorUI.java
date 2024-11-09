package ObjectUI;

import DataObject.PharmacyObjects.MedicineData;
import DataObject.PharmacyObjects.MedicineRequest;
import DataObject.Prescription.Prescription;
import HumanObject.Doctors.Doctors;
import DataObject.Appointment.Appointment;
import HumanObject.Patient.Patient;
import HumanObject.ROLE;
import InputHandler.Input;
import Serialisation.DataSerialisation;


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
    private Doctors doctor;
    private int choice;

    /**
     * Constructs a DoctorUI instance for a specific doctor.
     * Displays a menu of actions that the doctor can perform, such as viewing or updating records,
     * setting availability, and managing appointments. The interface runs in a loop until the doctor chooses to log out.
     *
     * @param doctor the Doctors object representing the doctor using this interface
     */
    public DoctorUI(Doctors doctor) {
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


    public void updatePatientMR() { // Change check test case 10
        Input.ClearConsole();
        int flag = 0;
        for (Appointment apt : doctor.getOngoingApt()) {
            System.out.println("Patient ID: " + apt.getPatientID() + ", Patient Name: " + apt.getPatientName());
            flag = 1;
        }
        if (flag == 1) {
            int choice = Input.ScanInt("Enter the Patient ID");
            Patient patient = (Patient) database.getPerson(choice, ROLE.PATIENT);
            do {
                System.out.println("1) Patient ID");
                System.out.println("2) Patient name");
                System.out.println("3) Date of birth");
                System.out.println("4) Gender");
                System.out.println("5) Contact information");
                System.out.println("6) Blood type");
                System.out.println("7) Exit");
                choice = Input.ScanInt("Which part of the medical record do you want to update?");
                switch (choice) {
                    case 1:
                        Input.ClearConsole();
                        patient.setID(Input.ScanInt("Enter Patient new ID"));
                        break;
                    case 2:
                        Input.ClearConsole();
                        patient.setName(Input.ScanString("Enter Patient new name"));
                        break;
                    case 3:
                        Input.ClearConsole();
                        patient.setDOB(Input.ScanDate("Enter date of birth"));
                        break;
                    case 4:
                        Input.ClearConsole();
                        patient.setGender(Input.ScanBoolean("Is it male(1) or female(0):"));
                    case 5:
                        int C2Choice;
                        do {
                            Input.ClearConsole();
                            C2Choice = Input.ScanInt("What do you want to update?\n" +
                                    "1. Email Address\n" +
                                    "2. Contact Number\n" +
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
                        break;
                    case 6:
                        patient.setBloodType(Input.ScanString("Enter Patient new blood type"));
                        break;
                    case 7:
                        break;
                    default:
                        System.out.println("Wrong input please enter again");
                }
            } while (choice != 7);
        } else {
            System.out.println("There are no patients under you to update");
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

        // Prompt the doctor to select a time slot
        int time = Input.ScanInt("Choose the timing:\n" +
                "1) 10AM-11AM\n" +
                "2) 11AM-12PM\n" +
                "3) 1PM-2PM\n" +
                "4) 2PM-3PM\n" +
                "5) 3PM-4PM\n") - 1;

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
        } else {
            // If an entry exists, check and update based on the chosen availability
            if (choice == 1) { // Setting to "Not Available"
                if (doctor.getTimeSlot(date)[time]) {
                    System.out.println("Slot is already unavailable");
                } else {
                    doctor.addTimeSlot(date, time);
                }
            } else if (choice == 2) { // Setting to "Available"
                if (doctor.getTimeSlot(date)[time]) {
                    // Check for ongoing appointments to prevent conflicts
                    for (Appointment apt : doctor.getOngoingApt()) {
                        if (apt.getDate().equals(date) && apt.getTimeSlot() == time) {
                            System.out.println("There is an ongoing appointment; you are unavailable at that timing.");
                            return;
                        }
                    }
                    doctor.getTimeSlot(date)[time] = false;
                } else {
                    doctor.getTimeSlot(date)[time] = false; // Set the slot to available
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
            String choice = Input.ScanString("Enter appointment ID to accept/decline");
            int index = 0;

            for (Appointment apt : doctor.getPendingApt()) {
                if (apt.getAppointmentID().equals(choice)) {
                    int option = Input.ScanInt("0) Decline\n1) Accept");

                    if (option == 0) {
                        // Decline the appointment
                        database.docAcceptApt(apt, false);
                        doctor.getAvailability().get(apt.getDate())[apt.getTimeSlot()] = false;
                    } else if (option == 1) {
                        // Accept the appointment
                        database.docAcceptApt(apt, true);
                    }
                }
                index++;
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

