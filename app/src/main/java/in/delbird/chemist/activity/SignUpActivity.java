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

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.GsonBuilder;

import org.json.JSONException;
import org.json.JSONObject;


import in.delbird.chemist.Controller.Constants;
import in.delbird.chemist.R;
import in.delbird.chemist.models.BaseErrorResponseModel;
import in.delbird.chemist.utils.DialogUtil;

/**
 * Created by dharmendra on 21/1/16.
 */
public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView textView1, textView2, textView3;
    private ImageView imageViewUser, imageVieweEmail;
    private EditText editTextUser, editTextEmail;
    private Button buttonLogin;
    private RequestQueue requestQueue;
    String userName, emailId, userId;
    public SharedPreferences preferences;
    private String mobileNumSignUp;
    DialogUtil dialogUtil = new DialogUtil();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        init();
    }

    public void init() {

        buttonLogin = (Button) findViewById(R.id.login_btn);
        buttonLogin.setOnClickListener(this);
        textView2 = (TextView) findViewById(R.id.tv_blinkSign);
        textView3 = (TextView) findViewById(R.id.tv_dellbirdSign);

        editTextUser = (EditText) findViewById(R.id.ed_user);
        editTextEmail = (EditText) findViewById(R.id.ed_mail);
        requestQueue = Volley.newRequestQueue(SignUpActivity.this);
        preferences = getSharedPreferences(Constants.SHAREDPREFERENCES_NAME, MODE_PRIVATE);
        mobileNumSignUp = preferences.getString(Constants.OTP_MOBILENUM, "0");
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.login_btn:

                userName = editTextUser.getText().toString().replace(" ","%20");
                emailId =  editTextEmail.getText().toString();
                 Log.e("u", userName);

                if (isValidUserName(userName)) {
                    if (isValidEmail(emailId)) {
                        createUser();
                    } else {
                        Toast.makeText(getApplicationContext(), "Enter valid email ID", Toast.LENGTH_LONG).show();
                        editTextEmail.requestFocus();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Enter valid user name", Toast.LENGTH_LONG).show();
                    editTextUser.requestFocus();
                }
                break;
        }
    }

    private boolean isValidEmail(String email) {
        Log.e("Email Validations", String.valueOf(android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()));
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private boolean isValidUserName(String name) {
        if (name.length() < 1) {
            return false;
        } else {
            return true;
        }
    }

    private void createUser() {
        dialogUtil.progressDialog(SignUpActivity.this, "Please Wait..");
        String url = "";
        preferences = getSharedPreferences(Constants.SHAREDPREFERENCES_NAME, MODE_PRIVATE);
        final String user_id = preferences.getString(Constants.USER_ID, "0");
        System.out.println("User_id"+user_id);
        url = String.format(Constants.createUser,user_id,userName,emailId,Constants.GCM_ID,"male","10/12/1992");

        /*final JSONObject jsonObject = new JSONObject();
        try {
            preferences = getSharedPreferences(Constants.SHAREDPREFERENCES_NAME, MODE_PRIVATE);
            String mob = preferences.getString(Constants.MOBILE_NUM_SEND, "0");
            jsonObject.put("mobile_no", mob);
            jsonObject.put("name", userName);
            jsonObject.put("email", emailId);
        } catch (JSONException e) {
            e.printStackTrace();
        }*/

        StringRequest jsonObjectRequest = new StringRequest(Request.Method.POST, url,  new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                dialogUtil.stopProgressDialog();
                //String userId;

                try {
                    //jsonObject1.has("");
                    BaseErrorResponseModel baseErrorResponseModel = new GsonBuilder().create().fromJson(response, BaseErrorResponseModel.class);

                    if(baseErrorResponseModel.getStatus().equalsIgnoreCase("success")) {
                        //userId = response.getString("user_id");
                        Toast.makeText(getApplicationContext(), "User Successfully Registered ", Toast.LENGTH_SHORT).show();
                        preferences = getSharedPreferences(Constants.SHAREDPREFERENCES_NAME, MODE_PRIVATE);
                        SharedPreferences.Editor editor = preferences.edit();
                        //editor.putString(Constants.USER_ID, userId);
                        editor.putString(Constants.USER_NAME, userName);
                        editor.putString(Constants.EMAIL_ID, emailId);
                        editor.putBoolean(Constants.IS_ALREADY_LOGGED_IN, true);
                        //Log.e("user_id", userId);
                        Log.e("email_id", emailId);
                        Log.e("user_name", userName);
//                    Log.e("",userId);
                        editor.commit();
                        Log.e("userId", user_id);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                Intent intent = new Intent(SignUpActivity.this, StartPageActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                finish();

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {

                dialogUtil.stopProgressDialog();
                Toast.makeText(SignUpActivity.this, "Something went wrong in creating user", Toast.LENGTH_LONG).show();
                error.printStackTrace();

            }
        });
        requestQueue.add(jsonObjectRequest);
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra(Constants.MOBILE_NUMBER, getIntent().getStringExtra(Constants.MOBILE_NUMBER));
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}