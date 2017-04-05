package kutumblink.appants.com.kutumblink;

/**
 * @auther Rrallabandi
 */

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import kutumblink.appants.com.kutumblink.fragments.BaseFragment;
import kutumblink.appants.com.kutumblink.fragments.CameraMainFragment;
import kutumblink.appants.com.kutumblink.fragments.EventsMainFragment;
import kutumblink.appants.com.kutumblink.fragments.GroupsMainFragment;
import kutumblink.appants.com.kutumblink.fragments.MessageMainFragment;

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

                    break;
                case R.id.navigation_events:
                    fragment=new EventsMainFragment();
                    break;
                case R.id.navigation_message:
                    fragment=new MessageMainFragment();
                    break;
                case R.id.navigation_photos:
                    fragment=new CameraMainFragment();
                    break;
            }

            final FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.replace(R.id.main_container, fragment).commit();

            return true;
        }

    };
    private NavigationView nvDrawer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_home);

        fragmentManager = getSupportFragmentManager();


        if (savedInstanceState == null) {
            // on first time display view for first nav item

                displayView(0);

        }else{

                displayView(0);

        }
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        //If you want disable shift change mode for bottom
        BottomNavigationViewHelper.disableShiftMode(navigation);


        mPlanetTitles = getResources().getStringArray(R.array.menu_name_array);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);


        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true); // this sets the button to the    back icon
        actionBar.setHomeButtonEnabled(true); // makes it clickable
        // actionBar.setHomeAsUpIndicator(R.drawable.ic_drawer);// set your own icon


        nvDrawer = (NavigationView) findViewById(R.id.nvView);


        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawerLayout, R.string.groups, R.string.app_name){

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                getSupportActionBar() .setTitle(getString(R.string.app_name));
            }

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getSupportActionBar().setTitle("Menu");
            }
        };

        mDrawerLayout.addDrawerListener(toggle);
        toggle.syncState();


        // Setup drawer view
        setupDrawerContent(nvDrawer);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            if(mDrawerLayout.isDrawerOpen(GravityCompat.START))
            {
                mDrawerLayout.closeDrawers();
            }
            else {
                mDrawerLayout.openDrawer(GravityCompat.START);
            }
        }
        return super.onOptionsItemSelected(item);
    }

    private void setupDrawerContent(NavigationView navigationView) {


        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        selectDrawerItem(menuItem);
                        return true;
                    }
                });
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
    public void selectDrawerItem(MenuItem menuItem) {
        // Create a new fragment and specify the fragment to show based on nav item clicked
        Fragment fragment = null;
        Class fragmentClass;
        switch(menuItem.getItemId()) {
            case R.id.nav_share_fragment:
               fragmentClass = GroupsMainFragment.class;
                break;
            case R.id.nav_rate_fragment:
                fragmentClass = MessageMainFragment.class;
                break;
            case R.id.nav_contact_fragment:
                fragmentClass = EventsMainFragment.class;
                break;
            default:
                fragmentClass = CameraMainFragment.class;
        }

        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Insert the fragment by replacing any existing fragment
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.main_container, fragment).commit();

        /*Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);

        startActivityForResult(intent,  */


        // Highlight the selected item has been done by NavigationView
        menuItem.setChecked(true);
        // Set action bar title
        setTitle(menuItem.getTitle());
        // Close the navigation drawer
        mDrawerLayout.closeDrawers();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {
        Log.i("TEST","Fragment interatcion"+uri);
    }
}
