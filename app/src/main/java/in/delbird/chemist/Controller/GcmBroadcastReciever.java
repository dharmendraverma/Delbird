package in.delbird.chemist.Controller;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.util.Log;

import com.demach.konotor.Konotor;
import com.google.android.gms.gcm.GoogleCloudMessaging;

/**
 * Created by deepak on 23/1/16.
 */
public class GcmBroadcastReciever extends WakefulBroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {



        if (GoogleCloudMessaging.MESSAGE_TYPE_MESSAGE.equals(intent))
        {
            if(Konotor.getInstance(context).isKonotorMessage(intent))
            {
                Konotor.getInstance(context).handleGcmOnMessage(intent);
                Log.e("Working","hi");
            }
            else
            {
                // application specific code
            }
        }




//        ComponentName comp = new ComponentName(context.getPackageName(),GCMIntentService.class.getName());
//        startWakefulService(context, (intent.setComponent(comp)));
//        setResultCode(Activity.RESULT_OK);
    }
}