package makememove.ml.makememove.activities.fragments.eventfragments;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import makememove.ml.makememove.R;
import makememove.ml.makememove.activities.fragments.NotificationFragment;
import makememove.ml.makememove.adapters.EventAdapter;
import makememove.ml.makememove.dpsystem.BaseView;
import makememove.ml.makememove.dpsystem.documents.EventDocument;
import makememove.ml.makememove.dpsystem.documents.EventListDocument;
import makememove.ml.makememove.dpsystem.presenters.EventListPresenter;
import makememove.ml.makememove.dpsystem.presenters.NotificationPresenter;
import makememove.ml.makememove.globals.GlobalClass;
import makememove.ml.makememove.user.User;

public class FindEventFragment extends Fragment implements EventAdapter.EventItemClickListener, BaseView {

    private Date filter_date = null;
    private Button bt_datepicker;
    private String filter_location=null;
    private String filter_title=null;
    private Integer filter_lowskillpoint=null;
    private Integer filter_highskillpoint=null;
    private Integer filter_visibility=null;
    private String date = null;
    private Button bt_addfilter;

    private RecyclerView recyclerView;
    private EventAdapter adapter;
    private static EventListDocument documents;

    private View Layout;

    private void initRecylerView(){
        recyclerView = Layout.findViewById(R.id.rv_findeventlist);
        adapter = new EventAdapter(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(GlobalClass.context));
        recyclerView.setAdapter(adapter);
    }

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
        documents = new EventListDocument();
        documents.attach(this);

        if (Layout != null ) {
            initRecylerView();

            EventListPresenter ep = new EventListPresenter(documents);
            ep.geteventswithfilter(User.getInstance().getToken(),null,null,null,null,null,null);


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
                    final CheckBox c_visibility = dialogView.findViewById(R.id.check_visibility);
                    final CheckBox c_minskill = dialogView.findViewById(R.id.check_min_skillpoint);
                    final CheckBox c_maxskill = dialogView.findViewById(R.id.check_max_skillpoint);

                    final EditText et_title = dialogView.findViewById(R.id.et_filtertitle);
                    final EditText et_location  = dialogView.findViewById(R.id.et_filterlocation);
                    final Spinner s_visibility  = dialogView.findViewById(R.id.spinner_visibility);
                    final EditText et_minskill = dialogView.findViewById(R.id.et_filter_min_skillpoint);
                    final EditText et_maxskill = dialogView.findViewById(R.id.et_filter_max_skillpoint);

                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if(c_title.isChecked())filter_title=et_title.getText().toString();
                            else filter_title=null;
                            if(c_location.isChecked())filter_location=et_location .getText().toString();
                            else filter_location=null;
                            if(c_minskill.isChecked()){
                                if(!et_minskill.getText().toString().equals(""))filter_lowskillpoint=Integer.valueOf(et_minskill.getText().toString());
                                else filter_lowskillpoint=null;
                            }
                            else filter_lowskillpoint=null;
                            if(c_maxskill.isChecked()){
                                if(!et_maxskill.getText().toString().equals(""))filter_highskillpoint=Integer.valueOf(et_maxskill.getText().toString());
                                else filter_highskillpoint=null;
                            }
                            else filter_highskillpoint=null;
                            if(c_visibility.isChecked())filter_visibility=Integer.valueOf(s_visibility.getSelectedItemPosition());
                            else filter_visibility=null;

                            if(!bt_datepicker.getText().toString().equals("Pick Date"))
                            {
                            }

                            bt_datepicker.setText("Pick Date");
                            filter_date=null;
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
                            int Month = dpStartDate.getMonth()+1;
                            int Day = dpStartDate.getDayOfMonth();
                            date = Year+"-"+Month+"-"+Day;
                            SimpleDateFormat SDF= new SimpleDateFormat("yyyy-MM-dd");
                            try {
                                filter_date=SDF.parse(Year+"-"+Month+"-"+Day);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }

                            bt_datepicker.setText(SDF.format(filter_date));
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
        EventListPresenter ep = new EventListPresenter(documents);
        ep.geteventswithfilter(User.getInstance().getToken(),filter_location,filter_title,filter_lowskillpoint,filter_highskillpoint,filter_visibility,date);
    }

    @Override
    public void onItemChanged(EventDocument item) {

    }

    @Override
    public void update() {
        Log.d("Az update","Itt van");
        initRecylerView();
        if(documents.getEvents().size()!=0){
            for (EventDocument doc: documents.getEvents()) {
                adapter.addItem(doc);
                Log.d("A token:assasa ",doc.getTitle());
            }
        }
    }
}
