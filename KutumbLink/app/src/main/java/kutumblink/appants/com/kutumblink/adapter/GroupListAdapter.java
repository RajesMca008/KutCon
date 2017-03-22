package kutumblink.appants.com.kutumblink.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import kutumblink.appants.com.kutumblink.R;
import kutumblink.appants.com.kutumblink.model.GroupDo;

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

        return 0;
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

        View view=null;

        if (convertView == null) {

            view = new View(context);

            // get layout from grid_item.xml
            view = inflater.inflate(R.layout.inflate_grouplist, null);
            TextView tv_groupName=(TextView)view.findViewById(R.id.tv_goupname);
            tv_groupName.setText(adb.getGroup_Name());



            // set value into textview
            return view;
        }



        return view;
    }


}
