package kutumblink.appants.com.kutumblink.fragments;


import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
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


    public GroupContactsFragment() {
        // Required empty public constructor
    }

    private static final int REQUEST_CONTACT = 0;
    public static Button btn_close;
    ListView lv_conatcst;
    DatabaseHandler dbHandler;
    ImageView iv_contacts;
    public static ArrayList<GroupDo> arr_group = new ArrayList<GroupDo>();
    LinearLayout ll_actions;


    TextView tv_Done, tv_Cancel;
    public static Button btn_actions;
    //  Dialog topDialog;
    LinearLayout ll_grpcontacts, ll_grpactionslist, ll_nocontacts;
    ListView lv_grpactionslist;
    public static ArrayList<ContactsDo> arr_contacts = new ArrayList<ContactsDo>();
    boolean isVISIBLEACTIONS = false;
    public static RelativeLayout rl_actions;

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_groupcontacts, container, false);
        Constants.NAV_GROUPS = 102;
        arr_contacts.clear();
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
        rl_actions = (RelativeLayout) view.findViewById(R.id.rl_actions);
        iv_contacts = (ImageView) view.findViewById(R.id.iv_selectContacts);

        rl_actions.setAlpha(0.5f);
        btn_close.setEnabled(false);
        btn_actions.setEnabled(false);






      /*  for (int a = 0; a < arr_contacts.size(); a++) {





            String whereName = ContactsContract.Data.MIMETYPE + " = ? AND " + ContactsContract.CommonDataKinds.StructuredName.CONTACT_ID + " = ?";
            String[] whereNameParams = new String[] { ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE, arr_contacts.get(a).getConatactId() };
            Cursor nameCur = getActivity().getContentResolver().query(ContactsContract.Data.CONTENT_URI, null, whereName, whereNameParams, ContactsContract.CommonDataKinds.StructuredName.GIVEN_NAME);
            while (nameCur.moveToNext()) {
                String fname = nameCur.getString(nameCur.getColumnIndex(ContactsContract.CommonDataKinds.StructuredName.GIVEN_NAME));
                String lname = nameCur.getString(nameCur.getColumnIndex(ContactsContract.CommonDataKinds.StructuredName.FAMILY_NAME));
                String mobileno = nameCur.getString(nameCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                String email = nameCur.getString(nameCur.getColumnIndex(ContactsContract.CommonDataKinds.Email.ADDRESS));


                Log.v("NAME","DATA FIRTSNAME.."+fname);
                Log.v("NAME","DATA FIRTSNAME.."+lname);
                Log.v("NAME","DATA FIRTSNAME.."+mobileno);
                Log.v("NAME","DATA FIRTSNAME.."+email);

            }
            nameCur.close();

        }*/


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
                fragmentTransaction.addToBackStack("group_Main");
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
                    showConfirmDialog(getString(R.string.app_name), "Please select contacts");
                }
            }
        });
        tv_email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String data[] = new String[arr_contacts.size()];

                boolean sel = false;
                for (int a = 0; a < arr_contacts.size(); a++) {

                    if (!arr_contacts.get(a).getConatactEmail().equalsIgnoreCase("null") && arr_contacts.get(a).getIS_CONTACT_SELECTED() == 1) {
                        data[a] = arr_contacts.get(a).getConatactEmail();

                      /*  ContentResolver cr = getActivity().getContentResolver();
                        Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
                        if (cur.getCount() > 0) {
                            while (cur.moveToNext()) {
                                String id = cur.getString(cur.getColumnIndex(ContactsContract.Contacts._ID));
                                Cursor cur1 = cr.query(
                                        ContactsContract.CommonDataKinds.Email.CONTENT_URI, null,
                                        ContactsContract.CommonDataKinds.Email.CONTACT_ID + " = ?",
                                        new String[]{id}, null);
                                while (cur1.moveToNext()) {
                                    //to get the contact names
                                    String name = cur1.getString(cur1.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));

                                    String email = cur1.getString(cur1.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA));

                                    if (email != null) {

                                        data[a] = email;
                                    }
                                }
                                cur1.close();
                            }
                        }*/

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
                        Toast.makeText(getActivity(), "Contact's doen't have email id", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    showConfirmDialog("Groups", "Selected contacts do not have email");
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

        activity.setTitle(Constants.GROUP_NAME);


        HomeActivity.ib_back.setBackgroundColor(Color.TRANSPARENT);

        HomeActivity.ib_back_next.setText("Done");
        HomeActivity.ib_menu.setBackgroundColor(Color.TRANSPARENT);
        HomeActivity.ib_menu.setText("Edit");
        HomeActivity.tv_title.setText(Constants.GROUP_NAME);
       /* topDialog = new Dialog(getActivity(), android.R.style.Theme_Translucent_NoTitleBar);
        topDialog.setCanceledOnTouchOutside(true);
*/
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
                fragmentTransaction.addToBackStack("group_Main");
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
                fragmentTransaction.addToBackStack("group_Main");
                fragmentTransaction.commit();

            }
        });

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

    //    Cursor c = dbHandler.retriveData("select * from " + DatabaseHandler.TABLE_PHONE_CONTACTS + " where Phone_Contact_Gid='" + Constants.GROUP_NAME + "' order by Phone_Contact_Name ASC");


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


        lv_conatcst.setAdapter(new ContactListAdapter(getActivity(), arr_contacts));


        return view;
    }

    private void sendVcardAsEmail(ArrayList<ContactsDo> selectedContactList) {

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



         /*   Intent emailIntent = new Intent(Intent.ACTION_SEND_MULTIPLE, Uri.parse("mailto:"));
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, "From "+getString(R.string.app_name));
            emailIntent.setType("text/plain");
        *//*Uri uri1 = Uri.parse("file://" +  URI1);
        Uri uri2 = Uri.parse("file://" +  URI2);
        Uri uri3 = Uri.parse("file://" +  URI3);*//*

       *//* arrayList.add(uri1);
        arrayList.add(uri2);
        arrayList.add(uri3);*//*
            emailIntent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, arrayList);
            emailIntent.setType("message/rfc822");
            emailIntent.putExtra(Intent.EXTRA_TEXT, "-");
            startActivity(Intent.createChooser(emailIntent, "Send Via..."));*/

        }
    }

    // ExpandableListAdapter listAdapter;
    //  ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;


  /*  private void prepareListData() {
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();

        // Adding child data
        listDataHeader.add("Action");

        // Adding child data
        List<String> action = new ArrayList<String>();
        action.add("Send Text Message");
        action.add("Email");
        action.add("Add to Group");
        action.add("Remove the Group");
        action.add("Add Event");
        action.add("Email Contact Info");



        listDataChild.put(listDataHeader.get(0), action); // Header, Child data

    }
*/


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

            Log.v("DATA....","DATA FIRSTLOADED...NAME"+name);
        Log.v("DATA....","DATA FIRSTLOADED...PHONE"+phone);
        Log.v("DATA....","DATA FIRSTLOADED...EMAILID"+emailContact);
        Log.v("DATA....","DATA FIRSTLOADED...EMAILTYPE"+emailType);


                ContentValues cv = new ContentValues();
                cv.put(dbHandler.PHONE_CONTACT_NAME, "" + name);
                // cv.put(dbHandler.PHONE_CONTACT_FNAME, "" + arr_contacts.get(a).get);
                //cv.put(dbHandler.PHONE_CONTACT_LNAME, "" + contact.getLastName());
                cv.put(dbHandler.PHONE_CONTACT_NUMBER, "" +phone);
                cv.put(dbHandler.PHONE_CONTACT_EMAIL, "" + emailContact);

                    dbHandler.UpdateTable(dbHandler.TABLE_PHONE_CONTACTS, cv, "Phone_Contact_ID='" + contactId + "'");







            emailCur.close();
     //   }
    }
}
