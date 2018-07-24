package in.delbird.chemist.adapters;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.demach.konotor.Konotor;

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
import in.delbird.chemist.activity.ChemistListActivity;
import in.delbird.chemist.activity.InvoiceDetailActivity;
import in.delbird.chemist.models.InvoiceListItemModel;
import in.delbird.chemist.models.RetailerModel;

public class InvoiceListAdapter extends RecyclerView.Adapter<InvoiceListAdapter.RecyclerViewHolders> {

    // ShopListModel shopListModel;
    int prev_size, updatedSize;
    ArrayList<InvoiceListItemModel> shopModelArrayList = new ArrayList<>();
    ShopListInterface shopListInterface;
    String username, email;
    HashMap<String, Integer> cartMap = new HashMap<>();


    private Context context;

    public InvoiceListAdapter(Context context, ArrayList<InvoiceListItemModel> shopModelArrayList) {
        //this.shopListModel = shopListModel;
        this.context = context;
        this.shopModelArrayList = shopModelArrayList;
//        this.shopListInterface = shopListInterface;
    }

    public void add(List<InvoiceListItemModel> shopModelArrayList) {
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
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_row_order_history_list, null);
        RecyclerViewHolders rcv = new RecyclerViewHolders(layoutView);
        return rcv;
    }

    @Override
    public void onBindViewHolder(final RecyclerViewHolders holder, final int position) {
        final InvoiceListItemModel shopModel = shopModelArrayList.get(position);
        if (shopModelArrayList != null) {
            //Log.e("shopName", String.valueOf(shopListModel.getNext_page_token()));

            holder.shopNameTV.setText(shopModel.getInvoiceMasterId()+"");
            holder.date.setText(shopModel.getCreatedAt());
            holder.mrp.setText((shopModel.getPaymentAmount())+"");
            holder.paymentMode.setText(shopModel.getPaymentMode());
            holder.status.setText(shopModel.getStatus());
            holder.status.setHighlightColor(Color.GREEN);

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, InvoiceDetailActivity.class);
                    intent.putExtra(InvoiceDetailActivity.MASTER_ID,shopModel.getInvoiceMasterId());
                    context.startActivity(intent);
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

    public void remove(RetailerModel shopModel) {
        shopModelArrayList.remove(shopModel);
        notifyDataSetChanged();
    }

    public static class RecyclerViewHolders extends RecyclerView.ViewHolder {
        @Bind(R.id.shopNameTV)
        GothumMediumTextView shopNameTV;
        @Bind(R.id.date)
        GothamLightTextView date;
        @Bind(R.id.mrp)
        GothamLightTextView mrp;
        @Bind(R.id.payment_mode)
        GothamLightTextView paymentMode;
        @Bind(R.id.status)
        TextView status;

        RecyclerViewHolders(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}


