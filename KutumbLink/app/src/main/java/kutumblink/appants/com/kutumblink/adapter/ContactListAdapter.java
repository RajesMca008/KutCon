package kutumblink.appants.com.kutumblink.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import kutumblink.appants.com.kutumblink.R;
import kutumblink.appants.com.kutumblink.model.ContactsDo;

/**
 * Created by Vishnu on 18-05-2016.
 */
public class ContactListAdapter extends BaseAdapter {

    ArrayList itemsList;
    private Context context;
    ContactsDo adb;
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
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view=null;

        if (convertView == null) {

           // view = new View(context);

            // get layout from grid_item.xml
            view = inflater.inflate(R.layout.inflate_contactlist, null);
            TextView tv_contactName=(TextView)view.findViewById(R.id.tv_contactName);
            final ImageView cb_conatacts=(ImageView)view.findViewById(R.id.cb_contacts);
            tv_contactName.setText(""+adb.getConatactName());
          //  cb_conatacts.setChecked(checkBoxState[position]);



            cb_conatacts.setOnClickListener(new View.OnClickListener() {

                public void onClick(View v) {
                    if(!checkBoxState[position]) {
                        checkBoxState[position] = true;
                        cb_conatacts.setBackgroundResource(R.drawable.radio_btn_selected);
                    } else {
                        cb_conatacts.setBackgroundResource(R.drawable.radio_btn);
                        checkBoxState[position] = false;
                    }

                }
            });

            // set value into textview
            return view;
        }



        return view;
    }


}
