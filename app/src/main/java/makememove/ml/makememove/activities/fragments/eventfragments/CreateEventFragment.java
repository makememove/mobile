package makememove.ml.makememove.activities.fragments.eventfragments;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import makememove.ml.makememove.R;
import makememove.ml.makememove.activities.UserActivity;
import makememove.ml.makememove.activities.fragments.UserMainFragment;

public class CreateEventFragment extends Fragment {

    private EditText et_title;
    private Spinner c_category;
    private Spinner s_sports;
    private Button bt_datepicker;
    private Button bt_Timepicker;
    private Spinner s_visiility;
    private EditText et_location;
    private EditText et_length;
    private EditText et_minskillpoint;
    private EditText et_maxskillpoint;
    private EditText et_description;





    

    private Button bt_create;
    private View Layout;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.createevent_fragment, container, false);
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Layout=this.getView();
        if(Layout != null) {

            bt_datepicker= Layout.findViewById(R.id.bt_datepicker);
            bt_datepicker.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    LayoutInflater inflater = (LayoutInflater) getLayoutInflater();
                    View customView = inflater.inflate(R.layout.datapicker, null);
                    final DatePicker dpStartDate = (DatePicker) customView.findViewById(R.id.simpleDatePicker);
                    Date today= new Date();
                    dpStartDate.setMinDate(today.getTime());
                    dpStartDate.setSpinnersShown(false);

                    AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                    builder.setView(customView); // Set the view of the dialog to your custom layout
                    builder.setTitle("Starting Date:");
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener(){
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            int Year = dpStartDate.getYear();
                            int Month = dpStartDate.getMonth();
                            int Day = dpStartDate.getDayOfMonth();
                            bt_datepicker.setText(Integer.toString(Year)+"-"+Integer.toString(Month)+"-"+Integer.toString(Day));
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
                            bt_Timepicker.setText(Integer.toString(hour)+":"+Integer.toString(min)+":"+Integer.toString(sec));
                            dialog.dismiss();
                        }});

                    // Create and show the dialog
                    builder.create().show();
                }
            });

            et_title = Layout.findViewById(R.id.et_titleofevent);
            et_length = Layout.findViewById(R.id.et_length);
            et_location = Layout.findViewById(R.id.et_location);
            et_minskillpoint = Layout.findViewById(R.id.et_minskillvalue);
            et_maxskillpoint = Layout.findViewById(R.id.et_maxskillvalue);

            bt_create = Layout.findViewById(R.id.bt_createevent);
            bt_create.setOnClickListener(new View.OnClickListener() {

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
                                et_maxskillpoint.getText().toString())){
                            //TODO create event Retrofit Api bekötése
                            Snackbar.make(getActivity().findViewById(R.id.content), "You created an event!", Snackbar.LENGTH_LONG).show();
                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
            });

            s_sports=Layout.findViewById(R.id.s_Sport);
            List<String> list = UserMainFragment.getAllSports();
            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_dropdown_item , list);
            s_sports.setAdapter(dataAdapter);
        }
    }

    private  boolean validation(String title,String date, String time, String location,String Length,String minSkillPoint,String maxSkillPoint) throws ParseException {

        //ki van e töltve a title fül
        if(4>=title.length()){
            et_title.setError("Title must be fulfilled at least 4 character!");
            return false;
        }

        //ki van e töltve a date fül
        if(date.equals(getString(R.string.pick_date))){
            Snackbar.make(getActivity().findViewById(R.id.content), "Error: Pick a Date!", Snackbar.LENGTH_LONG).show();
            return false;
        }
        //ki van e töltve a time fül
        if(time.equals(getString(R.string.pick_time))){
            Snackbar.make(getActivity().findViewById(R.id.content), "Error: Pick a Time!", Snackbar.LENGTH_LONG).show();
            return false;
        }
        //ki van e töltve a location fül
        if(4>=location.length()){
            et_location.setError("Location must be fulfilled at least 4 character!");
            return false;
        }
        //ki van e töltve a length fül
        if(1>=Length.length()){
            et_length.setError("Length must be fulfilled at least 1 character!");
            return false;
        }
        //ki van e töltve a minskillpoint fül
        if(1>=minSkillPoint.length()){
            et_minskillpoint.setError("MinSkillPoint must be fulfilled at least 1 character!");
            return false;
        }
        //ki van e töltve a maxskillpoint fül
        if(1>=maxSkillPoint.length()){
            et_maxskillpoint.setError("MaxSkillPoint must be fulfilled at least 1 character!");
            return false;
        }
        //date és time nagyobb mint actual time
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat dateformat3 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        try {
            Date date1 = dateformat3.parse(date+" "+time);
            Date date2 = new Date();

            if (date1.after(date2)) {
            }else if (date1.before(date2)||date1.equals(date2)) {
                Snackbar.make(getActivity().findViewById(R.id.content), "Error: Date and Time must be later then actual", Snackbar.LENGTH_LONG).show();
                return false;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        //minskillpoint kisebb mint max skillpoint
        if(Integer.parseInt(minSkillPoint)>=Integer.parseInt(maxSkillPoint)){
            Snackbar.make(getActivity().findViewById(R.id.content), "Error: MaxSkillPoint must be higher then MinSkillPoint!", Snackbar.LENGTH_LONG).show();
            return false;
        }


        return true;
    }

}
