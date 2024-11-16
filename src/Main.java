import DataObject.Appointment.Appointment;
import DataObject.PharmacyObjects.MedicineRequest;
import DataObject.PharmacyObjects.RestockRequest;
import DataObject.Prescription.Prescription;
import DepartmentObject.*;
import HumanObject.Administrator.Administrator;
import HumanObject.Doctor.Doctor;
import HumanObject.Patient.Patient;
import HumanObject.Pharmacist.Pharmacist;
import InputHandler.Input;
import ObjectUI.*;
import Serialisation.DataEncryption;
import Serialisation.DataSerialisation;
import Singleton.ServerHMS;


public class Main {
    public static void main(String[] args) {

        ServerHMS.setMode(Input.ScanBoolean("Run debug mode?"));

        Input.ClearConsole();
        AccountInfoDatabase login = ServerHMS.getInstance().getLogin();
        UserInfoDatabase database = ServerHMS.getInstance().getDatabase();
        int choice;
        do {
            Input.ClearConsole();
            System.out.println("Welcome to HMS\n1. Login\n2. Change password\n3. Register as patient\n4. Exit program");
            choice = Input.ScanInt("Choose one option:");
            switch (choice) {
                case 1: // Logging In
                    String UserID = login.login();
                    if (UserID == null) break;
                    BaseUI UI_interface = UICreator.createUI(database.getPerson(UserID));
                    break;
                case 2: // Changing Password
                    login.changePassword();
                    break;
                case 3: // Register Patient
                    login.addNewAccount(database.registerPatient());
                    break;
                default:
                    break;
            }

        } while (choice < 4);
        ServerHMS.getInstance().closeServer();
    }
}