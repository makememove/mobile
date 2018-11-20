package makememove.ml.makememove.activities.fragments.eventfragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

import makememove.ml.makememove.R;
import makememove.ml.makememove.activities.fragments.NotificationFragment;
import makememove.ml.makememove.dpsystem.documents.EventDocument;
import makememove.ml.makememove.dpsystem.presenters.NotificationPresenter;
import makememove.ml.makememove.user.User;

public class EventDetailsDetailFragment extends Fragment {
    private View Layout;
    private TextView titleset;
    private TextView sporttypeset;
    private TextView categoryset;
    private TextView dateset;
    private TextView timeset;
    private TextView locationset;
    private TextView creatorset;
    private TextView visibilityset;
    private TextView popularityset;
    private TextView lengthset;
    private TextView minskillset;
    private TextView maxskillset;
    private TextView detailset;
    private TextView teamset;
    private TextView memberset;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.eventdetailsdetail_fragment, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Layout=this.getView();
        if(Layout != null) {



            EventDocument currentEvent = EventDetailsFragment.getCurrentEvent();
            titleset = Layout.findViewById(R.id.tv_titleset);
            sporttypeset = Layout.findViewById(R.id.tv_sporttypeset);
            categoryset = Layout.findViewById(R.id.tv_Categoryset);
            dateset = Layout.findViewById(R.id.tv_dateset);
            timeset = Layout. findViewById(R.id.tv_timeset);
            locationset = Layout.findViewById(R.id.tv_locationset);
            creatorset = Layout.findViewById(R.id.tv_creatorset);
            visibilityset = Layout.findViewById(R.id.tv_visibilityset);
            popularityset = Layout.findViewById(R.id.tv_popularityset);
            lengthset = Layout.findViewById(R.id.tv_Lengthset);
            minskillset = Layout.findViewById(R.id.tv_minskillset);
            maxskillset = Layout.findViewById(R.id.tv_maxskillset);
            detailset = Layout.findViewById(R.id.tv_descriptionset);
            teamset = Layout.findViewById(R.id.tv_teamcapacity);
            memberset = Layout.findViewById(R.id.tv_membercapacity);

            titleset.setText(currentEvent.getTitle());
            sporttypeset.setText(currentEvent.getSport().getName());

            categoryset.setText(currentEvent.getCategory().getName());

            Date date = currentEvent.getDate();
            SimpleDateFormat dateString = new SimpleDateFormat("yyyy-MM-dd");
            dateset.setText( dateString.format(currentEvent.getDate()));

            SimpleDateFormat time = new SimpleDateFormat("hh:mm:ss");
            timeset.setText(time.format(currentEvent.getDate()));

            locationset.setText(currentEvent.getLocation());
            creatorset.setText(currentEvent.getCreatorMockup().getUserName());

            String[] visibilityArray = getResources().getStringArray(R.array.visibility_array);
            visibilityset.setText(visibilityArray[currentEvent.getPublished()]);
            popularityset.setText("Not Defined");
            lengthset.setText(Integer.toString(currentEvent.getLength()));
            minskillset.setText(Integer.toString(currentEvent.getLowestSkillPoint()));
            maxskillset.setText(Integer.toString(currentEvent.getHighestSkillPoint()));
            detailset.setText(currentEvent.getDescription());
            teamset.setText(Integer.toString(currentEvent.getMaxAttending()));
            if(currentEvent.getMemberLimit()!=null)
              memberset.setText(Integer.toString(currentEvent.getMemberLimit()));
            else
                memberset.setText("Not Defined");
        }






    }


}