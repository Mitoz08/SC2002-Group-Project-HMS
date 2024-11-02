package HumanObject.Doctors;

import DataObject.Appointment.AppointmentList;
import HumanObject.BasePerson;
import HumanObject.ROLE;

import java.util.Date;

public class Doctors extends BasePerson {
    private static int lastID = 0;

    private AppointmentList Ongoing;
    private AppointmentList Completed;
    private Boolean[][] availability = new Boolean[7][5];


    public Doctors(int ID, String Name, Date DOB, Boolean Gender){
        super(ID, Name, DOB, Gender);
        this.role = ROLE.DOCTOR;
        for (int i=0; i<7; i++){
            for (int j=0; j<5; j++){
                this.availability[i][j] = true;

            }
        }

    }
    public static void setLastID(int lastID1){
        lastID = lastID1;
    }



}

