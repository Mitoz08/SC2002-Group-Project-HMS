package Serialisation;

import DataObject.Appointment.*;
import DataObject.PharmacyObjects.*;
import DataObject.Prescription.*;
import HumanObject.Administrator.Administrator;
import HumanObject.Doctors.Doctors;
import HumanObject.Patient.Contact;
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
        if (pList.getCount() == 0) return "Empty";
        String[] StringArray = new String[pList.getCount()];
        for (int i = 0; i < pList.getCount(); i++) StringArray[i] = SerialisePrescription(pList.getPrescription(i));
        return convertStringArraytoString(StringArray,"/");
    }

    /**
     * To serialise appointment object to e.g. APT000001/0/Chemo/1001/P_Name/001/D_Name/2024-08-21-16-00/Empty/0-MedicineName1-10/0-MedicineName2-10
     * @param apt
     * @return
     */
    public static String SerialiseAppointment(Appointment apt) {
        String[] StringArray = new String[] {
                apt.getAppointmentID(),
                String.valueOf(apt.getStatus().ordinal()), apt.getNameOfApt(), String.valueOf(apt.getPatientID()), apt.getPatientName(),
                String.valueOf(apt.getDoctorID()), apt.getDoctorname(), SerialiseDate(apt.getAppointmentTime()), apt.getNotes(),
                SerialisePrescriptionList(apt.getPrescriptionList()),
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

    public static String SerialiseContact (Contact contact) {
        String[] StringArray = new String[] { contact.getEmail(),contact.getContactNumber()};
        return convertStringArraytoString(StringArray, "|");
    }

    public static String SerialisePatient(Patient patient) {
        String[] StringArray = new String[]
                { String.valueOf(patient.getID()), patient.getName(), SerialiseDate(patient.getDOB()),
                String.valueOf(patient.getGender()), patient.getBloodType(), SerialiseContact(patient.getContact())};
        String Serialised = convertStringArraytoString(StringArray, "/");
        return "PA&" + Serialised;
    }

    public static String SerialisedDoctor(Doctors doctor) {
        String[] StringArray = new String[]
                { String.valueOf(doctor.getID()), doctor.getName(), SerialiseDate(doctor.getDOB()),
                String.valueOf(doctor.getGender()) };
        String Serialised = convertStringArraytoString(StringArray, "/");
        return "DR&" + Serialised;
    }

    public static String SerialisedPharmacist(Pharmacist pharmacist) {
        String[] StringArray = new String[]
                { String.valueOf(pharmacist.getID()), pharmacist.getName(), SerialiseDate(pharmacist.getDOB()),
                String.valueOf(pharmacist.getGender()) };
        String Serialised = convertStringArraytoString(StringArray, "/");
        return "PH&" + Serialised;
    }

    public static String SerialisedAdministrator(Administrator administrator) {
        String[] StringArray = new String[]
                { String.valueOf(administrator.getID()), administrator.getName(), SerialiseDate(administrator.getDOB()),
                String.valueOf(administrator.getGender()) };
        String Serialised = convertStringArraytoString(StringArray, "/");
        return "AD&" + Serialised;
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
        String patientName = Data[index++];
        int doctorID = Integer.parseInt(Data[index++]);
        String doctorName = Data[index++];
        Date appointmentTime = DeserialiseDate(Data[index++]);
        String notes = Data[index++];
        PrescriptionList list = new PrescriptionList();
        while (index < Data.length && !Data[index].equals("Empty")) {
            System.out.println("test");
            try {
                Prescription prescription = new Prescription(Data[index++]);
                list.addPrescription(prescription);
            } catch (IndexOutOfBoundsException e) {
                break;
            }
        }
        return new Appointment(status, nameOfApt, patientID, patientName, doctorID, doctorName, appointmentTime, notes, list, aptID );
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

    public static Contact DeerialiseContact (String Serialised) {
        String[] StringArray = Serialised.split("\\|");
        return new Contact(StringArray[0],StringArray[1]);
    }

    public static Patient DeserialisePatient(String Serialised) {
         String[] StringArray = Serialised.split("/");
         int index = 0;
         int ID = Integer.parseInt(StringArray[index++]);
         String name = StringArray[index++];
         Date DOB = DeserialiseDate(StringArray[index++]);
         boolean Gender = Boolean.parseBoolean(StringArray[index++]);
         String bloodType = StringArray[index++];
         Contact contact = DeerialiseContact(StringArray[index++]);
         return new Patient(ID, name, DOB, Gender, bloodType, contact);
    }

    public static Doctors DeserialiseDoctor(String Serialised) {
         String[] StringArray = Serialised.split("/");
         int index = 0;
         int ID = Integer.parseInt(StringArray[index++]);
         String name = StringArray[index++];
         Date DOB = DeserialiseDate(StringArray[index++]);
         boolean Gender = Boolean.parseBoolean(StringArray[index++]);
         return new Doctors(ID, name, DOB, Gender);
    }

    public static Pharmacist DeserialisePharmacist(String Serialised) {
         String[] StringArray = Serialised.split("/");
         int index = 0;
         int ID = Integer.parseInt(StringArray[index++]);
         String name = StringArray[index++];
         Date DOB = DeserialiseDate(StringArray[index++]);
         boolean Gender = Boolean.parseBoolean(StringArray[index++]);
         return new Pharmacist(ID, name, DOB, Gender);
    }

    public static Administrator DeserialiseAdministrator(String Serialised) {
        String[] StringArray = Serialised.split("/");
         int index = 0;
         int ID = Integer.parseInt(StringArray[index++]);
         String name = StringArray[index++];
         Date DOB = DeserialiseDate(StringArray[index++]);
         boolean Gender = Boolean.parseBoolean(StringArray[index++]);
         return new Administrator(ID, name, DOB, Gender);
    }


    /**
     * Concatenate all the strings in the array with the delimiter
     * @param StringArray
     * @param delimiter
     * @return
     */
    public static String convertStringArraytoString (String[] StringArray, String delimiter) {
        StringBuilder sb = new StringBuilder();
        for (String str : StringArray)
            sb.append(str).append(delimiter);
        return sb.substring(0, sb.length() - 1);
    }
    // The 4 functions below are used in intialising from HMS.txt
    public static Patient createPatient(int ID, String name, Date DOB, Boolean gender, String bloodType, Contact contactPat){
        return new Patient(ID, name, DOB, gender, bloodType, contactPat);
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
