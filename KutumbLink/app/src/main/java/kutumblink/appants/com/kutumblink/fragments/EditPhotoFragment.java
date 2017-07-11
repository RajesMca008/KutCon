package kutumblink.appants.com.kutumblink.fragments;


import android.content.ContentValues;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import kutumblink.appants.com.kutumblink.HomeActivity;
import kutumblink.appants.com.kutumblink.R;
import kutumblink.appants.com.kutumblink.utils.DatabaseHandler;


/**
 * A simple {@link Fragment} subclass.
 */
public class EditPhotoFragment extends BaseFragment implements View.OnClickListener{


    private EditText textTitle;
    private EditText textLink;
    private static final String ARG_PARAM1 = "param1";

    private boolean isCreateNew=true;
    private String msgId;
    public EditPhotoFragment() {
        // Required empty public constructor
    }

    public static EditPhotoFragment newInstance(String param1)
    {
        EditPhotoFragment fragment=new EditPhotoFragment();


        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view=inflater.inflate(R.layout.fragment_edit_photo, container, false);
        textTitle= (EditText) view.findViewById(R.id.photo_title_text);
        textLink=(EditText) view.findViewById(R.id.photo_link_text);

        Button saveButton= (Button) view.findViewById(R.id.save_btn_id);

        HomeActivity.ib_menu.setBackground(null);
        HomeActivity.ib_menu.setText("");
        saveButton.setOnClickListener(this);


        if(getArguments()==null)
        {
            //Not for edit

            activity.setTitle("Create Photo link");
            isCreateNew=true;
        }else {
            // Came for Edit
            saveButton.setText("Update");
            activity.setTitle("Edit Photo link");
            isCreateNew=false;

            msgId= (String) getArguments().get(ARG_PARAM1);
            DatabaseHandler dbHandler=null;
            try {

                dbHandler = new DatabaseHandler(getActivity());

                Cursor c= dbHandler.retriveData("select * from " + DatabaseHandler.TABLE_PHOTOS+ " where "+DatabaseHandler.PHOTO_ID+" =" + msgId+ "");
                Log.i("TEST" ,"::"+c.getCount());


                String title=c.getString(c.getColumnIndex(DatabaseHandler.PHOTO_TITLE));
                String link=c.getString(c.getColumnIndex(DatabaseHandler.PHOTO_LINK));

                textTitle.setText(title);
                textLink.setText(link);
                dbHandler.close();
                if(c!=null)
                    if(!c.isClosed())
                        c.close();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }


        }
        return view;
    }

    @Override
    public void onClick(View view) {

        if(view.getId()==R.id.save_btn_id)
        {

            if(!(textTitle.getText().toString().length()>1))
            {
                showConfirmDialogActions(getString(R.string.app_name),getString(R.string.invalid_title));
                //textTitle.setError(getString(R.string.invalid_title));
                return;
            }

            if(textLink.getText().toString().trim().equals(""))
            {
                //textLink.setError(getString(R.string.enter_link));
                showConfirmDialogActions(getString(R.string.app_name),getString(R.string.enter_link));
                return;
            }

            //if(!(Patterns.WEB_URL.matcher(textLink.getText().toString()).matches())   || !(textLink.getText().toString().contains("http")))
            if(!(textLink.getText().toString().contains("https://") || textLink.getText().toString().contains("http://")))
            {
                //textLink.setError(getString(R.string.invalid_url));
                showConfirmDialogActions(getString(R.string.app_name),getString(R.string.invalid_url));
                return;
            }

            DatabaseHandler dbHandler=null;
            try {

                dbHandler=new DatabaseHandler(getActivity());

                ContentValues contentValues=new ContentValues();
                contentValues.put(DatabaseHandler.PHOTO_LINK,textLink.getText().toString().trim());
                contentValues.put(DatabaseHandler.PHOTO_TITLE,textTitle.getText().toString().trim());

                if(isCreateNew) {

                    long insert = dbHandler.insert(DatabaseHandler.TABLE_PHOTOS, contentValues);


                    if (insert > 0) {
                        //  Toast.makeText(getContext(),getString(R.string.saved_sucess),Toast.LENGTH_LONG).show();
                        showConfirmDialog(getString(R.string.app_name), getString(R.string.saved_sucess),true);

                    }
                }
                else {

                    long insert = dbHandler.UpdateTable(DatabaseHandler.TABLE_PHOTOS,contentValues,DatabaseHandler.PHOTO_ID+ " = "+msgId);


                    if (insert > 0) {
                        //Toast.makeText(getContext(),getString(R.string.saved_sucess),Toast.LENGTH_LONG).show();
                        showConfirmDialog(getString(R.string.app_name), getString(R.string.updated_sucess),true);
                        //getActivity().onBackPressed();
                    }
                }
                dbHandler.close();
            }
            catch (Exception e)
            {
                if(dbHandler!=null)
                    dbHandler.close();

            }

        }
    }
}
