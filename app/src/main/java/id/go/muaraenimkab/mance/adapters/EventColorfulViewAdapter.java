package id.go.muaraenimkab.mance.adapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bluejamesbond.text.DocumentView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import id.go.muaraenimkab.mance.R;
import id.go.muaraenimkab.mance.activities.FullImageEvent;
import id.go.muaraenimkab.mance.activities.MainActivity;
import id.go.muaraenimkab.mance.fragments.LokasiEventFragment;
import id.go.muaraenimkab.mance.models.Event;
import id.go.muaraenimkab.mance.utils.Utilities;

public class EventColorfulViewAdapter extends RecyclerView.Adapter<EventColorfulViewAdapter.DataObjectHolder> {
    private Context context;
    private ArrayList<Event> mListEvent;

    public EventColorfulViewAdapter(Context context, ArrayList<Event> mListEvent) {
        this.context = context;
        this.mListEvent = mListEvent;
    }

    @NonNull
    @Override
    public EventColorfulViewAdapter.DataObjectHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_event_colorful, parent, false);
        return new EventColorfulViewAdapter.DataObjectHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull EventColorfulViewAdapter.DataObjectHolder holder, @SuppressLint("RecyclerView") final int position) {
        holder.lbljudulEvent.setText(mListEvent.get(position).getNama_event());
        Picasso.with(context)
                .load(Utilities.getBaseURLImageEvent() + mListEvent.get(position).getGambar_event())
                .fit()
                .centerCrop()
                .into(holder.imgEvent);
        holder.lblDesEvent.setText(mListEvent.get(position).getDeskripsi());

        if(!mListEvent.get(position).getTanggal_pembukaan().equals(mListEvent.get(position).getTanggal_penutupan())) {
            holder.tanggalevent.setText(mListEvent.get(position).getTanggal_pembukaan() + " s.d. " + mListEvent.get(position).getTanggal_penutupan());
        }else {
            holder.tanggalevent.setText(mListEvent.get(position).getTanggal_pembukaan());
        }

        holder.btnLokasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Uri uri = Uri.parse("google.navigation:q="+mListEvent.get(position).getLat()+","+mListEvent.get(position).getLng());
//                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
//                intent.setPackage("com.google.android.apps.maps");
//                context.startActivity(intent);

                MainActivity.replaceFragment(LokasiEventFragment.newInstance(mListEvent.get(position).getLat(), mListEvent.get(position).getLng(), mListEvent.get(position).getAlamat(), mListEvent.get(position).getNama_event()), 6);
            }
        });

        holder.imgEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =  new Intent(context, FullImageEvent.class);
                intent.putExtra("img", mListEvent.get(position).getGambar_event());
                intent.putExtra("event", mListEvent.get(position).getNama_event());
                if(!mListEvent.get(position).getTanggal_pembukaan().equals(mListEvent.get(position).getTanggal_penutupan())) {
                    intent.putExtra("tgl", mListEvent.get(position).getTanggal_pembukaan()+" s.d "+mListEvent.get(position).getTanggal_penutupan());
                }else {
                    intent.putExtra("tgl", mListEvent.get(position).getTanggal_pembukaan());
                }
                intent.putExtra("des", mListEvent.get(position).getDeskripsi());
                intent.putExtra("loc", mListEvent.get(position).getAlamat());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mListEvent.size();
    }


    class DataObjectHolder extends RecyclerView.ViewHolder {
        RelativeLayout rlimgkategori;
        ImageView imgEvent;
        TextView lbljudulEvent, tanggalevent, lblDesEvent;
        ImageView btnLokasi;

        DataObjectHolder(View itemView) {
            super(itemView);
            rlimgkategori = itemView.findViewById(R.id.rlimgkategori);
            imgEvent = itemView.findViewById(R.id.imgkategori);
            lbljudulEvent = itemView.findViewById(R.id.lbljudul);
            tanggalevent = itemView.findViewById(R.id.lbltgl);
            lblDesEvent = itemView.findViewById(R.id.lblisi);
            btnLokasi = itemView.findViewById(R.id.ivmaps);

            Display display = ((Activity) context).getWindowManager().getDefaultDisplay();
            final DisplayMetrics outMetrics = new DisplayMetrics();
            display.getMetrics(outMetrics);
            int viewPagerWidth = Math.round(outMetrics.widthPixels);
            int more = viewPagerWidth / 6;
            int viewPagerHeight = (Math.round(outMetrics.widthPixels) / 3) + more;

            rlimgkategori.setLayoutParams(new RelativeLayout.LayoutParams(viewPagerWidth, viewPagerHeight));
        }
    }
}
