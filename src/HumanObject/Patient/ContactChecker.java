package HumanObject.Patient;


import java.util.regex.Pattern;
import java.util.regex.Matcher;

/**
 * The {@code ContactChecker} class provides utility methods for validating contact
 * information, such as email addresses and Singapore phone numbers.
 * It uses regular expressions to perform the validation.
 */
public class ContactChecker {

    /** Regular expression pattern for validating email addresses. */
    private static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$";

    /** Regular expression pattern for validating Singapore phone numbers. */
    private static final String SG_PHONE_REGEX = "^(\\+65)?[689]\\d{7}$";

    /**
     * Validates the given email address using a regular expression pattern.
     *
     * @param email The email address to validate.
     * @return {@code true} if the email address is valid, {@code false} otherwise.
     */
    public static boolean checkValidEmail(String email){
        Pattern pattern = Pattern.compile(EMAIL_REGEX);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    /**
     * Validates the given Singapore phone number using a regular expression pattern.
     * A valid Singapore phone number starts with a '+65' country code (optional),
     * followed by an 8-digit number starting with 6, 8, or 9.
     *
     * @param phone The Singapore phone number to validate.
     * @return {@code true} if the phone number is valid, {@code false} otherwise.
     */
    public static boolean checkValidSingaporePhone(String phone) {
        Pattern pattern = Pattern.compile(SG_PHONE_REGEX);
        Matcher matcher = pattern.matcher(phone);
        return matcher.matches();
    }
}
