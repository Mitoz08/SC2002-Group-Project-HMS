package DepartmentObject;

import HumanObject.BasePerson;
import HumanObject.ROLE;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class AccountInfoDatabase {

    private ArrayList<String> usernameList;
    private ArrayList<String> PasswordList;
    private ArrayList<ROLE> roleList;
    private ArrayList<Integer> idList;


    // will be called in the start of the main function this inputs can be gathered by reading the HMSAccount.txt first
    public AccountInfoDatabase(ArrayList<String> username,ArrayList<String> Password,ArrayList<ROLE> role,ArrayList<Integer> id){
        usernameList = username;
        PasswordList = Password;
        roleList = role;
        idList = id;
    }

    /*Account info comes with username,Password,role,id KIV TO DELETE
    private String[] verifyLogin(String username, String Password){
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
    public BaseUI login(String user, String Password, ROLE role){

        for (int i=0; i<idList.size(); i++){
            if (idList.get(i).equals(user) && PasswordList.get(i).equals(Password) && roleList.get(i).equals(role)){
                return BaseUI();

            }

        }

    }

     */

}
