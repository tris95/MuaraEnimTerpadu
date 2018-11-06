package id.go.muaraenimkab.mance.adapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bluejamesbond.text.DocumentView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import id.go.muaraenimkab.mance.R;
import id.go.muaraenimkab.mance.activities.MainActivity;
import id.go.muaraenimkab.mance.fragments.LokasiEventFragment;
import id.go.muaraenimkab.mance.models.Content;
import id.go.muaraenimkab.mance.models.Event;
import id.go.muaraenimkab.mance.utils.Utilities;

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
    public void onBindViewHolder(@NonNull EventViewAdapter.DataObjectHolder holder, @SuppressLint("RecyclerView") final int position) {
        holder.lbljudulEvent.setText(mListEvent.get(position).getNama_event());
        Picasso.with(context)
                .load(Utilities.getBaseURLImageEvent() + mListEvent.get(position).getGambar_event())
                .fit()
                .centerCrop()
                .into(holder.imgEvent);
        holder.lblDesEvent.setText(mListEvent.get(position).getDeskripsi());
        holder.tanggalevent.setText(mListEvent.get(position).getTanggal_pembukaan() + " s.d. " + mListEvent.get(position).getTanggal_penutupan());

        holder.btnLokasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Uri uri = Uri.parse("google.navigation:q="+mListEvent.get(position).getLat()+","+mListEvent.get(position).getLng());
//                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
//                intent.setPackage("com.google.android.apps.maps");
//                context.startActivity(intent);

                MainActivity.replaceFragment(LokasiEventFragment.newInstance(mListEvent.get(position).getLat(), mListEvent.get(position).getLng(), mListEvent.get(position).getAlamat(), mListEvent.get(position).getNama_event()), 5);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mListEvent.size();
    }


    class DataObjectHolder extends RecyclerView.ViewHolder {
        ImageView imgEvent;
        TextView lbljudulEvent, tanggalevent;
        DocumentView lblDesEvent;
        Button btnLokasi;

        DataObjectHolder(View itemView) {
            super(itemView);
            imgEvent = itemView.findViewById(R.id.imgEvent);
            lbljudulEvent = itemView.findViewById(R.id.lbljudulEvent);
            tanggalevent = itemView.findViewById(R.id.tanggalevent);
            lblDesEvent = itemView.findViewById(R.id.lblDesEvent);
            btnLokasi = itemView.findViewById(R.id.btnLokasi);

            Display display = ((Activity) context).getWindowManager().getDefaultDisplay();
            final DisplayMetrics outMetrics = new DisplayMetrics();
            display.getMetrics(outMetrics);
            int viewPagerWidth = Math.round(outMetrics.widthPixels);
            int more = viewPagerWidth / 4;
            int viewPagerHeight = (Math.round(outMetrics.widthPixels) / 2) + more;

            imgEvent.setLayoutParams(new LinearLayout.LayoutParams(viewPagerWidth, viewPagerHeight));
        }
    }
}
