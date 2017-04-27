package kutumblink.appants.com.kutumblink;

/**
 * @auther Rrallabandi
 */

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateFormat;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.DatePicker;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;

import java.util.Calendar;
import java.util.Date;

import kutumblink.appants.com.kutumblink.fragments.BaseFragment;
import kutumblink.appants.com.kutumblink.fragments.CameraMainFragment;
import kutumblink.appants.com.kutumblink.fragments.EditEventsFragment;
import kutumblink.appants.com.kutumblink.fragments.EventsMainFragment;
import kutumblink.appants.com.kutumblink.fragments.GroupsMainFragment;
import kutumblink.appants.com.kutumblink.fragments.MessageMainFragment;
import kutumblink.appants.com.kutumblink.fragments.SettingsFragment;
import kutumblink.appants.com.kutumblink.utils.Constants;
import kutumblink.appants.com.kutumblink.utils.RangeTimePickerDialog;
import kutumblink.appants.com.kutumblink.utils.Utils;

public class HomeActivity extends AppCompatActivity implements BaseFragment.OnFragmentInteractionListener {


    private Fragment fragment;
    private FragmentManager fragmentManager;
    public Context ctx;

    private String[] mPlanetTitles;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private CharSequence mTitle="";
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_groups:
                   fragment=new GroupsMainFragment();
                   // item.setIcon(R.mipmap.groups_btn_active_n);

                    break;
                case R.id.navigation_events:
                    fragment=new EventsMainFragment();
                   // item.setIcon(R.mipmap.events_btn_active_n);
                    break;
                case R.id.navigation_message:
                    fragment=new MessageMainFragment();
                   // item.setIcon(R.mipmap.messages_btn_active_n);
                    break;
                case R.id.navigation_photos:
                    fragment=new CameraMainFragment();
                   // item.setIcon(R.mipmap.photos_btn_active_n);
                    break;
            }

            if(Constants.needToShowAdd) {
                if (mAdView.isShown()) {

                    AdRequest adRequest = new AdRequest.Builder()
                            .build();
                    mAdView.loadAd(adRequest);
                }
            }
            final FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.replace(R.id.main_container, fragment).commit();

            return true;
        }

    };
    private NavigationView nvDrawer;

    public static TextView ib_back;
    public static TextView ib_back_next;

   public static  TextView tv_title;
   public static TextView ib_menu;
    private AdView mAdView;
    private InterstitialAd interstitial;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_home);

        fragmentManager = getSupportFragmentManager();

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.custom_actionbar);
        View view =getSupportActionBar().getCustomView();

        ib_back=(TextView)view.findViewById(R.id.ib_action_bar_back);
        ib_menu=(TextView)view.findViewById(R.id.ib_action_menu);
        tv_title=(TextView)view.findViewById(R.id.tv_title);
        ib_back_next=(TextView)view.findViewById(R.id.ib_action_bar_back_next);
        ib_menu.setBackgroundResource(R.mipmap.ic_launcher);
        ib_back_next.setText("");
        tv_title.setText("Groups");
        ib_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction ft=fragmentManager.beginTransaction();
                ft.replace(R.id.main_container, new SettingsFragment());
                ft.addToBackStack("group_Main");
                ft.setCustomAnimations(R.anim.slide_right , R.anim.slide_left);

                ft.commit();
            }
        });

        if (savedInstanceState == null) {

                displayView(0);

        }else{

                displayView(0);

        }
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);



        BottomNavigationMenuView menuView = (BottomNavigationMenuView) navigation.getChildAt(0);
        for (int i = 0; i < menuView.getChildCount(); i++) {
            final View iconView = menuView.getChildAt(i).findViewById(android.support.design.R.id.icon);
            final ViewGroup.LayoutParams layoutParams = iconView.getLayoutParams();
            final DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
            // set your height here
            layoutParams.height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 20, displayMetrics);
            // set your width here
            layoutParams.width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 20, displayMetrics);

            FrameLayout.LayoutParams  params = (FrameLayout.LayoutParams) iconView.getLayoutParams();
            params.gravity = Gravity.CENTER;
            iconView.setLayoutParams(layoutParams);
        }

        //If you want disable shift change mode for bottom
        BottomNavigationViewHelper.disableShiftMode(navigation);
        mPlanetTitles = getResources().getStringArray(R.array.menu_name_array);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        

        if(Constants.needToShowAdd) {
            //Ads
            mAdView = (AdView) findViewById(R.id.adView);
            AdRequest adRequest = new AdRequest.Builder()
                    .build();
            mAdView.loadAd(adRequest);


            AdRequest adRequest2 = new AdRequest.Builder()
                    //.addTestDevice("E399DEBE7C412EAE23738DF4A849FE60")
                    .build();

            interstitial = new InterstitialAd(HomeActivity.this);
            // Insert the Ad Unit ID
            interstitial.setAdUnitId(getString(R.string.intresttitle_ad_unit_id));
            // Load ads into Interstitial Ads
            interstitial.loadAd(adRequest2);

        }
    }

    private void displayView(int position) {
        // update the main content by replacing fragments
        android.app.Fragment fragment = null;
            switch (position) {
                case 0:
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.main_container, new GroupsMainFragment()).commit();
                    break;
                default:
                    break;

            }

    }



    public static class TimePicker extends DialogFragment implements TimePickerDialog.OnTimeSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
           /* final Calendar c = Calendar.getInstance();
            int hour = c.get(Calendar.HOUR_OF_DAY);
            int minute = c.get(Calendar.MINUTE);


            RangeTimePickerDialog timePicker=  new RangeTimePickerDialog(getActivity(), this, hour, minute, DateFormat.is24HourFormat(getActivity()));
            //timePicker.getT

            timePicker.setMin(hour,minute);*/


            final Calendar mcurrentTime = Calendar.getInstance();
            final int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
            final int minute = mcurrentTime.get(Calendar.MINUTE);
            final RangeTimePickerDialog mTimePicker;
            mTimePicker = new RangeTimePickerDialog(getActivity(), this, hour, minute, true);//true = 24 hour time
            mTimePicker.setTitle("Select Time");
            mTimePicker.setMin(hour, minute);
           // mTimePicker.show();

            return mTimePicker;
        }

        @Override
        public void onTimeSet(android.widget.TimePicker view, int hourOfDay, int minute) {
            Constants.strDT+=", "+String.valueOf(hourOfDay) + ":" + String.valueOf(minute);

            Log.v("LOG....","LOG DETAILS>..TIME...>."+Constants.strDT);
            EditEventsFragment.event_title_text.setText(Constants.strDT);
        }
    }

    public static class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datepicker= new DatePickerDialog(getActivity(), this, year, month, day);
            datepicker.getDatePicker().setMinDate(new Date().getTime());
            return datepicker;
        }
        public void onDateSet(DatePicker view, int year, int month, int day) {
        //    displayCurrentTime.setText("Selected date: " + String.valueOf(year) + " - " + String.valueOf(month) + " - " + String.valueOf(day));

            Constants.strDT= String.valueOf(day) + "/" + String.valueOf(month+1) + "/" +String.valueOf(year);
           EditEventsFragment.event_title_text.setText(Constants.strDT);
           // EditEventsFragment.event_title_text.setText(Constants.strDT+" ,");
            //Log.v("LOG....","LOG DETAILS>..date...>."+Constants.strDT);
            HomeActivity.TimePicker mTimePicker = new HomeActivity.TimePicker();
            mTimePicker.show(getActivity().getFragmentManager(), "Select time");
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(Constants.needToShowAdd) {
            Log.i("TEST", "onDes" + interstitial.isLoaded());
            if (interstitial.isLoaded()) {
                interstitial.show();
            }
        }
    }

    @Override
    public void onFragmentInteraction(Uri uri) {
        Log.i("TEST","Fragment interatcion"+uri);
    }
}
