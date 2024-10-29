package HumanObject;

import java.util.Date;

public class BasePerson {
    protected int ID;
    protected String Name;
    protected Date DOB;
    protected Boolean Gender;
    protected ROLE role;

    public BasePerson(int ID, String Name, Date DOB, Boolean Gender){
        this.ID = ID;
        this.Name= Name;
        this.DOB = DOB;
        this.Gender = Gender;
    }
}
