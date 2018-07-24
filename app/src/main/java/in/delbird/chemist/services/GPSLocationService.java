package in.delbird.chemist.services;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.List;


/**
 * GPSLocationService - fetches the last known location and the updated location via available location
 * providers.
 */
public class GPSLocationService {
    private static final int TWO_MINUTES = 1000 * 60 * 2;
    static Location myLocation;
    LocationManager locationManager;
    LocationListener locationListener;
    OnLocationFetchedListener onLocationFetchedListener;

    public GPSLocationService(Context context, OnLocationFetchedListener onLocationFetchedListener) {
        this.onLocationFetchedListener = onLocationFetchedListener;
        /*if (myLocation == null) {
            myLocation = new Location(LocationManager.NETWORK_PROVIDER);
        }*/
        if (locationManager == null) {
            locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
            // Creating an empty criteria object
            //Criteria criteria = new Criteria();

            // Getting the name of the provider that meets the criteria

            List<String> providers = locationManager.getAllProviders();
            if (providers != null && !providers.isEmpty()) {
                for (String provider : providers) {
                    Location location = locationManager.getLastKnownLocation(provider);
                    if (location != null) {

                   /* myLocation.setLatitude(location.getLatitude());
                    myLocation.setLongitude(location.getLongitude());*/
                        if (isBetterLocation(location, myLocation)) {
                            myLocation = location;


                            if (onLocationFetchedListener != null) {
                                onLocationFetchedListener.onLocationFetched(OnLocationFetchedListener.SOURCE_LAST_LOCATION, true);
                            }
                        }

                    } else {
                        if (onLocationFetchedListener != null) {
                            onLocationFetchedListener.onLocationFetched(OnLocationFetchedListener.SOURCE_LAST_LOCATION, false);
                        }
                    }
                }
            }


            /*String provider = locationManager.getBestProvider(criteria, false);
            if (provider != null && !provider.equals("")) {

                // Get the location from the given provider
                Location location = locationManager.getLastKnownLocation(provider);
                if (location != null) {

                    if(isBetterLocation(location,myLocation)) {
                        myLocation = location;


                        if (onLocationFetchedListener != null) {
                            onLocationFetchedListener.onLocationFetched(OnLocationFetchedListener.SOURCE_LAST_LOCATION, true);
                        }
                    }

                }else{
                    if(onLocationFetchedListener!=null){
                        onLocationFetchedListener.onLocationFetched(OnLocationFetchedListener.SOURCE_LAST_LOCATION,false);
                    }
                }
            }*/
        }

        if (locationListener == null) {
            locationListener = new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {
                    if (location != null) {
                        /*myLocation.setLatitude(location.getLatitude());
                        myLocation.setLongitude(location.getLongitude());*/
                        if (isBetterLocation(location, myLocation)) {
                            myLocation = location;

                            if (GPSLocationService.this.onLocationFetchedListener != null) {
                                GPSLocationService.this.onLocationFetchedListener.onLocationFetched(OnLocationFetchedListener.SOURCE_NEW_LOCATION, true);
                            }
                        }
                    } else {
                        if (GPSLocationService.this.onLocationFetchedListener != null) {
                            GPSLocationService.this.onLocationFetchedListener.onLocationFetched(OnLocationFetchedListener.SOURCE_NEW_LOCATION, false);
                        }
                    }
                }

                @Override
                public void onProviderDisabled(String provider) {
                }

                @Override
                public void onProviderEnabled(String provider) {
                }

                @Override
                public void onStatusChanged(String provider, int status, Bundle extras) {
                }
            };
        }

        /*try {
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
        } catch (Exception e) {
            // TODO: handle exception
        }*/

    }

    public GPSLocationService(Context context) {
        /*super();
        if (myLocation == null) {
            myLocation = new Location(LocationManager.NETWORK_PROVIDER);
        }
        if (locationManager == null) {
            locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
            // Creating an empty criteria object
            Criteria criteria = new Criteria();

            // Getting the name of the provider that meets the criteria
            String provider = locationManager.getBestProvider(criteria, false);

            if (provider != null && !provider.equals("")) {

                // Get the location from the given provider
                Location location = locationManager.getLastKnownLocation(provider);
                if (location != null) {

                    myLocation.setLatitude(location.getLatitude());
                    myLocation.setLongitude(location.getLongitude());

                }
            }
        }

        if (locationListener == null) {
            locationListener = new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {
                    myLocation.setLatitude(location.getLatitude());
                    myLocation.setLongitude(location.getLongitude());
                }

                @Override
                public void onProviderDisabled(String provider) {
                }

                @Override
                public void onProviderEnabled(String provider) {
                }

                @Override
                public void onStatusChanged(String provider, int status, Bundle extras) {
                }
            };
        }

        *//*try {
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
        } catch (Exception e) {
            // TODO: handle exception
        }*//*
*/
        this(context, null);
    }

    public static double getLocLong() {

        try {
            if (myLocation == null)
                return 0;
            return myLocation.getLongitude();
        } catch (Exception e) {
            return 0;
        }
    }

    public static double getLocLat() {

        try {
            if (myLocation == null)
                return 0;
            return myLocation.getLatitude();
        } catch (Exception e) {
            return 0;
        }
    }

    public static Location getMyLocation() {
        return myLocation;
    }

    public void startService() {

        try {
            /*Criteria criteria = new Criteria();

            String provider = locationManager.getBestProvider(criteria, false);

            if (provider != null && !provider.equals("")) {
                Log.d("GeoLocationServiceGPS", "startService Called provider"+provider);

                locationManager.requestLocationUpdates(provider, 0, 0, locationListener);*/
            /*}else {*/
            //Log.d("GeoLocationServiceGPS", "startService Called provider else");
            List<String> providers = locationManager.getAllProviders();
            if (providers != null && !providers.isEmpty()) {
                for (String provider : providers) {
                    //locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
                    locationManager.requestLocationUpdates(provider, 0, 0, locationListener);
                }
            }
            //locationManager.requestLocationUpdates(LocationManager.PASSIVE_PROVIDER, 0, 0, locationListener);
            /*}*/
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setOnLocationFetchedListener(OnLocationFetchedListener onLocationFetchedListener) {
        this.onLocationFetchedListener = onLocationFetchedListener;
    }

    public void removeOnLocationFetchListenerCallbacks() {
        if (onLocationFetchedListener != null)
            onLocationFetchedListener = null;
    }

    public void stopService() {


        try {
            locationManager.removeUpdates(locationListener);
        } catch (Exception e) {

        }
    }

    /**
     * Determines whether one Location reading is better than the current Location fix
     *
     * @param location            The new Location that you want to evaluate
     * @param currentBestLocation The current Location fix, to which you want to compare the new one
     */
    protected boolean isBetterLocation(Location location, Location currentBestLocation) {
        if (currentBestLocation == null) {
            // A new location is always better than no location
            return true;
        }

        // Check whether the new location fix is newer or older
        long timeDelta = location.getTime() - currentBestLocation.getTime();
        boolean isSignificantlyNewer = timeDelta > TWO_MINUTES;
        boolean isSignificantlyOlder = timeDelta < -TWO_MINUTES;
        boolean isNewer = timeDelta > 0;

        // If it's been more than two minutes since the current location, use the new location
        // because the user has likely moved
        if (isSignificantlyNewer) {
            return true;
            // If the new location is more than two minutes older, it must be worse
        } else if (isSignificantlyOlder) {
            return false;
        }

        // Check whether the new location fix is more or less accurate
        int accuracyDelta = (int) (location.getAccuracy() - currentBestLocation.getAccuracy());
        boolean isLessAccurate = accuracyDelta > 0;
        boolean isMoreAccurate = accuracyDelta < 0;
        boolean isSignificantlyLessAccurate = accuracyDelta > 200;

        // Check if the old and new location are from the same provider
        boolean isFromSameProvider = isSameProvider(location.getProvider(),
                currentBestLocation.getProvider());

        // Determine location quality using a combination of timeliness and accuracy
        if (isMoreAccurate) {
            return true;
        } else if (isNewer && !isLessAccurate) {
            return true;
        } else if (isNewer && !isSignificantlyLessAccurate && isFromSameProvider) {
            return true;
        }
        return false;
    }

    /**
     * Checks whether two providers are the same
     */
    private boolean isSameProvider(String provider1, String provider2) {
        if (provider1 == null) {
            return provider2 == null;
        }
        return provider1.equals(provider2);
    }

    public interface OnLocationFetchedListener {
        int SOURCE_LAST_LOCATION = 100;
        int SOURCE_NEW_LOCATION = 101;

        void onLocationFetched(@LocationSource int source, boolean isFetched);

        @IntDef({SOURCE_LAST_LOCATION, SOURCE_NEW_LOCATION})
        @Retention(RetentionPolicy.SOURCE)
        @interface LocationSource {
        }
    }

}// End of the class
