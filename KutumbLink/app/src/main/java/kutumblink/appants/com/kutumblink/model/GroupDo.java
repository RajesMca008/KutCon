package kutumblink.appants.com.kutumblink.model;

/**
 * Created by Vishnu on 22-03-2017.
 */

public class GroupDo {

    private String group_ID;
    private String group_Name;
    private int group_Pic;
    private String group_totalContactList;
    private int group_isSELECT;

    public int getGroup_isSELECT() {
        return group_isSELECT;
    }

    public void setGroup_isSELECT(int group_isSELECT) {
        this.group_isSELECT = group_isSELECT;
    }

    public int getGroup_Pic() {
        return group_Pic;
    }

    public void setGroup_Pic(int group_Pic) {
        this.group_Pic = group_Pic;
    }

    public String getGroup_ID() {
        return group_ID;
    }

    public void setGroup_ID(String group_ID) {
        this.group_ID = group_ID;
    }

    public String getGroup_Name() {
        return group_Name;
    }

    public void setGroup_Name(String group_Name) {
        this.group_Name = group_Name;
    }



    public String getGroup_totalContactList() {
        return group_totalContactList;
    }

    public void setGroup_totalContactList(String group_totalContactList) {
        this.group_totalContactList = group_totalContactList;
    }
}
