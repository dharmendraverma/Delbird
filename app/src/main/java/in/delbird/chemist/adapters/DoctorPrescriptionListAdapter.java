package in.delbird.chemist.adapters;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
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
import in.delbird.chemist.activity.ShopListActivity;
import in.delbird.chemist.activity.UserPrescriptionListActivity;
import in.delbird.chemist.models.CartModel;
import in.delbird.chemist.models.DoctorPrescriptionModel;

public class DoctorPrescriptionListAdapter extends RecyclerView.Adapter<DoctorPrescriptionListAdapter.RecyclerViewHolders> {

    // ShopListModel shopListModel;
    int prev_size, updatedSize;
    ArrayList<DoctorPrescriptionModel> shopModelArrayList = new ArrayList<>();
    private Context context;
    ShopListInterface shopListInterface;
    String username, email;
    HashMap<String, Integer> cartMap = new HashMap<>();

    public DoctorPrescriptionListAdapter(Context context, ArrayList<DoctorPrescriptionModel> shopModelArrayList) {
        //this.shopListModel = shopListModel;
        this.context = context;
        this.shopModelArrayList = shopModelArrayList;
//        this.shopListInterface = shopListInterface;
    }

    public void add(ArrayList<DoctorPrescriptionModel> shopModelArrayList) {
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
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.doctor_prescription_list, null);
        RecyclerViewHolders rcv = new RecyclerViewHolders(layoutView);
        return rcv;
    }

    @Override
    public void onBindViewHolder(final RecyclerViewHolders holder, final int position) {
        final DoctorPrescriptionModel shopModel = shopModelArrayList.get(position);
        if (shopModelArrayList != null) {
            //Log.e("shopName", String.valueOf(shopListModel.getNext_page_token()));

            holder.doctorName.setText(shopModel.getDoctorName() );
            holder.createdAt.setText(shopModel.getCreatedAt());
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, UserPrescriptionListActivity.class);
                    intent.putExtra("id",shopModelArrayList.get(position).getPrescriptionMasterId());
                    context.startActivity(intent);
                }
            });
        }

    }

    public void descriptionProduct() {
        // ((ShopListActivity)context).launchProductDetailActivity(shopModel);
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
        @Bind(R.id.doctor_name)
        TextView doctorName;
        @Bind(R.id.created_at)
        TextView createdAt;
        //      GothamLightTextView medicineQuantity;

        RecyclerViewHolders(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

}


