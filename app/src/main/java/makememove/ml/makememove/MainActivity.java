package makememove.ml.makememove;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private static TextView tv1;
    private static Button button;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        button = (Button) findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
             //   startActivity(new Intent(MainActivity.this,Main2Activity.class));
            }
        });

        /*
        Retrofit retrofit = new Retrofit.Builder().baseUrl(API.BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        API api = retrofit.create(API.class);

        Call<List<Event>> call = api.getEvent();

        call.enqueue(new Callback<List<Event>>() {

            @Override
            public void onResponse(Call<List<Event>> call, Response<List<Event>> response) {
                List<Event> events = response.body();
                String list="";
                for(Event e: events){
                    Log.d("id",e.getId());
                    Log.d("title",e.getTitle());
                    list = list + e.getId() +" - " + e.getTitle() + "\n";
                }
                tv1.setText(list);
            }

            @Override
            public void onFailure(Call<List<Event>> call, Throwable t) {
                Toast.makeText(getApplicationContext(),t.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });*/

    }
}
