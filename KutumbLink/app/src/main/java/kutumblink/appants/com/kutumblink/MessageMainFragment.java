package kutumblink.appants.com.kutumblink;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.preference.DialogPreference;
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
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class MessageMainFragment extends Fragment {


    public MessageMainFragment() {
        // Required empty public constructor
    }


    private ArrayList<MessageBean> mMsgList=null;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_message_main, container, false);
        ListView listView=(ListView)view.findViewById(R.id.listView);

        mMsgList=new ArrayList<MessageBean>();

        for (int i=0;i<20;i++)
        {
            MessageBean bean=new MessageBean();
            bean.setMsgTitle("Message title "+i);
            mMsgList.add(bean);
        }

        final MyListAdapter adapter=new MyListAdapter(getContext(),mMsgList);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getContext(),"Selected"+((MessageBean)adapterView.getItemAtPosition(i)).getMsgTitle(),Toast.LENGTH_LONG).show();
            }
        });

        listView .setOnItemLongClickListener (new AdapterView.OnItemLongClickListener() {
            @SuppressWarnings("rawtypes")
            public boolean onItemLongClick(AdapterView parent, View view, final int position, long id) {
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

                                            //Need to write code for delete item from DB
                                            mMsgList.remove(position);
                                            adapter.notifyDataSetInvalidated();
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
                            EditMessageFragment editFragment = new EditMessageFragment();
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
                //do your stuff here
                return false;
            }
        });
        return view;
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

            return view;
        }
    }
}
