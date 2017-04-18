package kutumblink.appants.com.kutumblink.fragments;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import kutumblink.appants.com.kutumblink.HomeActivity;
import kutumblink.appants.com.kutumblink.R;
import kutumblink.appants.com.kutumblink.utils.Constants;
import kutumblink.appants.com.kutumblink.utils.DatabaseHandler;


public class EventActionsFragment extends BaseFragment {


    public EventActionsFragment() {
        // Required empty public constructor
    }
    TextView tv_addevent,tv_eventSMS,tv_eventEmail;
    TextView tv_evtTitle,tv_evtDesc,tv_evtContacts,tv_evtTime;
    LinearLayout ll_actions,ll_events;
    ImageView iv_delete_event;
    Button btn_actions;


    String contactsInfo="";

    DatabaseHandler dbHandler;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
      //  View view = inflater.inflate(R.layout.event_details_layout, container, false);
        View view= inflater.inflate(R.layout.event_details_layout, container, false);

        dbHandler=new DatabaseHandler(getActivity());

        Bundle args = getArguments();
        final String title = args.getString("title");
        final String desc = args.getString("desc");
        final String time = args.getString("time");
        final String contacts = args.getString("contacts");
        ll_actions=(LinearLayout)view.findViewById(R.id.ll_actions);
        ll_events=(LinearLayout)view.findViewById(R.id.ll_eventactions);

        btn_actions=(Button)view.findViewById(R.id.btn_ations);

        tv_evtTitle=(TextView)view.findViewById(R.id.tv_event_title);
        tv_evtDesc=(TextView)view.findViewById(R.id.tv_desc);
        tv_evtContacts=(TextView)view.findViewById(R.id.tv_event_contactinfo);
        tv_evtTime=(TextView)view.findViewById(R.id.tv_event_time);
        iv_delete_event=(ImageView)view.findViewById(R.id.btn_delete_event);

        ll_actions.setVisibility(View.GONE);
        tv_evtTitle.setText(title);
        tv_evtDesc.setText(desc);
        tv_evtTime.setText(time.replace(",","\n"));

        Log.v("COntacts...","CONATCSTSSS..."+contacts);
        try {

            JSONArray jArr=new JSONArray(contacts);

            for(int i=0;i<jArr.length();i++){

                JSONObject jobj=jArr.getJSONObject(i);
                contactsInfo+=jobj.getString(DatabaseHandler.PHONE_CONTACT_NAME)+"\n";
            }


           // contactsInfo=jo


        }catch(JSONException e){

        }
        tv_evtContacts.setText(contactsInfo);


        HomeActivity.ib_back.setBackgroundResource(R.mipmap.left_arrow);

        HomeActivity.ib_back_next.setText("Events");
        HomeActivity.ib_menu.setBackgroundColor(Color.TRANSPARENT);
        HomeActivity.tv_title.setText("Event Details");
        HomeActivity.ib_menu.setText("Edit");

        HomeActivity.ib_back_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction ft=fragmentManager.beginTransaction();
                ft.replace(R.id.main_container, new EventsMainFragment());
                ft.commit();
            }
        });
        HomeActivity.ib_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction ft=fragmentManager.beginTransaction();
                ft.replace(R.id.main_container, new EventsMainFragment());
                ft.commit();
            }
        });


        HomeActivity.ib_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Constants.EVENT_OPERATIONS="EDIT";
                Constants.EVENTS_OLD_NAME=tv_evtTitle.getText().toString();

                EditEventsFragment editEventFrag =new EditEventsFragment();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction ft=fragmentManager.beginTransaction();
                Bundle args = new Bundle();
                args.putString("contacts",contacts);
                args.putString("desc",desc);
                args.putString("title",title);
                args.putString("time",time);
                editEventFrag.setArguments(args);
                ft.replace(R.id.main_container, editEventFrag);
                ft.commit();
            }
        });
        btn_actions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ll_actions.setVisibility(View.VISIBLE);
            }
        });

        iv_delete_event.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dbHandler.DeleteTable(DatabaseHandler.TABLE_EVENTS,"evt_title='"+title+"'");
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction ft=fragmentManager.beginTransaction();
                ft.replace(R.id.main_container, new EventsMainFragment());
                ft.commit();
            }
        });

        ll_events.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ll_actions.setVisibility(View.GONE);
            }
        });

       tv_addevent=(TextView)view.findViewById(R.id.tv_addevent);
        tv_eventSMS=(TextView)view.findViewById(R.id.tv_addevent);
        tv_eventEmail=(TextView)view.findViewById(R.id.tv_addevent);


        return  view;
    }
}
