package ObjectUI;

import DataObject.Appointment.Appointment;
import DataObject.Appointment.AppointmentList;
import DataObject.PharmacyObjects.RestockRequest;
import DepartmentObject.Pharmacy;
import DepartmentObject.UserInfoDatabase;
import HumanObject.Administrator.Administrator;
import HumanObject.BasePerson;
import HumanObject.Doctors.Doctors;
import HumanObject.Pharmacist.Pharmacist;
import InputHandler.Input;
import Serialisation.DataSerialisation;

import java.util.ArrayList;
import java.util.Date;

public class AdminUI extends BaseUI{

    //Attribute
    private UserInfoDatabase database;
    private Pharmacy pharmacy;
    private Administrator admin;

    public AdminUI(UserInfoDatabase database, Pharmacy pharmacy, Administrator admin){
        this.pharmacy = pharmacy;
        this.database = database;
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
                            addStaff();
                            break;
                        case 2:
                            removeStaff();
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

        AppointmentList[] allAppointments = this.database.getAllAppointments();
        for (Appointment apt: allAppointments[0]){
            apt.print();
        }
        for (Appointment apt: allAppointments[1]){
            apt.print();
        }
        for (Appointment apt: allAppointments[2]){
            apt.print();
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
        BasePerson notReal = null;
        int role = Input.ScanInt("What is the role of the new Staff?\n" +
                                    "1. Doctor\n" +
                                    "2. Pharmacist\n" +
                                    "3. Administrator\n");
        String name = Input.ScanString("What is the name of the new Staff?\n");
        Boolean Gender = Input.ScanBoolean("Is the staff a female\n?");
        Date DOB = Gender? DataSerialisation.DeserialiseDate(Input.ScanString("What is her Date of Birth: in YYYY-MM-DD")+"-00-00\n"):
        DataSerialisation.DeserialiseDate(Input.ScanString("What is his Date of Birth: in YYYY-MM-DD")+"-00-00\n");

        switch(role){
            case 1:
                return new Doctors(name, DOB, Gender);
            case 2:
                return new Pharmacist(name, DOB, Gender);
            case 3:
                return new Administrator(name, DOB, Gender);
            default:
                return notReal;
        }
    }
    private void removeStaff(){
        int choice1,choice2;
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
                }
                choice2 = Input.ScanInt("Choose which doctor to remove");
                break;

        }


        return;
    }

}
