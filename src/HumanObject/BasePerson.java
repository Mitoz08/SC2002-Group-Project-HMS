package HumanObject;


import InputHandler.Input;
import Serialisation.DataSerialisation;

import java.util.Date;

/**
 * The {@code BasePerson} class represents a basic person entity with attributes
 * such as ID, name, date of birth (DOB), gender, and role.
 * It provides methods for accessing and modifying these attributes, as well as
 * generating a formatted ID string based on the role.
 */
public class BasePerson {

    /** The unique identifier for the person. */
    protected int ID;

    /** The name of the person. */
    protected String name;

    /** The date of birth of the person. */
    protected Date DOB;

    /** The gender of the person. {@code True} represents male, and {@code False} represents female. */
    protected Boolean gender; // True(Male) False(Female)

    /** The role of the person, represented by the {@code ROLE} enum. */
    protected ROLE role;

    /** The length of the formatted ID string. */
    private static final int IDLength = 6;

    /** The prefixes used for generating the ID string based on the role. */
    private static final String[] IDPrefix = new String[] { "PA", "DR", "PH", "AD"};

    /**
     * Default constructor that initializes a {@code BasePerson} object by prompting the user
     * for input values such as name, date of birth, and gender.
     * The gender is determined based on user input ('m' for male, 'f' for female).
     */
    public BasePerson() {
        this.name = Input.ScanString("Enter name:");
        this.DOB = DataSerialisation.DeserialiseDate(Input.ScanString("Enter date of birth in this format YYYY-MM-DD-HH-MM:"));
        while (true) {
            char c = Input.ScanChar("Is it male(m) or female(f):");
            if (c != 'm' && c != 'f') continue;
            this.gender = c == 'm';
        }
    }

    /**
     * Constructs a {@code BasePerson} object with the specified ID, name, date of birth, and gender.
     *
     * @param ID     The unique identifier for the person.
     * @param Name   The name of the person.
     * @param DOB    The date of birth of the person.
     * @param Gender The gender of the person. {@code True} for male, {@code False} for female.
     */
    public BasePerson(int ID, String Name, Date DOB, Boolean Gender){
        this.ID = ID;
        this.name = Name;
        this.DOB = DOB;
        this.gender = Gender;
    }

    /**
     * Returns the unique identifier of the person.
     *
     * @return The ID of the person.
     */
    public int getID() {return this.ID;}

    /**
     * Returns the name of the person.
     *
     * @return The name of the person.
     */
    public String getName() {return this.name;}

    /**
     * Returns the date of birth of the person.
     *
     * @return The date of birth of the person.
     */
    public Date getDOB() {return this.DOB;}

    /**
     * Returns the gender of the person.
     *
     * @return {@code True} if the person is male, {@code False} if the person is female.
     */
    public Boolean getGender() {return this.gender;}

    /**
     * Returns the role of the person.
     *
     * @return The role of the person as a {@code ROLE} enum.
     */
    public ROLE getRole() {return this.role;}


    /**
     * Sets the unique identifier of the person.
     *
     * @param ID The new ID of the person.
     */
    public void setID(int ID){
        this.ID = ID;
    }

    /**
     * Sets the name of the person.
     *
     * @param name The new name of the person.
     */
    public void setName(String name){
        this.name = name;
    }

    /**
     * Sets the date of birth of the person.
     *
     * @param date The new date of birth of the person.
     */
    public void setDOB(Date date){
        this.DOB = date;
    }

    /**
     * Sets the gender of the person.
     *
     * @param gender The new gender of the person. {@code True} for male, {@code False} for female.
     */
    public void setGender(Boolean gender){
        this.gender = gender;
    }


    /**
     * Generates and returns a formatted string representation of the person's ID.
     * The string is constructed using a prefix based on the role, followed by zero-padding
     * to match the required length of {@code IDLength} (6 characters).
     *
     * @return The formatted string representation of the ID.
     */
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
