package ObjectUI;

import DepartmentObject.UserInfoDatabase;
import HumanObject.Doctors.Doctors;
import DataObject.Appointment.Appointment;
import HumanObject.Patient.Patient;
import HumanObject.ROLE;
import InputHandler.Input;

public class DoctorUI {
    private UserInfoDatabase database;
    Doctors doctor;
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
                    viewPaitient();
                    break;
                case 2:
                    System.out.println("Which Patient record do you want to update?");
                    updatePatientmr();
                    break;
                case 3:
                    viewSchedule();
                    break;
                case 4:
                    setAvailability();
                    break;
                case 5:




            }

        }while (choice < 8);

    }


    public void viewPaitient(){
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
        choice = Input.ScanInt("Enter the appointment ID");
        //to be continuoued

    }

    public void updatePatientmr(){
        Input.ClearConsole();
        viewPaitient();
        //database.updateapt()
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
        for (int i=0; i<7; i++){
            System.out.print(i+1 + " ");
            for (int j=0; j<5; j++){
                System.out.print(doctor.getAvailability()+ "  ");
            }
        }
        int choice = Input.ScanInt("Which Appointment do you want to change your availability? ");
    }
    public void aptReq(){
        for(Appointment apt: doctor.getPendingApt()){
            System.out.println("Patient ID: " + apt.getPatientID() + ", Patient Name: " + apt.getPatientName() + "Appointment ID: " + apt.getAppointmentID());
        }
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

            }

            }
            index++;
        }

    }

}
