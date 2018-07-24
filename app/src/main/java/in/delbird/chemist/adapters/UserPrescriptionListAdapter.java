package in.delbird.chemist.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;
import in.delbird.chemist.Interface.ShopListInterface;
import in.delbird.chemist.R;
import in.delbird.chemist.models.UserPrescriptionListModel;

public class UserPrescriptionListAdapter extends RecyclerView.Adapter<UserPrescriptionListAdapter.RecyclerViewHolders> {

    // ShopListModel shopListModel;
    int prev_size, updatedSize;
    ArrayList<UserPrescriptionListModel> shopModelArrayList = new ArrayList<>();
    ShopListInterface shopListInterface;
    String username, email;
    HashMap<String, Integer> cartMap = new HashMap<>();
    private Context context;

    public UserPrescriptionListAdapter(Context context, ArrayList<UserPrescriptionListModel> shopModelArrayList) {
        //this.shopListModel = shopListModel;
        this.context = context;
        this.shopModelArrayList = shopModelArrayList;
//        this.shopListInterface = shopListInterface;
    }

    public void add(ArrayList<UserPrescriptionListModel> shopModelArrayList) {
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
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_row_prescription_list, null);
        RecyclerViewHolders rcv = new RecyclerViewHolders(layoutView);
        return rcv;
    }

    @Override
    public void onBindViewHolder(final RecyclerViewHolders holder, final int position) {
        final UserPrescriptionListModel shopModel = shopModelArrayList.get(position);
        if (shopModelArrayList != null) {
            //Log.e("shopName", String.valueOf(shopListModel.getNext_page_token()));

            holder.medicineName.setText(shopModel.getProductName());
            holder.quantity.setText(shopModel.getDoseMorning() + "-" + shopModel.getDoseNoon() + "-"
                    + shopModel.getDoseNight() + " " + shopModel.getFoodPref());
            holder.duration.setText(shopModel.getDurationDays() + " " + shopModel.getDurationType());

            holder.instructions.setText(shopModel.getInstructions());
        }
    }


    //Returns the total number of items in the data set hold by the adapter.
    @Override
    public int getItemCount() {
        return shopModelArrayList.size();
    }

    public static class RecyclerViewHolders extends RecyclerView.ViewHolder {
        @Bind(R.id.medicine_name)
        TextView medicineName;
        @Bind(R.id.duration)
        TextView duration;
        @Bind(R.id.quantity)
        TextView quantity;
        @Bind(R.id.instructions)
        TextView instructions;


        RecyclerViewHolders(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}


