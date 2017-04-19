package kutumblink.appants.com.kutumblink.fragments;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import kutumblink.appants.com.kutumblink.HomeActivity;
import kutumblink.appants.com.kutumblink.R;
import kutumblink.appants.com.kutumblink.utils.DatabaseHandler;


/**
 * A simple {@link Fragment} subclass.
 */
public class CameraMainFragment extends BaseFragment {

    private ArrayList<MessageBean> mMsgList=null;
    private String linkURL;
    public  boolean editMode=false;
    private ListView listView;
    private MyListAdapter adapter;

    public CameraMainFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_camera_main, container, false);


        HomeActivity.ib_back.setBackgroundResource(R.mipmap.ic_launcher);

        HomeActivity.ib_back_next.setText("");
        //HomeActivity.ib_menu.setBackgroundResource(R.mipmap.menu);
        HomeActivity.ib_menu.setBackground(null);
        HomeActivity.ib_menu.setText("Edit");
        HomeActivity.tv_title.setText("Photo Links");
        HomeActivity.ib_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                editMode=!editMode;
                if(editMode)
                {
                    HomeActivity.ib_menu.setText("Done");
                }
                else {
                    HomeActivity.ib_menu.setText("Edit");
                }
                listView.invalidateViews();

               /* FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.main_container, new SettingsFragment()).commit();*/
            }
        });


          listView=(ListView)view.findViewById(R.id.listView);

        mMsgList=new ArrayList<MessageBean>();

        View addItem=view.findViewById(R.id.add_layout);

        addItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                EditPhotoFragment editFragment =  new EditPhotoFragment(); //New means creating adding.
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.main_container, editFragment);
                fragmentTransaction.addToBackStack("edit_msg");
                fragmentTransaction.commit();
            }
        });

        DatabaseHandler databaseHandler=null;
        databaseHandler=new DatabaseHandler(getActivity());
        Cursor cursor = databaseHandler.retriveData("select * from " + DatabaseHandler.TABLE_PHOTOS);


        if(cursor!=null && cursor.getCount()>0)
        {
            cursor.moveToFirst();

            do {
                MessageBean bean=new MessageBean();
                bean.setMsgLink(cursor.getString(cursor.getColumnIndex(DatabaseHandler.PHOTO_LINK)));
                bean.setMsgTitle(cursor.getString(cursor.getColumnIndex(DatabaseHandler.PHOTO_TITLE)));
                bean.setMsgId(cursor.getString(cursor.getColumnIndex(DatabaseHandler.PHOTO_ID)));
                mMsgList.add(bean);
            }
            while (cursor.moveToNext());
        }

        if(cursor!=null)
        cursor.close();
        if(databaseHandler!=null)
            databaseHandler.close();

        final  MyListAdapter adapter= new MyListAdapter(getContext(),mMsgList);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {


                if(!editMode) {
                    linkURL = ((MessageBean) adapterView.getItemAtPosition(i)).getMsgLink();
                    showConfirmDialog(getString(R.string.message_text));
                }
                else {
                    perFormEditDeleteOption(i);
                }

            }
        });

        listView .setOnItemLongClickListener (new AdapterView.OnItemLongClickListener() {
            @SuppressWarnings("rawtypes")
            public boolean onItemLongClick(AdapterView parent, View view, final int position, long id) {
                perFormEditDeleteOption(position);
                //do your stuff here
                return true;
            }
        });
        return view;
    }



    private void perFormEditDeleteOption(final int position) {

        final CharSequence[] items = {"Edit", "Delete" };

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        builder.setTitle("Action:");
        builder.setItems(items, new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int item) {
                //cart = mMsgList.get(position);
                //db.removeProductFromCart(context, cart);

                Log.i("TEST", "Action name" + item);
                if (item == 1) {
                    new AlertDialog.Builder(getContext())
                            .setTitle("Are You Sure ?")
                            .setMessage(""+mMsgList.get(position).getMsgTitle())
                            .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {

                                    try {
                                        DatabaseHandler dbHandler = new DatabaseHandler(getContext());
                                        dbHandler.DeleteTable(DatabaseHandler.TABLE_PHOTOS, DatabaseHandler.PHOTO_ID + " = " + mMsgList.get(position).getMsgId());
                                        mMsgList.remove(position);
                                        adapter.notifyDataSetInvalidated();

                                        dbHandler.close();
                                    }
                                    catch (Exception e)
                                    {
                                        e.printStackTrace();
                                    }
                                }
                            })
                            .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    Toast.makeText(getContext(), "Cancel", Toast.LENGTH_LONG).show();
                                }
                            })
                            .show();

                }
                else if(item==0)
                {
                    EditPhotoFragment editFragment =  EditPhotoFragment.newInstance(mMsgList.get(position).getMsgId());
                    FragmentManager fragmentManager = getFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.main_container, editFragment);
                    fragmentTransaction.addToBackStack("edit_msg");
                    fragmentTransaction.commit();
                }
            }

        });

        AlertDialog alert = builder.create();

        alert.show();
    }

    class MyListAdapter extends BaseAdapter {
        private Context mContext=null;
        private ArrayList<MessageBean> mList=null;

        public MyListAdapter(Context context, ArrayList<MessageBean> messageList) {
            this.mContext=context;
            this.mList=messageList;
        }

        @Override
        public int getCount() {
            return mList.size();
        }

        @Override
        public Object getItem(int i) {
            return mList.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {

            if(view==null)
            {
                view=View.inflate(mContext,R.layout.list_item,null);

            }
            else {

            }
            TextView messageTitle=(TextView)view.findViewById(R.id.text_id);

            messageTitle.setText(mList.get(i).getMsgTitle());
            ImageView editImage=(ImageView) view.findViewById(R.id.edit_mode);
            if(editMode)
            {
                editImage.setVisibility(View.VISIBLE);
            }else {
                editImage.setVisibility(View.GONE);
            }

            return view;
        }
    }

    public void showConfirmDialog(String message) {
        AlertDialog.Builder builder = new  AlertDialog.Builder(getContext());


        builder.setTitle(getString(R.string.app_name));
        builder.setIcon(R.mipmap.ic_launcher);
        StringBuffer sb = new StringBuffer(message);


        builder.setMessage(sb.toString()).setCancelable(false);
        builder.setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(linkURL));
                        startActivity(browserIntent);

                    }
                }

        );
        builder.setNegativeButton("Cancel", null);
        builder.show();
    }

}
