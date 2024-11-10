package DataObject.PharmacyObjects;

import java.util.HashMap;
import java.util.Map;

/**
 * The RestockRequest class represents a request for restocking medicines in a pharmacy system.
 * It includes details about requested medicines, approval status, and associated pharmacist and administrator.
 */
public class RestockRequest {

    // Private
    private HashMap<Integer,Integer> requestAmmount;
    private int pharmacistID;
    private boolean approved;
    private int administratorID;

    // Public

    /**
     * Constructs a new RestockRequest with specified request amounts and pharmacist details.
     * The request is initialized as not approved, and no administrator is assigned.
     *
     * @param requestAmount a map containing medicine IDs and their requested quantities
     * @param pharmacistID  the ID of the pharmacist making the request
     */
    public RestockRequest(HashMap<Integer,Integer> requestAmount, int pharmacistID) {
        this.requestAmmount = requestAmount;
        this.pharmacistID = pharmacistID;
        this.approved = false;
        this.administratorID = -1;
    }

    /**
     * Constructs a new RestockRequest with specified request amounts, pharmacist, approval status, and administrator details.
     * Used when loading from file.
     *
     * @param pharmacistID    the ID of the pharmacist making the request
     * @param approved        the approval status of the request
     * @param administratorID the ID of the administrator who approved the request
     * @param requestAmount   a map containing medicine IDs and their requested quantities
     */
    public RestockRequest(int pharmacistID, boolean approved, int administratorID, HashMap<Integer,Integer> requestAmount) {
        this.requestAmmount = requestAmount;
        this.pharmacistID = pharmacistID;
        this.approved = approved;
        this.administratorID = administratorID;
    }

    // Getters

    /**
     * Gets the pharmacist's ID of the request (Pharmacist who created the request)
     * @return the pharmacist's ID of the request (Pharmacist who created the request)
     */
    public int getPharmacistID() {return this.pharmacistID;}

    /**
     * Gets the {@code boolean} variable {@code approved} of the request
     * @return the {@code boolean} variable {@code approved} of the request
     */
    public boolean isApproved() {return this.approved;}

    /**
     * Gets the administrator's ID of the request (Administrator who approved the request)
     * @return the administrator's ID of the request (Administrator who approved the request)
     */
    public int getAdministratorID() { return this.administratorID;}

    /**
     * Returns a copy of the map containing the medicine IDs and their requested quantities.
     *
     * @return a clone of the request amount map
     */
    public HashMap<Integer,Integer> getRequestAmount() {
        return (HashMap<Integer,Integer>) requestAmmount.clone();
    }

    /**
     * Approves the {@code RestockRequest} and assigns an administrator ID.
     *
     * @param administratorID the ID of the administrator who approved the request
     */
    public void ApprovedRequest(int administratorID) {
        this.administratorID = administratorID;
        this.approved = true;
    }

    /**
     * Prints a formatted block of the restock request.
     */
    public void print() {
        System.out.printf("_______________________________\n");
        for (Map.Entry<Integer,Integer> e : requestAmmount.entrySet()){
            System.out.printf("|%-6s:%-8s %-6s:%-8s|\n", "MED ID", MedicineData.getStrID(e.getKey()), "Amount", e.getValue());
        }
        System.out.printf("|%-12s:%-18s|\n", "PharmacistID","PH"+ this.pharmacistID);
        System.out.println("_______________________________");
    }

    /**
     * Prints a formatted block of the restock request with an index.
     *
     * @param index the index to display before the block
     */
    public void print(int index) {
        System.out.printf("%2d)_______________________________\n",index);
        for (Map.Entry<Integer,Integer> e : requestAmmount.entrySet()){
            System.out.printf("   |%-6s:%-8s %-6s:%-8s|\n", "MED ID", MedicineData.getStrID(e.getKey()), "Amount", e.getValue());
        }
        System.out.printf("   |%-12s:%-18s|\n", "PharmacistID","PH"+ this.pharmacistID);
        System.out.println("   _______________________________");
    }

}
