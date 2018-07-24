package in.delbird.chemist.CustomViews;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by deepak on 18/12/15.
 */
public class GothumMediumTextView extends TextView {

    public GothumMediumTextView(Context context) {
        this(context, null);
    }

    public GothumMediumTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public GothumMediumTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        setTypeface(Typeface.createFromAsset(context.getAssets(), "GOTHAM-MEDIUM.TTF"));
    }
}