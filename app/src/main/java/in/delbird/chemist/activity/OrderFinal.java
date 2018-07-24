package in.delbird.chemist.activity;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import in.delbird.chemist.R;

public class OrderFinal extends ActionBarActivity implements View.OnClickListener{

    private Toolbar mToolbar;
    private TextView mTitle;

    private ImageView chatIV,cart_IV;
    LinearLayout mSearchProductLayout;
    TextView mFinalStartShopping;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_final);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mTitle = (TextView) mToolbar.findViewById(R.id.toolbar_title);
        mTitle.setVisibility(View.VISIBLE);
        mTitle.setText("      Order Successfully Placed");
        mToolbar.setTitle("      Order Successfully Placed");
        setSupportActionBar(mToolbar);
     //   getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        chatIV = (ImageView) findViewById(R.id.image_chat);
        cart_IV=(ImageView) findViewById(R.id.image_cart);
        cart_IV.setVisibility(View.INVISIBLE);
        chatIV.setVisibility(View.INVISIBLE);
        mSearchProductLayout=(LinearLayout)findViewById(R.id.search_product_Layout);
        mSearchProductLayout.setVisibility(View.GONE);
        mFinalStartShopping=(TextView)findViewById(R.id.final_start_shopping);
        mFinalStartShopping.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.final_start_shopping){
            startActivity(new Intent(this,StartPageActivity.class));
        }
    }
}
