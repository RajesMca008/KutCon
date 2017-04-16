package kutumblink.appants.com.kutumblink.fragments;


import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import kutumblink.appants.com.kutumblink.HomeActivity;
import kutumblink.appants.com.kutumblink.R;
import kutumblink.appants.com.kutumblink.adapter.ContactListAdapter;
import kutumblink.appants.com.kutumblink.model.ContactsDo;
import kutumblink.appants.com.kutumblink.utils.Constants;
import kutumblink.appants.com.kutumblink.utils.DatabaseHandler;


/**
 * A simple {@link Fragment} subclass.
 */
public class GroupContactsFragment extends BaseFragment implements Serializable  {


    public GroupContactsFragment() {
        // Required empty public constructor
    }
    Button btn_close;
    ListView lv_conatcst;
    DatabaseHandler dbHandler;

    LinearLayout ll_actions;



    Button btn_actions;
  //  Dialog topDialog;
    LinearLayout ll_grpcontacts,ll_grpactionslist;
    ListView lv_grpactionslist;
    public static ArrayList<ContactsDo> arr_contacts=new ArrayList<ContactsDo>();
    boolean isVISIBLEACTIONS=false;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_groupcontacts, container, false);
        Constants.NAV_GROUPS=102;
        arr_contacts.clear();
        dbHandler=new DatabaseHandler(getActivity());
        lv_conatcst=(ListView)view.findViewById(R.id.lv_contacts);
        btn_close=(Button)view.findViewById(R.id.btn_close);
        btn_actions=(Button)view.findViewById(R.id.btn_ations);
        ll_grpcontacts=(LinearLayout)view.findViewById(R.id.ll_grpcontacts);
        ll_actions=(LinearLayout)view.findViewById(R.id.ll_actions);
        ll_grpactionslist=(LinearLayout)view.findViewById(R.id.ll_grplistactions);
        lv_grpactionslist=(ListView)view.findViewById(R.id.lv_actionsgroups);

      //  expListView=(ExpandableListView)view.findViewById(R.id.lvExp);
        ll_actions.setVisibility(View.GONE);

        TextView tv_sms=(TextView)view.findViewById(R.id.tv_sms);
        TextView tv_email=(TextView)view.findViewById(R.id.tv_sendEmail);
        TextView tv_copygrp=(TextView)view.findViewById(R.id.tv_copygrp);
        TextView tv_rmvgrp=(TextView)view.findViewById(R.id.tv_removegroup);
        TextView tv_addevt=(TextView)view.findViewById(R.id.tv_addevt);
        TextView tv_eci=(TextView)view.findViewById(R.id.tv_eci);
        tv_sms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean sel=false;
                String phoneNos="";

                for(int a=0;a<arr_contacts.size();a++){

                    if(arr_contacts.get(a).getIS_CONTACT_SELECTED()==1) {

                        phoneNos+=arr_contacts.get(a).getConatactPhone()+";";
                        sel=true;
                    }

                }

                if(sel) {

                    Intent intent = new Intent(android.content.Intent.ACTION_VIEW);
                    intent.putExtra("address", phoneNos);

                    intent.putExtra("sms_body", "");
                    intent.setType("vnd.android-dir/mms-sms");
                    startActivity(intent);
                }else{
                    showConfirmDialog(getString(R.string.app_name),"Please select contacts");
                }
            }
        });
        tv_email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String data[]=new String[arr_contacts.size()];

                boolean sel=false;
                for(int a=0;a<arr_contacts.size();a++){

                    if(arr_contacts.get(a).getConatactEmail().length()!=0) {

                        data[a]=arr_contacts.get(a).getConatactEmail();

                        sel=true;
                    }

                }


                if(sel){
                    String[] TO = {""};
                    Intent emailIntent = new Intent(Intent.ACTION_SEND);

                    emailIntent.setData(Uri.parse("mailto:"));
                    emailIntent.setType("text/plain");
                    emailIntent.putExtra(Intent.EXTRA_EMAIL, data);
                    emailIntent.putExtra(Intent.EXTRA_SUBJECT, "");
                    emailIntent.putExtra(Intent.EXTRA_TEXT, "");

                    try {
                        startActivity(Intent.createChooser(emailIntent, "Send mail..."));

                    } catch (android.content.ActivityNotFoundException ex) {
                        Toast.makeText(getActivity(), "There is no email client installed.", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        ll_grpactionslist.setVisibility(View.GONE);
        tv_copygrp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isVISIBLEACTIONS = false;
                ll_actions.setVisibility(View.GONE);
                ll_grpactionslist.setVisibility(View.VISIBLE);
                lv_grpactionslist.setAdapter(new ContactListAdapter(getActivity(),arr_contacts));
               /* FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction ft=fragmentManager.beginTransaction();
                ft.replace(R.id.main_container, new AddGroupFragment());
                ft.commit();*/
            }
        });
        tv_rmvgrp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Constants.NAV_GROUPS=100;
                dbHandler.DeleteTable(dbHandler.TABLE_GROUP, "G_NAME='" + Constants.GROUP_NAME + "'");
                dbHandler.DeleteTable("TBL_PHONE_CONTACTS","Phone_Contact_Gid='"+Constants.GROUP_NAME+"'");
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction ft=fragmentManager.beginTransaction();
                ft.replace(R.id.main_container, new GroupsMainFragment());
                ft.commit();
                Toast.makeText(getActivity(),"Group deleted successfully",Toast.LENGTH_LONG).show();

            }
        });

        tv_addevt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction ft=fragmentManager.beginTransaction();
                ft.replace(R.id.main_container, new EditEventsFragment());
                ft.commit();
            }
        });


        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(isVISIBLEACTIONS) {
                    isVISIBLEACTIONS = false;
                    ll_actions.setVisibility(View.GONE);
                }
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
        btn_actions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isVISIBLEACTIONS=true;
             ll_actions.setVisibility(View.VISIBLE);
            }
        });


        HomeActivity.ib_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Constants.NAV_GROUPS=100;

                Constants.GROUP_OPERATIONS="EDIT";
                Constants.GROUP_OLD_NAME=Constants.GROUP_NAME;

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

                Constants.NAV_GROUPS=100;

                GroupsMainFragment groupContacts = new GroupsMainFragment(); //New means creating adding.
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.main_container, groupContacts);
                fragmentTransaction.addToBackStack("group_Main");
                fragmentTransaction.commit();

            }
        });

        Cursor c=dbHandler.retriveData("select * from "+DatabaseHandler.TABLE_PHONE_CONTACTS +" where Phone_Contact_Gid='"+ Constants.GROUP_NAME+"' order by Phone_Contact_Name ASC");
        if(c!=null)
        {
            if(c.getCount()>0)
            {
                c.moveToFirst();
                do {

                    ContactsDo contactsBean=new ContactsDo();
                    contactsBean.setConatactGroupName(c.getString(c.getColumnIndex(dbHandler.PHONE_CONTACT_GID)));
                    contactsBean.setConatactId(c.getString(c.getColumnIndex(dbHandler.PHONE_CONTACT_ID)));
                    contactsBean.setConatactName(c.getString(c.getColumnIndex(dbHandler.PHONE_CONTACT_NAME)));
                    contactsBean.setConatactPhone(c.getString(c.getColumnIndex(dbHandler.PHONE_CONTACT_NUMBER)));
                    contactsBean.setConatactPIC(c.getString(c.getColumnIndex(dbHandler.PHONE_CONTACT_PIC)));
                    contactsBean.setConatactEmail(c.getString(c.getColumnIndex(dbHandler.PHONE_CONTACT_EMAIL)));
                    contactsBean.setIS_CONTACT_SELECTED(0);
                    arr_contacts.add(contactsBean);

                }while(c.moveToNext());

            }
        }


        btn_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(isVISIBLEACTIONS){
                    isVISIBLEACTIONS=false;
                    ll_actions.setVisibility(View.GONE);
                }else {

                    Constants.NAV_GROUPS = 100;

                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.main_container, new GroupsMainFragment()).commit();
                }

            }
        });



        lv_conatcst.setAdapter(new ContactListAdapter(getActivity(),arr_contacts));



        return view;
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
}
