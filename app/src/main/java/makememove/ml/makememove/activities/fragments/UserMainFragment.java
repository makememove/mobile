package makememove.ml.makememove.activities.fragments;

import android.content.DialogInterface;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import makememove.ml.makememove.R;
import makememove.ml.makememove.activities.UserActivity;
import makememove.ml.makememove.globals.GlobalClass;

public class UserMainFragment extends Fragment {
    private ImageButton bt_addsport;
    private View Layout;
    private int sportdb=0;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.usermain_fragment, container, false);
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        final String[] sports = getResources().getStringArray(R.array.sports_array);

        Layout=this.getView();
        if(Layout != null) {
            bt_addsport = this.getView().findViewById(R.id.bt_addsport);
            bt_addsport.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog.Builder dialog = new AlertDialog.Builder(new ContextThemeWrapper(view.getContext(), R.style.AlertDialogCustom));
                    dialog.setTitle("Choose a Sport");
                    dialog.setItems(R.array.sports_array, new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int position) {

                            LayoutInflater inflater = getLayoutInflater();
                            LinearLayout mainLayout = Layout.findViewById(R.id.sportslayout);
                            View myLayout = inflater.inflate(R.layout.sportcsempe, mainLayout, true);
                            TextView t =mainLayout.getChildAt(sportdb++).findViewWithTag("sporttitle");
                            t.setText(sports[position]);

                        }


                    });
                    dialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    AlertDialog alert = dialog.create();
                    alert.show();
                }
            });
        }


    }
}

