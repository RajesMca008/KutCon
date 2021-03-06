package kutumblink.appants.com.kutumblink.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.Nullable;
import android.util.Base64;

import java.io.ByteArrayOutputStream;

/**
 * Created by Vishnu on 22-03-2017.
 */

public class Constants {

    public static final String PRF_FILE_NAME = "user_prf";
    public static final String DEFAULT = "Default";
    public static final String BY_FIRST_NAME = "By First Name";
    public static final String BY_LAST_NAME = "By Last Name";

    public static final int GROUP_DELETE=1;
    public static final int EVENTS_DELETE=2;
  //  public static final int GROUP_DELETE=1;
   // public static final int GROUP_DELETE=1;

    public static final boolean needToShowAdd=true;


    public static String imgID = "0";
    public static String GROUP_NAME = "";
    public static String GROUP_NAMECI = "";
    public static int RESULT = 0;

    public static String GROUP_NAMEP = "";

  //  public static String CONV_BM = "";


    public static String Event_NAME = "";
    public static String GROUP_OPERATIONS = "SAVE";
    public static String EVENT_OPERATIONS = "SAVE";
    public static String GROUP_OLD_NAME = "";
    public static String EVENTS_OLD_NAME = "";
    public static int NAV_GROUPS = 100;
    public static Boolean isGROUP_NOT_EXPAND = true;
    public static String SortOrderValue = Constants.DEFAULT;
    public static int GROUP_CONTACTS_SIZE = 0;

    public static String strDT = "";

    public static String bitMapToString(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] b = baos.toByteArray();
        String temp = Base64.encodeToString(b, Base64.DEFAULT);
        return temp;
    }
    @Nullable
    public static Bitmap stringToBitMap(String encodedString) {
        try {
            byte[] encodeByte = Base64.decode(encodedString, Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0,
                    encodeByte.length);
            return bitmap;
        } catch (Exception e) {
            e.getMessage();
            return null;
        }
    }

}
