package kutumblink.appants.com.kutumblink.fragments;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

import kutumblink.appants.com.kutumblink.R;
import kutumblink.appants.com.kutumblink.adapter.FavuarateGroupListAdapter;
import kutumblink.appants.com.kutumblink.model.GroupDo;
import kutumblink.appants.com.kutumblink.utils.Constants;
import kutumblink.appants.com.kutumblink.utils.DatabaseHandler;

public class FavaurateGroupFragment extends BaseFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public FavaurateGroupFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static FavaurateGroupFragment newInstance(String param1, String param2) {
        FavaurateGroupFragment fragment = new FavaurateGroupFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    ListView lv_GroupList;
    DatabaseHandler dbHandler;
    ArrayList<GroupDo> arr_group=new ArrayList<GroupDo>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        dbHandler=new DatabaseHandler(getActivity());
    }
    int[] flags = new int[]{
            R.drawable.add_group };
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_favgroups_main, container, false);

        lv_GroupList=(ListView)view.findViewById(R.id.lv_grouplist);
        arr_group.clear();


        String mDrawableName = "add_group";
        Bitmap bm = BitmapFactory.decodeResource(getResources(),getResources().getIdentifier(mDrawableName , "drawable", getActivity().getPackageName()));
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 100, baos); //bm is the bitmap object
        final String encodedImage = Base64.encodeToString(baos.toByteArray(), Base64.DEFAULT);


        Constants.imgID = Base64.encodeToString(baos.toByteArray(), Base64.DEFAULT);


        //getResources().getIdentifier(mDrawableName , "drawable", getActivity().getPackageName());




        Log.v("GROUP SIZE...","SIZE OF GROUP..."+arr_group.size());
       lv_GroupList.setAdapter(new FavuarateGroupListAdapter(getActivity(),arr_group));


        lv_GroupList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {


                Constants.GROUP_NAME=arr_group.get(i).getGroup_Name();


                AddGroupFragment addGroupFragment = new AddGroupFragment(); //New means creating adding.
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.main_container, addGroupFragment);
                fragmentTransaction.addToBackStack("group_Main");
                fragmentTransaction.commit();


            }
        });


        return view;

    }




}
