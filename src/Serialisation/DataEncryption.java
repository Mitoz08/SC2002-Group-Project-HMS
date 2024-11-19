package Serialisation;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

/**
 * A Class to encrypt and decrypt datas
 */
public class DataEncryption {

    /** The keys used for encrypting the text. */
    private static String[] key = new String[] {"SMACBEST", "OBJECT", "ORIENTED", "PROGRAMMING"};

    /** The number of encryption key. */
    private static int noOfKey = 4;

    /**
     * A vigenere cipher that encrypts the given input {@code text} with a keyword
     * @param text text to be encrypted
     * @return an encrypted text
     */
    public static String cipher(String text) {
        String keyWord = key[text.length()%noOfKey].toUpperCase();
        String cipherText = "";

        for (int i = 0; i < text.length(); i++) {
            char ch = text.charAt(i);
            ch += (char) (keyWord.charAt(i%keyWord.length()) - 'A');
            cipherText += ch;
        }
        return cipherText;
    }

    /**
     * A vigenere cipher that encrypts the given input {@code text} with a keyword
     * @param text text to be encrypted
     * @param value unique value to determine the keyword
     * @return an encrypted text
     */
    public static String cipher(String text, int value) {
        String keyWord = key[value%noOfKey].toUpperCase();
        String cipherText = "";

        for (int i = 0; i < text.length(); i++) {
            char ch = text.charAt(i);
            ch += (char) (keyWord.charAt(i%keyWord.length()) - 'A');
            cipherText += ch;
        }
        return cipherText;
    }

    /**
     * A vigenere cipher that decrypts the given input {@code text} with a keyword
     * @param text text to be decrypted
     * @return a formatted data String
     */
    public static String decipher(String text) {
        String keyWord = key[text.length()%noOfKey].toUpperCase();
        String decipherText = "";

        for (int i = 0; i < text.length(); i++) {
            char ch = text.charAt(i);
            ch -= (char) (keyWord.charAt(i%keyWord.length()) - 'A');
            decipherText += ch;
        }
        return decipherText;
    }

    /**
     * A vigenere cipher that decrypts the given input {@code text} with a keyword
     * @param text text to be decrypted
     * @param value unique value to determine the keyword
     * @return a formatted data String
     */
    public static String decipher(String text, int value) {
        String keyWord = key[value%noOfKey].toUpperCase();
        String decipherText = "";

        for (int i = 0; i < text.length(); i++) {
            char ch = text.charAt(i);
            ch -= (char) (keyWord.charAt(i%keyWord.length()) - 'A');
            decipherText += ch;
        }
        return decipherText;
    }

    /**
     * A SHA256 encryption for one-way encryption
     * @param text text to be encrypted
     * @return a encrypted string
     */
    public static String SHA3(String text) {
        String sha3Hex = null;
        try {
            final MessageDigest digest = MessageDigest.getInstance("SHA3-256");
            final byte[] hashbytes = digest.digest(
                    text.getBytes(StandardCharsets.UTF_8));
            sha3Hex = bytesToHex(hashbytes);
            return sha3Hex;
        } catch (Exception e) {
            System.out.println("Error with encryption");
        }
        return sha3Hex;
    }

    /**
     * Converts byte String to String
     * @param hash bytes to be converted
     * @return a Hexadecimal String
     */
    private static String bytesToHex(byte[] hash) {
        StringBuilder hexString = new StringBuilder(2 * hash.length);
        for (int i = 0; i < hash.length; i++) {
            String hex = Integer.toHexString(0xff & hash[i]);
            if(hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }
}
