package HumanObject.Pharmacist;

import HumanObject.*;

import java.util.Date;

/**
 * A class that stores the details of a Pharmacist
 */
public class Pharmacist extends BasePerson {

    // Private
    private static int lastID = 0;

    // Public
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
     * @param ID lastID from the previous runtime
     */
    public static void setLastID(int ID) {lastID = ID;}

    /**
     * To be used during termination to ensure that lastID remains the same during the next runtime
     * @return lastID variable
     */
    public static int getLastID() {return lastID;}


}
