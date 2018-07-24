package in.delbird.chemist.utils;

import android.content.Context;
import android.content.Intent;

/**
 * Created by deepak on 30/1/16.
 */
public class CommonUtility {

    public void getValueFromSharedPreference()
    {

    }
    public void call_intent(Context context, Class nextActivityClass )
    {
        Intent intent = new Intent(context,nextActivityClass);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);

    }
}
