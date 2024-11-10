package Serialisation;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

public class DataEncryption {

    private static String[] key = new String[] {"SMACBEST", "OBJECT", "ORIENTED", "PROGRAMMING"};
    private static int noOfKey = 4;


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
