package ObjectUI;

import DataObject.Appointment.Appointment;
import DataObject.Appointment.AppointmentList;
import DepartmentObject.*;
import HumanObject.Administrator.Administrator;
import HumanObject.BasePerson;
import HumanObject.Doctors.Doctors;
import HumanObject.Pharmacist.Pharmacist;
import HumanObject.ROLE;
import InputHandler.Input;
import Serialisation.DataSerialisation;
import Singleton.ServerHMS;

import java.util.ArrayList;
import java.util.Date;

public class AdminUI implements BaseUI{

    //Attribute
    private AccountInfoDatabase login;
    private Administrator admin;

    public AdminUI(Administrator admin){
        this.login = ServerHMS.getInstance().getLogin();
        this.admin = admin;

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
                            login.addNewAccount(toAdd.getName(),toAdd.getStrID());
                            switch (role){
                                case DOCTOR:
                                    this.database.getDoctors().add((Doctors) toAdd);
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
                            this.database.addToUserInfoDatabaseFile(toAdd);
                            System.out.println("The Staff has been added to the Database\n");
                            break;
                        case 2:
                            BasePerson toRemove = removeStaff();
                            this.database.toRemoveFromUserDatabaseFile(toRemove);
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
                    ApproveReplenishmentRequests();
                    break;
                case 6: //Logging out of Admin Account
                    Input.ClearConsole();
                    System.out.println("Logging out of Administrator");
                    break;
                default:
                    Input.ClearConsole();
                    System.out.println("Not the right option. Please select correctly");
                    break;
            }

        }while (choice!=6);

    }
    private void viewHospitalStaff(){
        int numOfStaff = 1;
        ArrayList<Doctors> doctors = this.database.getDoctors();
        ArrayList<Pharmacist> pharmacists = this.database.getPharmacists();
        ArrayList<Administrator> administrators = this.database.getAdministrators();
        for (Doctors doc: doctors){
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
        return;
    }
    private void viewAppointments(){

        if (database.getAllAppointments() == null){
            System.out.println();
        }
        AppointmentList[] allAppointments = this.database.getAllAppointments();
        AppointmentList allPending = allAppointments[0];
        AppointmentList allOngoing = allAppointments[1];
        AppointmentList allCompleted = allAppointments[2];

        if (allPending != null) {
            for (Appointment apt : allPending) {
                apt.print();
            }
        }
        else if (allPending == null) {
            System.out.println("The Pending list is empty\n");
        }

        if (allOngoing != null) {
            for (Appointment apt : allOngoing) {
                apt.print();
            }
        }
        else if (allOngoing == null){
            System.out.println("The Ongoing List is empty\n");
        }
        if (allCompleted != null) {
            for (Appointment apt : allCompleted) {
                apt.print();
            }
        }
        else if (allCompleted == null){
            System.out.println("The Completed List is empty\n");
        }
        return;


    }
    private void viewMedicationInventory(){
        this.pharmacy.viewStock();
    }
    private void ApproveReplenishmentRequests(){

        this.pharmacy.viewRestockRequest();
        int choice = Input.ScanInt("Which restock do you want to fulfill");
        this.pharmacy.approveRestock(choice-1);

        return;

    }
    private BasePerson addStaff(){

        int role = Input.ScanInt("What is the role of the new Staff?\n" +
                                    "1. Doctor\n" +
                                    "2. Pharmacist\n" +
                                    "3. Administrator\n");
        String name = Input.ScanString("What is the name of the new Staff?\n");
        Boolean Gender = Input.ScanBoolean("Is the staff a female\n?");
        Date DOB = Gender? DataSerialisation.DeserialiseDate(Input.ScanString("What is her Date of Birth: in YYYY-MM-DD")):
        DataSerialisation.DeserialiseDate(Input.ScanString("What is his Date of Birth: in YYYY-MM-DD"));

        switch(role){
            case 1:
                return new Doctors(name, DOB, Gender);
            case 2:
                return new Pharmacist(name, DOB, Gender);
            case 3:
                return new Administrator(name, DOB, Gender);
            default:
                return null;
        }
    }
    private BasePerson removeStaff(){
        int choice1,choice2, i=1;
        int index=1;
        choice1 = Input.ScanInt("Do you want to fire a\n " +
                                    "1. Doctor\n" +
                                    "2. Pharmacist\n" +
                                    "3. Administrator");
        switch (choice1){
            case 1:
                ArrayList<Doctors> docList = this.database.getDoctors();
                for (Doctors doc: docList){
                    System.out.println(index + ": " + "Name: " + doc.getName() + ", ID: " + doc.getID());
                    index++;
                }
                choice2 = Input.ScanInt("Choose which doctor to remove\n");
                for (Doctors doc: docList){
                    if (i == choice2){
                        System.out.println("This doctor is to be removed\n" + doc.getName());
                        this.database.getDoctors().remove(doc);
                        return doc;
                    }
                    i++;
                }
                break;
            case 2:
                ArrayList<Pharmacist> pharList = this.database.getPharmacists();
                for (Pharmacist ph: pharList){
                    System.out.println(index + ": " + "Name: " + ph.getName() + ", ID: " + ph.getID());
                    index++;
                }
                choice2 = Input.ScanInt("Choose which Pharmacist to remove\n");
                for (Pharmacist ph: pharList){
                    if (i == choice2){
                        System.out.println("This pharmacist is to be removed\n");
                        this.database.getPharmacists().remove(ph);
                        return ph;
                    }
                    i++;
                }
                break;
            case 3:
                ArrayList<Administrator> adList = this.database.getAdministrators();
                for (Administrator ad: adList){
                    System.out.println(index + ": " + "Name: " + ad.getName() + ", ID: " + ad.getID());
                    index++;
                }
                choice2 = Input.ScanInt("Choose which Administrator to remove\n");
                for (Administrator ad:adList){
                    if (i == choice2){
                        System.out.println("This administrator is to be removed\n");
                        this.database.getAdministrators().remove(ad);
                        return ad;
                    }
                    i++;
                }
                break;
            default:
                return null;
        }


        return null;
    }

}
