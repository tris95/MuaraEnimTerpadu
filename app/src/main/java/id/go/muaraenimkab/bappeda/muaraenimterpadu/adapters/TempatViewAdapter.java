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
import id.go.muaraenimkab.bappeda.muaraenimterpadu.fragments.DetailKontakFragment;
import id.go.muaraenimkab.bappeda.muaraenimterpadu.fragments.DetailWisataFragment;
import id.go.muaraenimkab.bappeda.muaraenimterpadu.models.TempatPariwisata;
import id.go.muaraenimkab.bappeda.muaraenimterpadu.utils.Utilities;

public class TempatViewAdapter extends RecyclerView.Adapter<TempatViewAdapter.DataObjectHolder> {
    private Context context;
    private ArrayList<TempatPariwisata> mListtempatPariwisata;
    String gambar;


    public TempatViewAdapter(Context context, ArrayList<TempatPariwisata> mListtempatPariwisata) {
        this.context = context;
        this.mListtempatPariwisata = mListtempatPariwisata;
    }

    @NonNull
    @Override
    public TempatViewAdapter.DataObjectHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_kategori, parent, false);
        return new TempatViewAdapter.DataObjectHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final TempatViewAdapter.DataObjectHolder holder, @SuppressLint("RecyclerView") final int position) {
        holder.lblnamatempat.setText(mListtempatPariwisata.get(position).getNama_tempat_pariwisata());
        gambar=Utilities.getBaseURLImageTempat() + mListtempatPariwisata.get(position).getGambar_lokasi();

        Picasso.with(context)
                .load(gambar)
                .into(holder.imgtempat);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    MainActivity.replaceFragment(DetailKontakFragment.newInstance(mListtempatPariwisata.get(position).getNama_tempat_pariwisata(),
                            mListtempatPariwisata.get(position).getLat(),mListtempatPariwisata.get(position).getLng(),
                            mListtempatPariwisata.get(position).getAlamat(), mListtempatPariwisata.get(position).getNo_hp(), gambar), 8);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mListtempatPariwisata.size();
    }

    class DataObjectHolder extends RecyclerView.ViewHolder {
        ImageView imgtempat;
        TextView lblnamatempat;

        DataObjectHolder(View itemView) {
            super(itemView);
            imgtempat = itemView.findViewById(R.id.imgkategori);
            lblnamatempat = itemView.findViewById(R.id.lbljudulkategori);

            Display display = ((Activity) context).getWindowManager().getDefaultDisplay();
            final DisplayMetrics outMetrics = new DisplayMetrics();
            display.getMetrics(outMetrics);
            int viewPagerWidth = Math.round(outMetrics.widthPixels);
            int viewPagerHeight = Math.round(outMetrics.widthPixels)/2;

            imgtempat.setLayoutParams(new RelativeLayout.LayoutParams(viewPagerWidth, viewPagerHeight));
        }
    }
}

