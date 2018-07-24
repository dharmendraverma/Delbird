package in.delbird.chemist.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
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

import in.delbird.chemist.Controller.AppController;
import in.delbird.chemist.Controller.Constants;
import in.delbird.chemist.Controller.Validations;
import in.delbird.chemist.R;
import in.delbird.chemist.models.BaseErrorResponseModel;
import in.delbird.chemist.utils.DialogUtil;

public class ProfileChangeActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView mTitle;
    private EditText phoneET, userET, emailET,addressET,cityET,stateET,pincodeET;
    private Button updateBT;
    String username;
    String email;
    private ImageView profileIV;
    String phone;
    String user_id;
    String address;
    String city;
    String state;
    String pincode;
    private RequestQueue requestQueue;
    String status;
    String latitude_string;
    String longitude_string;
    DialogUtil dialogUtil = new DialogUtil();


    public SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    Toolbar toolbar;
    private String user_id1;
    private ImageView chat_IV;

    @Override
    protected void onResume() {
        super.onResume();

        init();
//        profileIV.bringToFront();
        getValueFromSharedPreference();
        if (user_id == null) {
            Intent intent = new Intent(ProfileChangeActivity.this, MainActivity.class);
            Toast.makeText(getApplicationContext(), "User Does Not Exist", Toast.LENGTH_SHORT).show();
            startActivity(intent);
            finish();
        }
        setValueInViews();

        updateBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Validations validations = new Validations();

                if (validations.isValidEmail(emailET.getText().toString()) == false) {
                    Toast.makeText(getApplicationContext(), "Invalid Email Id", Toast.LENGTH_SHORT).show();
                    Log.e("email id", emailET.getText().toString());
                } else if (validations.isValidUserName(userET.getText().toString()) == false) {
                    Toast.makeText(getApplicationContext(), "Invalid User Name", Toast.LENGTH_SHORT).show();
                    Log.e("user name", userET.getText().toString());
                } else {
                    getJsonObjectResponse();
                }
            }
        });
    }

    private void setValueInViews() {
        userET.setText(username);
        phoneET.setText(phone);
        emailET.setText(email);
//        addressET.setText(address);
    //    cityET.setText(city);
    //    stateET.setText(state);
    //    pincodeET.setText(pincode);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_change);
        requestQueue = Volley.newRequestQueue(ProfileChangeActivity.this);
    }

    private void getJsonObjectResponse() {
        String url = "";

        String userUpdatedName = userET.getText().toString().replace(" ","%20");
       // String abc=userET.getText().toString().replace("","%20");
        String userUpdatedEmail = emailET.getText().toString();
      //  address = addressET.getText().toString();
        //city = cityET.getText().toString();
      //  state = stateET.getText().toString();
        //pincode=pincodeET.getText().toString();
      //  Double latitude=Double.parseDouble(latitude_string);
      //  Double longitude=Double.parseDouble(longitude_string);
        url = String.format(Constants.createUser, user_id, userUpdatedName, userUpdatedEmail, "2",
                "male","23/2/1992");
        dialogUtil.progressDialog(ProfileChangeActivity.this,"Please wait...");


        System.out.println("updated url"+url);
        //  final JSONObject jsonObject = new JSONObject();
        //  try {
//            //Intent intent = getIntent();
//            // String mob = intent.getStringExtra(Constants.MOBILE_NUMBER);
//            preferences = getSharedPreferences(Constants.SHAREDPREFERENCES_NAME, MODE_PRIVATE);
//            String mob = preferences.getString(Constants.MOBILE_NUM_SEND,"0");

        //  jsonObject.put("mobile_no", phone);
        //  jsonObject.put("name", userUpdatedName);
        //  jsonObject.put("email", userUpdatedEmail);
        //   jsonObject.put("user_id", user_id);
        // } catch (JSONException e) {
        //     e.printStackTrace();
        //  }
        StringRequest jsonObjectRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //BaseErrorResponseModel baseErrorResponseModel = new GsonBuilder().create().fromJson(response, BaseErrorResponseModel.class);
                dialogUtil.stopProgressDialog();
                //String s = null;
                // Log.e("Status", response.toString());
                try {
                    BaseErrorResponseModel baseErrorResponseModel = new GsonBuilder().create().fromJson(response, BaseErrorResponseModel.class);
                    if (baseErrorResponseModel.getStatus().equalsIgnoreCase("success")) {

                        getValuesFromViews();
                        putValuesInSharedPreference();
                        Toast.makeText(getApplicationContext(), "Successfully Updated", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(ProfileChangeActivity.this, StartPageActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(getApplicationContext(), "User Does Not Exist", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(ProfileChangeActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
//                User user=new GsonBuilder().create().fromJson(response.toString(),User.class);

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {

                dialogUtil.stopProgressDialog();
                Toast.makeText(ProfileChangeActivity.this, "Error from server", Toast.LENGTH_LONG).show();
                error.printStackTrace();

            }
        });
        requestQueue.add(jsonObjectRequest);
        AppController.getInstance().getRequestQueue().add(jsonObjectRequest);

    }
    private void getValuesFromViews() {
        username = userET.getText().toString().replace("%20"," ");
        email = emailET.getText().toString();
//        address = addressET.getText().toString();
  //      city = cityET.getText().toString();
  //      state = stateET.getText().toString();
  //      pincode=pincodeET.getText().toString();

    }

    private void getValueFromSharedPreference() {
        username = sharedPreferences.getString(Constants.USER_NAME, null);
        email = sharedPreferences.getString(Constants.EMAIL_ID, null);
        phone = sharedPreferences.getString(Constants.MOBILE_NUM_SEND, null);
        user_id = sharedPreferences.getString(Constants.USER_ID, null);


         if(username!=null)
         username=username.replace("%20"," ");
       // address = sharedPreferences.getString(Constants.ADDRESS, null);
       // city = sharedPreferences.getString(Constants.CITY, null);
       // state = sharedPreferences.getString(Constants.STATE, null);
       // pincode=sharedPreferences.getString(Constants.PINCODE,null);
       // latitude_string=sharedPreferences.getString(Constants.LATITUDE_ID,"0");
       // longitude_string=sharedPreferences.getString(Constants.LONGITUDE_ID,"0");
    }

    private void putValuesInSharedPreference() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(Constants.USER_NAME, username);
        editor.putString(Constants.EMAIL_ID, email);
       // editor.putString(Constants.ADDRESS,address);
       // editor.putString(Constants.CITY,city);
       // editor.putString(Constants.STATE,state);
       // editor.putString(Constants.PINCODE,pincode);
        editor.commit();
    }

    public void init() {

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        mTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mTitle.setText("My Profile");
        profileIV = (ImageView) findViewById(R.id.profileIV);
        userET = (EditText) findViewById(R.id.ed_user);
        phoneET = (EditText) findViewById(R.id.ed_phone);
        emailET = (EditText) findViewById(R.id.ed_email);
      //  addressET = (EditText) findViewById(R.id.ed_address);
      //  cityET = (EditText) findViewById(R.id.ed_city);
      //  stateET = (EditText) findViewById(R.id.ed_state);
       // pincodeET = (EditText) findViewById(R.id.ed_pincode);

        updateBT = (Button) findViewById(R.id.update_btn);
        sharedPreferences = getSharedPreferences(Constants.SHAREDPREFERENCES_NAME, MODE_PRIVATE);
      //  chat_IV = (ImageView) findViewById(R.id.image_chat);
//        chat_IV.setVisibility(View.INVISIBLE);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_btn:
                break;
        }
    }
}
