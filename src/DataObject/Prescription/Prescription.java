package DataObject.Prescription;

import InputHandler.Input;
import Singleton.ServerHMS;

import java.util.Scanner;

/**
 * A class to store the prescription of medicine
 */
public class Prescription implements Comparable<Prescription> {


    // Private attributes
    /** Enumeration for the status of the Prescription. */
    private MED_STATUS status;

    /** The name of the medicine. */
    private String medicineName;

    /** The amount of medicine. */
    private int amount;

    // Constructor
    /**
     * Constructs a {@code prescription} object and prompts input (Used for creating a new prescription)
     */
    public Prescription () {
        this.status = MED_STATUS.PENDING;
        do {
            this.medicineName = Input.ScanString("Enter name of medicine: ");
            if (ServerHMS.getInstance().getPharmacy().isMedicine(this.medicineName)) break;
            System.out.println("Medicine does not exist.");
        } while (!ServerHMS.getInstance().getPharmacy().isMedicine(this.medicineName));
        this.amount = Input.ScanInt("Enter the amount: ");
    }

    /**
     * Constructs a {@code prescription} with given input (Used for loading in from file)
     * @param status Prescribed or not
     * @param medicineName Name of medicine
     * @param amount Amount of medicine
     */
    public Prescription (MED_STATUS status ,String medicineName, int amount) {
        this.status = status;
        this.medicineName = medicineName;
        this.amount = amount;
    }

    // Getters

    /**
     * Gets the medicine name of the prescription
     * @return the medicine name of the prescription
     */
    public String getMedicineName() {return this.medicineName;}

    /**
     * Gets the amount of medicine being prescribed
     * @return the amount of medicine being prescribed
     */
    public int getAmount() {return this.amount;}

    /**
     * Gets the Status of the prescription Pending or Prescribed
     * @return the Status of the prescription
     */
    public MED_STATUS getStatus() {return this.status;}

    /**
     * Prints out a formatted prescription block
     * <p>Prints:</p>
     * <ul>
     *     <li> Medicine name</li>
     *     <li> Medicine amount</li>
     *     <li> Prescribe status</li>
     * </ul>
     */
    public void print() {
        System.out.println("┌─────────────────────────────┐");
        System.out.printf("│%-8s:%-20s│\n", "Medicine", this.medicineName);
        System.out.printf("│%-8s:%-20d│\n", "Amount", this.amount);
        System.out.printf("│%-8s:%-20s│\n", "Status", this.status);
        System.out.println("└─────────────────────────────┘");
    }

    /**
     * Sets status to PRESCRIBED
     */
    public void prescribed() {
        this.status = MED_STATUS.PRESCRIBED;
        System.out.printf("Prescribed %d %s\n",this.amount,this.medicineName);
        // More code can be added if necessary
    }

    /**
     * Checks if medicine is prescribed
     * @return {@code true} if prescribed
     */
    public boolean isPrescribed() {
        return this.status == MED_STATUS.PRESCRIBED;
    }

    /**
     * Compares the current prescription with the argument prescription
     * <ul>
     *     <li>when current medicine is smaller than the argument medicine (alphabetically) returns less than 0</li>
     *     <li>when current medicine is larger than the argument medicine (alphabetically) returns more than 0</li>
     *     <li>when current medicine is equal to the argument medicine, returns the difference in the amount</li>
     * </ul>
     * @param o the object to be compared.
     * @return an {@code Integer} base on the comparison
     */
    @Override
    public int compareTo(Prescription o) {
        if (this.medicineName.compareTo(o.getMedicineName()) == 0) {
            return this.amount - o.amount;
        }
        return this.medicineName.compareTo(o.getMedicineName());
    }

    // Private method
}
