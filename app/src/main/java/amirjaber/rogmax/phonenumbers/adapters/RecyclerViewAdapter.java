package amirjaber.rogmax.phonenumbers.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import amirjaber.rogmax.phonenumbers.R;
import amirjaber.rogmax.phonenumbers.models.Model;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private Context context;
    private List<Model> results;

    public RecyclerViewAdapter(Context context, List<Model> results) {
        this.context = context;
        this.results = results;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_list_view, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Model model = results.get(position);
        holder.textViewCountry.setText(model.getCountry());
        holder.textViewService.setText(model.getService());
        holder.textViewNumber.setText(model.getNumber());

    }

    @Override
    public int getItemCount() {
        return results.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        Long id;
        TextView textViewCountry, textViewService, textViewNumber;

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        ViewHolder(View itemView) {
            super(itemView);
            textViewCountry = itemView.findViewById(R.id.textCountry);
            textViewService = itemView.findViewById(R.id.textService);
            textViewNumber = itemView.findViewById(R.id.textNumber);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

            Long id = getId();
            Toast.makeText(context, id.toString(), Toast.LENGTH_SHORT).show();

        }
    }
}
