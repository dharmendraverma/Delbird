package in.delbird.chemist.CustomViews;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.Button;

/**
 * Created by deepak on 30/1/16.
 */
public class GothamMediumButtonText extends Button {
    public GothamMediumButtonText(Context context) {
        super(context);
    }

    public GothamMediumButtonText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public GothamMediumButtonText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

//    public GothamMediumButtonText(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
//        super(context, attrs, defStyleAttr, defStyleRes);
//    }
    private void init() {
        if (!isInEditMode()) {
            Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "GOTHAM-MEDIUM.TTF");
            setTypeface(tf);
        }
    }
}
