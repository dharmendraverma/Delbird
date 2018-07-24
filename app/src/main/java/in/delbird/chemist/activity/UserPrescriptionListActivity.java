package in.delbird.chemist.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
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
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.Arrays;

import butterknife.Bind;
import butterknife.ButterKnife;
import in.delbird.chemist.Controller.AppController;
import in.delbird.chemist.Controller.Constants;
import in.delbird.chemist.CustomViews.GothamBoldTextView;
import in.delbird.chemist.CustomViews.GothamLightTextView;
import in.delbird.chemist.R;
import in.delbird.chemist.adapters.DoctorPrescriptionListAdapter;
import in.delbird.chemist.adapters.UserPrescriptionListAdapter;
import in.delbird.chemist.models.DoctorPrescriptionModel;
import in.delbird.chemist.models.ShopModel;
import in.delbird.chemist.models.UserPrescriptionListModel;
import in.delbird.chemist.utils.DialogUtil;
import in.delbird.chemist.utils.RecyclerViewUtils.EndlessRecyclerOnScrollListener;

/**
 * Created by Dharmendra  on 10/4/16.
 */
public class UserPrescriptionListActivity extends AppCompatActivity {

    @Bind(R.id.toolbar_title)
    GothamLightTextView toolbarTitle;
    @Bind(R.id.search_icon)
    ImageView searchIcon;
    @Bind(R.id.clear)
    ImageView clear;
    @Bind(R.id.search_product_Layout)
    LinearLayout searchProductLayout;
    @Bind(R.id.image_chat)
    ImageView imageChat;
    @Bind(R.id.image_cart)
    ImageView imageCart;
    @Bind(R.id.cart_count)
    TextView cartCount;
    @Bind(R.id.chat_count)
    TextView chatCount;
    @Bind(R.id.title_txt)
    RelativeLayout titleTxt;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;

    Boolean oldData = false; // to see if old is there or not in the list

    ArrayList<UserPrescriptionListModel> shopModelArrayList = new ArrayList<>();


    UserPrescriptionListAdapter doctorPrescriptionListAdapter;
    private DialogUtil dialogUtil = new DialogUtil();
    private SharedPreferences sharedPreferences;
    private long details_id;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_prescription_list);
        ButterKnife.bind(this);
        details_id = getIntent().getExtras().getLong("id",0);
        doctorPrescriptionListAdapter = new UserPrescriptionListAdapter(this,shopModelArrayList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(doctorPrescriptionListAdapter);
        sharedPreferences = getSharedPreferences(Constants.SHAREDPREFERENCES_NAME, MODE_PRIVATE);
        searchProductLayout.setVisibility(View.INVISIBLE);
        imageCart.setVisibility(View.INVISIBLE);
        imageChat.setVisibility(View.INVISIBLE);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        TextView mTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
        mTitle.setVisibility(View.VISIBLE);
        mTitle.setText("Doctor Prescription Details");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onResume() {
        super.onResume();
        doctorPrescriptionListAdapter.clearData();
        getShopList();
    }

    private void getShopList() {
        String url = Constants.prescriptionsDetailUrl;
        url = String.format(url,details_id);
        dialogUtil.progressDialog(this, "Fetching Prescriptions..");
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                dialogUtil.stopProgressDialog();

                shopModelArrayList = new ArrayList<>(Arrays.asList(new GsonBuilder().create().fromJson(response, UserPrescriptionListModel[].class)));

                if (shopModelArrayList == null) {
//                    Toast.makeText(getApplicationContext(),"finish",Toast.LENGTH_SHORT).show();
                } else {
                    if (oldData == true) // if there is old data on refresh then clear it and add new
                    {
                        doctorPrescriptionListAdapter.clearData(); // clearing the data in the adapter
                        oldData = false;  // now old data is false
                    }
                    doctorPrescriptionListAdapter.add(shopModelArrayList); // fetching the data
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

}
