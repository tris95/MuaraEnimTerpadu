package id.go.muaraenimkab.bappeda.muaraenimterpadu.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Objects;

import id.go.muaraenimkab.bappeda.muaraenimterpadu.R;
import id.go.muaraenimkab.bappeda.muaraenimterpadu.activities.MainActivity;
import id.go.muaraenimkab.bappeda.muaraenimterpadu.activities.SignInActivity;
import id.go.muaraenimkab.bappeda.muaraenimterpadu.adapters.BeritaViewAdapter;
import id.go.muaraenimkab.bappeda.muaraenimterpadu.adapters.EventViewAdapter;
import id.go.muaraenimkab.bappeda.muaraenimterpadu.adapters.LaporanViewAdapter;
import id.go.muaraenimkab.bappeda.muaraenimterpadu.models.Berita;
import id.go.muaraenimkab.bappeda.muaraenimterpadu.models.Content;
import id.go.muaraenimkab.bappeda.muaraenimterpadu.models.Event;
import id.go.muaraenimkab.bappeda.muaraenimterpadu.models.Laporan;
import id.go.muaraenimkab.bappeda.muaraenimterpadu.models.User;
import id.go.muaraenimkab.bappeda.muaraenimterpadu.models.Value;
import id.go.muaraenimkab.bappeda.muaraenimterpadu.services.APIServices;
import id.go.muaraenimkab.bappeda.muaraenimterpadu.utils.Utilities;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class LaporanSayaFragment extends Fragment {
    RecyclerView rView;
    LinearLayoutManager linearLayoutManager;
    ArrayList<Laporan> mList;
    SwipeRefreshLayout swipeRefreshLayout;

    public LaporanSayaFragment() {
        // Required empty public constructor
    }

    public static LaporanSayaFragment newInstance() {
        LaporanSayaFragment fragment = new LaporanSayaFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_laporan_saya, container, false);
        rView = v.findViewById(R.id.rView);
        swipeRefreshLayout = v.findViewById(R.id.swipeRefresh);
        rView.addItemDecoration(new DividerItemDecoration(rView.getContext(), DividerItemDecoration.VERTICAL));

        if (MainActivity.laporans.size() != 0){
            linearLayoutManager = new LinearLayoutManager(getContext());
            rView.setLayoutManager(linearLayoutManager);
            LaporanViewAdapter laporanViewAdapter = new LaporanViewAdapter(getContext(), (ArrayList<Laporan>) MainActivity.laporans);
            rView.setAdapter(laporanViewAdapter);
        }else {
            if (Utilities.isLogin(getContext())) {
                User user = Utilities.getUser(getContext());
                getLaporan(user.getId_user());
            }else {
                startActivity(new Intent(getContext(), SignInActivity.class));
                getActivity().finish();
            }
        }

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                User user = Utilities.getUser(getContext());
                getLaporan(user.getId_user());
            }
        });

        return v;
    }

    public void onButtonPressed(Uri uri) {
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    private void getLaporan(String id) {
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
        Call<Value<Laporan>> call = api.getlaporan(random, id);
        call.enqueue(new Callback<Value<Laporan>>() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onResponse(@NonNull Call<Value<Laporan>> call, @NonNull Response<Value<Laporan>> response) {
                if (response.body() != null) {
                    int success = Objects.requireNonNull(response.body()).getSuccess();
                    if (success == 1) {
                        swipeRefreshLayout.setRefreshing(false);
                        mList = (ArrayList<Laporan>) Objects.requireNonNull(response.body()).getData();
                        MainActivity.laporans = mList;

                        linearLayoutManager = new LinearLayoutManager(getContext());
                        rView.setLayoutManager(linearLayoutManager);
                        LaporanViewAdapter laporanViewAdapter = new LaporanViewAdapter(getContext(), mList);
                        rView.setAdapter(laporanViewAdapter);

                        pDialog.dismiss();

                    } else {
                        pDialog.dismiss();
                        Snackbar.make(Objects.requireNonNull(getActivity()).findViewById(android.R.id.content), "Gagal mengambil data. Silahkan coba lagi",
                                Snackbar.LENGTH_LONG).show();
                    }
                } else {
                    pDialog.dismiss();
                    Snackbar.make(Objects.requireNonNull(getActivity()).findViewById(android.R.id.content), "Gagal mengambil data. Silahkan coba lagi",
                            Snackbar.LENGTH_LONG).show();
                }
            }

            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onFailure(@NonNull Call<Value<Laporan>> call, @NonNull Throwable t) {
                System.out.println("Retrofit Error:" + t.getMessage());
                pDialog.dismiss();
                Snackbar.make(Objects.requireNonNull(getActivity()).findViewById(android.R.id.content), "Tidak terhubung ke Internet",
                        Snackbar.LENGTH_LONG).show();
            }
        });
    }

}
