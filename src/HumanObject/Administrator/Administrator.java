package HumanObject.Administrator;

import HumanObject.BasePerson;
import HumanObject.ROLE;

import java.util.Date;


/**
 * The {@code Administrator} class represents an administrator entity, which extends the {@code BasePerson} class.
 * It includes methods for managing patients and staff, and handles ID generation for administrator objects.
 */
public class Administrator extends BasePerson {

    /** A static field to keep track of the last assigned administrator ID.*/
    private static int lastID = 0;

    /**
     * Default constructor that initializes an {@code Administrator} object.
     * The ID is auto-incremented based on {@code lastID}.
     * The role is set to {@code ROLE.ADMINISTRATOR}.
     */
    public Administrator() {
        super();
        this.ID = lastID++;
        this.role = ROLE.PHARMACIST;
    }
    //This constructor is used to add to TXT file
    /**
     * Constructor used for creating an {@code Administrator} object and adding it to a text file.
     *
     * @param Name   The name of the administrator.
     * @param DOB    The date of birth of the administrator.
     * @param Gender The gender of the administrator. {@code True} for male, {@code False} for female.
     */
    public Administrator (String Name, Date DOB, Boolean Gender){
        super(lastID++,Name,DOB,Gender);
        this.role = ROLE.ADMINISTRATOR;

    }
    //This constructor is used to initialise it from TXT file
    /**
     * Constructor used for initializing an {@code Administrator} object from a text file.
     *
     * @param ID     The unique identifier of the administrator.
     * @param Name   The name of the administrator.
     * @param DOB    The date of birth of the administrator.
     * @param Gender The gender of the administrator. {@code True} for male, {@code False} for female.
     */
    public Administrator(int ID, String Name, Date DOB, Boolean Gender){
        super(ID,Name,DOB,Gender);
        this.role = ROLE.ADMINISTRATOR;
    }

    /**
     * Sets the last assigned ID for administrator objects.
     *
     * @param ID The ID to set as the last assigned ID.
     */
    public static void setLastID(int ID) {lastID = ID;}

    /**
     * Returns the last assigned ID for administrator objects.
     *
     * @return The last assigned ID.
     */
    public static int getLastID() {return lastID;}

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
