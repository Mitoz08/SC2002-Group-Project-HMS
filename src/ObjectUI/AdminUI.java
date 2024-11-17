package ObjectUI;

import DataObject.Appointment.Appointment;
import DataObject.Appointment.AppointmentList;
import DataObject.PharmacyObjects.RestockRequest;
import DepartmentObject.*;
import HumanObject.Administrator.Administrator;
import HumanObject.BasePerson;
import HumanObject.Doctor.Doctor;
import HumanObject.Pharmacist.Pharmacist;
import HumanObject.ROLE;
import InputHandler.Input;
import Singleton.ServerHMS;

import java.util.ArrayList;
import java.util.Date;

public class AdminUI implements BaseUI{

    //Attribute
    private AccountInfoDatabase login;

    /**
     * Constructs an AdminUI for managing hospital administrative functions for a given Administrator.
     * <p>
     * This user interface allows the administrator to perform several management functions within a hospital
     * setting, including viewing staff, managing staff records, viewing appointments, checking medication
     * inventory, approving restock requests, and logging out.
     * </p>
     * <p><strong>Options available:</strong></p>
     * <ul>
     *     <li><strong>1: View Hospital Staff</strong> - Displays a list of all hospital staff members,
     *         including doctors, pharmacists, and administrators.</li>
     *     <li><strong>2: Manage Hospital Staff</strong> - Allows the administrator to add or remove staff members.
     *         When adding, the selected role determines whether the new staff member is a doctor, pharmacist,
     *         or administrator. Removing a staff member includes removing all of their associated login credentials.</li>
     *     <li><strong>3: View Appointment Details</strong> - Displays detailed information about pending, ongoing,
     *         and completed appointments.</li>
     *     <li><strong>4: View Medication Inventory</strong> - Displays current stock levels and other details
     *         regarding the hospital's medication inventory.</li>
     *     <li><strong>5: Approve Replenishment Requests</strong> - Enables the administrator to review and
     *         approve or deny requests for medication restocking.</li>
     *     <li><strong>6: Logout</strong> - Logs the administrator out of the system.</li>
     * </ul>
     *
     * <p><strong>Usage:</strong></p>
     * This method runs in a loop, repeatedly displaying the options menu and awaiting user input until the
     * administrator chooses to log out (option 6).
     *
     * @param admin The Administrator who is logged into the AdminUI and performing these actions.
     */
    public AdminUI(Administrator admin){
        this.login = ServerHMS.getInstance().getLogin();

        int choice=0;


        do{
            Input.ClearConsole();
            System.out.println("Admin UI \n" +
                    "1: View Hospital Staff\n" +
                    "2: Manage Hospital Staff\n" +
                    "3: View Appointment Details\n" +
                    "4: View Medication Inventory\n" +
                    "5: Approving Replemenishment Requests\n" +
                    "6: Logout");

            choice = Input.ScanInt("Choose an option:");

            switch (choice){
                case 1: //View Hospital Staff
                    Input.ClearConsole();
                    viewHospitalStaff();
                    break;
                case 2: //Manage Hospital Staff
                    Input.ClearConsole();
                    int add = Input.ScanInt("1. Add staff\n" + "2. Remove Staff");
                    switch(add){
                        case 1:
                            BasePerson toAdd = addStaff();
                            ROLE role = toAdd.getRole();
                            login.addNewAccount(toAdd);
                            switch (role){
                                case DOCTOR:
                                    this.database.getDoctors().add((Doctor) toAdd);
                                    break;
                                case ADMINISTRATOR:
                                    this.database.getAdministrators().add((Administrator) toAdd);
                                    break;
                                case PHARMACIST:
                                    this.database.getPharmacists().add((Pharmacist) toAdd);
                                    break;
                                default:
                                    System.out.println("There is an error while adding the new staff");
                                    break;
                            }
                            System.out.println("The Staff has been added to the Database\n");
                            break;
                        case 2:
                            BasePerson toRemove = removeStaff();
                            if (toRemove == null){
                                break;
                            }
                            login.removeAccount(toRemove);
                            System.out.println("The Staff has been removed from the Database\n");
                            break;
                    }
                    break;
                case 3: //View Appointment Details
                    Input.ClearConsole();
                    viewAppointments();
                    break;
                case 4: //View Medication Inventory
                    Input.ClearConsole();
                    viewMedicationInventory();
                    break;
                case 5: //Approve Replenishment Requests
                    Input.ClearConsole();
                    ApproveReplenishmentRequests(admin);
                    break;
                case 6: //Logging out of Admin Account
                    Input.ClearConsole();
                    System.out.println("Logging out of Administrator");
                    break;
                default:
                    Input.ClearConsole();
                    System.out.println("Not the right option. Please select correctly");
                    continue;
            }

        }while (choice!=6);

    }

    /**
     * Displays a list of all hospital staff members, including doctors, pharmacists, and administrators.
     * Each staff member's name, ID, and role are printed in a numbered format.
     * If any of the staff lists are empty, the method will skip over them without printing.
     *
     * Method accesses the `Doctors`, `Pharmacists`, and `Administrators` lists from the database
     * to retrieve staff information.
     */
    private void viewHospitalStaff(){
        int numOfStaff = 1;
        ArrayList<Doctor> doctors = this.database.getDoctors();
        ArrayList<Pharmacist> pharmacists = this.database.getPharmacists();
        ArrayList<Administrator> administrators = this.database.getAdministrators();
        for (Doctor doc: doctors){
            System.out.println(numOfStaff + ". " + "Name: "+ doc.getName() + ", ID: " + doc.getID() + ", Role: " + doc.getRole());
            numOfStaff++;
        }
        for (Pharmacist ph: pharmacists){
            System.out.println(numOfStaff + ". " +"Name: "+ ph.getName() + ", ID: " + ph.getID() + ", Role: " + ph.getRole());
            numOfStaff++;
        }
        for (Administrator ad: administrators){
            System.out.println(numOfStaff + ". " +"Name: "+ ad.getName() + ", ID: " + ad.getID() + ", Role: " + ad.getRole());
            numOfStaff++;
        }
        Input.ScanString("Enter to continue...");
        return;
    }

    /**
     * Displays a list of all hospital appointments, categorized by status: pending, ongoing, and completed.
     * Each appointment in a non-empty category is printed. If a category has no appointments, a message is displayed
     * indicating the absence of appointments in that category.
     *
     * Method accesses the `AppointmentList` array from the database to retrieve appointments by status.
     */
    private void viewAppointments(){
        AppointmentList[] allAppointments = this.database.getAllAppointments();
        AppointmentList allPending = allAppointments[0];
        AppointmentList allOngoing = allAppointments[1];
        AppointmentList allCompleted = allAppointments[2];

        if (allPending.getCount() == 0) {
            System.out.println("There is no pending appointments\n");
        } else {
            for (Appointment apt : allPending) {
                apt.print();
            }
        }


        if (allOngoing.getCount() == 0) {
            System.out.println("There is no ongoing appointments\n");
        } else {
            for (Appointment apt : allOngoing) {
                apt.print();
            }

        }


        if (allCompleted.getCount() == 0) {
            System.out.println("There is no completed appointments\n");
        } else {
            for (Appointment apt : allCompleted) {
                apt.print();
            }

        }
        Input.ScanString("Enter to continue...");
    }

    /**
     * Displays a list of all the medicine in the pharmacy.
     * Each medicine is printed with its name, minimum stock, current stock  and pending stock
     */
    private void viewMedicationInventory(){
        this.pharmacy.viewStock();
        Input.ScanString("Enter to continue...");
    }

    /**
     * Approves replenishment requests for pharmacy stock. The method displays pending restock requests
     * and allows the user to approve specific requests, marking them as fulfilled.
     *
     * If there are no pending restock requests, a message is displayed, and the method exits.
     * Otherwise, the method prompts the user to select a specific request to approve.
     * The request is then marked as approved with the current administrator's ID, and
     * it is removed from the pending list.
     *
     * <p><strong>Steps performed:</strong></p>
     * <ul>
     *   <li>Checks the number of pending restock requests in the pharmacy. If there are none,
     *       displays a message and exits.</li>
     *   <li>Prompts the user to select a request by index to approve.</li>
     *   <li>Marks the chosen request as approved using the administrator's ID.</li>
     *   <li>Updates the pharmacy's records to reflect the approved request.</li>
     * </ul>
     *
     * <p><strong>Note:</strong> If the user enters an invalid choice (a number outside the valid range),
     * the method displays an error message.</p>
     */
    private void ApproveReplenishmentRequests(Administrator administrator){
        int choice;
        int size = this.pharmacy.viewRestockRequest();
        if (size == 0) {
            Input.ScanString("No existing request\nEnter to continue...");
            return;
        }
        do {
            choice = Input.ScanInt("Which restock do you want to fulfill");
            if (choice > this.pharmacy.viewRestockRequest()){
                System.out.println("Invalid option please try again");
                continue;
            }
            break;
        }while(true);
        RestockRequest request = this.pharmacy.getRestockRequest(choice - 1);
        request.ApprovedRequest(administrator.getID());
        this.pharmacy.approveRestock(choice - 1);

    }

    /**
     * Adds a new staff member to the hospital system. The method prompts the user for the role,
     * name, gender, and date of birth of the new staff member and creates an instance of the appropriate
     * subclass (Doctor, Pharmacist, or Administrator) based on the selected role.
     *
     * @return A new {@code BasePerson} object representing the staff member,
     *         which is a {@code Doctor}, {@code Pharmacist}, or {@code Administrator}
     *         based on the user's input. Returns {@code null} if an invalid role is entered.
     *
     * User input is taken for the following:
     * <ul>
     *   <li>Role: An integer to select the staff member's role (1 for Doctor, 2 for Pharmacist, 3 for Administrator).</li>
     *   <li>Name: A string representing the name of the staff member.</li>
     *   <li>Gender: A boolean value where {@code true} represents female, and {@code false} represents male.</li>
     *   <li>Date of Birth (DOB): The date of birth, with prompt customized based on gender.</li>
     * </ul>
     */
    private BasePerson addStaff(){
        int role;
        do {
            role = Input.ScanInt("What is the role of the new Staff?\n" +
                    "1. Doctor\n" +
                    "2. Pharmacist\n" +
                    "3. Administrator\n");
            if (role >3 ){
                System.out.println("Invalid option please try again");
                continue;
            }
            break;
        }while(true);
        String name = Input.ScanString("What is the name of the new Staff?");
        Boolean Gender = Input.ScanBoolean("Is the staff a female?");
        Date DOB = Gender ? Input.ScanDate("What is her Date of Birth") :
                Input.ScanDate("What is his Date of Birth");

        switch (role) {
            case 1:
                return new Doctor(name, DOB, Gender);
            case 2:
                return new Pharmacist(name, DOB, Gender);
            case 3:
                return new Administrator(name, DOB, Gender);
        }
        return null;
    }

    /**
     * Removes a specified staff member (Doctor, Pharmacist, or Administrator) from the hospital database.
     * <p>
     * This method displays a list of the specified type of staff members currently in the hospital and allows
     * the user to choose one to remove. All appointments associated with the selected staff member, if applicable,
     * are also canceled.
     * </p>
     * <p><strong>Steps performed:</strong></p>
     * <ul>
     *     <li>Prompts the user to select a staff category (Doctor, Pharmacist, or Administrator).</li>
     *     <li>Checks if there are any staff members of the selected type available to remove. If none,
     *     displays a message and exits.</li>
     *     <li>Displays a list of staff members within the chosen category, prompting the user to select one to remove.</li>
     *     <li>For a selected Doctor, cancels all pending and ongoing appointments associated with that doctor.</li>
     *     <li>Removes the selected staff member from the hospital database.</li>
     * </ul>
     *
     * <p><strong>Returns:</strong></p>
     * <ul>
     *     <li>The removed staff member as a {@code BasePerson} object, or {@code null} if no staff member was removed.</li>
     * </ul>
     *
     * <p><strong>Notes:</strong></p>
     * <ul>
     *     <li>If an invalid selection is made, a message is displayed, and the prompt is re-displayed until a valid choice is made.</li>
     *     <li>The method will keep prompting the user to select an option until a valid choice is provided.</li>
     * </ul>
     */
    private BasePerson removeStaff() {
        int choice1, choice2, i = 1;
        int index = 1;
        do {
            choice1 = Input.ScanInt("Do you want to fire a\n" +
                    "1. Doctor\n" +
                    "2. Pharmacist\n" +
                    "3. Administrator");
            switch (choice1) {
                case 1:
                    ArrayList<Doctor> docList = this.database.getDoctors();
                    if (docList.isEmpty()) {
                        System.out.println("There aren't any doctors left in the Hospital");
                        return null;
                    }
                    for (Doctor doc : docList) {
                        System.out.println(index + ": " + "Name: " + doc.getName() + ", ID: " + doc.getID());
                        index++;
                    }
                    choice2 = Input.ScanInt("Choose which doctor to remove\n");
                    for (Doctor doc : docList) {
                        if (i == choice2) {
                            System.out.println("This doctor is to be removed, " + doc.getName() + " with an ID of " + doc.getID());
                            AppointmentList temp = doc.getOngoingApt();
                            for (Appointment toRemove : temp) {
                                System.out.println("The upcoming appointment for " + toRemove.getPatientName() + " has been removed as well!");
                                this.database.cancelApt(toRemove);
                            }
                            temp = doc.getPendingApt();
                            for (Appointment toRemove : temp) {
                                System.out.println("The pending appointmeent for " + toRemove.getPatientName() + " has been removed as well!");
                                this.database.cancelApt(toRemove);
                            }
                            this.database.getDoctors().remove(doc);
                            return doc;
                        }
                        i++;
                    }
                    break;
                case 2:
                    ArrayList<Pharmacist> pharList = this.database.getPharmacists();
                    if (pharList.isEmpty()) {
                        System.out.println("There aren't any pharmacist left in the hospital");
                        return null;
                    }
                    for (Pharmacist ph : pharList) {
                        System.out.println(index + ": " + "Name: " + ph.getName() + ", ID: " + ph.getID());
                        index++;
                    }
                    choice2 = Input.ScanInt("Choose which Pharmacist to remove\n");
                    for (Pharmacist ph : pharList) {
                        if (i == choice2) {
                            System.out.println("This pharmacist is to be removed\n");
                            this.database.getPharmacists().remove(ph);
                            return ph;
                        }
                        i++;
                    }
                    break;
                case 3:
                    ArrayList<Administrator> adList = this.database.getAdministrators();
                    if (adList.isEmpty()) {
                        System.out.println("There aren't any administrators left in the hospital");
                        return null;
                    }
                    for (Administrator ad : adList) {
                        System.out.println(index + ": " + "Name: " + ad.getName() + ", ID: " + ad.getID());
                        index++;
                    }
                    choice2 = Input.ScanInt("Choose which Administrator to remove\n");
                    for (Administrator ad : adList) {
                        if (i == choice2) {
                            System.out.println("This administrator is to be removed\n");
                            this.database.getAdministrators().remove(ad);
                            return ad;
                        }
                        i++;
                    }
                    break;
                default:
                    System.out.println("Invalid option please try again");
                    continue;
            }
        } while (true);
    }

}
