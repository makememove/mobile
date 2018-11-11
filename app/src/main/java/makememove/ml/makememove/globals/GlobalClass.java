package makememove.ml.makememove.globals;

import android.app.Application;
import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import makememove.ml.makememove.R;

public class GlobalClass extends Application {

  public static Context context;

   @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
    }

    public static void setSportPicture(String name, ImageView iv){
        if(name.equals("Basketball")) iv.setImageResource(R.drawable.basketball);
        else if(name.equals("Football")) iv.setImageResource(R.drawable.football_1);
        //TODO több sport esetén kiegészítendő
    }
  }