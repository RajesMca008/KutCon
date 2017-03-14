package kutumblink.appants.com.kutumblink.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import kutumblink.appants.com.kutumblink.R;

/**
 * Created by Vishnu on 18-05-2016.
 */
public class GroupListAdapter extends BaseAdapter {

    ArrayList<GroupListBean> itemsList;
    private Context context;

    //Constructor to initialize values
    public GroupListAdapter(Context context,  ArrayList<GroupListBean> itemsList) {



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

        return null;
    }

    @Override
    public long getItemId(int position) {

        return 0;
    }


    // Number of times getView method call depends upon gridValues.length

    public View getView(final int position, View convertView, ViewGroup parent) {

        //LayoutInflator to call external grid_item.xml file

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view=null;

        if (convertView == null) {

            view = new View(context);

            // get layout from grid_item.xml
            view = inflater.inflate(R.layout.inflate_grouplist, null);



            // set value into textview
            return view;
        }



        return view;
    }


}
