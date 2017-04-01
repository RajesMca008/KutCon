package kutumblink.appants.com.kutumblink.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kutumblink.appants.com.kutumblink.R;
import kutumblink.appants.com.kutumblink.utils.Constants;


/**
 * A simple {@link Fragment} subclass.
 */
public class FavuarateGroupIconsFragment extends BaseFragment  {


    private EditText textTitle;
    private EditText textLink;
    private static final String ARG_PARAM1 = "param1";

    public FavuarateGroupIconsFragment() {
        // Required empty public constructor
    }
    // Array of integers points to images stored in /res/drawable-ldpi/
    int[] flags = new int[]{
            R.drawable.bag,
            R.drawable.basket,
            R.drawable.monitor,
            R.drawable.movies,
            R.drawable.note,
            R.drawable.pad,
            R.drawable.photo_gallery,
            R.drawable.photos,
            R.drawable.post_card,
            R.drawable.printwer  };

    String[] str_flags = new String[]{
            "Family",
            "Extended Family",
            "Cousins",
            "All Relatives",
            "Friends",
            "Work Friends",
            "photo_gallery",
            "Neighbors",
            "Party Friends" };


    public static FavuarateGroupIconsFragment newInstance(String param1)
    {
        FavuarateGroupIconsFragment fragment=new FavuarateGroupIconsFragment();
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


        final List<HashMap<String,String>> aList = new ArrayList<HashMap<String,String>>();

        for(int i=0;i<10;i++){
            HashMap<String, String> hm = new HashMap<String,String>();
           hm.put("str_flags", str_flags[i]);
            hm.put("flags", Integer.toString(flags[i]) );
            aList.add(hm);
        }

        // Keys used in Hashmap
        String[] from = { "flags","str_flags"};

        // Ids of views in listview_layout
        int[] to = { R.id.iv_photo,R.id.tv_goupname};
        ListView gridView = (ListView)view. findViewById(R.id.lv_grouplist);
        // Instantiating an adapter to store each items
        // R.layout.listview_layout defines the layout of each item
        SimpleAdapter adapter = new SimpleAdapter(getActivity().getBaseContext(), aList, R.layout.inflate_favgrouplist, from, to);
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

                            if(key.equalsIgnoreCase("flags")) {
                                Constants.imgID=Integer.parseInt(value);

                                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                                fragmentManager.beginTransaction().replace(R.id.main_container, new AddGroupFragment()).commit();

                                Toast.makeText(getActivity(), "key....." + key + "...value..." + value, Toast.LENGTH_LONG).show();
                            }
                        }

                    }

                    a++;
                }
            }
        });
        return view;
    }



}
