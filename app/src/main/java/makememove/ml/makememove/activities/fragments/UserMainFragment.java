package makememove.ml.makememove.activities.fragments;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import makememove.ml.makememove.R;

public class UserMainFragment extends Fragment {
    private ImageButton bt_addsport;
    private String[] Sports;
    private View Layout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Sports =new String[]{"Tennis","Golf","Football"};

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

        Layout=this.getView();
        if(Layout != null) {
            bt_addsport = this.getView().findViewById(R.id.bt_addsport);
            bt_addsport.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
                    dialog.setTitle("Pick Colour");
                    dialog.setItems(Sports,new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int position) {
                            LayoutInflater inflater = getLayoutInflater();
                            LinearLayout mainLayout = Layout.findViewById(R.id.sportslayout);
                            View mylayout = inflater.inflate(R.layout.sportcsempe, mainLayout, true);
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

