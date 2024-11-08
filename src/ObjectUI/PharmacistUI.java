package ObjectUI;

import DataObject.Appointment.Appointment;
import DataObject.Appointment.AppointmentList;
import DataObject.PharmacyObjects.MedicineData;
import DataObject.PharmacyObjects.MedicineRequest;
import DataObject.PharmacyObjects.RestockRequest;
import DataObject.Prescription.Prescription;
import DataObject.Prescription.PrescriptionList;
import HumanObject.Patient.Patient;
import HumanObject.Pharmacist.Pharmacist;
import HumanObject.ROLE;
import InputHandler.Input;
import Serialisation.DataSerialisation;

import java.util.ArrayList;
import java.util.HashMap;

public class PharmacistUI implements BaseUI {

    // Attribute
    private Pharmacist pharmacist;

    public PharmacistUI(Pharmacist pharmacist) {
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
            Patient p;
            MedicineRequest request;
            PrescriptionList list;
            Appointment appointment = null;
            AppointmentList appointments;

            switch (choice) {
                case 1:
                    Option1();
                    break;
                case 2:
                    Option2();
                    break;
                case 3:
                    Option3();
                    break;
                case 4:
                    Option4();
                    break;
                default:
                    break;
            }
        } while (choice < 5);

    }

    private void Option1() {
        Input.ClearConsole();
        int index;
        MedicineRequest request;
        Patient p;
        AppointmentList appointments;
        Appointment appointment = null;
        PrescriptionList prescriptions;

        int size = pharmacy.viewMedRequest();
        if (size == 0) {
            Input.ScanString("No existing request\nEnter to continue...");
            return;
        }
        do {
            index = Input.ScanInt("Choose an request to view:") - 1;
            if (index >= size) System.out.println("Incorrect index.");
        } while (index >= size);
        request = pharmacy.getMedRequest(index);

        // Waiting on Database function to fetch appointment
        p = (Patient) database.getPerson(request.getPatientID(), ROLE.PATIENT);
        if (p == null) {
            System.out.println("Error getting Patient object in PharmacistUI");
            return;
        }
        appointments = p.getCompleted();
        for (Appointment apt : appointments) {
            if (apt.getAppointmentID().equals(request.getAppointmentID())) appointment = apt;
        }

        if (appointment == null) {
            System.out.println("Appointment does not exist!");
            return;
        }
//                    appointment = DataSerialisation.DeserialiseAppointment("APT000001/0/Chemo/1001/001/2024-08-21-16-00/Empty/0-MedicineName1-10/0-MedicineName2-10");

        prescriptions = appointment.getPrescriptionList();

        if (printFulfillable(prescriptions)) return;

        providePrescription(prescriptions);

        Input.ClearConsole();
        if (appointment.isPrescribed()) {
            request.ApproveRequest(pharmacist.getID());
            pharmacy.approveMedicine(index);
            Input.ScanString("Request completed. \nPress enter to continue...");
        } else {
            Input.ScanString("Request not completed. \nPress enter to continue...");
        }
    }

    private void Option2() {
        Input.ClearConsole();
        MedicineRequest request;
        Patient p;
        AppointmentList appointments;
        Appointment appointment = null;
        PrescriptionList prescriptions;

        request = pharmacy.getMedRequest(0);
        if (request == null) {
            Input.ScanString("No existing request\nEnter to continue...");
            return;
        }
        //appointment = DataSerialisation.DeserialiseAppointment("APT000001/0/Chemo/1001/P_Name/001/D_Name/2024-08-21-16-00/Empty/0-MedicineName1-10/0-MedicineName2-10"); // Get appointment from database using APT_ID/PatientID/DoctorID
        p = (Patient) database.getPerson(request.getPatientID(), ROLE.PATIENT);
        if (p == null) {
            System.out.println("Error getting Patient object in PharmacistUI");
            return;
        }
        appointments = p.getCompleted();
        for (Appointment apt : appointments) {
            if (apt.getAppointmentID().equals(request.getAppointmentID())) appointment = apt;
        }

        prescriptions = appointment.getPrescriptionList();

        if (printFulfillable(prescriptions)) return;

        providePrescription(prescriptions);

        Input.ClearConsole();
        if (appointment.isPrescribed()) {
            request.ApproveRequest(pharmacist.getID());
            pharmacy.approveMedicine(0);
            Input.ScanString("Request completed. \nPress enter to continue...");
        } else {
            Input.ScanString("Request not completed. \nPress enter to continue...");
        }
    }

    private void Option3() {
        Input.ClearConsole();
        pharmacy.viewStock();
        Input.ScanString("Press enter to continue...");
    }

    private void Option4() {
        Input.ClearConsole();
        pharmacy.requestRestock(createRestockReq());
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
                    if (pharmacy.prescribeMedicine(temp.getMedicineName(),temp.getAmount())) {
                        temp.prescribed();
                        prescriptions.removeFirst();
                    }
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

    private RestockRequest createRestockReq(){
        Input.ClearConsole();
        HashMap<Integer,Integer> indentStock = new HashMap<>();
        pharmacy.viewStock();
        while (true) {
            String medID = Input.ScanString("Enter the ID you want to indent (-1 to stop indenting):").toUpperCase();
            int ID = -1, amt;
            if (medID.length() != MedicineData.IDLength) {
                try {
                    if (Integer.valueOf(medID) == -1) break;
                    throw new Exception("Entered integer not equals to -1.");
                } catch (Exception e) {
                    System.out.println("Incorrect input");
                    continue;
                }
            } else {
                String prefix = medID.substring(0,3);
                String postfix = medID.substring(3);
                try {
                    if (!prefix.equals("MED")) {
                        throw new Exception("ID of medicine does not start with MED");
                    }
                    ID = Integer.parseInt(postfix);
                } catch (Exception e) {
                    System.out.println("Incorrect input");
                    continue;
                }
            }
            if (!pharmacy.checkExistingID(ID)) {
                System.out.println("ID does not exist.");
                continue;
            }
            amt = Input.ScanInt("How many do you want to indent for " + medID + ":");
            if (amt <= 0) {
                System.out.println("Not indented");
                continue;
            }
            indentStock.put(ID,amt);
            Input.ClearConsole();
            pharmacy.viewStock(indentStock);
        }
        if(indentStock.isEmpty()) {
            System.out.println("Nothing is indented.\nRequest not created");
            Input.ScanString("Enter to continue...");
            return null;
        }
        RestockRequest request = new RestockRequest(indentStock,pharmacist.getID());
        return request;
    }
}
