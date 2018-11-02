package makememove.ml.makememove.datahandler;

import android.content.Context;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

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
           // BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(fos));
            fos.write(token.getBytes());
            System.out.printf("Az elmentett token: "+token+"\n");
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
            InputStreamReader inputStreamReader = new InputStreamReader(fis);
            byte[] tomb = new byte[200];
            fis.read(tomb);
            String line = new String(tomb);
            String line2 = line.trim();
            System.out.printf("A byte token: "+line2+"\n");

          //  BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
          //  String line = bufferedReader.readLine();
            User.getInstance().setToken(line2);
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
