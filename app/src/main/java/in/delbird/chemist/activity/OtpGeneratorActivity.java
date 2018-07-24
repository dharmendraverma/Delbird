package in.delbird.chemist.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import in.delbird.chemist.Controller.AppController;
import in.delbird.chemist.Controller.Constants;
import in.delbird.chemist.R;
import in.delbird.chemist.utils.DialogUtil;

public class OtpGeneratorActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView textView1, textView2, textView3, resendTV;
    private EditText editText;
    private Button button;
    String codeNum; /*,status, userEmailResponce, userNameResponce, userIdResponce*/;
    String resendStatus, resendCode;
    String varifyCode, varifyStatus, mobileNumOtp, varifyMob;
   // private RequestQueue requestQueue;
    public SharedPreferences preferences;
    boolean is_number_registered = false;
    DialogUtil dialogUtil = new DialogUtil();
    private long userID;
    private boolean isNewUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otpgenerator);

        // Getting value comming from Main Activity send by intent
        Intent intent = getIntent();
        varifyCode = intent.getStringExtra(Constants.OTP_CODE);
        varifyStatus = intent.getStringExtra(Constants.OTP_STATUS);
        varifyMob = intent.getStringExtra(Constants.MOBILE_NUMBER);
        userID= intent.getLongExtra(Constants.USERID,0);
        isNewUser = intent.getBooleanExtra(Constants.ISNEWUSER,true);

        preferences = getSharedPreferences(Constants.SHAREDPREFERENCES_NAME, MODE_PRIVATE);
        mobileNumOtp = preferences.getString(Constants.OTP_MOBILENUM, "0");

        init();

    }

    public void init() {

        button = (Button) findViewById(R.id.varify_btn);
        resendTV = (TextView) findViewById(R.id.tv_resend);
        button.setOnClickListener(this);
        resendTV.setOnClickListener(this);
        textView2 = (TextView) findViewById(R.id.tv_happiness);
        textView3 = (TextView) findViewById(R.id.tv_dellbird);

        editText = (EditText) findViewById(R.id.ed_otp);
       // requestQueue = Volley.newRequestQueue(OtpGeneratorActivity.this);

    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.varify_btn:
                codeNum = String.valueOf(editText.getText());
                if (codeNum.equalsIgnoreCase(varifyCode) || codeNum.equalsIgnoreCase(resendCode)) {
                    checkUserVerifyCall();

                    // Intent intent = new Intent(OtpGeneratorActivity.this, SignUpActivity.class);
                    // startActivity(intent);
                    //finish();
                    break;

                } else {
                    Toast.makeText(OtpGeneratorActivity.this, "code is not matched", Toast.LENGTH_SHORT).show();
                    break;
                }

            case R.id.tv_resend:
                getOtpCall();
                break;

        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra(Constants.MOBILE_NUMBER, getIntent().getStringExtra(Constants.MOBILE_NUMBER));
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    private void checkUserVerifyCall() {
      /*  dialogUtil.progressDialog(OtpGeneratorActivity.this, "Please Wait..");

        String url = "";

        url = Constants.chekvarifiyUser + varifyMob;
        Log.e("url", url);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                dialogUtil.stopProgressDialog();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    status = jsonObject.getString("status");
                    // Log.e("s", status);
                    if (status.equalsIgnoreCase("success")) {

                        String data = jsonObject.getString("data");
                        JSONObject jsonObject1 = new JSONObject(data);
                        userNameResponce = jsonObject1.getString("name");
                        userIdResponce = jsonObject1.getString("user_id");
                        userEmailResponce = jsonObject1.getString("email");
                        Log.e("st", userEmailResponce);
                        Log.e("st1", userNameResponce);
                        Log.e("st2", userIdResponce);
                        Toast.makeText(getApplicationContext(),"User allready registered",Toast.LENGTH_LONG).show();

                        Intent intent = new Intent(OtpGeneratorActivity.this, StartPageActivity.class);
                        is_number_registered = true;
                        Toast.makeText(getApplicationContext(),"User already registered",Toast.LENGTH_SHORT).show();
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.putBoolean(Constants.FLAG, is_number_registered);

//                        editor.putString(Constants.USER_EMAIL_ON_RESPONCE, userEmailResponce);
//                        editor.putString(Constants.USER_ID_ON_RESPONCE, userIdResponce);
//                        editor.putString(Constants.USER_NAME_ON_RESPONCE, userNameResponce);

                        editor.putString(Constants.EMAIL_ID, userEmailResponce);
                        editor.putString(Constants.USER_NAME, userNameResponce);
                        editor.putString(Constants.USER_ID, userIdResponce);
                        editor.commit();
                        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                        startActivity(intent);
                        finish();
                    } else {
                        Intent intent = new Intent(OtpGeneratorActivity.this, SignUpActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                        startActivity(intent);
                        finish();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                dialogUtil.stopProgressDialog();
                Toast.makeText(OtpGeneratorActivity.this, "something went wrong in verifying user", Toast.LENGTH_LONG).show();
                error.printStackTrace();
            }
        });
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(15000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
       // requestQueue.add(stringRequest);
        AppController.getInstance().getRequestQueue().add(stringRequest);*/

        if (!isNewUser) {

            Toast.makeText(getApplicationContext(),"User allready registered",Toast.LENGTH_LONG).show();

            Intent intent = new Intent(OtpGeneratorActivity.this, StartPageActivity.class);
            is_number_registered = true;
            Toast.makeText(getApplicationContext(),"User already registered",Toast.LENGTH_SHORT).show();
            SharedPreferences.Editor editor = preferences.edit();
            editor.putBoolean(Constants.FLAG, is_number_registered);

//                        editor.putString(Constants.USER_EMAIL_ON_RESPONCE, userEmailResponce);
//                        editor.putString(Constants.USER_ID_ON_RESPONCE, userIdResponce);
//                        editor.putString(Constants.USER_NAME_ON_RESPONCE, userNameResponce);

            /*editor.putString(Constants.EMAIL_ID, userEmailResponce);
            editor.putString(Constants.USER_NAME, userNameResponce);
            editor.putString(Constants.USER_ID, userIdResponce);*/
            editor.putString(Constants.USER_ID, String.valueOf(userID));
            editor.commit();
            intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
            finish();
        } else {
            Intent intent = new Intent(OtpGeneratorActivity.this, SignUpActivity.class);
            SharedPreferences.Editor editor = preferences.edit();

//                        editor.putString(Constants.USER_EMAIL_ON_RESPONCE, userEmailResponce);
//                        editor.putString(Constants.USER_ID_ON_RESPONCE, userIdResponce);
//                        editor.putString(Constants.USER_NAME_ON_RESPONCE, userNameResponce);

            /*editor.putString(Constants.EMAIL_ID, userEmailResponce);
            editor.putString(Constants.USER_NAME, userNameResponce);
            editor.putString(Constants.USER_ID, userIdResponce);*/
            editor.putString(Constants.USER_ID, String.valueOf(userID));
            editor.commit();
            intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
            finish();
        }
    }


    private void getOtpCall() {
        dialogUtil.progressDialog(OtpGeneratorActivity.this, "Please Wait..");
        String url = "";
        try {
            url = Constants.varify + URLEncoder.encode(varifyMob, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                dialogUtil.stopProgressDialog();
                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject(response);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    resendStatus = jsonObject.getString("status");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    resendCode = jsonObject.getString("otp");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                preferences = getSharedPreferences(Constants.SHAREDPREFERENCES_NAME, MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString(Constants.MOBILE_NUM_SEND, varifyMob);
                editor.commit();

                if (resendStatus.equalsIgnoreCase("success")) {
                    //Toast.makeText(getApplicationContext(), "success" + " " + resendCode, Toast.LENGTH_LONG).show();
                    Toast.makeText(getApplicationContext(),"OTP code sent on Phone Number",Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplication(), "OTP not generated, try again", Toast.LENGTH_LONG).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                dialogUtil.stopProgressDialog();
                Toast.makeText(OtpGeneratorActivity.this, "something went wrong in getting OTP", Toast.LENGTH_SHORT).show();
                error.printStackTrace();
            }
        });
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(15000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
       // requestQueue.add(stringRequest);
        AppController.getInstance().getRequestQueue().add(stringRequest);
    }
}
