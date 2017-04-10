package kutumblink.appants.com.kutumblink.fragments;


import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kutumblink.appants.com.kutumblink.R;
import kutumblink.appants.com.kutumblink.utils.Constants;
import kutumblink.appants.com.kutumblink.utils.Utils;


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

    String[] imgName = new String[]{
            "bag.jpg",
            "basket.jpg",
            "monitor.jpg",
            "movies.jpg",
            "note.jpg",
            "pad.jpg",
            "photo_gallery.jpg",
            "photos.jpg",
            "post_card.jpg",
            "printwer.jpg",

            "reading.jpg",
            "rescue.jpg",
            "rubix_cube.jpg",
            "bank.jpg",
            "search.jpg",
            "sit.jpg",
            "speaker.jpg",
            "speedometer.jpg",
            "spray.jpg",
            "stop.jpg",
            "sweep.jpg",
            "twitter.jpg",

            "umbrella.jpg",
            "wheel.jpg",
            "wifi.jpg","lock.jpg","binacular.jpg","neighbour.jpg","landline.jpg","id_card.jpg",
            "building.jpg","bulb_twitter.jpg","bulbtwo.jpg","calendar.jpg","caliculator.jpg","caliculatortertwo.jpg",
            "camera.jpg","cameratwo.jpg","car_service.jpg","cd.jpg","chat.jpg","font_letter.jpg",
            "home.jpg","flag.jpg","doll.jpg","clock.jpg","chattwo.jpg"  };
    public static SelectGroupIconsFragment newInstance(String param1)
    {
        SelectGroupIconsFragment fragment=new SelectGroupIconsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    Button btn_camera,btn_gallery;
    private String userChoosenTask;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_group_icons, container, false);
        btn_camera=(Button)view.findViewById(R.id.btn_camera);
        btn_gallery=(Button)view.findViewById(R.id.btn_gallery);

        btn_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userChoosenTask ="Take Photo";
                    cameraIntent();
            }
        });

        btn_gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userChoosenTask ="Choose from Library";
                    galleryIntent();
            }
        });

        Constants.NAV_GROUPS=103;
        final List<HashMap<String,String>> aList = new ArrayList<HashMap<String,String>>();

        for(int i=0;i<46;i++){
            HashMap<String, String> hm = new HashMap<String,String>();
            hm.put("imgName", imgName[i]);
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



                int a=0;

                for (HashMap<String, String> map : aList) {
                    if (a == i) {
                        for (Map.Entry<String, String> mapEntry : map.entrySet()) {
                            String key = mapEntry.getKey();
                            String value = mapEntry.getValue();

                            if(key.equalsIgnoreCase("flag")) {
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

    private void galleryIntent()
    {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);//
        startActivityForResult(Intent.createChooser(intent, "Select File"),SELECT_FILE);
    }

    private void cameraIntent()
    {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_CAMERA);
    }

    private int REQUEST_CAMERA = 0, SELECT_FILE = 1;
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case Utils.MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if(userChoosenTask.equals("Take Photo"))
                        cameraIntent();
                    else if(userChoosenTask.equals("Choose from Library"))
                        galleryIntent();
                } else {
                    //code for deny
                }
                break;
        }
    }

    private void onCaptureImageResult(Intent data) {
        Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        thumbnail.compress(Bitmap.CompressFormat.JPEG, 90, bytes);

        File destination = new File(Environment.getExternalStorageDirectory(),
                System.currentTimeMillis() + ".jpg");

        FileOutputStream fo;
        try {
            destination.createNewFile();
            fo = new FileOutputStream(destination);
            fo.write(bytes.toByteArray());
            fo.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

       // ivImage.setImageBitmap(thumbnail);
    }

    @SuppressWarnings("deprecation")
    private void onSelectFromGalleryResult(Intent data) {

        Bitmap bm=null;
        if (data != null) {
            try {
                bm = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), data.getData());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

     //   ivImage.setImageBitmap(bm);
    }




}
