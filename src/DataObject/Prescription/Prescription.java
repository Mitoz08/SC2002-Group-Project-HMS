package DataObject.Prescription;

import java.util.Scanner;

public class Prescription implements Comparable<Prescription> {


    // Private attributes
    private MED_STATUS status;
    private String medicineName;
    private int amount;

    // Constructor
    public Prescription () {
        Scanner sc = new Scanner(System.in);
        this.status = MED_STATUS.PENDING;
        System.out.print("Enter name of medicine: ");
        this.medicineName = sc.nextLine();
        System.out.print("Enter the amount: ");
        this.amount = sc.nextInt();
    }

    public Prescription (String medicineName, int amount, MED_STATUS status) {
        this.status = status;
        this.medicineName = medicineName;
        this.amount = amount;
    }

    public Prescription (String DataInput) {
        // Sample 0-MedicineName-10
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

    public void print() {
        System.out.println("______________________________");
        System.out.printf("|%-8s:%-20s|\n", "Medicine", this.medicineName);
        System.out.printf("|%-8s:%-20d|\n", "Amount", this.amount);
        System.out.printf("|%-8s:%-20s|\n", "Status", this.status);
        System.out.println("______________________________");
    }

    public void prescribed() {
        this.status = MED_STATUS.PRESCRIBED;
        // More code can be added if necessary
    }

    public String getDataSave() {
        return DataSave();
    }

    @Override
    public int compareTo(Prescription o) {
        if (this.medicineName.compareTo(o.getMedicineName()) == 0) {
            return this.amount - o.amount;
        }
        return this.medicineName.compareTo(o.getMedicineName());
    }

    // Private method
    private String DataSave () {
        // Sample 0-MedicineName1-10
        return this.status.ordinal() + "-" + this.medicineName + "-" + this.amount; // Separated by '-' so that each prescription is stored in a cell
    }
}
