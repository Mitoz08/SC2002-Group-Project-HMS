package Serialisation;

import DataObject.Appointment.APT_STATUS;
import DataObject.Appointment.Appointment;
import DataObject.Prescription.MED_STATUS;
import DataObject.Prescription.Prescription;
import DataObject.Prescription.PrescriptionList;

import java.sql.Statement;
import java.util.Date;
import java.util.Scanner;

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


}
