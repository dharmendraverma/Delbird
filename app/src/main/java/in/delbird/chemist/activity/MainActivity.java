package in.delbird.chemist.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.GsonBuilder;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import in.delbird.chemist.Controller.AppController;
import in.delbird.chemist.Controller.Constants;
import in.delbird.chemist.R;
import in.delbird.chemist.models.OTPResponseModel;
import in.delbird.chemist.utils.DialogUtil;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    public SharedPreferences preferences;
    private TextView textViewHead, textViewBlink, textViewDell;
    private EditText editTextMobile;
    private ImageView imageView;
    String mobileNum, newUserId, signUpUserId;
    Button buttonLogin;
    boolean flag = false;
   // private RequestQueue requestQueue;
    private OTPResponseModel otpResponseModel;
    DialogUtil dialogUtil = new DialogUtil();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        preferences = getSharedPreferences(Constants.SHAREDPREFERENCES_NAME, MODE_PRIVATE);
        Log.i("main activity", "main");
        Log.i("shared preferences", Constants.SHAREDPREFERENCES_NAME);

        //requestQueue = Volley.newRequestQueue(MainActivity.this);
        preferences = getSharedPreferences(Constants.SHAREDPREFERENCES_NAME, MODE_PRIVATE);
        signUpUserId = preferences.getString(Constants.USER_ID, "0");
        Log.i("sign up user id",preferences.getString(Constants.USER_ID,"0"));
        flag = preferences.getBoolean(Constants.IS_ALREADY_LOGGED_IN, false);
        Log.i("flag ",Constants.IS_ALREADY_LOGGED_IN);
        boolean is_number_already_register = preferences.getBoolean(Constants.FLAG, false);
        Log.i("",""+is_number_already_register);
        //Checking USER is already logged in or not
        if (preferences.getBoolean(Constants.IS_ALREADY_LOGGED_IN, false) || is_number_already_register) {
            Log.i("user already register", "user already register checking");
            Intent intent = new Intent(MainActivity.this, StartPageActivity.class);
            startActivity(intent);
            finish();
        }
        init();
    }

    public void init() {
        buttonLogin = (Button) findViewById(R.id.login_btn);
        textViewHead = (TextView) findViewById(R.id.tv_deliver);
        textViewBlink = (TextView) findViewById(R.id.tv_blink);
        textViewDell = (TextView) findViewById(R.id.tv_dellbird);
        imageView = (ImageView) findViewById(R.id.iv_phone);
        editTextMobile = (EditText) findViewById(R.id.ed_mobile);

        buttonLogin.setOnClickListener(this);

//setting the mobile number in Edit text when we back from OTP page
        if (getIntent().hasExtra(Constants.MOBILE_NUMBER)) {
            editTextMobile.setText(getIntent().getStringExtra(Constants.MOBILE_NUMBER));
        }
    }
    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.login_btn:

                mobileNum = String.valueOf(editTextMobile.getText());
                Log.e("mobileNum", mobileNum);

                if (isValidMobile(mobileNum)) {
                    //checkUserVerifyCall();
                    getOtpCall();
                } else {
                    Toast.makeText(getApplicationContext(), "Enter valide mobile Number", Toast.LENGTH_LONG).show();
                    editTextMobile.requestFocus();
                }
                break;
        }
    }

    private boolean isValidMobile(String phone) {
        if (mobileNum.length() != 10) {
            return false;
        } else {
            return android.util.Patterns.PHONE.matcher(phone).matches();
        }
    }

    private void getOtpCall() {
        dialogUtil.progressDialog(MainActivity.this, "Please Wait..");
        String url = "";
        try {
            url = Constants.varify + URLEncoder.encode(mobileNum, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        StringRequest  stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                dialogUtil.stopProgressDialog();
                otpResponseModel = new GsonBuilder().create().fromJson(response, OTPResponseModel.class);
                // Toast.makeText(getApplicationContext(), otpResponseModel.getStatus() + " " + otpResponseModel.getCode(), Toast.LENGTH_LONG).show();
                Toast.makeText(getApplicationContext(),"OTP code sent on Phone Number",Toast.LENGTH_LONG).show();
                String code = otpResponseModel.getOtp();
                String status = otpResponseModel.getStatus();
                System.out.println("otp="+otpResponseModel.getOtp());

//Putting the Mobile Number in sharedPreferences for further user
                preferences = getSharedPreferences(Constants.SHAREDPREFERENCES_NAME, MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString(Constants.MOBILE_NUM_SEND, mobileNum);
                System.out.println("Constants."+mobileNum+Constants.MOBILE_NUM_SEND);
                editor.commit();

                if (status.equalsIgnoreCase("success")) {
                    Intent intent = new Intent(MainActivity.this, OtpGeneratorActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    intent.putExtra(Constants.OTP_CODE, code);
                    intent.putExtra(Constants.OTP_STATUS, status);
                    intent.putExtra(Constants.MOBILE_NUMBER, mobileNum);
                    System.out.println("Constants." + mobileNum + Constants.MOBILE_NUM_SEND+mobileNum);
                    System.out.println("get user id="+otpResponseModel.getUserId());
                    intent.putExtra(Constants.USERID, otpResponseModel.getUserId());
                    intent.putExtra(Constants.ISNEWUSER, otpResponseModel.getNewUser());

                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(getApplication(), "OTP not generated, try again", Toast.LENGTH_LONG).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                dialogUtil.stopProgressDialog();
                Toast.makeText(MainActivity.this, "something went wrong in getting OTP", Toast.LENGTH_SHORT).show();
                error.printStackTrace();
            }
        });
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(15000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        AppController.getInstance().getRequestQueue().add(stringRequest);
        //requestQueue.add(stringRequest);
    }

}