package ObjectUI;

import DataObject.Appointment.Appointment;
import DataObject.PharmacyObjects.MedicineRequest;
import DataObject.Prescription.Prescription;
import DataObject.Prescription.PrescriptionList;
import DepartmentObject.Pharmacy;
import DepartmentObject.UserInfoDatabase;
import HumanObject.Pharmacist.Pharmacist;
import InputHandler.Input;
import Serialisation.DataSerialisation;

import java.util.ArrayList;

public class PharmacistUI {

    // Attribute
    private UserInfoDatabase database;
    private Pharmacy pharmacy;
    private Pharmacist pharmacist;

    public PharmacistUI(Pharmacy pharmacy, Pharmacist pharmacist) {
        this.pharmacy = pharmacy;
        this.pharmacist = pharmacist;

        int choice;

        do {
            Input.ClearConsole();
            System.out.println("Pharmacist UI \n" +
                    "1: View Appointment Outcome\n" +
                    "2: Update Prescription Status\n" +
                    "3: View Medication Inventory\n" +
                    "4: Submit Replenishment Request\n" +
                    "5: Logout");
            choice = Input.ScanInt("Choose an option:");

            int index;
            MedicineRequest request;

            PrescriptionList list;

            switch (choice) {
                case 1:

                    Input.ClearConsole();
                    pharmacy.viewMedRequest();
                    index = Input.ScanInt("Choose an request to view:") - 1;
                    request = pharmacy.getMedRequest(index);
                    Appointment appointment = DataSerialisation.DeserialiseAppointment("APT000001/0/Chemo/1001/001/2024-08-21-16-00/Empty/0-MedicineName1-10/0-MedicineName2-10"); // Get appointment from database using APT_ID/PatientID/DoctorID

                    list = appointment.getPrescriptionList();

                    if (printFulfillable(list)) break;

                    if (providePrescription(list)) continue;

                    Input.ClearConsole();
                    if (appointment.isPrescribed()) {
                        request.ApproveRequest(pharmacist.getID());
                        pharmacy.approveMedicine(index);
                        Input.ScanString("Request completed. \nPress enter to continue...");
                    } else {
                        Input.ScanString("Request not completed. \nPress enter to continue...");
                    }
                    break;
                case 2:

                    Input.ClearConsole();
                    request = pharmacy.getMedRequest(0);
                    appointment = DataSerialisation.DeserialiseAppointment("APT0000001/0/Chemo/1001/001/2024-08-21-16-00/Empty/0-MedicineName1-10/0-MedicineName2-10"); // Get appointment from database using APT_ID/PatientID/DoctorID

                    list = appointment.getPrescriptionList();

                    if (printFulfillable(list)) break;

                    if (providePrescription(list)) continue;

                    Input.ClearConsole();
                    if (appointment.isPrescribed()) {
                        request.ApproveRequest(pharmacist.getID());
                        pharmacy.approveMedicine(0);
                        Input.ScanString("Request completed. \nPress enter to continue...");
                    } else {
                        Input.ScanString("Request not completed. \nPress enter to continue...");
                    }
                    break;
                case 3:
                    pharmacy.viewStock();
                    break;
                case 4:
                    break;
                default:
                    break;
            }





        } while (choice < 5);

    }

    private boolean printFulfillable(PrescriptionList list) {
        Input.ClearConsole();
        for (Prescription o: list) {
                o.print();
                System.out.printf("|%-14s:%-14s|\n", "Fulfillable", pharmacy.checkFulfillable(o.getMedicineName(),o.getAmount()));
                System.out.println("_______________________________");
            }
        if (!Input.ScanBoolean("Do you want to prescribe the medicine?")) {
            Input.ScanString("Press enter to exit...");
            return true;
        }
        return false;
    }

    private boolean providePrescription(PrescriptionList list) {
        Input.ClearConsole();
        ArrayList<Prescription> prescriptions = new ArrayList<>();
        Prescription temp;
        System.out.println("Dispensable:");
        System.out.printf("%-4s%-21s%-5s\n","No.","Medicine","Amt");
        for (Prescription o: list) {
            if (o.isPrescribed()) continue;
            if (!pharmacy.checkFulfillable(o.getMedicineName(),o.getAmount())) continue;
            prescriptions.add(o);
        }
        while (!prescriptions.isEmpty()) {
            for (int i = 0; i < prescriptions.size(); i++) {
                temp = prescriptions.get(i);
                System.out.printf("%-3d:%-20s %-5d\n", i+1, temp.getMedicineName(), temp.getAmount());
            }
            System.out.printf("%-3d:Return\n", prescriptions.size()+1);
            int choice;
            while (true) {
                choice = Input.ScanInt("Enter index or -1 to fulfill all available:");
                if (choice > prescriptions.size() + 1) {
                    System.out.println("Invalid input");
                    continue;
                }
                break;
            }
            if (choice < 0) {
                while (!prescriptions.isEmpty()) {
                    temp = prescriptions.getFirst();
                    if (pharmacy.prescribeMedicine(temp.getMedicineName(),temp.getAmount())) temp.prescribed();
                    else System.out.println("Error dispensing medicine.");
                }
                Input.ScanString("Medicine prescribed. Press enter to return...");
                return true;
            } else if (choice == prescriptions.size() + 1) {
                Input.ScanString("Stopped dispensing medicine. Press enter to return...");
                return false;
            } else {
                temp = prescriptions.remove(choice-1);
                if (pharmacy.prescribeMedicine(temp.getMedicineName(),temp.getAmount())) {
                    temp.prescribed();
                }
                else System.out.println("Error dispensing medicine.");
            }
        }
        Input.ScanString("Medicine prescribed. Press enter to return...");
        return false;
    }

}
