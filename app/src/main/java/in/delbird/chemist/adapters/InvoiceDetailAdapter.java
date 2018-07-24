package in.delbird.chemist.adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.demach.konotor.Konotor;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import in.delbird.chemist.Controller.Constants;
import in.delbird.chemist.CustomViews.GothamLightTextView;
import in.delbird.chemist.CustomViews.GothumMediumTextView;
import in.delbird.chemist.Interface.ShopListInterface;
import in.delbird.chemist.R;
import in.delbird.chemist.models.InvoiceDetailItemModel;

public class InvoiceDetailAdapter extends RecyclerView.Adapter<InvoiceDetailAdapter.RecyclerViewHolders> {

    // ShopListModel shopListModel;
    int prev_size, updatedSize;
    ArrayList<InvoiceDetailItemModel> shopModelArrayList = new ArrayList<>();
    ShopListInterface shopListInterface;
    String username, email;
    HashMap<String, Integer> cartMap = new HashMap<>();



    private Context context;

    public InvoiceDetailAdapter(Context context, ArrayList<InvoiceDetailItemModel> shopModelArrayList) {
        //this.shopListModel = shopListModel;
        this.context = context;
        this.shopModelArrayList = shopModelArrayList;
//        this.shopListInterface = shopListInterface;
    }

    public void add(List<InvoiceDetailItemModel> shopModelArrayList) {
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
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_row_invoice_detail_list, null);
        RecyclerViewHolders rcv = new RecyclerViewHolders(layoutView);
        return rcv;
    }

    @Override
    public void onBindViewHolder(final RecyclerViewHolders holder, final int position) {
        final InvoiceDetailItemModel shopModel = shopModelArrayList.get(position);
        if (shopModelArrayList != null) {
            //Log.e("shopName", String.valueOf(shopListModel.getNext_page_token()));

            holder.shopNameTV.setText(shopModel.getProductName());
            holder.mrp.setText(shopModel.getProductPrice()+"");
            holder.quantity.setText(shopModel.getQuantity()+"");
            holder.totalPrice.setText(Long.valueOf(shopModel.getProductPrice())*shopModel.getQuantity()+"");
            Picasso.with(context).load(shopModel.getImage_url()).placeholder(R.drawable.default_user_img).into(holder.shopPicIV);
            holder.shopAddressTV.setText(shopModel.getDescription());
            /*holder.shopAddressTV.setText(shopModel.getUserMobileNo());
            holder.chemistAddress.setText(shopModel.getPickupMode());
*/
           /* holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });*/
            //final ArrayList<ShopModel> shopModelArrayList = shopListModel.getResults();
            /*Log.e("size", String.valueOf(shopModelArrayList.size()));
            holder.shopNameTV.setText(String.valueOf(shopModel.getShop_name()));
            holder.shopAddressTV.setText(shopModel.getAddress());
            holder.shopDistanceTV.setText(String.valueOf(shopModel.getDistance()) + " km");
            initialize_konotor();
            holder.linearLayoutParent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (context instanceof ShopListActivity) {
                        ((ShopListActivity) context).getKonotorChat();
                    }
                }
            });*/


        }

    }


    private void initialize_konotor() {
        getValuesFromSharedPreference();
        Konotor.getInstance(context)
                .withUserName(username)            // optional name by which to display the user
                .withIdentifier("" + email)            // optional unique identifier for your reference
                .withUserEmail("" + email)        // optional email address of the user
                .withLaunchMainActivityOnFinish(true) // to launch your app on hitting the back button on Konotor's inbox interface, in case the app was not running already
                .withWelcomeMessage("Welcome TO Delbird")
                .withUsesCustomSupportImage(true)
                .withFeedbackScreenTitle("Delbird")
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
        @Bind(R.id.mrp)
        GothamLightTextView mrp;
        @Bind(R.id.totalPrice)
        GothamLightTextView totalPrice;
        @Bind(R.id.quantity)
        GothamLightTextView quantity;

        RecyclerViewHolders(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}


