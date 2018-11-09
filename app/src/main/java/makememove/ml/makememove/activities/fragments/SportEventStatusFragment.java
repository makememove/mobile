package makememove.ml.makememove.activities.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import makememove.ml.makememove.R;
import makememove.ml.makememove.activities.fragments.eventfragments.EventDetailsFragment;

import static makememove.ml.makememove.activities.UserActivity.fragmentManager;

public class SportEventStatusFragment extends Fragment {

    private View Layout;
    private Button testbt;
    private TextView sportName;
    private ImageButton previousButton;
    private ImageButton nextButton;
    private static String sportNameString;
    private static int sportID;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.sporteventstatus_fragment, container, false);

    }

    public static SportEventStatusFragment newInstance(int arg) {

        Bundle args = new Bundle();
        args.putInt("SportID",arg);

        SportEventStatusFragment fragment = new SportEventStatusFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Layout = this.getView();
        if (Layout != null) {
            previousButton = Layout.findViewById(R.id.ib_prev);
            nextButton = Layout.findViewById(R.id.ib_next);
            sportName = Layout.findViewById(R.id.tv_actualsport);
            sportID = (int) getArguments().get("SportID");
            sportNameString = UserMainFragment.getName(sportID);
            sportName.setText(sportNameString);
            testbt=Layout.findViewById(R.id.test);
            testbt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    EventDetailsFragment sportEventFragment = new EventDetailsFragment();
                    fragmentManager.beginTransaction()
                            .replace(R.id.content, sportEventFragment).addToBackStack(null)
                            .commit();
                }
            });

            

            previousButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(sportID == 0){
                        sportID = UserMainFragment.getListSize()-1;
                        sportNameString = UserMainFragment.getName(sportID);
                        sportName.setText(sportNameString);
                    }
                    else{
                        sportID--;
                        sportNameString = UserMainFragment.getName(sportID);
                        sportName.setText(sportNameString);
                    }
                }
            });
            nextButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(sportID+1 == UserMainFragment.getListSize()){
                        sportID =0;
                        sportNameString = UserMainFragment.getName(sportID);
                        sportName.setText(sportNameString);
                    }
                    else{
                        sportID++;
                        sportNameString = UserMainFragment.getName(sportID);
                        sportName.setText(sportNameString);
                    }
                }
            });
        }
    }

}
