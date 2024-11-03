package ObjectUI;

import DataObject.PharmacyObjects.MedicineRequest;
import DataObject.Prescription.PrescriptionList;
import DepartmentObject.Pharmacy;
import DepartmentObject.UserInfoDatabase;
import HumanObject.Patient.Patient;
import HumanObject.ROLE;
import InputHandler.Input;
import HumanObject.Patient.ContactChecker;

public class PatientUI {
    private Pharmacy pharmacy;
    private Patient patient;
    private UserInfoDatabase database;

    public PatientUI(UserInfoDatabase database, Pharmacy pharmacy, Patient patient) {
        this.pharmacy = pharmacy;
        this.database = database;


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

            int index;
            MedicineRequest request;

            PrescriptionList list;

            switch (choice) {
                case 1: // View patient's own medical record
                    Input.ClearConsole();
                    patient.printMedicalRecord();
//                    patient = (Patient) database.getPerson(patient.getID(), ROLE.PATIENT);
                    break;
                case 2: // Update Patient's personal information
                    Input.ClearConsole();
                    int C2Choice = Input.ScanInt("What do you want to update?\n"+
                            "1. Email Address\n" +
                            "2. Contact Number\n"+
                            "3. Go back\n");
                    Input.ClearConsole();

                    while (C2Choice != 3) {
                        switch (C2Choice) {
                            case 1:
                                String email;
                                Input.ClearConsole();
                                do{
                                    email = Input.ScanString("Enter a new email address: ");
                                    if (ContactChecker.checkValidEmail(email)) {
                                        patient.getContact().setEmail(email);
                                    }
                                    else {
                                        System.out.println("That isn't a valid email address, try again");
                                    }
                                }
                                while (!ContactChecker.checkValidEmail(email));
                                break;

                            case 2:
                                String contactNo;
                                Input.ClearConsole();
                                do{
                                    contactNo = Input.ScanString("Enter a new contact number: ");
                                    if (ContactChecker.checkValidSingaporePhone(contactNo)) {
                                        patient.getContact().setContactNumber(contactNo);
                                    }
                                    else {
                                        System.out.println("That isn't a valid contact number, try again");
                                    }
                                }
                                while (!ContactChecker.checkValidSingaporePhone(contactNo));
                                break;

                            case 3:
                                break;
                            default:
                                System.out.println("That is the wrong input, try again\n");
                                break;
                        }
                    }
                    Input.ClearConsole();
                    break;
                case 3:

                    break;
                case 4:
                    // To be inputted by Julian
                    break;
                case 5:
                    // To be inputted by Julian
                    break;
                case 6:
                    // To be inputted by Julian
                    break;
                case 7:
                    System.out.println("Here are your past appointments");
                    patient.getOngoing().print(true);
                    patient.getPending().print(true);
                    break;
                case 8:
                    System.out.println("Here are your past appointments");
                    patient.getCompleted().print(true);
                    break;
                case 9:
                    break;
                default:
                    System.out.println("Not the right option. Please select correctly");
                    break;
            }

    } while (choice != 9);

    }
}
