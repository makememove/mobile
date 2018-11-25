package makememove.ml.makememove.activities.fragments.eventfragments;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import makememove.ml.makememove.R;
import makememove.ml.makememove.activities.fragments.UserMainFragment;
import makememove.ml.makememove.adapters.ModifyResultAdapter;
import makememove.ml.makememove.adapters.ResultAdapter;
import makememove.ml.makememove.dpsystem.BaseView;
import makememove.ml.makememove.dpsystem.documents.AuthInputDocument;
import makememove.ml.makememove.dpsystem.documents.EventDocument;
import makememove.ml.makememove.dpsystem.documents.EventDocumentContainer;
import makememove.ml.makememove.dpsystem.documents.RankDocument;
import makememove.ml.makememove.dpsystem.documents.subdocuments.FinishedRank;
import makememove.ml.makememove.dpsystem.documents.subdocuments.ResultDocument;
import makememove.ml.makememove.dpsystem.documents.subdocuments.Team;
import makememove.ml.makememove.dpsystem.presenters.EventPresenter;
import makememove.ml.makememove.dpsystem.presenters.PostPresenter;
import makememove.ml.makememove.globals.GlobalClass;
import makememove.ml.makememove.user.User;

public class EventModifyFragment extends Fragment implements BaseView {


    private View Layout;
    private static EventDocument currentEvent;
    private EditText et_title;
    private Spinner s_category;
    private Spinner s_sports;
    private Button bt_datepicker;
    private Button bt_Timepicker;
    private Spinner s_visibility;
    private EditText et_location;
    private EditText et_length;
    private EditText et_teamcapacity;
    private EditText et_membercapacity;
    private EditText et_minskillpoint;
    private EditText et_maxskillpoint;
    private EditText et_description;
    private EditText et_attandances;
    private Button bt_modify;
    private Button bt_closeevent;
    private static EventDocumentContainer teamDocument;
    private ModifyResultAdapter adapter;
    private RecyclerView recyclerView;
    private static AuthInputDocument closedEvent;

    public static EventDocument getCurrentEvent() {
        return currentEvent;
    }

    public static void setCurrentEvent(EventDocument currentEvent) {
        EventModifyFragment.currentEvent = currentEvent;
    }

    private void initRecylerView(){
        recyclerView = this.getView().findViewById(R.id.rv_result);
        adapter = new ModifyResultAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(GlobalClass.context));
        recyclerView.setAdapter(adapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.eventmodify_fragment, container, false);

    }

    public void getEvent(){
        teamDocument = new EventDocumentContainer();
        teamDocument.attach(this);
        EventPresenter ep = new EventPresenter(teamDocument);
        ep.getEvent(User.getInstance().getToken(),currentEvent.getId());
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Layout = this.getView();
        if (Layout != null) {
            initRecylerView();
            getEvent();

            bt_datepicker= Layout.findViewById(R.id.bt_datepicker);
            bt_datepicker.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    LayoutInflater inflater = (LayoutInflater) getLayoutInflater();
                    View customView = inflater.inflate(R.layout.datapicker, null);
                    final DatePicker dpStartDate = (DatePicker) customView.findViewById(R.id.simpleDatePicker);
                    Date today= new Date();
                    dpStartDate.setMinDate(today.getTime());
                    dpStartDate.setCalendarViewShown(false);
                    dpStartDate.setSpinnersShown(true);

                    AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                    builder.setView(customView); // Set the view of the dialog to your custom layout
                    builder.setTitle("Starting Date:");
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener(){
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            int Year = dpStartDate.getYear();
                            int Month = dpStartDate.getMonth()+1;
                            int Day = dpStartDate.getDayOfMonth();
                            bt_datepicker.setText(String.format("%04d",Year)+"-"+ String.format("%02d",Month) + "-"+String.format("%02d",Day));
                            dialog.dismiss();
                        }});

                    // Create and show the dialog
                    builder.create().show();
                }
            });

            bt_Timepicker= Layout.findViewById(R.id.bt_timepicker);
            bt_Timepicker.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    LayoutInflater inflater = (LayoutInflater) getLayoutInflater();
                    View customView = inflater.inflate(R.layout.timepicker, null);
                    final TimePicker timePicker = (TimePicker) customView.findViewById(R.id.simpletimepicker);
                    timePicker.setIs24HourView(true);


                    AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                    builder.setView(customView); // Set the view of the dialog to your custom layout
                    builder.setTitle("Starting time:");
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener(){
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            int hour = timePicker.getHour();
                            int min = timePicker.getMinute();
                            int sec = 0;
                            bt_Timepicker.setText(String.format("%02d",hour)+":"+ String.format("%02d",min) + ":"+String.format("%02d",sec));
                            dialog.dismiss();
                        }});

                    // Create and show the dialog
                    builder.create().show();
                }
            });

            et_title = Layout.findViewById(R.id.et_titleofevent);
            et_length = Layout.findViewById(R.id.et_length);
            et_teamcapacity = Layout.findViewById(R.id.et_modifyteamcapacity);
            et_membercapacity = Layout.findViewById(R.id.et_modifymembercapacity);
            et_location = Layout.findViewById(R.id.et_location);
            et_minskillpoint = Layout.findViewById(R.id.et_minskillvalue);
            et_maxskillpoint = Layout.findViewById(R.id.et_maxskillvalue);
            s_visibility = Layout.findViewById(R.id.s_visibility);
            s_sports = Layout.findViewById(R.id.s_Sport);
            s_category = Layout.findViewById(R.id.s_Category);
            et_description = Layout.findViewById(R.id.et_description);
            bt_modify = Layout.findViewById(R.id.bt_modifyyevent);
            bt_closeevent = Layout.findViewById(R.id.bt_closeevent);

            et_title.setText(currentEvent.getTitle());

            s_category.setSelection(currentEvent.getCategoryId()-1);
            s_visibility.setSelection(currentEvent.getPublished());

            List<String> list = UserMainFragment.getAllSports();
            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_dropdown_item , list);
            s_sports.setAdapter(dataAdapter);
            s_sports.setSelection(currentEvent.getSportId()-1);

            et_length.setText(Integer.toString(currentEvent.getLength()));
                 et_teamcapacity.setText(Integer.toString(currentEvent.getMaxAttending()));
            if(currentEvent.getMemberLimit()!=null)
                et_membercapacity.setText(Integer.toString(currentEvent.getMemberLimit()));
            else
                et_membercapacity.setText("Not defined!");
            et_location.setText(currentEvent.getLocation());
            et_minskillpoint.setText(Integer.toString(currentEvent.getLowestSkillPoint()));
            et_maxskillpoint.setText(Integer.toString(currentEvent.getHighestSkillPoint()));
            et_description.setText(currentEvent.getDescription());

            Date date = currentEvent.getDate();
            SimpleDateFormat dateString = new SimpleDateFormat("yyyy-MM-dd");
            bt_datepicker.setText( dateString.format(currentEvent.getDate()));

            SimpleDateFormat time = new SimpleDateFormat("hh:mm:ss");
            bt_Timepicker.setText(time.format(currentEvent.getDate()));


            bt_modify.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {

                        if(validation(
                                et_title.getText().toString(),
                                bt_datepicker.getText().toString(),
                                bt_Timepicker.getText().toString(),
                                et_location.getText().toString(),
                                et_length.getText().toString(),
                                et_minskillpoint.getText().toString(),
                                et_maxskillpoint.getText().toString(),
                                et_teamcapacity.getText().toString(),
                                et_membercapacity.getText().toString())
                                ){
                            EventDocument event = new EventDocument();
                            event.setTitle(et_title.getText().toString());

                            SimpleDateFormat dateformat3 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                            String date =  bt_datepicker.getText().toString();
                            String time =  bt_Timepicker.getText().toString();
                            Date date1 = dateformat3.parse(date+" "+time);
                            event.setDate(date1);
                            event.setId(currentEvent.getId());

                            event.setDescription(et_description.getText().toString());
                            event.setCategoryId(s_category.getSelectedItemPosition()+1);
                            event.setSportId(s_sports.getSelectedItemPosition()+1);
                            event.setPublished(s_visibility.getSelectedItemPosition()+1);
                            event.setLocation( et_location.getText().toString());
                            event.setLength( Integer.parseInt(et_length.getText().toString()));
                            event.setMaxAttending(Integer.parseInt(et_teamcapacity.getText().toString()));
                            event.setMemberLimit(Integer.parseInt(et_membercapacity.getText().toString()));
                            event.setClosed(0);

                            Log.d("Attending","Attending: "+event.getMaxAttending());

                            event.setLowestSkillPoint(Integer.parseInt( et_minskillpoint.getText().toString()));
                            event.setHighestSkillPoint(Integer.parseInt(  et_maxskillpoint.getText().toString()));

                            modifyEvent(event);
                            Snackbar.make(getActivity().findViewById(R.id.content), "You modified the event!", Snackbar.LENGTH_LONG).show();
                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
            });

            bt_closeevent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    FinishedRank fr = new FinishedRank();
                    fr.setRankings(adapter.getItems());
                    for(int i = 0;i< fr.getRankings().size();i++){
                        fr.getRankings().get(i).setPlace(i+1);
                    }
                    closeEvent(fr);
                    Snackbar.make(getActivity().findViewById(R.id.content), "The event has been successfully closed!", Snackbar.LENGTH_LONG).show();
                }
            });

        }
    }

    public void modifyEvent(EventDocument doc){
        PostPresenter pp = new PostPresenter(doc);
        pp.modifyEvent(User.getInstance().getToken(),doc.getId(),doc);
    }

    public void closeEvent(FinishedRank rankings){
        closedEvent = new AuthInputDocument();
        closedEvent.attach(this);

        Log.d("Csapat Eventid","Csapat Eventid "+teamDocument.getEvent().getId());
        for(ResultDocument current : rankings.getRankings()){
            Log.d("Csapat","Csapatid: "+current.getId()+" csapathelyezés: "+current.getPlace());
        }

        PostPresenter pp = new PostPresenter(closedEvent);
        pp.closeEvent(User.getInstance().getToken(),teamDocument.getEvent().getId(),rankings);
    }


    private  boolean validation(String title,String date, String time, String location,String Length,String minSkillPoint,String maxSkillPoint,String teamcap,String membercap) throws ParseException {


        if(title.length()<4){
            et_title.setError("Title must be  at least 4 character!");
            et_title.requestFocus();
            return false;
        }

        if(title.length()>20){
            et_title.setError("Title must be  at most 20 character!");
            et_title.requestFocus();
            return false;
        }


        if(date.equals(getString(R.string.pick_date))){
            Snackbar.make(getActivity().findViewById(R.id.content), "Error: Pick a Date!", Snackbar.LENGTH_LONG).show();
            return false;
        }


        if(time.equals(getString(R.string.pick_time))){
            Snackbar.make(getActivity().findViewById(R.id.content), "Error: Pick a Time!", Snackbar.LENGTH_LONG).show();
            return false;
        }

        if(location.length()<1){
            et_location.setError("Location must be filled!");
            et_location.requestFocus();
            return false;
        }

        if(location.length()>40){
            et_location.setError("Location must be at most 40 character!");
            et_location.requestFocus();
            return false;
        }

        if(Length.length()<1){
            et_length.setError("Length must be filled!");
            et_length.requestFocus();
            return false;
        }

        if(Length.length()>2){
            et_length.setError("Length must be at most 2 character!");
            et_length.requestFocus();
            return false;
        }

        if(teamcap.length()<1){
            et_teamcapacity.setError("Team Capacity must be filled!");
            et_teamcapacity.requestFocus();
            return false;
        }

        if(teamcap.length()>2){
            et_teamcapacity.setError("Team Capacity must be at most 2 character!");
            et_teamcapacity.requestFocus();
            return false;
        }


        if(membercap.length()<1){
            et_membercapacity.setError("Member Capacity must be filled!");
            et_membercapacity.requestFocus();
            return false;
        }

        if(membercap.length()>2){
            et_membercapacity.setError("Member Capacity must be at most 2 character!");
            et_membercapacity.requestFocus();
            return false;
        }

        if(minSkillPoint.length()<1){
            et_minskillpoint.setError("MinSkillPoint must be filled!");
            et_minskillpoint.requestFocus();
            return false;
        }

        if(minSkillPoint.length()>4){
            et_minskillpoint.setError("MinSkillPoint must be at most 4 character!");
            et_minskillpoint.requestFocus();
            return false;
        }

        if(maxSkillPoint.length()<1){
            et_maxskillpoint.setError("MaxSkillPoint must be filled!");
            et_maxskillpoint.requestFocus();
            return false;
        }

        if(maxSkillPoint.length()>4){
            et_maxskillpoint.setError("MaxSkillPoint must be at most 4 character!");
            et_maxskillpoint.requestFocus();
            return false;
        }

        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat dateformat3 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        try {
            Date date1 = dateformat3.parse(date+" "+time);
            Date date2 = new Date();
            date2.setHours(date2.getHours()+1);



            if (date1.after(date2)) {
            }else if (date1.before(date2)||date1.equals(date2)) {
                Snackbar.make(getActivity().findViewById(R.id.content), "Error: Date and Time must be later then actual", Snackbar.LENGTH_LONG).show();
                return false;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }


        if(Integer.parseInt(minSkillPoint)>=Integer.parseInt(maxSkillPoint)){
            Snackbar.make(getActivity().findViewById(R.id.content), "Error: MaxSkillPoint must be higher then MinSkillPoint!", Snackbar.LENGTH_LONG).show();
            et_maxskillpoint.requestFocus();
            return false;
        }


        return true;
    }

    @Override
    public void update() {
        initRecylerView();
        List<Team> teams = teamDocument.getEvent().getTeams();
        Log.d("TeamId","TeamId "+teamDocument.getEvent().getId());
        if (teams.size() == 0) {

        } else {
            for (Team team : teams) {
                ResultDocument currentTeam = new ResultDocument(team.getId(), team.getName());
                adapter.addItem(currentTeam);
            }
        }
    }

}
