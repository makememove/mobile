package makememove.ml.makememove.datahandler;

import android.content.Context;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import makememove.ml.makememove.globals.GlobalClass;
import makememove.ml.makememove.user.User;

public class TokenHandler {
    private String token;
    private String fileName;
    public TokenHandler() {
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
    public void setToken(String token){
        User.getInstance().setToken(token);
        this.token = token;
        saveToken();
    }

    public void loadToken() {
        try {
            FileInputStream fis = GlobalClass.context.openFileInput(fileName);
            byte[] tomb = new byte[200];
            fis.read(tomb);
            String line = new String(tomb).trim();
            User.getInstance().setToken(line);
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

    public void clear (){
        File file = GlobalClass.context.getFileStreamPath(fileName);
        if(file.exists())file.delete();
    }
}
