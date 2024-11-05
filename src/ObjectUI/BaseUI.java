package ObjectUI;

import DepartmentObject.Pharmacy;
import DepartmentObject.UserInfoDatabase;
import Singleton.ServerHMS;

public interface BaseUI {
    public UserInfoDatabase database = ServerHMS.getInstance().getDatabase();
    public Pharmacy pharmacy = ServerHMS.getInstance().getPharmacy();
}
