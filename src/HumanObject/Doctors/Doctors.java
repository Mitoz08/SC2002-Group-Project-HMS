package HumanObject.Doctors;

import DataObject.Appointment.AppointmentList;
import HumanObject.BasePerson;
import HumanObject.ROLE;

import java.util.Date;

public class Doctors extends BasePerson {
    private static int lastID = 0;

    private AppointmentList Ongoing;
    private AppointmentList Completed;
    private Boolean[][] Availability = new Boolean[7][5];

    /*THIS IS A SPECIFIC CONSTRUCTOR TO INITIALISE FROM TXT FILE
    public Doctors(String Name, Date DOB, Boolean Gender, AppointmentList Ongoing, AppointmentList Completed, Boolean[][] Availability){
        super(lastID++, Name, DOB, Gender);
        this.role = ROLE.DOCTOR;
        this.Ongoing = Ongoing;
        this.Completed = Completed;
        this.Availability = Availability;
    }
    public static void setLastID(int lastID1){
        lastID = lastID1;
    }
     */

}

