package in.delbird.chemist.CustomViews;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.EditText;

/**
 * Created by deepak on 20/1/16.
 */
public class GouthamLightEditText extends EditText {


    public GouthamLightEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public GouthamLightEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public GouthamLightEditText(Context context) {
        super(context);
        init();
    }

    private void init() {
        if (!isInEditMode()) {
            Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "GOTHAM-LIGHT.TTF");
            setTypeface(tf);
        }
    }

}
