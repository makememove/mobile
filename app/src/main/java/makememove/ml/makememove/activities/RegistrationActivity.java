package makememove.ml.makememove.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import makememove.ml.makememove.R;
import makememove.ml.makememove.autentication.NormalRegister;

public class RegistrationActivity extends AppCompatActivity {

    private EditText et_username;
    private EditText et_email;
    private EditText et_password;
    private EditText et_confirmpassword;
    private Button bt_registrate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        et_username= findViewById(R.id.et_username);
        et_email= findViewById(R.id.et_email);
        et_password= findViewById(R.id.et_password);
        et_confirmpassword= findViewById(R.id.et_confirmpassword);
        bt_registrate = findViewById(R.id.bt_registrate);


        bt_registrate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    if(et_password.getText().toString().equals(et_confirmpassword.getText().toString())){
                        NormalRegister nregister = new NormalRegister();
                        nregister.registrate(et_email.getText().toString(),et_username.getText().toString(),et_password.getText().toString());
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
    }
}

























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

