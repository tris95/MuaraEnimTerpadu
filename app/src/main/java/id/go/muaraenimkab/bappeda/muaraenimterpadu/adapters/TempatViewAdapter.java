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

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import id.go.muaraenimkab.bappeda.muaraenimterpadu.R;
import id.go.muaraenimkab.bappeda.muaraenimterpadu.activities.MainActivity;
import id.go.muaraenimkab.bappeda.muaraenimterpadu.models.TempatPariwisata;
import id.go.muaraenimkab.bappeda.muaraenimterpadu.utils.Utilities;

public class TempatViewAdapter extends RecyclerView.Adapter<TempatViewAdapter.DataObjectHolder> {
    private Context context;
    private ArrayList<TempatPariwisata> mListtempatPariwisata;


    public TempatViewAdapter(Context context, ArrayList<TempatPariwisata> mListtempatPariwisata) {
        this.context = context;
        this.mListtempatPariwisata = mListtempatPariwisata;
    }

    @NonNull
    @Override
    public TempatViewAdapter.DataObjectHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_pariwisata, parent, false);
        return new TempatViewAdapter.DataObjectHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final TempatViewAdapter.DataObjectHolder holder, @SuppressLint("RecyclerView") final int position) {
        holder.lblnamatempat.setText(mListtempatPariwisata.get(position).getNama_tempat_pariwisata());

        Picasso.with(context)
                .load(Utilities.getBaseURLImageTempat() + mListtempatPariwisata.get(position).getGambar_lokasi())
                .into(holder.imgtempat);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    //MainActivity.replaceFragment(DetailWisataFragment.newInstance(mListtemapatPariwisata.get(position).getId_pariwisata(), mListtempatPariwisata.get(position).getNama_pariwisata()), 8);
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
        }
    }
}

