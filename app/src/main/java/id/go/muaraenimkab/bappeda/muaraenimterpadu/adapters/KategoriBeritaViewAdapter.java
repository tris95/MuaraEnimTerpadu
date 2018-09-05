package id.go.muaraenimkab.bappeda.muaraenimterpadu.adapters;


import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import id.go.muaraenimkab.bappeda.muaraenimterpadu.R;
import id.go.muaraenimkab.bappeda.muaraenimterpadu.activities.MainActivity;
import id.go.muaraenimkab.bappeda.muaraenimterpadu.fragments.BeritaFragment;
import id.go.muaraenimkab.bappeda.muaraenimterpadu.models.KategoriBerita;

public class KategoriBeritaViewAdapter extends RecyclerView.Adapter<KategoriBeritaViewAdapter.DataObjectHolder> {
    private Context context;
    private ArrayList<KategoriBerita> mListKategoriBerita;


    public KategoriBeritaViewAdapter(Context context, ArrayList<KategoriBerita> mListKategoriBerita) {
        this.context = context;
        this.mListKategoriBerita = mListKategoriBerita;
    }

    @NonNull
    @Override
    public DataObjectHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_kategori, parent, false);
        return new DataObjectHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final DataObjectHolder holder, @SuppressLint("RecyclerView") final int position) {
        holder.imgkategoriBerita.setImageResource(R.drawable.jalan);
        holder.lbljudulkategori.setText("Politik");

//        Glide.with(context)
//                .load(Utilities.getBaseURLImageProduk() + mListDatasetProduct.get(position).get(0).getGambar())
//                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
//                .placeholder(R.color.colorDivider)
//                .into(holder.ivProductImage);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.replaceFragment(BeritaFragment.newInstance(), 6);
            }
        });

    }

    @Override
    public int getItemCount() {
        return 5;
    }
    class DataObjectHolder extends RecyclerView.ViewHolder {
        ImageView imgkategoriBerita;
        TextView lbljudulkategori;

        DataObjectHolder(View itemView) {
            super(itemView);
            imgkategoriBerita = itemView.findViewById(R.id.imgkategori);
            lbljudulkategori = itemView.findViewById(R.id.lbljudulkategori);
        }
    }
}
