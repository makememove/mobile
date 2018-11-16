package makememove.ml.makememove.activities.fragments;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Spinner;
import android.widget.TextView;

import makememove.ml.makememove.R;
import makememove.ml.makememove.user.User;

public class ProfileFragment extends Fragment {

    private TextView userName;
    private EditText firstName;
    private EditText lastName;
    private Button birthday;
    private Spinner gender;
    private TextView email;
    private TextView permission;
    private TextView popularity;
  User user = User.getInstance();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.profile_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if(this.getView()!=null) {
            final View view = this.getView();
            userName = (TextView) view.findViewById(R.id.tv_usernameset);
            firstName = (EditText) view.findViewById(R.id.et_firstname);
            lastName = (EditText) view.findViewById(R.id.et_lastname);
            birthday = (Button) view.findViewById(R.id.bt_birthdaypicker);
            gender = view.findViewById(R.id.s_gender);
            email = view.findViewById(R.id.tv_emailset);
            permission = view.findViewById(R.id.tv_permissionset);
            popularity = view.findViewById(R.id.tv_popularityset);


            userName.setText(user.getUserName());

            firstName.setText(user.getFirstName());

            lastName.setText(user.getLastName());

            if(user.getBirthday()!= null)
                 birthday.setText(user.getBirthday().toString());

            //TODO Gender bekötése

            email.setText(user.getEmail());

            popularity.setText(String.valueOf(user.getPopularity()));

            permission.setText(user.getUserString());

            birthday.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    LayoutInflater inflater = (LayoutInflater) getLayoutInflater();
                    View customView = inflater.inflate(R.layout.datapicker, null);
                    final DatePicker dpStartDate = (DatePicker) customView.findViewById(R.id.simpleDatePicker);
                    dpStartDate.setSpinnersShown(false);

                    AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                    builder.setView(customView); // Set the view of the dialog to your custom layout
                    builder.setTitle("Birthday");
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener(){
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            int Year = dpStartDate.getYear();
                            int Month = dpStartDate.getMonth();
                            int Day = dpStartDate.getDayOfMonth();
                            birthday.setText(Integer.toString(Year)+"-"+Integer.toString(Month)+"-"+Integer.toString(Day));
                            dialog.dismiss();
                        }});

                    // Create and show the dialog
                    builder.create().show();
                }
            });
        }
    }
}
