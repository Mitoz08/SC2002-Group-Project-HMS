package HumanObject.Patient;


import InputHandler.Input;

public class Contact {
    private String email;
    private String contact_number;


    public Contact() {
        setEmail();
        setContactNumber();
    }

    public Contact(String email, String contact_number){
        this.email = email;
        this.contact_number = contact_number;
    }

    public void setContactNumber(){
        String contactNo;
        do{
            contactNo = Input.ScanString("Enter a new contact number: ");
            if (ContactChecker.checkValidSingaporePhone(contactNo)) {
                this.contact_number = contactNo;
            }
            else {
                System.out.println("That isn't a valid contact number, try again");
            }
        }
        while (!ContactChecker.checkValidSingaporePhone(contactNo));
    }

    public void setEmail(){
        String email;
        do{
            email = Input.ScanString("Enter a new email address: ");
            if (ContactChecker.checkValidEmail(email)) {
                this.email = email;
            }
            else {
                System.out.println("That isn't a valid email address, try again");
            }
        }
        while (!ContactChecker.checkValidEmail(email));
    }

    public String getEmail(){
        return this.email;
    }

    public String getContactNumber(){
        return this.contact_number;
    }


}
