package kutumblink.appants.com.kutumblink;

import java.io.Serializable;

/**
 * Created by rrallabandi on 3/13/2017.
 */

class MessageBean implements Serializable{
    private String msgTitle=null;

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
