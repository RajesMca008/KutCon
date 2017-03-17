package kutumblink.appants.com.kutumblink.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import kutumblink.appants.com.kutumblink.R;
import kutumblink.appants.com.kutumblink.fragments.BaseFragment;

/**
 * Created by Vishnu on 13-03-2016.
 */
/**
 * A simple {@link Fragment} subclass.
 */
public class AddGroupFragment extends BaseFragment {


    public AddGroupFragment() {
        // Required empty public constructor
    }
    private static final int CONTACT_PICKER_RESULT = 1001;
    public static final int REQUEST_CODE_PICK_CONTACT = 1;
    public static final int  MAX_PICK_CONTACT= 10;
    TextView tv_selectContact;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_add_group, container, false);

        tv_selectContact=(TextView)view.findViewById(R.id.tv_selectContact);
        tv_selectContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent phonebookIntent = new Intent("intent.action.INTERACTION_TOPMENU");
                phonebookIntent.putExtra("additional", "phone-multi");
                phonebookIntent.putExtra("maxRecipientCount", MAX_PICK_CONTACT);
                phonebookIntent.putExtra("FromMMS", true);
                startActivityForResult(phonebookIntent, REQUEST_CODE_PICK_CONTACT);
            }
        });

        return view;
    }

}
