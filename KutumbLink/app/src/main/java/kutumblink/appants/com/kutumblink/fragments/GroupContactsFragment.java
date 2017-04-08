package kutumblink.appants.com.kutumblink.fragments;


import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import kutumblink.appants.com.kutumblink.HomeActivity;
import kutumblink.appants.com.kutumblink.R;
import kutumblink.appants.com.kutumblink.adapter.ContactListAdapter;
import kutumblink.appants.com.kutumblink.adapter.ExpandableListAdapter;
import kutumblink.appants.com.kutumblink.model.ContactsDo;
import kutumblink.appants.com.kutumblink.utils.Constants;
import kutumblink.appants.com.kutumblink.utils.DatabaseHandler;


/**
 * A simple {@link Fragment} subclass.
 */
public class GroupContactsFragment extends BaseFragment {


    public GroupContactsFragment() {
        // Required empty public constructor
    }
    Button btn_close;
    ListView lv_conatcst;
    DatabaseHandler dbHandler;

    public static ExpandableListView et_action;
   public static ArrayList<ContactsDo> arr_contacts=new ArrayList<ContactsDo>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_groupcontacts, container, false);

        arr_contacts.clear();
        dbHandler=new DatabaseHandler(getActivity());
        lv_conatcst=(ListView)view.findViewById(R.id.lv_contacts);
        btn_close=(Button)view.findViewById(R.id.btn_close);
        expListView=(ExpandableListView)view.findViewById(R.id.lvExp);


        activity.setTitle(Constants.GROUP_NAME);


        HomeActivity.ib_back.setBackgroundColor(Color.TRANSPARENT);

        HomeActivity.ib_back_next.setText("Done");
        HomeActivity.ib_menu.setBackgroundColor(Color.TRANSPARENT);
        HomeActivity.ib_menu.setText("Edit");
        HomeActivity.tv_title.setText(Constants.GROUP_NAME);

        HomeActivity.ib_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

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
                    contactsBean.setIS_CONTACT_SELECTED(0);
                    arr_contacts.add(contactsBean);

                }while(c.moveToNext());

            }
        }


        btn_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.main_container, new GroupsMainFragment()).commit();



            }
        });


        expListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView expandableListView, View view, int i, int i1, long l) {


                Toast.makeText(getActivity(),".."+i1,Toast.LENGTH_LONG).show();
                String phoneNos="";
                if(i1==0){
                    // message
                    for(int a=0;a<arr_contacts.size();a++){

                        if(arr_contacts.get(a).getIS_CONTACT_SELECTED()==1) {

                            phoneNos+=arr_contacts.get(a).getConatactPhone()+";";
                        }

                    }
                    Intent intent = new Intent(android.content.Intent.ACTION_VIEW);
                    intent.putExtra("address", phoneNos);

                    intent.putExtra("sms_body", "");
                    intent.setType("vnd.android-dir/mms-sms");
                    startActivity(intent);
                }else if(i1==1){
// Email

                }else if(i1==2){

                }else if(i1==3){

                }else if(i1==4){

                }else if(i1==5){

                }
                return false;
            }
        });
       /* et_action.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for(int i=0;i<arr_contacts.size();i++){

                    if(arr_contacts.get(i).getIS_CONTACT_SELECTED()==1) {
                        Log.v("Contacts Selected....", "...Contacts.." + arr_contacts.get(i).getConatactName());
                        Log.v("Contacts Selected....", "...Contacts.." + arr_contacts.get(i).getConatactGroupName());
                    }

                }
            }
        });*/


        // preparing list data
        prepareListData();

        listAdapter = new ExpandableListAdapter(getActivity(), listDataHeader, listDataChild);

        // setting list adapter
        expListView.setAdapter(listAdapter);
        lv_conatcst.setAdapter(new ContactListAdapter(getActivity(),arr_contacts));

        lv_conatcst.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {
                Log.v("Contacts Selected....", "...list ITEM CLICKED.." + arr_contacts.get(pos).getConatactName());
                for(int i=0;i<arr_contacts.size();i++){

                    if(arr_contacts.get(i).getIS_CONTACT_SELECTED()==1) {
                        Log.v("Contacts Selected....", "...Contacts.." + arr_contacts.get(i).getConatactName());
                        Log.v("Contacts Selected....", "...Contacts.." + arr_contacts.get(i).getConatactGroupName());
                    }

                }
            }
        });

        return view;
    }

    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;


    private void prepareListData() {
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();

        // Adding child data
        listDataHeader.add("");

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

}
