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
import id.go.muaraenimkab.bappeda.muaraenimterpadu.fragments.DetailBeritaFragment;
import id.go.muaraenimkab.bappeda.muaraenimterpadu.models.Berita;
import id.go.muaraenimkab.bappeda.muaraenimterpadu.utils.Utilities;

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
        final String like,view;
        holder.lblJudulBerita.setText(mListBerita.get(position).getJudul_berita());
        holder.lblIsiBerita.setText(mListBerita.get(position).getIsi_berita());
        holder.lbltanggalBerita.setText(mListBerita.get(position).getTanggal_post());

        if (mListBerita.get(position).getJumlahlike() != null)
            like=mListBerita.get(position).getJumlahlike();
        else
            like="0";
        holder.lbllikeBerita.setText(like);

        if (mListBerita.get(position).getJumlahview() != null)
            view=mListBerita.get(position).getJumlahview();
        else
            view="0";

        holder.lblviewBerita.setText(view);

        Picasso.with(context)
                .load(Utilities.getURLImageBerita() + mListBerita.get(position).getGambar_berita())
                .into(holder.imgBerita);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (MainActivity.flag2 == 6)
                    MainActivity.replaceFragment(DetailBeritaFragment.newInstance(), 7);
                else
                    MainActivity.replaceFragment(DetailBeritaFragment.newInstance(), 5);

                DetailBeritaFragment.idberita = mListBerita.get(position).getId_berita();
                DetailBeritaFragment.judulberita = mListBerita.get(position).getJudul_berita();
                DetailBeritaFragment.tanggalberita = mListBerita.get(position).getTanggal_post();
                DetailBeritaFragment.likeberita = like;
                DetailBeritaFragment.viewberita = view;
                DetailBeritaFragment.gambar = Utilities.getURLImageBerita() + mListBerita.get(position).getGambar_berita();

            }
        });

    }

    @Override
    public int getItemCount() {
        return mListBerita.size();
    }

    class DataObjectHolder extends RecyclerView.ViewHolder {
        ImageView imgBerita;
        TextView lblJudulBerita, lblIsiBerita, lbltanggalBerita, lbllikeBerita, lblviewBerita;

        DataObjectHolder(View itemView) {
            super(itemView);
            imgBerita = itemView.findViewById(R.id.imgBerita);
            lblJudulBerita = itemView.findViewById(R.id.lblJudulBerita);
            lblIsiBerita = itemView.findViewById(R.id.lblIsiBerita);
            lbltanggalBerita = itemView.findViewById(R.id.lbltanggalBerita);
            lbllikeBerita = itemView.findViewById(R.id.lblLikeBerita);
            lblviewBerita = itemView.findViewById(R.id.lblViewBerita);
        }
    }
}
