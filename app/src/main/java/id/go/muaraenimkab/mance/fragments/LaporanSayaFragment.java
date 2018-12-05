package id.go.muaraenimkab.mance.fragments;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Objects;

import id.go.muaraenimkab.mance.R;
import id.go.muaraenimkab.mance.activities.MainActivity;
import id.go.muaraenimkab.mance.adapters.LaporanViewAdapter;
import id.go.muaraenimkab.mance.models.Berita;
import id.go.muaraenimkab.mance.models.Content;
import id.go.muaraenimkab.mance.models.Laporan;
import id.go.muaraenimkab.mance.models.LaporanSpik;
import id.go.muaraenimkab.mance.models.User;
import id.go.muaraenimkab.mance.models.Value;
import id.go.muaraenimkab.mance.services.APIServices;
import id.go.muaraenimkab.mance.utils.Utilities;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class LaporanSayaFragment extends Fragment {
    RecyclerView rView;
    LinearLayoutManager linearLayoutManager;
    ArrayList<LaporanSpik> mList;
    SwipeRefreshLayout swipeRefreshLayout;
    RelativeLayout rl_none, rl_conn;
    TextView tv_cobalagi;
    String flag, id;

    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @SuppressLint("NewApi")
        @Override
        public void onReceive(Context context, Intent intent) {
            //getLaporan();
            getLaporanspik();
        }
    };


    public LaporanSayaFragment() {

    }

    @SuppressLint("ValidFragment")
    public LaporanSayaFragment(String flag) {
        this.flag = flag;
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

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_laporan_saya, container, false);
        rView = v.findViewById(R.id.rView);
        swipeRefreshLayout = v.findViewById(R.id.swipeRefresh);
        rl_none = v.findViewById(R.id.rl_none);
        rl_conn = v.findViewById(R.id.rl);
        tv_cobalagi = v.findViewById(R.id.tv_cobalagi);
        rView.addItemDecoration(new DividerItemDecoration(rView.getContext(), DividerItemDecoration.VERTICAL));

        User user = Utilities.getUser(getContext());
        id = user.getId_user();
        if (MainActivity.laporans.size() != 0) {
            if (flag != null) {
                if (flag.equals("3")) {
                    if (id != null) {
                        //getLaporan();
                        getLaporanspik();
                    }
                } else {
                    linearLayoutManager = new LinearLayoutManager(getContext());
                    rView.setLayoutManager(linearLayoutManager);
                    LaporanViewAdapter laporanViewAdapter = new LaporanViewAdapter(getContext(), (ArrayList<LaporanSpik>) MainActivity.laporans);
                    rView.setAdapter(laporanViewAdapter);
                }
            } else {
                linearLayoutManager = new LinearLayoutManager(getContext());
                rView.setLayoutManager(linearLayoutManager);
                LaporanViewAdapter laporanViewAdapter = new LaporanViewAdapter(getContext(), (ArrayList<LaporanSpik>) MainActivity.laporans);
                rView.setAdapter(laporanViewAdapter);
            }
        } else {
            if (id != null) {
                //getLaporan();
                getLaporanspik();
            }
        }

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //getLaporan();
                getLaporanspik();
            }
        });

        tv_cobalagi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //getLaporan();
                getLaporanspik();
            }
        });

        return v;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }


//    private void getLaporan() {
//        final ProgressDialog pDialog = new ProgressDialog(getActivity());
//        pDialog.setMessage("Loading...");
//        pDialog.setIndeterminate(false);
//        pDialog.setCancelable(false);
//        pDialog.show();
//
//        String random = Utilities.getRandom(5);
//
//        OkHttpClient okHttpClient = Utilities.getUnsafeOkHttpClient();
//
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl(Utilities.getBaseURLUser())
//                .addConverterFactory(GsonConverterFactory.create())
//                .client(okHttpClient)
//                .build();
//        APIServices api = retrofit.create(APIServices.class);
//        Call<Value<Laporan>> call = api.getlaporan(random, id);
//        call.enqueue(new Callback<Value<Laporan>>() {
//            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
//            @Override
//            public void onResponse(@NonNull Call<Value<Laporan>> call, @NonNull Response<Value<Laporan>> response) {
//                if (response.body() != null) {
//                    int success = Objects.requireNonNull(response.body()).getSuccess();
//                    swipeRefreshLayout.setRefreshing(false);
//                    if (success == 1) {
//                        mList = (ArrayList<Laporan>) Objects.requireNonNull(response.body()).getData();
//                        if (mList.size() == 0) {
//                            rl_none.setVisibility(View.VISIBLE);
//                            rl_conn.setVisibility(View.GONE);
//                            rView.setVisibility(View.GONE);
//                        } else {
//                            rl_none.setVisibility(View.GONE);
//                            rl_conn.setVisibility(View.GONE);
//                            rView.setVisibility(View.VISIBLE);
//                            MainActivity.laporans.clear();
//                            MainActivity.laporans = mList;
//                            linearLayoutManager = new LinearLayoutManager(getContext());
//                            rView.setLayoutManager(linearLayoutManager);
//                            LaporanViewAdapter laporanViewAdapter = new LaporanViewAdapter(getContext(), mList);
//                            rView.setAdapter(laporanViewAdapter);
//                        }
//                        pDialog.dismiss();
//                    } else {
//                        rl_none.setVisibility(View.GONE);
//                        rl_conn.setVisibility(View.VISIBLE);
//                        rView.setVisibility(View.GONE);
//                        pDialog.dismiss();
//                        Snackbar.make(Objects.requireNonNull(getActivity()).findViewById(android.R.id.content), "Gagal mengambil data. Silahkan coba lagi",
//                                Snackbar.LENGTH_LONG).show();
//                    }
//                } else {
//                    rl_none.setVisibility(View.GONE);
//                    rl_conn.setVisibility(View.VISIBLE);
//                    rView.setVisibility(View.GONE);
//                    pDialog.dismiss();
//                    Snackbar.make(Objects.requireNonNull(getActivity()).findViewById(android.R.id.content), "Gagal mengambil data. Silahkan coba lagi",
//                            Snackbar.LENGTH_LONG).show();
//                }
//            }
//
//            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
//            @Override
//            public void onFailure(@NonNull Call<Value<Laporan>> call, @NonNull Throwable t) {
//                System.out.println("Retrofit Error:" + t.getMessage());
//                rl_none.setVisibility(View.GONE);
//                rl_conn.setVisibility(View.VISIBLE);
//                rView.setVisibility(View.GONE);
//                swipeRefreshLayout.setRefreshing(false);
//                pDialog.dismiss();
//                Snackbar.make(Objects.requireNonNull(getActivity()).findViewById(android.R.id.content), "Tidak terhubung ke Internet",
//                        Snackbar.LENGTH_LONG).show();
//            }
//        });
//    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onResume() {
        super.onResume();
        IntentFilter filter = new IntentFilter();
        filter.addAction("refresh");
        Objects.requireNonNull(getActivity()).registerReceiver(receiver, filter);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onPause() {
        Objects.requireNonNull(getActivity()).unregisterReceiver(receiver);
        super.onPause();
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void getLaporanspik() {
        final ProgressDialog pDialog = new ProgressDialog(getActivity());
        pDialog.setMessage("Loading...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();

        String random = Utilities.getRandom(5);

        OkHttpClient okHttpClient = Utilities.getUnsafeOkHttpClient();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Utilities.getBaseURLUserspik())
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();
        APIServices api = retrofit.create(APIServices.class);
        Call<Value<LaporanSpik>> call = api.getlaporanspik(random, Utilities.getUser(getContext()).getId_refuser_spikm());
        call.enqueue(new Callback<Value<LaporanSpik>>() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onResponse(@NonNull Call<Value<LaporanSpik>> call, @NonNull Response<Value<LaporanSpik>> response) {
                if (response.body() != null) {
                    int success = Objects.requireNonNull(response.body()).getSuccess();
                    swipeRefreshLayout.setRefreshing(false);
                    if (success == 1) {
                        mList = (ArrayList<LaporanSpik>) Objects.requireNonNull(response.body()).getData();
                        if (mList.size() == 0) {
                            rl_none.setVisibility(View.VISIBLE);
                            rl_conn.setVisibility(View.GONE);
                            rView.setVisibility(View.GONE);
                        } else {
                            rl_none.setVisibility(View.GONE);
                            rl_conn.setVisibility(View.GONE);
                            rView.setVisibility(View.VISIBLE);
                            MainActivity.laporans.clear();
                            MainActivity.laporans = mList;
                            linearLayoutManager = new LinearLayoutManager(getContext());
                            rView.setLayoutManager(linearLayoutManager);
                            LaporanViewAdapter laporanViewAdapter = new LaporanViewAdapter(getContext(), mList);
                            rView.setAdapter(laporanViewAdapter);
                        }
                        pDialog.dismiss();
                    } else {
                        rl_none.setVisibility(View.GONE);
                        rl_conn.setVisibility(View.VISIBLE);
                        rView.setVisibility(View.GONE);
                        pDialog.dismiss();
                        Snackbar.make(Objects.requireNonNull(getActivity()).findViewById(android.R.id.content), "Gagal mengambil data. Silahkan coba lagi",
                                Snackbar.LENGTH_LONG).show();
                    }
                } else {
                    rl_none.setVisibility(View.GONE);
                    rl_conn.setVisibility(View.VISIBLE);
                    rView.setVisibility(View.GONE);
                    pDialog.dismiss();
                    Snackbar.make(Objects.requireNonNull(getActivity()).findViewById(android.R.id.content), "Gagal mengambil data. Silahkan coba lagi",
                            Snackbar.LENGTH_LONG).show();
                }
            }

            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onFailure(@NonNull Call<Value<LaporanSpik>> call, @NonNull Throwable t) {
                System.out.println("Retrofit Error:" + t.getMessage());
                rl_none.setVisibility(View.GONE);
                rl_conn.setVisibility(View.VISIBLE);
                rView.setVisibility(View.GONE);
                swipeRefreshLayout.setRefreshing(false);
                pDialog.dismiss();
                Snackbar.make(Objects.requireNonNull(getActivity()).findViewById(android.R.id.content), "Tidak terhubung ke Internet",
                        Snackbar.LENGTH_LONG).show();
            }
        });
    }
}
