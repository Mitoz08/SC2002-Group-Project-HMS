package DepartmentObject;

import java.io.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import DataObject.Appointment.Appointment;
import DataObject.Appointment.AppointmentList;
import DataObject.Prescription.Prescription;
import DataObject.Prescription.PrescriptionList;
import HumanObject.ROLE;
import Serialisation.DataEncryption;
import Serialisation.DataSerialisation;

public class UserInfoDatabase {

    //Class method to read file and turn it into an ArrayList to be used
    private static ArrayList<String> readFile(){
        int i=0;
        ArrayList<String>strArray = new ArrayList<>();
        try {
            BufferedReader reader = new BufferedReader((new FileReader("HMS.txt")));
            String line;
            while ((line = reader.readLine()) != null){
                strArray.add(i++, line);
            }
            reader.close();
        }catch (IOException e) {
            e.printStackTrace();
        }
        return strArray;

    }
    public static void getStaffList() throws ParseException {
        int j =0;
        String[] parts = new String[11];
        ArrayList<String> strArray = new ArrayList<String>();
        strArray = UserInfoDatabase.readFile();
        //To parse the data into separate ArrayList

        // Only outputs id, names and role
        ArrayList<ROLE> role = new ArrayList<ROLE>(); // parts[0]
        ArrayList<Integer> id = new ArrayList<Integer>(); // parts[1]
        ArrayList<String> names = new ArrayList<String>(); // parts[2]

        for (String str: strArray){
            parts = str.split("[*]");
            role.add(ROLE.valueOf(DataEncryption.Decrypt(parts[0])));
            id.add(Integer.parseInt(DataEncryption.Decrypt(parts[1])));
            names.add(DataEncryption.Decrypt(parts[2]));

        }
        while (!id.isEmpty()) {
            if (role.get(j).equals(ROLE.DOCTOR) || role.get(j).equals(ROLE.ADMINISTRATOR) || role.get(j).equals(ROLE.PHARMACIST)) {
                System.out.println("Name: " + names.get(j) + ", ID: " + id.get(j) + ", Role: " + role.get(j));
                j++;
                //could have a possible error of not printing as when Decrypting we set everything to UPPERCASE
            }
        }
        return;

    }
    public static void scheduleApt(Appointment apt){

    }
    public static void rescheduleApt(Appointment newApt, Appointment oldApt){

    }
    public static void cancelApt(Appointment toCancelApt){

    }
    /*
    serialiseData (for Patients) will have the following String
    serialiseData is being called in updateFile()
    There are 11 parts of info in this string
    Role*id*Name*DOB*Gender*BloodType*Contact|Contact*doctor|doctor|doctor|...*apt|apt|apt|...|apt*apt|apt|apt|apt|...|apt*pre|pre|pre|...|pre|*

    private static String serialiseData(ROLE role, int id, String name, Date DOB, Boolean Gender, String BloodType, String[] Contact, ArrayList<String> DoctorAssigned, AppointmentList onGoingAptList, AppointmentList completeAptList, PrescriptionList prescripList){
        StringBuilder str = new StringBuilder();
        str.append(role).append("*");
        str.append(id).append("*");
        str.append(name).append("*");
        str.append(DOB).append("*");
        str.append(Gender).append("*");
        str.append(BloodType).append("*");
        for (String contact: Contact){
            str.append(contact).append("|");
        }
        str.append("*");
        for (String doctor: DoctorAssigned){
            str.append(doctor).append("|");
        }
        str.append("*");
        for (Appointment apt: onGoingAptList){
            str.append(apt).append("|");
        }
        str.append("*");
        for (Appointment apt: completeAptList){
            str.append(apt).append("|");
        }
        str.append("*");
        for (Prescription pre: prescripList){
            str.append(pre).append("|");
        }
        str.append("*");
        return str.toString();

    }
     */
    /*serialiseData (for doctors)  will have the following string
    There are 8 parts to the string
    Role*id*Name*DOB*Gender*BloodType*apt|apt|apt|...|apt*apt|apt|apt|apt|...|apt*availability|availability|...availability|*
    private static String serialiseData(ROLE role, int id, String name, Date DOB, Boolean Gender, AppointmentList onGoingAptList, AppointmentList completeAptList){
        StringBuilder str = new StringBuilder();
        str.append(role).append("*");
        str.append(id).append("*");
        str.append(name).append("*");
        str.append(DOB).append("*");
        str.append(Gender).append("*");
        for (Appointment apt: onGoingAptList){
            String aptTemp = DataSerialisation.SerialiseAppointment(apt);
            str.append(aptTemp).append("|");
        }
        str.append("*");
        for (Appointment apt: completeAptList){
            String aptTemp = DataSerialisation.SerialiseAppointment(apt);
            str.append(aptTemp).append("|");
        }
        str.append("*");

        Have to add the boolean availability 7x5 matrix

        str.append("*");
        return str.toString();

    }

    serialiseData (for administrators and pharmacists) will have the following string
    Role*id*Name*DOB*Gender*
    private static String serialiseData(ROLE role, int id, String name, Date DOB, Boolean Gender){
        StringBuilder str = new StringBuilder();
        str.append(role).append("*");
        str.append(id).append("*");
        str.append(name).append("*");
        str.append(DOB).append("*");
        str.append(Gender).append("*");
        return str.toString();

    }

    addToFile is used when someone creates a new user
    public static void addToFile(){
       String encrypt //TO PUT INTO FILE
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("HMS.txt", true));
            writer.write(encrypt + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
            return;
    }
     */


}
