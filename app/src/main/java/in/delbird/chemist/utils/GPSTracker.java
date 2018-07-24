package in.delbird.chemist.utils;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by deepak on 27/1/16.
 */
public class GPSTracker extends Service implements LocationListener {

    private final Context mContext;
    boolean isGPSEnabled = false;
    boolean isNetworkEnabled = false;
    boolean canGetLocation = false;

    Location location;
    double latitude;
    double longitude;
    protected LocationManager locationManager;

    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10;  //10 meter
    private static final long MIN_TIME_BE_UPDATES = 1000 * 60 * 1; //1 min


    public GPSTracker(Context mContext) {
        this.mContext = mContext;
        location = getLocation();
    }

    public Location getLocation() {
        try {
            locationManager = (LocationManager) mContext.getSystemService(LOCATION_SERVICE);

            //getting gps status
            isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

            isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

            if (!isGPSEnabled && !isNetworkEnabled) {
                Toast.makeText(getApplicationContext(), "Gps not Enabled or Network Not Enabled", Toast.LENGTH_LONG).show();
            } else {
                this.canGetLocation = true;

                //first get get location from network provider
                if (isNetworkEnabled) {
                    if (locationManager != null) {

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                                    || checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

                            }
                        } else {
                            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, MIN_TIME_BE_UPDATES, MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                            Log.d("network", "netwrok");
                            location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                            Log.e("latt longg", String.valueOf(location) + " null");
                            if (location != null) {
                                latitude = location.getLatitude();
                                longitude = location.getLongitude();
                                Log.e("latt long", String.valueOf(location) + " null");
                            }
                        }

                    }


                }
                if (isGPSEnabled) {
                    if (location == null) {
                        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, MIN_TIME_BE_UPDATES, MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                        Log.d("gps enabled", "gps enabled");
                        if (locationManager != null) {
                            location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                            if (location != null) {
                                latitude = location.getLatitude();
                                longitude = location.getLongitude();

                            }
                        }
                    }

                }


            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return location;

    }

    public double getLatitude() {
        if (location != null) {
            latitude = location.getLatitude();
        }
        return latitude;


    }

    public double getLongitude() {
        if (location != null) {
            longitude = location.getLongitude();
        }
        return longitude;

    }

    public boolean canGetLocation() {
        return this.canGetLocation;
    }

    public void showSettingAlert() {
        AlertDialog.Builder alertdialog = new AlertDialog.Builder(mContext);
        alertdialog.setTitle("Gps Setting");
        alertdialog.setMessage("Gps is disabled. Do You Want to go to Settings Menu");
//        alertdialog.setPositiveButton()
        alertdialog.setPositiveButton("Settings", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                mContext.startActivity(intent);
            }
        });

        alertdialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        //show alert message
        alertdialog.show();
    }

    //Stop using gps listener
    public void stopUsingGPS() {
        if (locationManager != null) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            locationManager.removeUpdates(GPSTracker.this);
//            locationManager.removeUpdates(GPSTracker.this);
        }
    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
