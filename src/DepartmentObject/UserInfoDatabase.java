package DepartmentObject;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import HumanObject.ROLE;
import Serialisation.DataEncryption;

public class UserInfoDatabase {

    public static ArrayList<String> readFile(){
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


    public static String serialiseData(String username,int id, String name, Date DOB, Boolean Gender, ROLE role){
        StringBuilder str = new StringBuilder();
        str.append(username).append("*");
        str.append(id).append("&");
        str.append(name).append("%");
        str.append(id).append("#");
        str.append(id).append("!");
        str.append(id).append("$");

        return str.toString();

    }
    public static void getAllData(String username){
        //check if user not in AccountDatabase, !AccountDatabase.search(), dont do anything
        //else below





    return;//output is a ArrayList<String> that is the userinfo

    }
    public static void getID(String username){

        return; //output is an int id
    }
    public static void getName(String username){

        return; //output is a String name
    }
    public static void getDOB(String username){

        return;//output is a Date
    }
    public static void getGender(String username){

        return;//output is a boolean
    }
    public static void getRole(String username){

        return;//output is a String
    }

    public static void getStaffList() throws ParseException {
        int j =0;
        ArrayList<String> strArray = new ArrayList<String>();
        strArray = UserInfoDatabase.readFile();
        //To parse the data into separate ArrayList
        ArrayList<String> username = new ArrayList<String>();
        ArrayList<Integer> id = new ArrayList<Integer>();
        ArrayList<String> names = new ArrayList<String>();
        ArrayList<Date> DOB = new ArrayList<Date>();
        ArrayList<Boolean> Gender = new ArrayList<Boolean>();
        ArrayList<ROLE> role = new ArrayList<ROLE>();
        for (String str: strArray){
            String[] parts = str.split("[*&%#!$]");
            if (parts.length == 6){
                username.add(DataEncryption.Decrypt(parts[0]));
                id.add(Integer.parseInt(DataEncryption.Decrypt(parts[1]))); //Decrypts them and adds them into its respective ArrayList
                names.add(DataEncryption.Decrypt(parts[2]));
                Date date = new SimpleDateFormat("yyyy-MM-dd").parse(DataEncryption.Decrypt(parts[3]));
                DOB.add(date);
                Gender.add(DataEncryption.Decrypt(parts[4]).equals("1"));
                role.add(ROLE.valueOf(DataEncryption.Decrypt(parts[5])));
            }
            else {
                System.out.println("Out of bounds error"); //debug
            }
            while (!id.isEmpty()){
                if (role.get(j).equals(ROLE.DOCTOR) || role.get(j).equals(ROLE.ADMINISTRATOR) || role.get(j).equals(ROLE.PHARMACIST)){
                    System.out.println("Name: " + names.get(j) + ", Role: " + role.get(j));

                }
            }
        }
        return;

    }
    public static void getAllAppointment(){

        return;
    }

}
