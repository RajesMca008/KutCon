package kutumblink.appants.com.kutumblink.fragments;


import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
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
import android.view.inputmethod.InputMethodManager;
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
    private CustomTextView sortOderTextView = null;

    public AddGroupFragment() {
        // Required empty public constructor
    }

    ImageView iv_gicon, fav_gicon;
    TextView tv_selectContact;
    EditText et_groupname;
    DatabaseHandler dbHandler;
    final int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 102;


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        boolean canUseExternalStorage = false;
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_CONTACTS: {
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    canUseExternalStorage = true;
                }
                if (!canUseExternalStorage){
                    Toast.makeText(getActivity(), "Cannot use this feature without requested permission", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }


    TextView tv_createContact;
    int INSERT_CONTACT_REQUEST = 2;
    //private FragmentManager fragmentManager;
    Button btn_removegroup;

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_add_group, container, false);

        tv_selectContact = (TextView) view.findViewById(R.id.tv_selectContact);
        et_groupname = (EditText) view.findViewById(R.id.et_groupname);
        iv_gicon = (ImageView) view.findViewById(R.id.iv_groupicon);
        tv_createContact = (TextView) view.findViewById(R.id.tv_createContact);
        fav_gicon = (ImageView) view.findViewById(R.id.iv_favgroups);
        dbHandler = new DatabaseHandler(getActivity());
        btn_removegroup = (Button) view.findViewById(R.id.btn_removegroup);
        Constants.NAV_GROUPS = 101;
        HomeActivity.ib_back.setBackgroundResource(R.drawable.left_arrow);

        HomeActivity.ib_back_next.setText("Groups");
        HomeActivity.tv_title.setText("Add Group");
        if(!Constants.GROUP_NAME.equals(""))
        {
            HomeActivity.tv_title.setText(Constants.GROUP_NAME);

        }
        HomeActivity.ib_menu.setBackgroundColor(Color.TRANSPARENT);
        HomeActivity.ib_menu.setText("Save");


        if (Constants.imgID.length() < 15) {
            iv_gicon.setImageResource(Integer.parseInt(Constants.imgID));
        } else {
            iv_gicon.setImageBitmap(Constants.stringToBitMap(Constants.imgID));
        }

        if (Constants.GROUP_OPERATIONS.equalsIgnoreCase("EDIT")) {
            btn_removegroup.setVisibility(View.VISIBLE);

        } else if (Constants.GROUP_OPERATIONS.equalsIgnoreCase("SAVE")) {
            btn_removegroup.setVisibility(View.INVISIBLE);
        }
        btn_removegroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showConfirmOptionsDialog("Delete Group", "Are you sure?", Constants.GROUP_DELETE, Constants.GROUP_NAME);
            }
        });

        HomeActivity.ib_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                View keyView = mActivity.getCurrentFocus();
                if (keyView != null) {
                    InputMethodManager imm = (InputMethodManager)mActivity.getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(keyView.getWindowToken(), 0);
                }

                 if (!Constants.GROUP_NAME.equalsIgnoreCase("")) {
                    Constants.NAV_GROUPS = 100;
                    if (Constants.GROUP_OPERATIONS.equalsIgnoreCase("EDIT")) {
                        boolean _GROUP_EXISTSEDT = false;
                        Cursor cg = dbHandler.retriveData("select * from " + DatabaseHandler.TABLE_GROUP + " where G_NAME='" + Constants.GROUP_OLD_NAME + "'");
                        if (cg != null) {
                            if (cg.getCount() > 0) {
                                _GROUP_EXISTSEDT = true;
                            } else {
                                _GROUP_EXISTSEDT = false;
                            }
                            if (_GROUP_EXISTSEDT) {
                                ContentValues g_cv = new ContentValues();
                                g_cv.put(dbHandler.GROUP_NAME, Constants.GROUP_NAME);
                                 g_cv.put(dbHandler.GROUP_TOTALCONTACTS, tempContactObjList.size());
                                g_cv.put(dbHandler.GROUP_PIC, "" + Constants.imgID);
                                g_cv.put(dbHandler.GROUP_SORT_ORDER, "" + Constants.SortOrderValue);
                               long res= dbHandler.UpdateTable(dbHandler.TABLE_GROUP, g_cv, " G_NAME='" + Constants.GROUP_OLD_NAME + "'");

                               if(res==-1)
                               {
                                   showConfirmDialog("",getString(R.string.gname_exists),false);
                               }

                                ContentValues cv1 = new ContentValues();
                                cv1.put(dbHandler.PHONE_CONTACT_GID, "" + Constants.GROUP_NAME);
                                dbHandler.UpdateTable(dbHandler.TABLE_PHONE_CONTACTS, cv1, " Phone_Contact_Gid='" + Constants.GROUP_OLD_NAME + "'");
                                //dbHandler.DeleteTable(DatabaseHandler.TABLE_PHONE_CONTACTS, "Phone_Contact_Gid='" + Constants.GROUP_OLD_NAME + "'");

                                for(int i=0;i<tempContactObjList.size();i++){
                                    ContentValues cv = new ContentValues();
                                    cv.put(DatabaseHandler.PHONE_CONTACT_ID, "" + tempContactObjList.get(i).getId());
                                    cv.put(DatabaseHandler.PHONE_CONTACT_NAME, "" + tempContactObjList.get(i).getDisplayName());
                                    cv.put(DatabaseHandler.PHONE_CONTACT_FNAME, "" + tempContactObjList.get(i).getFirstName());
                                    cv.put(DatabaseHandler.PHONE_CONTACT_LNAME, "" + tempContactObjList.get(i).getLastName());
                                    cv.put(DatabaseHandler.PHONE_CONTACT_NUMBER, "" + tempContactObjList.get(i).getPhone());
                                    cv.put(DatabaseHandler.PHONE_CONTACT_EMAIL, "" + tempContactObjList.get(i).getEmail());
                                    cv.put(DatabaseHandler.PHONE_CONTACT_PIC, "" + tempContactObjList.get(i).getPhotUri());
                                    // cv.put(DatabaseHandler.PHONE_CONTACT_GID neeed to send group name
                                    cv.put(DatabaseHandler.PHONE_CONTACT_GID, "" + Constants.GROUP_NAME);

                                    dbHandler.insert(DatabaseHandler.TABLE_PHONE_CONTACTS, cv);

                                }

                                tempContactObjList.clear();
                            }
                        }
                        cg.close();
                        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                        fragmentManager.beginTransaction().replace(R.id.main_container, new GroupsMainFragment()).commit();


                    } else if (Constants.GROUP_OPERATIONS.equalsIgnoreCase("SAVE")) {

                        Cursor cgsavesameName = dbHandler.retriveData("select * from " + DatabaseHandler.TABLE_GROUP + " where G_NAME='" + Constants.GROUP_NAME.trim() + "'");

                        if(cgsavesameName.getCount()>0){
                            showConfirmDialog("","Group name already exists",false);

                        }else {


                            //New code added by Raj
                            ContentValues g_cv = new ContentValues();


                            g_cv.put(DatabaseHandler.GROUP_NAME, "" + Constants.GROUP_NAME);
                            g_cv.put(DatabaseHandler.GROUP_TOTALCONTACTS, "0");
                            g_cv.put(DatabaseHandler.GROUP_PIC, "" + Constants.imgID);


                            dbHandler.insert(DatabaseHandler.TABLE_GROUP, g_cv);


                            for(int i=0;i<tempContactObjList.size();i++){
                                ContentValues cv = new ContentValues();
                                cv.put(DatabaseHandler.PHONE_CONTACT_ID, "" + tempContactObjList.get(i).getId());
                                cv.put(DatabaseHandler.PHONE_CONTACT_NAME, "" + tempContactObjList.get(i).getDisplayName());
                                cv.put(DatabaseHandler.PHONE_CONTACT_FNAME, "" + tempContactObjList.get(i).getFirstName());
                                cv.put(DatabaseHandler.PHONE_CONTACT_LNAME, "" + tempContactObjList.get(i).getLastName());
                                cv.put(DatabaseHandler.PHONE_CONTACT_NUMBER, "" + tempContactObjList.get(i).getPhone());
                                cv.put(DatabaseHandler.PHONE_CONTACT_EMAIL, "" + tempContactObjList.get(i).getEmail());
                                cv.put(DatabaseHandler.PHONE_CONTACT_PIC, "" + tempContactObjList.get(i).getPhotUri());
                                // cv.put(DatabaseHandler.PHONE_CONTACT_GID neeed to send group name
                                cv.put(DatabaseHandler.PHONE_CONTACT_GID, "" + Constants.GROUP_NAME);


                                dbHandler.insert(DatabaseHandler.TABLE_PHONE_CONTACTS, cv);
                            }

                            tempContactObjList.clear();

                            FragmentManager fragmentManager = mActivity.getSupportFragmentManager();
                            fragmentManager.beginTransaction().replace(R.id.main_container, new GroupsMainFragment()).commit();

                           /* boolean _GROUP_EXISTSAVE = false;

                            Cursor cgsave = dbHandler.retriveData("select * from " + DatabaseHandler.TABLE_GROUP + " where G_NAME='" + "0" + "'");
                            if (cgsave != null) {
                                if (cgsave.getCount() > 0) {

                                    _GROUP_EXISTSAVE = true;

                                } else {
                                    _GROUP_EXISTSAVE = false;
                                }


                                if (_GROUP_EXISTSAVE) {


                                    ContentValues g_cv = new ContentValues();
                                    g_cv.put(dbHandler.GROUP_NAME, Constants.GROUP_NAME);
                                    g_cv.put(dbHandler.GROUP_TOTALCONTACTS, "0");
                                    g_cv.put(dbHandler.GROUP_PIC, "" + Constants.imgID);
                                    g_cv.put(dbHandler.GROUP_SORT_ORDER, "" + Constants.SortOrderValue);
                                    dbHandler.UpdateTable(dbHandler.TABLE_GROUP, g_cv, " G_NAME='" + "0" + "'");

                                    ContentValues cv = new ContentValues();
                                    cv.put(dbHandler.PHONE_CONTACT_GID, "" + Constants.GROUP_NAME);
                                    dbHandler.UpdateTable(dbHandler.TABLE_PHONE_CONTACTS, cv, " Phone_Contact_Gid='" + "0" + "'");
                                }

                                else {

                                    ContentValues g_cv = new ContentValues();
                                    g_cv.put(dbHandler.GROUP_NAME, Constants.GROUP_NAME);
                                    g_cv.put(dbHandler.GROUP_TOTALCONTACTS, "0");
                                    g_cv.put(dbHandler.GROUP_PIC, "" + Constants.imgID);
                                    g_cv.put(dbHandler.GROUP_SORT_ORDER, "" + Constants.SortOrderValue);
                                    dbHandler.insert(dbHandler.TABLE_GROUP, g_cv);


                                }
                                FragmentManager fragmentManager = mActivity.getSupportFragmentManager();
                                fragmentManager.beginTransaction().replace(R.id.main_container, new GroupsMainFragment()).commit();





                            } else {


                                if (Constants.GROUP_NAMEP.equalsIgnoreCase("vdgfhhTT")) {
                                    ContentValues g_cv = new ContentValues();
                                    g_cv.put(dbHandler.GROUP_NAME, Constants.GROUP_NAME);
                                    g_cv.put(dbHandler.GROUP_TOTALCONTACTS, "0");
                                    g_cv.put(dbHandler.GROUP_PIC, "" + Constants.imgID);
                                    g_cv.put(dbHandler.GROUP_SORT_ORDER, "" + Constants.SortOrderValue);
                                    Cursor c = dbHandler.retriveData("select * from " + DatabaseHandler.TABLE_GROUP + " where G_NAME='" + "0" + "'");

                                    if (c == null || c.getCount() == 0) {
                                        dbHandler.insert(dbHandler.TABLE_GROUP, g_cv);
                                    } else {

                                        dbHandler.UpdateTable(dbHandler.TABLE_GROUP, g_cv, " G_NAME='" + "0" + "'");

                                    }

                                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                                    fragmentManager.beginTransaction().replace(R.id.main_container, new GroupsMainFragment()).commit();
                                } else if (Constants.GROUP_NAMECI.equalsIgnoreCase("CINSERT")) {


                                    ContentValues g_cv = new ContentValues();
                                    g_cv.put(dbHandler.GROUP_NAME, Constants.GROUP_NAME);
                                    g_cv.put(dbHandler.GROUP_TOTALCONTACTS, "0");
                                    g_cv.put(dbHandler.GROUP_PIC, "" + Constants.imgID);
                                    g_cv.put(dbHandler.GROUP_SORT_ORDER, "" + Constants.SortOrderValue);


                                    dbHandler.UpdateTable(dbHandler.TABLE_GROUP, g_cv, " G_NAME='" + "0" + "'");

                                    ContentValues cv = new ContentValues();


                                    cv.put(dbHandler.PHONE_CONTACT_GID, "" + Constants.GROUP_NAME);


                                    dbHandler.UpdateTable(dbHandler.TABLE_PHONE_CONTACTS, cv, " Phone_Contact_Gid='" + "0" + "'");


                                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                                    fragmentManager.beginTransaction().replace(R.id.main_container, new GroupsMainFragment()).commit();
                                }
                            }*/
                        }

                    }
                } else {
                     showConfirmDialog("","Please provide Group name.",false);
                }
            }
        });











       /* HomeActivity.ib_back_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!Constants.GROUP_NAME.equalsIgnoreCase("")) {

                    //  if(gEXISTS) {
                    Constants.NAV_GROUPS = 100;


                    if (Constants.GROUP_OPERATIONS.equalsIgnoreCase("EDIT")) {


                        ContentValues g_cv = new ContentValues();
                        g_cv.put(dbHandler.GROUP_NAME, Constants.GROUP_NAME);
                        g_cv.put(dbHandler.GROUP_TOTALCONTACTS, "0");
                        g_cv.put(dbHandler.GROUP_PIC, "" + Constants.imgID);
                        g_cv.put(dbHandler.GROUP_SORT_ORDER, "" + Constants.SortOrderValue);
                        dbHandler.UpdateTable(dbHandler.TABLE_GROUP, g_cv, " G_NAME='" + Constants.GROUP_OLD_NAME + "'");

                        ContentValues cv = new ContentValues();
                        cv.put(dbHandler.PHONE_CONTACT_GID, "" + Constants.GROUP_NAME);
                        dbHandler.UpdateTable(dbHandler.TABLE_PHONE_CONTACTS, cv, " Phone_Contact_Gid='" + Constants.GROUP_OLD_NAME + "'");

                    } else if (Constants.GROUP_OPERATIONS.equalsIgnoreCase("SAVE")) {


                        if (Constants.GROUP_NAME.equalsIgnoreCase("vdgfhhTT")) {
                            ContentValues g_cv = new ContentValues();
                            g_cv.put(dbHandler.GROUP_NAME, Constants.GROUP_NAME);
                            g_cv.put(dbHandler.GROUP_TOTALCONTACTS, "0");
                            g_cv.put(dbHandler.GROUP_PIC, "" + Constants.imgID);
                            g_cv.put(dbHandler.GROUP_SORT_ORDER, "" + Constants.SortOrderValue);
                            Cursor c = dbHandler.retriveData("select * from " + DatabaseHandler.TABLE_GROUP + " where G_NAME='" + "" + "'");

                            if (c == null || c.getCount() == 0) {
                                dbHandler.UpdateTable(dbHandler.TABLE_GROUP, g_cv, " G_NAME='" + Constants.GROUP_NAME + "'");
                            } else {

                                dbHandler.UpdateTable(dbHandler.TABLE_GROUP, g_cv, " G_NAME='" + "" + "'");

                            }


                            ContentValues cv = new ContentValues();

                            cv.put(dbHandler.PHONE_CONTACT_GID, "" + Constants.GROUP_NAME);

                            Cursor cphone = dbHandler.retriveData("select * from " + DatabaseHandler.TABLE_PHONE_CONTACTS + " where Phone_Contact_Gid='" + "" + "'");

                            if (cphone == null || cphone.getCount() == 0) {

                                dbHandler.UpdateTable(dbHandler.TABLE_PHONE_CONTACTS, cv, " Phone_Contact_Gid='" + Constants.GROUP_NAME + "'");
                            } else {


                                dbHandler.UpdateTable(dbHandler.TABLE_PHONE_CONTACTS, cv, " Phone_Contact_Gid='" + "" + "'");

                            }
                        } else {


                            ContentValues g_cv = new ContentValues();
                            g_cv.put(dbHandler.GROUP_NAME, Constants.GROUP_NAME);
                            g_cv.put(dbHandler.GROUP_TOTALCONTACTS, "0");
                            g_cv.put(dbHandler.GROUP_PIC, "" + Constants.imgID);
                            g_cv.put(dbHandler.GROUP_SORT_ORDER, "" + Constants.SortOrderValue);
                            Cursor c = dbHandler.retriveData("select * from " + DatabaseHandler.TABLE_GROUP + " where G_NAME='" + Constants.GROUP_NAME + "'");

                            if (c == null || c.getCount() == 0) {
                                dbHandler.insert(dbHandler.TABLE_GROUP, g_cv);
                            } else {

                                dbHandler.UpdateTable(dbHandler.TABLE_GROUP, g_cv, " G_NAME='" + Constants.GROUP_NAME + "'");

                            }


                            ContentValues cv = new ContentValues();

                            cv.put(dbHandler.PHONE_CONTACT_GID, "" + Constants.GROUP_NAME);

                            Cursor cphone = dbHandler.retriveData("select * from " + DatabaseHandler.TABLE_PHONE_CONTACTS + " where Phone_Contact_Gid='" + Constants.GROUP_NAME + "'");

                            if (cphone == null || cphone.getCount() == 0) {
                                dbHandler.insert(dbHandler.TABLE_PHONE_CONTACTS, cv);
                            } else {


                                dbHandler.UpdateTable(dbHandler.TABLE_PHONE_CONTACTS, cv, " Phone_Contact_Gid='" + Constants.GROUP_NAME + "'");

                            }

                        }
                    }
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.main_container, new GroupsMainFragment()).commit();

                } else {

                }
            }
        });*/


        et_groupname.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {

                if (s.length() != 0) {

                    Constants.GROUP_NAME = et_groupname.getText().toString().trim();
                    HomeActivity.ib_menu.setTextColor(Color.parseColor("#000000"));
                } else {

                    Constants.GROUP_NAMEP = "vdgfhhTT12";
                    HomeActivity.ib_menu.setTextColor(Color.parseColor("#838281"));
                }
            }
        });

        HomeActivity.ib_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Constants.NAV_GROUPS = 100;

               /* FragmentManager fragmentManager =mActivity.getSupportFragmentManager();
                fragmentManager.popBackStack();
                FragmentTransaction ft = fragmentManager.beginTransaction();
                ft.replace(R.id.main_container, new GroupsMainFragment());
                ft.addToBackStack("group_Main");
                ft.commit();*/
                FragmentManager fragmentManager = activity.getSupportFragmentManager();
                fragmentManager.popBackStack();
            }
        });

        HomeActivity.ib_back_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Constants.NAV_GROUPS = 100;
                if(Constants.GROUP_NAME.equals("") ||Constants.GROUP_NAME.equals("0") || Constants.GROUP_OLD_NAME.equals(""))
                {
                    try {
                        dbHandler.DeleteTable(DatabaseHandler.TABLE_PHONE_CONTACTS, "Phone_Contact_Gid=0" );
                        dbHandler.DeleteTable(DatabaseHandler.TABLE_GROUP, "G_NAME=0");
                    }catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                }

                FragmentManager fragmentManager = mActivity.getSupportFragmentManager();
                fragmentManager.popBackStack();
                FragmentTransaction ft = fragmentManager.beginTransaction();
                ft.replace(R.id.main_container, new GroupsMainFragment());
                ft.addToBackStack("group_Main");
                ft.commit();


            }
        });


        if (Constants.imgID.length() < 15) {
            iv_gicon.setImageResource(Integer.parseInt(Constants.imgID));
            et_groupname.setText(Constants.GROUP_NAME);
        } else {
            iv_gicon.setImageBitmap(Constants.stringToBitMap(Constants.imgID));
            et_groupname.setText(Constants.GROUP_NAME);
        }

        tv_createContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent i = new Intent(Intent.ACTION_INSERT, ContactsContract.Contacts.CONTENT_URI);
                if (Integer.valueOf(Build.VERSION.SDK) > 14)
                    i.putExtra("finishActivityOnSaveCompleted", true); // Fix for 4.0.3 +
                startActivityForResult(i, INSERT_CONTACT_REQUEST);

            }
        });

        fav_gicon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = mActivity.getSupportFragmentManager();
                FragmentTransaction ft = fragmentManager.beginTransaction();
                ft.replace(R.id.main_container, new FavuarateGroupIconsFragment());
                ft.addToBackStack("group_Main");
                ft.commit();
            }
        });
        iv_gicon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = mActivity.getSupportFragmentManager();
                FragmentTransaction ft = fragmentManager.beginTransaction();
                ft.replace(R.id.main_container, new SelectGroupIconsFragment());
                ft.addToBackStack("group_Main");
                ft.commit();
            }
        });
        tv_selectContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(Constants.GROUP_OPERATIONS.equalsIgnoreCase("SAVE")) {
                    Cursor cgsavesameName = dbHandler.retriveData("select * from " + DatabaseHandler.TABLE_GROUP + " where G_NAME='" + Constants.GROUP_NAME + "'");

                    if (cgsavesameName.getCount() > 0) {
                        showConfirmDialog("", "Group name already exists",true);
                        return;
                    }
                }

                if ((ContextCompat.checkSelfPermission(mActivity, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED)) {

                   /* requestPermissions(new String[]{Manifest.permission.READ_CONTACTS},
                            MY_PERMISSIONS_REQUEST_READ_CONTACTS);*/
                    Constants.GROUP_NAME = et_groupname.getText().toString().trim();
                    Intent intent = new Intent(mActivity, ContactPickerActivity.class)
                            //   .putExtra(ContactPickerActivity.EXTRA_THEME, mDarkTheme ? R.style.Theme_AppCompat : R.style.TextAppearance_AppCompat_Caption)
                            .putExtra(ContactPickerActivity.EXTRA_CONTACT_BADGE_TYPE, ContactPictureType.ROUND.name())
                            .putExtra(ContactPickerActivity.EXTRA_SHOW_CHECK_ALL, true)
                            .putExtra(ContactPickerActivity.EXTRA_CONTACT_DESCRIPTION, ContactDescription.ADDRESS.name())
                            .putExtra(ContactPickerActivity.EXTRA_CONTACT_DESCRIPTION_TYPE, ContactsContract.CommonDataKinds.Email.TYPE_WORK)
                            .putExtra(ContactPickerActivity.EXTRA_CONTACT_SORT_ORDER, ContactSortOrder.AUTOMATIC.name());

                    startActivityForResult(intent, REQUEST_CONTACT);
                } else {
                    /*requestPermissions(new String[]{Manifest.permission.READ_CONTACTS},
                            MY_PERMISSIONS_REQUEST_READ_CONTACTS);*/
                    Integer selectedList[] = new Integer[GroupsMainFragment.arr_group.size()];
                    Collection<Long> selectContats = new ArrayList<Long>();
                    int i = 0;


                    //Rajesh code
                    Cursor c = dbHandler.retriveData("select * from " + DatabaseHandler.TABLE_PHONE_CONTACTS + " where Phone_Contact_Gid='" + Constants.GROUP_NAME + "'");
                    if (c != null) {
                        if (c.getCount() > 0) {
                            c.moveToFirst();
                            do {

                                if (c.getString(c.getColumnIndex(dbHandler.PHONE_CONTACT_ID)) != null) {
                                    ContactsDo contactsBean = new ContactsDo();
                                    contactsBean.setConatactId(c.getString(c.getColumnIndex(dbHandler.PHONE_CONTACT_ID)));

                                    selectContats.add(Long.parseLong(c.getString(c.getColumnIndex(dbHandler.PHONE_CONTACT_ID))));

                                }
                                i++;
                            } while (c.moveToNext());

                        }
                    }
                    c.close();


                    Constants.GROUP_NAME = et_groupname.getText().toString().trim();

                    Intent intent = new Intent(mActivity, ContactPickerActivity.class)
                            // .putExtra(ContactPickerActivity.EXTRA_THEME, mDarkTheme ? R.style.Theme_Dark : R.style.Theme_Light)
                            .putExtra(ContactPickerActivity.EXTRA_CONTACT_BADGE_TYPE, ContactPictureType.ROUND.name())
                            .putExtra(ContactPickerActivity.EXTRA_SHOW_CHECK_ALL, true)
                            //  .putExtra(ContactPickerActivity.EXTRA_PRESELECTED_CONTACTS,  GroupsMainFragment.arr_group)
                            .putExtra(ContactPickerActivity.EXTRA_CONTACT_DESCRIPTION, ContactDescription.ADDRESS.name())
                            .putExtra(ContactPickerActivity.EXTRA_CONTACT_DESCRIPTION_TYPE, ContactsContract.CommonDataKinds.Email.TYPE_WORK)
                            .putExtra(ContactPickerActivity.EXTRA_ONLY_CONTACTS_WITH_PHONE, false)

                            .putExtra(ContactPickerActivity.EXTRA_PRESELECTED_CONTACTS, (Serializable) selectContats)

                            .putExtra(ContactPickerActivity.EXTRA_CONTACT_SORT_ORDER, ContactSortOrder.AUTOMATIC.name());
                    startActivityForResult(intent, REQUEST_CONTACT);


                }


            }
        });

        sortOderTextView = (CustomTextView) view.findViewById(R.id.sort_order_text);

        sortOderTextView.setText("Sort Order  -  " + Constants.SortOrderValue);

        sortOderTextView.setOnClickListener(new View.OnClickListener() {
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



    List<TempContactObj> tempContactObjList=new ArrayList<TempContactObj>();
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CONTACT && resultCode == Activity.RESULT_OK &&
                data != null && data.hasExtra(ContactPickerActivity.RESULT_CONTACT_DATA)) {
            List<Contact> contacts;
            contacts = (List<Contact>) data.getSerializableExtra(ContactPickerActivity.RESULT_CONTACT_DATA);
            Constants.RESULT = 1;
            if (Constants.GROUP_OPERATIONS.equalsIgnoreCase("EDIT")) {
                dbHandler.DeleteTable(DatabaseHandler.TABLE_PHONE_CONTACTS, "Phone_Contact_Gid='" + Constants.GROUP_OLD_NAME + "'");
               // dbHandler.DeleteTable(DatabaseHandler.TABLE_GROUP, "G_NAME='" + Constants.GROUP_OLD_NAME + "'");
            } else {

               // dbHandler.DeleteTable(DatabaseHandler.TABLE_PHONE_CONTACTS, "Phone_Contact_Gid='" + Constants.GROUP_NAME + "'");
               // dbHandler.DeleteTable(DatabaseHandler.TABLE_GROUP, "G_NAME='" + Constants.GROUP_NAME + "'");
            }

            Log.v("GROUP NAME...", "GROUPNAME...." + Constants.GROUP_NAME);


            //Removing previous added
            tempContactObjList.clear();

          /*  ContentValues g_cv = new ContentValues();
            if (Constants.GROUP_NAME.equalsIgnoreCase("")) {
                g_cv.put(DatabaseHandler.GROUP_NAME, "0");
                Constants.GROUP_NAMECI = "CINSERT";
            } else {
                if(Constants.GROUP_OPERATIONS.equalsIgnoreCase("SAVE"))
                g_cv.put(DatabaseHandler.GROUP_NAME, "0" *//*+ Constants.GROUP_NAME*//*);
                else
                    g_cv.put(DatabaseHandler.GROUP_NAME, "" + Constants.GROUP_NAME);
            }
            g_cv.put(DatabaseHandler.GROUP_TOTALCONTACTS, "0");
            g_cv.put(DatabaseHandler.GROUP_PIC, "" + Constants.imgID);


            dbHandler.insert(DatabaseHandler.TABLE_GROUP, g_cv);*/

            TempContactObj tempContactObj=null;
            for (Contact contact : contacts) {
                tempContactObj=new TempContactObj();


                tempContactObj.setId(contact.getId());
                tempContactObj.setDisplayName(contact.getDisplayName());
                tempContactObj.setFirstName(contact.getFirstName());
                tempContactObj.setLastName(contact.getLastName());
                tempContactObj.setPhone(contact.getPhone(0));
                tempContactObj.setEmail(contact.getEmail(0));
                tempContactObj.setPhotUri(contact.getPhotoUri());


                tempContactObjList.add(tempContactObj);

               /* ContentValues cv = new ContentValues();
                cv.put(DatabaseHandler.PHONE_CONTACT_ID, "" + contact.getId());
                cv.put(DatabaseHandler.PHONE_CONTACT_NAME, "" + contact.getDisplayName());
                cv.put(DatabaseHandler.PHONE_CONTACT_FNAME, "" + contact.getFirstName());
                cv.put(DatabaseHandler.PHONE_CONTACT_LNAME, "" + contact.getLastName());
                cv.put(DatabaseHandler.PHONE_CONTACT_NUMBER, "" + contact.getPhone(0));
                cv.put(DatabaseHandler.PHONE_CONTACT_EMAIL, "" + contact.getEmail(0));
                cv.put(DatabaseHandler.PHONE_CONTACT_PIC, "" + contact.getPhotoUri());
                // cv.put(DatabaseHandler.PHONE_CONTACT_GID neeed to send group name

                if (Constants.GROUP_NAME.equalsIgnoreCase("")) {
                    cv.put(DatabaseHandler.PHONE_CONTACT_GID, "0");
                    Constants.GROUP_NAMECI = "CINSERT";
                } else {
                    if(Constants.GROUP_OPERATIONS.equalsIgnoreCase("EDIT"))
                    cv.put(DatabaseHandler.PHONE_CONTACT_GID, "" + Constants.GROUP_NAME);
                    else
                        cv.put(DatabaseHandler.PHONE_CONTACT_GID, "0"*//* + Constants.GROUP_NAME*//*);
                }


                dbHandler.insert(DatabaseHandler.TABLE_PHONE_CONTACTS, cv);*/
            }

           /* if (Constants.GROUP_OPERATIONS.equalsIgnoreCase("SAVE")) {
                FragmentManager fragmentManager = mActivity.getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.main_container, new GroupsMainFragment()).commit();
            }*/

        } else if (requestCode == INSERT_CONTACT_REQUEST) {

            Cursor c = null;
            Cursor emailCur = null;

            if (data != null) {
                try {
                    Uri contactData = data.getData();
                    c = mActivity.managedQuery(contactData, null, null, null, null);
                    ContentResolver cr = mActivity.getContentResolver();
                    String cNumber = "";
                    String id = "";
                    String emailContact = "";
                    String name = "";
                    if (c.moveToFirst()) {
                        id = c.getString(c.getColumnIndexOrThrow(ContactsContract.Contacts._ID));

                        String hasPhone = c.getString(c.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));

                        if (hasPhone.equalsIgnoreCase("1")) {
                            Cursor phones = mActivity.getContentResolver().query(
                                    ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                                    ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + id,
                                    null, null);
                            phones.moveToFirst();
                            cNumber = phones.getString(phones.getColumnIndex("data1"));
                            System.out.println("number is:" + cNumber);
                        }
                        if (c.getString(c.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME)) != null) {
                            name = c.getString(c.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                        }

                        System.out.println("number is:" + name);


                    }
                    Log.v("DATA...", "Cursor;;;...." + c);

                    emailCur = cr.query(ContactsContract.CommonDataKinds.Email.CONTENT_URI, null, ContactsContract.CommonDataKinds.Email.CONTACT_ID + " = ?", new String[]{id}, null);
                    while (emailCur.moveToNext()) {
                        emailContact = emailCur.getString(emailCur.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA));
                    }

                  /*  Log.v("DATA NAME...", "NAME CONTACTS.NAME.." + name);
                    Log.v("DATA NAME...", "NAME CONTACTS.NUMBER.." + cNumber);
                    Log.v("DATA NAME...", "NAME CONTACTS.EMAIL.." + emailContact);*/



                  /*  ContentValues g_cv = new ContentValues();
                    if (Constants.GROUP_NAME.equalsIgnoreCase("")) {
                        Constants.GROUP_NAMECI = "CINSERT";
                        g_cv.put(DatabaseHandler.GROUP_NAME, "0");
                    } else {
                        g_cv.put(DatabaseHandler.GROUP_NAME, "" + Constants.GROUP_NAME);
                    }
                    g_cv.put(DatabaseHandler.GROUP_TOTALCONTACTS, "0");
                    g_cv.put(DatabaseHandler.GROUP_PIC, "" + Constants.imgID);


                    Cursor cg = dbHandler.retriveData("select * from " + DatabaseHandler.TABLE_GROUP);


                    boolean cINS = false;
                    if (cg != null) {
                        cg.moveToFirst();
                        if (cg.getCount() > 0) {
                            do {


                                if (cg.getString(cg.getColumnIndex(DatabaseHandler.GROUP_NAME)).equalsIgnoreCase(Constants.GROUP_NAME)) {

                                    cINS = true;
                                    //    dbHandler.UpdateTable(dbHandler.TABLE_GROUP, g_cv, " G_NAME='" + Constants.GROUP_NAME + "'");
                                }

                                // Log.v("GROUP NAMESSS...","GROUPS..."+cg.getString(cg.getColumnIndex(dbHandler.GROUP_NAME)));
                            } while (cg.moveToNext());

                        }
                    }


                    if (!cINS) {
                        dbHandler.insert(DatabaseHandler.TABLE_GROUP, g_cv);
                    }*/


                    //Holding values in temp object
                    TempContactObj tempContactObj=null;
                    tempContactObj=new TempContactObj();

                    tempContactObj.setEmail(emailContact);
                    tempContactObj.setFirstName(name);
                    tempContactObj.setDisplayName(name);
                    tempContactObj.setPhone(cNumber);
                    tempContactObj.setId(Long.parseLong(id));

                    tempContactObjList.add(tempContactObj);



                  /*  ContentValues cv = new ContentValues();
                    cv.put(DatabaseHandler.PHONE_CONTACT_ID, "" + id);
                    cv.put(DatabaseHandler.PHONE_CONTACT_NAME, "" + name);
                    cv.put(DatabaseHandler.PHONE_CONTACT_NUMBER, "" + cNumber);
                    cv.put(DatabaseHandler.PHONE_CONTACT_EMAIL, "" + emailContact);

                    if (Constants.GROUP_NAME.equalsIgnoreCase("")) {
                        Constants.GROUP_NAMECI = "CINSERT";
                        cv.put(DatabaseHandler.PHONE_CONTACT_GID, "0");
                    } else {
                        cv.put(DatabaseHandler.PHONE_CONTACT_GID, "" + Constants.GROUP_NAME);
                    }

                    dbHandler.insert(DatabaseHandler.TABLE_PHONE_CONTACTS, cv);*/

                } finally {
                    if (c != null) {
                        c.close();
                        emailCur.close();
                    }
                }
            } else {
                Log.v("DATA...", "Error;;;....");
            }

        }
    }

}

