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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.demach.konotor.Konotor;
import com.google.gson.GsonBuilder;
import com.squareup.picasso.Picasso;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import in.delbird.chemist.Controller.AppController;
import in.delbird.chemist.Controller.Constants;
import in.delbird.chemist.R;
import in.delbird.chemist.models.BaseErrorResponseModel;
import in.delbird.chemist.models.ShopModel;
import in.delbird.chemist.utils.DialogUtil;

public class ProductDetailActivity extends AppCompatActivity {

    Toolbar toolbar;
    TextView mTitle, textViewMorn;
    ImageView imageViewCart;
    ImageView chat_imageview;
    ImageView cart_IV;
    String username, email;
    SharedPreferences preferences;
    TextView chatCount;
    long shop_id;
    @Bind(R.id.productImage)
    ImageView productImage;
    @Bind(R.id.prductDescription)
    TextView prductDescription;
    private IntentFilter konotorIntentFilter;
    private ShopModel shopModel;
    private DialogUtil dialogUtil;
    private LinearLayout mSearchProductLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);
        mSearchProductLayout=(LinearLayout)findViewById(R.id.search_product_Layout);
        mSearchProductLayout.setVisibility(View.GONE);
        ButterKnife.bind(this);
        init();
        if (getIntent().hasExtra(Constants.PRODUCT_DETAIL_MODEL)) {
            shopModel = (ShopModel) getIntent().getSerializableExtra(Constants.PRODUCT_DETAIL_MODEL);
        }
        if (shopModel != null) {
            Picasso.with(this).load(shopModel.getImageUrl()).placeholder(R.drawable.default_user_img).into(productImage);
            prductDescription.setText(shopModel.getDescription());
        }
//        Toast.makeText(getApplicationContext(),"shop id = "+shop_id,Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    public void init() {

        //sharedPreferences=getSharedPreferences("TIME_PREF",MODE_PRIVATE);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        TextView mTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
        mTitle.setVisibility(View.VISIBLE);
       //textViewMorn = (TextView) findViewById(R.id.tv_morn);
       // imageViewCart = (ImageView) findViewById(R.id.iv_cart);
        chat_imageview = (ImageView) findViewById(R.id.image_chat);
        chat_imageview.setVisibility(View.GONE);
        cart_IV=(ImageView)findViewById(R.id.image_cart);
        cart_IV.setVisibility(View.INVISIBLE);

        konotorIntentFilter = new IntentFilter("Konotor_UnreadMessageCountChanged");
        LocalBroadcastManager.getInstance(getApplicationContext()).registerReceiver(konotor_broadcastReceiver, konotorIntentFilter);
//        chat_count = (TextView) findViewById(R.id.chat_count);
        chatCount = (TextView) findViewById(R.id.chat_count);
        mTitle.setText("Product Description");

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //toolbar.setLogo(R.drawable.chat_icon);

        getSupportActionBar().setTitle("");

    }

    @Override
    protected void onResume() {
        super.onResume();

        preferences = getSharedPreferences(Constants.SHAREDPREFERENCES_NAME, MODE_PRIVATE);
        username = preferences.getString(Constants.USER_NAME, null);
        email = preferences.getString(Constants.EMAIL_ID, null);

        Konotor.getInstance(getApplicationContext())
                .withUserName(username)            // optional name by which to display the user
                .withIdentifier("" + email)            // optional unique identifier for your reference
                .withUserEmail("" + email)        // optional email address of the user
                .withLaunchMainActivityOnFinish(true) // to launch your app on hitting the back button on Konotor's inbox interface, in case the app was not running already
                .withWelcomeMessage("Welcome TO Delbird")
                .withUsesCustomSupportImage(true)
                .withFeedbackScreenTitle("Delbird")
                .init(Constants.delbird3Konotorid, Constants.getDelbird3Konotorkey);

        chat_imageview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Konotor.getInstance(getApplicationContext())
                        .launchFeedbackScreen(ProductDetailActivity.this);
                chatCount.setVisibility(View.INVISIBLE);
            }
        });


       // Log.e("url ", url);
        //check unreadMessageCount
        if (updateUnreadMessageCount() != 0) {
            chatCount.setVisibility(View.VISIBLE);
            chatCount.setText(String.valueOf(updateUnreadMessageCount()));
        } else if (updateUnreadMessageCount() == 0) {
            chatCount.setVisibility(View.INVISIBLE);
        }
    }


    private BroadcastReceiver konotor_broadcastReceiver = new BroadcastReceiver() {


        @Override
        public void onReceive(Context context, Intent intent) {
            int unreadMessageCount = Konotor.getInstance(getApplicationContext()).getUnreadMessageCount();
            Log.e("unread", String.valueOf(unreadMessageCount));
            chatCount.setVisibility(View.VISIBLE);
            chatCount.setText(String.valueOf(unreadMessageCount));
            if (unreadMessageCount != 0) {
                chatCount.setVisibility(View.VISIBLE);
                chatCount.setText(String.valueOf(unreadMessageCount));
            }
        }
    };


    public int updateUnreadMessageCount() {
        int unreadMessageCount = Konotor.getInstance(getApplicationContext()).getUnreadMessageCount();
        return unreadMessageCount;

    }

}
