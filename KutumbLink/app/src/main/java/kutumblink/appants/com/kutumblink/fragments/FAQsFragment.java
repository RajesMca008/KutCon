package kutumblink.appants.com.kutumblink.fragments;


import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import kutumblink.appants.com.kutumblink.HomeActivity;
import kutumblink.appants.com.kutumblink.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class FAQsFragment extends Fragment {


    public FAQsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_faqs, container, false);

        ((TextView)view.findViewById(R.id.faq1)).setText(Html.fromHtml(getString(R.string.faq1)));

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            ((TextView)view.findViewById(R.id.faq1)).setText(Html.fromHtml(getString(R.string.faq1),Html.FROM_HTML_MODE_LEGACY));
            ((TextView)view.findViewById(R.id.faq2)).setText(Html.fromHtml(getString(R.string.faq2),Html.FROM_HTML_MODE_LEGACY));
            ((TextView)view.findViewById(R.id.faq3)).setText(Html.fromHtml(getString(R.string.faq3),Html.FROM_HTML_MODE_LEGACY));
            ((TextView)view.findViewById(R.id.faq4)).setText(Html.fromHtml(getString(R.string.faq4),Html.FROM_HTML_MODE_LEGACY));
        }

        else
        {
            ((TextView)view.findViewById(R.id.faq1)).setText(Html.fromHtml(getString(R.string.faq1)));
            ((TextView)view.findViewById(R.id.faq2)).setText(Html.fromHtml(getString(R.string.faq2)));
            ((TextView)view.findViewById(R.id.faq3)).setText(Html.fromHtml(getString(R.string.faq3)));
            ((TextView)view.findViewById(R.id.faq4)).setText(Html.fromHtml(getString(R.string.faq4)));
        }

        HomeActivity.tv_title.setText("FAQs");
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
