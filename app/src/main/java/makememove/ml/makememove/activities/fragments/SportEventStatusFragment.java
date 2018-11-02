package makememove.ml.makememove.activities.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import makememove.ml.makememove.R;
import makememove.ml.makememove.activities.UserActivity;

import static makememove.ml.makememove.activities.UserActivity.fragmentManager;

public class SportEventStatusFragment extends Fragment {

    private View Layout;
    private Button testbt;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.sporteventstatus_fragment, container, false);

    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Layout = this.getView();
        if (Layout != null) {
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
        }
    }

}
