package in.delbird.chemist.services;

import android.app.Service;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;

/**
 * GeoLocationService - runs as an android service and can be bounded as well.
 * Fetches the user location coordinates using @See GPSLocationService in the background
 */
public class GeoLocationService extends Service implements GPSLocationService.OnLocationFetchedListener {

    private GeoLocationServiceBinder geoLocationServiceBinder = new GeoLocationServiceBinder();
    GPSLocationService gpsLocationService;
    boolean isServiceStarted;
    int startId;
    Runnable mRunnable;
    Handler handler;

    private static final int TIME_DIFFERENCE = 1000 * 60 * 15; //15 minutes

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return geoLocationServiceBinder;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        gpsLocationService = new GPSLocationService(this, this);
        isServiceStarted = false;
        handler = new Handler();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        this.startId = startId;
        startGeoLocationServiceifNecessary(false);
        return START_NOT_STICKY;
    }

    public void startGeoLocationServiceifNecessary(boolean isBound) {
        if (gpsLocationService != null) {
            Location location = GPSLocationService.getMyLocation();
            long timeDiff = 0;
            if (location != null) {
                timeDiff = System.currentTimeMillis() - location.getTime();
            }
            if (location == null || timeDiff > TIME_DIFFERENCE || !(location.getProvider().equalsIgnoreCase(LocationManager.NETWORK_PROVIDER) || location.getProvider().equalsIgnoreCase(LocationManager.GPS_PROVIDER))) {
                if (!isServiceStarted) {
                    gpsLocationService.setOnLocationFetchedListener(this);
                    gpsLocationService.startService();
                    isServiceStarted = true;
                    if (handler == null) {
                        handler = new Handler();
                    }
                    if (mRunnable != null) {
                        handler.removeCallbacks(mRunnable);
                    }
                    if (isBound) {
                        mRunnable = null;
                    } else {
                        mRunnable = new Runnable() {
                            @Override
                            public void run() {
                                if (gpsLocationService != null) {
                                    isServiceStarted = false;
                                    gpsLocationService.stopService();
                                    gpsLocationService.removeOnLocationFetchListenerCallbacks();
                                }
                                //stopService(intent);
                                stopSelf();
                            }
                        };
                        handler.postDelayed(mRunnable, 120000);
                    }
                }
            } else {


                stopSelf();
            }
        }
    }


    @Override
    public void onDestroy() {

        if (mRunnable != null && handler != null) {
            handler.removeCallbacks(mRunnable);
            mRunnable = null;
            handler = null;
        }
        if (gpsLocationService != null) {
            gpsLocationService.removeOnLocationFetchListenerCallbacks();
            gpsLocationService = null;
        }
        super.onDestroy();
    }

    @Override
    public boolean onUnbind(Intent intent) {

        if (gpsLocationService != null && isServiceStarted && mRunnable == null) {
            isServiceStarted = false;
            gpsLocationService.stopService();
            gpsLocationService.removeOnLocationFetchListenerCallbacks();
        }
        return false;
    }

    @Override
    public void onLocationFetched(@LocationSource int source, boolean isFetched) {
        if (source == SOURCE_NEW_LOCATION && isFetched) {
            if (gpsLocationService != null) {
                isServiceStarted = false;
                gpsLocationService.stopService();
                gpsLocationService.removeOnLocationFetchListenerCallbacks();
            }
            stopSelf();
        }
    }

    public class GeoLocationServiceBinder extends Binder {

        public GeoLocationService getGeoLocationService() {
            return GeoLocationService.this;
        }
    }
}
