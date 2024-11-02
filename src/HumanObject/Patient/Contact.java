package HumanObject.Patient;


public class Contact {
    private String email;
    private String contact_number;


    public Contact(String email, String contact_number){
        this.email = email;
        this.contact_number = contact_number;
    }

    public void setContact_number(String contact_number){
        this.contact_number = contact_number;
    }

    public void setEmail(String email){
        this.email = email;
    }

    public String getEmail(){
        return this.email;
    }

    public String getContact_number(){
        return this.contact_number;
    }


}
