package kutumblink.appants.com.kutumblink.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import kutumblink.appants.com.kutumblink.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class SelectGroupIconsFragment extends BaseFragment  {


    private EditText textTitle;
    private EditText textLink;
    private static final String ARG_PARAM1 = "param1";

    public SelectGroupIconsFragment() {
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
            R.drawable.printwer,

            R.drawable.reading,
            R.drawable.rescue,
            R.drawable.rubix_cube,
            R.drawable.bank,
            R.drawable.search,
            R.drawable.sit,
            R.drawable.speaker,
            R.drawable.speedometer,
            R.drawable.spray,
            R.drawable.stop,
            R.drawable.sweep,
            R.drawable.twitter,

            R.drawable.umbrella,
            R.drawable.wheel,
            R.drawable.wifi,R.drawable.lock,R.drawable.binacular,R.drawable.neighbour,R.drawable.landline,R.drawable.id_card,
            R.drawable.building,R.drawable.bulb_twitter,R.drawable.bulbtwo,R.drawable.calendar,R.drawable.caliculator,R.drawable.caliculatortertwo,
            R.drawable.camera,R.drawable.cameratwo,R.drawable.car_service,R.drawable.cd,R.drawable.chat,R.drawable.font_letter,
            R.drawable.home,R.drawable.flag,R.drawable.doll,R.drawable.clock,R.drawable.chattwo   };


    public static SelectGroupIconsFragment newInstance(String param1)
    {
        SelectGroupIconsFragment fragment=new SelectGroupIconsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_group_icons, container, false);


        List<HashMap<String,String>> aList = new ArrayList<HashMap<String,String>>();

        for(int i=0;i<46;i++){
            HashMap<String, String> hm = new HashMap<String,String>();
           // hm.put("txt", countries[i]);
            hm.put("flag", Integer.toString(flags[i]) );
            aList.add(hm);
        }

        // Keys used in Hashmap
        String[] from = { "flag"};

        // Ids of views in listview_layout
        int[] to = { R.id.flag};
        GridView gridView = (GridView)view. findViewById(R.id.gridview);
        // Instantiating an adapter to store each items
        // R.layout.listview_layout defines the layout of each item
        SimpleAdapter adapter = new SimpleAdapter(getActivity().getBaseContext(), aList, R.layout.inflate_group_icons, from, to);
        gridView.setAdapter(adapter);
            activity.setTitle("Select Group Image");


        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getActivity(),flags[i],Toast.LENGTH_LONG).show();

            }
        });
        return view;
    }



}
