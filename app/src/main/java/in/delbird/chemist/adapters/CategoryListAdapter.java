package in.delbird.chemist.adapters;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import in.delbird.chemist.Controller.Constants;
import in.delbird.chemist.R;
import in.delbird.chemist.activity.ShopListActivity;
import in.delbird.chemist.models.CategoryModel;
import in.delbird.chemist.utils.CategoryNamesUtil;

/**
 * Created by Dharmendra on 2/2/16.
 */
public class CategoryListAdapter extends BaseAdapter {

    Context context;
    private ImageView foodIV;
    private TextView foodTV;
    ArrayList<CategoryModel> categoryModelArrayList = new ArrayList<>();
    private LinearLayout linearLayoutTop;
    //CategoryNamesUtil categoryNamesUtil = new CategoryNamesUtil();
    public SharedPreferences sharedPreferences;

    public CategoryListAdapter(Context context, ArrayList<CategoryModel> categoryModelArrayList) {
        this.context = context;
        this.categoryModelArrayList = categoryModelArrayList;
        //categoryNamesUtil.initializeHashMap();
    }

    public void changeData(ArrayList<CategoryModel> categoryModelArrayList) {
        this.categoryModelArrayList = categoryModelArrayList;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return categoryModelArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return categoryModelArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.single_row_category, null, false);

        }

        foodTV = (TextView) convertView.findViewById(R.id.foodTV);
        foodIV = (ImageView) convertView.findViewById(R.id.foodIV);

        //String Categorykey = categoryModelArrayList.get(position).getCategory_type();
        foodTV.setText(categoryModelArrayList.get(position).getName());

        // foodTV.setText(categoryModelArrayList.get(position).getCategory_type());
        Picasso.with(context).load(categoryModelArrayList.get(position).getIcon()).placeholder(R.drawable.default_user_img).into(foodIV);

        linearLayoutTop = (LinearLayout) convertView.findViewById(R.id.top_layout);
        linearLayoutTop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ShopListActivity.class);
                intent.putExtra("category", categoryModelArrayList.get(position));

                sharedPreferences = context.getSharedPreferences(Constants.SHAREDPREFERENCES_NAME, context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(Constants.CATEGORY, String.valueOf(categoryModelArrayList.get(position).getId()));
                editor.commit();

                Log.e("category", categoryModelArrayList.get(position).getName());
                context.startActivity(intent);

            }
        });
        return convertView;

    }
}
