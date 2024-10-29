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
        return this.Name;
    }
    public Boolean getGender(){
        return this.Gender;
    }
    public Date getDOB(){
        return this.DOB;
    }

}
