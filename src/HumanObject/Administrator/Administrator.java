package HumanObject.Administrator;

import DepartmentObject.UserInfoDatabase;
import HumanObject.BasePerson;
import HumanObject.Doctors.Doctors;
import HumanObject.Pharmacist.Pharmacist;
import HumanObject.ROLE;

import java.util.ArrayList;
import java.util.Date;

public class Administrator extends BasePerson {
    private static int lastID = 0;

    public Administrator() {
        super();
        this.ID = lastID++;
        this.role = ROLE.PHARMACIST;
    }

    public Administrator (String Name, Date DOB, Boolean Gender){
        super(lastID++,Name,DOB,Gender);
        this.role = ROLE.ADMINISTRATOR;

    }
    public Administrator(int ID, String Name, Date DOB, Boolean Gender){
        super(ID,Name,DOB,Gender);
        this.role = ROLE.ADMINISTRATOR;
    }

    public static void setLastID(int lastID1){
        lastID = lastID1;
    }
    public void getStaffList() {
        UserInfoDatabase database = new UserInfoDatabase();
        ArrayList<Doctors> doctors = database.getDoctors();
        ArrayList<Pharmacist> pharmacists = database.getPharmacists();
        ArrayList<Administrator> administrators = database.getAdministrators();
        for (Doctors doc: doctors){
            System.out.println("Name: "+ doc.getName() + ", ID: " + doc.getID() + ", Role: " + doc.getRole());
        }
        for (Pharmacist ph: pharmacists){
            System.out.println("Name: "+ ph.getName() + ", ID: " + ph.getID() + ", Role: " + ph.getRole());
        }
        for (Administrator ad: administrators){
            System.out.println("Name: "+ ad.getName() + ", ID: " + ad.getID() + ", Role: " + ad.getRole());
        }
        return;
    }

    public void addPatient(){
        //Uses updateFile
        //remember to update number of Patients

    }
    public void addStaff(){
        //Uses updateFile
        //remember to add number of staff(Doctors, Pharmacists, Administrator respectively)

    }
    public void fireStaff(){
        //Uses updateFile
        //remember to add number of staff(Doctors, Pharmacists, Administrator respectively)
    }

}
