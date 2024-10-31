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
     * To construct a pharmacist object with given inputs
     * @param Name
     * @param DOB
     * @param Gender
     */
    public Pharmacist(String Name, Date DOB, Boolean Gender) {
        super(lastID++, Name, DOB, Gender);
        this.role = ROLE.PHARMACIST;
    }

    /**
     * To be used during initialisation to ensure there is unique ID for each pharmacist
     * @param lastID1
     */
    public static void setLastID(int lastID1) {lastID = lastID1;}


}
