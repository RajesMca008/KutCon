package kutumblink.appants.com.kutumblink.fragments;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.ContactsContract;
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

import com.onegravity.contactpicker.contact.ContactDescription;
import com.onegravity.contactpicker.contact.ContactSortOrder;
import com.onegravity.contactpicker.core.ContactPickerActivity;
import com.onegravity.contactpicker.picture.ContactPictureType;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import kutumblink.appants.com.kutumblink.HomeActivity;
import kutumblink.appants.com.kutumblink.R;
import kutumblink.appants.com.kutumblink.model.EventsDo;
import kutumblink.appants.com.kutumblink.utils.Constants;
import kutumblink.appants.com.kutumblink.utils.DatabaseHandler;


public class EventActionsFragment extends BaseFragment {

    private static final int REQUEST_CONTACT = 0;
    int INSERT_CONTACT_REQUEST = 2;

    public EventActionsFragment() {
        // Required empty public constructor
    }

    TextView tv_addevent, tv_eventSMS, tv_eventEmail;
    TextView tv_evtTitle, tv_evtDesc, tv_evtContacts, tv_evtTime;
    LinearLayout ll_actions, ll_events;
    ImageView iv_delete_event;
    Button btn_actions;
    ArrayList<EventsDo> arrEvt = new ArrayList<>();

    String contactsInfo = "";

    DatabaseHandler dbHandler;

    String title = "";
    String desc = "";
    String time = "";
    String contacts = "";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //  View view = inflater.inflate(R.layout.event_details_layout, container, false);
        View view = inflater.inflate(R.layout.event_details_layout, container, false);

        dbHandler = new DatabaseHandler(getActivity());

        Bundle args = getArguments();

        if (args != null) {
            title = args.getString("title");
            desc = args.getString("desc");
            time = args.getString("time");
            contacts = args.getString("contacts");
        }
        ll_actions = (LinearLayout) view.findViewById(R.id.ll_actions);
        ll_events = (LinearLayout) view.findViewById(R.id.ll_eventactions);

        btn_actions = (Button) view.findViewById(R.id.btn_ations);

        tv_evtTitle = (TextView) view.findViewById(R.id.tv_event_title);
        tv_evtDesc = (TextView) view.findViewById(R.id.tv_desc);
        tv_evtContacts = (TextView) view.findViewById(R.id.tv_event_contactinfo);
        tv_evtTime = (TextView) view.findViewById(R.id.tv_event_time);
        iv_delete_event = (ImageView) view.findViewById(R.id.btn_delete_event);

        ll_actions.setVisibility(View.GONE);
        tv_evtTitle.setText(title);
        tv_evtDesc.setText(desc);
        tv_evtTime.setText(time.replace(",", "\n"));

        Log.v("COntacts...", "CONATCSTSSS..." + contacts);
        try {

            JSONArray jArr = new JSONArray(contacts);

            for (int i = 0; i < jArr.length(); i++) {

                JSONObject jobj = jArr.getJSONObject(i);
                contactsInfo += jobj.getString(DatabaseHandler.PHONE_CONTACT_NAME) + "\n";
                EventsDo ed = new EventsDo();
                ed.setEvtContacts(jobj.getString(DatabaseHandler.PHONE_CONTACT_ID));
                arrEvt.add(ed);

            }

        } catch (JSONException e) {

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
                FragmentTransaction ft = fragmentManager.beginTransaction();
                ft.replace(R.id.main_container, new EventsMainFragment());
                ft.commit();
            }
        });
        HomeActivity.ib_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction ft = fragmentManager.beginTransaction();
                ft.replace(R.id.main_container, new EventsMainFragment());
                ft.commit();
            }
        });


        HomeActivity.ib_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Constants.EVENT_OPERATIONS = "EDIT";
                Constants.EVENTS_OLD_NAME = tv_evtTitle.getText().toString();

                EditEventsFragment editEventFrag = new EditEventsFragment();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction ft = fragmentManager.beginTransaction();
                Bundle args = new Bundle();
                args.putString("contacts", contacts);
                args.putString("desc", desc);
                args.putString("title", title);
                args.putString("time", time);
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
                dbHandler.DeleteTable(DatabaseHandler.TABLE_EVENTS, "evt_title='" + title + "'");
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction ft = fragmentManager.beginTransaction();
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

        tv_addevent = (TextView) view.findViewById(R.id.tv_addevent);
        tv_eventSMS = (TextView) view.findViewById(R.id.tv_addevent);
        tv_eventEmail = (TextView) view.findViewById(R.id.tv_addevent);

        final Collection<Long> selectContats = new ArrayList<Long>();
        tv_addevent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (int i = 0; i < arrEvt.size(); i++) {
                    selectContats.add(Long.parseLong(arrEvt.get(i).getEvtContacts()));
                }
                Intent intent = new Intent(getActivity(), ContactPickerActivity.class)
                        // .putExtra(ContactPickerActivity.EXTRA_THEME, mDarkTheme ? R.style.Theme_Dark : R.style.Theme_Light)
                        .putExtra(ContactPickerActivity.EXTRA_CONTACT_BADGE_TYPE, ContactPictureType.ROUND.name())
                        .putExtra(ContactPickerActivity.EXTRA_SHOW_CHECK_ALL, true)
                        //  .putExtra(ContactPickerActivity.EXTRA_PRESELECTED_CONTACTS,  GroupsMainFragment.arr_group)
                        .putExtra(ContactPickerActivity.EXTRA_CONTACT_DESCRIPTION, ContactDescription.ADDRESS.name())
                        .putExtra(ContactPickerActivity.EXTRA_CONTACT_DESCRIPTION_TYPE, ContactsContract.CommonDataKinds.Email.TYPE_WORK)
                        .putExtra(ContactPickerActivity.EXTRA_ONLY_CONTACTS_WITH_PHONE, true)

                        .putExtra(ContactPickerActivity.EXTRA_PRESELECTED_CONTACTS, (Serializable) selectContats)

                        .putExtra(ContactPickerActivity.EXTRA_CONTACT_SORT_ORDER, ContactSortOrder.AUTOMATIC.name());
                //.putExtra(ContactPickerActivity.SELECTED_CONTACTS, GroupContactsFragment.arr_contacts);

                startActivityForResult(intent, REQUEST_CONTACT);
            }
        });

        tv_eventSMS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        tv_eventEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        return view;
    }
}
