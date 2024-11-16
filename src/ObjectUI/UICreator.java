package ObjectUI;

import HumanObject.Administrator.Administrator;
import HumanObject.BasePerson;
import HumanObject.Doctor.Doctor;
import HumanObject.Patient.Patient;
import HumanObject.Pharmacist.Pharmacist;

/**
 * A class used to initialise a BaseUI with the given BasePerson object
 */
public class UICreator {

    /**
     * Method to create a new BaseUI
     * @param person User using the UI
     * @return the specific UI for the user
     */
    public static BaseUI createUI(BasePerson person) {
        switch (person.getRole()) {
            case PATIENT:
                return new PatientUI((Patient) person);
            case DOCTOR:
                return new DoctorUI((Doctor) person);
            case PHARMACIST:
                return new PharmacistUI((Pharmacist) person);
            case ADMINISTRATOR:
                return new AdminUI((Administrator) person);
//            case NURSE:
//                return new NurseUI((Nurse) person);
        }
        return null;
    }
}
