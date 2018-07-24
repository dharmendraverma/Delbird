package in.delbird.chemist.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
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
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import in.delbird.chemist.Controller.AppController;
import in.delbird.chemist.Controller.Constants;
import in.delbird.chemist.CustomViews.GothamLightTextView;
import in.delbird.chemist.R;
import in.delbird.chemist.adapters.InvoiceDetailAdapter;
import in.delbird.chemist.models.InvoiceDetailItemModel;
import in.delbird.chemist.models.ShopModel;
import in.delbird.chemist.utils.DialogUtil;
import in.delbird.chemist.utils.RecyclerViewUtils.EndlessRecyclerOnScrollListener;

public class InvoiceDetailActivity extends AppCompatActivity {
    public static final String MASTER_ID = "from_menu";
    public SharedPreferences sharedPreferences;
    DialogUtil dialogUtil;
    RecyclerView recyclerView;
    Toolbar mToolbar;
    private TextView mTitle;
    LinearLayout mSearchProductLayout;
    TextView mReceived;
    String username, email, category, url, nextPageToken;
    double latitude, longitude;
    ArrayList<InvoiceDetailItemModel> shopModelArrayList = new ArrayList<>();
    Boolean oldData = false; // to see if old is there or not in the list
    Boolean firstCall = false; // to see whether url was called for the first time
    int prev_size;
    //A RecyclerView.LayoutManager implementation which provides similar functionality to ListView
    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
    //ShopListModel shopListModelObject = new ShopListModel();
    SwipeRefreshLayout swipeRefreshLayout;

    @Bind(R.id.image_cart)
    ImageView imageCart;

    @Bind(R.id.chat_count)
    TextView chatCount;
    @Bind(R.id.title_txt)
    RelativeLayout titleTxt;
    @Bind(R.id.toolbar_title)
    GothamLightTextView toolbarTitle;


    @Bind(R.id.shopListRecylerView)
    RecyclerView shopListRecylerView;

    //implements SwipeRefreshLayout.OnRefreshListener
    private Toolbar toolbar;
    private ImageView chatIV;
    private TextView chatCountTV;
    private IntentFilter konotorIntentFilter;
    private InvoiceDetailAdapter shopListAdapter;
    //local Broadcast Reciever for Konotor
    private BroadcastReceiver konotor_broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            int unreadMessageCount = Konotor.getInstance(getApplicationContext()).getUnreadMessageCount();
            Log.e("unread", String.valueOf(unreadMessageCount));
            chatCountTV.setVisibility(View.VISIBLE);

            chatCountTV.setText(String.valueOf(unreadMessageCount));
            if (unreadMessageCount != 0) {
                chatCountTV.setVisibility(View.VISIBLE);
                chatCountTV.setText(String.valueOf(unreadMessageCount));
            }
        }
    };
    private long masterId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retailer_detail_listcategory);
        ButterKnife.bind(this);
        shopListAdapter = new InvoiceDetailAdapter(this, shopModelArrayList);
        recyclerView = (RecyclerView) findViewById(R.id.shopListRecylerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        mSearchProductLayout=(LinearLayout)findViewById(R.id.search_product_Layout);
        imageCart.setVisibility(View.GONE);
        mSearchProductLayout.setVisibility(View.GONE);
        masterId = getIntent().getLongExtra(MASTER_ID, 0l);
       // mReceived=(TextView)findViewById(R.id.)
        init();
        firstCall = true; // this tells the url was called for the first time
//        call_list();
    }

    @Override
    protected void onResume() {
        super.onResume();
        shopListAdapter.clearData();
        firstCall = true;
        if (firstCall) {
            call_list();
        }
        init();

        final LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
//        recyclerView.setOnClickListener(this);
        chatIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getKonotorChat();
            }
        });
        recyclerView.setOnScrollListener(new EndlessRecyclerOnScrollListener() {
            @Override
            public void onLoadMore(int currentPage) {
//                Toast.makeText(getApplicationContext(), "Loading..", Toast.LENGTH_SHORT).show();
                call_list();
            }
        });
        if (updateUnreadMessageCount() != 0) {
            chatCountTV.setVisibility(View.VISIBLE);
            chatCountTV.setText(String.valueOf(updateUnreadMessageCount()));
        } else if (updateUnreadMessageCount() == 0) {
            chatCountTV.setVisibility(View.INVISIBLE);
        }
    }

    private void call_list() {
        if (firstCall) {
            dialogUtil = new DialogUtil();
            dialogUtil.progressDialog(this, "Fetching History Details..");

            url = Constants.InvoiceDetail + "user_id=" + sharedPreferences.getString(Constants.USER_ID, "") + "&invoiceMaster_id=" + masterId;
            getShopList(url); //get shop list

        }/* else {
                nextPageToken = shopListModelObject.getNext_page_token();
                url = Constants.SHOP_LIST_URL + "lat=" + String.valueOf(latitude) + "&retailer_id="+retailer_id+"&" + "lon=" + String.valueOf(longitude) + "&category_name=" + category + "&next_page_token=" + nextPageToken+"&product_name=";
            }*/
    }

    public void getKonotorChat() {
        Konotor.getInstance(getApplicationContext())
                .launchFeedbackScreen(this);
        chatCountTV.setVisibility(View.INVISIBLE);
    }

    private void getShopList(String url) {
        Log.e("url", url);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                dialogUtil.stopProgressDialog();

                shopModelArrayList = new ArrayList<>(Arrays.asList(new GsonBuilder().create().fromJson(response, InvoiceDetailItemModel[].class)));

                if (shopModelArrayList == null) {
//                    Toast.makeText(getApplicationContext(),"finish",Toast.LENGTH_SHORT).show();
                } else {
                    if (oldData == true) // if there is old data on refresh then clear it and add new
                    {
                        shopListAdapter.clearData(); // clearing the data in the adapter
                        oldData = false;  // now old data is false
                    }
                    firstCall = false;
                    shopListAdapter.add(shopModelArrayList);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                dialogUtil.stopProgressDialog();
                Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();
                error.printStackTrace();
            }
        });
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(15000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        AppController.getInstance().getRequestQueue().add(stringRequest);
    }

    @Override
    protected void onPause() {
        super.onPause();
//        shopListAdapter.clearData();
//        firstCall = true;
    }

    private void init() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
//        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
//        swipeRefreshLayout.setOnRefreshListener(this);
        TextView mTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
        mTitle.setVisibility(View.VISIBLE);
        mTitle.setText("Invoice Detail List");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        konotorIntentFilter = new IntentFilter("Konotor_UnreadMessageCountChanged");
        LocalBroadcastManager.getInstance(getApplicationContext()).registerReceiver(konotor_broadcastReceiver, konotorIntentFilter);
        chatCountTV = (TextView) findViewById(R.id.chat_count);
        chatIV = (ImageView) findViewById(R.id.image_chat);
        sharedPreferences = getSharedPreferences(Constants.SHAREDPREFERENCES_NAME, MODE_PRIVATE);
        chatIV.setVisibility(View.GONE);
        getValuesFromSharedPreference(); //fetch required value from shared preferences
        initialize_konotor(); //initializing Konotor Instance
        recyclerView.setAdapter(shopListAdapter); //set the adapter of recycler view
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void getValuesFromSharedPreference() {
        username = sharedPreferences.getString(Constants.USER_NAME, null);
        email = sharedPreferences.getString(Constants.EMAIL_ID, null);
        category = sharedPreferences.getString(Constants.CATEGORY, null); //fetch category for updating shop list based on category
    }

    public void initialize_konotor() {
        Konotor.getInstance(getApplicationContext())
                .withUserName(username)            // optional name by which to display the user
                .withIdentifier("" + email)            // optional unique identifier for your reference
                .withUserEmail("" + email)        // optional email address of the user
                .withLaunchMainActivityOnFinish(true) // to launch your app on hitting the back button on Konotor's inbox interface, in case the app was not running already
                .withWelcomeMessage("Welcome To Bharatam Jayate")
                .withUsesCustomSupportImage(true)
                .withFeedbackScreenTitle("Customer Support")
                .init(Constants.delbird3Konotorid, Constants.getDelbird3Konotorkey);
    }

//    @Override
//    public void onRefresh() {
//        oldData = true;
//        firstCall = true;
//        swipeRefreshLayout.setRefreshing(true);
//        call_list();
//        shopListAdapter.notifyDataSetChanged();
//        recyclerView.invalidate();
//
//    }

    public int updateUnreadMessageCount() {
        int unreadMessageCount = Konotor.getInstance(getApplicationContext()).getUnreadMessageCount();
        return unreadMessageCount;

    }

    public void launchProductDetailActivity(ShopModel shopModel) {
        Intent intent = new Intent(this, ProductDetailActivity.class);
        intent.putExtra(Constants.PRODUCT_DETAIL_MODEL, shopModel);
        startActivity(intent);
    }


    public String joinList(List list, String literal) {
        return list.toString().replaceAll(",", literal).replaceAll("[\\[.\\].\\s+]", "");
    }

    @OnClick({R.id.received})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.received:
            performReceivedRequest();
            break;
        }
    }

    private void performReceivedRequest() {
        dialogUtil.progressDialog(this, "Please wait..");

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.dispatchRequest+"invoiceMaster_id="+masterId
                +"&invoiceStatus=received", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                dialogUtil.stopProgressDialog();
                Toast.makeText(getApplicationContext(), "Status Updated", Toast.LENGTH_LONG).show();


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                dialogUtil.stopProgressDialog();
                Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();
                error.printStackTrace();
            }
        });
        AppController.getInstance().getRequestQueue().add(stringRequest);
    }

}
