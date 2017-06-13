package newton.android.skistar.Models;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.ArrayList;

import newton.android.skistar.R;

public class ListAdapter extends ArrayAdapter<Run> {

    private ArrayList<Run> dataSet;

    // View lookup cache
    private static class ViewHolder {
        TextView date;
        TextView lift;
        TextView location;
        TextView height;
    }

    public ListAdapter(ArrayList<Run> data, Context context) {
        super(context, R.layout.list_item, data);
        this.dataSet = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Run run = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag

        if (convertView == null) {

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.list_item, parent, false);
            viewHolder.date = (TextView) convertView.findViewById(R.id.date_listItem);
            viewHolder.lift = (TextView) convertView.findViewById(R.id.lift_listItem);
            viewHolder.location = (TextView) convertView.findViewById(R.id.location_listItem);
            viewHolder.height = (TextView) convertView.findViewById(R.id.height_listItem);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        assert run != null;
        viewHolder.date.setText(run.getSwipeTime());
        viewHolder.lift.setText(run.getLiftName());
        viewHolder.location.setText(run.getName());
        viewHolder.height.setText(run.getHeight());

        // Return the completed view to render on screen
        return convertView;
    }
}

