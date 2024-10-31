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
    protected Boolean gender;
    protected ROLE role;

    private static final int IDLength = 6;
    private static final String[] IDPrefix = new String[] { "PA", "DR", "PH", "AD"};

    public BasePerson() {
        this.name = Input.ScanString("Enter name");
        this.DOB = DataSerialisation.DeserialiseDate(Input.ScanString("Enter date of birth in this format YYYY-MM-DD-HH-MM"));
        this.gender = Input.ScanBoolean("Is it male (1) or female (0)");
    }

    public BasePerson(int ID, String Name, Date DOB, Boolean Gender){
        this.ID = ID;
        this.name = Name;
        this.DOB = DOB;
        this.gender = Gender;
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
