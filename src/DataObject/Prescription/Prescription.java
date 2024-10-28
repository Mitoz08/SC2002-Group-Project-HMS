package DataObject.Prescription;

import java.util.Scanner;

/**
 * A class to store the prescription of medicine
 */
public class Prescription implements Comparable<Prescription> {


    // Private attributes
    private MED_STATUS status;
    private String medicineName;
    private int amount;

    // Constructor

    /**
     * Creates prescription object and prompts input
     */
    public Prescription () {
        Scanner sc = new Scanner(System.in);
        this.status = MED_STATUS.PENDING;
        System.out.print("Enter name of medicine: ");
        this.medicineName = sc.nextLine();
        System.out.print("Enter the amount: ");
        this.amount = sc.nextInt();
    }

    /**
     * Creates prescription object with given input
     * @param status Prescribed or not
     * @param medicineName Name of medicine
     * @param amount Amount of medicine
     */
    public Prescription (MED_STATUS status ,String medicineName, int amount) {
        this.status = status;
        this.medicineName = medicineName;
        this.amount = amount;
    }

    /**
     *  Creates prescription object from serialised data
     * @param DataInput serialised data string (e.g. 0-MedicineName-10)
     */
    public Prescription (String DataInput) {
        String[] inputs = DataInput.split("[-/,]"); // Converting data into array
        try {
            int index = 0;
            this.status = MED_STATUS.values()[Integer.parseInt(inputs[index++])];
            this.medicineName = inputs[index++];
            this.amount = Integer.parseInt(inputs[index++]);
        } catch (IndexOutOfBoundsException e) {
            Scanner sc = new Scanner(System.in);

            System.out.println("Incorrect data input - manual input");
            System.out.print("Enter name of medicine: ");
            this.medicineName = sc.nextLine();
            System.out.print("Enter the amount: ");
            this.amount = sc.nextInt();
        }
    }

    // Public methods
    public String getMedicineName() {return this.medicineName;}
    public int getAmount() {return this.amount;}
    public MED_STATUS getStatus() {return this.status;}

    public void setMedicineName(String medicineName) {this.medicineName = medicineName;} // Don't think setters will be used
    public void setAmount(int amount) {this.amount = amount;}
    public void setStatus(MED_STATUS status) {this.status = status;}

    /**
     * Prints out a formatted prescription block
     */
    public void print() {
        System.out.println("______________________________");
        System.out.printf("|%-8s:%-20s|\n", "Medicine", this.medicineName);
        System.out.printf("|%-8s:%-20d|\n", "Amount", this.amount);
        System.out.printf("|%-8s:%-20s|\n", "Status", this.status);
        System.out.println("______________________________");
    }

    /**
     * Sets status to PRESCRIBED
     */
    public void prescribed() {
        this.status = MED_STATUS.PRESCRIBED;
        // More code can be added if necessary
    }

    /**
     * Returns the serialised data
     * @return
     */
    public String getDataSave() {
        return DataSave();
    }

    /**
     * Compares the current prescription with the argument prescription
     * when current medicine is smaller than the argument medicine (alphabetically) returns less than 0
     * when current medicine is larger than the argument medicine (alphabetically) returns more than 0
     * when current medicine is equal to the argument medicine, returns the difference in the amount
     * @param o the object to be compared.
     * @return
     */
    @Override
    public int compareTo(Prescription o) {
        if (this.medicineName.compareTo(o.getMedicineName()) == 0) {
            return this.amount - o.amount;
        }
        return this.medicineName.compareTo(o.getMedicineName());
    }

    // Private method

    /**
     * To convert the object into string for data storing
     * To be deleted
     * @return
     */
    private String DataSave () {
        // Sample 0-MedicineName1-10
        return this.status.ordinal() + "-" + this.medicineName + "-" + this.amount; // Separated by '-' so that each prescription is stored in a cell
    }
}
