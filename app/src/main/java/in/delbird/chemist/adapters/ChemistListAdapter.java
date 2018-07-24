package in.delbird.chemist.adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.demach.konotor.Konotor;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;
import in.delbird.chemist.Controller.Constants;
import in.delbird.chemist.CustomViews.GothamLightTextView;
import in.delbird.chemist.CustomViews.GothumMediumTextView;
import in.delbird.chemist.Interface.ShopListInterface;
import in.delbird.chemist.R;
import in.delbird.chemist.activity.ChemistListActivity;
import in.delbird.chemist.models.RetailerModel;

public class ChemistListAdapter extends RecyclerView.Adapter<ChemistListAdapter.RecyclerViewHolders> {

    // ShopListModel shopListModel;
    int prev_size, updatedSize;
    ArrayList<RetailerModel> shopModelArrayList = new ArrayList<>();
    ShopListInterface shopListInterface;
    String username, email;
    HashMap<String, Integer> cartMap = new HashMap<>();

    private Context context;

    public ChemistListAdapter(Context context, ArrayList<RetailerModel> shopModelArrayList) {
        //this.shopListModel = shopListModel;
        this.context = context;
        this.shopModelArrayList = shopModelArrayList;
//        this.shopListInterface = shopListInterface;
    }

    public void add(RetailerModel shopModelArrayList) {
        this.shopModelArrayList.add(shopModelArrayList);
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
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_row_retailer_list, null);
        RecyclerViewHolders rcv = new RecyclerViewHolders(layoutView);
        return rcv;
    }

    @Override
    public void onBindViewHolder(final RecyclerViewHolders holder, final int position) {
        final RetailerModel shopModel = shopModelArrayList.get(position);
        if (shopModelArrayList != null) {
            //Log.e("shopName", String.valueOf(shopListModel.getNext_page_token()));

            holder.shopNameTV.setText(shopModel.getName());
            holder.shopAddressTV.setText(shopModel.getAddressLine1() + "\n" + shopModel.getCity()
                    + "\n" + shopModel.getState() + "\n" + shopModel.getZip());

            holder.shopremove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ((ChemistListActivity) context).removeChemist(shopModel);

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
        @Bind(R.id.shopAddressTV)
        GothamLightTextView shopAddressTV;
        @Bind(R.id.shopdistance)
        GothamLightTextView shopdistance;
        @Bind(R.id.shopremove)
        ImageView shopremove;

        RecyclerViewHolders(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}


