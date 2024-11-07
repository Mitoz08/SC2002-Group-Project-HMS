package DataObject.PharmacyObjects;

import java.util.HashMap;
import java.util.Map;

public class RestockRequest {

    // Private
    private HashMap<Integer,Integer> requestAmmount;
    private int pharmacistID;
    private boolean approved;
    private int administratorID;

    // Public

    public RestockRequest(HashMap<Integer,Integer> requestAmount, int pharmacistID) {
        this.requestAmmount = requestAmount;
        this.pharmacistID = pharmacistID;
        this.approved = false;
        this.administratorID = -1;
    }

    public RestockRequest(int pharmacistID, boolean approved, int administratorID, HashMap<Integer,Integer> requestAmount) {
        this.requestAmmount = requestAmount;
        this.pharmacistID = pharmacistID;
        this.approved = approved;
        this.administratorID = administratorID;
    }


    public int getPharmacistID() {return this.pharmacistID;}
    public boolean isApproved() {return this.approved;}
    public int getAdministratorID() { return this.administratorID;}

    public HashMap<Integer,Integer> getRequestAmount() {
        return (HashMap<Integer,Integer>) requestAmmount.clone();
    }

    public void AddRequest(int medicineID, int newAmount) {
        requestAmmount.put(medicineID,newAmount);
    }

    public void UpdateRequest(int medicineID, int newAmount) {
        requestAmmount.replace(medicineID,newAmount);
    }

    public void ApprovedRequest(int adminstratorID) {
        this.administratorID = adminstratorID;
        this.approved = true;
    }

    public void print() {
        System.out.printf("_______________________________\n");
        for (Map.Entry<Integer,Integer> e : requestAmmount.entrySet()){
            System.out.printf("|%-6s:%-8s %-6s:%-8s|\n", "MED ID", MedicineData.getStrID(e.getKey()), "Amount", e.getValue());
        }
        System.out.printf("|%-12s:%-18d|\n", "PharmacistID", this.pharmacistID);
        System.out.println("_______________________________");
    }

    public void print(int index) {
        System.out.printf("%2d)_______________________________\n",index);
        for (Map.Entry<Integer,Integer> e : requestAmmount.entrySet()){
            System.out.printf("   |%-6s:%-8s %-6s:%-8s|\n", "MED ID", MedicineData.getStrID(e.getKey()), "Amount", e.getValue());
        }
        System.out.printf("   |%-12s:%-18d|\n", "PharmacistID", this.pharmacistID);
        System.out.println("   _______________________________");
    }

}
