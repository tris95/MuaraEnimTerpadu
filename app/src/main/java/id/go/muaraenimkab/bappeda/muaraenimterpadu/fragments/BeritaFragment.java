package id.go.muaraenimkab.bappeda.muaraenimterpadu.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Objects;

import id.go.muaraenimkab.bappeda.muaraenimterpadu.R;
import id.go.muaraenimkab.bappeda.muaraenimterpadu.activities.MainActivity;
import id.go.muaraenimkab.bappeda.muaraenimterpadu.adapters.BeritaViewAdapter;
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


public class BeritaFragment extends Fragment {
    Toolbar toolbar;
    private static final String ARG_Kategori_berita = "kategoriberita",ARG_idKategori_berita = "idkategoriberita";
    RecyclerView rvBerita;
    TextView tv_cobalagi;
    LinearLayoutManager linearLayoutManager;
    ArrayList<Berita> mListBerita;
    String kategoriberita,idkategoriberita;
    RelativeLayout rl,rltidakadaberita;

    public BeritaFragment() {
        // Required empty public constructor
    }

    public static BeritaFragment newInstance(String idkategoriberita,String kategoriberita) {
        BeritaFragment fragment = new BeritaFragment();
        Bundle args = new Bundle();
        args.putString(ARG_idKategori_berita, idkategoriberita);
        args.putString(ARG_Kategori_berita, kategoriberita);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            idkategoriberita = getArguments().getString(ARG_idKategori_berita);
            kategoriberita = getArguments().getString(ARG_Kategori_berita);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_berita, container, false);
        toolbar = v.findViewById(R.id.toolbar);
        rl=v.findViewById(R.id.rl);
        rltidakadaberita=v.findViewById(R.id.rltidakadaberita);
        tv_cobalagi  = v.findViewById(R.id.tv_cobalagi);

        ((AppCompatActivity) Objects.requireNonNull(getActivity())).setSupportActionBar(toolbar);

        if (((AppCompatActivity) getActivity()).getSupportActionBar() != null) {
            Objects.requireNonNull(((AppCompatActivity) getActivity()).getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
            Objects.requireNonNull(((AppCompatActivity) getActivity()).getSupportActionBar()).setDisplayShowHomeEnabled(true);
            Objects.requireNonNull(((AppCompatActivity) getActivity()).getSupportActionBar()).setTitle(kategoriberita);
        }
        rvBerita = v.findViewById(R.id.rvBerita);
        Log.e("boo", MainActivity.selectedKategori+" "+idkategoriberita);
        if (MainActivity.Beritask.size() != 0) {
            if(idkategoriberita.equals(MainActivity.selectedKategori)) {
                linearLayoutManager = new LinearLayoutManager(getContext());
                rvBerita.setLayoutManager(linearLayoutManager);
                BeritaViewAdapter beritaViewAdapter = new BeritaViewAdapter(getContext(), (ArrayList<Berita>) MainActivity.Beritask);
                rvBerita.setAdapter(beritaViewAdapter);
                rltidakadaberita.setVisibility(View.GONE);
                rvBerita.setVisibility(View.VISIBLE);
            }else {
                getBerita();
            }
        }else {
            getBerita();
        }
        tv_cobalagi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getBerita();
            }
        });
        return v;
    }

    private void getBerita() {
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
        Call<Value<Berita>> call = api.getBeritaOfKategori(random,idkategoriberita);
        call.enqueue(new Callback<Value<Berita>>() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onResponse(@NonNull Call<Value<Berita>> call, @NonNull Response<Value<Berita>> response) {
                if (response.body() != null) {
                    int success = Objects.requireNonNull(response.body()).getSuccess();
                    if (success == 1) {
                        mListBerita = (ArrayList<Berita>) Objects.requireNonNull(response.body()).getData();

                        if (mListBerita.size()!=0) {
                            MainActivity.Beritask.clear();
                            MainActivity.Beritask=mListBerita;
                            MainActivity.selectedKategori = idkategoriberita;
                            linearLayoutManager = new LinearLayoutManager(getContext());
                            rvBerita.setLayoutManager(linearLayoutManager);
                            BeritaViewAdapter beritaViewAdapter = new BeritaViewAdapter(getContext(), mListBerita);
                            rvBerita.setAdapter(beritaViewAdapter);
                            rltidakadaberita.setVisibility(View.GONE);
                            rvBerita.setVisibility(View.VISIBLE);
                        }
                        else {
                            rltidakadaberita.setVisibility(View.VISIBLE);
                            rvBerita.setVisibility(View.GONE);
                        }
                        rl.setVisibility(View.GONE);
                        pDialog.dismiss();

                    } else {
                        pDialog.dismiss();
                        Snackbar.make(Objects.requireNonNull(getActivity()).findViewById(android.R.id.content), "Gagal mengambil data. Silahkan coba lagi",
                                Snackbar.LENGTH_LONG).show();
                        rl.setVisibility(View.VISIBLE);
                        rltidakadaberita.setVisibility(View.GONE);
                        rvBerita.setVisibility(View.GONE);
                    }
                } else {
                    pDialog.dismiss();
                    Snackbar.make(Objects.requireNonNull(getActivity()).findViewById(android.R.id.content), "Gagal mengambil data. Silahkan coba lagi",
                            Snackbar.LENGTH_LONG).show();
                    rl.setVisibility(View.VISIBLE);
                    rltidakadaberita.setVisibility(View.GONE);
                    rvBerita.setVisibility(View.GONE);
                }
            }

            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onFailure(@NonNull Call<Value<Berita>> call, @NonNull Throwable t) {
                System.out.println("Retrofit Error:" + t.getMessage());
                pDialog.dismiss();
                Snackbar.make(Objects.requireNonNull(getActivity()).findViewById(android.R.id.content), "Tidak terhubung ke Internet",
                        Snackbar.LENGTH_LONG).show();
                rl.setVisibility(View.VISIBLE);
                rltidakadaberita.setVisibility(View.GONE);
                rvBerita.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

}
