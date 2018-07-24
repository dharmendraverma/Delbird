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
public abstract class BaseCartActivity extends AppCompatActivity {


    TextView cartCount;

    @Override
    protected void onResume() {
        super.onResume();
        cartCount = (TextView) findViewById(R.id.cart_count);
       // mCartCheckOut=(in.delbird.chemist.CustomViews.GothamBoldTextView)findViewById(R.id.cart_check_out);
        updateCartQuantity();
    }

    public void updateCartQuantity() {
        int quantity = CartModel.getCartModelInstance().getCartItemsCount();
        // if(quantity==0){ mCartCheckOut.setVisibility(View.GONE);}
        if (quantity > 0) {
            cartCount.setText(quantity + "");
            // mCartCheckOut.setVisibility(View.VISIBLE);
            //  mCartCheckOut.setText("Proceed to Cart (Rs."+CartModel.getCartModelInstance().getTotalAmount()+")          ->");
            cartCount.setVisibility(View.GONE);
        } else {
            // mCartCheckOut.setVisibility(View.GONE);
            cartCount.setVisibility(View.GONE);
        }

    }
}
