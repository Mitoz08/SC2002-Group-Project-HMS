package Serialisation;

import DataObject.Appointment.*;
import DataObject.PharmacyObjects.*;
import DataObject.Prescription.*;
import HumanObject.Administrator.Administrator;
import HumanObject.Doctor.Doctor;
import HumanObject.Patient.Contact;
import HumanObject.Patient.Patient;
import HumanObject.Pharmacist.Pharmacist;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class DataSerialisation {

    // Object to String

    /**
     * To serialise {@code Prescription} object to Status-MedicineName-Amount e.g. 0-Paracetamol-10
     * @param p {@code Prescription} to be serialised
     * @return formatted {@code Prescription} String
     */
    public static String SerialisePrescription(Prescription p) {
        String[] StringArray = new String[] {String.valueOf(p.getStatus().ordinal()),
                                             p.getMedicineName(),
                                             String.valueOf(p.getAmount())};
        return convertStringArraytoString(StringArray, "-");
    }

    /**
     * To serialise {@code PrescriptionList} object to 0-Paracetamol-10/0-Paracetamol-15/0-Paracetamol-20
     * @param pList {@code PrescriptionList} to be serialised
     * @return formatted {@code PrescriptionList} String
     */
    public static String SerialisePrescriptionList(PrescriptionList pList) {
        if (pList.getCount() == 0) return "Empty";
        String[] StringArray = new String[pList.getCount()];
        for (int i = 0; i < pList.getCount(); i++) StringArray[i] = SerialisePrescription(pList.getPrescription(i));
        return convertStringArraytoString(StringArray,"/");
    }

    /**
     * To serialise {@code Appointment} object to e.g. APT000001/0/Chemo/1001/P_Name/001/D_Name/2024-08-21-16-00/Empty/0-MedicineName1-10/0-MedicineName2-10
     * @param apt {@code Appointment} to be serialised
     * @return formatted {@code Appointment} String
     */
    public static String SerialiseAppointment(Appointment apt) {
        String[] StringArray = new String[] {
                apt.getAppointmentID(),
                String.valueOf(apt.getStatus().ordinal()), apt.getNameOfApt(), String.valueOf(apt.getPatientID()), apt.getPatientName(),
                String.valueOf(apt.getDoctorID()), apt.getDoctorName(), SerialiseDate(apt.getAppointmentTime()), apt.getNotes(),
                SerialisePrescriptionList(apt.getPrescriptionList()),
        };
        return convertStringArraytoString(StringArray, "/");
    }

    /**
     * To serialise {@code Date} object to YYYY-MM-DD-HH-MM 2024-08-21-16-00
     * @param date {@code Date} to be serialised
     * @return formatted {@code Date} String
     */
    public static String SerialiseDate (Date date) {
        String[] StringArray = new String[]
                {String.valueOf(date.getYear()), String.valueOf(date.getMonth()), String.valueOf(date.getDate()),
                        String.valueOf(date.getHours()), String.valueOf(date.getMinutes())};
        return convertStringArraytoString(StringArray, "-");
    }

    /**
     * To serialise {@code MedicineRequest} object to 1001/1001/APT0000000/true/1001
     * @param request {@code MedicineRequest} to be serialised
     * @return formatted {@code MedicineRequest} String
     */
    public static String SerialiseMedicineReq(MedicineRequest request) {
        String[] StringArray = new String[]
                {String.valueOf(request.getPatientID()), String.valueOf(request.getDoctorID()), request.getAppointmentID(),
                String.valueOf(request.isApproved()), String.valueOf(request.getPharmacistID()) };
        return convertStringArraytoString(StringArray, "/");
    }

    /**
     * To serialise {@code RestockRequest} object to 1001/true/1001/1-10/2-20
     * @param request {@code RestockRequest} to be serialised
     * @return formatted {@code RestockRequest} String
     */
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

    /**
     * To serialise {@code MedicineData} object to 1/Paracetamol/20/20
     * @param medicineData {@code MedicineData} to be serialised
     * @return formatted {@code MedicineData} String
     */
    public static String SerialiseMedicineData(MedicineData medicineData) {
        String[] StringArray = new String[]
                {String.valueOf(medicineData.getID()), medicineData.getName(), String.valueOf(medicineData.getAmount()),
                String.valueOf(medicineData.getMinStock()) };
        return convertStringArraytoString(StringArray, "/");
    }

    /**
     * To serialise {@code Contact} object to email@gmail.com|88888888
     * @param contact {@code Contact} to be serialised
     * @return formatted {@code Contact} String
     */
    public static String SerialiseContact (Contact contact) {
        String[] StringArray = new String[] { contact.getEmail(),contact.getContactNumber()};
        return convertStringArraytoString(StringArray, "|");
    }

    /**
     * To serialise {@code Patient} object to PA&1001/Name/2002-08-21-16-00/true/O+/email@gmail.com|88888888
     * @param patient {@code Patient} to be serialised
     * @return formatted {@code Patient} String
     */
    public static String SerialisePatient(Patient patient) {
        String[] StringArray = new String[]
                { String.valueOf(patient.getID()), patient.getName(), SerialiseDate(patient.getDOB()),
                String.valueOf(patient.getGender()), patient.getBloodType(), SerialiseContact(patient.getContact())};
        String Serialised = convertStringArraytoString(StringArray, "/");
        return "PA&" + Serialised;
    }

    /**
     * To serialise {@code Doctor} object to DR&1001/Name/2002-08-21-16-00/true/1241111-true-true-true-true-true|1241120-true-true-true-true-true
     * @param doctor {@code Doctor} to be serialised
     * @return formatted {@code Doctor} String
     */
    public static String SerialiseDoctor(Doctor doctor) {
        String[] StringArray = new String[]
                { String.valueOf(doctor.getID()), doctor.getName(), SerialiseDate(doctor.getDOB()),
                String.valueOf(doctor.getGender()), SerialiseDrAvailability(doctor.getAvailability()) };
        String Serialised = convertStringArraytoString(StringArray, "/");
        return "DR&" + Serialised;
    }

    /**
     * To serialise {@code Map} (availability of the doctor) object to 1241111-true-true-true-true-true|1241120-true-true-true-true-true
     * @param map {@code Map} to be serialised
     * @return formatted {@code Map} String
     */
    public static String SerialiseDrAvailability (Map<Integer, Boolean[]> map){
        if (map.isEmpty()) return "Empty";
        StringBuilder Serialised = new StringBuilder();
        for (Map.Entry<Integer,Boolean[]> o : map.entrySet()){
            Serialised.append(o.getKey());
            for (Boolean b : o.getValue()) {
                Serialised.append("-" + String.valueOf(b));
            }
            Serialised.append("|");
        }
        Serialised.deleteCharAt(Serialised.length()-1);
        return Serialised.toString();
    }

    /**
     * To serialise {@code Pharmacist} object to PH&1001/Name/2002-08-21-16-00/true
     * @param pharmacist {@code Pharmacist} to be serialised
     * @return formatted {@code Pharmacist} String
     */
    public static String SerialisePharmacist(Pharmacist pharmacist) {
        String[] StringArray = new String[]
                { String.valueOf(pharmacist.getID()), pharmacist.getName(), SerialiseDate(pharmacist.getDOB()),
                String.valueOf(pharmacist.getGender()) };
        String Serialised = convertStringArraytoString(StringArray, "/");
        return "PH&" + Serialised;
    }

    /**
     * To serialise {@code Administrator} object to AD&1001/Name/2002-08-21-16-00/true
     * @param administrator {@code Administrator} to be serialised
     * @return formatted {@code Administrator} String
     */
    public static String SerialiseAdministrator(Administrator administrator) {
        String[] StringArray = new String[]
                { String.valueOf(administrator.getID()), administrator.getName(), SerialiseDate(administrator.getDOB()),
                String.valueOf(administrator.getGender()) };
        String Serialised = convertStringArraytoString(StringArray, "/");
        return "AD&" + Serialised;
    }

    // String to Object

    /**
     * To create {@code Prescription} object from serialised data
     * @param Serialised serialised String to be created into {@code Prescription}
     * @return a {@code Prescription}
     */
    public static Prescription DeserialisePrescription(String Serialised) {
        int index = 0;
        String[] Data = Serialised.split("-"); // Converting data into array
        return new Prescription(MED_STATUS.values()[Integer.parseInt(Data[index++])],Data[index++], Integer.parseInt(Data[index++]));
    }

    /**
     * To create {@code PrescriptionList} object from serialised data
     * @param Serialised serialised String to be created into {@code PrescriptionList}
     * @return a {@code PrescriptionList}
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
     * To create {@code Appointment} object from serialised data
     * @param Serialised serialised String to be created into {@code Appointment}
     * @return a {@code Appointment}
     */
    public static Appointment DeserialiseAppointment(String Serialised) {
        int index = 0;
        String[] Data = Serialised.split("/");
        String aptID = Data[index++];
        System.out.println(Data[index]);
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
            try {
                Prescription prescription = DeserialisePrescription(Data[index++]);
                list.addPrescription(prescription);
            } catch (IndexOutOfBoundsException e) {
                break;
            }
        }
        return new Appointment(status, nameOfApt, patientID, patientName, doctorID, doctorName, appointmentTime, notes, list, aptID );
    }

    /**
     * To create {@code Date} object from serialised data
     * @param Serialised serialised String to be created into {@code Date}
     * @return a {@code Date}
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

    /**
     * To create {@code MedicineRequest} object from serialised data
     * @param Serialised serialised String to be created into {@code MedicineRequest}
     * @return a {@code MedicineRequest}
     */
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

    /**
     * To create {@code RestockRequest} object from serialised data
     * @param Serialised serialised String to be created into {@code RestockRequest}
     * @return a {@code RestockRequest}
     */
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

    /**
     * To create {@code MedicineData} object from serialised data
     * @param Serialised serialised String to be created into {@code MedicineData}
     * @return a {@code MedicineData}
     */
    public static MedicineData DeserialiseMedicineData(String Serialised) {
        int index = 0;
        String[] Data = Serialised.split("/");
        MedicineData medicineData = new MedicineData( Integer.parseInt(Data[index++]), Data[index++],
                Integer.parseInt(Data[index++]), Integer.parseInt(Data[index++]));
        return medicineData;
    }

    /**
     * To create {@code Contact} object from serialised data
     * @param Serialised serialised String to be created into {@code Contact}
     * @return a {@code Contact}
     */
    public static Contact DeserialiseContact(String Serialised) {
        String[] StringArray = Serialised.split("\\|");
        return new Contact(StringArray[0],StringArray[1]);
    }

    /**
     * To create {@code Patient} object from serialised data
     * @param Serialised serialised String to be created into {@code Patient}
     * @return a {@code Patient}
     */
    public static Patient DeserialisePatient(String Serialised) {
         String[] StringArray = Serialised.split("/");
         int index = 0;
         int ID = Integer.parseInt(StringArray[index++]);
         String name = StringArray[index++];
         Date DOB = DeserialiseDate(StringArray[index++]);
         boolean Gender = Boolean.parseBoolean(StringArray[index++]);
         String bloodType = StringArray[index++];
         Contact contact = DeserialiseContact(StringArray[index++]);
         return new Patient(ID, name, DOB, Gender, bloodType, contact);
    }

    /**
     * To create {@code Doctor} object from serialised data
     * @param Serialised serialised String to be created into {@code Doctor}
     * @return a {@code Doctor}
     */
    public static Doctor DeserialiseDoctor(String Serialised) {
         String[] StringArray = Serialised.split("/");
         int index = 0;
         int ID = Integer.parseInt(StringArray[index++]);
         String name = StringArray[index++];
         Date DOB = DeserialiseDate(StringArray[index++]);
         boolean Gender = Boolean.parseBoolean(StringArray[index++]);
         HashMap<Integer, Boolean[]> map = DeserialiseDrAvailability(StringArray[index++]);
         return new Doctor(ID, name, DOB, Gender, map);
    }

    /**
     * To create {@code Map} object for doctor's availability from serialised data
     * @param Serialised serialised String to be created into {@code Map}
     * @return a {@code Map}
     */
    public static HashMap<Integer, Boolean[]> DeserialiseDrAvailability(String Serialised){
        if (Serialised.equals("Empty")) return new HashMap<>();
        String[] StringArray = Serialised.split("\\|");
        HashMap<Integer, Boolean[]> map = new HashMap<>();
        for (String s : StringArray) {
            int index = 0;
            String[] DataArray = s.split("-");
            int key = Integer.parseInt(DataArray[index++]);
            Boolean[] slot = new Boolean[] {
                    Boolean.parseBoolean(DataArray[index++]),
                    Boolean.parseBoolean(DataArray[index++]),
                    Boolean.parseBoolean(DataArray[index++]),
                    Boolean.parseBoolean(DataArray[index++]),
                    Boolean.parseBoolean(DataArray[index++]),
            };
            map.put(key,slot);
        }
        return map;
    }

    /**
     * To create {@code Pharmacist} object from serialised data
     * @param Serialised serialised String to be created into {@code Pharmacist}
     * @return a {@code Pharmacist}
     */
    public static Pharmacist DeserialisePharmacist(String Serialised) {
         String[] StringArray = Serialised.split("/");
         int index = 0;
         int ID = Integer.parseInt(StringArray[index++]);
         String name = StringArray[index++];
         Date DOB = DeserialiseDate(StringArray[index++]);
         boolean Gender = Boolean.parseBoolean(StringArray[index++]);
         return new Pharmacist(ID, name, DOB, Gender);
    }

    /**
     * To create {@code Administrator} object from serialised data
     * @param Serialised serialised String to be created into {@code Administrator}
     * @return a {@code Administrator}
     */
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
        if (StringArray.length == 0) return "";
        StringBuilder sb = new StringBuilder();
        for (String str : StringArray)
            sb.append(str).append(delimiter);
        return sb.substring(0, sb.length() - 1);
    }
}
