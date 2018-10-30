package id.go.muaraenimkab.bappeda.muaraenimterpadu.adapters;


import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.siyamed.shapeimageview.CircularImageView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import id.go.muaraenimkab.bappeda.muaraenimterpadu.R;
import id.go.muaraenimkab.bappeda.muaraenimterpadu.activities.MainActivity;
import id.go.muaraenimkab.bappeda.muaraenimterpadu.fragments.DetailBeritaFragment;
import id.go.muaraenimkab.bappeda.muaraenimterpadu.models.Berita;
import id.go.muaraenimkab.bappeda.muaraenimterpadu.models.Laporan;
import id.go.muaraenimkab.bappeda.muaraenimterpadu.models.LaporanSpik;
import id.go.muaraenimkab.bappeda.muaraenimterpadu.utils.Utilities;

public class LaporanViewAdapter extends RecyclerView.Adapter<LaporanViewAdapter.DataObjectHolder> {
    private Context context;
    private ArrayList<LaporanSpik> mList;


    public LaporanViewAdapter(Context context, ArrayList<LaporanSpik> mList) {
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
//        holder.lblJudul.setText(mList.get(position).getJudul_laporan());
//        holder.lblIsi.setText(mList.get(position).getIsi_laporan());
//        holder.lblTanggal.setText(mList.get(position).getTanggal_laporan());
//
//        if (mList.get(position).getFoto().equals("")){
//            holder.img.setVisibility(View.GONE);
//            holder.rldefauld.setVisibility(View.GONE);
//        }else {
//            holder.img.setVisibility(View.VISIBLE);
//            Picasso.with(context)
//                    .load(Utilities.getBaseURLImageLaporan() + mList.get(position).getFoto())
//                    .fit()
//                    .centerCrop()
//                    .into(holder.img);
//        }
//
//        switch (mList.get(position).getStatus_tanggapan()) {
//            case "0":
//                holder.ll.setBackgroundResource(R.color.colorYellow);
//                break;
//            case "1":
//                holder.ll.setBackgroundResource(R.color.colorGreen);
//                break;
//            case "2":
//                holder.ll.setBackgroundResource(R.color.colorRed);
//                break;
//        }
        holder.lblJudul.setText(mList.get(position).getTblinbox_subyek());
        holder.lblIsi.setText(mList.get(position).getTblinbox_isi());
        holder.lblTanggal.setText(mList.get(position).getTblinbox_tanggal());

        if (mList.get(position).getTblinbox_datapendukung().equals("")){
            holder.img.setVisibility(View.GONE);
            holder.rldefauld.setVisibility(View.GONE);
        }else {
            holder.img.setVisibility(View.VISIBLE);
            Picasso.with(context)
                    .load(Utilities.getBaseURLImageLaporanspik() + mList.get(position).getTblinbox_datapendukung())
                    .fit()
                    .centerCrop()
                    .into(holder.img);
        }

        if (mList.get(position).getTblinbox_isverifikasi().equals("T")){
            holder.ll.setBackgroundResource(R.color.colorYellow);
            holder.lblJudul.setTextColor(Color.WHITE);
            holder.lblIsi.setTextColor(Color.WHITE);
            holder.lblTanggal.setTextColor(Color.WHITE);
        }
        if (mList.get(position).getTblinbox_istanggapi().equals("T")){
            holder.ll.setBackgroundResource(R.color.colorBlue);
            holder.lblJudul.setTextColor(Color.WHITE);
            holder.lblIsi.setTextColor(Color.WHITE);
            holder.lblTanggal.setTextColor(Color.WHITE);
        }
        if (mList.get(position).getTblinbox_isdisposisi().equals("T")){
            holder.ll.setBackgroundResource(R.color.colorGreen);
            holder.lblJudul.setTextColor(Color.WHITE);
            holder.lblIsi.setTextColor(Color.WHITE);
            holder.lblTanggal.setTextColor(Color.WHITE);
        }
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class DataObjectHolder extends RecyclerView.ViewHolder {
        CircularImageView img;
        RelativeLayout rldefauld;
        LinearLayout ll;
        TextView lblJudul, lblIsi, lblTanggal;

        DataObjectHolder(View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.img);
            rldefauld= itemView.findViewById(R.id.rldefauld);
            lblJudul = itemView.findViewById(R.id.tvJudul);
            lblIsi = itemView.findViewById(R.id.tvIsi);
            lblTanggal = itemView.findViewById(R.id.tvTgl);
            ll = itemView.findViewById(R.id.ll);
        }
    }
}
