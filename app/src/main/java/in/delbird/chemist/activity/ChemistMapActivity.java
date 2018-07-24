package in.delbird.chemist.activity;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.demach.konotor.Konotor;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.GsonBuilder;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import in.delbird.chemist.Controller.AppController;
import in.delbird.chemist.Controller.Constants;
import in.delbird.chemist.R;
import in.delbird.chemist.models.RetailerModel;
import in.delbird.chemist.utils.DialogUtil;

/**
 * Created by Dharmendra on 11/10/2015.
 */
public class ChemistMapActivity extends AppCompatActivity implements OnMapReadyCallback, View.OnClickListener {
    private static final String SAVED_INSTANCE_HOTEL_LIST_DATA = "hotelListData";
    GoogleMap googleMap;
    List<RetailerModel> hotelMapArrayList;
    ProgressBar progressBar;
    boolean loadingMessage = false;
    DialogUtil dialogUtil = new DialogUtil();
    private HashMap<String, RetailerModel> markersHashMap;
    private SharedPreferences sharedPreferences;
    private Toolbar mToolbar;
    private TextView mTitle;
    private ImageView chat_IV, cart_IV;
    private TextView mChemistList;
    LocationManager locationManager;
    TextView chatCountTV, emailTV;
    private IntentFilter konotorIntentFilter;
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
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_chemist_map_view_screen);
        sharedPreferences = getSharedPreferences(Constants.SHAREDPREFERENCES_NAME, MODE_PRIVATE);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mTitle = (TextView) mToolbar.findViewById(R.id.toolbar_title);
        mTitle.setVisibility(View.VISIBLE);
        mTitle.setText("Select Your Chemist");

        hotelMapArrayList = Collections.emptyList();
        initializeLayout();
        initMapMarkerClickListener();

        progressBar.setVisibility(View.VISIBLE);
        progressBar.setIndeterminate(true);


        chatCountTV = (TextView) findViewById(R.id.chat_count);
        konotorIntentFilter = new IntentFilter("Konotor_UnreadMessageCountChanged");
        konotorIntentFilter = new IntentFilter("Konotor_UnreadMessageCountChanged");
        LocalBroadcastManager.getInstance(getApplicationContext()).registerReceiver(konotor_broadcastReceiver, konotorIntentFilter);
        chat_IV = (ImageView) findViewById(R.id.image_chat);
        cart_IV = (ImageView) findViewById(R.id.image_cart);
        cart_IV.setVisibility(View.INVISIBLE);
        chat_IV.setVisibility(View.INVISIBLE);
        mSearchProductLayout=(LinearLayout)findViewById(R.id.search_product_Layout);
        mSearchProductLayout.setVisibility(View.GONE);

        mChemistList=(TextView)findViewById(R.id.chemist_list);
        mChemistList.setOnClickListener(this);
        //discout_IV=(ImageView)findViewById(R.id.image_discount);
        chat_IV.setOnClickListener(this);
        cart_IV.setOnClickListener(this);
        Toast.makeText(getApplicationContext(), "Tap To Select Chemist", Toast.LENGTH_LONG).show();

        //refreshMapMarkers();
    }// end of onCreate


    private void refreshMapMarkers() {
        googleMap.clear();
        //googleMap.setMyLocationEnabled(true);
        markersHashMap.clear();
        setMarkersInMap(hotelMapArrayList.size());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void initMapMarkerClickListener() {
        if (googleMap != null) {
            googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                @Override
                public boolean onMarkerClick(final Marker marker) {
                    marker.showInfoWindow();
                    /*Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                marker.showInfoWindow();
                            } catch (Exception e) {
                                Utility.printStackTrace(e);
                            }
                        }
                    }, 2000);*/
                    return true;
                }
            });
        }
    }

    private void setMarkersInMap(final int sizeOfList) {
        if(sizeOfList!=0)
        {
            String latlong[] = hotelMapArrayList.get(0).getLatlong().split(",");
            plotMarkers(0, sizeOfList);
            progressBar.setVisibility(View.GONE);
            googleMap.getUiSettings().setZoomControlsEnabled(false);

            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(Double.valueOf(latlong[0]), Double.valueOf(latlong[1])), 10));

            googleMap.animateCamera(CameraUpdateFactory.zoomTo(10), 2000, null);
        }


    }

    private void plotMarkers(int initial, int size) {
        if (hotelMapArrayList != null) {
            try {
                Bitmap markerBitmap = getViewBitmap();

                for (int i = initial; i < size; i++) {
                    String latlong[] = hotelMapArrayList.get(i).getLatlong().split(",");

                    MarkerOptions markerOption = new MarkerOptions().position(new LatLng(Double.valueOf(latlong[0]), Double.valueOf(latlong[1])))
                            .icon(BitmapDescriptorFactory.fromBitmap(markerBitmap));
                    //googleMap.addMarker(markerOption);
                    markersHashMap.put(googleMap.addMarker(markerOption).getId(), hotelMapArrayList.get(i));

                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }// end of plotMarkers

    private Bitmap getViewBitmap() {
        /*View view = getLayoutInflater().inflate(R.layout.layout_hotel_map_marker_view, null);
        if (view.getMeasuredHeight() <= 0) {
            view.measure(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            Bitmap b = Bitmap.createBitmap(view.getMeasuredWidth(), view.getMeasuredHeight(), Bitmap.Config.ARGB_8888);
            Canvas c = new Canvas(b);
            view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
            view.draw(c);
            return b;
        }
        Bitmap b = Bitmap.createBitmap(view.getLayoutParams().width, view.getLayoutParams().height, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(b);
        view.layout(view.getLeft(), view.getTop(), view.getRight(), view.getBottom());
        view.draw(c);*/
        Bitmap b = BitmapFactory.decodeResource(getResources(), R.drawable.location);
        return b;
    }

    @Override
    protected void onResume() {
        super.onResume();
        getCategory();
    }

    private void initializeLayout() {
        ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.hotel_map_screen_map_view_fragment)).getMapAsync(this);
        progressBar = (ProgressBar) findViewById(R.id.map_view_progress_bar);
        markersHashMap = new HashMap<>();

    }// end of initializeLayout


    protected void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putBoolean("loadingMessage", loadingMessage);
        savedInstanceState.putSerializable(SAVED_INSTANCE_HOTEL_LIST_DATA, (Serializable) hotelMapArrayList);
    }


    private void manageMapInfoWindowClicks() {
        googleMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                /*HotelInformationViewModel hotelInformation = markersHashMap.get(marker.getId());
                hotelDetailsCriteria = HotelDetailsMapper.mapViewModelToCriteria(hotelInformation);
                loadingMessage = true;
                getLoadingMessage();
                hotelBookingCriteria.setHotelDetailsCriteria(hotelDetailsCriteria);
                hotelBookingCriteria.setSelectedHotel(hotelInformation);
                AbstractMediator.launchMediator(new HotelDetailsMediator(instanceBaseController), hotelBookingCriteria, true);*/
            }
        });

        googleMap.setInfoWindowAdapter(new MarkerInfoWindowAdapter());

    }// end of manageMapInfoWindowClicks


    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
        manageMapInfoWindowClicks();
        refreshMapMarkers();
    }


    public void getCategory() {
        dialogUtil.progressDialog(ChemistMapActivity.this, "Fetching Chemists...");
        String url = Constants.RetailerList;
        Log.e("url", url);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                dialogUtil.stopProgressDialog();

                hotelMapArrayList = new ArrayList<>(Arrays.asList(new GsonBuilder().create().fromJson(response, RetailerModel[].class)));
//                gridView.setAdapter(new CategoryListAdapter(CategoryActivity.this, categoryModelArrayList));
                refreshMapMarkers();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                dialogUtil.stopProgressDialog();
                Toast.makeText(getApplicationContext(), "something went wrong in getting responce", Toast.LENGTH_LONG).show();
                error.printStackTrace();
            }
        });
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(15000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        AppController.getInstance().getRequestQueue().add(stringRequest);

    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.image_chat:
                Konotor.getInstance(getApplicationContext())
                        .launchFeedbackScreen(ChemistMapActivity.this);
                chatCountTV.setVisibility(View.INVISIBLE);
                break;
            case R.id.chemist_list:
                showRetailerListActivity();
                break;
        }
    }


    public class MarkerInfoWindowAdapter implements GoogleMap.InfoWindowAdapter {

        @SuppressLint("NewApi")
        @Override
        public View getInfoWindow(final Marker marker) {

            RetailerModel retailerModel = markersHashMap.get(marker.getId());
            String retailerId=sharedPreferences.getString(Constants.RETAILER_IDS,"");
            if(retailerId.contains(retailerModel.getRetailerId().toString())){
               // showRetailerListActivity();
                Toast.makeText(getApplicationContext(), "Chemist already Added", Toast.LENGTH_LONG).show();
            }else{
                sharedPreferences.edit().putString(Constants.RETAILER_IDS,(retailerId.isEmpty()?"":retailerId+",")+retailerModel.getRetailerId()).commit();
               // showRetailerListActivity();
                Toast.makeText(getApplicationContext(), "Chemist selected", Toast.LENGTH_LONG).show();
            }
            /*




            View view = getLayoutInflater().inflate(R.layout.layout_hotel_map_marker_info_view_layout, null);
            final HotelInformationViewModel hotelInformationViewModel = markersHashMap.get(marker.getId());
            final ImageView hotelImageView = (ImageView) view.findViewById(R.id.hotel_map_view_image_view);
            CustomTextViewLight hotelNameText = (CustomTextViewLight) view.findViewById(R.id.hotel_map_view_hotel_name_text_view);
            LinearLayout starRatingLayout = (LinearLayout) view.findViewById(R.id.hotel_map_view_star_rating_linear_layout);
            CustomTextView priceTextView = (CustomTextView) view.findViewById(R.id.hotel_map_view_price_text_view);
            //CustomImageView arrowImageView = (CustomImageView) view.findViewById(R.id.hotel_map_view_arrow_image_view);
            hotelNameText.setText(hotelInformationViewModel.getHotelName());
            hotelImageView.setPadding(0, 0, -3, 0);
            priceTextView.setText(CurrencyManager.priceWithCurrency(instanceBaseController, hotelInformationViewModel.getDisplayAverageNightlyRate(), true));

            if (hotelInformationViewModel.getStarRating() >= 0 && hotelInformationViewModel.getStarRating() <= 5) {
                starRatingLayout.removeAllViews();
                starRatingLayout.addView(HotelUtility.ratingStarsLayout(instanceBaseController, hotelInformationViewModel.getStarRating()));
            } else {
                starRatingLayout.removeAllViews();
            }

            if (hotelInformationViewModel.getHotelMainImageURL() != null) {
                Picasso.with(instanceBaseController).load(hotelInformationViewModel.getHotelMainImageURL())
                        .placeholder(R.drawable.map_view_image)
                        .error(R.drawable.map_view_image)
                        .transform(new HotelUtility.RoundedImageTransformation(3, 0))
                        .resize((int) Utility.getPxFromDp(instanceBaseController, 58), (int) Utility.getPxFromDp(instanceBaseController, 56))
                        .into(hotelImageView);

            }

            return view;*/
            return null;
        }

        @SuppressLint("NewApi")
        @Override
        public View getInfoContents(Marker marker) {

            return null;
        }// end of getInfoContents

    }// end of MarkerInfoWindowAdapter

    private void showRetailerListActivity() {
        Intent intent = new Intent(this,ChemistListActivity.class);
        startActivity(intent);
    }


}// end of the class
