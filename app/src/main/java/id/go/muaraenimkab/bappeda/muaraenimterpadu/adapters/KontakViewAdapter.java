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
import id.go.muaraenimkab.bappeda.muaraenimterpadu.fragments.DetailKontakFragment;
import id.go.muaraenimkab.bappeda.muaraenimterpadu.fragments.KontakFragment;
import id.go.muaraenimkab.bappeda.muaraenimterpadu.models.Kontak;

public class KontakViewAdapter extends RecyclerView.Adapter<KontakViewAdapter.DataObjectHolder>  {
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
        holder.imgkontak.setImageResource(R.drawable.jalan);
        holder.lbljudulkontak.setText("Pemadap Kebakaran");

//        Glide.with(context)
//                .load(Utilities.getBaseURLImageProduk() + mListDatasetProduct.get(position).get(0).getGambar())
//                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
//                .placeholder(R.color.colorDivider)
//                .into(holder.ivProductImage);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.replaceFragment(DetailKontakFragment.newInstance(), 5);
            }
        });

    }

    @Override
    public int getItemCount() {
        return 5;
    }
    class DataObjectHolder extends RecyclerView.ViewHolder {
        ImageView imgkontak;
        TextView lbljudulkontak;

        DataObjectHolder(View itemView) {
            super(itemView);
            imgkontak = itemView.findViewById(R.id.imgkategori);
            lbljudulkontak = itemView.findViewById(R.id.lbljudulkategori);
        }
    }
}