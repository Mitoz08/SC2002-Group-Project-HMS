package Serialisation;

import DataObject.Appointment.APT_STATUS;
import DataObject.Appointment.Appointment;
import DataObject.PharmacyObjects.MedicineData;
import DataObject.PharmacyObjects.MedicineRequest;
import DataObject.PharmacyObjects.RestockRequest;
import DataObject.Prescription.MED_STATUS;
import DataObject.Prescription.Prescription;
import DataObject.Prescription.PrescriptionList;
import HumanObject.Administrator.Administrator;
import HumanObject.Doctors.Doctors;
import HumanObject.Patient.Patient;
import HumanObject.Pharmacist.Pharmacist;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class DataSerialisation {

    // Object to String

    /**
     * To serialise prescription object to Status-MedicineName-Amount e.g. 0-Paracetamol-10
     * @param p
     * @return
     */
    public static String SerialisePrescription(Prescription p) {
        String[] StringArray = new String[] {String.valueOf(p.getStatus().ordinal()),
                                             p.getMedicineName(),
                                             String.valueOf(p.getAmount())};
        return convertStringArraytoString(StringArray, "-");
    }

    /**
     * To serialise prescriptionlist object to 0-Paracetamol-10/0-Paracetamol-15/0-Paracetamol-20
     * @param pList
     * @return
     */
    public static String SerialisePrescriptionList(PrescriptionList pList) {
        String[] StringArray = new String[pList.getCount()];
        for (int i = 0; i < pList.getCount(); i++) StringArray[i] = SerialisePrescription(pList.getPrescription(i));
        return convertStringArraytoString(StringArray,"/");
    }

    /**
     * To serialise appointment object to e.g. APT000001/0/Chemo/1001/001/2024-08-21-16-00/Empty/0-MedicineName1-10/0-MedicineName2-10
     * @param apt
     * @return
     */
    public static String SerialiseAppointment(Appointment apt) {
        String[] StringArray = new String[] {
                apt.getAppointmentID(),
                String.valueOf(apt.getStatus().ordinal()), apt.getNameOfApt(), String.valueOf(apt.getPatientID()),
                String.valueOf(apt.getDoctorID()), SerialiseDate(apt.getAppointmentTime()), apt.getNotes(), SerialisePrescriptionList(apt.getPrescriptionList()),
        };
        return convertStringArraytoString(StringArray, "/");
    }

    /**
     * To serialise date object to YYYY-MM-DD-HH-MM 2024-08-21-16-00
     * @param date
     * @return
     */
    public static String SerialiseDate (Date date) {
        String[] StringArray = new String[]
                {String.valueOf(date.getYear()), String.valueOf(date.getMonth()), String.valueOf(date.getDate()),
                        String.valueOf(date.getHours()), String.valueOf(date.getMinutes())};
        return convertStringArraytoString(StringArray, "-");
    }

    public static String SerialiseMedicineReq(MedicineRequest request) {
        String[] StringArray = new String[]
                {String.valueOf(request.getPatientID()), String.valueOf(request.getDoctorID()), request.getAppointmentID(),
                String.valueOf(request.isApproved()), String.valueOf(request.getPharmacistID()) };
        return convertStringArraytoString(StringArray, "/");
    }

    public static String SerialiseRestockReq(RestockRequest request) {
        String[] StringArray = new String[]
                {String.valueOf(request.getPharmacistID()), String.valueOf(request.isApproved()), String.valueOf(request.getAdministratorID()),""};
        String HashMap = "";
        for (Map.Entry<Integer,Integer> o: request.getRequestAmount().entrySet()) {
            HashMap += o.getKey() + '-' + o.getValue() + '/';
        }
        HashMap.substring(0,HashMap.length()-1);
        StringArray[StringArray.length-1] += HashMap;
        return convertStringArraytoString(StringArray, "/");
    }

    public static String SerialiseMedicineData(MedicineData medicineData) {
        String[] StringArray = new String[]
                {String.valueOf(medicineData.getID()), medicineData.getName(), String.valueOf(medicineData.getAmount()),
                String.valueOf(medicineData.getMinStock()) };
        return convertStringArraytoString(StringArray, "/");
    }

    // String to Object

    /**
     * To create prescription object from serialised data
     * @param Serialised
     * @return
     */
    public static Prescription DeserialisePrescription(String Serialised) {
        int index = 0;
        String[] Data = Serialised.split("-"); // Converting data into array
        return new Prescription(MED_STATUS.values()[Integer.parseInt(Data[index++])],Data[index++], Integer.parseInt(Data[index++]));
    }

    /**
     * To create prescription list object from serialised data
     * @param Serialised
     * @return
     */
    public static PrescriptionList DeserialisePrescriptionList(String Serialised) {
        String[] Data = Serialised.split("/");
        PrescriptionList list = new PrescriptionList();
        for (String s: Data) {
            list.addPrescription(DeserialisePrescription(s));
        }
        return list;
    }

    /**
     * To create appointment object from serialised data
     * @param Serialised
     * @return
     */
    public static Appointment DeserialiseAppointment(String Serialised) {
        int index = 0;
        String[] Data = Serialised.split("/");
        String aptID = Data[index++];
        APT_STATUS status = APT_STATUS.values()[Integer.parseInt(Data[index++])];
        String nameOfApt = Data[index++];
        int patientID = Integer.parseInt(Data[index++]);
        int doctorID = Integer.parseInt(Data[index++]);
        Date appointmentTime = DeserialiseDate(Data[index++]);
        String notes = Data[index++];
        PrescriptionList list = new PrescriptionList();
        while (true) {
            try {
                Prescription prescription = new Prescription(Data[index++]);
                list.addPrescription(prescription);
            } catch (IndexOutOfBoundsException e) {
                break;
            }
        }
        return new Appointment(status, nameOfApt, patientID, doctorID, appointmentTime, notes, list, aptID );
    }

    /**
     * To create date object from serialised data
     * @param Serialised
     * @return
     */
    public static Date DeserialiseDate(String Serialised) {
        int index = 0;
        String[] Data = Serialised.split("-");
        Date date = new Date(Integer.parseInt(Data[index++]),
                Integer.parseInt(Data[index++]),
                Integer.parseInt(Data[index++]),
                Integer.parseInt(Data[index++]),
                Integer.parseInt(Data[index++]));
        return date;
    }

    public static MedicineRequest DeserialiseMedicineReq(String Serialised) {
        int index = 0;
        String[] Data = Serialised.split("/");
        MedicineRequest request = new MedicineRequest(Integer.parseInt(Data[index++]),
                Integer.parseInt(Data[index++]),
                Data[index++],
                Boolean.valueOf(Data[index++]),
                Integer.parseInt(Data[index++]));
        return request;
    }

    public static RestockRequest DeserialiseRestockReq(String Serialised) {
        int index = 0;
        String[] Data = Serialised.split("/");
        int pharmacistID = Integer.parseInt(Data[index++]);
        boolean approved = Boolean.valueOf(Data[index++]);
        int adminstratorID = Integer.parseInt(Data[index++]);
        HashMap<Integer,Integer> requestAmmount = new HashMap<>();
        for (;index < Data.length; index++){
            String[] tuple = Data[index].split("-");
            requestAmmount.put(Integer.parseInt(tuple[0]),Integer.parseInt(tuple[1]));
        }
        return new RestockRequest(pharmacistID, approved, adminstratorID, requestAmmount);
    }

    public static MedicineData DeserialiseMedicineData(String Serialised) {
        int index = 0;
        String[] Data = Serialised.split("/");
        MedicineData medicineData = new MedicineData( Integer.parseInt(Data[index++]), Data[index++],
                Integer.parseInt(Data[index++]), Integer.parseInt(Data[index++]));
        return medicineData;
    }



    /**
     * Concatenate all the strings in the array with the delimiter
     * @param StringArray
     * @param delimiter
     * @return
     */
    private static String convertStringArraytoString (String[] StringArray, String delimiter) {
        StringBuilder sb = new StringBuilder();
        for (String str : StringArray)
            sb.append(str).append(delimiter);
        return sb.substring(0, sb.length() - 1);
    }
    // The 4 functions below are used in intialising from HMS.txt
    public static Patient createPatient(){
        return new Patient();
    }
    public static Doctors createDoctor(int ID, String Name, Date DOB, Boolean Gender){
        return new Doctors(ID,Name,DOB,Gender);
    }
    public static Pharmacist createPharmacist(int ID, String Name, Date DOB, Boolean Gender){
        return new Pharmacist(ID, Name, DOB, Gender);
    }
    public static Administrator createAdministrator(int ID, String Name, Date DOB, Boolean Gender){
        return new Administrator(ID, Name, DOB, Gender);
    }


}
