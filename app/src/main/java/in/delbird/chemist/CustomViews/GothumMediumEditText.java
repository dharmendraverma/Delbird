package in.delbird.chemist.CustomViews;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.EditText;

/**
 * Created by Dharmendra on 18/12/15.
 */
public class GothumMediumEditText extends EditText {


        public GothumMediumEditText(Context context, AttributeSet attrs, int defStyle) {
            super(context, attrs, defStyle);
            init();
        }

        public GothumMediumEditText(Context context, AttributeSet attrs) {
            super(context, attrs);
            init();
        }

        public GothumMediumEditText(Context context) {
            super(context);
            init();
        }

        private void init() {
            if (!isInEditMode()) {
                Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "GOTHAM-MEDIUM.TTF");
                setTypeface(tf);
            }
        }

    }

