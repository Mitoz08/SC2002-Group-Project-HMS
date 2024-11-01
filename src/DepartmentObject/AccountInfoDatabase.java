package DepartmentObject;

import HumanObject.BasePerson;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class AccountInfoDatabase {

    //Account info comes with username,Password,role,id
    private static String[] verifyLogin(String username, String Password){
        String line;
        String[] empty = new String[4];
        String[] parts = new String[4];
        try{
            BufferedReader reader = new BufferedReader(new FileReader("HMSAccount.txt"));
            while ((line = reader.readLine()) != null){
                parts = line.split("[,]");
                if (parts[0].equals(username) && parts[1].equals(Password)){
                    return parts;
                }
            }
        }catch(IOException e){
            e.printStackTrace();
        }
        return empty;
    }

}
