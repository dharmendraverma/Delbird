package in.delbird.chemist.adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.demach.konotor.Konotor;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;
import in.delbird.chemist.Controller.Constants;
import in.delbird.chemist.CustomViews.GothamLightTextView;
import in.delbird.chemist.CustomViews.GothumMediumTextView;
import in.delbird.chemist.Interface.ShopListInterface;
import in.delbird.chemist.R;
import in.delbird.chemist.activity.CartActivity;
import in.delbird.chemist.activity.StartPageActivity;
import in.delbird.chemist.models.CartModel;
import in.delbird.chemist.models.ShopModel;

public class CartListAdapter extends RecyclerView.Adapter<CartListAdapter.RecyclerViewHolders> {

    // ShopListModel shopListModel;
    int prev_size, updatedSize;
    ArrayList<ShopModel> shopModelArrayList = new ArrayList<>();

    private Context context;
    ShopListInterface shopListInterface;
    String username, email;
    int cartValue=0;
    HashMap<String, Integer> cartMap = new HashMap<>();

    public CartListAdapter(Context context) {
        //this.shopListModel = shopListModel;
        this.context = context;
        this.shopModelArrayList = CartModel.getCartModelInstance().getProductsList();
//        this.shopListInterface = shopListInterface;
    }

    public void add(ArrayList<ShopModel> shopModelArrayList) {
        this.shopModelArrayList.addAll(shopModelArrayList);
        notifyDataSetChanged();
//        prev_size = this.shopModelArrayList.size();
//        updatedSize = prev_size + shopModelArrayList.size();
//        notifyItemRangeChanged(0,updatedSize);
    }

    public void clearData() {

        shopModelArrayList.clear();
        notifyDataSetChanged();
    }

    //This method calls onCreateViewHolder(ViewGroup, int)
    // to create a new RecyclerView.ViewHolder and initializes some private fields to be used by RecyclerView.
    @Override
    public RecyclerViewHolders onCreateViewHolder(ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_row_cart_list, null);
        RecyclerViewHolders rcv = new RecyclerViewHolders(layoutView);
        return rcv;
    }

    @Override
    public void onBindViewHolder(final RecyclerViewHolders holder, final int position) {
        final ShopModel shopModel = shopModelArrayList.get(position);
        if (shopModelArrayList != null) {
            //Log.e("shopName", String.valueOf(shopListModel.getNext_page_token()));

            holder.shopNameTV.setText(shopModel.getProductName());
            holder.quantity.setText(CartModel.getCartModelInstance().getThisProductQuantity(shopModel.getProductId()) + "");
            holder.shopAddressTV.setText(shopModel.getDescription());
            holder.mrp.setText(shopModel.getPrice() + "");
            if (shopModel.getRetailer() != null) {
                holder.chemistName.setText(shopModel.getRetailer().getName());
                holder.discount.setText(shopModel.getRetailer().getPriceDiscounted() + "");
                holder.sellPrice.setText((int)(shopModel.getPrice() * (1 - (shopModel.getRetailer().getPriceDiscounted() / 100.0))) + "");
            } else {
                holder.discount.setText("0");
                holder.sellPrice.setText(shopModel.getPrice() + "");
            }
            Picasso.with(context).load(shopModel.getImageUrl()).placeholder(R.drawable.default_user_img).into(holder.shopPicIV);

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ((CartActivity) context).launchProductDetailActivity(shopModel);
                }
            });
            //final ArrayList<ShopModel> shopModelArrayList = shopListModel.getResults();
            /*Log.e("size", String.valueOf(shopModelArrayList.size()));
            holder.shopNameTV.setText(String.valueOf(shopModel.getShop_name()));
            holder.shopAddressTV.setText(shopModel.getAddress());
            holder.shopDistanceTV.setText(String.valueOf(shopModel.getDistance()) + " km");
            initialize_konotor();
            holder.linearLayoutParent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (context instanceof CartActivity) {
                        ((CartActivity) context).getKonotorChat();
                    }
                }
            });*/



            holder.ivPlus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (((CartActivity) context).getRetailerIdForList().isEmpty()) {
                        Toast.makeText(context, "Please Select Chemists", Toast.LENGTH_SHORT).show();
                    } else {
                        System.out.println("iv_plus called");
                        holder.quantity.setText(manageCartForProduct(true, shopModel,false) + "");
                        ((CartActivity)context).updateButtonText();
//                        ((StartPageActivity)context).updateCartText();
                        //((CartActivity)context).updateCartQuantity(quantity--);
                       // ((CartActivity)context).cartCount(cartValue++);
                        System.out.println(cartValue);
                    }
                }
            });
            holder.ivMinus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (((CartActivity) context).getRetailerIdForList().isEmpty()) {
                        Toast.makeText(context, "Please Select Chemists", Toast.LENGTH_SHORT).show();
                    } else {
                        long quantity = manageCartForProduct(false, shopModel,false);
                        System.out.println("iv_minus called");
                        System.out.println("quantity="+quantity);
                        holder.quantity.setText(quantity + "");
                        if (quantity <= 0) {
                            shopModelArrayList.remove(shopModel);
                            notifyDataSetChanged();
                        }
                        ((CartActivity)context).updateButtonText();
                      //  ((StartPageActivity)context).updateCartText();
                       //((CartActivity)context).cartCount(cartValue--);
                        System.out.println(cartValue);
                    }
                }
            });

            holder.ivRemoveItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    long quantity = manageCartForProduct(false, shopModel,true);
                    shopModelArrayList.remove(shopModel);
                    System.out.println("in Remove");
                    notifyDataSetChanged();
                    ((CartActivity)context).updateButtonText();
                }
            });
        }

    }

    private long manageCartForProduct(boolean isIncremented, ShopModel shopModel,boolean remove) {
        long cartItems;
        if (isIncremented) {
            cartItems = CartModel.getCartModelInstance().addProduct(shopModel);
        } else {
            cartItems = CartModel.getCartModelInstance().decrementProductQuantity(shopModel.getProductId());
        }
        if(remove) {
            ((CartActivity) context).updateCartQuantity();
            CartModel.getCartModelInstance().removeProduct(shopModel.getProductId());
            ((CartActivity) context).updateCartQuantity();
        }
        else{
            ((CartActivity) context).updateCartQuantity();
        }
        return cartItems;
    }

    private void initialize_konotor() {
        getValuesFromSharedPreference();
        Konotor.getInstance(context)
                .withUserName(username)            // optional name by which to display the user
                .withIdentifier("" + email)            // optional unique identifier for your reference
                .withUserEmail("" + email)        // optional email address of the user
                .withLaunchMainActivityOnFinish(true) // to launch your app on hitting the back button on Konotor's inbox interface, in case the app was not running already
                .withWelcomeMessage("Welcome To Bharatam Jayate")
                .withUsesCustomSupportImage(true)
                .withFeedbackScreenTitle("Customer Support")
                .init(Constants.delbird3Konotorid, Constants.getDelbird3Konotorkey);
    }


    private void getValuesFromSharedPreference() {
        SharedPreferences sharedPreferences;
        sharedPreferences = context.getSharedPreferences(Constants.SHAREDPREFERENCES_NAME, context.MODE_PRIVATE);
        username = sharedPreferences.getString(Constants.USER_NAME, null);
        email = sharedPreferences.getString(Constants.EMAIL_ID, null);

    }

    //Returns the total number of items in the data set hold by the adapter.
    @Override
    public int getItemCount() {
        return shopModelArrayList.size();
    }

    public static class RecyclerViewHolders extends RecyclerView.ViewHolder {
        @Bind(R.id.shopPicIV)
        ImageView shopPicIV;
        @Bind(R.id.shopNameTV)
        GothumMediumTextView shopNameTV;
        @Bind(R.id.shopAddressTV)
        GothamLightTextView shopAddressTV;
        @Bind(R.id.chemistName)
        GothamLightTextView chemistName;
        @Bind(R.id.mrp)
        GothamLightTextView mrp;
        @Bind(R.id.sellPrice)
        GothamLightTextView sellPrice;
        @Bind(R.id.discount)
        GothamLightTextView discount;
        @Bind(R.id.iv_minus)
        ImageView ivMinus;
        @Bind(R.id.quantity)
        GothamLightTextView quantity;
        @Bind(R.id.iv_plus)
        ImageView ivPlus;
        @Bind(R.id.iv_remove_item)
        ImageView ivRemoveItem;

        RecyclerViewHolders(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}


