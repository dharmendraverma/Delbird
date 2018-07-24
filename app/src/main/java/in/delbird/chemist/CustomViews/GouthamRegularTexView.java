package in.delbird.chemist.CustomViews;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by deepak on 17/12/15.
 */
public class GouthamRegularTexView extends TextView {

    public GouthamRegularTexView(Context context) {
        this(context, null);
    }

    public GouthamRegularTexView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public GouthamRegularTexView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        setTypeface(Typeface.createFromAsset(context.getAssets(), "GOTHAM-LIGHT.OTF"));
    }
}