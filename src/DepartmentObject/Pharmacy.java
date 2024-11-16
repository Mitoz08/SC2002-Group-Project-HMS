package DepartmentObject;

import DataObject.PharmacyObjects.*;
import InputHandler.Input;
import Serialisation.DataEncryption;
import Serialisation.DataSerialisation;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;


/**
 * The {@code Pharmacy} class manages the medicine stock for the Hospital Management System
 * <p>
 *     An instance of this class works off of one database (one text file), allowing
 *     the Hospital Management System to include multiple pharmacy department if needed.
 * </p>
 * <p>
 *     This class uses the {@code MedicineData} class to store information for each medicine in the hospital
 * </p>
 * <p>
 *     {@code MedicineRequest} and {@code RestockRequest} classes are used to to keep track of the transaction
 *     going on within the {@code Pharmacy}
 * </p>
 *
 * @see MedicineData    MedicineData - Data type to store medicine details
 * @see MedicineRequest MedicineRequest - Data type to store medicine request details
 * @see RestockRequest  RestockRequest - Data type to store medicine restock details
 */
public class Pharmacy {

    // Attributes

    /** The HashMap storing the name of the medicine to its integer ID. */
    private HashMap<String, Integer> nameToID;

    /** The HashMap storing medicine ID to its pending amount (Current restock request). */
    private HashMap<Integer,Integer> pendingAmount;

    /** The HashMap storing medicine ID to its medicine data. */
    private HashMap<Integer, MedicineData> medicineStorage;

    /** The ArrayList storing the ongoing medicine request. */
    private ArrayList<MedicineRequest> medicineRequests;

    /** The ArrayList storing the ongoing restock request. */
    private ArrayList<RestockRequest> restockRequests;

    /** The ArrayList storing the past medicine request. */
    private ArrayList<MedicineRequest> pastMedReq;

    /** The ArrayList storing the past restock request. */
    private ArrayList<RestockRequest> pastRestockReq;

    /** The file name to read and write to. */
    private String pharmacyFileName;

    // Methods

    /**
     * Constructs a {@code Pharmacy object} with its {@code ArrayList} and {@code HashMap} initialised
     */
    public Pharmacy(){
        this.nameToID = new HashMap<String,Integer>();
        this.pendingAmount = new HashMap<Integer,Integer>();
        this.medicineStorage = new HashMap<Integer,MedicineData>();
        this.medicineRequests = new ArrayList<MedicineRequest>();
        this.restockRequests = new ArrayList<RestockRequest>();
        this.pastMedReq = new ArrayList<MedicineRequest>();
        this.pastRestockReq = new ArrayList<RestockRequest>();
//        loadFile();
//        Input.ScanString("");
//        testRun();
    }

    // Getter/Setter

    /**
     * Gets a copy of the MedicineStorage
     * @return a copy of the MedicineStorage
     */
    public HashMap<Integer,MedicineData> getMedicine() {
        HashMap<Integer,MedicineData> temp = (HashMap<Integer,MedicineData>) this.medicineStorage.clone();
        return temp;
    }

    /**
     * Sets the file for the {@code Pharmacy} instance to read from
     * @param fileName file to read from
     */
    public void setPharmacyFileName(String fileName) {
        this.pharmacyFileName = fileName;
    }

    /**
     * Terminates the {@code Pharmacy} after saving the data into the file
     */
    public void endPharmacy() {
        saveFile();
    }

    /**
     * A method used to test run the program by initialising the default medicine
     */
    public void testRun() {
        MedicineData m1 = new MedicineData(1,"Paracetamol", 100,20);
        MedicineData m2 = new MedicineData(2,"Ibuprofen", 50,10);
        MedicineData m3 = new MedicineData(3,"Amoxicillin", 75,15);
        addMedicine(m1);
        addMedicine(m2);
        addMedicine(m3);
    }

    /**
     * Adds a {@code MedicineRequest} into the queue
     * @param request request to be added
     */
    public void requestMedicine(MedicineRequest request) {
        this.medicineRequests.add(request);
        Input.ScanString("Request has been added. \nPress enter to continue...");
    }

    /**
     * Adds a {@code RestockRequest} into the queue
     * @param request request to be added
     */
    public void requestRestock(RestockRequest request) {
        if (request == null) return;
        updatePendingRestock(true , request);
        this.restockRequests.add(request);
        Input.ScanString("Request has been added. \nPress enter to continue...");
    }

    /**
     * Approves the {@code MedicineRequest} with the given {@code index}
     * <p>
     *     Moves request from {@code medicineRequest} (active list) to {@code pastMedReq} (completed list) for tracking
     * </p>
     * @param index {@code index} of the {@code MedicineRequest} in the ArrayList
     */
    public void approveMedicine(int index) {
        MedicineRequest request = this.medicineRequests.remove(index);
        this.pastMedReq.add(request);
        request.print();
        Input.ScanString("Request has been approved. \nPress enter to continue...");
    }

    /**
     * Approves the {@code RestockRequest} with the given {@code index}
     * <p>
     *     Moves request from {@code restockRequest} (active list) to {@code pastRestockReq} (completed list) for tracking
     * </p>
     * @param index {@code index} of the {@code RestockRequest} in the ArrayList
     */
    public void approveRestock (int index) {
        RestockRequest request = this.restockRequests.remove(index);
        updatePendingRestock(false , request);
        this.pastRestockReq.add(request);
        updateCurrentStock(request);
        request.print();
        Input.ScanString("Request has been approved. \nPress enter to continue...");
    }

    /**
     * Prints out every {@code MedicineRequest} in {@code medicineRequest} (active list)
     * @return the size of the ArrayList {@code medicineRequest}
     */
    public int viewMedRequest() {
        int index = 1;
        for (MedicineRequest o : this.medicineRequests) {
            o.print(index++);
        }
        return this.medicineRequests.size();
    }

    /**
     * Prints out every {@code RestockRequest} in {@code restockRequests} (active list)
     * @return the size of the ArrayList {@code restockRequest}
     */
    public int viewRestockRequest() {
        int index = 1;
        for (RestockRequest o : this.restockRequests) {
            o.print(index++);
        }
        return this.restockRequests.size();
    }

    /**
     * Gets the request in the ArrayList {@code medicineRequest} with the given {@code index}
     * @param index {@code index} of the {@code MedicineRequest}
     * @return {@code null} if empty or out of bound, {@code MedicineRequest} when it is in the ArrayList
     */
    public MedicineRequest getMedRequest(int index) {
        if (this.medicineRequests.isEmpty() || index >= this.medicineRequests.size()) return null;
        return this.medicineRequests.get(index);
    }

    /**
     * Gets the request in the ArrayList {@code restockRequest} with the given {@code index}
     * @param index {@code index} of the {@code RestockRequest}
     * @return {@code null} if empty or out of bound, {@code RestockRequest} when it is in the ArrayList
     */
    public RestockRequest getRestockRequest(int index) {
        if (this.restockRequests.isEmpty() || index >= this.restockRequests.size()) return null;
        return this.restockRequests.get(index);
    }

    /**
     * Checks whether the {@code Pharmacy} has enough medicine to fulfill the request
     * @param medicineName name of medicine to prescribe
     * @param amount amount to be prescribed
     * @return {@code true} when there is enough stock, {@code false} otherwise
     */
    public boolean checkFulfillable(String medicineName, int amount){
        int medicineID = convertNameToID(medicineName);
        return medicineStorage.get(medicineID).amount > amount;
    }

    /**
     * Prescribes the medicine by deducting given {@code amount} from {@code medicineName} in the {@code medicineStorage}
     * @param medicineName name of medicine
     * @param amount amount of that medicine
     * @return
     */
    public boolean prescribeMedicine(String medicineName, int amount){
        int medicineID = convertNameToID(medicineName);
        if (medicineStorage.get(medicineID).amount < amount) return false;
        medicineStorage.get(medicineID).amount -= amount;
        return true;
    }

    /**
     * Prints out the stock of the medicine in {@code medicineStorage} with its pending amount in {@code pendingAmount}
     */
    public void viewStock() {
        System.out.println("_____________________________________________");
        for (Map.Entry<Integer, MedicineData> o: medicineStorage.entrySet()) {
            String MedID = o.getValue().getIDString();
            String MedName = o.getValue().name;
            int MinAmt = o.getValue().minStock;
            int CurAmt = o.getValue().amount;
            int PedAmt = pendingAmount.get(o.getKey());
            System.out.printf("%-8s:%-16s Min:%-5d Low:%-5s\n", MedID, MedName, MinAmt, o.getValue().getLevelStatus());
            System.out.printf("In Stock:%-6d Pending:%-6d\n", CurAmt, PedAmt);
            System.out.println("_____________________________________________");
        }
    }

    /**
     * Prints out the stock of the medicine in {@code medicineStorage} with its pending amount in {@code pendingAmount}
     * <p>
     *     Takes in a {@code HashMap<Integer,Integer>} storing the indenting amount which is to be printed
     * </p>
     * @param currentRequest {@code HashMap<Integer,Integer>} storing the indenting amount
     */
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

    /**
     * Updates the {@code  HashMap<Integer,Integer> pendingAmount} when there is a new indent or a restock has been fulfilled
     * @param Add {@code true} adds to the pending amount, {@code false} subtracts from the pending amount
     * @param request {@code RestockRequest} to be referenced to
     */
    public void updatePendingRestock( boolean Add , RestockRequest request ) {
        for (Map.Entry<Integer, Integer> o: request.getRequestAmount().entrySet()) {
            int ID = o.getKey();
            if (Add) pendingAmount.replace(ID,pendingAmount.get(ID),pendingAmount.get(ID) + o.getValue());
            else pendingAmount.replace(ID,pendingAmount.get(ID),pendingAmount.get(ID) - o.getValue());
        }
    }

    /**
     * Checks whether there is a medicine with the existing {@code ID}
     * @param ID ID to be checked
     * @return {@code true} if there is a medicine with that ID, {@code false} otherwise
     */
    public boolean checkExistingID(int ID) {
        return medicineStorage.containsKey(ID);
    }

    /**
     * Checks whether there is a medicine with the existing {@code medicine} name
     * @param medicine name of Medicine
     * @return {@code true} if there is a medicine with that name, {@code false} otherwise
     */
    public boolean isMedicine(String medicine) {
        return nameToID.containsKey(medicine.toLowerCase());
    }

    /**
     * Takes in a medicine name and converts it into its ID by looking at the {@code HashMap<String,Integer> nameToID}
     * @param medicineName the name of the mdicine
     * @return the ID with that medicine name, {@code null} if no medicine with that name in the {@code Pharmacy}
     */
    public int convertNameToID(String medicineName){
        medicineName = medicineName.toLowerCase();
        //System.out.println(medicineName);
        return nameToID.get(medicineName);
    }

    /**
     * Called when a {@code RestockRequest} is approved, and increase the stock with the information in the request
     * @param request {@code RestockRequest} to be referenced
     */
    private void updateCurrentStock(RestockRequest request) {
        for (Map.Entry<Integer,Integer> o: request.getRequestAmount().entrySet()) {
            int ID = o.getKey();
            MedicineData medicineData = medicineStorage.get(ID);
            medicineData.amount += o.getValue();
        }
    }

    /**
     * Adds a new medicine into the {@code Pharmacy}
     * @param medicine medicine to be added
     * @return {@code true} when successfully added, {@code false} otherwise
     */
    private boolean addMedicine(MedicineData medicine) {
        if (medicineStorage.containsKey(medicine.ID)) return false; // Return false when there is same ID
        medicineStorage.put(medicine.ID,medicine);
        pendingAmount.put(medicine.ID,0);
        nameToID.put(medicine.name.toLowerCase(),medicine.ID);
        return true;
    }

    /**
     * Used to read and load in data from the file.
     * <p>Creates a new {@code File} and {@code Scanner} with the file name {@code pharmacyFileName} and passes it into
     * the function {@code loadData()}</p>
     * @see #loadData(Scanner) loadData() function
     */
    public void loadFile() {
        File savefile = new File(pharmacyFileName);
        Scanner file;
        try {
            file = new Scanner(savefile);
            loadData(file);
            file.close();
        } catch (Exception e) {
            System.out.println("Error reading Pharmacy file");
            return;
        } finally {
//            System.out.println("Finish load function");
        }
    }

    /**
     * Reads from the given {@code Scanner} object and creates {@code MedicineRequest} followed by {@code RestockRequest} then {@code MedicineData}
     * @param fileReader {@code Scanner} object for reading
     *
     * @see MedicineRequest MedicineRequest Class
     * @see RestockRequest  RestockRequest Class
     * @see MedicineData    MedicineData class
     */
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
//                System.out.println(medicineData.name + medicineData.ID);
                nameToID.put(medicineData.name.toLowerCase(),medicineData.ID);
                if (!pendingAmount.containsKey(medicineData.ID)) pendingAmount.put(medicineData.ID,0);
            }
        }
    }

    /**
     * Used to save and write data into the file.
     * <p>Creates a new {@code File} and {@code FileWriter} with the file name {@code pharmacyFileName} and passes it into
     * the function {@code saveData()}</p>
     * @see #saveData(FileWriter)   saveData() function
     */
    private void saveFile() {
        File savefile = new File(pharmacyFileName);
        FileWriter file;
        try {
            file = new FileWriter(savefile);
            saveData(file);
            file.close();
        } catch (Exception e) {
            System.out.println("Error writing into Pharmacy file");
            return;
        } finally {
//            System.out.println("Finish save function");
        }

    }

    /**
     * Saves {@code MedicineRequest} followed by {@code RestockRequest} then {@code MedicineData} from memory into
     * the file using the FileWriter object
     * @param fileWriter {@code FileWriter} object for writing
     * @throws IOException throws an error when it fails to write to file
     */
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
