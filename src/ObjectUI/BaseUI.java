package ObjectUI;

import DepartmentObject.Pharmacy;
import DepartmentObject.UserInfoDatabase;
import Singleton.ServerHMS;

/** A Base Interface to keep the default {@code Variables} and {@code Methods} a User Interface should have*/
public interface BaseUI {
    /** {@code UserInfoData} object used by all the User Interfaces*/
    public UserInfoDatabase database = ServerHMS.getInstance().getDatabase();

    /** {@code Pharmacy} object used by all the User Interfaces*/
    public Pharmacy pharmacy = ServerHMS.getInstance().getPharmacy();
}
