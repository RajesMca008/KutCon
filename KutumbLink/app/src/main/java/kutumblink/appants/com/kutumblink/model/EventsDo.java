package kutumblink.appants.com.kutumblink.model;

import kutumblink.appants.com.kutumblink.utils.Constants;

/**
 * Created by Vishnu on 22-03-2017.
 */

public class EventsDo {

    private String evtTitle;
    private String evtDesc;
    private String evtContacts;
    private String evtDate;
    private String evtEmail;
    private String evtphone;

    public String getEvtSortOder() {
        return evtSortOder;
    }

    public void setEvtSortOder(String evtSortOder) {
        this.evtSortOder = evtSortOder;
    }

    private String evtSortOder= Constants.DEFAULT;

    public long getTimeInMilli() {
        return timeInMilli;
    }

    public void setTimeInMilli(long timeInMilli) {
        this.timeInMilli = timeInMilli;
    }

    private long timeInMilli;




    public String getEvtphone() {
        return evtphone;
    }

    public void setEvtphone(String evtphone) {
        this.evtphone = evtphone;
    }

    public String getEvtEmail() {
        return evtEmail;
    }

    public void setEvtEmail(String evtEmail) {
        this.evtEmail = evtEmail;
    }

    public String getEvtTitle() {
        return evtTitle;
    }

    public void setEvtTitle(String evtTitle) {
        this.evtTitle = evtTitle;
    }

    public String getEvtDesc() {
        return evtDesc;
    }

    public void setEvtDesc(String evtDesc) {
        this.evtDesc = evtDesc;
    }

    public String getEvtContacts() {
        return evtContacts;
    }

    public void setEvtContacts(String evtContacts) {
        this.evtContacts = evtContacts;
    }

    public String getEvtDate() {
        return evtDate;
    }

    public void setEvtDate(String evtDate) {
        this.evtDate = evtDate;
    }
}
