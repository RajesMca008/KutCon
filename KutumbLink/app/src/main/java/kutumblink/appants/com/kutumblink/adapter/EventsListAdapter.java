package kutumblink.appants.com.kutumblink.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import kutumblink.appants.com.kutumblink.R;
import kutumblink.appants.com.kutumblink.model.EventsDo;

/**
 * Created by Vishnu on 18-05-2016.
 */
public class EventsListAdapter extends BaseAdapter {

    ArrayList itemsList;
    private Context context;
    EventsDo adb;
    //Constructor to initialize values
    public EventsListAdapter(Context context, ArrayList itemsList) {



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
            adb = (EventsDo) getItem(position);
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
            view = inflater.inflate(R.layout.inflate_eventslist, null);
            TextView tv_title=(TextView)view.findViewById(R.id.tv_title);
            TextView tv_desc=(TextView)view.findViewById(R.id.tv_desc);
            TextView tv_date=(TextView)view.findViewById(R.id.tv_date);
            tv_title.setText(""+adb.getEvtTitle());
            tv_desc.setText(""+adb.getEvtDesc());
            tv_date.setText(adb.getEvtDate().replace(",","\n"));


            // set value into textview
            return view;
        }



        return view;
    }


}
