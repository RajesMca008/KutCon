package kutumblink.appants.com.kutumblink.fragments;


import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kutumblink.appants.com.kutumblink.HomeActivity;
import kutumblink.appants.com.kutumblink.R;
import kutumblink.appants.com.kutumblink.utils.Constants;


/**
 * A simple {@link Fragment} subclass.
 */
public class SettingsFragment extends BaseFragment  {


    private EditText textTitle;
    private EditText textLink;
    private static final String ARG_PARAM1 = "param1";

    public SettingsFragment() {
        // Required empty public constructor
    }


    String[] str_flags = new String[]{
            "Share KutumbLink",
            "Rate KutumbLink",
            "Contact",
            "FAQs",
            "Kutumblink App Screen",
           };


    public static SettingsFragment newInstance(String param1)
    {
        SettingsFragment fragment=new SettingsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_favgroups_main, container, false);

        Constants.NAV_GROUPS=104;


        HomeActivity.ib_back.setBackgroundResource(R.mipmap.left_arrow);

        HomeActivity.ib_back_next.setText("Groups");
        HomeActivity.ib_menu.setBackgroundColor(Color.TRANSPARENT);
        HomeActivity.ib_menu.setText("");
        HomeActivity.tv_title.setText("Settings");

        HomeActivity.ib_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Constants.NAV_GROUPS=100;
                GroupsMainFragment groupContacts = new GroupsMainFragment(); //New means creating adding.
                FragmentManager fragmentManager = getFragmentManager();
                if(fragmentManager!=null) {
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.main_container, new GroupsMainFragment());

                    fragmentTransaction.commit();
                }
            }
        });

        HomeActivity.ib_back_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Constants.NAV_GROUPS=100;
                GroupsMainFragment groupContacts = new GroupsMainFragment(); //New means creating adding.
                FragmentManager fragmentManager = getFragmentManager();
                if(fragmentManager!=null) {
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.main_container, new GroupsMainFragment());

                    fragmentTransaction.commit();
                }
            }
        });


        final List<HashMap<String,String>> aList = new ArrayList<HashMap<String,String>>();

        for(int i=0;i<5;i++){
            HashMap<String, String> hm = new HashMap<String,String>();
           hm.put("str_flags", str_flags[i]);

            aList.add(hm);
        }

        // Keys used in Hashmap
        String[] from = { "str_flags"};

        // Ids of views in listview_layout
        int[] to = { R.id.tv_settings};
        ListView gridView = (ListView)view. findViewById(R.id.lv_grouplist);
        // Instantiating an adapter to store each items
        // R.layout.listview_layout defines the layout of each item
        SimpleAdapter adapter = new SimpleAdapter(getActivity().getBaseContext(), aList, R.layout.inflate_settings, from, to);
        gridView.setAdapter(adapter);
            activity.setTitle("Select Group Image");


        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                int a=0;

                for (HashMap<String, String> map : aList) {
                    if (a == i) {
                        for (Map.Entry<String, String> mapEntry : map.entrySet()) {
                            String key = mapEntry.getKey();
                            String value = mapEntry.getValue();

                            if(key.equalsIgnoreCase("flags")  ) {
                                Constants.imgID=Integer.parseInt(value);

                            }

                            if(key.equalsIgnoreCase("str_flags")  ) {
                                Constants.GROUP_NAME=value;

                                if(value.equalsIgnoreCase("Share KutumbLink"))
                                {
                                    int applicationNameId = getActivity().getApplicationInfo().labelRes;
                                    final String appPackageName =  getActivity().getPackageName();
                                    Intent shareIntent = new Intent(Intent.ACTION_SEND);
                                    shareIntent.setType("text/plain");
                                    shareIntent.putExtra(Intent.EXTRA_SUBJECT, activity.getString(applicationNameId));
                                    String text = "Install this application: ";
                                    String link = "https://play.google.com/store/apps/details?id=" + appPackageName;
                                    shareIntent.putExtra(Intent.EXTRA_TEXT, text + " " + link);
                                    startActivity(Intent.createChooser(shareIntent, "Share link:"));
                                    return;
                                }
                                if(value.equalsIgnoreCase("Rate KutumbLink") )
                                {
                                    Uri uri = Uri.parse("market://details?id=" + getActivity().getPackageName());
                                    Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
                                    // To count with Play market backstack, After pressing back button,
                                    // to taken back to our application, we need to add following flags to intent.
                                    goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
                                            Intent.FLAG_ACTIVITY_NEW_DOCUMENT |
                                            Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                                    try {
                                        startActivity(goToMarket);
                                    } catch (ActivityNotFoundException e) {
                                        startActivity(new Intent(Intent.ACTION_VIEW,
                                                Uri.parse("http://play.google.com/store/apps/details?id=" + getActivity().getPackageName())));
                                    }
                                    return;
                                }
                                if(value.equalsIgnoreCase("Contact"))
                                {

                                    //Contact
                                    ContactEmailSentFragment emailIntent = new ContactEmailSentFragment(); //New means creating adding.
                                    FragmentManager fragmentManager = getFragmentManager();
                                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                    fragmentTransaction.replace(R.id.main_container, emailIntent);

                                    fragmentTransaction.commit();
                                    return;
                                }

                                if(value.equalsIgnoreCase("FAQs"))
                                {

                                    //FAQs
                                    FAQsFragment faQsFragment = new FAQsFragment(); //New means creating adding.
                                    FragmentManager fragmentManager = getFragmentManager();
                                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                    fragmentTransaction.replace(R.id.main_container, faQsFragment);
                                    fragmentTransaction.commit();
                                    return;
                                }
                                if(value.equalsIgnoreCase("Kutumblink App Screen"))
                                {

                                    //Kutumblink App Screen

                                    //FAQs
                                    AppScreen appScreen = new AppScreen(); //New means creating adding.
                                    FragmentManager fragmentManager = getFragmentManager();
                                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                    fragmentTransaction.replace(R.id.main_container, appScreen);
                                    fragmentTransaction.commit();
                                    return;
                                }

                            }




                        }
                    /*    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                        fragmentManager.beginTransaction().replace(R.id.main_container, new AddGroupFragment()).commit();
*/
                    }

                    a++;
                }
            }
        });
        return view;
    }



}
