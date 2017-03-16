package kutumblink.appants.com.kutumblink;


import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Vishnu on 13-03-2016.
 */
/**
 * A simple {@link Fragment} subclass.
 */
public class AddGroupFragment extends Fragment {


    public AddGroupFragment() {
        // Required empty public constructor
    }
    private static final int CONTACT_PICKER_RESULT = 1001;
    public static final int REQUEST_CODE_PICK_CONTACT = 1;
    public static final int  MAX_PICK_CONTACT= 10;
    TextView tv_selectContact;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_add_group, container, false);

        tv_selectContact=(TextView)view.findViewById(R.id.tv_selectContact);
        tv_selectContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              /*  FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.main_container, new SelectContactsFragment()).commit();*/
                Intent i = new Intent(Intent.ACTION_PICK,    ContactsContract.Contacts.CONTENT_URI);//CommonDataKinds.Phone.CONTENT_URI);
                i.putExtra("additional", "phone-multi");
                startActivityForResult(i, CONTACT_PICKER_RESULT);

              /*  Intent phonebookIntent = new Intent("intent.action.INTERACTION_TOPMENU");
                phonebookIntent.putExtra("additional", "phone-multi");

                startActivityForResult(phonebookIntent, 1);*/

            }
        });

        return view;
    }

}
