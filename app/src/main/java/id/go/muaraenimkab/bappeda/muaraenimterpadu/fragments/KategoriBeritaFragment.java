package id.go.muaraenimkab.bappeda.muaraenimterpadu.fragments;

import android.content.Context;
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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Objects;

import id.go.muaraenimkab.bappeda.muaraenimterpadu.R;
import id.go.muaraenimkab.bappeda.muaraenimterpadu.activities.MainActivity;
import id.go.muaraenimkab.bappeda.muaraenimterpadu.adapters.KategoriBeritaViewAdapter;
import id.go.muaraenimkab.bappeda.muaraenimterpadu.models.KategoriBerita;
import id.go.muaraenimkab.bappeda.muaraenimterpadu.models.Value;
import id.go.muaraenimkab.bappeda.muaraenimterpadu.services.APIServices;
import id.go.muaraenimkab.bappeda.muaraenimterpadu.utils.Utilities;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class KategoriBeritaFragment extends Fragment {
    Toolbar toolbar;
    RecyclerView rvKategoriBerita;
    LinearLayoutManager linearLayoutManager;
    ArrayList<KategoriBerita> mListKategoriBerita;

    public KategoriBeritaFragment() {
        // Required empty public constructor
    }

    public static KategoriBeritaFragment newInstance() {
        KategoriBeritaFragment fragment = new KategoriBeritaFragment();
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
        View v = inflater.inflate(R.layout.fragment_kategori_berita, container, false);
        toolbar = v.findViewById(R.id.toolbar);
        rvKategoriBerita=v.findViewById(R.id.rvKategoriBerita);

        ((AppCompatActivity) Objects.requireNonNull(getActivity())).setSupportActionBar(toolbar);

        if (((AppCompatActivity) getActivity()).getSupportActionBar() != null) {
            Objects.requireNonNull(((AppCompatActivity) getActivity()).getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
            Objects.requireNonNull(((AppCompatActivity) getActivity()).getSupportActionBar()).setDisplayShowHomeEnabled(true);
            Objects.requireNonNull(((AppCompatActivity) getActivity()).getSupportActionBar()).setTitle("Kategori Berita");
        }

        if (MainActivity.kategoriBeritas.size() != 0) {
            linearLayoutManager=new LinearLayoutManager(getContext());
            rvKategoriBerita.setLayoutManager(linearLayoutManager);
            KategoriBeritaViewAdapter kategoriberitaViewAdapter=new KategoriBeritaViewAdapter(getContext(), (ArrayList<KategoriBerita>) MainActivity.kategoriBeritas);
            rvKategoriBerita.setAdapter(kategoriberitaViewAdapter);

        } else
            getKategoriBerita();


        return v;
    }

    private void getKategoriBerita() {
        String random = Utilities.getRandom(5);

        OkHttpClient okHttpClient = Utilities.getUnsafeOkHttpClient();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Utilities.getBaseURLUser())
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();

        APIServices api = retrofit.create(APIServices.class);
        Call<Value<KategoriBerita>> call = api.getKategoriBerita(random);
        call.enqueue(new Callback<Value<KategoriBerita>>() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onResponse(@NonNull Call<Value<KategoriBerita>> call, @NonNull Response<Value<KategoriBerita>> response) {
                if (response.body() != null) {
                    int success = Objects.requireNonNull(response.body()).getSuccess();
                    if (success == 1) {
                        mListKategoriBerita = (ArrayList<KategoriBerita>) Objects.requireNonNull(response.body()).getData();
                        MainActivity.kategoriBeritas = mListKategoriBerita;

                        linearLayoutManager=new LinearLayoutManager(getContext());
                        rvKategoriBerita.setLayoutManager(linearLayoutManager);
                        KategoriBeritaViewAdapter kategoriBeritaViewAdapter=new KategoriBeritaViewAdapter(getContext(),mListKategoriBerita);
                        rvKategoriBerita.setAdapter(kategoriBeritaViewAdapter);

                    } else {
                        Snackbar.make(Objects.requireNonNull(getActivity()).findViewById(android.R.id.content), "Gagal mengambil data. Silahkan coba lagi",
                                Snackbar.LENGTH_LONG).show();
                    }
                } else {
                    Snackbar.make(Objects.requireNonNull(getActivity()).findViewById(android.R.id.content), "Gagal mengambil data. Silahkan coba lagi",
                            Snackbar.LENGTH_LONG).show();
                }
            }

            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onFailure(@NonNull Call<Value<KategoriBerita>> call, @NonNull Throwable t) {
                System.out.println("Retrofit Error:" + t.getMessage());
                Snackbar.make(Objects.requireNonNull(getActivity()).findViewById(android.R.id.content), "Tidak terhubung ke Internet",
                        Snackbar.LENGTH_LONG).show();
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
