package kutumblink.appants.com.kutumblink;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class EditPhotoFragment extends Fragment implements View.OnClickListener{


    private EditText textTitle;
    private EditText textLink;

    public EditPhotoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view=inflater.inflate(R.layout.fragment_edit_photo, container, false);
        textTitle= (EditText) view.findViewById(R.id.photo_title_text);
        textLink=(EditText) view.findViewById(R.id.photo_link_text);

        Button saveButton= (Button) view.findViewById(R.id.save_btn_id);


        saveButton.setOnClickListener(this);
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


            Toast.makeText(getContext(),"Valid url",Toast.LENGTH_LONG).show();

        }
    }
}
