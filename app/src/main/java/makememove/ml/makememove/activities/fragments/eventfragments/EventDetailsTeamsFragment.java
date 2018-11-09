package makememove.ml.makememove.activities.fragments.eventfragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import makememove.ml.makememove.R;

public class EventDetailsTeamsFragment extends Fragment {
    private ImageButton add_team;
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
        if(Layout != null) {
            add_team= Layout.findViewById(R.id.ib_addteam);
            add_team.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    LayoutInflater inflater = getLayoutInflater();
                    LinearLayout mainLayout = (LinearLayout) Layout.findViewById(R.id.l_teams);
                    View myLayout = inflater.inflate(R.layout.teamcsempe, mainLayout, true);
                }
            });



        }






    }


}
