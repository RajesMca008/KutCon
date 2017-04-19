package kutumblink.appants.com.kutumblink.fragments;


import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckedTextView;
import android.widget.TextView;

import kutumblink.appants.com.kutumblink.HomeActivity;
import kutumblink.appants.com.kutumblink.R;
import kutumblink.appants.com.kutumblink.utils.Constants;

/**
 * A simple {@link Fragment} subclass.
 */
public class SortOderDetailsFragment extends BaseFragment implements View.OnClickListener {

    private CheckedTextView defaultText,firstName,lastname;

    public SortOderDetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.sort_oder_details, container, false);

        defaultText= (CheckedTextView) view.findViewById(R.id.default_id);
        firstName= (CheckedTextView) view.findViewById(R.id.by_firstname);
        lastname= (CheckedTextView) view.findViewById(R.id.by_lastname);

        defaultText.setOnClickListener(this);
        firstName.setOnClickListener(this);
        lastname.setOnClickListener(this);

       /* SharedPreferences sharedPreferences=getActivity().getSharedPreferences(Constants.PRF_FILE_NAME, Context.MODE_PRIVATE);

        String sortOder=sharedPreferences.getString("SORT_ORDER",Constants.DEFAULT);*/


        initi(Constants.SortOrderValue);

        HomeActivity.ib_back.setBackgroundResource(R.mipmap.left_arrow);

        HomeActivity.ib_back_next.setText(HomeActivity.ib_back_next.getText().toString());
        HomeActivity.ib_menu.setBackgroundColor(Color.TRANSPARENT);
        HomeActivity.ib_menu.setText("");

        HomeActivity.ib_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.popBackStack();

            }
        });

        HomeActivity.ib_back_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });
        return view;
    }

    private void initi(String sortOder) {

        if(sortOder.equalsIgnoreCase(Constants.DEFAULT))
        {
            defaultText.setCheckMarkDrawable(android.R.drawable.checkbox_on_background);
            firstName.setCheckMarkDrawable(null);
            lastname.setCheckMarkDrawable(null);
        }else if(sortOder.equalsIgnoreCase(Constants.BY_FIRST_NAME))
        {
            defaultText.setCheckMarkDrawable(null);
            firstName.setCheckMarkDrawable(android.R.drawable.checkbox_on_background);
            lastname.setCheckMarkDrawable(null);
        }
        else {
            defaultText.setCheckMarkDrawable(null);
            firstName.setCheckMarkDrawable(null);
            lastname.setCheckMarkDrawable(android.R.drawable.checkbox_on_background);
        }
    }

    @Override
    public void onClick(View view) {

        SharedPreferences sharedPreferences=getActivity().getSharedPreferences(Constants.PRF_FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor edit=sharedPreferences.edit();

        switch (view.getId())
        {
            case R.id.default_id:
                defaultText.setCheckMarkDrawable(android.R.drawable.checkbox_on_background);
                firstName.setCheckMarkDrawable(null);
                lastname.setCheckMarkDrawable(null);
                edit.putString("SORT_ORDER",Constants.DEFAULT);
                Constants.SortOrderValue=Constants.DEFAULT;
                break;
            case R.id.by_firstname:

                defaultText.setCheckMarkDrawable(null);
                firstName.setCheckMarkDrawable(android.R.drawable.checkbox_on_background);
                lastname.setCheckMarkDrawable(null);
                edit.putString("SORT_ORDER",Constants.BY_FIRST_NAME);
                Constants.SortOrderValue=Constants.BY_FIRST_NAME;
                break;
            case R.id.by_lastname:
                defaultText.setCheckMarkDrawable(null);
                firstName.setCheckMarkDrawable(null);
                lastname.setCheckMarkDrawable(android.R.drawable.checkbox_on_background);
                edit.putString("SORT_ORDER",Constants.BY_LAST_NAME);
                Constants.SortOrderValue=Constants.BY_LAST_NAME;
                break;
        }
        edit.commit();
    }
}
