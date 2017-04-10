package kutumblink.appants.com.kutumblink.fragments;


import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import kutumblink.appants.com.kutumblink.HomeActivity;
import kutumblink.appants.com.kutumblink.R;
import kutumblink.appants.com.kutumblink.utils.Constants;
import kutumblink.appants.com.kutumblink.utils.DatabaseHandler;

import static android.app.Activity.RESULT_OK;


/**
 * A simple {@link Fragment} subclass.
 */
public class ContactEmailSentFragment extends BaseFragment implements View.OnClickListener{


    private EditText emailTitle;
    private EditText emailBody;

    public ContactEmailSentFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view=inflater.inflate(R.layout.fragment_contact_email_send, container, false);
        emailTitle = (EditText) view.findViewById(R.id.email_subject_text);
        emailBody =(EditText) view.findViewById(R.id.email_body_text);

        Button saveButton= (Button) view.findViewById(R.id.save_btn_id);

        HomeActivity.tv_title.setText("Contact");
        HomeActivity.ib_back_next.setText("Groups");

        HomeActivity.ib_back_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Constants.NAV_GROUPS=100;
                GroupsMainFragment groupContacts = new GroupsMainFragment(); //New means creating adding.
                FragmentManager fragmentManager = getFragmentManager();
                if(fragmentManager!=null) {
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.main_container, groupContacts);

                    fragmentTransaction.commit();
                }
            }
        });

        saveButton.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View view) {

        if(view.getId()==R.id.save_btn_id)
        {

            if(!(emailTitle.getText().toString().length()>1))
            {
                emailTitle.setError("Invalid Subject");
                return;
            }

            if(!(emailBody.getText().toString().length()>1))
            {
                emailBody.setError("Empty body");
                return;
            }

            Intent email = new Intent(Intent.ACTION_SEND);
            email.putExtra(Intent.EXTRA_EMAIL,new String[] { getString(R.string.email)});
            email.putExtra(Intent.EXTRA_SUBJECT,emailTitle.getText().toString().trim());
            email.putExtra(Intent.EXTRA_TEXT,emailBody.getText().toString().trim());
            //email.putExtra(Intent.EXTRA_TEXT,"sent a message using for testing ");

            email.setType("message/rfc822");

            startActivityForResult(Intent.createChooser(email, "Choose an Email client:"),
                    1);

        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == 1) {
            if(resultCode == RESULT_OK) {
                getActivity().onBackPressed();
                makeToast("Email sent.");

            } else {

                makeToast("Email sent Fail");



            }
        }

    }
}
