package in.delbird.chemist;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;

import com.demach.konotor.Konotor;
import com.demach.konotor.access.K;
import com.google.android.gcm.GCMBaseIntentService;

/**
 * Created by Dharmendra on 19/1/16.
 */
public class GCMIntentService extends GCMBaseIntentService {

    public static final String TAG = GCMIntentService.class.getName();

    public GCMIntentService()
    {
        super(K.ANDROID_PROJECT_SENDER_ID);
    }

    @Override
    protected void onMessage(Context context, Intent intent)
    {
        Konotor.getInstance(context).handleGcmOnMessage(intent);
    }

    @Override
    protected void onError(Context context, String errorId)
    {
        Konotor.getInstance(context).handleGcmOnError(errorId);
    }

    @Override
    protected void onRegistered(Context context, String registrationId)
    {
        Konotor.getInstance(context).handleGcmOnRegistered(registrationId);
    }

    @Override
    protected void onUnregistered(Context context, String registrationId)
    {
        Konotor.getInstance(context).handleGcmOnUnRegistered(registrationId);
    }
    public void sendLocalBroadcast() {
        Intent intent = new Intent("send");
//        int unreadMessageCount = Konotor.getInstance(getApplicationContext()).getUnreadMessageCount();
////        Log.e("unreadMessageCount", String.valueOf(unreadMessageCount));
//        intent.putExtra("unreadMessageCount", String.valueOf(unreadMessageCount));
//        Log.i("unreadMessageCount",String.valueOf(unreadMessageCount));
////        intent.putExtra("latitude", "12.2342342");
////        intent.putExtra("longitude", "12.21124");
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }
}