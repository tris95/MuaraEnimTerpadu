package id.go.muaraenimkab.bappeda.muaraenimterpadu.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bluejamesbond.text.DocumentView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import id.go.muaraenimkab.bappeda.muaraenimterpadu.R;
import id.go.muaraenimkab.bappeda.muaraenimterpadu.models.Content;
import id.go.muaraenimkab.bappeda.muaraenimterpadu.models.Event;
import id.go.muaraenimkab.bappeda.muaraenimterpadu.utils.Utilities;

public class EventViewAdapter extends RecyclerView.Adapter<EventViewAdapter.DataObjectHolder> {
    private Context context;
    private ArrayList<Event> mListEvent;

    public EventViewAdapter(Context context, ArrayList<Event> mListEvent) {
        this.context = context;
        this.mListEvent = mListEvent;
    }

    @NonNull
    @Override
    public EventViewAdapter.DataObjectHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_event, parent, false);
        return new EventViewAdapter.DataObjectHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EventViewAdapter.DataObjectHolder holder, int position) {
        holder.lbljudulEvent.setText(mListEvent.get(position).getNama_event());
        Picasso.with(context)
                .load(Utilities.getBaseURLImageEvent() + mListEvent.get(position).getGambar_event())
                .into(holder.imgEvent);
        holder.lblDesEvent.setText(mListEvent.get(position).getDeskripsi());
        holder.tanggalevent.setText(mListEvent.get(position).getTanggal_pembukaan()+" - "+mListEvent.get(position).getTanggal_penutupan());
    }

    @Override
    public int getItemCount() {
        return mListEvent.size();
    }


    class DataObjectHolder extends RecyclerView.ViewHolder {
        ImageView imgEvent;
        TextView lbljudulEvent, tanggalevent;
        DocumentView lblDesEvent;

        DataObjectHolder(View itemView) {
            super(itemView);
            imgEvent = itemView.findViewById(R.id.imgEvent);
            lbljudulEvent = itemView.findViewById(R.id.lbljudulEvent);
            tanggalevent = itemView.findViewById(R.id.tanggalevent);
            lblDesEvent = itemView.findViewById(R.id.lblDesEvent);
        }
    }
}
