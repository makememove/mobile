package makememove.ml.makememove.datahandlers.users;

import android.content.Context;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import makememove.ml.makememove.GlobalClass;
import makememove.ml.makememove.user.User;

public class TokenSaver {
    String token;
    String fileName;
    public TokenSaver() {
       token = User.getInstance().getToken();
       fileName = "TokenFile";
    }
    public void saveToken() {
        FileOutputStream fos = null;
        try {
            fos = GlobalClass.context.openFileOutput(fileName, Context.MODE_PRIVATE);
            fos.write(token.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            try {
                if(fos!=null)
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

        }
    }
    public void loadToken() {
        try {
            FileInputStream fis = GlobalClass.context.openFileInput(fileName);
            byte[] byteToken = new byte[1024];
            fis.read(byteToken);
            User.getInstance().setToken(new String(byteToken));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public boolean availableToken() {
        File file = GlobalClass.context.getFileStreamPath(fileName);
        if(file == null || !file.exists()) {
            return false;
        }
        return true;
    }
}
