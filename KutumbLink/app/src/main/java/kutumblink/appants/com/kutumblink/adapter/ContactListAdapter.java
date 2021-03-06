package kutumblink.appants.com.kutumblink.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import kutumblink.appants.com.kutumblink.R;
import kutumblink.appants.com.kutumblink.fragments.GroupContactsFragment;
import kutumblink.appants.com.kutumblink.model.ContactsDo;
import kutumblink.appants.com.kutumblink.utils.Constants;

/**
 * Created by Vishnu on 18-05-2016.
 */
public class ContactListAdapter extends BaseAdapter {

    private ArrayList itemsList;
    private Context context;
    private ContactsDo adb;
    boolean[] checkBoxState;
    //Constructor to initialize values
    public ContactListAdapter(Context context, ArrayList itemsList) {



        this.context = context;
        this.itemsList=itemsList;
        checkBoxState=new boolean[itemsList.size()];
    }

    @Override
    public int getCount() {

        // Number of times getView method call depends upon gridValues.length
        return itemsList.size();
    }


    @Override
    public Object getItem(int position) {
        return itemsList.get(position);
    }

    @Override
    public long getItemId(int position) {

        return 0;
    }


    // Number of times getView method call depends upon gridValues.length

    public View getView(final int position, View convertView, ViewGroup parent) {

        //LayoutInflator to call external grid_item.xml file
        try {
            adb = (ContactsDo) getItem(position);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }




        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            // view = new View(context);

            // get layout from grid_item.xml
            convertView = inflater.inflate(R.layout.inflate_contactlist, null);


            // set value into textview
        }

        TextView tv_contactName=(TextView)convertView.findViewById(R.id.tv_contactName);
        RelativeLayout rl_contacts=(RelativeLayout)convertView.findViewById(R.id.rl_contacts);
        final ImageView cb_conatacts=(ImageView)convertView.findViewById(R.id.cb_contacts);
        Button btn_phone=(Button)convertView.findViewById(R.id.btn_phone);
        final Button btn_email=(Button)convertView.findViewById(R.id.btn_email);



        if(checkBoxState[position])
        {
            cb_conatacts.setBackgroundResource(R.drawable.radio_btn_selected);
        }else{
            cb_conatacts.setBackgroundResource(R.drawable.radio_btn);}

        try{
            if(adb.getConatactName().split(" ").length>1 && !adb.getConatactName().matches(".*\\d+.*"))
            {
                if( Constants.SortOrderValue.equalsIgnoreCase(Constants.BY_LAST_NAME))
                {



                    String[] words = adb.getConatactName().split(" ");
                    String tmp = words[0];  // grab the first
                    words[0] = words[words.length-1];  //replace the first with the last
                    words[words.length-1] = tmp;
                    String name="";
                    for (int i=0;i<words.length;i++)
                    {
                        name=name+" "+words[i];
                    }
                    Log.i("TEST", "Sort Name"+name);
                    tv_contactName.setText(""+name);
                }
                else {
                    tv_contactName.setText(""+adb.getConatactName());
                }
            }
            else {
                tv_contactName.setText(""+adb.getConatactName());
            }
        }catch (Exception e)
        {
            tv_contactName.setText(""+adb.getConatactName());
            e.printStackTrace();
        }

        try {
            if (GroupContactsFragment.arr_contacts.get(position).getConatactEmail().equalsIgnoreCase("null")) {
                btn_email.setVisibility(View.GONE);
            } else {
                btn_email.setVisibility(View.VISIBLE);
            }

            if (GroupContactsFragment.arr_contacts.get(position).getConatactPhone().equalsIgnoreCase("null")) {
                btn_phone.setVisibility(View.GONE);
            } else {
                btn_phone.setVisibility(View.VISIBLE);
            }
        }catch(Exception e){

        }

        tv_contactName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if( GroupContactsFragment.ll_actions.getVisibility()==View.VISIBLE)
                {
                    GroupContactsFragment.ll_actions.setVisibility(View.GONE);
                }
                //Log.i("TEST","Contact selected "+GroupContactsFragment.arr_contacts.get(position).getConatactName());
                Intent intent = new Intent(Intent.ACTION_VIEW);
                Uri uri = Uri.withAppendedPath(ContactsContract.Contacts.CONTENT_URI, String.valueOf(GroupContactsFragment.arr_contacts.get(position).getConatactId()));
                intent.setData(uri);
                ((Activity)context).startActivityForResult(intent,987);
            }
        });
        cb_conatacts.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                if( GroupContactsFragment.ll_actions.getVisibility()==View.VISIBLE)
                {
                    GroupContactsFragment.ll_actions.setVisibility(View.GONE);
                }

                if(!checkBoxState[position]) {
                    checkBoxState[position] = true;
                   // Log.v("Email....","....EMAIL....Y."+GroupContactsFragment.arr_contacts.get(position).getConatactEmail());

                    cb_conatacts.setBackgroundResource(R.drawable.radio_btn_selected);
                    GroupContactsFragment.arr_contacts.get(position).setIS_CONTACT_SELECTED(1);
                    boolean sel = false;

                    for (int a = 0; a < GroupContactsFragment.arr_contacts.size(); a++) {

                        if (GroupContactsFragment.arr_contacts.get(a).getIS_CONTACT_SELECTED() == 1) {

                            sel = true;
                        }

                    }


                    if(sel){
                        GroupContactsFragment.rl_actions.setAlpha(0.99f);
                        GroupContactsFragment.rl_actions.setEnabled(true);
                        GroupContactsFragment.btn_actions.setEnabled(true);
                        GroupContactsFragment.btn_close.setEnabled(true);
                    }else{
                        GroupContactsFragment.rl_actions.setAlpha(0.5f);
                        GroupContactsFragment.rl_actions.setEnabled(false);
                        GroupContactsFragment.btn_actions.setEnabled(false);
                        GroupContactsFragment.btn_close.setEnabled(false);
                    }
                } else {
                   // Log.v("Email....","....EMAIL....N."+GroupContactsFragment.arr_contacts.get(position).getConatactEmail());

                    cb_conatacts.setBackgroundResource(R.drawable.radio_btn);
                    checkBoxState[position] = false;
                    GroupContactsFragment.arr_contacts.get(position).setIS_CONTACT_SELECTED(0);
                    boolean sel = false;

                    for (int a = 0; a < GroupContactsFragment.arr_contacts.size(); a++) {

                        if (GroupContactsFragment.arr_contacts.get(a).getIS_CONTACT_SELECTED() == 1) {

                            sel = true;
                        }

                    }


                    if(sel){
                        GroupContactsFragment.rl_actions.setAlpha(0.99f);
                        GroupContactsFragment.rl_actions.setEnabled(true);
                        GroupContactsFragment.btn_actions.setEnabled(true);
                        GroupContactsFragment.btn_close.setEnabled(true);
                    }else{
                        GroupContactsFragment.rl_actions.setAlpha(0.5f);
                        GroupContactsFragment.rl_actions.setEnabled(false);
                        GroupContactsFragment.btn_actions.setEnabled(false);
                        GroupContactsFragment.btn_close.setEnabled(false);
                    }

                }

            }
        });

        return convertView;
    }


}
