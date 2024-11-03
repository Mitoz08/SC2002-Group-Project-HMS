package DepartmentObject;

import DataObject.PharmacyObjects.*;
import InputHandler.Input;
import Serialisation.DataEncryption;
import Serialisation.DataSerialisation;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;


public class Pharmacy {

    // Attributes
    private HashMap<String, Integer> nameToID;
    HashMap<Integer,Integer> pendingAmount;
    private HashMap<Integer, MedicineData> medicineStorage;
    private ArrayList<MedicineRequest> medicineRequests;
    private ArrayList<RestockRequest> restockRequests;
    private ArrayList<MedicineRequest> pastMedReq;
    private ArrayList<RestockRequest> pastRestockReq;

    // Methods

    public Pharmacy(){
        this.nameToID = new HashMap<String,Integer>();
        this.pendingAmount = new HashMap<Integer,Integer>();
        this.medicineStorage = new HashMap<Integer,MedicineData>();
        this.medicineRequests = new ArrayList<MedicineRequest>();
        this.restockRequests = new ArrayList<RestockRequest>();
        this.pastMedReq = new ArrayList<MedicineRequest>();
        this.pastRestockReq = new ArrayList<RestockRequest>();
        loadFile();
        Input.ScanString("");
        //testRun();
    }

    public void endPharmacy() {

        saveFile();
    }

    public void testRun() {
        MedicineData m1 = new MedicineData(1,"MedicineName1", 30,20);
        MedicineData m2 = new MedicineData(2,"MedicineName2", 5,20);
        addMedicine(m1);
        addMedicine(m2);

        MedicineRequest request = new MedicineRequest(1001,001,"APT0000001");
        requestMedicine(request);

    }

    public void requestMedicine(MedicineRequest request) {
        this.medicineRequests.add(request);
        Input.ScanString("Request has been added. \nPress enter to continue...");
    }

    public void requestRestock(RestockRequest request) {
        updatePendingRestock(true , request);
        this.restockRequests.add(request);
        Input.ScanString("Request has been added. \nPress enter to continue...");
    }

    public void approveMedicine(int index) {
        MedicineRequest request = this.medicineRequests.remove(index);
        this.pastMedReq.add(request);
        request.print();
        Input.ScanString("Request has been approved. \nPress enter to continue...");
    }

    public void approveRestock (int index) {
        RestockRequest request = this.restockRequests.remove(index);
        updatePendingRestock(false , request);
        this.pastRestockReq.add(request);
        updateCurrentStock(request);
        request.print();
        Input.ScanString("Request has been approved. \nPress enter to continue...");
    }

    public int viewMedRequest() {
        int index = 1;
        for (MedicineRequest o : this.medicineRequests) {
            System.out.println(index++ + ")");
            o.print();
        }
        return this.medicineRequests.size();
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
        System.out.println("_____________________________________________");
        for (Map.Entry<Integer, MedicineData> o: medicineStorage.entrySet()) {
            String MedID = o.getValue().getIDString();
            String MedName = o.getValue().name;
            int MinAmt = o.getValue().minStock;
            int CurAmt = o.getValue().amount;
            int PedAmt = pendingAmount.get(o.getKey());
            System.out.printf("%-8s:%-16s Min:%-5d Low:%-5s\n", MedID, MedName, MinAmt, o.getValue().getLevelStatus());
            System.out.printf("In Stock:%-5d Pending:%-5d\n", CurAmt, PedAmt);
            System.out.println("_____________________________________________");
        }
    }

    public void viewStock(HashMap<Integer,Integer> currentRequest) {
        System.out.println("_____________________________________________");
        for (Map.Entry<Integer, MedicineData> o: medicineStorage.entrySet()) {
            String MedID = o.getValue().getIDString();
            String MedName = o.getValue().name;
            int MinAmt = o.getValue().minStock;
            int CurAmt = o.getValue().amount;
            int PedAmt = pendingAmount.get(o.getKey());
            int ReqAmt = 0;
            if (currentRequest.get(o.getKey()) != null) ReqAmt += currentRequest.get(o.getKey());
            System.out.printf("%-8s:%-16s Min:%-5d Low:%-5s\n", MedID, MedName, MinAmt, o.getValue().getLevelStatus());
            System.out.printf("In Stock:%-6d Pending:%-6d Indent:%-6d\n", CurAmt, PedAmt, ReqAmt);
        System.out.println("_____________________________________________");
        }
    }

    public void updatePendingRestock( boolean Add , RestockRequest request ) {
        for (Map.Entry<Integer, Integer> o: request.getRequestAmount().entrySet()) {
            int ID = o.getKey();
            if (Add) pendingAmount.replace(ID,pendingAmount.get(ID),pendingAmount.get(ID) + o.getValue());
            else pendingAmount.replace(ID,pendingAmount.get(ID),pendingAmount.get(ID) - o.getValue());
        }
    }

    public boolean checkExistingID(int ID) {
        return medicineStorage.containsKey(ID);
    }

    private void updateCurrentStock(RestockRequest request) {
        for (Map.Entry<Integer,Integer> o: request.getRequestAmount().entrySet()) {
            int ID = o.getKey();
            MedicineData medicineData = medicineStorage.get(ID);
            medicineData.amount += o.getValue();
        }
    }

    private boolean addMedicine(MedicineData medicine) {
        if (medicineStorage.containsKey(medicine.ID)) return false; // Return false when there is same ID
        medicineStorage.put(medicine.ID,medicine);
        pendingAmount.put(medicine.ID,0);
        nameToID.put(medicine.name.toLowerCase(),medicine.ID);
        return true;
    }

    private int convertNameToID(String medicineName){
        medicineName = medicineName.toLowerCase();
        //System.out.println(medicineName);
        return nameToID.get(medicineName);
    }

    private void loadFile() {
        File savefile = new File("Pharmacy.txt");
        Scanner file;
        try {
            file = new Scanner(savefile);
            loadData(file);
            file.close();
        } catch (Exception e) {
            System.out.println("Error");
            return;
        } finally {
            System.out.println("Finish load function");
        }
    }

    private void loadData(Scanner fileReader) {
        while (fileReader.hasNextLine()) {
            String text = fileReader.nextLine();
            while (true) {
                text = fileReader.nextLine();
                text = DataEncryption.decipher(text);
                //System.out.println(text);
                if (text.equals("RestockRequest")) break;
                MedicineRequest request = DataSerialisation.DeserialiseMedicineReq(text);
                if (request.isApproved()) pastMedReq.add(request);
                else medicineRequests.add(request);
            }

            while (true) {
                text = fileReader.nextLine();
                text = DataEncryption.decipher(text);
                //System.out.println(text);
                if (text.equals("MedicineData")) break;
                RestockRequest request = DataSerialisation.DeserialiseRestockReq(text);
                if (request.isApproved()) pastRestockReq.add(request);
                else {
                    restockRequests.add(request);
                    updatePendingRestock(true,request);
                }
            }

            while (fileReader.hasNextLine()) {
                text = fileReader.nextLine();
                text = DataEncryption.decipher(text);
                //System.out.println(text);
                MedicineData medicineData = DataSerialisation.DeserialiseMedicineData(text);
                medicineStorage.put(medicineData.ID, medicineData);
                System.out.println(medicineData.name + medicineData.ID);
                nameToID.put(medicineData.name.toLowerCase(),medicineData.ID);
                if (!pendingAmount.containsKey(medicineData.ID)) pendingAmount.put(medicineData.ID,0);
            }
        }
    }

    private void saveFile() {
        File savefile = new File("Pharmacy.txt");
        FileWriter file;
        try {
            file = new FileWriter(savefile);
            saveData(file);
            file.close();
        } catch (Exception e) {
            System.out.println("Error");
            return;
        } finally {
            System.out.println("Finish save function");
        }

    }

    private void saveData(FileWriter fileWriter) throws IOException {
        String text = "MedicineRequest";
        text = DataEncryption.cipher(text);
        fileWriter.write(text);
        fileWriter.write("\n");
        while (!medicineRequests.isEmpty()){
            text = DataSerialisation.SerialiseMedicineReq(medicineRequests.removeFirst());
            text = DataEncryption.cipher(text);
            fileWriter.write(text);
            fileWriter.write("\n");
        }

        while (!pastMedReq.isEmpty()) {
            text = DataSerialisation.SerialiseMedicineReq(pastMedReq.removeFirst());
            text = DataEncryption.cipher(text);
            fileWriter.write(text);
            fileWriter.write("\n");
        }

        text = "RestockRequest";
        text = DataEncryption.cipher(text);
        fileWriter.write(text);
        fileWriter.write("\n");

        while (!restockRequests.isEmpty()) {
            text = DataSerialisation.SerialiseRestockReq(restockRequests.removeFirst());
            text = DataEncryption.cipher(text);
            fileWriter.write(text);
            fileWriter.write("\n");
        }

        while (!pastRestockReq.isEmpty()) {
            text = DataSerialisation.SerialiseRestockReq(pastRestockReq.removeFirst());
            text = DataEncryption.cipher(text);
            fileWriter.write(text);
            fileWriter.write("\n");
        }

        text = "MedicineData";
        text = DataEncryption.cipher(text);
        fileWriter.write(text);
        fileWriter.write("\n");

        for (Map.Entry<Integer,MedicineData> o : medicineStorage.entrySet()){
            text = DataSerialisation.SerialiseMedicineData(o.getValue());
            text = DataEncryption.cipher(text);
            fileWriter.write(text);
            fileWriter.write("\n");
        }

    }


}
