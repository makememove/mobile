package makememove.ml.makememove.activities.fragments.eventfragments;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import makememove.ml.makememove.R;

public class EventDetailsTeamsFragment extends Fragment {
    private ImageButton add_team;
    private String m_Text = "";
    private View Layout;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.eventdetailsteams_fragment, container, false);





    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Layout=this.getView();

        //TODO teammembercsempe inflatelése csatlakozáskor, szerverről lekkérdezett tagokra,
        // TODO barát hozzáadása gomb bekötése, saját magamnál ne legyen barárnak jelölés gomb

        if(Layout != null) {
            add_team= Layout.findViewById(R.id.ib_addteam);
            add_team.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                     m_Text = "";

                    AlertDialog.Builder builder = new AlertDialog.Builder(Layout.getContext());
                    builder.setTitle("Teamname:");

                    final EditText input = new EditText(Layout.getContext());

                    input.setInputType(InputType.TYPE_CLASS_TEXT);
                    builder.setView(input);


                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if(input.getText().toString().length()>3) {
                                m_Text = input.getText().toString();
                                LayoutInflater inflater = getLayoutInflater();
                                LinearLayout mainLayout = (LinearLayout) Layout.findViewById(R.id.l_teams);
                                View myLayout = inflater.inflate(R.layout.teamcsempe, mainLayout, true);
                                TextView teamname = myLayout.findViewById(R.id.tv_teamnameset);
                                teamname.setText(m_Text);

                            }else{
                                Snackbar.make(getActivity().findViewById(R.id.content), "Error: Teamname must be at least 4 character!", Snackbar.LENGTH_LONG).show();
                            }
                        }
                    });
                    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });

                    builder.show();



                }
            });




        }






    }


}
