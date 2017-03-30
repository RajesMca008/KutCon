package kutumblink.appants.com.kutumblink.fragments;


import android.content.ContentValues;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Date;

import kutumblink.appants.com.kutumblink.R;
import kutumblink.appants.com.kutumblink.utils.DatabaseHandler;


/**
 * A simple {@link Fragment} subclass.
 */
public class EditMessageFragment extends BaseFragment implements View.OnClickListener {


    private EditText textTitle;
    private EditText textLink;
    private static final String ARG_PARAM1 = "param1";

    public EditMessageFragment() {
        // Required empty public constructor
    }

    public static EditMessageFragment newInstance(String param1)
    {
        EditMessageFragment fragment=new EditMessageFragment();


        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_edit_message, container, false);

          textTitle= (EditText) view.findViewById(R.id.message_title_text);
          textLink=(EditText) view.findViewById(R.id.message_link_text);

        Button saveButton= (Button) view.findViewById(R.id.save_btn_id);


        saveButton.setOnClickListener(this);


        if(getArguments()==null)
        {
            //Not for edit

            activity.setTitle("Create Message");
        }else {
            // Came for Edit

            activity.setTitle("Edit Message");
        }

        return view;
    }

    @Override
    public void onClick(View view) {

        if(view.getId()==R.id.save_btn_id)
        {

            if(!(textTitle.getText().toString().length()>1))
            {
                textTitle.setError(getString(R.string.invalid_title));
                return;
            }

            if(!(Patterns.WEB_URL.matcher(textLink.getText().toString()).matches()))
            {
                textLink.setError(getString(R.string.invalid_url));
                return;
            }

            DatabaseHandler dbHandler=null;
            try {

                dbHandler=new DatabaseHandler(getActivity());

                ContentValues contentValues=new ContentValues();
                contentValues.put(DatabaseHandler.MSG_LINK,textLink.getText().toString().trim());
                contentValues.put(DatabaseHandler.MSG_TITLE,textTitle.getText().toString().trim());


                long insert= dbHandler.insert(DatabaseHandler.TABLE_MESSAGES,contentValues);


                if(insert>0)
                {
                    Toast.makeText(getContext(),getString(R.string.saved_sucess),Toast.LENGTH_LONG).show();
                    getActivity().onBackPressed();
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
