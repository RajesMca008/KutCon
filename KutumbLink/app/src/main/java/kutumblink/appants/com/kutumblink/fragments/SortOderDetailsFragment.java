package kutumblink.appants.com.kutumblink.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import kutumblink.appants.com.kutumblink.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class SortOderDetailsFragment extends BaseFragment {


    public SortOderDetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.sort_oder_details, container, false);
        return view;
    }

}
