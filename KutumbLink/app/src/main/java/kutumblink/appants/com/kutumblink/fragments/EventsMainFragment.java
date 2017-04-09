package kutumblink.appants.com.kutumblink.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import kutumblink.appants.com.kutumblink.HomeActivity;
import kutumblink.appants.com.kutumblink.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class EventsMainFragment extends BaseFragment {


    public EventsMainFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_events_main, container, false);



        HomeActivity.ib_back.setBackgroundResource(R.mipmap.ic_launcher);

        HomeActivity.ib_back_next.setText("");
        HomeActivity.ib_menu.setBackgroundResource(R.mipmap.menu);
        HomeActivity.ib_menu.setText("");
        HomeActivity.tv_title.setText("Events");


        ListView listView=(ListView)view.findViewById(R.id.listView);

        View addItem=view.findViewById(R.id.add_layout);

        addItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                EditEventsFragment editEvent = new EditEventsFragment(); //New means creating adding.
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.main_container, editEvent);
                fragmentTransaction.addToBackStack("edit_event");
                fragmentTransaction.commit();
            }
        });




        return  view;
    }

}
