package in.delbird.chemist.CustomViews;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by Dharmendra on 20/1/16.
 */
public class GothamLightTextView extends TextView {

    public GothamLightTextView(Context context) {
        this(context, null);
    }

    public GothamLightTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public GothamLightTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        setTypeface(Typeface.createFromAsset(context.getAssets(), "GOTHAM-LIGHT.TTF"));
    }
}