package id.go.muaraenimkab.bappeda.muaraenimterpadu.fragments;

import android.app.ProgressDialog;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bluejamesbond.text.DocumentView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Objects;

import id.go.muaraenimkab.bappeda.muaraenimterpadu.R;
import id.go.muaraenimkab.bappeda.muaraenimterpadu.models.Berita;
import id.go.muaraenimkab.bappeda.muaraenimterpadu.models.Value;
import id.go.muaraenimkab.bappeda.muaraenimterpadu.services.APIServices;
import id.go.muaraenimkab.bappeda.muaraenimterpadu.utils.Utilities;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class DetailBeritaFragment extends Fragment {
    public static String idberita,judulberita,tanggalberita,likeberita,viewberita,gambar;
    Toolbar toolbar;
    TextView tv_cobalagi,lblLikeUnlike,lblJudulBerita,lbltanggalBerita,lblLikeBerita,lblViewBerita;
    DocumentView lblIsiBerita;
    ImageView imgDetaiBerita;
    ArrayList<Berita> mListBerita;
    RelativeLayout rl,rlket;

    public DetailBeritaFragment() {
        // Required empty public constructor
    }

    public static DetailBeritaFragment newInstance() {
        DetailBeritaFragment fragment = new DetailBeritaFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_detail_berita, container, false);
        toolbar = v.findViewById(R.id.toolbar);
        tv_cobalagi  = v.findViewById(R.id.tv_cobalagi);
        lblJudulBerita = v.findViewById(R.id.lblJudulBerita);
        lbltanggalBerita = v.findViewById(R.id.lbltanggalBerita);
        lblLikeBerita = v.findViewById(R.id.lblLikeBerita);
        lblViewBerita = v.findViewById(R.id.lblViewBerita);
        lblIsiBerita = v.findViewById(R.id.lblIsiBerita);
        imgDetaiBerita = v.findViewById(R.id.imgDetaiBerita);
        rl=v.findViewById(R.id.rl);
        rlket=v.findViewById(R.id.rlket);

        ((AppCompatActivity) Objects.requireNonNull(getActivity())).setSupportActionBar(toolbar);

        if (((AppCompatActivity) getActivity()).getSupportActionBar() != null) {
            Objects.requireNonNull(((AppCompatActivity) getActivity()).getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
            Objects.requireNonNull(((AppCompatActivity) getActivity()).getSupportActionBar()).setDisplayShowHomeEnabled(true);
            Objects.requireNonNull(((AppCompatActivity) getActivity()).getSupportActionBar()).setTitle("Detail Berita");
        }
        getisiBerita();

        lblLikeUnlike = v.findViewById(R.id.lblLikeUnlike);
        lblLikeUnlike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //lblLikeUnlike.setTextColor(Color.parseColor("#41c300"));
            }
        });

        tv_cobalagi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getisiBerita();
            }
        });

        return v;
    }

    public void getisiBerita() {
        final ProgressDialog pDialog = new ProgressDialog(getActivity());
        pDialog.setMessage("Loading...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();

        String random = Utilities.getRandom(5);

        OkHttpClient okHttpClient = Utilities.getUnsafeOkHttpClient();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Utilities.getBaseURLUser())
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();

        APIServices api = retrofit.create(APIServices.class);
        Call<Value<Berita>> call = api.getIsiBerita(random,idberita);
        call.enqueue(new Callback<Value<Berita>>() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onResponse(@NonNull Call<Value<Berita>> call, @NonNull Response<Value<Berita>> response) {
                if (response.body() != null) {
                    int success = Objects.requireNonNull(response.body()).getSuccess();
                    if (success == 1) {
                        mListBerita = (ArrayList<Berita>) Objects.requireNonNull(response.body()).getData();

                        lblJudulBerita.setText(judulberita);
                        lbltanggalBerita.setText(tanggalberita);
                        lblLikeBerita.setText(likeberita);
                        lblViewBerita.setText(viewberita);

                        Picasso.with(getContext())
                                .load(gambar)
                                .into(imgDetaiBerita);

                        lblIsiBerita.setText(mListBerita.get(0).getIsi_berita());

                        rl.setVisibility(View.GONE);
                        lblLikeUnlike.setVisibility(View.VISIBLE);
                        rlket.setVisibility(View.VISIBLE);

                        pDialog.dismiss();

                    } else {
                        rl.setVisibility(View.VISIBLE);
                        lblLikeUnlike.setVisibility(View.GONE);
                        rlket.setVisibility(View.GONE);
                        pDialog.dismiss();
                        Snackbar.make(Objects.requireNonNull(getActivity()).findViewById(android.R.id.content), "Gagal mengambil data. Silahkan coba lagi",
                                Snackbar.LENGTH_LONG).show();
                        rl.setVisibility(View.VISIBLE);
                    }
                } else {
                    rl.setVisibility(View.VISIBLE);
                    lblLikeUnlike.setVisibility(View.GONE);
                    rlket.setVisibility(View.GONE);
                    pDialog.dismiss();
                    Snackbar.make(Objects.requireNonNull(getActivity()).findViewById(android.R.id.content), "Gagal mengambil data. Silahkan coba lagi",
                            Snackbar.LENGTH_LONG).show();
                    rl.setVisibility(View.VISIBLE);
                }
            }

            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onFailure(@NonNull Call<Value<Berita>> call, @NonNull Throwable t) {
                System.out.println("Retrofit Error:" + t.getMessage());
                rl.setVisibility(View.VISIBLE);
                lblLikeUnlike.setVisibility(View.GONE);
                rlket.setVisibility(View.GONE);
                pDialog.dismiss();
                Snackbar.make(Objects.requireNonNull(getActivity()).findViewById(android.R.id.content), "Tidak terhubung ke Internet",
                        Snackbar.LENGTH_LONG).show();
                rl.setVisibility(View.VISIBLE);
            }
        });
    }
}
