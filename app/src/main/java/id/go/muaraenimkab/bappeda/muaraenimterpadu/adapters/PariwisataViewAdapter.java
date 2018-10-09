package id.go.muaraenimkab.bappeda.muaraenimterpadu.adapters;

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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import id.go.muaraenimkab.bappeda.muaraenimterpadu.R;
import id.go.muaraenimkab.bappeda.muaraenimterpadu.activities.MainActivity;
import id.go.muaraenimkab.bappeda.muaraenimterpadu.fragments.BeritaFragment;
import id.go.muaraenimkab.bappeda.muaraenimterpadu.fragments.DetailKulinerFragment;
import id.go.muaraenimkab.bappeda.muaraenimterpadu.fragments.DetailWisataFragment;
import id.go.muaraenimkab.bappeda.muaraenimterpadu.models.Pariwisata;
import id.go.muaraenimkab.bappeda.muaraenimterpadu.utils.Utilities;

public class PariwisataViewAdapter extends RecyclerView.Adapter<PariwisataViewAdapter.DataObjectHolder> {
    private Context context;
    private ArrayList<Pariwisata> mListPariwisata;
    private String jumlahtempat;


    public PariwisataViewAdapter(Context context, ArrayList<Pariwisata> mListPariwisata,String jumlahtempat) {
        this.context = context;
        this.mListPariwisata = mListPariwisata;
        this.jumlahtempat=jumlahtempat;
    }

    @NonNull
    @Override
    public PariwisataViewAdapter.DataObjectHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_kategori, parent, false);
        return new PariwisataViewAdapter.DataObjectHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final PariwisataViewAdapter.DataObjectHolder holder, @SuppressLint("RecyclerView") final int position) {
        holder.lbljudulkategori.setText(mListPariwisata.get(position).getNama_pariwisata());

        Picasso.with(context)
                .load(Utilities.getURLImagePariwisata() + mListPariwisata.get(position).getGambar_pariwisata())
                .into(holder.imgkategoriBerita);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (jumlahtempat.equals("1"))
                    MainActivity.replaceFragment(DetailKulinerFragment.newInstance(mListPariwisata.get(position).getId_pariwisata(), mListPariwisata.get(position).getNama_pariwisata(),Utilities.getURLImagePariwisata() + mListPariwisata.get(position).getGambar_pariwisata()), 6);
                else
                    MainActivity.replaceFragment(DetailWisataFragment.newInstance(mListPariwisata.get(position).getId_pariwisata(), mListPariwisata.get(position).getNama_pariwisata(),Utilities.getURLImagePariwisata() + mListPariwisata.get(position).getGambar_pariwisata(), mListPariwisata.get(position).getDeskripsi_pariwisata(), mListPariwisata.get(position).getLat(), mListPariwisata.get(position).getLng()), 6);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mListPariwisata.size();
    }

    class DataObjectHolder extends RecyclerView.ViewHolder {
        ImageView imgkategoriBerita;
        TextView lbljudulkategori;

        DataObjectHolder(View itemView) {
            super(itemView);
            imgkategoriBerita = itemView.findViewById(R.id.imgkategori);
            lbljudulkategori = itemView.findViewById(R.id.lbljudulkategori);

            Display display = ((Activity) context).getWindowManager().getDefaultDisplay();
            final DisplayMetrics outMetrics = new DisplayMetrics();
            display.getMetrics(outMetrics);
            int viewPagerWidth = Math.round(outMetrics.widthPixels);
            int viewPagerHeight = Math.round(outMetrics.widthPixels)/2;

            imgkategoriBerita.setLayoutParams(new RelativeLayout.LayoutParams(viewPagerWidth, viewPagerHeight));
        }
    }
}
