package in.delbird.chemist.CustomViews;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.Button;

/**
 * Created by deepak on 30/1/16.
 */
public class GothamLightButtonText extends Button {
    public GothamLightButtonText(Context context) {
        super(context);
    }

    public GothamLightButtonText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public GothamLightButtonText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    private void init() {
        if (!isInEditMode()) {
            Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "GOTHAM-LIGHT.TTF");
            setTypeface(tf);
        }
    }
}
