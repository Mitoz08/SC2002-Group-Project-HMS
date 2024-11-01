package HumanObject.Administrator;

import HumanObject.BasePerson;
import HumanObject.ROLE;

import java.util.Date;

public class Administrator extends BasePerson {
    public Administrator (int ID, String Name, Date DOB, Boolean Gender){
        super(ID,Name,DOB,Gender);
        this.role = ROLE.ADMINISTRATOR;

    }
    public int getID(){
        return this.ID;
    }
    public String getName(){
        return this.name;
    }
    public Boolean getGender(){
        return this.gender;
    }
    public Date getDOB(){
        return this.DOB;
    }

    //function to add a new patient and increment
    // but why so long winded

    public void addPatient(){

    }
    public void addStaff(){

    }
    public void fireStaff(){

    }

}
