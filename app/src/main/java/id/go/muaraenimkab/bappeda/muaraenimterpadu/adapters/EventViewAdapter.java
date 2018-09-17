package id.go.muaraenimkab.bappeda.muaraenimterpadu.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

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
//        holder.content.setText(mListEvent.get(position).getNama_kategori_pariwisata());
//        Picasso.with(context)
//                .load(Utilities.getURLImagePariwisata() + mListContent.get(position).getGambar_kategori_pariwisata())
//                .into(holder.imgContent);
//
//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return 5;
    }


    class DataObjectHolder extends RecyclerView.ViewHolder {
        ImageView imgContent;
        TextView content;

        DataObjectHolder(View itemView) {
            super(itemView);
            imgContent = itemView.findViewById(R.id.imgContent);
            content = itemView.findViewById(R.id.lblContent);
        }
    }
}
