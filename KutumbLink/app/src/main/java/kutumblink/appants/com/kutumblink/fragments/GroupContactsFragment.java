package kutumblink.appants.com.kutumblink.fragments;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import kutumblink.appants.com.kutumblink.HomeActivity;
import kutumblink.appants.com.kutumblink.R;
import kutumblink.appants.com.kutumblink.adapter.ContactGroupListAdapter;
import kutumblink.appants.com.kutumblink.adapter.ContactListAdapter;
import kutumblink.appants.com.kutumblink.model.ContactsDo;
import kutumblink.appants.com.kutumblink.model.GroupDo;
import kutumblink.appants.com.kutumblink.utils.Constants;
import kutumblink.appants.com.kutumblink.utils.DatabaseHandler;


/**
 * A simple {@link Fragment} subclass.
 */
public class GroupContactsFragment extends BaseFragment implements Serializable {
    private static final String ARG_PARAM1 = "param1";
    private TextView no_contacts;

    public GroupContactsFragment() {
        // Required empty public constructor
    }


    public static GroupContactsFragment newInstance(String param1)
    {
        GroupContactsFragment fragment=new GroupContactsFragment();


        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    private static final int REQUEST_CONTACT = 0;
    public static Button btn_close;
    ListView lv_conatcst;
    DatabaseHandler dbHandler;
    ImageView iv_contacts;
    public static ArrayList<GroupDo> arr_group = new ArrayList<GroupDo>();
    public static LinearLayout ll_actions;


    TextView tv_Done, tv_Cancel;
    public static Button btn_actions;
    //  Dialog topDialog;
    LinearLayout ll_grpcontacts, ll_grpactionslist, ll_nocontacts;
    ListView lv_grpactionslist;
    public static ArrayList<ContactsDo> arr_contacts = new ArrayList<ContactsDo>();
    boolean isVISIBLEACTIONS = false;
    public static LinearLayout rl_actions;

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_groupcontacts, container, false);
       // Constants.NAV_GROUPS = 102;

        activity.setTitle(Constants.GROUP_NAME);


        HomeActivity.ib_back.setBackgroundColor(Color.TRANSPARENT);

        HomeActivity.ib_back_next.setText("Done");
        HomeActivity.ib_menu.setBackgroundColor(Color.TRANSPARENT);
        HomeActivity.ib_menu.setText("Edit");
        HomeActivity.tv_title.setText(Constants.GROUP_NAME);


        dbHandler = new DatabaseHandler(getActivity());
        lv_conatcst = (ListView) view.findViewById(R.id.lv_contacts);
        btn_close = (Button) view.findViewById(R.id.btn_close);
        btn_actions = (Button) view.findViewById(R.id.btn_ations);
        ll_grpcontacts = (LinearLayout) view.findViewById(R.id.ll_grpcontacts);
        ll_actions = (LinearLayout) view.findViewById(R.id.ll_actions);
        ll_grpactionslist = (LinearLayout) view.findViewById(R.id.ll_grplistactions);
        lv_grpactionslist = (ListView) view.findViewById(R.id.lv_actionsgroups);
        ll_nocontacts = (LinearLayout) view.findViewById(R.id.ll_nocontacts);
        tv_Cancel = (TextView) view.findViewById(R.id.tv_cancel);
        tv_Done = (TextView) view.findViewById(R.id.tv_done);
        rl_actions = (LinearLayout) view.findViewById(R.id.rl_actions);
        iv_contacts = (ImageView) view.findViewById(R.id.iv_selectContacts);
        no_contacts=(TextView)view.findViewById(R.id.no_contacts);
        no_contacts.setVisibility(View.GONE);


        rl_actions.setAlpha(0.5f);
        btn_close.setEnabled(false);
        btn_actions.setEnabled(false);

         new updateContacts(getActivity()).execute();
        arr_contacts.clear();


        tv_Cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ll_grpactionslist.setVisibility(View.GONE);
            }
        });



        iv_contacts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Constants.GROUP_OPERATIONS = "EDIT";
                Constants.GROUP_OLD_NAME = Constants.GROUP_NAME;

                AddGroupFragment groupContacts = new AddGroupFragment(); //New means creating adding.
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.main_container, groupContacts);
                //fragmentTransaction.addToBackStack("group_Main");
                fragmentTransaction.commit();


            }
        });
        tv_Done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Boolean sel = false;
                for (int i = 0; i < arr_group.size(); i++) {

                    if (arr_group.get(i).getGroup_isSELECT() == 1) {

                        sel = true;
                    }
                }

                if (sel) {

                    showConfirmOptionsDialog("Contacts copied to selected groups", "Are you sure?");
                } else {

                    showConfirmOptionsDialog("Groups", "Please select Group");
                }

            }
        });

        //  expListView=(ExpandableListView)view.findViewById(R.id.lvExp);
        ll_actions.setVisibility(View.GONE);

        TextView tv_sms = (TextView) view.findViewById(R.id.tv_sms);
        TextView tv_email = (TextView) view.findViewById(R.id.tv_sendEmail);
        TextView tv_copygrp = (TextView) view.findViewById(R.id.tv_copygrp);
        TextView tv_rmvgrp = (TextView) view.findViewById(R.id.tv_removegroup);
        TextView tv_addevt = (TextView) view.findViewById(R.id.tv_addevt);
        TextView tv_eci = (TextView) view.findViewById(R.id.tv_eci);

        tv_eci.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ArrayList<ContactsDo> selectedContactList = new ArrayList<ContactsDo>();
                for (int a = 0; a < arr_contacts.size(); a++) {

                    if (arr_contacts.get(a).getIS_CONTACT_SELECTED() == 1) {


                        ContactsDo contact = arr_contacts.get(a);
                        selectedContactList.add(contact);

                    }

                }
                //Requesting for generat multiple fiels for vcard.

                if (selectedContactList.size() > 0) {
                    sendVcardAsEmail(selectedContactList);
                } else {
                    makeToast("Please select contacts");
                }

            }
        });
        tv_sms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if( ll_actions.getVisibility()==View.VISIBLE)
                {
                    ll_actions.setVisibility(View.GONE);
                }
                boolean sel = false;
                String phoneNos = "";

                for (int a = 0; a < arr_contacts.size(); a++) {

                    if (arr_contacts.get(a).getIS_CONTACT_SELECTED() == 1) {

                        phoneNos += arr_contacts.get(a).getConatactPhone() + ";";
                        sel = true;
                    }

                }

                if (sel) {

                    Intent intent = new Intent(android.content.Intent.ACTION_VIEW);
                    intent.putExtra("address", phoneNos);

                    intent.putExtra("sms_body", "");
                    intent.setType("vnd.android-dir/mms-sms");
                    startActivity(intent);
                } else {
                    showConfirmDialog(getString(R.string.app_name), "Please select contacts",false);
                }
            }
        });
        tv_email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String data[] = new String[arr_contacts.size()];
                if( ll_actions.getVisibility()==View.VISIBLE)
                {
                    ll_actions.setVisibility(View.GONE);
                }

                boolean sel = false;
                for (int a = 0; a < arr_contacts.size(); a++) {

                    if (!arr_contacts.get(a).getConatactEmail().equalsIgnoreCase("null") && arr_contacts.get(a).getIS_CONTACT_SELECTED() == 1) {
                        data[a] = arr_contacts.get(a).getConatactEmail();


                        sel = true;
                    }

                }


                if (sel) {
                    //  String[] TO = {data};

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
                } else {
                    if(ll_actions.getVisibility()==View.VISIBLE)
                    {
                        ll_actions.setVisibility(View.GONE);
                    }
                    showConfirmDialog("Groups", "Selected contacts do not have email",false);
                }
            }
        });
        ll_grpactionslist.setVisibility(View.GONE);
        tv_copygrp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isVISIBLEACTIONS = false;
                ll_actions.setVisibility(View.GONE);
                boolean isContacts = false;

                arr_group.clear();
                Cursor cg = dbHandler.retriveData("select * from " + DatabaseHandler.TABLE_GROUP);
                if (cg != null) {
                    if (cg.getCount() > 0) {
                        cg.moveToFirst();
                        do {

                            if (!cg.getString(cg.getColumnIndex(dbHandler.GROUP_NAME)).equalsIgnoreCase(Constants.GROUP_NAME)) {

                                GroupDo groupDetails = new GroupDo();
                                groupDetails.setGroup_Name(cg.getString(cg.getColumnIndex(dbHandler.GROUP_NAME)));
                                groupDetails.setGroup_Pic(cg.getString(cg.getColumnIndex(dbHandler.GROUP_PIC)));

                                groupDetails.setGroup_isSELECT(0);

                                arr_group.add(groupDetails);
                            }

                        } while (cg.moveToNext());

                    }
                }
                cg.close();

                if (arr_group.size() > 0) {
                    for (int i = 0; i < arr_contacts.size(); i++) {
                        if (arr_contacts.get(i).getIS_CONTACT_SELECTED() == 1) {
                            isContacts = true;
                        }
                    }

                    if (isContacts) {

                        if (Constants.GROUP_CONTACTS_SIZE != 0) {

                            if (arr_group.size() > 0) {
                                tv_Done.setVisibility(View.VISIBLE);
                            } else {
                                tv_Done.setVisibility(View.GONE);
                            }
                            ll_grpactionslist.setVisibility(View.VISIBLE);

                            lv_grpactionslist.setAdapter(new ContactGroupListAdapter(getActivity(), arr_group));
                        } else {


                            showConfirmDialogActions(getString(R.string.app_name), "You don't have more groups");
                        }
                    } else {
                        showConfirmDialogActions(getString(R.string.app_name), "Please select contacts");
                    }

                } else {
                    showConfirmDialogActions("Groups", "There are no groups to add this contact into.");
                }
            }
        });

        if (Constants.GROUP_CONTACTS_SIZE == 0) {
            ll_nocontacts.setVisibility(View.VISIBLE);
            lv_grpactionslist.setVisibility(View.GONE);
        } else {
            ll_nocontacts.setVisibility(View.GONE);
            lv_grpactionslist.setVisibility(View.VISIBLE);
        }
        tv_rmvgrp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                showConfirmOptionsDialog("Remove contacts from group", "Are you sure?", 3, "");
                //  dbHandler.DeleteTable(dbHandler.TABLE_GROUP, "G_NAME='" + Constants.GROUP_NAME + "'");

                // Toast.makeText(getActivity(), "Group deleted successfully", Toast.LENGTH_LONG).show();

            }
        });

        tv_addevt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if( ll_actions.getVisibility()==View.VISIBLE)
                {
                    ll_actions.setVisibility(View.GONE);
                }
                try {
                    JSONArray jsonArray = new JSONArray();
                    for (int i = 0; i < arr_contacts.size(); i++) {

                        if (arr_contacts.get(i).getIS_CONTACT_SELECTED() == 1) {


                            JSONObject jobj = new JSONObject();
                            jobj.put(DatabaseHandler.PHONE_CONTACT_ID, "" + arr_contacts.get(i).getConatactId());
                            jobj.put(DatabaseHandler.PHONE_CONTACT_NUMBER, "" + arr_contacts.get(i).getConatactPhone());
                            jobj.put(DatabaseHandler.PHONE_CONTACT_EMAIL, "" + arr_contacts.get(i).getConatactEmail());
                            jobj.put(DatabaseHandler.PHONE_CONTACT_NAME, "" + arr_contacts.get(i).getConatactName());

                            jsonArray.put(jobj);

                        }
                    }


                    EditEventsFragment edtFrg = new EditEventsFragment();
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    FragmentTransaction ft = fragmentManager.beginTransaction();

                    Bundle args = new Bundle();
                    args.putString("title", "");
                    args.putString("desc", "");
                    args.putString("time", "");
                    args.putString("contacts", jsonArray.toString());

                    edtFrg.setArguments(args);
                    ft.replace(R.id.main_container, edtFrg);
                    ft.commit();
                } catch (Exception e) {

                }
            }
        });

        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                if (isVISIBLEACTIONS) {
                    isVISIBLEACTIONS = false;
                    ll_actions.setVisibility(View.GONE);
                }


                return false;
            }
        });


        GroupContactsFragment.btn_actions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                boolean sel = false;

                for (int a = 0; a < GroupContactsFragment.arr_contacts.size(); a++) {

                    if (GroupContactsFragment.arr_contacts.get(a).getIS_CONTACT_SELECTED() == 1) {

                        sel = true;
                    }

                }

                if (sel) {

                    isVISIBLEACTIONS = true;
                    ll_actions.setVisibility(View.VISIBLE);
                } else {

                    isVISIBLEACTIONS = false;
                    ll_actions.setVisibility(View.GONE);
                }
            }
        });


        HomeActivity.ib_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Constants.NAV_GROUPS = 100;

                Constants.GROUP_OPERATIONS = "EDIT";
                Constants.GROUP_OLD_NAME = Constants.GROUP_NAME;

                AddGroupFragment groupContacts = new AddGroupFragment(); //New means creating adding.
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.main_container, groupContacts);
               // fragmentTransaction.addToBackStack("group_Main");
                fragmentTransaction.commit();
            }
        });

        HomeActivity.ib_back_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Constants.NAV_GROUPS = 100;

                GroupsMainFragment groupContacts = new GroupsMainFragment(); //New means creating adding.
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.main_container, groupContacts);
                //fragmentTransaction.addToBackStack("group_Main");
                fragmentTransaction.commit();

            }
        });




        btn_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                boolean sel = false;

                for (int a = 0; a < GroupContactsFragment.arr_contacts.size(); a++) {

                    if (GroupContactsFragment.arr_contacts.get(a).getIS_CONTACT_SELECTED() == 1) {

                        sel = true;
                    }

                }


                if (sel) {

                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.main_container, new GroupContactsFragment()).commit();

                } else {
                    GroupContactsFragment.rl_actions.setAlpha(0.5f);
                    GroupContactsFragment.rl_actions.setEnabled(false);
                    GroupContactsFragment.btn_actions.setEnabled(false);
                    GroupContactsFragment.btn_close.setEnabled(false);
                }

            }
        });




        return view;
    }

    private void sendVcardAsEmail(ArrayList<ContactsDo> selectedContactList) {

        if( ll_actions.getVisibility()==View.VISIBLE)
        {
            ll_actions.setVisibility(View.GONE);
        }

        ArrayList<File> fileNames = new ArrayList<File>();
        for (int i = 0; i < selectedContactList.size(); i++) {

            File vcfFile = new File(getActivity().getExternalFilesDir(null), selectedContactList.get(i).getConatactName() + "_info.vcf");

            FileWriter fw = null;
            ContactsDo contact = selectedContactList.get(i);
            try {

                fw = new FileWriter(vcfFile);
                fw.write("BEGIN:VCARD\r\n");
                fw.write("VERSION:3.0\r\n");
                //fw.write("N:" + p.getSurname() + ";" + p.getFirstName() + "\r\n");
                fw.write("N:" + contact.getConatactName() + ";\r\n");
                //fw.write("FN:" + p.getFirstName() + " " + p.getSurname() + "\r\n");
                fw.write("ORG: \r\n");
                fw.write("TITLE:" + contact.getConatactName() + "\r\n");
                fw.write("TEL;TYPE=WORK,VOICE:" + contact.getConatactPhone() + "\r\n");
                fw.write("TEL;TYPE=HOME,VOICE:" + contact.getConatactPhone() + "\r\n");
                //fw.write("ADR;TYPE=WORK:;;" + p.getStreet() + ";" + p.getCity() + ";" + p.getState() + ";" + p.getPostcode() + ";" + p.getCountry() + "\r\n");
                fw.write("EMAIL;TYPE=PREF,INTERNET:" + contact.getConatactEmail() + "\r\n");
                fw.write("END:VCARD\r\n");
                fw.close();

                fileNames.add(vcfFile);

            } catch (IOException e) {
                e.printStackTrace();
            }


        }


        //ArrayList<Uri> uris = new ArrayList<Uri>();
        ArrayList<Uri> arrayList = new ArrayList<Uri>();
        //convert from paths to Android friendly Parcelable Uri's

        for (int i = 0; i < fileNames.size(); i++) {
            Uri u = Uri.fromFile(fileNames.get(i));
            arrayList.add(u);
        }


        if (arrayList.size() > 0) {


            final Intent intent = new Intent(android.content.Intent.ACTION_SEND);
            intent.setType("text/plain");

            intent.putExtra(Intent.EXTRA_SUBJECT, "From " + getString(R.string.app_name));
            intent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, arrayList);
            intent.setType("message/rfc822");
            final PackageManager pm = getActivity().getPackageManager();
            final List<ResolveInfo> matches = pm.queryIntentActivities(intent, 0);
            ResolveInfo best = null;
            for (final ResolveInfo info : matches)
                if (info.activityInfo.packageName.endsWith(".gm") ||
                        info.activityInfo.name.toLowerCase().contains("gmail")) best = info;
            if (best != null)
                intent.setClassName(best.activityInfo.packageName, best.activityInfo.name);
            //   startActivity(intent);
            startActivityForResult(Intent.createChooser(intent, "Send mail client :"), REQUEST_CONTACT);





        }
    }


    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;

    public void showConfirmOptionsDialog(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());


        builder.setTitle(title);
        builder.setIcon(R.mipmap.ic_launcher);
        StringBuffer sb = new StringBuffer(message);


        builder.setMessage(sb.toString()).setCancelable(false);
        builder.setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {


                        for (int i = 0; i < arr_group.size(); i++) {

                            if (arr_group.get(i).getGroup_isSELECT() == 1) {
                                for (int a = 0; a < arr_contacts.size(); a++) {


                                    if (arr_contacts.get(a).getIS_CONTACT_SELECTED() == 1) {

                                        ContentValues cv = new ContentValues();
                                        cv.put(dbHandler.PHONE_CONTACT_ID, "" + arr_contacts.get(a).getConatactId());
                                        cv.put(dbHandler.PHONE_CONTACT_NAME, "" + arr_contacts.get(a).getConatactName());
                                        // cv.put(dbHandler.PHONE_CONTACT_FNAME, "" + arr_contacts.get(a).get);
                                        //cv.put(dbHandler.PHONE_CONTACT_LNAME, "" + contact.getLastName());
                                        cv.put(dbHandler.PHONE_CONTACT_NUMBER, "" + arr_contacts.get(a).getConatactPhone());
                                        cv.put(dbHandler.PHONE_CONTACT_EMAIL, "" + arr_contacts.get(a).getConatactEmail());
                                        cv.put(dbHandler.PHONE_CONTACT_GID, "" + arr_group.get(i).getGroup_Name());
                                        Cursor conatacts = dbHandler.retriveData("select * from " + DatabaseHandler.TABLE_PHONE_CONTACTS + " where Phone_Contact_Gid='" + arr_group.get(i).getGroup_Name() + "' AND Phone_Contact_ID='" + arr_contacts.get(a).getConatactId() + "'");

                                        if (conatacts.getCount() == 0) {
                                            dbHandler.insert(dbHandler.TABLE_PHONE_CONTACTS, cv);
                                        } else {
                                            dbHandler.UpdateTable(dbHandler.TABLE_PHONE_CONTACTS, cv, "where Phone_Contact_Gid='" + arr_group.get(i).getGroup_Name() + "' AND Phone_Contact_ID='" + arr_contacts.get(a).getConatactId() + "'");
                                        }

                                        conatacts.close();
                                    }
                                }


                            }
                        }

                        Constants.NAV_GROUPS = 100;
                        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                        FragmentTransaction ft = fragmentManager.beginTransaction();
                        ft.replace(R.id.main_container, new GroupsMainFragment());
                        ft.commit();
                        ll_grpactionslist.setVisibility(View.GONE);


                    }
                }

        );
        builder.setNegativeButton("Cancel", null);
        builder.show();
    }


    public void readContacts(String contactId) {
        StringBuffer sb = new StringBuffer();
        sb.append("......Contact Details.....");
        ContentResolver cr = getActivity().getContentResolver();
        Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
        String phone = null;
        String name=null;
        String emailContact = null;
        String emailType = null;
        String image_uri = "";

                System.out.println("*****name :  ID : " + contactId);
                sb.append("\n Contact Name:");
                Cursor pCur = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?", new String[]{contactId}, null);
                while (pCur.moveToNext()) {

                        name = pCur.getString(pCur.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                        phone = pCur.getString(pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));

                        sb.append("\n Phone number:" + phone);
                        System.out.println("******phone" + phone + "NAME..." + name);
                }
                pCur.close();
               Cursor emailCur = cr.query(ContactsContract.CommonDataKinds.Email.CONTENT_URI, null, ContactsContract.CommonDataKinds.Email.CONTACT_ID + " = ?", new String[]{contactId}, null);
                while (emailCur.moveToNext()) {
                        emailContact = emailCur.getString(emailCur.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA));
                        emailType = emailCur.getString(emailCur.getColumnIndex(ContactsContract.CommonDataKinds.Email.TYPE));
                        sb.append("\nEmail:" + emailContact + "Email type:" + emailType);
                        System.out.println("****Email " + emailContact + " Email Type : " + emailType);
            }
            emailCur.close();
                ContentValues cv = new ContentValues();
                cv.put(dbHandler.PHONE_CONTACT_NAME, "" + name);
                cv.put(dbHandler.PHONE_CONTACT_NUMBER, "" +phone);
                cv.put(dbHandler.PHONE_CONTACT_EMAIL, "" + emailContact);

                    dbHandler.UpdateTable(dbHandler.TABLE_PHONE_CONTACTS, cv, "Phone_Contact_ID='" + contactId + "'");
            emailCur.close();
    }
    class updateContacts extends AsyncTask<String, String, String> {
        private ProgressDialog dialog;

        public updateContacts(Activity activity) {
            dialog = new ProgressDialog(activity);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
          dialog.setMessage("Please wait...");
            dialog.setCancelable(false);
            dialog.show();

        }

        @Override
        protected String doInBackground(String... f_url) {
            int count;

            arr_contacts.clear();
            Cursor c = dbHandler.retriveData("select * from " + DatabaseHandler.TABLE_PHONE_CONTACTS + " where Phone_Contact_Gid='" + Constants.GROUP_NAME + "' order by Phone_Contact_Name ASC");
            if (c != null) {
                if (c.getCount() > 0) {
                    c.moveToFirst();
                    do {
                        try {
                            if (!c.getString(c.getColumnIndex(dbHandler.PHONE_CONTACT_ID)).equalsIgnoreCase("null")) {
                                readContacts(c.getString(c.getColumnIndex(dbHandler.PHONE_CONTACT_ID)));
                            }
                        } catch (Exception e) {

                        }
                    } while (c.moveToNext());
                }
            }
            c.close();
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
           if (dialog.isShowing()) {
                dialog.dismiss();
                dialog.setCancelable(false);

          arr_contacts.clear();
                Cursor c = dbHandler.retriveData("select * from " + DatabaseHandler.TABLE_PHONE_CONTACTS + " where Phone_Contact_Gid='" + Constants.GROUP_NAME + "' order by Phone_Contact_Name ASC");

                if (c != null) {
                    if (c.getCount() > 0) {
                        c.moveToFirst();
                        do {
                            try {
                                if (!c.getString(c.getColumnIndex(dbHandler.PHONE_CONTACT_ID)).equalsIgnoreCase("null")) {
                                    ContactsDo contactsBean = new ContactsDo();
                                    contactsBean.setConatactGroupName(c.getString(c.getColumnIndex(dbHandler.PHONE_CONTACT_GID)));
                                    contactsBean.setConatactId(c.getString(c.getColumnIndex(dbHandler.PHONE_CONTACT_ID)));
                                    contactsBean.setConatactName(c.getString(c.getColumnIndex(dbHandler.PHONE_CONTACT_NAME)));
                                    contactsBean.setConatactPhone(c.getString(c.getColumnIndex(dbHandler.PHONE_CONTACT_NUMBER)));
                                    contactsBean.setConatactPIC(c.getString(c.getColumnIndex(dbHandler.PHONE_CONTACT_PIC)));
                                    contactsBean.setConatactEmail(c.getString(c.getColumnIndex(dbHandler.PHONE_CONTACT_EMAIL)));
                                    contactsBean.setIS_CONTACT_SELECTED(0);
                                    arr_contacts.add(contactsBean);
                                }
                            } catch (Exception e) {
                        }
                        } while (c.moveToNext());
                    }
                }

               Log.i("TEST","List size:"+arr_contacts.size());
                if(arr_contacts.size()==0)
                {
                    no_contacts.setVisibility(View.VISIBLE);
                }
                else
                {
                    no_contacts.setVisibility(View.GONE);
                }


               if(c!=null)
                c.close();
                lv_conatcst.setAdapter(new ContactListAdapter(getActivity(), arr_contacts));

            }
        }
    }
    @Override
    public void onResume() {
        super.onResume();
        View view = getActivity().getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
       // arr_contacts.clear();
       //  new updateContacts(getActivity()).execute();

    }

   /* @Override
    public void onBackPressed() {

        int count = getFragmentManager().getBackStackEntryCount();

        if (count == 0) {
            //super.onBackPressed();
            //additional code
        } else {
            getFragmentManager().popBackStack();
        }
    }*/

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(ll_actions.getVisibility()==View.VISIBLE)
        ll_actions.setVisibility(View.GONE);
    }
}
