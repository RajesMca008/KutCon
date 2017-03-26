package kutumblink.appants.com.kutumblink.fragments;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import kutumblink.appants.com.kutumblink.R;
import kutumblink.appants.com.kutumblink.adapter.GroupListAdapter;
import kutumblink.appants.com.kutumblink.model.GroupDo;
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
    LinearLayout ll_addgroup;
    TextView tv_totalcontacts;
    ListView lv_GroupList;
    DatabaseHandler dbHandler;
    ArrayList<GroupDo> arr_group=new ArrayList<GroupDo>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        dbHandler=new DatabaseHandler(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_groups_main, container, false);
        ll_addgroup=(LinearLayout)view.findViewById(R.id.ll_addgroup);

        lv_GroupList=(ListView)view.findViewById(R.id.lv_grouplist);
        arr_group.clear();
        ll_addgroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.main_container, new AddGroupFragment()).commit();

            }
        });





        Cursor c=dbHandler.retriveData("select * from "+DatabaseHandler.TABLE_GROUP);
        if(c!=null)
        {
            if(c.getCount()>0)
            {
                c.moveToFirst();
                do {
                    GroupDo groupDetails=new GroupDo();
                    groupDetails.setGroup_ID(c.getString(c.getColumnIndex(dbHandler.GROUP_ID)));
                    groupDetails.setGroup_Name(c.getString(c.getColumnIndex(dbHandler.GROUP_NAME)));
                    groupDetails.setGroup_totalContactList(c.getString(c.getColumnIndex(dbHandler.GROUP_TOTALCONTACTS)));

                  //  groupDetails.setGroup_ID(c.getString(c.getColumnIndex(dbHandler.GROUP_ID)));
                    arr_group.add(groupDetails);

                }while(c.moveToNext());

            }
        }

        Log.v("GROUP SIZE...","SIZE OF GROUP..."+arr_group.size());
       lv_GroupList.setAdapter(new GroupListAdapter(getActivity(),arr_group));
        return view;

    }




}
