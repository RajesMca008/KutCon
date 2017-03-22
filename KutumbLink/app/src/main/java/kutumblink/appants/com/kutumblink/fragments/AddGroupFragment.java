package kutumblink.appants.com.kutumblink.fragments;


import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.onegravity.contactpicker.contact.Contact;
import com.onegravity.contactpicker.contact.ContactDescription;
import com.onegravity.contactpicker.contact.ContactSortOrder;
import com.onegravity.contactpicker.core.ContactPickerActivity;
import com.onegravity.contactpicker.picture.ContactPictureType;

import java.util.List;

import kutumblink.appants.com.kutumblink.R;
import kutumblink.appants.com.kutumblink.utils.Constants;
import kutumblink.appants.com.kutumblink.utils.DatabaseHandler;

/**
 * Created by Vishnu on 13-03-2016.
 */
/**
 * A simple {@link Fragment} subclass.
 */
public class AddGroupFragment extends BaseFragment {

    private static final int REQUEST_CONTACT = 0;

    public AddGroupFragment() {
        // Required empty public constructor
    }

    private static final int CONTACT_PICKER_RESULT = 1001;
    public static final int REQUEST_CODE_PICK_CONTACT = 1;
    public static final int MAX_PICK_CONTACT = 10;
    TextView tv_selectContact;
    EditText et_groupname;
    DatabaseHandler dbHandler;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_group, container, false);

        tv_selectContact = (TextView) view.findViewById(R.id.tv_selectContact);
        et_groupname = (EditText) view.findViewById(R.id.et_groupname);
        dbHandler = new DatabaseHandler(getActivity());
        tv_selectContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (et_groupname.length() != 0) {

                    Constants.G_NAME = et_groupname.getText().toString();
                    Intent intent = new Intent(getActivity(), ContactPickerActivity.class)
                            // .putExtra(ContactPickerActivity.EXTRA_THEME, mDarkTheme ? R.style.Theme_Dark : R.style.Theme_Light)
                            .putExtra(ContactPickerActivity.EXTRA_CONTACT_BADGE_TYPE, ContactPictureType.ROUND.name())
                            .putExtra(ContactPickerActivity.EXTRA_SHOW_CHECK_ALL, true)
                            .putExtra(ContactPickerActivity.EXTRA_CONTACT_DESCRIPTION, ContactDescription.ADDRESS.name())
                            .putExtra(ContactPickerActivity.EXTRA_CONTACT_DESCRIPTION_TYPE, ContactsContract.CommonDataKinds.Email.TYPE_WORK)
                            .putExtra(ContactPickerActivity.EXTRA_CONTACT_SORT_ORDER, ContactSortOrder.AUTOMATIC.name());
                    startActivityForResult(intent, REQUEST_CONTACT);
                } else {
                    Toast.makeText(getActivity(), "Please select provide the Group Name", Toast.LENGTH_SHORT).show();
                }

            }
        });

        return view;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CONTACT && resultCode == Activity.RESULT_OK &&
                data != null && data.hasExtra(ContactPickerActivity.RESULT_CONTACT_DATA)) {

            List<Contact> contacts = (List<Contact>) data.getSerializableExtra(ContactPickerActivity.RESULT_CONTACT_DATA);
            for (Contact contact : contacts) {
                ContentValues cv = new ContentValues();
                cv.put(dbHandler.PHONE_CONTACT_ID, "" + contact.getId());
                cv.put(dbHandler.PHONE_CONTACT_NAME, "" + contact.getDisplayName());
                cv.put(dbHandler.PHONE_CONTACT_NUMBER, "" + contact.getPhone(0));
                cv.put(dbHandler.PHONE_CONTACT_EMAIL, "" + contact.getEmail(0));
                cv.put(dbHandler.PHONE_CONTACT_GID, "" + Constants.G_NAME);
                cv.put(dbHandler.PHONE_CONTACT_PIC, "" + contact.getPhotoUri());

                dbHandler.insert(dbHandler.TABLE_PHONE_CONTACTS, cv);

                Cursor c = dbHandler.retriveData("select * from " + DatabaseHandler.TABLE_GROUP + " where G_NAME=" + Constants.G_NAME);
                if (c != null) {
                    if (c.getCount() == 0) {
                        ContentValues g_cv = new ContentValues();
                        g_cv.put(dbHandler.GROUP_NAME, Constants.G_NAME);
                        // g_cv.put(dbHandler.GROUP_PIC,""+contact.getPhone(0));
                        //  g_cv.put(dbHandler.GROUP_TOTALCONTACTS,""+contact.getEmail(0));

                        dbHandler.insert(dbHandler.TABLE_GROUP, cv);
                        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                        fragmentManager.beginTransaction().replace(R.id.main_container, new GroupsMainFragment()).commit();
                    }else{
                        Toast.makeText(getActivity(),"Groupname already Exists",Toast.LENGTH_SHORT).show();
                    }

                }

            }
        }


    }
}
