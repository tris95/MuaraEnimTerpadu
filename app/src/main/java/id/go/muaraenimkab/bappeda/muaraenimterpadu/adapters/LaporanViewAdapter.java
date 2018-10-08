package id.go.muaraenimkab.bappeda.muaraenimterpadu.adapters;


import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.siyamed.shapeimageview.CircularImageView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import id.go.muaraenimkab.bappeda.muaraenimterpadu.R;
import id.go.muaraenimkab.bappeda.muaraenimterpadu.activities.MainActivity;
import id.go.muaraenimkab.bappeda.muaraenimterpadu.fragments.DetailBeritaFragment;
import id.go.muaraenimkab.bappeda.muaraenimterpadu.models.Berita;
import id.go.muaraenimkab.bappeda.muaraenimterpadu.models.Laporan;
import id.go.muaraenimkab.bappeda.muaraenimterpadu.utils.Utilities;

public class LaporanViewAdapter extends RecyclerView.Adapter<LaporanViewAdapter.DataObjectHolder> {
    private Context context;
    private ArrayList<Laporan> mList;


    public LaporanViewAdapter(Context context, ArrayList<Laporan> mList) {
        this.context = context;
        this.mList = mList;
    }

    @NonNull
    @Override
    public DataObjectHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_laporan, parent, false);
        return new DataObjectHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final DataObjectHolder holder, @SuppressLint("RecyclerView") final int position) {
        holder.lblJudul.setText(mList.get(position).getJudul_laporan());
        holder.lblIsi.setText(mList.get(position).getIsi_laporan());
        holder.lblTanggal.setText(mList.get(position).getTanggal_laporan());

        if (mList.get(position).getFoto().equals("")){
            holder.img.setVisibility(View.GONE);
        }else {
            holder.img.setVisibility(View.VISIBLE);
            Picasso.with(context)
                    .load(Utilities.getBaseURLImageLaporan() + mList.get(position).getFoto())
                    .into(holder.img);
        }

        switch (mList.get(position).getStatus_tanggapan()) {
            case "0":
                holder.ll.setBackgroundResource(R.color.colorWhite);
                break;
            case "1":
                holder.ll.setBackgroundResource(R.color.colorGreen);
                break;
            case "2":
                holder.ll.setBackgroundResource(R.color.colorRed);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class DataObjectHolder extends RecyclerView.ViewHolder {
        CircularImageView img;
        LinearLayout ll;
        TextView lblJudul, lblIsi, lblTanggal;

        DataObjectHolder(View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.img);
            lblJudul = itemView.findViewById(R.id.tvJudul);
            lblIsi = itemView.findViewById(R.id.tvIsi);
            lblTanggal = itemView.findViewById(R.id.tvTgl);
            ll = itemView.findViewById(R.id.ll);
        }
    }
}
