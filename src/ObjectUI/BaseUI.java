package ObjectUI;

import DepartmentObject.Pharmacy;
import DepartmentObject.UserInfoDatabase;
import Singleton.ServerHMS;

public class BaseUI {

    protected UserInfoDatabase database;
    protected Pharmacy pharmacy;

    public BaseUI() {
        database = ServerHMS.getInstance().getDatabase();
        pharmacy = ServerHMS.getInstance().getPharmacy();
    }
}
