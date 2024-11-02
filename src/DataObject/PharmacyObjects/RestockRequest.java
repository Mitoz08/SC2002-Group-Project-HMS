package DataObject.PharmacyObjects;

import java.util.HashMap;
import java.util.Map;

public class RestockRequest {

    // Private
    private HashMap<Integer,Integer> requestAmmount;
    private int pharmacistID;
    private boolean approved;
    private int adminstratorID;

    // Public

    public RestockRequest(HashMap<Integer,Integer> requestAmmount, int pharmacistID) {
        this.requestAmmount = requestAmmount;
        this.pharmacistID = pharmacistID;
    }

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
        this.adminstratorID = adminstratorID;
        this.approved = true;
    }

    public void print() {
        System.out.println("_______________________________");
        for (Map.Entry<Integer,Integer> e : requestAmmount.entrySet()){
            System.out.printf("|%-6s:%-8s %-6s:%-8s|\n", "MED ID", MedicineData.getStrID(e.getKey()), "Amount", e.getValue());
        }
        System.out.printf("|%-12s:%-18d|\n", "PharmacistID", this.pharmacistID);
        System.out.println("_______________________________");
    }

}
