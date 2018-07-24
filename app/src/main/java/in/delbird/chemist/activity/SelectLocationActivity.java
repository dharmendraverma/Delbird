package in.delbird.chemist.activity;

import android.Manifest;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.demach.konotor.Konotor;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;

import java.text.DateFormat;
import java.util.Date;

import butterknife.ButterKnife;
import butterknife.OnClick;
import in.delbird.chemist.Controller.Constants;
import in.delbird.chemist.R;
import in.delbird.chemist.adapters.PlaceAutocompleteAdapter;


/**
 * Created by Dharmendra on 1/21/16.
 */
public class SelectLocationActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, View.OnClickListener,
        LocationListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
    private static final long INTERVAL = 1000 * 2;
    private static final long FASTEST_INTERVAL = 1000 * 1;
    private static final String TAG = "BaseActivity";
    private static final LatLngBounds BOUNDS_INDIA = new LatLngBounds(
            new LatLng(8.1897416147439, 65.09399374999998), new LatLng(32.657875116336676, 100.25024374999998));
    public static double lat = 28.5707, lng = 77.3261;
    LocationRequest mLocationRequest;
    GoogleApiClient mGoogleApiClient;
    Location mCurrentLocation;
    String mLastUpdateTime;
    private ImageView chat_IV, cart_IV;
    private IntentFilter konotorIntentFilter;
    AutoCompleteTextView from;
    LocationManager locationManager;
    PlaceAutocompleteAdapter searchAdapter;
    ImageView imageView;
    private Toolbar toolbar;
    private TextView mTitle;
    TextView chatCountTV, emailTV;
    private GoogleApiClient googleApiClient;
    private String startAddress = "", why = "";
    private SharedPreferences preferences;
    LinearLayout mSearchProductLayout;
    private BroadcastReceiver konotor_broadcastReceiver = new BroadcastReceiver() {


        @Override
        public void onReceive(Context context, Intent intent) {

            int unreadMessageCount = Konotor.getInstance(getApplicationContext()).getUnreadMessageCount();
            if (unreadMessageCount != 0) {
                chatCountTV.setVisibility(View.VISIBLE);
                chatCountTV.setText(String.valueOf(unreadMessageCount));
            }
        }
    };
    //private ImageButton call, addParcel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);




        try {


            //show error dialog if GoolglePlayServices not available
            if (!isGooglePlayServicesAvailable()) {
                finish();
            }
            createLocationRequest();
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addApi(LocationServices.API)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .build();
            mGoogleApiClient.connect();
        } catch (Exception e) {
            e.printStackTrace();
        }
        setContentView(R.layout.select_address);
        ButterKnife.bind(this);

        try {
            googleApiClient = new GoogleApiClient
                    .Builder(this)
                    .addApi(Places.GEO_DATA_API)
                    .addApi(Places.PLACE_DETECTION_API)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .build();
            googleApiClient.connect();
            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        } catch (Exception e) {
            e.printStackTrace();
        }


        toolbar = (Toolbar) findViewById(R.id.toolbar);
        mTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
        mTitle.setVisibility(View.VISIBLE);
        mTitle.setText("Set Your Location");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        chatCountTV = (TextView) findViewById(R.id.chat_count);
        konotorIntentFilter = new IntentFilter("Konotor_UnreadMessageCountChanged");
        konotorIntentFilter = new IntentFilter("Konotor_UnreadMessageCountChanged");
        LocalBroadcastManager.getInstance(getApplicationContext()).registerReceiver(konotor_broadcastReceiver, konotorIntentFilter);
        chat_IV = (ImageView) findViewById(R.id.image_chat);
        cart_IV = (ImageView) findViewById(R.id.image_cart);
        cart_IV.setVisibility(View.INVISIBLE);
        chat_IV.setVisibility(View.GONE);
        mSearchProductLayout = (LinearLayout) findViewById(R.id.search_product_Layout);
        mSearchProductLayout.setVisibility(View.INVISIBLE);
        //discout_IV=(ImageView)findViewById(R.id.image_discount);
        chat_IV.setOnClickListener(this);
        cart_IV.setOnClickListener(this);


        from = (AutoCompleteTextView) findViewById(R.id.pickup_location);
        imageView = (ImageView) findViewById(R.id.clear);
        //call = (ImageButton) findViewById(R.id.call);
        //addParcel = (ImageButton) findViewById(R.id.add_parcel);
        searchAdapter = new PlaceAutocompleteAdapter(getApplicationContext(), R.layout.spinner_item, googleApiClient, BOUNDS_INDIA, null);
        from.setAdapter(searchAdapter);
        from.setOnItemClickListener(this);
        imageView.setOnClickListener(this);
        Intent intent = this.getIntent();
        if (intent != null) {
            why = intent.getStringExtra("from");
            if (why != null) {
                if (why.equalsIgnoreCase("Home")) {
                    String address = intent.getStringExtra("pickupAddress");
                    if (address.length() > 0) {
                        from.setText(address);
                        from.setSelection(address.length());
                    }
                } else if (why.equalsIgnoreCase("RequestingBike")) {
                    from.setHint("Destination Address");
                    String address = intent.getStringExtra("pickupAddress");
                    if (address.length() > 0) {
                        from.setText(address);
                        from.setSelection(address.length());
                    }
                } else if (why.equalsIgnoreCase("addParcel")) {
                    from.setHint("Destination Address");
                    String address = intent.getStringExtra("pickupAddress");
                    if (address.length() > 0) {
                        from.setText(address);
                        from.setSelection(address.length());
                    }
                }
            }

        }

        preferences = getSharedPreferences(Constants.SHAREDPREFERENCES_NAME, MODE_PRIVATE);

    }



    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        startAddress = parent.getItemAtPosition(position).toString();
        SharedPreferences.Editor editor =preferences.edit();
                editor.putString(Constants.MY_LOCATION_NAME,startAddress);
        editor.putBoolean(Constants.CURRENT_LOCATION,false);
        editor.commit();
        finish();
        /*Show.showLog("Pick up location", startAddress);
        InputMethodManager in = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        in.hideSoftInputFromWindow(from.getWindowToken(), 0);

        if (why.equalsIgnoreCase("Home")) {
            Intent intent = new Intent(SelectLocation.this, HomeScreen.class);
            intent.putExtra("selectedLocation", startAddress);
            setResult(101, intent);
            finish();
        } else if (why.equalsIgnoreCase("RequestingBike")) {
            Intent intent = new Intent(SelectLocation.this, HomeScreen.class);
            intent.putExtra("selectedLocation", startAddress);
            setResult(101, intent);
            finish();
        } else if (why.equalsIgnoreCase("addParcel")) {
            Intent intent = new Intent(SelectLocation.this, HomeScreen.class);
            intent.putExtra("selectedLocation", startAddress);
            setResult(101, intent);
            finish();
        }*/
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return true;

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.clear) {
            from.setText("");
        }
             if (id==R.id.image_chat){
                Konotor.getInstance(getApplicationContext())
                        .launchFeedbackScreen(SelectLocationActivity.this);
                chatCountTV.setVisibility(View.INVISIBLE);

        }
    }


    private boolean isGooglePlayServicesAvailable() {
        int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
        if (ConnectionResult.SUCCESS == status) {
            return true;
        } else {
            GooglePlayServicesUtil.getErrorDialog(status, this, 0).show();
            return false;
        }
    }

    protected void createLocationRequest() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(INTERVAL);
        mLocationRequest.setFastestInterval(FASTEST_INTERVAL);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    @Override
    public void onConnected(Bundle bundle) {
        startLocationUpdates();
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {
        Log.d(TAG, "Firing onLocationChanged..............................................");
        mCurrentLocation = location;
        mLastUpdateTime = DateFormat.getTimeInstance().format(new Date());
        updateUI();


    }

    public void updateUI() {
        Log.d(TAG, "UI update initiated .............");
        if (null != mCurrentLocation) {
            lat = mCurrentLocation.getLatitude();
            lng = mCurrentLocation.getLongitude();

            if (Double.compare(lat, 0.0) == 0 && Double.compare(lng, 0.0) == 0) {
                lat = 28.5707;
                lng = 77.3261;
            }

//
//
//            ShowToast.temporaryToast(getApplicationContext(), "At Time: " + mLastUpdateTime + "\n" +
//                    "Latitude: " + lat + "\n" +
//                    "Longitude: " + lng + "\n" +
//                    "Accuracy: " + mCurrentLocation.getAccuracy() + "\n" +
//                    "Provider: " + mCurrentLocation.getProvider());

            Log.d("Location", "At Time: " + mLastUpdateTime + "\n" +
                    "Latitude: " + lat + "\n" +
                    "Longitude: " + lng + "\n" +
                    "Accuracy: " + mCurrentLocation.getAccuracy() + "\n" +
                    "Provider: " + mCurrentLocation.getProvider());


        } else {
            Log.d(TAG, "location is null ...............");
        }
    }

    protected void startLocationUpdates() {
        try {


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
            PendingResult<Status> pendingResult = LocationServices.FusedLocationApi.requestLocationUpdates(
                    mGoogleApiClient, mLocationRequest, this);
            Log.d(TAG, "Location update started ..............: ");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
        Log.d(TAG, "onStart fired ..............");

    }

    @Override
    public void onStop() {
        super.onStop();

        Log.d(TAG, "onStop fired ..............");
//        mGoogleApiClient.disconnect();
        Log.d(TAG, "isConnected ...............: " + mGoogleApiClient.isConnected());
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mGoogleApiClient.isConnected()) {
            startLocationUpdates();
            Log.d(TAG, "Location update resumed .....................");
        }
        String ns = Context.NOTIFICATION_SERVICE;
        NotificationManager nMgr = (NotificationManager) getApplicationContext().getSystemService(ns);
        nMgr.cancel(0);
    }

    @OnClick(R.id.current_location)
    public void onClick() {
        SharedPreferences.Editor editor =preferences.edit();
        editor.putString(Constants.MY_LOCATION_NAME,"");
        editor.putBoolean(Constants.CURRENT_LOCATION,true);
        editor.commit();
        finish();
    }
}
