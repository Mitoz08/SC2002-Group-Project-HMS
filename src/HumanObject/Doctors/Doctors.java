package HumanObject.Doctors;

import DataObject.Appointment.AppointmentList;
import HumanObject.BasePerson;
import HumanObject.ROLE;


import java.util.Date;

public class Doctors extends BasePerson {
    private static int lastID = 0;

    private AppointmentList Ongoing;
    private AppointmentList Completed;
    private AppointmentList Pending;
    private Boolean[][] availability = new Boolean[7][5];


    //This constructor is used for Initialising from TXT File
    public Doctors(int ID, String Name, Date DOB, Boolean Gender){
        super(ID, Name, DOB, Gender);
        this.role = ROLE.DOCTOR;
        for (int i=0; i<7; i++){
            for (int j=0; j<5; j++){
                this.availability[i][j] = true;

            }
        }
        this.Ongoing = new AppointmentList(true);
        this.Completed = new AppointmentList(false);
        this.Pending = new AppointmentList(true);

    }
    //This constructor is for adding a Doctor into TXT file
    public Doctors (String Name, Date DOB, Boolean Gender){
        super(lastID++, Name, DOB, Gender);
        this.role = ROLE.DOCTOR;
    }
    public static void setLastID(int ID) {lastID = ID;}
    public static int getLastID() {return lastID;}
    public AppointmentList getOngoingApt(){
        return Ongoing;
    }
    public AppointmentList getCompletedApt(){
        return Completed;
    }
    public AppointmentList getPendingApt(){
        return Pending;
    }
    public Boolean[][] getAvailability(){
        return availability;
    }

    public void toggleAvailability(int[] Slot, boolean b) {
        availability[Slot[0]][Slot[1]] = b;
    }
}


