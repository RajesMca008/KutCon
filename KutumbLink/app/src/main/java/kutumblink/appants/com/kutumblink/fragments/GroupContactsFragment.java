package kutumblink.appants.com.kutumblink.fragments;


import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

import kutumblink.appants.com.kutumblink.R;
import kutumblink.appants.com.kutumblink.adapter.ContactListAdapter;
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
    EditText et_action;
    Button btn_close;
    ListView lv_conatcst;
    DatabaseHandler dbHandler;


    ArrayList<ContactsDo> arr_contacts=new ArrayList<ContactsDo>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_groupcontacts, container, false);

        dbHandler=new DatabaseHandler(getActivity());
        lv_conatcst=(ListView)view.findViewById(R.id.lv_contacts);
        btn_close=(Button)view.findViewById(R.id.btn_close);

        Cursor c=dbHandler.retriveData("select * from "+DatabaseHandler.TABLE_PHONE_CONTACTS +" where Phone_Contact_Gid='"+ Constants.GROUP_NAME+"'");
        if(c!=null)
        {
            if(c.getCount()>0)
            {
                c.moveToFirst();
                do {

                    ContactsDo contactsBean=new ContactsDo();
                    contactsBean.setConatactGroupName(c.getString(c.getColumnIndex(dbHandler.PHONE_CONTACT_NAME)));
                    contactsBean.setConatactId(c.getString(c.getColumnIndex(dbHandler.PHONE_CONTACT_ID)));
                    contactsBean.setConatactName(c.getString(c.getColumnIndex(dbHandler.PHONE_CONTACT_NAME)));
                    contactsBean.setConatactPhone(c.getString(c.getColumnIndex(dbHandler.PHONE_CONTACT_NUMBER)));
                    contactsBean.setConatactPIC(c.getString(c.getColumnIndex(dbHandler.PHONE_CONTACT_PIC)));
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
        lv_conatcst.setAdapter(new ContactListAdapter(getActivity(),arr_contacts));


        return view;
    }

}
