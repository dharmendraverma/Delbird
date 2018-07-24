package in.delbird.chemist.activity;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.Bind;
import in.delbird.chemist.R;

public class WalletActivity extends ActionBarActivity implements View.OnClickListener{


    RadioButton option1;
    RadioButton option2;
    RadioButton option3;
    RadioButton option4;
    TextView walletRecharge;
    TextView currentWallet;
    static int amount=0;


    private TextView mTitle;

    private ImageView chat_IV,cart_IV;
    LinearLayout mSearchProductLayout;

    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet);

      //  proceed=(TextView)findViewById(R.id.delivery_proceed);
      //  proceed.setOnClickListener(this);

        cart_IV=(ImageView)findViewById(R.id.image_cart);
        chat_IV=(ImageView)findViewById(R.id.image_chat);
        chat_IV.setVisibility(View.INVISIBLE);
        cart_IV.setVisibility(View.INVISIBLE);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        currentWallet=(TextView)findViewById(R.id.current_balance);
        currentWallet.setText(""+amount);
        option1=(RadioButton)findViewById(R.id.option1);
        option1.setOnClickListener(this);
        option2=(RadioButton)findViewById(R.id.option2);
        option2.setOnClickListener(this);
        option3=(RadioButton)findViewById(R.id.option3);
        option3.setOnClickListener(this);
        option4=(RadioButton)findViewById(R.id.option4);
        option4.setOnClickListener(this);
        TextView mTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
        mTitle.setVisibility(View.VISIBLE);
        mTitle.setText("My Wallet");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        walletRecharge=(TextView)findViewById(R.id.wallet_recharge);
        walletRecharge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //  startActivity(new Intent(WalletActivity.this,RechargeWalletActivity.class));
                if (option1.isChecked()) {
                   amount= Integer.parseInt(currentWallet.getText().toString());
                    if(amount==10000){
                        Toast.makeText(getApplicationContext(), "Maximum Limit Reached", Toast.LENGTH_SHORT).show();
                        amount=amount-100;
                    }
                    amount=amount+100;
                    currentWallet.setText(""+amount);
                    //rechargeWallet(Integer.parseInt(option1.getText().toString().substring(3)));
                } else if (option2.isChecked()) {
                    amount= Integer.parseInt(currentWallet.getText().toString());
                    if(amount==10000){
                        Toast.makeText(getApplicationContext(), "Maximum Limit Reached", Toast.LENGTH_SHORT).show();
                        amount=amount-200;
                    }
                    amount=amount+200;
                    currentWallet.setText(""+amount);
                   // rechargeWallet(Integer.parseInt(option2.getText().toString().substring(3)));
                } else if (option3.isChecked()) {
                    amount= Integer.parseInt(currentWallet.getText().toString());
                    if(amount==10000){
                        Toast.makeText(getApplicationContext(), "Maximum Limit Reached", Toast.LENGTH_SHORT).show();
                        amount=amount-300;
                    }
                    amount=amount+300;
                    currentWallet.setText(""+amount);
                   // rechargeWallet(Integer.parseInt(option3.getText().toString().substring(3)));
                } else if (option4.isChecked()) {
                    amount= Integer.parseInt(currentWallet.getText().toString());
                    if(amount==10000){
                        Toast.makeText(getApplicationContext(), "Maximum Limit Reached", Toast.LENGTH_SHORT).show();
                        amount=amount-500;
                    }
                    amount=amount+500;
                    currentWallet.setText(""+amount);
                   // rechargeWallet(Integer.parseInt(option4.getText().toString().substring(3)));
                }
            }
        });
        mSearchProductLayout=(LinearLayout)findViewById(R.id.search_product_Layout);
        mSearchProductLayout.setVisibility(View.GONE);

    }

    private void setRadioButtonSelection(boolean option1, boolean option2, boolean option3, boolean option4) {
        this.option1.setChecked(option1);
        this.option2.setChecked(option2);
        this.option3.setChecked(option3);
        this.option4.setChecked(option4);

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
        switch (v.getId()){
            case R.id.option1:
                setRadioButtonSelection(true, false, false, false);
                break;
            case R.id.option2:
                setRadioButtonSelection(false, true, false, false);
                break;
            case R.id.option3:
                setRadioButtonSelection(false, false, true, false);
                break;
            case R.id.option4:
                setRadioButtonSelection(false, false, false, true);
                break;
            //case R.id.delivery_proceed:
             //   startActivity(new Intent(this,CheckoutActivity.class));
        }
    }

    public int getAmount(){
        return amount;
    }

    public void setAmount(int amount){
        this.amount=amount;
    }
}