package in.delbird.chemist.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import in.delbird.chemist.CustomViews.GothamLightTextView;
import in.delbird.chemist.R;
import in.delbird.chemist.models.AddressModel;
import in.delbird.chemist.models.CartModel;

/**
 * Created by Dharmendra on 3/4/16.
 */
public class AddressActivity extends AppCompatActivity {

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
    @Bind(R.id.address1)
    EditText address1;
    @Bind(R.id.city)
    EditText city;
    @Bind(R.id.state)
    EditText state;
    @Bind(R.id.pincode)
    EditText pincode;
    @Bind(R.id.house_no)
    EditText houseno;
    @Bind(R.id.proceed_to_payment)
    TextView proceedtopayment;
    @Bind(R.id.option1)
    RadioButton option1;
    @Bind(R.id.option2)
    RadioButton option2;

    private Toolbar mToolbar;
    private TextView mTitle;

    private ImageView chatIV, cart_IV;
    LinearLayout mSearchProductLayout;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address);
        ButterKnife.bind(this);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mTitle = (TextView) mToolbar.findViewById(R.id.toolbar_title);
        mTitle.setVisibility(View.VISIBLE);
        mTitle.setText("Checkout");
        mToolbar.setTitle("Checkout");
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        chatIV = (ImageView) findViewById(R.id.image_chat);
        cart_IV = (ImageView) findViewById(R.id.image_cart);
        cart_IV.setVisibility(View.INVISIBLE);
        chatIV.setVisibility(View.INVISIBLE);
        mSearchProductLayout = (LinearLayout) findViewById(R.id.search_product_Layout);
        mSearchProductLayout.setVisibility(View.GONE);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @OnClick(R.id.proceed_to_payment)
    public void onClick() {
        AddressModel addressModel = CartModel.getCartModelInstance().getAddressModel();
        if (validateData() && addressModel != null) {
            addressModel.setAddress1(houseno.getText().toString() + address1.getText().toString());
            addressModel.setCity(city.getText().toString());
            addressModel.setState(state.getText().toString());
            addressModel.setPin(pincode.getText().toString());
            addressModel.setDeliveryType(option1.isChecked()?"PickUp":"Delivery");
            Intent intent = new Intent(this, CheckoutActivity.class);
            startActivity(intent);
        }
    }

    private boolean validateData() {
        if (!isValidUserName(houseno.getText().toString())) {
            Toast.makeText(getApplicationContext(), "Please Enter House No", Toast.LENGTH_LONG).show();
            houseno.requestFocus();
            return false;
        }
        if (!isValidUserName(address1.getText().toString())) {
            Toast.makeText(getApplicationContext(), "Please Enter Street Name", Toast.LENGTH_LONG).show();
            address1.requestFocus();
            return false;
        }
        if (!isValidUserName(city.getText().toString())) {
            Toast.makeText(getApplicationContext(), "Enter City", Toast.LENGTH_LONG).show();
            city.requestFocus();
            return false;
        }
        if (!isValidUserName(state.getText().toString())) {
            Toast.makeText(getApplicationContext(), "Enter State", Toast.LENGTH_LONG).show();
            state.requestFocus();
            return false;
        }
        // System.out.println(pincode.getText().length());
        if ((pincode.getText().toString().length() != 6)) {
            if (pincode.getText().toString().length() != 6) {
                System.out.println(pincode.getText().length());
                Toast.makeText(getApplicationContext(), "Please enter 6 digit pincode", Toast.LENGTH_LONG).show();
            }
            pincode.requestFocus();
            return false;
        }
        return true;
    }

    private boolean isValidUserName(String name) {
        if (name.length() < 1) {
            return false;
        } else {
            return true;
        }
    }
}
