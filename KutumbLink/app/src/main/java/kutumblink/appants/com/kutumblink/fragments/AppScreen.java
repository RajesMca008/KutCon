package kutumblink.appants.com.kutumblink.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import kutumblink.appants.com.kutumblink.HomeActivity;
import kutumblink.appants.com.kutumblink.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class AppScreen extends Fragment {


    public AppScreen() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_app_screen, container, false);


        HomeActivity.tv_title.setText("");
        HomeActivity.ib_back_next.setText(getString(R.string.settings));

        HomeActivity.ib_back_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SettingsFragment settingsFragment = new SettingsFragment(); //New means creating adding.
                FragmentManager fragmentManager = getFragmentManager();
                if(fragmentManager!=null) {
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.main_container, settingsFragment);
                    fragmentTransaction.commit();
                }
            }
        });
        return view;
    }

}
