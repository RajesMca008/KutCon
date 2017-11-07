package kutumblink.appants.com.kutumblink.fragments;

import android.net.Uri;

/**
 * Created by rrallabandi on 10/11/2017.
 */

class TempContactObj {

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Uri getPhotUri() {
        return photUri;
    }

    public void setPhotUri(Uri photUri) {
        this.photUri = photUri;
    }

    private String displayName="";
    private String firstName="";
    private String lastName="";
    private String phone="";
    private String email="";
    private Uri photUri;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    private long id=0;

}
