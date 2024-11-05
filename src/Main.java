import DepartmentObject.*;
import HumanObject.Administrator.Administrator;
import HumanObject.Doctors.Doctors;
import HumanObject.Patient.Patient;
import HumanObject.Pharmacist.Pharmacist;
import InputHandler.Input;
import ObjectUI.*;
import Singleton.ServerHMS;


public class Main {
    public static void main(String[] args) {
        Input.ClearConsole();
        AccountInfoDatabase login = ServerHMS.getInstance().getLogin();
        UserInfoDatabase database = ServerHMS.getInstance().getDatabase();
        int choice;

        do {
            System.out.println("Welcome to HMS\n1. Login\n2. Change password\n3. Exit program");
            choice = Input.ScanInt("Choose one option:");
            switch (choice) {
                case 1:
                    String UserID = login.login();
                    String role = UserID.substring(0,2);
                    int ID = Integer.parseInt(UserID.substring(2));
                    switch (role) {
                        case "PA":
                            for (Patient p : database.getPatients()) {
                                if (p.getID() == ID) new PatientUI(p);
                            }
                            break;
                        case "DR":
                            for (Doctors d : database.getDoctors()) {
                                if (d.getID() == ID) new DoctorUI(d);
                            }
                            break;
                        case "PH":
                            for (Pharmacist p : database.getPharmacists()) {
                                if (p.getID() == ID) new PharmacistUI(p);
                            }
                            break;
                        case "AD":
                            for (Administrator a : database.getAdministrators()) {
                                if (a.getID() == ID) new AdminUI(a);
                            }
                            break;
                    }
                    break;
                case 2:
                    login.changePassword();
                    break;
                default:
                    break;
            }

        } while (choice < 3);
        ServerHMS.getInstance().closeServer();
    }
}