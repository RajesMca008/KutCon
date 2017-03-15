package kutumblink.appants.com.kutumblink;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import kutumblink.appants.com.kutumblink.fragments.SelectContactsFragment;

/**
 * Created by Vishnu on 13-03-2016.
 */
/**
 * A simple {@link Fragment} subclass.
 */
public class AddGroupFragment extends Fragment {


    public AddGroupFragment() {
        // Required empty public constructor
    }

    TextView tv_selectContact;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_add_group, container, false);

        tv_selectContact=(TextView)view.findViewById(R.id.tv_selectContact);
        tv_selectContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.main_container, new SelectContactsFragment()).commit();

            }
        });

        return view;
    }

}
