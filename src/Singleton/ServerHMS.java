package Singleton;

import DepartmentObject.AccountInfoDatabase;
import DepartmentObject.Pharmacy;
import DepartmentObject.UserInfoDatabase;

public class ServerHMS {

    private static ServerHMS instance = null;
    private static boolean mode;

    private UserInfoDatabase database;
    private AccountInfoDatabase login;
    private Pharmacy pharmacy;

    public static void setMode(boolean Debug) { mode = Debug;}

    private ServerHMS() {
        this.login = new AccountInfoDatabase();
        this.database = new UserInfoDatabase();
        this.pharmacy = new Pharmacy();

        if (mode) {
            database.testRun();
            pharmacy.testRun();
            login.testRun();
        } else {
            database.loadFile(); // Change back to private before submitting code
            pharmacy.loadFile();
        }
    }

    public UserInfoDatabase getDatabase() {
        return database;
    }

    public AccountInfoDatabase getLogin() {
        return login;
    }

    public Pharmacy getPharmacy() {
        return pharmacy;
    }

    public void closeServer() {
        this.pharmacy.endPharmacy();
        this.database.endUserInfoDatabase();
    }

    public static synchronized ServerHMS getInstance() {
        if (instance == null) instance = new ServerHMS();
        return instance;
    }

}
