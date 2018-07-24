package in.delbird.chemist.CustomViews;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by Dharmendra on 17/12/15.
 */
public class GothamBoldTextView extends TextView {

    public GothamBoldTextView(Context context) {
        this(context, null);
    }

    public GothamBoldTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public GothamBoldTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        setTypeface(Typeface.createFromAsset(context.getAssets(), "GOTHAM-BOLD.TTF"));
    }
}
