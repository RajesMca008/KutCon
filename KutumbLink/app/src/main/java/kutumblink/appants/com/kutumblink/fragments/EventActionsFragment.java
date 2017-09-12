package kutumblink.appants.com.kutumblink.fragments;


import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.onegravity.contactpicker.contact.Contact;
import com.onegravity.contactpicker.contact.ContactDescription;
import com.onegravity.contactpicker.contact.ContactSortOrder;
import com.onegravity.contactpicker.core.ContactPickerActivity;
import com.onegravity.contactpicker.picture.ContactPictureType;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

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
    LinearLayout  ll_events;
    TextView iv_delete_event;
    Button btn_actions;
    ArrayList<EventsDo> arrEvt = new ArrayList<>();

    String contactsInfo = "";

    DatabaseHandler dbHandler;

    String b_title = "";
    String b_desc = "";
    String b_time = "";
    String b_contactsInfo = "";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //  View view = inflater.inflate(R.layout.event_details_layout, container, false);
        View view = inflater.inflate(R.layout.event_details_layout, container, false);

        dbHandler = new DatabaseHandler(getActivity());

        Bundle args = getArguments();

        if (args != null) {
            b_title = args.getString("title");
            b_desc = args.getString("desc");
            b_time = args.getString("time");
            b_contactsInfo = args.getString("contacts");
        }
        ll_actions = (LinearLayout) view.findViewById(R.id.ll_actions);
        ll_events = (LinearLayout) view.findViewById(R.id.ll_eventactions);

        btn_actions = (Button) view.findViewById(R.id.btn_ations);

        tv_evtTitle = (TextView) view.findViewById(R.id.tv_event_title);
        tv_evtDesc = (TextView) view.findViewById(R.id.tv_desc);
        tv_evtContacts = (TextView) view.findViewById(R.id.tv_event_contactinfo);
        tv_evtTime = (TextView) view.findViewById(R.id.tv_event_time);
        iv_delete_event = (TextView) view.findViewById(R.id.btn_delete_event);

        tv_addevent = (TextView) view.findViewById(R.id.tv_addevent);
        tv_eventSMS = (TextView) view.findViewById(R.id.tv_eventsms);
        tv_eventEmail = (TextView) view.findViewById(R.id.tv_eventsendEmail);


        ll_actions.setVisibility(View.GONE);
        tv_evtTitle.setText(b_title);
        tv_evtDesc.setText(b_desc);


        tv_evtTime.setText(b_time.replace(",", "\n")+"rajesh");
        try{

            String date = new SimpleDateFormat("MMM dd yyyy").format(new SimpleDateFormat("dd/mm/yyyy").parse(b_time.split(",")[0]));

            SimpleDateFormat date24Format= new SimpleDateFormat("HH:mm");
            Date time24Hours = date24Format.parse(b_time.split(",")[1].trim());
            SimpleDateFormat date12Format= new SimpleDateFormat("hh:mm a");

            String timeText=date12Format.format(time24Hours);
            date=date+"\n"+timeText;
            tv_evtTime.setText(date);

        }catch (Exception e)
        {
            e.printStackTrace();
        }

        try {

            if (b_contactsInfo != null) {

                JSONArray jArr = new JSONArray(b_contactsInfo);

                for (int i = 0; i < jArr.length(); i++) {

                    JSONObject jobj = jArr.getJSONObject(i);
                    contactsInfo += jobj.getString(DatabaseHandler.PHONE_CONTACT_NAME) + "\n";
                    EventsDo ed = new EventsDo();
                    ed.setEvtContacts(jobj.getString(DatabaseHandler.PHONE_CONTACT_ID));
                    ed.setEvtphone(jobj.getString(DatabaseHandler.PHONE_CONTACT_NUMBER));
                    ed.setEvtEmail(jobj.getString(DatabaseHandler.PHONE_CONTACT_EMAIL));
                    arrEvt.add(ed);

                }
            }

        } catch (JSONException e) {

        }
        if(contactsInfo.length()==0){
            tv_evtContacts.setText(contactsInfo);
        }else {
            tv_evtContacts.setText(contactsInfo);
        }


        HomeActivity.ib_back.setBackgroundResource(R.drawable.left_arrow);

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
                args.putString("contacts", b_contactsInfo);
                args.putString("desc", b_desc);
                args.putString("title", b_title);
                args.putString("time", b_time);
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

                showConfirmOptionsDialog("Delete Event","Are you sure?",Constants.EVENTS_DELETE,b_title);


            }
        });

        ll_events.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ll_actions.setVisibility(View.GONE);
            }
        });


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
                        .putExtra(ContactPickerActivity.EXTRA_ONLY_CONTACTS_WITH_PHONE, false)

                        .putExtra(ContactPickerActivity.EXTRA_PRESELECTED_CONTACTS, (Serializable) selectContats)

                        .putExtra(ContactPickerActivity.EXTRA_CONTACT_SORT_ORDER, ContactSortOrder.AUTOMATIC.name());
                //.putExtra(ContactPickerActivity.SELECTED_CONTACTS, GroupContactsFragment.arr_contacts);

                startActivityForResult(intent, REQUEST_CONTACT);
            }
        });

        tv_eventSMS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean sel = false;
                String phoneNos = "";

                for (int a = 0; a < arrEvt.size(); a++) {
                    phoneNos += arrEvt.get(a).getEvtphone() + ";";
                    sel = true;
                }

                if (sel) {

                    Intent intent = new Intent(android.content.Intent.ACTION_VIEW);
                    intent.putExtra("address", phoneNos);

                    intent.putExtra("sms_body", "Event Deails \n" + tv_evtTitle.getText().toString() + "\n Event Date & Time:" + tv_evtTime.getText().toString());
                    intent.setType("vnd.android-dir/mms-sms");
                    startActivity(intent);
                } else {
                    showConfirmDialogActions(getString(R.string.app_name), "Please select contacts");
                }
            }
        });
        tv_eventEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String data[] = new String[arrEvt.size()];


                if (data != null) {

                    final Intent intent = new Intent(android.content.Intent.ACTION_SEND);
                    intent.setType("text/plain");

                    intent.putExtra(Intent.EXTRA_EMAIL, data);
                    intent.setType("message/rfc822");
                    final PackageManager pm = getActivity().getPackageManager();
                    final List<ResolveInfo> matches = pm.queryIntentActivities(intent, 0);
                    ResolveInfo best = null;
                    for (final ResolveInfo info : matches)
                        if (info.activityInfo.packageName.endsWith(".gm") ||
                                info.activityInfo.name.toLowerCase().contains("gmail"))
                            best = info;
                    if (best != null)
                        intent.setClassName(best.activityInfo.packageName, best.activityInfo.name);
                    //   startActivity(intent);
                    startActivityForResult(Intent.createChooser(intent, "Send mail client :"), Activity.RESULT_OK);


                } else {
                    Toast.makeText(getActivity(), "Contact's doesn't have email id", Toast.LENGTH_SHORT).show();
                }

            }
        });

        return view;
    }


    List<Contact> contacts;

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CONTACT && resultCode == Activity.RESULT_OK &&
                data != null && data.hasExtra(ContactPickerActivity.RESULT_CONTACT_DATA)) {

            contacts = (List<Contact>) data.getSerializableExtra(ContactPickerActivity.RESULT_CONTACT_DATA);


            try {

                JSONArray jsonArray = new JSONArray();
                for (Contact contact : contacts) {
                    JSONObject jobj = new JSONObject();
                    jobj.put(DatabaseHandler.PHONE_CONTACT_ID, "" + contact.getId());
                    jobj.put(DatabaseHandler.PHONE_CONTACT_NUMBER, "" + contact.getPhone(0));
                    jobj.put(DatabaseHandler.PHONE_CONTACT_EMAIL, "" + contact.getEmail(0));
                    jobj.put(DatabaseHandler.PHONE_CONTACT_NAME, "" + contact.getDisplayName());

                    jsonArray.put(jobj);

                    Log.v("DATA....", "DATA.....SAVE..CONTACTID...." + contact.getId());
                }
                ContentValues cv = new ContentValues();
                cv.put(DatabaseHandler.EVT_TITLE, tv_evtTitle.getText().toString());
                cv.put(DatabaseHandler.EVT_DESC, tv_evtDesc.getText().toString());
                cv.put(DatabaseHandler.EVT_CONTACTS, jsonArray.toString());
                cv.put(DatabaseHandler.EVT_CREATED_ON, tv_evtTime.getText().toString());
                //  dbHandler.insert(DatabaseHandler.TABLE_EVENTS, cv);
                dbHandler.UpdateTable(DatabaseHandler.TABLE_EVENTS, cv, " evt_title='" + tv_evtTitle.getText().toString() + "'");

                Log.v("DATA....", "DATA.....SAVE..." + jsonArray.toString());


                EventActionsFragment groupContacts = new EventActionsFragment(); //New means creating adding.
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();


                Bundle args = new Bundle();
                args.putString("title",tv_evtTitle.getText().toString());
                args.putString("desc",tv_evtDesc.getText().toString());
                args.putString("time",tv_evtTime.getText().toString());
                args.putString("contacts",jsonArray.toString());
                groupContacts.setArguments(args);
                fragmentTransaction.replace(R.id.main_container, groupContacts);
                fragmentTransaction.addToBackStack("Events_Main");
                fragmentTransaction.commit();


            } catch (Exception e) {

            }

          /*  FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.main_container, new EventActionsFragment()).commit();*/

            //  }


        } else if (requestCode == INSERT_CONTACT_REQUEST) {
            if (resultCode == Activity.RESULT_OK) {


            } else if (resultCode == Activity.RESULT_CANCELED) {
                //    Toast.makeText(getActivity(),"Contact Added Succesfully",Toast.LENGTH_SHORT).show();
            }

        }
    }
}
