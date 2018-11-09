package makememove.ml.makememove.activities.fragments.eventfragments;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TimePicker;

import java.sql.Time;

import makememove.ml.makememove.R;

import static makememove.ml.makememove.activities.UserActivity.fragmentManager;

public class EventDetailsFragment extends Fragment {
    private Button bt_changedetail;
    private Button bt_changeteams;
    private Button bt_changeresult;



    private View Layout;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.eventdetails_fragment, container, false);





    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Layout=this.getView();
        if(Layout != null) {






            bt_changedetail=Layout.findViewById(R.id.bt_detailchange);
            bt_changedetail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    EventDetailsDetailFragment sportEventFragment = new EventDetailsDetailFragment();
                    fragmentManager.beginTransaction()
                            .replace(R.id.eventdetailscontent, sportEventFragment).addToBackStack(null)
                            .commit();

                    bt_changedetail.setBackgroundColor(getResources().getColor(R.color.Headerbg));
                    bt_changeresult.setBackgroundColor(getResources().getColor(R.color.background));
                    bt_changeteams.setBackgroundColor(getResources().getColor(R.color.background));
                }
            });

            bt_changeteams=Layout.findViewById(R.id.bt_teamchange);
            bt_changeteams.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    EventDetailsTeamsFragment sportEventFragment = new EventDetailsTeamsFragment();
                    fragmentManager.beginTransaction()
                            .replace(R.id.eventdetailscontent, sportEventFragment).addToBackStack(null)
                            .commit();

                    bt_changedetail.setBackgroundColor(getResources().getColor(R.color.background));
                    bt_changeresult.setBackgroundColor(getResources().getColor(R.color.background));
                    bt_changeteams.setBackgroundColor(getResources().getColor(R.color.Headerbg));
                }
            });

            bt_changeresult=Layout.findViewById(R.id.bt_resultchange);
            bt_changeresult.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    EventDetailsResultFragment sportEventFragment = new EventDetailsResultFragment();
                    fragmentManager.beginTransaction()
                            .replace(R.id.eventdetailscontent, sportEventFragment).addToBackStack(null)
                            .commit();

                    bt_changedetail.setBackgroundColor(getResources().getColor(R.color.background));
                    bt_changeresult.setBackgroundColor(getResources().getColor(R.color.Headerbg));
                    bt_changeteams.setBackgroundColor(getResources().getColor(R.color.background));
                }
            });

        }







    }
    @Override
    public void onStart() {
        super.onStart();
        EventDetailsDetailFragment sportEventFragment = new EventDetailsDetailFragment();
        fragmentManager.beginTransaction()
                .replace(R.id.eventdetailscontent, sportEventFragment).addToBackStack(null)
                .commit();

        bt_changedetail.setBackgroundColor(getResources().getColor(R.color.Headerbg));
        bt_changeresult.setBackgroundColor(getResources().getColor(R.color.background));
        bt_changeteams.setBackgroundColor(getResources().getColor(R.color.background));
    }



}
