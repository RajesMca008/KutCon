package kutumblink.appants.com.kutumblink.fragments;

import java.io.Serializable;

/**
 * Created by rrallabandi on 3/13/2017.
 */

class MessageBean implements Serializable{
    private String msgTitle=null;

    public String getMsgLink() {
        return msgLink;
    }

    public void setMsgLink(String msgLink) {
        this.msgLink = msgLink;
    }

    private String msgLink=null;

    public String getMsgTitle() {
        return msgTitle;
    }

    public void setMsgTitle(String msgTitle) {
        this.msgTitle = msgTitle;
    }

    public String getMsgId() {
        return msgId;
    }

    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }

    private String msgId;

}
