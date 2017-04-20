package kutumblink.appants.com.kutumblink.fragments;


import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import kutumblink.appants.com.kutumblink.HomeActivity;
import kutumblink.appants.com.kutumblink.R;
import kutumblink.appants.com.kutumblink.adapter.EventsListAdapter;
import kutumblink.appants.com.kutumblink.model.EventsDo;
import kutumblink.appants.com.kutumblink.utils.Constants;
import kutumblink.appants.com.kutumblink.utils.DatabaseHandler;


/**
 * A simple {@link Fragment} subclass.
 */
public class EventsMainFragment extends BaseFragment {


    public EventsMainFragment() {
        // Required empty public constructor
    }

    DatabaseHandler dbHandler;
    ArrayList<EventsDo> arr_evts=new ArrayList<EventsDo>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_events_main, container, false);

        dbHandler=new DatabaseHandler(getActivity());
        arr_evts.clear();
        HomeActivity.ib_back.setBackgroundResource(R.mipmap.ic_launcher);

        HomeActivity.ib_back_next.setText("");
        HomeActivity.ib_menu.setBackgroundResource(R.mipmap.menu);
        HomeActivity.ib_menu.setText("");
        HomeActivity.tv_title.setText("Events");


        Cursor c=dbHandler.retriveData("select * from "+ DatabaseHandler.TABLE_EVENTS);
        if(c!=null)
        {
            if(c.getCount()>0)
            {
                c.moveToFirst();
                do {


                    EventsDo evtDetails=new EventsDo();
                    evtDetails.setEvtTitle(c.getString(c.getColumnIndex(DatabaseHandler.EVT_TITLE)));
                    evtDetails.setEvtDesc(c.getString(c.getColumnIndex(DatabaseHandler.EVT_DESC)));
                    evtDetails.setEvtContacts(c.getString(c.getColumnIndex(DatabaseHandler.EVT_CONTACTS)));
                    evtDetails.setEvtDate(c.getString(c.getColumnIndex(DatabaseHandler.EVT_CREATED_ON)));


                    //  groupDetails.setGroup_ID(c.getString(c.getColumnIndex(dbHandler.GROUP_ID)));
                    arr_evts.add(evtDetails);

                }while(c.moveToNext());

            }
        }

        HomeActivity.ib_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.main_container, new SettingsFragment()).commit();
            }
        });
        ListView listView=(ListView)view.findViewById(R.id.listView);

        listView.setAdapter(new EventsListAdapter(getActivity(),arr_evts));


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {


                Constants.EVENT_OPERATIONS="EDIT";
                Constants.Event_NAME=arr_evts.get(i).getEvtTitle();


                EventActionsFragment groupContacts = new EventActionsFragment(); //New means creating adding.
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();


                Bundle args = new Bundle();
                args.putString("title",arr_evts.get(i).getEvtTitle());
                args.putString("desc",arr_evts.get(i).getEvtDesc());
                args.putString("time",arr_evts.get(i).getEvtDate());
                args.putString("contacts",arr_evts.get(i).getEvtContacts());
                groupContacts.setArguments(args);
                fragmentTransaction.replace(R.id.main_container, groupContacts);
                fragmentTransaction.addToBackStack("Events_Main");
                fragmentTransaction.commit();


            }
        });

        View addItem=view.findViewById(R.id.add_layout);

        addItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Constants.EVENT_OPERATIONS="SAVE";

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
