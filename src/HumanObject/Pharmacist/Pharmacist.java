package HumanObject.Pharmacist;

import HumanObject.*;

import java.util.Date;

/**
 * A class that stores the details of a Pharmacist
 */
public class Pharmacist extends BasePerson {

    // Private
    /** A static field to keep track of the last assigned pharmacist ID.*/
    private static int lastID = 0;

    // Public
    /**
     * Constructs a {@code Pharmacist} object with the specified name, date of birth, and gender.
     * The ID is automatically assigned based on the current value of {@code lastID}, which is then incremented.
     * The role is set to {@code ROLE.PHARMACIST}.
     *
     * @param Name   The name of the pharmacist.
     * @param DOB    The date of birth of the pharmacist.
     * @param Gender The gender of the pharmacist. {@code True} for male, {@code False} for female.
     */
    public Pharmacist(String Name, Date DOB, Boolean Gender) {
        super(lastID++, Name, DOB, Gender);
        this.role = ROLE.PHARMACIST;
    }

    /**
     * Constructs a {@code Pharmacist} object with the specified ID, name, date of birth, and gender.
     * This constructor is used for initializing a {@code Pharmacist} object from a saved record in the text file {@code HMS.txt}.
     * The role is set to {@code ROLE.PHARMACIST}.
     *
     * @param ID     The unique identifier for the pharmacist.
     * @param Name   The name of the pharmacist.
     * @param DOB    The date of birth of the pharmacist.
     * @param Gender The gender of the pharmacist. {@code True} for male, {@code False} for female.
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
