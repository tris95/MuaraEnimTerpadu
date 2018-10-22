package id.go.muaraenimkab.bappeda.muaraenimterpadu.adapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
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
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Objects;

import id.go.muaraenimkab.bappeda.muaraenimterpadu.R;
import id.go.muaraenimkab.bappeda.muaraenimterpadu.activities.MainActivity;
import id.go.muaraenimkab.bappeda.muaraenimterpadu.fragments.BeritaFragment;
import id.go.muaraenimkab.bappeda.muaraenimterpadu.fragments.DetailKulinerFragment;
import id.go.muaraenimkab.bappeda.muaraenimterpadu.fragments.DetailWisataFragment;
import id.go.muaraenimkab.bappeda.muaraenimterpadu.models.Pariwisata;
import id.go.muaraenimkab.bappeda.muaraenimterpadu.models.TempatPariwisata;
import id.go.muaraenimkab.bappeda.muaraenimterpadu.models.Value;
import id.go.muaraenimkab.bappeda.muaraenimterpadu.services.APIServices;
import id.go.muaraenimkab.bappeda.muaraenimterpadu.utils.Utilities;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PariwisataViewAdapter extends RecyclerView.Adapter<PariwisataViewAdapter.DataObjectHolder> {
    private Context context;
    private ArrayList<Pariwisata> mListPariwisata;
    private String jumlahtempat;


    public PariwisataViewAdapter(Context context, ArrayList<Pariwisata> mListPariwisata,String jumlahtempat) {
        this.context = context;
        this.mListPariwisata = mListPariwisata;
        this.jumlahtempat=jumlahtempat;
    }

    @NonNull
    @Override
    public PariwisataViewAdapter.DataObjectHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_kategori, parent, false);
        return new PariwisataViewAdapter.DataObjectHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final PariwisataViewAdapter.DataObjectHolder holder, @SuppressLint("RecyclerView") final int position) {
        holder.lbljudulkategori.setText(mListPariwisata.get(position).getNama_pariwisata());

        Picasso.with(context)
                .load(Utilities.getURLImagePariwisata() + mListPariwisata.get(position).getGambar_pariwisata())
                .fit()
                .centerCrop()
                .into(holder.imgkategoriBerita);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (jumlahtempat.equals("1"))
                    gettempatPariwisata(mListPariwisata.get(position).getId_pariwisata(), mListPariwisata.get(position).getNama_pariwisata(), mListPariwisata.get(position).getDeskripsi_pariwisata(), Utilities.getURLImagePariwisata() + mListPariwisata.get(position).getGambar_pariwisata());
                else
                    MainActivity.replaceFragment(DetailWisataFragment.newInstance(mListPariwisata.get(position).getId_pariwisata(), mListPariwisata.get(position).getNama_pariwisata(),Utilities.getURLImagePariwisata() + mListPariwisata.get(position).getGambar_pariwisata(), mListPariwisata.get(position).getDeskripsi_pariwisata(), mListPariwisata.get(position).getAlamat(), mListPariwisata.get(position).getLat(), mListPariwisata.get(position).getLng()), 6);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mListPariwisata.size();
    }

    class DataObjectHolder extends RecyclerView.ViewHolder {
        ImageView imgkategoriBerita;
        TextView lbljudulkategori;
        RelativeLayout rlimgkategori;

        DataObjectHolder(View itemView) {
            super(itemView);
            imgkategoriBerita = itemView.findViewById(R.id.imgkategori);
            lbljudulkategori = itemView.findViewById(R.id.lbljudulkategori);
            rlimgkategori=itemView.findViewById(R.id.rlimgkategori);

            Display display = ((Activity) context).getWindowManager().getDefaultDisplay();
            final DisplayMetrics outMetrics = new DisplayMetrics();
            display.getMetrics(outMetrics);
            int viewPagerWidth = Math.round(outMetrics.widthPixels);
            int viewPagerHeight = Math.round(outMetrics.widthPixels)/2;

            rlimgkategori.setLayoutParams(new RelativeLayout.LayoutParams(viewPagerWidth, viewPagerHeight));
        }
    }

    private void gettempatPariwisata(final String idpariwisata, final String namapariwisata, final String deskripsi, final String gambar) {


        String random = Utilities.getRandom(5);

        OkHttpClient okHttpClient = Utilities.getUnsafeOkHttpClient();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Utilities.getBaseURLUser())
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();

        APIServices api = retrofit.create(APIServices.class);
        Call<Value<TempatPariwisata>> call = api.gettempatpariwisata(random, idpariwisata);
        call.enqueue(new Callback<Value<TempatPariwisata>>() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onResponse(@NonNull Call<Value<TempatPariwisata>> call, @NonNull Response<Value<TempatPariwisata>> response) {
                if (response.body() != null) {
                    int success = Objects.requireNonNull(response.body()).getSuccess();
                    if (success == 1) {
                        ArrayList mListtempatPariwisata = (ArrayList<TempatPariwisata>) Objects.requireNonNull(response.body()).getData();
                        String toko;
                        if (mListtempatPariwisata.size() != 0)
                            toko="1";
                        else
                            toko="0";

                        MainActivity.replaceFragment(DetailKulinerFragment.newInstance(idpariwisata,namapariwisata,deskripsi,gambar,toko ), 6);
                    }
                }

            }

            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onFailure(@NonNull Call<Value<TempatPariwisata>> call, @NonNull Throwable t) {
                System.out.println("Retrofit Error:" + t.getMessage());
            }
        });
    }
}
