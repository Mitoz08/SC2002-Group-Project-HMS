package Serialisation;

public class DataEncryption {
    public static String Encrpyt (String text){
        text = text.toUpperCase();
        String key = "SMACBEST";
        String encrypted = "";
        int j =0;
        int i;
        for (i=0; i<text.length(); i++){
            char ch = text.charAt(i);
            if (ch < 'A' || ch > 'Z'){
                encrypted += ch;
                continue;
            }
            encrypted += (char)((ch + key.charAt(j)-2*'A')%26 + 'A'); //LHS looks at how much it needs to be incremented, RHS is the base 'A'
            j++; //looks at the next key
            if (j == key.length()){
                j = 0; //Rerun the key again
            }

        }
        return encrypted;
    }
    public static String Decrypt (String text){
        text = text.toUpperCase();
        String key = "SMACBEST";
        String decrypted = "";
        int j=0;
        int i;

        for (i=0; i<text.length(); i++){
            char ch = text.charAt(i);
            if (ch < 'A' || ch > 'Z'){
                decrypted += ch;
                continue;
            }
            decrypted += (char)((ch - key.charAt(j)+26)%26 + 'A'); //LHS looks at how much needs to be decremented, RHS is the base
            j++; //looks at the next key
            if (j==key.length()){
                j=0; //Rerun the key again
            }
        }
        return decrypted;
    }

}
