package in.delbird.chemist.adapters;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * Created by Dharmendra on 1/2/16.
 */
public class SampleAdapter extends BaseAdapter {
    @Override
    public int getCount() {
        return 10;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
//            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.sample_single_row, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.name.setText("Ravi");
        holder.age.setText("24");
        return convertView;
    }

    public class ViewHolder {
        private TextView name;
        private TextView age;

        public ViewHolder(View convertView) {
//            name = convertView.findViewById(R.id.name);
//            age = convertView.findViewById(R.id.age);
        }
    }
}
