package kutumblink.appants.com.kutumblink.fragments;


import android.Manifest;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.onegravity.contactpicker.contact.Contact;
import com.onegravity.contactpicker.contact.ContactDescription;
import com.onegravity.contactpicker.contact.ContactSortOrder;
import com.onegravity.contactpicker.core.ContactPickerActivity;
import com.onegravity.contactpicker.picture.ContactPictureType;

import java.util.List;

import kutumblink.appants.com.kutumblink.HomeActivity;
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

    TextView tv_selectContact;
    EditText et_groupname;
    DatabaseHandler dbHandler;
    final int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 102;

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {

        boolean canUseExternalStorage = false;

        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_CONTACTS: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    canUseExternalStorage = true;
                }

                if (!canUseExternalStorage) {
                    Toast.makeText(getActivity(), "Cannot use this feature without requested permission", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    ImageView iv_gicon,iv_groupicon,fav_gicon;
    TextView tv_createContact;
    int INSERT_CONTACT_REQUEST=2;
    private FragmentManager fragmentManager;
    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment




        View view = inflater.inflate(R.layout.fragment_add_group, container, false);

        tv_selectContact = (TextView) view.findViewById(R.id.tv_selectContact);
        et_groupname = (EditText) view.findViewById(R.id.et_groupname);
        iv_gicon=(ImageView)view.findViewById(R.id.iv_groupicon) ;
        iv_groupicon=(ImageView)view.findViewById(R.id.iv_groupicon);
        tv_createContact=(TextView)view.findViewById(R.id.tv_createContact);
        fav_gicon=(ImageView)view.findViewById(R.id.iv_favgroups);
        dbHandler = new DatabaseHandler(getActivity());

        HomeActivity.ib_back.setBackgroundResource(R.mipmap.left_arrow);

        HomeActivity.ib_back_next.setText("Groups");
        HomeActivity.ib_menu.setBackgroundColor(Color.TRANSPARENT);
        HomeActivity.ib_menu.setText("Save");

        HomeActivity.ib_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(et_groupname.getText().toString().length()!=0){

                    try {
                        if (contacts != null || contacts.size() != 0) {
                            for (Contact contact : contacts) {
                                ContentValues cv = new ContentValues();

                                cv.put(dbHandler.PHONE_CONTACT_ID, "" + contact.getId());
                                cv.put(dbHandler.PHONE_CONTACT_NAME, "" + contact.getDisplayName());
                                cv.put(dbHandler.PHONE_CONTACT_NUMBER, "" + contact.getPhone(0));
                                cv.put(dbHandler.PHONE_CONTACT_EMAIL, "" + contact.getEmail(0));
                                cv.put(dbHandler.PHONE_CONTACT_GID, "" + Constants.GROUP_NAME);
                                cv.put(dbHandler.PHONE_CONTACT_PIC, "" + contact.getPhotoUri());

                                dbHandler.insert(dbHandler.TABLE_PHONE_CONTACTS, cv);


                            }
                            ContentValues g_cv = new ContentValues();
                            g_cv.put(dbHandler.GROUP_NAME, Constants.GROUP_NAME);
                            g_cv.put(dbHandler.GROUP_TOTALCONTACTS,""+contacts.size());
                            g_cv.put(dbHandler.GROUP_PIC,""+Constants.imgID);
                            Cursor c = dbHandler.retriveData("select * from " + DatabaseHandler.TABLE_GROUP + " where G_NAME='" + Constants.GROUP_NAME+"'");

                            if (c==null || c.getCount() == 0) {
                                dbHandler.insert(dbHandler.TABLE_GROUP, g_cv);
                                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                                fragmentManager.beginTransaction().replace(R.id.main_container, new GroupsMainFragment()).commit();
                            }else{
                                Toast.makeText(getActivity(),"Groupname already Exists",Toast.LENGTH_SHORT).show();
                            }
                        }
                    }catch(Exception e){
                        ContentValues g_cv = new ContentValues();
                        g_cv.put(dbHandler.GROUP_NAME, Constants.GROUP_NAME);
                        g_cv.put(dbHandler.GROUP_TOTALCONTACTS,"0");
                        g_cv.put(dbHandler.GROUP_PIC,""+Constants.imgID);
                    /*    Cursor c = dbHandler.retriveData("select * from " + DatabaseHandler.TABLE_GROUP + " where G_NAME='" + Constants.GROUP_NAME+"'");

                        if (c==null || c.getCount() == 0) {*/
                            dbHandler.insert(dbHandler.TABLE_GROUP, g_cv);
                            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                            fragmentManager.beginTransaction().replace(R.id.main_container, new GroupsMainFragment()).commit();
                       /* }else{
                            Toast.makeText(getActivity(),"Groupname already Exists",Toast.LENGTH_SHORT).show();
                        }*/
                    }


                 /*   ContentValues g_cv = new ContentValues();
                    g_cv.put(dbHandler.GROUP_NAME, Constants.GROUP_NAME);
                    g_cv.put(dbHandler.GROUP_TOTALCONTACTS,""+contacts.size());
                    g_cv.put(dbHandler.GROUP_PIC,""+Constants.imgID);
                    Cursor c = dbHandler.retriveData("select * from " + DatabaseHandler.TABLE_GROUP + " where G_NAME='" + Constants.GROUP_NAME+"'");

                    if (c==null || c.getCount() == 0) {
                        dbHandler.insert(dbHandler.TABLE_GROUP, g_cv);
                        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                        fragmentManager.beginTransaction().replace(R.id.main_container, new GroupsMainFragment()).commit();
                    }else{
                        Toast.makeText(getActivity(),"Groupname already Exists",Toast.LENGTH_SHORT).show();
                    }*/
                }else{

                }
            }
        });

        et_groupname.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {}

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {

                if(s.length()!=0){

                    Constants.GROUP_NAME=et_groupname.getText().toString();
                    HomeActivity.ib_menu.setTextColor(Color.parseColor("#000000"));
                }else{
                    HomeActivity.ib_menu.setTextColor(Color.parseColor("#838281"));
                }
            }
        });

        HomeActivity.ib_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction ft=fragmentManager.beginTransaction();
                ft.replace(R.id.main_container, new GroupsMainFragment());
                ft.addToBackStack("group_Main");
                ft.commit();
            }
        });

        HomeActivity.ib_back_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction ft=fragmentManager.beginTransaction();
                ft.replace(R.id.main_container, new GroupsMainFragment());
                ft.addToBackStack("group_Main");
                ft.commit();
            }
        });
        HomeActivity.tv_title.setText("Add Group");

        if(Constants.imgID!=0){
            iv_gicon.setImageResource(Constants.imgID);
            et_groupname.setText(Constants.GROUP_NAME);
        }

        tv_createContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(Intent.ACTION_INSERT, ContactsContract.Contacts.CONTENT_URI);
                startActivityForResult(i, INSERT_CONTACT_REQUEST);
            }
        });

        fav_gicon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction ft=fragmentManager.beginTransaction();
                ft.replace(R.id.main_container, new FavuarateGroupIconsFragment());
                ft.addToBackStack("group_Main");
                        ft.commit();
            }
        });
        iv_gicon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction ft=fragmentManager.beginTransaction();
                ft.replace(R.id.main_container, new SelectGroupIconsFragment());
                ft.addToBackStack("group_Main");
                ft.commit();
            }
        });
        tv_selectContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



              //  if (et_groupname.length() != 0) {
                    if ((ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED)) {

                        requestPermissions(new String[]{Manifest.permission.READ_CONTACTS},
                                MY_PERMISSIONS_REQUEST_READ_CONTACTS);


                        Constants.GROUP_NAME = et_groupname.getText().toString();
                        Intent intent = new Intent(getActivity(), ContactPickerActivity.class)
                              //   .putExtra(ContactPickerActivity.EXTRA_THEME, mDarkTheme ? R.style.Theme_AppCompat : R.style.TextAppearance_AppCompat_Caption)
                                .putExtra(ContactPickerActivity.EXTRA_CONTACT_BADGE_TYPE, ContactPictureType.ROUND.name())
                                .putExtra(ContactPickerActivity.EXTRA_SHOW_CHECK_ALL, true)
                                .putExtra(ContactPickerActivity.EXTRA_PRESELECTED_CONTACTS, true)
                                .putExtra(ContactPickerActivity.EXTRA_CONTACT_DESCRIPTION, ContactDescription.ADDRESS.name())
                                .putExtra(ContactPickerActivity.EXTRA_CONTACT_DESCRIPTION_TYPE, ContactsContract.CommonDataKinds.Email.TYPE_WORK)
                                .putExtra(ContactPickerActivity.EXTRA_CONTACT_SORT_ORDER, ContactSortOrder.AUTOMATIC.name());
                        startActivityForResult(intent, REQUEST_CONTACT);
                    }else{
                        requestPermissions(new String[]{Manifest.permission.READ_CONTACTS},
                                MY_PERMISSIONS_REQUEST_READ_CONTACTS);

                        Constants.GROUP_NAME = et_groupname.getText().toString();
                        Intent intent = new Intent(getActivity(), ContactPickerActivity.class)
                                // .putExtra(ContactPickerActivity.EXTRA_THEME, mDarkTheme ? R.style.Theme_Dark : R.style.Theme_Light)
                                .putExtra(ContactPickerActivity.EXTRA_CONTACT_BADGE_TYPE, ContactPictureType.ROUND.name())
                                .putExtra(ContactPickerActivity.EXTRA_SHOW_CHECK_ALL, true)
                                .putExtra(ContactPickerActivity.EXTRA_CONTACT_DESCRIPTION, ContactDescription.ADDRESS.name())
                                .putExtra(ContactPickerActivity.EXTRA_CONTACT_DESCRIPTION_TYPE, ContactsContract.CommonDataKinds.Email.TYPE_WORK)
                                .putExtra(ContactPickerActivity.EXTRA_CONTACT_SORT_ORDER, ContactSortOrder.AUTOMATIC.name());
                        startActivityForResult(intent, REQUEST_CONTACT);
                    }
             /*   } else {
                    Toast.makeText(getActivity(), "Please enter the Group Name", Toast.LENGTH_SHORT).show();
                }*/

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
            if(Constants.GROUP_NAME.length()!=0){
                for (Contact contact : contacts) {
                    ContentValues cv = new ContentValues();


                    Cursor c = dbHandler.retriveData("select * from " + DatabaseHandler.TABLE_PHONE_CONTACTS + " where Phone_Contact_ID='" + contact.getId()+"'");

                    if (!c.getString(c.getColumnIndex("Phone_Contact_ID")).equalsIgnoreCase(""+contact.getId())) {

                        cv.put(dbHandler.PHONE_CONTACT_ID, "" + contact.getId());
                        cv.put(dbHandler.PHONE_CONTACT_NAME, "" + contact.getDisplayName());
                        cv.put(dbHandler.PHONE_CONTACT_NUMBER, "" + contact.getPhone(0));
                        cv.put(dbHandler.PHONE_CONTACT_EMAIL, "" + contact.getEmail(0));
                        cv.put(dbHandler.PHONE_CONTACT_GID, "" + Constants.GROUP_NAME);
                        cv.put(dbHandler.PHONE_CONTACT_PIC, "" + contact.getPhotoUri());

                        dbHandler.insert(dbHandler.TABLE_PHONE_CONTACTS, cv);

                    }else{
                        cv.put(dbHandler.PHONE_CONTACT_ID, "" + contact.getId());
                        cv.put(dbHandler.PHONE_CONTACT_NAME, "" + contact.getDisplayName());
                        cv.put(dbHandler.PHONE_CONTACT_NUMBER, "" + contact.getPhone(0));
                        cv.put(dbHandler.PHONE_CONTACT_EMAIL, "" + contact.getEmail(0));
                        cv.put(dbHandler.PHONE_CONTACT_GID, "" + Constants.GROUP_NAME);
                        cv.put(dbHandler.PHONE_CONTACT_PIC, "" + contact.getPhotoUri());

                        dbHandler.UpdateTable(dbHandler.TABLE_PHONE_CONTACTS, cv,"Phone_Contact_ID='"+contact.getId()+"'");
                    }


                }
                ContentValues g_cv = new ContentValues();
                g_cv.put(dbHandler.GROUP_NAME, Constants.GROUP_NAME);
                g_cv.put(dbHandler.GROUP_TOTALCONTACTS,""+contacts.size());
                g_cv.put(dbHandler.GROUP_PIC,""+Constants.imgID);
               Cursor c = dbHandler.retriveData("select * from " + DatabaseHandler.TABLE_GROUP + " where G_NAME='" + Constants.GROUP_NAME+"'");

               if (c==null || c.getCount() == 0) {
                    dbHandler.insert(dbHandler.TABLE_GROUP, g_cv);
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.main_container, new GroupsMainFragment()).commit();
                }else{

                   dbHandler.UpdateTable(dbHandler.TABLE_GROUP, g_cv,"G_NAME='" + Constants.GROUP_NAME+"'");
                  // dbHandler.UpdateTable();
                   FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                   fragmentManager.beginTransaction().replace(R.id.main_container, new GroupsMainFragment()).commit();
                    //Toast.makeText(getActivity(),"Groupname already Exists",Toast.LENGTH_SHORT).show();
                }
            }
        }else if(requestCode == INSERT_CONTACT_REQUEST){
            if (resultCode == Activity.RESULT_OK)
            {


            }else if(resultCode == Activity.RESULT_CANCELED)
            {
            //    Toast.makeText(getActivity(),"Contact Added Succesfully",Toast.LENGTH_SHORT).show();
            }

        }
        }


    }

