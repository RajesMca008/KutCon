package kutumblink.appants.com.kutumblink.fragments;


import android.Manifest;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.onegravity.contactpicker.contact.Contact;
import com.onegravity.contactpicker.contact.ContactDescription;
import com.onegravity.contactpicker.contact.ContactSortOrder;
import com.onegravity.contactpicker.core.ContactPickerActivity;
import com.onegravity.contactpicker.picture.ContactPictureType;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import kutumblink.appants.com.kutumblink.HomeActivity;
import kutumblink.appants.com.kutumblink.R;
import kutumblink.appants.com.kutumblink.model.ContactsDo;
import kutumblink.appants.com.kutumblink.utils.Constants;
import kutumblink.appants.com.kutumblink.utils.CustomTextView;
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

    ImageView iv_gicon,fav_gicon;
    TextView tv_createContact;
    int INSERT_CONTACT_REQUEST=2;
    private FragmentManager fragmentManager;
    Button btn_removegroup;
    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment




        View view = inflater.inflate(R.layout.fragment_add_group, container, false);

        tv_selectContact = (TextView) view.findViewById(R.id.tv_selectContact);
        et_groupname = (EditText) view.findViewById(R.id.et_groupname);
        iv_gicon=(ImageView)view.findViewById(R.id.iv_groupicon) ;
        tv_createContact=(TextView)view.findViewById(R.id.tv_createContact);
        fav_gicon=(ImageView)view.findViewById(R.id.iv_favgroups);
        dbHandler = new DatabaseHandler(getActivity());
        btn_removegroup=(Button)view.findViewById(R.id.btn_removegroup);
        Constants.NAV_GROUPS=101;
        HomeActivity.ib_back.setBackgroundResource(R.mipmap.left_arrow);

        HomeActivity.ib_back_next.setText("Groups");
        HomeActivity.ib_menu.setBackgroundColor(Color.TRANSPARENT);
        HomeActivity.ib_menu.setText("Save");

        if(Constants.GROUP_OPERATIONS.equalsIgnoreCase("EDIT")){
            btn_removegroup.setVisibility(View.VISIBLE);

        }else   if(Constants.GROUP_OPERATIONS.equalsIgnoreCase("SAVE")){
            btn_removegroup.setVisibility(View.INVISIBLE);
        }

        btn_removegroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Constants.NAV_GROUPS=100;
                dbHandler.DeleteTable(dbHandler.TABLE_GROUP, "G_NAME='" + Constants.GROUP_NAME + "'");
                dbHandler.DeleteTable("TBL_PHONE_CONTACTS","Phone_Contact_Gid='"+Constants.GROUP_NAME+"'");
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction ft=fragmentManager.beginTransaction();
                ft.replace(R.id.main_container, new GroupsMainFragment());
                ft.commit();
                showConfirmDialog(getString(R.string.app_name),"Group deleted successfully");
            }
        });

        HomeActivity.ib_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(et_groupname.getText().toString().length()!=0){

                  //  if(gEXISTS) {
                    Constants.NAV_GROUPS=100;


                    if(Constants.GROUP_OPERATIONS.equalsIgnoreCase("EDIT")){


                        ContentValues g_cv = new ContentValues();
                        g_cv.put(dbHandler.GROUP_NAME, Constants.GROUP_NAME);
                        g_cv.put(dbHandler.GROUP_TOTALCONTACTS, "0");
                        g_cv.put(dbHandler.GROUP_PIC, "" + Constants.imgID);
                        Cursor c = dbHandler.retriveData("select * from " + DatabaseHandler.TABLE_GROUP + " where G_NAME='" + Constants.GROUP_OLD_NAME + "'");

                        if (c == null || c.getCount() == 0) {
                            dbHandler.insert(dbHandler.TABLE_GROUP, g_cv);
                        } else {

                            dbHandler.UpdateTable(dbHandler.TABLE_GROUP, g_cv, " G_NAME='" + Constants.GROUP_OLD_NAME + "'");
                        }



                        ContentValues cv = new ContentValues();

                        cv.put(dbHandler.PHONE_CONTACT_GID, "" + Constants.GROUP_NAME);

                        Cursor cphone = dbHandler.retriveData("select * from " + DatabaseHandler.TABLE_PHONE_CONTACTS + " where Phone_Contact_Gid='" + Constants.GROUP_OLD_NAME + "'");

                        if (cphone == null || cphone.getCount() == 0) {
                            dbHandler.insert(dbHandler.TABLE_PHONE_CONTACTS, cv);
                        } else {

                            dbHandler.UpdateTable(dbHandler.TABLE_PHONE_CONTACTS, cv, " Phone_Contact_Gid='" + Constants.GROUP_OLD_NAME + "'");
                        }





                    }else   if(Constants.GROUP_OPERATIONS.equalsIgnoreCase("SAVE")){

                    ContentValues g_cv = new ContentValues();
                    g_cv.put(dbHandler.GROUP_NAME, Constants.GROUP_NAME);
                    g_cv.put(dbHandler.GROUP_TOTALCONTACTS, "0");
                    g_cv.put(dbHandler.GROUP_PIC, "" + Constants.imgID);
                    Cursor c = dbHandler.retriveData("select * from " + DatabaseHandler.TABLE_GROUP + " where G_NAME='" + Constants.GROUP_NAME + "'");

                    if (c == null || c.getCount() == 0) {
                        dbHandler.insert(dbHandler.TABLE_GROUP, g_cv);
                    } else {

                        dbHandler.UpdateTable(dbHandler.TABLE_GROUP, g_cv, " G_NAME='" + Constants.GROUP_NAME + "'");
                    }


                        ContentValues cv = new ContentValues();

                        cv.put(dbHandler.PHONE_CONTACT_GID, "" + Constants.GROUP_NAME);

                        Cursor cphone = dbHandler.retriveData("select * from " + DatabaseHandler.TABLE_PHONE_CONTACTS + " where Phone_Contact_Gid='" + Constants.GROUP_OLD_NAME + "'");

                        if (cphone == null || cphone.getCount() == 0) {
                            dbHandler.insert(dbHandler.TABLE_PHONE_CONTACTS, cv);
                        } else {

                            dbHandler.UpdateTable(dbHandler.TABLE_PHONE_CONTACTS, cv, " Phone_Contact_Gid='" + Constants.GROUP_OLD_NAME + "'");
                        }

                    }
                        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                        fragmentManager.beginTransaction().replace(R.id.main_container, new GroupsMainFragment()).commit();

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
                Constants.NAV_GROUPS=100;
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
                Constants.NAV_GROUPS=100;
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
                    if ((ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED)) {

                        requestPermissions(new String[]{Manifest.permission.READ_CONTACTS},
                                MY_PERMISSIONS_REQUEST_READ_CONTACTS);
                        Constants.GROUP_NAME = et_groupname.getText().toString();
                        Intent intent = new Intent(getActivity(), ContactPickerActivity.class)
                              //   .putExtra(ContactPickerActivity.EXTRA_THEME, mDarkTheme ? R.style.Theme_AppCompat : R.style.TextAppearance_AppCompat_Caption)
                                .putExtra(ContactPickerActivity.EXTRA_CONTACT_BADGE_TYPE, ContactPictureType.ROUND.name())
                                .putExtra(ContactPickerActivity.EXTRA_SHOW_CHECK_ALL, true)
                                .putExtra(ContactPickerActivity.EXTRA_CONTACT_DESCRIPTION, ContactDescription.ADDRESS.name())
                                .putExtra(ContactPickerActivity.EXTRA_CONTACT_DESCRIPTION_TYPE, ContactsContract.CommonDataKinds.Email.TYPE_WORK)
                                .putExtra(ContactPickerActivity.EXTRA_CONTACT_SORT_ORDER, ContactSortOrder.AUTOMATIC.name());

                        startActivityForResult(intent, REQUEST_CONTACT);
                    }else{
                        requestPermissions(new String[]{Manifest.permission.READ_CONTACTS},
                                MY_PERMISSIONS_REQUEST_READ_CONTACTS);
                        Integer selectedList[]=new Integer[GroupsMainFragment.arr_group.size()];
                        Collection<Long> selectContats=new ArrayList<Long>();
                     int i=0;


                        //Rajesh code
                        Cursor c=dbHandler.retriveData("select * from "+DatabaseHandler.TABLE_PHONE_CONTACTS +" where Phone_Contact_Gid='"+ Constants.GROUP_NAME+"'");
                        if(c!=null)
                        {
                            if(c.getCount()>0)
                            {
                                c.moveToFirst();
                                do {

                                    ContactsDo contactsBean=new ContactsDo();
                                    contactsBean.setConatactId(c.getString(c.getColumnIndex(dbHandler.PHONE_CONTACT_ID)));
                                //    arr_contacts.add(contactsBean);
                                 //   selectedList[i]=Integer.parseInt(c.getString(c.getColumnIndex(dbHandler.PHONE_CONTACT_ID)));

                                    Log.v("SELECTED CONTACTS...","CONTACTS.."+c.getString(c.getColumnIndex(dbHandler.PHONE_CONTACT_ID)));

                                    selectContats.add(Long.parseLong(c.getString(c.getColumnIndex(dbHandler.PHONE_CONTACT_ID))));

                               i++;
                                }while(c.moveToNext());

                            }
                        }



                        Constants.GROUP_NAME = et_groupname.getText().toString();

                        if(Constants.GROUP_OPERATIONS.equalsIgnoreCase("EDIT")) {
                            Intent intent = new Intent(getActivity(), ContactPickerActivity.class)
                                    // .putExtra(ContactPickerActivity.EXTRA_THEME, mDarkTheme ? R.style.Theme_Dark : R.style.Theme_Light)
                                    .putExtra(ContactPickerActivity.EXTRA_CONTACT_BADGE_TYPE, ContactPictureType.ROUND.name())
                                    .putExtra(ContactPickerActivity.EXTRA_SHOW_CHECK_ALL, true)
                                    //  .putExtra(ContactPickerActivity.EXTRA_PRESELECTED_CONTACTS,  GroupsMainFragment.arr_group)
                                    .putExtra(ContactPickerActivity.EXTRA_CONTACT_DESCRIPTION, ContactDescription.ADDRESS.name())
                                    .putExtra(ContactPickerActivity.EXTRA_CONTACT_DESCRIPTION_TYPE, ContactsContract.CommonDataKinds.Email.TYPE_WORK)
                                    .putExtra(ContactPickerActivity.EXTRA_ONLY_CONTACTS_WITH_PHONE, true)

                                    .putExtra(ContactPickerActivity.EXTRA_PRESELECTED_CONTACTS, (Serializable) selectContats)

                                    .putExtra(ContactPickerActivity.EXTRA_CONTACT_SORT_ORDER, ContactSortOrder.AUTOMATIC.name());
                            //.putExtra(ContactPickerActivity.SELECTED_CONTACTS, GroupContactsFragment.arr_contacts);

                            startActivityForResult(intent, REQUEST_CONTACT);

                        }else{
                            Intent intent = new Intent(getActivity(), ContactPickerActivity.class)
                                    // .putExtra(ContactPickerActivity.EXTRA_THEME, mDarkTheme ? R.style.Theme_Dark : R.style.Theme_Light)
                                    .putExtra(ContactPickerActivity.EXTRA_CONTACT_BADGE_TYPE, ContactPictureType.ROUND.name())
                                    .putExtra(ContactPickerActivity.EXTRA_SHOW_CHECK_ALL, true)
                                    //  .putExtra(ContactPickerActivity.EXTRA_PRESELECTED_CONTACTS,  GroupsMainFragment.arr_group)
                                    .putExtra(ContactPickerActivity.EXTRA_CONTACT_DESCRIPTION, ContactDescription.ADDRESS.name())
                                    .putExtra(ContactPickerActivity.EXTRA_CONTACT_DESCRIPTION_TYPE, ContactsContract.CommonDataKinds.Email.TYPE_WORK)
                                    .putExtra(ContactPickerActivity.EXTRA_ONLY_CONTACTS_WITH_PHONE, true)

                                  //  .putExtra(ContactPickerActivity.EXTRA_PRESELECTED_CONTACTS, (Serializable) selectContats)

                                    .putExtra(ContactPickerActivity.EXTRA_CONTACT_SORT_ORDER, ContactSortOrder.AUTOMATIC.name());
                            //.putExtra(ContactPickerActivity.SELECTED_CONTACTS, GroupContactsFragment.arr_contacts);

                            startActivityForResult(intent, REQUEST_CONTACT);
                        }
                    }
             /*   } else {
                    Toast.makeText(getActivity(), "Please enter the Group Name", Toast.LENGTH_SHORT).show();
                }*/

            }
        });

        CustomTextView sortOderText= (CustomTextView) view.findViewById(R.id.sort_order_text);

        SharedPreferences sharedPreferences=getActivity().getSharedPreferences(Constants.PRF_FILE_NAME, Context.MODE_PRIVATE);

        String sortOder=sharedPreferences.getString("SORT_ORDER",Constants.DEFAULT);


        sortOderText.setText("Sort Order- "+sortOder);

        sortOderText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Sort Oder
                SortOderDetailsFragment sortOrderFragment = new SortOderDetailsFragment(); //New means creating adding.
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.main_container, sortOrderFragment);
                fragmentTransaction.addToBackStack("sort_order");
                fragmentTransaction.commit();
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

            if(Constants.GROUP_OPERATIONS.equalsIgnoreCase("EDIT")){
                dbHandler.DeleteTable(DatabaseHandler.TABLE_PHONE_CONTACTS,"Phone_Contact_Gid='"+Constants.GROUP_OLD_NAME+"'");

                ContentValues g_cv = new ContentValues();
                g_cv.put(dbHandler.GROUP_NAME, Constants.GROUP_NAME);
                g_cv.put(dbHandler.GROUP_TOTALCONTACTS, "0");
                g_cv.put(dbHandler.GROUP_PIC, "" + Constants.imgID);
                Cursor c = dbHandler.retriveData("select * from " + DatabaseHandler.TABLE_GROUP + " where G_NAME='" + Constants.GROUP_OLD_NAME + "'");

                if (c == null || c.getCount() == 0) {
                    dbHandler.insert(dbHandler.TABLE_GROUP, g_cv);
                } else {

                    dbHandler.UpdateTable(dbHandler.TABLE_GROUP, g_cv, " G_NAME='" + Constants.GROUP_OLD_NAME + "'");
                }

            }else if(Constants.GROUP_OPERATIONS.equalsIgnoreCase("SAVE")){
                dbHandler.DeleteTable(DatabaseHandler.TABLE_PHONE_CONTACTS,"Phone_Contact_Gid='"+Constants.GROUP_NAME+"'");
                ContentValues g_cv = new ContentValues();
                g_cv.put(dbHandler.GROUP_NAME, Constants.GROUP_NAME);
                g_cv.put(dbHandler.GROUP_TOTALCONTACTS, "0");
                g_cv.put(dbHandler.GROUP_PIC, "" + Constants.imgID);
                    dbHandler.insert(dbHandler.TABLE_GROUP, g_cv);

            }



                for (Contact contact : contacts) {
                    ContentValues cv = new ContentValues();
                 //   Cursor conatacts = dbHandler.retriveData("select * from " + DatabaseHandler.TABLE_PHONE_CONTACTS + " where Phone_Contact_ID='" + contact.getId()+"'");
                    cv.put(dbHandler.PHONE_CONTACT_ID, "" + contact.getId());
                    cv.put(dbHandler.PHONE_CONTACT_NAME, "" + contact.getDisplayName());
                    cv.put(dbHandler.PHONE_CONTACT_FNAME, "" + contact.getFirstName());
                    cv.put(dbHandler.PHONE_CONTACT_LNAME, "" + contact.getLastName());
                    cv.put(dbHandler.PHONE_CONTACT_NUMBER, "" + contact.getPhone(0));
                    cv.put(dbHandler.PHONE_CONTACT_EMAIL, "" + contact.getEmail(0));
                    cv.put(dbHandler.PHONE_CONTACT_GID, "" + Constants.GROUP_NAME);
                    cv.put(dbHandler.PHONE_CONTACT_PIC, "" + contact.getPhotoUri());

                        dbHandler.insert(dbHandler.TABLE_PHONE_CONTACTS, cv);
            }

            if (Constants.GROUP_OPERATIONS.equalsIgnoreCase("SAVE")) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.main_container, new GroupsMainFragment()).commit();
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

