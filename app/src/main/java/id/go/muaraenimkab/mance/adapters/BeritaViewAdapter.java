package id.go.muaraenimkab.mance.adapters;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Objects;

import id.go.muaraenimkab.mance.R;
import id.go.muaraenimkab.mance.activities.MainActivity;
import id.go.muaraenimkab.mance.fragments.BeritaFragment;
import id.go.muaraenimkab.mance.fragments.DetailBeritaFragment;
import id.go.muaraenimkab.mance.fragments.HomeFragment;
import id.go.muaraenimkab.mance.models.Berita;
import id.go.muaraenimkab.mance.utils.Utilities;

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

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onBindViewHolder(@NonNull final DataObjectHolder holder, @SuppressLint("RecyclerView") final int position) {
        final String like, view;
        holder.lblJudulBerita.setText(mListBerita.get(position).getJudul_berita());
        holder.lblIsiBerita.setText(mListBerita.get(position).getIsi_berita());
        holder.lbltanggalBerita.setText(mListBerita.get(position).getTanggal_post());

        if (mListBerita.get(position).getJumlahlike() != null)
            like = mListBerita.get(position).getJumlahlike();
        else
            like = "0";
        holder.lbllikeBerita.setText(like);

        if (mListBerita.get(position).getJumlahview() != null)
            view = mListBerita.get(position).getJumlahview();
        else
            view = "0";

        holder.lblviewBerita.setText(view);

        Picasso.with(context)
                .load(Utilities.getURLImageBerita() + mListBerita.get(position).getGambar_berita())
                .fit()
                .centerCrop()
                .into(holder.imgBerita);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (MainActivity.flag2 == 6)
                    MainActivity.replaceFragment(DetailBeritaFragment.newInstance(mListBerita.get(position).getId_berita(),
                            mListBerita.get(position).getJudul_berita(), mListBerita.get(position).getTanggal_post(),
                            like, view, Utilities.getURLImageBerita() + mListBerita.get(position).getGambar_berita()), 7);
                else if (MainActivity.flag2 == 4 || MainActivity.flag2 == 0)
                    MainActivity.replaceFragment(DetailBeritaFragment.newInstance(mListBerita.get(position).getId_berita(),
                            mListBerita.get(position).getJudul_berita(), mListBerita.get(position).getTanggal_post(),
                            like, view, Utilities.getURLImageBerita() + mListBerita.get(position).getGambar_berita()), 5);

                HomeFragment.flag = true;
                BeritaFragment.flag = true;
//                BeritaFragment.flag_id = mListBerita.get(position).getId_berita();
//                notifyDataSetChanged();

            }
        });

    }

    @Override
    public int getItemCount() {
        return mListBerita.size();
    }

    class DataObjectHolder extends RecyclerView.ViewHolder {
        ImageView imgBerita, imgtanggalBerita, imglikeBerita, imgviewBerita;
        TextView lblJudulBerita, lblIsiBerita, lbltanggalBerita, lbllikeBerita, lblviewBerita;

        DataObjectHolder(View itemView) {
            super(itemView);
            imgBerita = itemView.findViewById(R.id.imgBerita);
            lblJudulBerita = itemView.findViewById(R.id.lblJudulBerita);
            lblIsiBerita = itemView.findViewById(R.id.lblIsiBerita);
            lbltanggalBerita = itemView.findViewById(R.id.lbltanggalBerita);
            lbllikeBerita = itemView.findViewById(R.id.lblLikeBerita);
            lblviewBerita = itemView.findViewById(R.id.lblViewBerita);
            imgtanggalBerita = itemView.findViewById(R.id.imgtanggalberita);
            imglikeBerita = itemView.findViewById(R.id.imglikeberita);
            imgviewBerita = itemView.findViewById(R.id.imgviewberita);

            Display display = ((Activity) context).getWindowManager().getDefaultDisplay();
            final DisplayMetrics outMetrics = new DisplayMetrics();
            display.getMetrics(outMetrics);
            int viewPagerWidth = Math.round(outMetrics.widthPixels) / 4;
            int viewPagerHeight = Math.round(outMetrics.widthPixels) / 4;

            imgBerita.setLayoutParams(new RelativeLayout.LayoutParams(viewPagerWidth, viewPagerHeight));
//            lblJudulBerita.setTextSize(TypedValue.COMPLEX_UNIT_SP,Width/47);
//            lblIsiBerita.setTextSize(TypedValue.COMPLEX_UNIT_SP,Width/51);
//            lbltanggalBerita.setTextSize(TypedValue.COMPLEX_UNIT_SP,Width/53);
//            lbllikeBerita.setTextSize(TypedValue.COMPLEX_UNIT_SP,Width/53);
//            lblviewBerita.setTextSize(TypedValue.COMPLEX_UNIT_SP,Width/53);
//
//            imgtanggalBerita.setLayoutParams(new LinearLayout.LayoutParams(Width/21, Width/21));
//            imglikeBerita.setLayoutParams(new LinearLayout.LayoutParams(Width/21, Width/21));
//            imgviewBerita.setLayoutParams(new LinearLayout.LayoutParams(Width/21, Width/21));
        }
    }
}
