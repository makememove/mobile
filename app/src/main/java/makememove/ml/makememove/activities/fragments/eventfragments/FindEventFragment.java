package makememove.ml.makememove.activities.fragments.eventfragments;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Date;

import makememove.ml.makememove.R;
import makememove.ml.makememove.activities.fragments.NotificationFragment;
import makememove.ml.makememove.dpsystem.presenters.NotificationPresenter;
import makememove.ml.makememove.user.User;

public class FindEventFragment extends Fragment {

    private String filter_date = null;
    private Button bt_datepicker;
    private String filter_location=null;
    private String filter_title=null;
    private String filter_lowskillpoint=null;
    private String filter_highskillpoint=null;
    private Button bt_addfilter;

    private View Layout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.findevent_fragment, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Layout = this.getView();
        if (Layout != null ) {
            NotificationPresenter np = new NotificationPresenter(NotificationFragment.document);
            np.getNotifications(User.getInstance().getToken());


            bt_addfilter=Layout.findViewById(R.id.bt_addfilter);
            bt_addfilter.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final AlertDialog.Builder builder = new AlertDialog.Builder(Layout.getContext());
                    builder.setTitle("Filter:");

                    LayoutInflater inflater = getLayoutInflater();
                    View dialogView = inflater.inflate(R.layout.filterdialog, null);
                    builder.setView(dialogView);

                    final CheckBox c_title = dialogView.findViewById(R.id.check_title);
                    final CheckBox c_location = dialogView.findViewById(R.id.check_location);
                    final CheckBox c_minskill = dialogView.findViewById(R.id.check_min_skillpoint);
                    final CheckBox c_maxskill = dialogView.findViewById(R.id.check_max_skillpoint);

                    final EditText et_title = dialogView.findViewById(R.id.et_filtertitle);
                    final EditText et_location  = dialogView.findViewById(R.id.et_filterlocation);
                    final EditText et_minskill = dialogView.findViewById(R.id.et_filter_min_skillpoint);
                    final EditText et_maxskill = dialogView.findViewById(R.id.et_filter_max_skillpoint);

                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if(c_title.isChecked())filter_title=et_title.getText().toString();
                            else filter_title=null;
                            if(c_location.isChecked())filter_location=et_location .getText().toString();
                            else filter_location=null;
                            if(c_minskill.isChecked())filter_lowskillpoint=et_minskill.getText().toString();
                            else filter_lowskillpoint=null;
                            if(c_maxskill.isChecked())filter_highskillpoint=et_maxskill.getText().toString();
                            else filter_highskillpoint=null;

                            //TODO filter visibility bekötése
                            searchwithfilters();
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

            bt_datepicker= Layout.findViewById(R.id.bt_findeventpickdate);
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
                            int Month = dpStartDate.getMonth();
                            int Day = dpStartDate.getDayOfMonth();
                            filter_date=(Integer.toString(Year)+"-"+Integer.toString(Month)+"-"+Integer.toString(Day));
                            searchwithfilters();
                            dialog.dismiss();
                        }});

                    // Create and show the dialog
                    builder.create().show();
                }
            });


        }

    }

    private void searchwithfilters(){
        //TODO Lista frissítése, filteres keresés bekötése
    }
}
