package framework.utility.Helper;

import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class PasswordEncryptionDecryption {
    private static final int key =5;

    public static void main(String[] args) throws NoSuchAlgorithmException {
        String password = "<password>";
        String encryptedString = getEncryptedString(password);
        System.out.println("password: "+password);
        System.out.println("encryptedString: "+encryptedString);


    }
    private static String getEncryptedString(String password){

        char[] chars=password.toCharArray();
        StringBuilder stringBuilder = new StringBuilder();
        for(char c: chars){
            c +=key;
            stringBuilder.append(c);
        }
        String finalEncryptedString= Base64.getEncoder().encodeToString(stringBuilder.toString().getBytes());
        return finalEncryptedString;
    }

    public static String getDecryptString(String encryptedPassword){
        String decryptedString= new String(Base64.getMimeDecoder().decode(encryptedPassword));
        char[] chars=decryptedString.toCharArray();
        StringBuilder stringBuilder = new StringBuilder();
        for(char c: chars){
            c -=key;
            stringBuilder.append(c);
        }

        return stringBuilder.toString();
    }


}
