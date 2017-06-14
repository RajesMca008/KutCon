package kutumblink.appants.com.kutumblink.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import kutumblink.appants.com.kutumblink.R;
import kutumblink.appants.com.kutumblink.model.GroupDo;
import kutumblink.appants.com.kutumblink.utils.Constants;

/**
 * Created by Vishnu on 18-05-2016.
 */
public class GroupListAdapter extends BaseAdapter {

    ArrayList itemsList;
    private Context context;
    GroupDo adb;
    //Constructor to initialize values
    public GroupListAdapter(Context context,  ArrayList itemsList) {



        this.context = context;
        this.itemsList=itemsList;
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

        return position;
    }


    // Number of times getView method call depends upon gridValues.length

    public View getView(final int position, View convertView, ViewGroup parent) {

        //LayoutInflator to call external grid_item.xml file
        try {
            adb = (GroupDo) getItem(position);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);



        if (convertView == null) {

            convertView = inflater.inflate(R.layout.inflate_grouplist, null);
            TextView tv_groupName=(TextView)convertView.findViewById(R.id.tv_goupname);
            TextView tv_totalcontacts=(TextView)convertView.findViewById(R.id.tv_totalcontacts);
            ImageView iv_groupPic=(ImageView)convertView.findViewById(R.id.iv_photo);
            tv_groupName.setText(""+adb.getGroup_Name());
            tv_totalcontacts.setText(""+adb.getGroup_totalContactList());
            if(!adb.getGroup_Pic().contains("/")){
                iv_groupPic.setImageResource(Integer.parseInt(adb.getGroup_Pic()));
            }else{
                iv_groupPic.setImageBitmap(Constants.stringToBitMap(adb.getGroup_Pic()));
            }

            Log.v("DATA..","DATA..BASE 64...."+adb.getGroup_Pic());
            // set value into textview
           // return view;
        }



        return convertView;
    }


}
