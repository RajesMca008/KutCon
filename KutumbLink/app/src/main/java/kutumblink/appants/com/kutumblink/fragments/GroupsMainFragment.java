package kutumblink.appants.com.kutumblink.fragments;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

import kutumblink.appants.com.kutumblink.HomeActivity;
import kutumblink.appants.com.kutumblink.R;
import kutumblink.appants.com.kutumblink.adapter.GroupListAdapter;
import kutumblink.appants.com.kutumblink.model.GroupDo;
import kutumblink.appants.com.kutumblink.utils.Constants;
import kutumblink.appants.com.kutumblink.utils.DatabaseHandler;

public class GroupsMainFragment extends BaseFragment {
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
        Constants.GROUP_NAMECI="";
        Constants.GROUP_NAMEP="";
      //  Constants.RESULT=0;
    /*    Cursor cr = dbHandler.retriveData("select * from " + DatabaseHandler.TABLE_PHONE_CONTACTS + " where Phone_Contact_Gid='" + Constants.GROUP_NAME + "' order by Phone_Contact_Name ASC");


        if (cr != null) {
            if (cr.getCount() > 0) {
                cr.moveToFirst();
                do {
                    try {
                        if (!cr.getString(cr.getColumnIndex(dbHandler.PHONE_CONTACT_ID)).equalsIgnoreCase("null")) {
                            readContacts(cr.getString(cr.getColumnIndex(dbHandler.PHONE_CONTACT_ID)));



                        }
                    } catch (Exception e) {

                    }

                } while (cr.moveToNext());

            }
        }*/
      if(Constants.NAV_GROUPS==101){


          Cursor c = dbHandler.retriveData("select * from " + DatabaseHandler.TABLE_PHONE_CONTACTS + " where Phone_Contact_Gid='" + Constants.GROUP_NAME + "' order by Phone_Contact_Name ASC");


          if (c != null) {
              if (c.getCount() > 0) {
                  c.moveToFirst();
                  do {
                      try {
                          if (!c.getString(c.getColumnIndex(dbHandler.PHONE_CONTACT_ID)).equalsIgnoreCase("null")) {
                              readContacts(c.getString(c.getColumnIndex(dbHandler.PHONE_CONTACT_ID)));



                          }
                      } catch (Exception e) {

                      }

                  } while (c.moveToNext());

              }
          }
          FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
          FragmentTransaction ft=fragmentManager.beginTransaction();
          //ft.replace(R.id.main_container, new AddGroupFragment());
          ft.add(R.id.main_container, new AddGroupFragment());
          ft.addToBackStack(null);

          ft.commit();
      }else if(Constants.NAV_GROUPS==102){

          Cursor c = dbHandler.retriveData("select * from " + DatabaseHandler.TABLE_PHONE_CONTACTS + " where Phone_Contact_Gid='" + Constants.GROUP_NAME + "' order by Phone_Contact_Name ASC");


          if (c != null) {
              if (c.getCount() > 0) {
                  c.moveToFirst();
                  do {
                      try {
                          if (!c.getString(c.getColumnIndex(dbHandler.PHONE_CONTACT_ID)).equalsIgnoreCase("null")) {
                              readContacts(c.getString(c.getColumnIndex(dbHandler.PHONE_CONTACT_ID)));



                          }
                      } catch (Exception e) {

                      }

                  } while (c.moveToNext());

              }
          }






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
                String mDrawableName = "group_default_icon";
                Constants.GROUP_OPERATIONS="SAVE";
                Constants.GROUP_NAME="";
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
               FragmentTransaction ft= fragmentManager.beginTransaction();
                ft.add(R.id.main_container, new AddGroupFragment());
                         ft.addToBackStack(null);
                        ft.commit();


                Bitmap bm = BitmapFactory.decodeResource(getResources(),getResources().getIdentifier(mDrawableName , "mipmap", getActivity().getPackageName()));
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bm.compress(Bitmap.CompressFormat.PNG, 100, baos);
                final String encodedImage = Base64.encodeToString(baos.toByteArray(), Base64.DEFAULT);


                Constants.imgID = Base64.encodeToString(baos.toByteArray(), Base64.DEFAULT);
              //  Constants.imgID = getResources().getIdentifier(mDrawableName, "mipmap", getActivity().getPackageName());
            }
        });
        HomeActivity.tv_title.setText("Groups");
        HomeActivity.ib_back.setBackgroundResource(R.mipmap.ic_launcher);
        HomeActivity.ib_back_next.setText("");
        HomeActivity.ib_menu.setBackgroundResource(R.mipmap.menu);
        HomeActivity.ib_menu.setText("");
        String mDrawableName = "group_default_icon";





        if(Constants.imgID.length()<5){
            mDrawableName = "add_group";
            Bitmap bm = BitmapFactory.decodeResource(getResources(),getResources().getIdentifier(mDrawableName , "drawable", getActivity().getPackageName()));
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bm.compress(Bitmap.CompressFormat.JPEG, 100, baos); //bm is the bitmap object
            final String encodedImage = Base64.encodeToString(baos.toByteArray(), Base64.DEFAULT);


            Constants.imgID = Base64.encodeToString(baos.toByteArray(), Base64.DEFAULT);
        }



        /*else {

             mDrawableName = "add_group";
            Bitmap bm = BitmapFactory.decodeResource(getResources(),getResources().getIdentifier(mDrawableName , "drawable", getActivity().getPackageName()));
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bm.compress(Bitmap.CompressFormat.JPEG, 100, baos); //bm is the bitmap object
            final String encodedImage = Base64.encodeToString(baos.toByteArray(), Base64.DEFAULT);


            Constants.imgID = Base64.encodeToString(baos.toByteArray(), Base64.DEFAULT);


         //   Constants.imgID = getResources().getIdentifier(mDrawableName, "mipmap", getActivity().getPackageName());
        }*/



        dbHandler.DeleteTable(DatabaseHandler.TABLE_GROUP,"G_NAME='"+""+"'");
        dbHandler.DeleteTable(DatabaseHandler.TABLE_PHONE_CONTACTS,"Phone_Contact_Gid='"+""+"'");
        dbHandler.DeleteTable(DatabaseHandler.TABLE_PHONE_CONTACTS,"Phone_Contact_Number='"+"##000"+"'");

        Cursor c=dbHandler.retriveData("select * from "+DatabaseHandler.TABLE_GROUP);
        if(c!=null)
        {
            if(c.getCount()>0)
            {
                c.moveToFirst();
                do {
                    try {

                        Cursor cg = dbHandler.retriveData("select * from " + DatabaseHandler.TABLE_PHONE_CONTACTS + " where Phone_Contact_Gid='" + c.getString(c.getColumnIndex(dbHandler.GROUP_NAME)) + "'");

                        GroupDo groupDetails = new GroupDo();
                        groupDetails.setGroup_ID(c.getString(c.getColumnIndex(dbHandler.GROUP_ID)));
                        groupDetails.setGroup_Name(c.getString(c.getColumnIndex(dbHandler.GROUP_NAME)));
                        groupDetails.setGroup_Pic(c.getString(c.getColumnIndex(dbHandler.GROUP_PIC)));
                        groupDetails.setGroup_sortOrder(c.getString(c.getColumnIndex(dbHandler.GROUP_SORT_ORDER)));

                        if (cg != null && cg.getCount() > 0) {
                            groupDetails.setGroup_totalContactList("" + cg.getCount());
                        } else {
                            groupDetails.setGroup_totalContactList("0");// + cg.getCount());
                        }

                        //  groupDetails.setGroup_ID(c.getString(c.getColumnIndex(dbHandler.GROUP_ID)));
                        arr_group.add(groupDetails);
                    }catch(Exception e){

                    }

                }while(c.moveToNext());

            }
        }
       lv_GroupList.setAdapter(new GroupListAdapter(getActivity(),arr_group));

        if(arr_group.size()>0)
        {
            view.findViewById(R.id.bottom_view_line).setVisibility(View.VISIBLE);
        }else {
            view.findViewById(R.id.bottom_view_line).setVisibility(View.GONE);
        }

        lv_GroupList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Constants.GROUP_OPERATIONS="EDIT";
                Constants.GROUP_NAME=arr_group.get(i).getGroup_Name().trim();
                Constants.SortOrderValue=arr_group.get(i).getGroup_sortOrder();
                Constants.GROUP_CONTACTS_SIZE=Integer.parseInt(arr_group.get(i).getGroup_totalContactList());


                Constants.imgID=arr_group.get(i).getGroup_Pic();
                GroupContactsFragment groupContacts =  GroupContactsFragment.newInstance(Constants.GROUP_OPERATIONS); //New means creating adding.
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.main_container, groupContacts);
                fragmentTransaction.addToBackStack("group_Main");
                fragmentTransaction.commit();


            }
        });


        return view;

    }


    public void readContacts(String contactId) {
        StringBuffer sb = new StringBuffer();
        sb.append("......Contact Details.....");
        ContentResolver cr = getActivity().getContentResolver();
        Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
        String phone = null;
        String name=null;
        String emailContact = null;
        String emailType = null;
        String image_uri = "";

        System.out.println("*****name :  ID : " + contactId);
        sb.append("\n Contact Name:");
        Cursor pCur = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?", new String[]{contactId}, null);
        while (pCur.moveToNext()) {

            name = pCur.getString(pCur.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
            phone = pCur.getString(pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));

            sb.append("\n Phone number:" + phone);
            System.out.println("******phone" + phone + "NAME..." + name);
        }
        pCur.close();
        Cursor emailCur = cr.query(ContactsContract.CommonDataKinds.Email.CONTENT_URI, null, ContactsContract.CommonDataKinds.Email.CONTACT_ID + " = ?", new String[]{contactId}, null);
        while (emailCur.moveToNext()) {
            emailContact = emailCur.getString(emailCur.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA));
            emailType = emailCur.getString(emailCur.getColumnIndex(ContactsContract.CommonDataKinds.Email.TYPE));
            sb.append("\nEmail:" + emailContact + "Email type:" + emailType);
            System.out.println("****Email " + emailContact + " Email Type : " + emailType);

        }

       /* Log.v("DATA....","DATA FIRSTLOADED...NAME"+name);
        Log.v("DATA....","DATA FIRSTLOADED...PHONE"+phone);
        Log.v("DATA....","DATA FIRSTLOADED...EMAILID"+emailContact);
        Log.v("DATA....","DATA FIRSTLOADED...EMAILTYPE"+emailType);*/


        ContentValues cv = new ContentValues();
        cv.put(dbHandler.PHONE_CONTACT_NAME, "" + name);
        // cv.put(dbHandler.PHONE_CONTACT_FNAME, "" + arr_contacts.get(a).get);
        //cv.put(dbHandler.PHONE_CONTACT_LNAME, "" + contact.getLastName());
        cv.put(dbHandler.PHONE_CONTACT_NUMBER, "" +phone);
        cv.put(dbHandler.PHONE_CONTACT_EMAIL, "" + emailContact);

        dbHandler.UpdateTable(dbHandler.TABLE_PHONE_CONTACTS, cv, "Phone_Contact_ID='" + contactId + "'");
        emailCur.close();
    }







}
