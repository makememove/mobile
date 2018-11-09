package makememove.ml.makememove.activities.fragments.eventfragments;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.Toast;

import makememove.ml.makememove.R;
import makememove.ml.makememove.activities.UserActivity;

public class CreateEventFragment extends Fragment {

    private Button bt_datepicker;
    private Button bt_Timepicker;
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
                    dpStartDate.setSpinnersShown(false);

                    AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                    builder.setView(customView); // Set the view of the dialog to your custom layout
                    builder.setTitle("Birthday");
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

        }
    }
}
