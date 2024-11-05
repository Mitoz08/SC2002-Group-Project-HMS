package HumanObject;

import DataObject.Prescription.PrescriptionList;

import InputHandler.Input;
import Serialisation.DataSerialisation;

import java.util.Date;
import java.util.Scanner;

public class BasePerson {
    protected int ID;
    protected String name;
    protected Date DOB;
    protected Boolean gender; // True(Male) False(Female)
    protected ROLE role;

    private static final int IDLength = 6;
    private static final String[] IDPrefix = new String[] { "PA", "DR", "PH", "AD"};

    public BasePerson() {
        this.name = Input.ScanString("Enter name:");
        this.DOB = DataSerialisation.DeserialiseDate(Input.ScanString("Enter date of birth in this format YYYY-MM-DD-HH-MM:"));
        while (true) {
            char c = Input.ScanChar("Is it male(m) or female(f):");
            if (c != 'm' && c != 'f') continue;
            this.gender = c == 'm';
        }
    }

    public BasePerson(int ID, String Name, Date DOB, Boolean Gender){
        this.ID = ID;
        this.name = Name;
        this.DOB = DOB;
        this.gender = Gender;
    }

    public int getID() {return this.ID;}
    public String getName() {return this.name;}
    public Date getDOB() {return this.DOB;}
    public Boolean getGender() {return this.gender;}
    public ROLE getRole() {return this.role;}

    public void setID(int ID){
        this.ID = ID;
    }
    public void setName(String name){
        this.name = name;
    }
    public void setDOB(Date date){
        this.DOB = date;
    }
    public void setGender(Boolean gender){
        this.gender = gender;
    }



    public String getStrID() {
        StringBuilder str = new StringBuilder();
        String IDStr = String.valueOf(this.ID);

        str.append(IDPrefix[role.ordinal()]);
        for (int i = 0; i < IDLength-IDPrefix[role.ordinal()].length()-IDStr.length(); i++) {
            str.append('0');
        }
        str.append(IDStr);
        return str.toString();
    }
}
