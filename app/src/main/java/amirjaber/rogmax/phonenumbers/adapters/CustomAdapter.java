package amirjaber.rogmax.phonenumbers.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class CustomAdapter extends ArrayAdapter<String> {

    private int hideItemIndex;

    public CustomAdapter(@NonNull Context context, int resource, @NonNull List<String> objects, int hideItemIndex) {
        super(context, resource, objects);
        this.hideItemIndex = hideItemIndex;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v;
        if (position == hideItemIndex) {
            TextView textView = new TextView(getContext());
            textView.setVisibility(View.GONE);
            textView.setHeight(0);
            v = textView;
        } else {
            v = super.getDropDownView(position, null, parent);
        }
        return v;
    }
}
