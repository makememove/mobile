package makememove.ml.makememove.activities.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import makememove.ml.makememove.R;
import makememove.ml.makememove.user.User;

public class ProfileFragment extends Fragment {

    private TextView userName;
    private EditText firstName;
    private EditText lastName;
    private EditText birthday;
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
            View view = this.getView();
            userName = view.findViewById(R.id.tv_usernameset);
            firstName = view.findViewById(R.id.et_firstname);
            lastName = view.findViewById(R.id.et_lastname);
            birthday = view.findViewById(R.id.et_birthday);
            gender = view.findViewById(R.id.s_gender);
            email = view.findViewById(R.id.tv_emailset);
            permission = view.findViewById(R.id.tv_permissionset);
            popularity = view.findViewById(R.id.tv_popularityset);


            System.out.printf("Username: "+User.getInstance().getEmail()+"\n");
            userName.setText(user.getUserName());
            firstName.setHint(user.getFirstName());
            lastName.setHint(user.getLastName());
            if(user.getBirthday()!= null)
                 birthday.setHint(user.getBirthday().toString());
            email.setText(user.getEmail());
            popularity.setText(String.valueOf(user.getPopularity()));
        }
    }
}
