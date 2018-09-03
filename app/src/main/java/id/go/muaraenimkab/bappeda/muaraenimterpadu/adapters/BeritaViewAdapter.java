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
import java.util.List;

import id.go.muaraenimkab.bappeda.muaraenimterpadu.R;
import id.go.muaraenimkab.bappeda.muaraenimterpadu.models.Berita;

public class BeritaViewAdapter extends RecyclerView.Adapter<BeritaViewAdapter.DataObjectHolder> {
    private Context context;
    private ArrayList<Berita> mListBerita;


    public BeritaViewAdapter(Context context, ArrayList<Berita> mListBerita) {
        this.context = context;
        this.mListBerita = mListBerita;
    }

    @NonNull
    @Override
    public DataObjectHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_berita, parent, false);
        return new DataObjectHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final DataObjectHolder holder, @SuppressLint("RecyclerView") final int position) {
        holder.imgBerita.setImageResource(R.drawable.jalan);
        holder.lblJudulBerita.setText("Material Design Android");
        holder.lblIsiBerita.setText("Material is an adaptable system of guidelines, components, and tools that support the best practices of user interface design. Backed by open-source code, Material streamlines collaboration between designers and developers, and helps teams quickly build beautiful products.");

//        Glide.with(context)
//                .load(Utilities.getBaseURLImageProduk() + mListDatasetProduct.get(position).get(0).getGambar())
//                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
//                .placeholder(R.color.colorDivider)
//                .into(holder.ivProductImage);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }

    @Override
    public int getItemCount() {
        return 5;
    }
    class DataObjectHolder extends RecyclerView.ViewHolder {
        ImageView imgBerita;
        TextView lblJudulBerita,lblIsiBerita;

        DataObjectHolder(View view) {
            super(view);
            imgBerita = view.findViewById(R.id.imgBerita);
            lblJudulBerita = view.findViewById(R.id.lblJudulBerita);
            lblIsiBerita = view.findViewById(R.id.lblIsiBerita);
        }
    }
}
