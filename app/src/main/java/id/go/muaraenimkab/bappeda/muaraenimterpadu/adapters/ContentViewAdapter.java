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
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Objects;

import id.go.muaraenimkab.bappeda.muaraenimterpadu.R;
import id.go.muaraenimkab.bappeda.muaraenimterpadu.activities.MainActivity;
import id.go.muaraenimkab.bappeda.muaraenimterpadu.fragments.DetailWisataFragment;
import id.go.muaraenimkab.bappeda.muaraenimterpadu.fragments.PariwisataFragment;
import id.go.muaraenimkab.bappeda.muaraenimterpadu.models.Content;
import id.go.muaraenimkab.bappeda.muaraenimterpadu.utils.Utilities;

public class ContentViewAdapter extends RecyclerView.Adapter<ContentViewAdapter.DataObjectHolder> {
    private Context context;
    private ArrayList<Content> mListContent;

    public ContentViewAdapter(Context context, ArrayList<Content> mListContent) {
        this.context = context;
        this.mListContent = mListContent;
    }

    @NonNull
    @Override
    public DataObjectHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_content, parent, false);
        return new DataObjectHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DataObjectHolder holder, @SuppressLint("RecyclerView") final int position) {
        holder.content.setText(mListContent.get(position).getNama_kategori_pariwisata());
        Picasso.with(context)
                .load(Utilities.getURLImageKategoriPariwisata() + mListContent.get(position).getGambar_kategori_pariwisata())
                .into(holder.imgContent);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.replaceFragment(PariwisataFragment.newInstance(mListContent.get(position).getId_kategori_pariwisata(),mListContent.get(position).getNama_kategori_pariwisata(),
                        mListContent.get(position).getJumlah_tempat()), 5);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mListContent.size();
    }


    class DataObjectHolder extends RecyclerView.ViewHolder {
        ImageView imgContent;
        TextView content;
        RelativeLayout rl;

        DataObjectHolder(View itemView) {
            super(itemView);
            imgContent = itemView.findViewById(R.id.imgContent);
            content = itemView.findViewById(R.id.lblContent);
            rl = itemView.findViewById(R.id.rl);

            Display display = ((Activity) context).getWindowManager().getDefaultDisplay();
            final DisplayMetrics outMetrics = new DisplayMetrics();
            display.getMetrics(outMetrics);
            int viewPagerWidth = Math.round(outMetrics.widthPixels)/3;
            int viewPagerHeight = Math.round(outMetrics.widthPixels)/4;

            rl.setLayoutParams(new RelativeLayout.LayoutParams(viewPagerWidth, viewPagerHeight));
        }
    }
}
