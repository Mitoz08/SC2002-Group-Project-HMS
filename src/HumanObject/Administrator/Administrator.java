package HumanObject.Administrator;

import DepartmentObject.UserInfoDatabase;
import HumanObject.BasePerson;
import HumanObject.Doctors.Doctors;
import HumanObject.Pharmacist.Pharmacist;
import HumanObject.ROLE;

import java.util.ArrayList;
import java.util.Date;

public class Administrator extends BasePerson {
    private static int lastID = 0;

    public Administrator() {
        super();
        this.ID = lastID++;
        this.role = ROLE.PHARMACIST;
    }
    //This constructor is used to add to TXT file
    public Administrator (String Name, Date DOB, Boolean Gender){
        super(lastID++,Name,DOB,Gender);
        this.role = ROLE.ADMINISTRATOR;

    }
    //This constructor is used to initialise it from TXT file
    public Administrator(int ID, String Name, Date DOB, Boolean Gender){
        super(ID,Name,DOB,Gender);
        this.role = ROLE.ADMINISTRATOR;
    }

    public static void setLastID(int lastID1){
        lastID = lastID1;
    }

    public void addPatient(){
        //Uses updateFile
        //remember to update number of Patients

    }
    public void addStaff(){
        //Uses updateFile
        //remember to add number of staff(Doctors, Pharmacists, Administrator respectively)

    }
    public void fireStaff(){
        //Uses updateFile
        //remember to add number of staff(Doctors, Pharmacists, Administrator respectively)
    }

}
