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
import id.go.muaraenimkab.bappeda.muaraenimterpadu.fragments.KontakFragment;
import id.go.muaraenimkab.bappeda.muaraenimterpadu.models.Kontak;
import id.go.muaraenimkab.bappeda.muaraenimterpadu.utils.Utilities;

public class KontakViewAdapter extends RecyclerView.Adapter<KontakViewAdapter.DataObjectHolder> {
    private Context context;
    private ArrayList<Kontak> mListKontak;


    public KontakViewAdapter(Context context, ArrayList<Kontak> mListKontak) {
        this.context = context;
        this.mListKontak = mListKontak;
    }

    @NonNull
    @Override
    public DataObjectHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_kategori, parent, false);
        return new DataObjectHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final DataObjectHolder holder, @SuppressLint("RecyclerView") final int position) {
        holder.lbljudulkontak.setText(mListKontak.get(position).getNama_kantor());

        Picasso.with(context)
                .load(Utilities.getURLImageKontak() + mListKontak.get(position).getGambar_kantor())
                .fit()
                .centerCrop()
                .into(holder.imgkontak);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.replaceFragment(DetailKontakFragment.newInstance(mListKontak.get(position).getNama_kantor(),
                        mListKontak.get(position).getLat(),mListKontak.get(position).getLng(),mListKontak.get(position).getAlamat(),
                        mListKontak.get(position).getNo_tlp(),Utilities.getURLImageKontak() + mListKontak.get(position).getGambar_kantor()), 5);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mListKontak.size();
    }

    class DataObjectHolder extends RecyclerView.ViewHolder {
        ImageView imgkontak;
        TextView lbljudulkontak;
        RelativeLayout rlimgkategori;

        DataObjectHolder(View itemView) {
            super(itemView);
            imgkontak = itemView.findViewById(R.id.imgkategori);
            lbljudulkontak = itemView.findViewById(R.id.lbljudulkategori);
            rlimgkategori=itemView.findViewById(R.id.rlimgkategori);

            Display display = ((Activity) context).getWindowManager().getDefaultDisplay();
            final DisplayMetrics outMetrics = new DisplayMetrics();
            display.getMetrics(outMetrics);
            int viewPagerWidth = Math.round(outMetrics.widthPixels);
            int viewPagerHeight = Math.round(outMetrics.widthPixels)/2;

            rlimgkategori.setLayoutParams(new RelativeLayout.LayoutParams(viewPagerWidth, viewPagerHeight));
        }
    }
}