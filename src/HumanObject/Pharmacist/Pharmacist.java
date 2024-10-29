package HumanObject.Pharmacist;

import HumanObject.*;

import java.util.Date;

public class Pharmacist extends BasePerson {

    // Private


    // Public

    /**
     * To construct a pharmacist object
     * @param ID
     * @param Name
     * @param DOB
     * @param Gender
     */
    public Pharmacist(int ID, String Name, Date DOB, Boolean Gender) {
        super(ID, Name, DOB, Gender);
        this.role = ROLE.PHARMACIST;
    }
}
