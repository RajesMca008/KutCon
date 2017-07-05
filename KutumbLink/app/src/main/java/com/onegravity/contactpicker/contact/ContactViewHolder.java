package com.onegravity.contactpicker.contact;

import android.content.Context;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.onegravity.contactpicker.Helper;
import com.onegravity.contactpicker.core.ContactPickerActivity;
import com.onegravity.contactpicker.picture.ContactBadge;
import com.onegravity.contactpicker.picture.ContactPictureManager;
import com.onegravity.contactpicker.picture.ContactPictureType;

import kutumblink.appants.com.kutumblink.R;

public class ContactViewHolder extends RecyclerView.ViewHolder {

    private View mRoot;
    private TextView mName;
    private TextView mDescription;
    private ContactBadge mBadge;
    private CheckBox mSelect;

    private ContactPictureType mContactPictureType=null;
    private ContactDescription mContactDescription=null;
    private int mContactDescriptionType=0;
    private ContactPictureManager mContactPictureLoader=null;

    private Integer mSelectedList[]=null;

    ContactViewHolder(View root, ContactPictureManager contactPictureLoader, ContactPictureType contactPictureType,
                      ContactDescription contactDescription, int contactDescriptionType, Context mContext) {
        super(root);

        mRoot = root;
        mName = (TextView) root.findViewById(R.id.name);
        mDescription = (TextView) root.findViewById(R.id.description);
        mBadge = (ContactBadge) root.findViewById(R.id.contact_badge);
        mSelect = (CheckBox) root.findViewById(R.id.select);

        mContactPictureType = contactPictureType;
        mContactDescription = contactDescription;
        mContactDescriptionType = contactDescriptionType;
        mContactPictureLoader = contactPictureLoader;

        mBadge.setBadgeType(mContactPictureType);

        mSelectedList= (Integer[]) ((ContactPickerActivity)mContext).getIntent().getExtras().get(ContactPickerActivity.SELECTED_CONTACTS);

        Log.i("TEST","::"+mSelectedList);
    }

    void bind(final Contact contact) {
        mRoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSelect.toggle();
            }
        });

        // main text / title
        mName.setText(contact.getDisplayName());

        // description
        String description = "";
        switch (mContactDescription) {
            case EMAIL:
                description = contact.getEmail(mContactDescriptionType);
                break;
            case PHONE:
                description = contact.getPhone(mContactDescriptionType);
                break;
            case ADDRESS:
                description = contact.getAddress(mContactDescriptionType);
                break;
        }
        mDescription.setText(description);
        mDescription.setVisibility( Helper.isNullOrEmpty(description) ? View.GONE : View.VISIBLE );

        // contact picture
        if (mContactPictureType == ContactPictureType.NONE) {
            mBadge.setVisibility(View.GONE);
        }
        else {
            mContactPictureLoader.loadContactPicture(contact, mBadge);
            mBadge.setVisibility(View.VISIBLE);

            String lookupKey = contact.getLookupKey();
            if (lookupKey != null) {
                Uri contactUri = Uri.withAppendedPath(ContactsContract.Contacts.CONTENT_LOOKUP_URI, lookupKey);
                mBadge.assignContactUri(contactUri);
            }
        }

        // check box
        mSelect.setOnCheckedChangeListener(null);


        if(mSelectedList!=null)
        {
            for (int i=0;i<mSelectedList.length;i++)
            {
                if(contact.getId()==mSelectedList[i] && mSelectedList[i]!=null)
                {
                    mSelect.setChecked(true );
                }
                else {
                    mSelect.setChecked( contact.isChecked() );
                }
            }
        }else {

            mSelect.setChecked( contact.isChecked() );
        }


        mSelect.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                contact.setChecked(isChecked, false);
            }
        });
    }

    void onRecycled() {
        mBadge.onDestroy();
    }

}
