package kutumblink.appants.com.kutumblink.utils;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by rrallabandi on 3/16/2017.
 */

public class CustomTextView extends android.support.v7.widget.AppCompatTextView {

    public static final String ANDROID_SCHEMA = "http://schemas.android.com/apk/res/android";

    public CustomTextView(Context context, AttributeSet attrs) {
        super(context, attrs);

        applyCustomFont(context, attrs);
    }

    public CustomTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        applyCustomFont(context, attrs);
    }

    private void applyCustomFont(Context context, AttributeSet attrs) {
        int textStyle = attrs.getAttributeIntValue(ANDROID_SCHEMA, "textStyle", Typeface.NORMAL);

        Typeface customFont = selectTypeface(context, textStyle);
        setTypeface(customFont);
    }

    private Typeface selectTypeface(Context context, int textStyle) {
        /*
        * information about the TextView textStyle:
        * http://developer.android.com/reference/android/R.styleable.html#TextView_textStyle
        */
        switch (textStyle) {
            case Typeface.BOLD: // bold
                //return FontCache.getTypeface("SourceSansPro-Bold.ttf", context);
                return  Typeface.createFromAsset(getContext().getAssets(),"trebucbd.ttf");

            case Typeface.ITALIC: // italic
                return  Typeface.createFromAsset(getContext().getAssets(),"trebucit.ttf");
                //return FontCache.getTypeface("SourceSansPro-Italic.ttf", context);

            case Typeface.BOLD_ITALIC: // bold italic
                return  Typeface.createFromAsset(getContext().getAssets(),"trebucbi.ttf");
               // return FontCache.getTypeface("SourceSansPro-BoldItalic.ttf", context);

            case Typeface.NORMAL: // regular
                return  Typeface.createFromAsset(getContext().getAssets(),"trebuc.ttf");
            default:
                return  Typeface.createFromAsset(getContext().getAssets(),"trebuc.ttf");
                //return FontCache.getTypeface("SourceSansPro-Regular.ttf", context);
        }
    }

}
