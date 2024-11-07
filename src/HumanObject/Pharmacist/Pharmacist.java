package HumanObject.Pharmacist;

import DataObject.Appointment.APT_STATUS;
import DataObject.Prescription.PrescriptionList;
import HumanObject.*;
import Serialisation.DataSerialisation;

import java.util.Date;
import java.util.Scanner;

public class Pharmacist extends BasePerson {

    // Private
    private static int lastID = 0;

    // Public

    /**
     * To construct a pharmacist object and prompts input
     */
    public Pharmacist() {
        super();
        this.ID = lastID++;
        this.role = ROLE.PHARMACIST;
    }

    /**
     * To construct a pharmacist object with given inputs (Used for administrator to create new pharmacist account)
     * @param Name
     * @param DOB
     * @param Gender
     */
    public Pharmacist(String Name, Date DOB, Boolean Gender) {
        super(lastID++, Name, DOB, Gender);
        this.role = ROLE.PHARMACIST;

    }

    /**
     * To construct a pharmacist object given inputs (Used for loading in from save file)
     * @param ID
     * @param Name
     * @param DOB
     * @param Gender
     */
    public Pharmacist(int ID, String Name, Date DOB, Boolean Gender){
        super(ID,Name,DOB,Gender);
        this.role = ROLE.PHARMACIST;
        //SAVED in HMS.txt as PH*ID*NAME*DOB*GENDER, used for Initialising from HMS.txt
    }


    /**
     * To be used during initialisation to ensure there is unique ID for each pharmacist
     * @param ID
     */
    public static void setLastID(int ID) {lastID = ID;}

    public static int getLastID() {return lastID;}


}
