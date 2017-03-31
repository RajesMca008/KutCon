package kutumblink.appants.com.kutumblink.model;

/**
 * Created by Vishnu on 22-03-2017.
 */

public class ContactsDo {


    private String conatactId;
    private String conatactName;
    private String conatactPhone;
    private String conatactGroupName;
    private String conatactPIC;

    private int IS_CONTACT_SELECTED;


    public int getIS_CONTACT_SELECTED() {
        return IS_CONTACT_SELECTED;
    }

    public void setIS_CONTACT_SELECTED(int IS_CONTACT_SELECTED) {
        this.IS_CONTACT_SELECTED = IS_CONTACT_SELECTED;
    }

    public String getConatactId() {
        return conatactId;
    }

    public void setConatactId(String conatactId) {
        this.conatactId = conatactId;
    }

    public String getConatactName() {
        return conatactName;
    }

    public void setConatactName(String conatactName) {
        this.conatactName = conatactName;
    }

    public String getConatactPhone() {
        return conatactPhone;
    }

    public void setConatactPhone(String conatactPhone) {
        this.conatactPhone = conatactPhone;
    }

    public String getConatactGroupName() {
        return conatactGroupName;
    }

    public void setConatactGroupName(String conatactGroupName) {
        this.conatactGroupName = conatactGroupName;
    }

    public String getConatactPIC() {
        return conatactPIC;
    }

    public void setConatactPIC(String conatactPIC) {
        this.conatactPIC = conatactPIC;
    }
}
