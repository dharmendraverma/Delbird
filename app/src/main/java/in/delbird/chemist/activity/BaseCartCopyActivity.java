package in.delbird.chemist.activity;

import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import in.delbird.chemist.CustomViews.GothamBoldTextView;
import in.delbird.chemist.R;
import in.delbird.chemist.models.CartModel;

/**
 * Created by Dharmendra on 3/4/16.
 */
public abstract class BaseCartCopyActivity extends AppCompatActivity {


    TextView cartCount;
    GothamBoldTextView mCartCheckOut;
    TextView mCartProceed;

    @Override
    protected void onResume() {
        super.onResume();
        cartCount = (TextView) findViewById(R.id.cart_count);
        mCartCheckOut=(GothamBoldTextView)findViewById(R.id.cart_check_out);
        mCartProceed=(TextView)findViewById(R.id.cart_proceed);
        updateCartQuantity();
    }

    public void updateCartQuantity(){
        int quantity = CartModel.getCartModelInstance().getCartItemsCount();
        if(quantity==0){ mCartCheckOut.setVisibility(View.GONE);}
        if(quantity>0){
            int amount =(int)(CartModel.getCartModelInstance().getTotalAmount());
            cartCount.setText(quantity + "");
            mCartCheckOut.setVisibility(View.VISIBLE);
            mCartCheckOut.setText("Proceed to Cart (Rs."+amount+")");
            mCartProceed.setVisibility(View.VISIBLE);
            cartCount.setVisibility(View.VISIBLE);
        }else {
            mCartCheckOut.setVisibility(View.GONE);
            mCartProceed.setVisibility(View.GONE);
            cartCount.setVisibility(View.GONE);
        }

    }
}
