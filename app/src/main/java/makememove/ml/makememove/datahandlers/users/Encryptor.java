package makememove.ml.makememove.datahandlers.users;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

public class Encryptor {
    public String code(String source){

        return null;
    }

    public String decode(String source){
        byte[] bytes = source.getBytes();
        byte[] encodedHash = null;
        try
        {
            System.out.println(new String(bytes));
            try{
                MessageDigest digestor = MessageDigest.getInstance("SHA-256");
                 encodedHash = digestor.digest(source.getBytes(StandardCharsets.UTF_8));
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return bytesToHex(encodedHash);
    }

    private static String bytesToHex(byte[] hash) {
        StringBuffer hexString = new StringBuffer();
        for (int i = 0; i < hash.length; i++) {
            String hex = Integer.toHexString(0xff & hash[i]);
            if(hex.length() == 1) hexString.append('0');
            hexString.append(hex);
        }
        return hexString.toString();
    }
}
