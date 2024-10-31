package DepartmentObject;

import DataObject.PharmacyObjects.*;
import InputHandler.Input;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class Pharmacy {

    // Attributes
    private HashMap<String, Integer> nameToID;
    private HashMap<Integer, MedicineData> medicineStorage;
    private ArrayList<MedicineRequest> medicineRequests;
    private ArrayList<RestockRequest> restockRequests;
    private ArrayList<MedicineRequest> pastMedReq;
    private ArrayList<RestockRequest> pastRestockReq;

    // Methods

    public Pharmacy(){
        this.nameToID = new HashMap<String,Integer>();
        this.medicineStorage = new HashMap<Integer,MedicineData>();
        this.medicineRequests = new ArrayList<MedicineRequest>();
        this.restockRequests = new ArrayList<RestockRequest>();
        this.pastMedReq = new ArrayList<MedicineRequest>();
        this.pastRestockReq = new ArrayList<RestockRequest>();
        loadFile();
        testRun();
    }

    public void testRun() {
        MedicineData m1 = new MedicineData(1,"MedicineName1", 30);
        MedicineData m2 = new MedicineData(2,"MedicineName2", 5);
        medicineStorage.put(m1.ID,m1);
        medicineStorage.put(m2.ID,m2);
        nameToID.put("MedicineName1".toLowerCase(),1);
        nameToID.put("MedicineName2".toLowerCase(),2);


        MedicineRequest request = new MedicineRequest(0001,"APT0000001");
        medicineRequests.add(request);

    }

    public void requestMedicine(MedicineRequest request) {
        this.medicineRequests.add(request);
    }

    public void requestRestock(RestockRequest request) {
        this.restockRequests.add(request);
    }

    public void approveMedicine(int index) {
        MedicineRequest request = this.medicineRequests.remove(index);
        this.pastMedReq.add(request);
        request.print();
        Input.ScanString("Has been approved. \nPress enter to continue...");
    }

    public void approveRestock (int index) {
        RestockRequest request = this.restockRequests.remove(index);
        this.pastRestockReq.add(request);
        request.print();
        Input.ScanString("Has been approved. \nPress enter to continue...");
    }

    public void viewMedRequest() {
        int index = 1;
        for (MedicineRequest o : this.medicineRequests) {
            System.out.println(index++ + ")");
            o.print();
        }
    }

    public void viewRestockRequest() {
        int index = 1;
        for (RestockRequest o : this.restockRequests) {
            System.out.println(index++ + ")");
            o.print();
        }
    }

    public MedicineRequest getMedRequest(int index) {
        return this.medicineRequests.get(index);
    }

    public RestockRequest getRestockRequest(int index) {
        return this.restockRequests.get(index);
    }

    public boolean checkFulfillable(String medicineName, int amount){
        int medicineID = convertNameToID(medicineName);
        return medicineStorage.get(medicineID).amount > amount;
    }

    public boolean prescribeMedicine(String medicineName, int amount){
        int medicineID = convertNameToID(medicineName);
        if (medicineStorage.get(medicineID).amount < amount) return false;
        medicineStorage.get(medicineID).amount -= amount;
        return true;
    }

    public void viewStock() {
        System.out.println("_________________________________________");
        for (Map.Entry<Integer, MedicineData> o: medicineStorage.entrySet()) {
            o.getValue().print();
        }
        System.out.println("_________________________________________");
    }

    private int convertNameToID(String medicineName){
        medicineName = medicineName.toLowerCase();
        return nameToID.get(medicineName);
    }

    private void loadFile() {
        System.out.println("Load file");

        /* nameToID stores key medicineName (lowercase) value medicineID
        * medicineStorage stores key medicineID value the medicine data
        * medicineRequests;
        * restockRequests;
        * pastMedReq;
        * pastRestockReq;
        *
        *
        * */
    }

}
