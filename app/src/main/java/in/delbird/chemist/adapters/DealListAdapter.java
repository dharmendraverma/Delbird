package in.delbird.chemist.adapters;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import in.delbird.chemist.Controller.Constants;
import in.delbird.chemist.R;
import in.delbird.chemist.activity.ShopListActivity;
import in.delbird.chemist.models.DealModel;

/**
 * Created by Dharmendra on 21/1/16.
 */
public class DealListAdapter extends BaseAdapter {

    private final Context context;

    private ImageView imageViewProfile, imageViewPhone, imageViewDistance;
    private TextView textViewShopName, textViewAddress, textViewCategory, textViewPhoneNumber, textViewDistance;
    LinearLayout linearLayoutParent;
    ArrayList<DealModel> dealModelArrayList = new ArrayList<>();


    public DealListAdapter(Context context, ArrayList<DealModel> dealModelArrayList) {
        this.context = context;
        this.dealModelArrayList = dealModelArrayList;
    }

    public void changeData(ArrayList<DealModel> dealModelArrayList) {
        this.dealModelArrayList = dealModelArrayList;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return dealModelArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return dealModelArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return dealModelArrayList.get(position).getShop_id();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.single_row_dealist, null);
        }
        imageViewProfile = (ImageView) convertView.findViewById(R.id.shopPicIV);
        imageViewPhone = (ImageView) convertView.findViewById(R.id.iv_phone);
        imageViewDistance = (ImageView) convertView.findViewById(R.id.iv_distance);
        textViewShopName = (TextView) convertView.findViewById(R.id.shopNameTV);
        textViewAddress = (TextView) convertView.findViewById(R.id.shopAddressTV);
        textViewCategory = (TextView) convertView.findViewById(R.id.shopCategoryTV);
        textViewDistance = (TextView) convertView.findViewById(R.id.tv_distance);
        textViewPhoneNumber = (TextView) convertView.findViewById(R.id.shopPhoneTV);
        textViewShopName.setText(dealModelArrayList.get(position).getShop_name());
        textViewPhoneNumber.setText(dealModelArrayList.get(position).getShop_mobile_no());
        textViewCategory.setText(dealModelArrayList.get(position).getShop_type());
        textViewAddress.setText(dealModelArrayList.get(position).getShop_address());

        textViewDistance.setText(dealModelArrayList.get(position).getDistance().toString() + " km");
        Log.e("distance", dealModelArrayList.get(position).getDistance().toString());

        imageViewPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent phoneIntent = new Intent(Intent.ACTION_CALL);
                phoneIntent.setData(Uri.parse("tel:" +dealModelArrayList.get(position).getShop_mobile_no()));
                if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    Toast.makeText(context,"Application does not have required permisiion",Toast.LENGTH_SHORT).show();
                    return;
                }
                context.startActivity(phoneIntent);
            }
        });
//        textViewCategory.setText(dealModelArrayList.get);
        //category left
        linearLayoutParent = (LinearLayout) convertView.findViewById(R.id.parent_layout);
        linearLayoutParent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ShopListActivity.class);
                intent.putExtra(Constants.SHOP_MODEL, dealModelArrayList.get(position));
                context.startActivity(intent);
            }
        });
        Picasso.with(context).load(dealModelArrayList.get(position).getPic_url()).placeholder(R.drawable.default_user_img).into(imageViewProfile);

        return convertView;
    }
}
