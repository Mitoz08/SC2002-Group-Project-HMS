package HumanObject.Patient;


import InputHandler.Input;

/**
 * The {@code Contact} class represents a patient's contact information, including
 * an email address and a contact number. It provides methods for setting and validating
 * these attributes using user input.
 *
 * @see ContactChecker
 */
public class Contact {

    /** The email address of the contact. */
    private String email;

    /** The contact number of the contact. */
    private String contact_number;

    /**
     * Default constructor that initializes a {@code Contact} object by prompting
     * the user for an email address and a contact number.
     * The input is validated before being set.
     */
    public Contact() {
        setEmail();
        setContactNumber();
    }

    /**
     * Constructs a {@code Contact} object with the specified email and contact number.
     *
     * @param email          The email address of the contact.
     * @param contact_number The contact number of the contact.
     */
    public Contact(String email, String contact_number){
        this.email = email;
        this.contact_number = contact_number;
    }

    /**
     * Sets the contact number by prompting the user for input.
     * The input is validated using {@code ContactChecker.checkValidSingaporePhone()}.
     * If the input is invalid, the user is prompted to try again until a valid number is entered.
     */
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

    /**
     * Sets the email address by prompting the user for input.
     * The input is validated using {@code ContactChecker.checkValidEmail()}.
     * If the input is invalid, the user is prompted to try again until a valid email address is entered.
     */
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

    /**
     * Returns the email address of the contact.
     *
     * @return The email address of the contact.
     */
    public String getEmail(){
        return this.email;
    }

    /**
     * Returns the contact number of the contact.
     *
     * @return The contact number of the contact.
     */
    public String getContactNumber(){
        return this.contact_number;
    }


}
