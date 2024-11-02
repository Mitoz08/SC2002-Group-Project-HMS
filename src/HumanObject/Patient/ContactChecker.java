package HumanObject.Patient;
import InputHandler.Input;

import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class ContactChecker {
    private Contact contact;
    private final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$";
    private final String SG_PHONE_REGEX = "^(\\+65)?[689]\\d{7}$";

    public boolean checkValidEmail(String email){
        Pattern pattern = Pattern.compile(EMAIL_REGEX);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public boolean checkValidSingaporePhone(String phone) {
        Pattern pattern = Pattern.compile(SG_PHONE_REGEX);
        Matcher matcher = pattern.matcher(phone);
        return matcher.matches();
    }
}
