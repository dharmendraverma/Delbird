package in.delbird.chemist.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.demach.konotor.Konotor;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.Arrays;

import in.delbird.chemist.Controller.AppController;
import in.delbird.chemist.Controller.Constants;
import in.delbird.chemist.utils.DialogUtil;
import in.delbird.chemist.utils.GPSTracker;
import in.delbird.chemist.R;
import in.delbird.chemist.adapters.DealListAdapter;
import in.delbird.chemist.models.DealModel;

public class DealsListActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TextView mTitle, chatCountTV;
    ListView listView;
    private ImageView chatIV;
    double longitude = 0, latitude = 0;
    DealListAdapter dealListAdapter;
    private IntentFilter konotorIntentFilter;
    ArrayList<DealModel> dealModelArrayList = new ArrayList<>();
    public SharedPreferences preferences;
    String username, email;
    DialogUtil dialogUtil = new DialogUtil();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_list);
    }

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
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onResume() {
        super.onResume();
        init();

        preferences = getSharedPreferences(Constants.SHAREDPREFERENCES_NAME, MODE_PRIVATE);
        username = preferences.getString(Constants.USER_NAME, null);
        email = preferences.getString(Constants.EMAIL_ID, null);

        Konotor.getInstance(getApplicationContext())
                .withUserName(username)            // optional name by which to display the user
                .withIdentifier(email)            // optional unique identifier for your reference
                .withUserEmail(email)        // optional email address of the user
                .withLaunchMainActivityOnFinish(true) // to launch your app on hitting the back button on Konotor's inbox interface, in case the app was not running already
                .withWelcomeMessage("Welcome To Bharattam Jayate")
                .withUsesCustomSupportImage(true)
                .withFeedbackScreenTitle("Customer Support")
                .init(Constants.delbird3Konotorid, Constants.getDelbird3Konotorkey);


        chatIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Konotor.getInstance(getApplicationContext())
                        .launchFeedbackScreen(DealsListActivity.this);
                chatCountTV.setVisibility(View.INVISIBLE);
            }
        });

        if (updateUnreadMessageCount() != 0) {
            chatCountTV.setVisibility(View.VISIBLE);
            chatCountTV.setText(String.valueOf(updateUnreadMessageCount()));
        }
        else if(updateUnreadMessageCount()==0)
        {
            chatCountTV.setVisibility(View.INVISIBLE);
        }

        GPSTracker gps = new GPSTracker(this);
        if (gps.canGetLocation()) {
            Log.e("location", String.valueOf(gps.canGetLocation()));
            latitude = gps.getLatitude();
            longitude = gps.getLongitude();
            dialogUtil.progressDialog(DealsListActivity.this, "Please Wait..");
            String url = Constants.DEAL_LIST_URL_BY_LAT_LON + latitude + "&lon=" + longitude;
            Log.e("url", url);
            StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    dialogUtil.stopProgressDialog();
                    dealModelArrayList = new ArrayList<>(Arrays.asList(new GsonBuilder().create().fromJson(response, DealModel[].class)));
                    listView.setAdapter(new DealListAdapter(DealsListActivity.this, dealModelArrayList));
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
        } else {
            gps.showSettingAlert();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public void init() {

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        mTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mTitle.setText("Offers NearBy");
        chatIV = (ImageView)findViewById(R.id.image_chat);
        chatCountTV = (TextView)findViewById(R.id.chat_count);
        listView= (ListView) findViewById(R.id.lv_shoplist);
        konotorIntentFilter = new IntentFilter("Konotor_UnreadMessageCountChanged");
        LocalBroadcastManager.getInstance(getApplicationContext()).registerReceiver(konotor_broadcastReceiver, konotorIntentFilter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(DealsListActivity.this, ShopListActivity.class);
                startActivity(intent);
            }
        });
    }

    public int updateUnreadMessageCount() {
        int unreadMessageCount = Konotor.getInstance(getApplicationContext()).getUnreadMessageCount();
        return unreadMessageCount;
    }
}