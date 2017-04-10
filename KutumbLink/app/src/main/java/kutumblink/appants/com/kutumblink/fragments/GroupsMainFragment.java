package kutumblink.appants.com.kutumblink.fragments;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import kutumblink.appants.com.kutumblink.HomeActivity;
import kutumblink.appants.com.kutumblink.R;
import kutumblink.appants.com.kutumblink.adapter.GroupListAdapter;
import kutumblink.appants.com.kutumblink.model.GroupDo;
import kutumblink.appants.com.kutumblink.utils.Constants;
import kutumblink.appants.com.kutumblink.utils.DatabaseHandler;

public class GroupsMainFragment extends BaseFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public GroupsMainFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static GroupsMainFragment newInstance(String param1, String param2) {
        GroupsMainFragment fragment = new GroupsMainFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
    RelativeLayout ll_addgroup;
    TextView tv_totalcontacts;
    ListView lv_GroupList;
    DatabaseHandler dbHandler;
   public static ArrayList<GroupDo> arr_group=new ArrayList<GroupDo>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        dbHandler=new DatabaseHandler(getActivity());
    }
    int[] flags = new int[]{
            R.drawable.add_group };
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_groups_main, container, false);


      if(Constants.NAV_GROUPS==101){
          FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
          FragmentTransaction ft=fragmentManager.beginTransaction();
          ft.replace(R.id.main_container, new AddGroupFragment());
          ft.commit();
      }else if(Constants.NAV_GROUPS==102){
          FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
          FragmentTransaction ft=fragmentManager.beginTransaction();
          ft.replace(R.id.main_container, new GroupContactsFragment());
          ft.commit();
      }else if(Constants.NAV_GROUPS==103){
          FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
          FragmentTransaction ft=fragmentManager.beginTransaction();
          ft.replace(R.id.main_container, new SelectGroupIconsFragment());
          ft.commit();
      }else if(Constants.NAV_GROUPS==104){
          FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
          FragmentTransaction ft=fragmentManager.beginTransaction();
          ft.replace(R.id.main_container, new SettingsFragment());
          ft.commit();
      }else if(Constants.NAV_GROUPS==105){
          FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
          FragmentTransaction ft=fragmentManager.beginTransaction();
          ft.replace(R.id.main_container, new FavuarateGroupIconsFragment());
          ft.commit();
      }

        HomeActivity.ib_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.main_container, new SettingsFragment()).commit();
            }
        });


        ll_addgroup=(RelativeLayout)view.findViewById(R.id.ll_addgroup);

        lv_GroupList=(ListView)view.findViewById(R.id.lv_grouplist);
        arr_group.clear();
        ll_addgroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Constants.GROUP_NAME="";
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
               FragmentTransaction ft= fragmentManager.beginTransaction();
                ft.replace(R.id.main_container, new AddGroupFragment());
                        ft.addToBackStack("group_Main");
                        ft.commit();
            }
        });
        HomeActivity.tv_title.setText("Groups");
        HomeActivity.ib_back.setBackgroundResource(R.mipmap.ic_launcher);
        HomeActivity.ib_back_next.setText("");
        HomeActivity.ib_menu.setBackgroundResource(R.mipmap.menu);
        HomeActivity.ib_menu.setText("");
        String mDrawableName = "group_default_icon";
        Constants.imgID = getResources().getIdentifier(mDrawableName , "mipmap", getActivity().getPackageName());


        Cursor c=dbHandler.retriveData("select * from "+DatabaseHandler.TABLE_GROUP);
        if(c!=null)
        {
            if(c.getCount()>0)
            {
                c.moveToFirst();
                do {

                    Cursor cg = dbHandler.retriveData("select * from " + DatabaseHandler.TABLE_PHONE_CONTACTS + " where Phone_Contact_Gid='" + c.getString(c.getColumnIndex(dbHandler.GROUP_NAME)) + "'");

                        GroupDo groupDetails=new GroupDo();
                    groupDetails.setGroup_ID(c.getString(c.getColumnIndex(dbHandler.GROUP_ID)));
                    groupDetails.setGroup_Name(c.getString(c.getColumnIndex(dbHandler.GROUP_NAME)));
                    groupDetails.setGroup_Pic(Integer.parseInt(c.getString(c.getColumnIndex(dbHandler.GROUP_PIC))));

                    if(cg.getCount()>0) {
                        groupDetails.setGroup_totalContactList("" + cg.getCount());
                    }else{
                        groupDetails.setGroup_totalContactList("0");// + cg.getCount());
                    }

                  //  groupDetails.setGroup_ID(c.getString(c.getColumnIndex(dbHandler.GROUP_ID)));
                    arr_group.add(groupDetails);

                }while(c.moveToNext());

            }
        }

        Log.v("GROUP SIZE...","SIZE OF GROUP..."+arr_group.size());
       lv_GroupList.setAdapter(new GroupListAdapter(getActivity(),arr_group));


        lv_GroupList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {


                Constants.GROUP_OPERATIONS="EDIT";
                Constants.GROUP_NAME=arr_group.get(i).getGroup_Name();


                Constants.imgID=arr_group.get(i).getGroup_Pic();
                GroupContactsFragment groupContacts = new GroupContactsFragment(); //New means creating adding.
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.main_container, groupContacts);
                fragmentTransaction.addToBackStack("group_Main");
                fragmentTransaction.commit();


            }
        });


        return view;

    }




}
