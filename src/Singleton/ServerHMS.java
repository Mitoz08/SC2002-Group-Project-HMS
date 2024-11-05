package Singleton;

import DepartmentObject.AccountInfoDatabase;
import DepartmentObject.Pharmacy;
import DepartmentObject.UserInfoDatabase;

public class ServerHMS {

    private static ServerHMS instance = null;

    private UserInfoDatabase database;
    private AccountInfoDatabase login;
    private Pharmacy pharmacy;

    private ServerHMS() {
        this.database = new UserInfoDatabase();
        this.login = new AccountInfoDatabase();
        this.pharmacy = new Pharmacy();
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

    }

    public static synchronized ServerHMS getInstance() {
        if (instance == null) instance = new ServerHMS();
        return instance;
    }

}
