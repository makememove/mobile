package makememove.ml.makememove;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RegistrationActivity extends AppCompatActivity {

    private static TextView tv1;
    private static EditText et1;
    private static Button button;
    private static int ID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

       // tv1 = (TextView) findViewById(R.id.textView2);
        et1 = (EditText) findViewById(R.id.editText);
       // button = (Button) findViewById(R.id.button2);
                                          /*

        button.setOnClickListener(new View.OnClickListener() {
                                      @Override
                                      public void onClick(View view) {
                                        ID = Integer.parseInt(et1.getText().toString());
                                        finish();
                                        startActivity(getIntent());
                                      }
                                  });
                                        */

        /*
                Retrofit retrofit = new Retrofit.Builder().baseUrl(API.BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();

        API api = retrofit.create(API.class);

        Call<Description> call = api.getDescription(ID);

        call.enqueue(new Callback<Description>() {
            @Override
            public void onResponse(Call<Description> call, Response<Description> response) {
                Description description = response.body();

                Log.d("id",description.getId());
                Log.d("title",description.getTitle());
                Log.d("description",description.getDescription());
                Log.d("time",description.getTime());

                String selected =  description.getId() +" - " +
                        description.getTitle() + " - "+description.getDescription()+" - "+description.getTime()+"\n";

                tv1.setText(selected);
            }

            @Override
            public void onFailure(Call<Description> call, Throwable t) {
                Toast.makeText(getApplicationContext(),t.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
        */
    }
}
