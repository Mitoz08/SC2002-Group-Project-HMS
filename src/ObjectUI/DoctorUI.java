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


import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class DoctorUI implements BaseUI {
    private Doctors doctor;
    private int choice;

    public DoctorUI(Doctors doctor){
        this.doctor = doctor;

        do{
            Input.ClearConsole();
            System.out.println("Doctor UI \n" +
                    "1: View Patient Medical Record\n" +
                    "2: Update Patient Medical Records \n" +
                    "3: View Personal Schedule \n" +
                    "4: Set Availability for Appointments \n" +
                    "5: Accept or Decline Appointment Requests \n" +
                    "6: View Upcoming Appointments  \n" +
                    "7: Record Appointment Outcome \n"+
                    "8: Logout");
            choice = Input.ScanInt("Choose an option:");

            switch(choice){
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
        }while (choice < 8);
    }


    public void viewPatient(){
        Input.ClearConsole();
        int flag = 0;
        for(Appointment apt: doctor.getOngoingApt()){
            System.out.println("Patient ID: " + apt.getPatientID() + ", Patient Name: " + apt.getPatientName());
            flag=1;
        }
        if(flag == 0){
            System.out.println("There are no patients under you now. ");
        }
        else {
            int choice = Input.ScanInt("Enter the ID of the patient you want to view");
            Patient patient = (Patient) database.getPerson(choice, ROLE.PATIENT);
            patient.printMedicalRecord();

            for (Appointment apt : doctor.getCompletedApt()) {
                if (apt.getPatientID() == choice) {
                    apt.print(false);
                }
            }
        }
    }

    public void updatePatientMR(){
        Input.ClearConsole();
        int flag =0;
        for(Appointment apt: doctor.getOngoingApt()){
            System.out.println("Patient ID: " + apt.getPatientID() + ", Patient Name: " + apt.getPatientName());
            flag=1;
        }
        if(flag==1) {
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
        }
        else{
            System.out.println("There are no patients under you to update");
        }

    }
    public void viewSchedule(){
        Input.ClearConsole();

        System.out.println("  Mon   Tue   Wed   Thr   Fri   Sat   Sun");
        doctor.printFirstWeekTimeSlot();
        Input.ScanString("Enter to continue...");
    }

    public void setAvailability(){
        Input.ClearConsole();
        viewSchedule();
        System.out.println("Which date and time do you want to change your availability? ");
        Date date = Input.ScanFutureDate("Choose the date");

        int time = Input.ScanInt("Choose the timing:\n" +
                                        "1) 10AM-11AM\n" +
                                        "2) 11AM-12PM\n" +
                                        "3) 1PM-2PM\n" +
                                        "4) 2PM-3PM\n" +
                                        "5) 3PM-4PM\n") - 1;

        int choice = Input.ScanInt("What do you want to change your availability to?\n"+
                                           "1) Not Available\n"+
                                           "2) Available\n");
        if(doctor.getTimeSlot(date) == null) {
            if(choice == 1) {
                doctor.addTimeSlot(date, time);
            }
            else{
                System.out.println("Timing is already available");
            }
        }
        else {

            if (choice == 1) {//if choose not available
                if (doctor.getTimeSlot(date)[time]) {//if not available = true
                    System.out.println("Slot is already unavailable");
                }
                else {
                    doctor.addTimeSlot(date,time);
                }
            }
            else if (choice == 2) {
                if (doctor.getTimeSlot(date)[time]) {// if not available = true
                    for (Appointment apt : doctor.getOngoingApt()) {
                        if (apt.getAppointmentTime() == date) {// means doctor is trying to set availability to true when there is an ongoing appointment
                            //if(timing match) to be added
                            System.out.println("There is an Ongoing appointment, you are unavailable at that timing ");
                        }
                        else{
                            doctor.getTimeSlot(date)[time] = false;//set to aailable
                        }
                    }
                }
                else{
                    doctor.getTimeSlot(date)[time] = false;//set to aailable

                }
            }
        }
    }

    public void aptReq(){
        Input.ClearConsole();
        int flag = 0;
        for(Appointment apt: this.doctor.getPendingApt()){
            System.out.println("Patient ID: " + apt.getPatientID() + ", Patient Name: " + apt.getPatientName() + ", Appointment ID: " + apt.getAppointmentID());
            flag = 1;
        }
        if(flag == 1) {
            String choice = Input.ScanString("Enter appointment ID to accept/decline ");
            int index = 0;
            for (Appointment apt : doctor.getPendingApt()) {
                if (apt.getAppointmentID().equals(choice)) {
                    int option = Input.ScanInt("0) Decline\n" +
                                                       "1) Accept");
                    if (option == 0) {
                        database.docAcceptApt(apt, false);
                    } else if (option == 1) {
                        database.docAcceptApt(apt, true);
                        doctor.addTimeSlot(apt.getDate(), apt.getTimeSlot());
                    }
                }
                index++;
            }
        }
        else{
            System.out.println("There are currently no pending appointments");
        }

    }
    public void viewOngoingAPT(){
        Input.ClearConsole();
        int flag = 0;
        for(Appointment apt: this.doctor.getOngoingApt()){
            System.out.println("Patient ID: " + apt.getPatientID() + ", Patient Name: " + apt.getPatientName() + ", Appointment ID: " + apt.getAppointmentID());
            flag = 1;
        }
        if(flag == 0){
            System.out.println("There are currently no ongoing appointments");
        }
    }

    public void recordAptOutcome(){
        Input.ClearConsole();
        Appointment apt = doctor.getOngoingApt().getAppointment(0);
        if(apt == null){
            System.out.println("There are no Ongoing appointments to record outcome.");
            return;
        }
        boolean choice = Input.ScanBoolean("Do you want to give a prescription");
        HashMap<Integer, MedicineData> temp = pharmacy.getMedicine();
        while (choice && !temp.isEmpty()) {
            for (Map.Entry<Integer,MedicineData> o : temp.entrySet()) {
                System.out.println(o.getValue().getIDString() + ": " + o.getValue().getName());
            }
            Prescription prescription = new Prescription();
            int ID = pharmacy.convertNameToID(prescription.getMedicineName());
            temp.remove(ID);
            apt.getPrescriptionList().addPrescription(prescription);
            if (temp.isEmpty()) break;
            choice = Input.ScanBoolean("Do you still want to prescribe any more medicine.");
        }
        MedicineRequest req = new MedicineRequest(apt.getPatientID(), apt.getDoctorID(), apt.getAppointmentID());
        pharmacy.requestMedicine(req);
        apt.setNotes(Input.ScanString("Enter Consultation notes"));
//        apt.setNameOfApt(Input.ScanString("Enter type of service"));
        database.completeApt(apt);
    }
}
