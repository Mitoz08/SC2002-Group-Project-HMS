package ObjectUI;

import DataObject.PharmacyObjects.MedicineRequest;
import DataObject.Prescription.MED_STATUS;
import DataObject.Prescription.Prescription;
import DepartmentObject.Pharmacy;
import DepartmentObject.UserInfoDatabase;
import HumanObject.Doctors.Doctors;
import DataObject.Appointment.Appointment;
import HumanObject.Patient.Patient;
import HumanObject.ROLE;
import InputHandler.Input;

public class DoctorUI {
    private UserInfoDatabase database;
    Doctors doctor;
    Pharmacy pharmacy;
    int choice;

    public DoctorUI(Doctors doctor){
        this.doctor=doctor;

        do{
            Input.ClearConsole();
            System.out.println("Pharmacist UI \n" +
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
                    System.out.println("Which Patients record do you want to view?");
                    viewPatient();
                    break;
                case 2:
                    System.out.println("Which Patient record do you want to update?");
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
        for(Appointment apt: doctor.getOngoingApt()){
            System.out.println("Patient ID: " + apt.getPatientID() + ", Patient Name: " + apt.getPatientName());
        }
        int choice = Input.ScanInt("Enter the Patient ID");
        Patient patient = (Patient) database.getPerson(choice, ROLE.PATIENT);
        System.out.println("Patient ID: " +  patient.getID());
        System.out.println("Patient name: " + patient.getName());
        System.out.println("Patient gender: " + patient.getGender());
        System.out.println("Date of birth: " + patient.getDOB());

        for(Appointment apt: doctor.getCompletedApt()){
            if(apt.getPatientID() == choice){
                apt.print(false);
            }
        }
        //choice = Input.ScanInt("Enter the appointment ID");

    }

    public void updatePatientMR(){
        Input.ClearConsole();
        for(Appointment apt: doctor.getOngoingApt()){
            System.out.println("Patient ID: " + apt.getPatientID() + ", Patient Name: " + apt.getPatientName());
        }
        int choice = Input.ScanInt("Enter the Patient ID");
        Patient patient = (Patient) database.getPerson(choice, ROLE.PATIENT);
        //update paitient medical record

        //database.update()
    }
    public void viewSchedule(){
        Input.ClearConsole();
        for(Appointment apt: doctor.getOngoingApt()){
            apt.print(false);
        }
    }

    public void setAvailability(){
        Input.ClearConsole();
        System.out.println(" Mon Tue Wed Thr Fri Sat Sun");
        for (int i=0; i<5; i++){
            System.out.print(i+1 + " ");
            for (int j=0; j<7; j++){
                System.out.print(doctor.getAvailability()[j][i]+ "  ");
            }
        }
        System.out.println("Which timings do you want to change your availability? ");
        int []dateSlot = new int[2];
        dateSlot[0] = Input.ScanInt("Choose the day: \n" +
                                            "0) Monday\n"+
                                            "1) Tuesday\n"+
                                            "2) Wednesday\n"+
                                            "3) Thursday\n"+
                                            "4) Friday\n"+
                                            "5) Saturday\n"+
                                            "6) Sunday\n");
        dateSlot[1] = Input.ScanInt("Choose the timing:\n" +
                                            "0) 10AM-11AM\n"+
                                            "1) 11AM-12AM\n"+
                                            "2) 1PM-2Pm\n"+
                                            "3) 2PM-3Pm\n"+
                                            "4) 3PM-4PM\n");
        int choice = Input.ScanInt("What do you want to change your availability to?\n"+
                                           "0) Not Available\n"+
                                           "1) Available\n");

        if(choice == 0){
            if(doctor.getAvailability()[dateSlot[0]][dateSlot[1]]){//if availability = true
                doctor.getAvailability()[dateSlot[0]][dateSlot[1]] = false;
            }
            else{
                System.out.println("It is already Unavailable");
            }
        }
        else if (choice == 1){
            if(!doctor.getAvailability()[dateSlot[0]][dateSlot[1]]){// if availability = false
                boolean i = true;
                for(Appointment apt: doctor.getOngoingApt()){
                    if(apt.getAptSlot()==dateSlot){// means doctor is trying to set availability to true when there is an ongoing appointment
                        System.out.println("There is an Ongoing appointment, you are unavailable at that timing ");
                        i = false;
                    }
                }
                if(i){// means there is no ongoing appointment with the same timing
                    doctor.getAvailability()[dateSlot[0]][dateSlot[1]] = true;
                }
            }
        }
        else{
            System.out.println("wrong option");
        }


    }
    public void aptReq(){
        Input.ClearConsole();
        viewOngoingAPT();
        String choice = Input.ScanString("Enter appointment ID to accept/decline ");
        int index = 0;
        for(Appointment apt: doctor.getPendingApt()){
            if(apt.getAppointmentID().equals(choice)){
                int option = Input.ScanInt("0) Decline\n"+
                        "1) Accept");
            if(option == 0){
                doctor.getPendingApt().removeAppointment(index);
                }
            else if(option ==1){
                doctor.getOngoingApt().addAppointment(apt);
                doctor.getPendingApt().removeAppointment(index);
                int[] dateSlot = apt.getAptSlot();
                doctor.getAvailability()[dateSlot[0]][dateSlot[1]]= true;
            }

            }
            index++;
        }
    }
    public void viewOngoingAPT(){
        Input.ClearConsole();
        for(Appointment apt: doctor.getPendingApt()){
            System.out.println("Patient ID: " + apt.getPatientID() + ", Patient Name: " + apt.getPatientName() + "Appointment ID: " + apt.getAppointmentID());
        }
    }

    public void recordAptOutcome(){
        Input.ClearConsole();
        Appointment apt = doctor.getOngoingApt().getAppointment(0);
        //String medicineName = Input.ScanString("Enter medicine name:");
        //int amt = Input.ScanInt("Enter medicine amt");
        Prescription prescription = new Prescription(); //= new Prescription(MED_STATUS.PENDING,medicineName,amt);
        MedicineRequest req = new MedicineRequest(apt.getPatientID(), apt.getDoctorID(), apt.getAppointmentID());
        pharmacy.requestMedicine(req);
        apt.getPrescriptionList().addPrescription(prescription);
        apt.setNotes(Input.ScanString("Enter Consultation notes"));
        apt.setNameOfApt(Input.ScanString("Enter type of service"));
    }

}
