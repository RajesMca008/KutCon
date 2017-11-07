package kutumblink.appants.com.kutumblink.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

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




        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
           // view = new View(context);

            // get layout from grid_item.xml
            convertView = inflater.inflate(R.layout.inflate_eventslist, null);
            TextView tv_title=(TextView)convertView.findViewById(R.id.tv_title);
            TextView tv_desc=(TextView)convertView.findViewById(R.id.tv_desc);
            TextView tv_date=(TextView)convertView.findViewById(R.id.tv_date);
            tv_title.setText(""+adb.getEvtTitle());
            tv_desc.setText(""+adb.getEvtDesc());

            //Event date compair
            if(adb.getTimeInMilli()<System.currentTimeMillis())
            {
                tv_date.setTextColor(Color.RED);
            }else {
                tv_date.setTextColor(Color.BLACK);
            }

            try {

                String date = new SimpleDateFormat("MMM dd yyyy").format(new SimpleDateFormat("dd/MM/yyyy").parse(adb.getEvtDate().split(",")[0]));

               SimpleDateFormat date24Format= new SimpleDateFormat("HH:mm");
                Date time24Hours = date24Format.parse(adb.getEvtDate().split(",")[1].trim());
                SimpleDateFormat date12Format= new SimpleDateFormat("hh:mm a");

                String timeText=date12Format.format(time24Hours);
                date=date+"\n"+timeText.toUpperCase();
                tv_date.setText(date);
            } catch (ParseException e) {
                e.printStackTrace();
                tv_date.setText(adb.getEvtDate().replace(",","\n"));
            }



            // set value into textview

        }



        return convertView;
    }


}
