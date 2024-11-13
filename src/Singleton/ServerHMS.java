package Singleton;

import DepartmentObject.AccountInfoDatabase;
import DepartmentObject.Pharmacy;
import DepartmentObject.UserInfoDatabase;

/**
 * A Singleton Class to ensure that only one instance of {@code AccountInfoDatabase}, {@code UserInfoDatabase} and {@code Pharmacy}
 * is present. It also acts as a global variable for the User Interface classes to access
 * <p>
 *     Allows the user to run debug mode that always runs with default data in the system for testing
 * </p>
 */
public class ServerHMS {

    private static ServerHMS instance = null;
    private static boolean mode;

    private UserInfoDatabase database;
    private AccountInfoDatabase login;
    private Pharmacy pharmacy;

    /**
     * Sets the boolean to {@code true} if debug mode is on
     * @param Debug boolean for debug mode
     */
    public static void setMode(boolean Debug) { mode = Debug;}

    /**
     * Private constructor to ensure this constructor can only be called in the class itself
     */
    private ServerHMS() {
        this.login = new AccountInfoDatabase();
        this.database = new UserInfoDatabase();
        this.pharmacy = new Pharmacy();

        if (mode) {
            database.setHMSFileName("Debug_HMS.txt");
            database.setAPTFileName("Debug_APT.txt");
            pharmacy.setPharmacyFileName("Debug_Pharmacy.txt");
            login.setFileName("Debug_Login.txt");
            database.testRun();
            pharmacy.testRun();
            login.testRun();
        } else {
            database.setHMSFileName("HMS.txt");
            database.setAPTFileName("APT.txt");
            pharmacy.setPharmacyFileName("Pharmacy.txt");
            login.setFileName("Login.txt");
            database.loadFile(); // Change back to private before submitting code
            pharmacy.loadFile();
        }
    }

    /**
     * Gets the reference to the {@code UserInfoDatabase}
     * @return the reference to the {@code UserInfoDatabase}
     */
    public UserInfoDatabase getDatabase() {
        return database;
    }

    /**
     * Gets the reference to the {@code AccountInfoDatabase}
     * @return the reference to the {@code AccountInfoDatabase}
     */
    public AccountInfoDatabase getLogin() {
        return login;
    }

    /**
     * Gets the reference to the {@code Pharmacy}
     * @return the reference to the {@code Pharmacy}
     */
    public Pharmacy getPharmacy() {
        return pharmacy;
    }

    /**
     * Calls the {@code endPharmacy()} and {@code endUserDatabase()} function for saving
     *
     * @see Pharmacy#endPharmacy()                  End Pharmacy function
     * @see UserInfoDatabase#endUserInfoDatabase()  End UserInfoDatabase function
     */
    public void closeServer() {
        this.pharmacy.endPharmacy();
        this.database.endUserInfoDatabase();
    }

    /**
     * Public method to get the instance of the {@code ServerHMS}, if {@code null} the private constructor is called
     * @return the reference to {@code ServerHMS}
     */
    public static synchronized ServerHMS getInstance() {
        if (instance == null) instance = new ServerHMS();
        return instance;
    }

}
