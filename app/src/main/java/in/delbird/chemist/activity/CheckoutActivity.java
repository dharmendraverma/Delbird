package in.delbird.chemist.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import in.delbird.chemist.Controller.AppController;
import in.delbird.chemist.Controller.Constants;
import in.delbird.chemist.CustomViews.GothamBoldTextView;
import in.delbird.chemist.CustomViews.GothamLightTextView;
import in.delbird.chemist.R;
import in.delbird.chemist.models.CartModel;
import in.delbird.chemist.utils.DialogUtil;

/**
 * Created by Dharmendra on 3/4/16.
 */
     public class CheckoutActivity extends AppCompatActivity {

    @Bind(R.id.toolbar_title)
    GothamLightTextView toolbarTitle;
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
    @Bind(R.id.current_balance)
    GothamBoldTextView currentBalance;
    @Bind(R.id.option1)
    RadioButton option1;
    @Bind(R.id.option2)
    RadioButton option2;
    @Bind(R.id.recharge)
    TextView recharge;
    @Bind(R.id.parent)
    LinearLayout parent;
    private SharedPreferences preferences;
    DialogUtil dialogUtil = new DialogUtil();
    private ImageView chat_IV,cart_IV;
    LinearLayout mSearchProductLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_payement);
        ButterKnife.bind(this);
        preferences = getSharedPreferences(Constants.SHAREDPREFERENCES_NAME, MODE_PRIVATE);

        currentBalance.setText(CartModel.getCartModelInstance().getTotalAmount() + "");

        cart_IV=(ImageView)findViewById(R.id.image_cart);
        chat_IV=(ImageView)findViewById(R.id.image_chat);
        mSearchProductLayout=(LinearLayout)findViewById(R.id.search_product_Layout);
        mSearchProductLayout.setVisibility(View.GONE);
        chat_IV.setVisibility(View.INVISIBLE);
        cart_IV.setVisibility(View.INVISIBLE);
        TextView mTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
        mTitle.setVisibility(View.VISIBLE);
        mTitle.setText("Payment");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        option2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(CheckoutActivity.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.view_dialog_box);

                // set the custom dialog components - text and button
                TextView text = (TextView) dialog.findViewById(R.id.txtDiaTitle);
                TextView image = (TextView) dialog.findViewById(R.id.txtDiaMsg);

                Button dialogButton = (Button) dialog.findViewById(R.id.btnOk);
                // if button is clicked, close the custom dialog
                dialogButton.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();

                    }
                });
                dialog.show();


            }
        });
        option1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(CheckoutActivity.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.view_dialog_box_wallet);

                // set the custom dialog components - text and button
                TextView text = (TextView) dialog.findViewById(R.id.txtDiaTitle);
                TextView image = (TextView) dialog.findViewById(R.id.txtDiaMsg);

                Button dialogButton = (Button) dialog.findViewById(R.id.btnOk);
                // if button is clicked, close the custom dialog
                dialogButton.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();

                    }
                });
                dialog.show();


            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    @OnClick(R.id.recharge)
    public void onClick() {
        dialogUtil.progressDialog(this, "Please Wait..");
        processPayment();
    }

    private void processPayment() {

        JSONObject jsonObject = CartModel.getCartModelInstance().buildRequestBody((option2.isChecked())?"cod":"online", preferences.getString(Constants.USER_ID,""));
        System.out.print(jsonObject);
        JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.POST, Constants.CHECKOUT_URL, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                       /* Intent intent = new Intent(OtpGeneratorActivity.this, SignUpActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                        startActivity(intent);
                        finish();*/
                 System.out.println(Constants.CHECKOUT_URL);

                try {
                    String status = response.getString("status");
                    if (status.equalsIgnoreCase("success")){
                        //launch order detail
                        if(option1.isChecked())
                        WalletActivity.amount-=(int)(CartModel.getCartModelInstance().getTotalAmount()*0.8);
                        CartModel.getCartModelInstance().clearCart();
                        Toast.makeText(CheckoutActivity.this, "Order Placed Successfully...", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(CheckoutActivity.this,OrderFinal.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                    }
                }catch (Exception e){
                    Toast.makeText(CheckoutActivity.this, "something went wrong...", Toast.LENGTH_LONG).show();
                }
                dialogUtil.stopProgressDialog();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                dialogUtil.stopProgressDialog();
                Toast.makeText(CheckoutActivity.this, "something went wrong...", Toast.LENGTH_LONG).show();
                error.printStackTrace();
            }
        });
        // requestQueue.add(stringRequest);
        AppController.getInstance().getRequestQueue().add(stringRequest);
    }
}
