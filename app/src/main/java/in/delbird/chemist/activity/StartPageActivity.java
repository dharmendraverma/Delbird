package in.delbird.chemist.activity;

import android.app.ActionBar;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.demach.konotor.Konotor;
import com.google.common.base.Predicate;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import com.google.common.collect.Collections2;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import in.delbird.chemist.Controller.AppController;
import in.delbird.chemist.Controller.Constants;
import in.delbird.chemist.CustomViews.GothamBoldTextView;
import in.delbird.chemist.CustomViews.GothamLightTextView;
import in.delbird.chemist.CustomViews.GouthamLightEditText;
import in.delbird.chemist.R;
import in.delbird.chemist.adapters.CategoryListAdapter;
import in.delbird.chemist.adapters.ShopListAdapter;
import in.delbird.chemist.models.CartModel;
import in.delbird.chemist.models.CategoryHasMAp;
import in.delbird.chemist.models.CategoryModel;
import in.delbird.chemist.models.ShopModel;
import in.delbird.chemist.services.GPSLocationService;
import in.delbird.chemist.services.GeoLocationService;
import in.delbird.chemist.utils.CommonUtility;
import in.delbird.chemist.utils.DialogUtil;

public class StartPageActivity extends BaseCartActivity implements View.OnClickListener {

    public SharedPreferences preferences;
    String username, email;
    NavigationView navigationView;
    DrawerLayout navigationDrawerND;
    TextView chatCountTV, emailTV;
    ActionBar mActionbar;
    // ProgressDialog pdilog;
    ArrayList<CategoryModel> categoryModelArrayList = new ArrayList<>();
    CategoryListAdapter categoryListAdapter;
    DialogUtil dialogUtil = new DialogUtil();
    @Bind(R.id.enter_location)
    GothamLightTextView enterLocation;
    GouthamLightEditText searchProduct;
    private IntentFilter konotorIntentFilter;
    private Button orderThruChatBT, chooseFavDealBT, choosFavShopBT;
    private ImageView chat_IV, cart_IV, discout_IV;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private Toolbar mToolbar;
    private TextView mTitle;
    private GridView gridView;
    private TextView cartCount;
    TextView mHeaderPhoneNumber;
    LinearLayout mSearchProductLayout;
    private ShopListAdapter shopListAdapter;
    ArrayList<ShopModel> shopModelArrayList = new ArrayList<>();
    //Local Broadcast Reciever
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
    private boolean isCurrentLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_page);
        ButterKnife.bind(this);
        init();
        navigationView = (NavigationView) findViewById(R.id.navigation_view);
        shopListAdapter = new ShopListAdapter(StartPageActivity.this, shopModelArrayList);

        preferences = getSharedPreferences(Constants.SHAREDPREFERENCES_NAME, MODE_PRIVATE);
        View header=navigationView.getHeaderView(0);
       // View headerView=navigationView.inflateHeaderView(R.layout.header);
        mHeaderPhoneNumber = (TextView)header.findViewById(R.id.user_number);
     //   System.out.println("mobile_number=" + preferences.getString(Constants.MOBILE_NUMBER,"0")+preferences.getString(Constants.MOBILE_NUM_SEND,"0"));
        mHeaderPhoneNumber.setText("+91 "+preferences.getString(Constants.MOBILE_NUM_SEND,"0"));
    }



    public void init() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mTitle = (TextView) mToolbar.findViewById(R.id.toolbar_title);
        mTitle.setVisibility(View.VISIBLE);
        mTitle.setText("Categories");
        mToolbar.setTitle("Categories");
        setSupportActionBar(mToolbar);

        gridView = (GridView) findViewById(R.id.grid);
        categoryListAdapter = new CategoryListAdapter(this, categoryModelArrayList);
        gridView.setAdapter(categoryListAdapter);
        chatCountTV = (TextView) findViewById(R.id.chat_count);
        konotorIntentFilter = new IntentFilter("Konotor_UnreadMessageCountChanged");
        LocalBroadcastManager.getInstance(getApplicationContext()).registerReceiver(konotor_broadcastReceiver, konotorIntentFilter);
        chat_IV = (ImageView) findViewById(R.id.image_chat);
        cart_IV = (ImageView) findViewById(R.id.image_cart);
        //discout_IV=(ImageView)findViewById(R.id.image_discount);
        chat_IV.setOnClickListener(this);
        cart_IV.setOnClickListener(this);
        // discout_IV.setOnClickListener(this);
        navigationView = (NavigationView) findViewById(R.id.navigation_view);
        navigationDrawerND = (DrawerLayout) findViewById(R.id.myNavigationDrawerND);
        cartCount=(TextView)findViewById(R.id.cart_count);
        searchProduct=(GouthamLightEditText)findViewById(R.id.search_product);
        searchProduct.setOnClickListener(this);
        mSearchProductLayout=(LinearLayout)findViewById(R.id.search_product_Layout);
        mSearchProductLayout.setVisibility(View.INVISIBLE);
    }


    private void updateList(final String string) {
        if(!TextUtils.isEmpty(string)) {
            ArrayList<ShopModel> categoryModelArray = new ArrayList<>(
                    Collections2.filter(shopModelArrayList, new Predicate<ShopModel>() {
                        @Override
                        public boolean apply(ShopModel userFavListModel) {
                            return userFavListModel.getProductName().toLowerCase().startsWith(string);
                        }
                    }));
            shopListAdapter.changeData(categoryModelArray);
        }else{
            shopListAdapter.changeData(shopModelArrayList);
        }

    }


    public void updateCartText(){
        if(CartModel.getCartModelInstance().getCartItemsCount()>0) {
            System.out.println("------"+CartModel.getCartModelInstance().getCartItemsCount());
            cartCount.setVisibility(View.VISIBLE);
            cartCount.setText(CartModel.getCartModelInstance().getCartItemsCount());
        }

        else{
            cartCount.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.image_chat:
                Konotor.getInstance(getApplicationContext())
                        .launchFeedbackScreen(StartPageActivity.this);
                chatCountTV.setVisibility(View.INVISIBLE);
                break;
            case R.id.image_cart:
                startActivity(new Intent(StartPageActivity.this, CartActivity.class));
                break;
            case R.id.search_product:
               // startActivity(new Intent(StartPageActivity.this,SearchListActivity.class));
                break;

        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        try {
            startService(new Intent(this, GeoLocationService.class));

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        init();

        preferences = getSharedPreferences(Constants.SHAREDPREFERENCES_NAME, MODE_PRIVATE);
        username = preferences.getString(Constants.USER_NAME, null);
        email = preferences.getString(Constants.EMAIL_ID, null);
        isCurrentLocation = preferences.getBoolean(Constants.CURRENT_LOCATION, true);
        getCategory();
        Konotor.getInstance(getApplicationContext())
                .withUserName(username)            // optional name by which to display the user
                .withIdentifier(email)            // optional unique identifier for your reference
                .withUserEmail(email)        // optional email address of the user
                .withLaunchMainActivityOnFinish(true) // to launch your app on hitting the back button on Konotor's inbox interface, in case the app was not running already
                .withWelcomeMessage("Welcome To Bharatam Jayate")
                .withUsesCustomSupportImage(true)
                .withFeedbackScreenTitle("Customer Support")
                .init(Constants.delbird3Konotorid, Constants.getDelbird3Konotorkey);





        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
//                return false;
                CommonUtility commonUtility = new CommonUtility();
                item.setChecked(true);
                switch (item.getItemId()) {
//                    case R.id.termsIT: {
//                        commonUtility.call_intent(getApplicationContext(), TermsAndConditionActivity.class);
//                        break;
//                    }
//                    case R.id.homeIT: {
//                      //  commonUtility.call_intent(getApplicationContext(),HomePageActivity.class);
//                        break;
//                    }
                    case R.id.changeProfileIT: {
                        commonUtility.call_intent(getApplicationContext(), ProfileChangeActivity.class);
                        break;

                    }
                    case R.id.chatIT:{
                        Konotor.getInstance(getApplicationContext())
                                .launchFeedbackScreen(StartPageActivity.this);
                       // chatCountTV.setVisibility(View.INVISIBLE);
                        break;
                    }
                    case R.id.rateUsIT:{
                        break;
                    }
                    case R.id.feedBackIT:{

                        break;
                    }
                    case R.id.aboutIT:{
                        Intent intent = new Intent(StartPageActivity.this, AboutUs.class);
                       // intent.putExtra(InvoiceListActivity.IS_FROM_MENU, true);
                        startActivity(intent);
                        break;
                    }

                    case R.id.walletIT:{
                        Intent intent = new Intent(StartPageActivity.this, WalletActivity.class);
                        // intent.putExtra(InvoiceListActivity.IS_FROM_MENU, true);
                        startActivity(intent);
                        break;
                    }


                    case R.id.orderIT:
                        Intent intent = new Intent(StartPageActivity.this, InvoiceListActivity.class);
                        intent.putExtra(InvoiceListActivity.IS_FROM_MENU, true);
                        startActivity(intent);
                        break;

                    case R.id.wallet_historyIT:
                        Intent intentnew = new Intent(StartPageActivity.this, InvoiceListActivity.class);
                        intentnew.putExtra(InvoiceListActivity.IS_FROM_MENU, true);
                      //  startActivity(intentnew);
                        break;

                   // case R.id.doctorIT:
                     //   Intent intent3 = new Intent(StartPageActivity.this, DoctorPrescriptionListActivity.class);
                     //   intent3.putExtra(InvoiceListActivity.IS_FROM_MENU, true);
                      //  startActivity(intent3);
                      //  break;


                 //   <group
                 //   android:id="@+id/doctorGP"
                 //   android:checkableBehavior="single">
                 //   <item
                 //   android:id="@+id/doctorIT"
                 //   android:icon="@drawable/orderlist"
                 //   android:title="My Prescription" />
                 //   </group>

                }
                navigationDrawerND.closeDrawers();
                return true;
            }
        });
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, navigationDrawerND, mToolbar, R.string.openDrawer, R.string.closeDrawer) {
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }
        };


        navigationDrawerND.setDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        if (updateUnreadMessageCount() != 0) {
            chatCountTV.setVisibility(View.VISIBLE);
            chatCountTV.setText(String.valueOf(updateUnreadMessageCount()));
        } else if (updateUnreadMessageCount() == 0) {
            chatCountTV.setVisibility(View.INVISIBLE);
        }

        setLocationText();
    }

    private void setLocationText() {
        enterLocation.setText(preferences.getString(Constants.MY_LOCATION_NAME, ""));
        if (GPSLocationService.getMyLocation() != null && isCurrentLocation) {
            List<Address> addresses;
            try {
                Geocoder geocoder = new Geocoder(this, Locale.getDefault());
                addresses = geocoder.getFromLocation(GPSLocationService.getLocLat(),
                        GPSLocationService.getLocLong(), 1);
                //String name = addresses.get(0).getFeatureName();

                //String address = addresses.get(0).getAddressLine(0);
                String city = addresses.get(0).getAddressLine(1);
                //String country = addresses.get(0).getAddressLine(2);
                String locality = addresses.get(0).getLocality();
                String cities[] =city.split(",");
                enterLocation.setText(cities[cities.length-1]+"," +locality);
                preferences.edit().putString(Constants.MY_LOCATION_NAME,cities[cities.length-1]+"," +locality).apply();
            }catch (Exception e){

            }
        }
    }

    public void getCategory() {
        dialogUtil.progressDialog(StartPageActivity.this, "Fetching Categories...");
        String url = Constants.CategoryList;
        Log.e("url", url);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                dialogUtil.stopProgressDialog();

                categoryModelArrayList = new ArrayList<>(Arrays.asList(new GsonBuilder().create().fromJson(response, CategoryModel[].class)));
//                gridView.setAdapter(new CategoryListAdapter(CategoryActivity.this, categoryModelArrayList));
                categoryListAdapter.changeData(categoryModelArrayList);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                dialogUtil.stopProgressDialog();
                Toast.makeText(getApplicationContext(), "No Internet Connectivity", Toast.LENGTH_LONG).show();
                error.printStackTrace();
            }
        });
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(15000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        AppController.getInstance().getRequestQueue().add(stringRequest);

    }

    //Function To Call Konotor Chat
    public void getKonotorChat() {
        Konotor.getInstance(getApplicationContext())
                .launchFeedbackScreen(StartPageActivity.this);

        chatCountTV.setVisibility(View.INVISIBLE);
    }

    public int updateUnreadMessageCount() {
        int unreadMessageCount = Konotor.getInstance(getApplicationContext()).getUnreadMessageCount();
        return unreadMessageCount;

    }

    @OnClick(R.id.enter_location)
    public void onClick() {
        Intent intent = new Intent(StartPageActivity.this, SelectLocationActivity.class);
        intent.putExtra("pickupAddress", "sample text");
        //intent.putExtra("from", "Home");
        startActivity(intent);
    }

}
