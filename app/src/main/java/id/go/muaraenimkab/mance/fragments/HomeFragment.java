package id.go.muaraenimkab.mance.fragments;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
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

import com.daimajia.slider.library.Indicators.PagerIndicator;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.Random;

import id.go.muaraenimkab.mance.R;
import id.go.muaraenimkab.mance.activities.MainActivity;
import id.go.muaraenimkab.mance.adapters.BeritaViewAdapter;
import id.go.muaraenimkab.mance.adapters.ContentViewAdapter;
import id.go.muaraenimkab.mance.models.Ad;
import id.go.muaraenimkab.mance.models.Berita;
import id.go.muaraenimkab.mance.models.Content;
import id.go.muaraenimkab.mance.models.Value;
import id.go.muaraenimkab.mance.services.APIServices;
import id.go.muaraenimkab.mance.utils.Utilities;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class HomeFragment extends Fragment {
    //Toolbar toolbar;
    View view;
    private SliderLayout mSlider;
    RelativeLayout rl, rlberita, rlslider;
    TextView lblBeritaselengkapnya, lblBeritaterbaru, tv_cobalagi, lblkonten;
    RecyclerView rvContent, rvBerita;
    ArrayList<Content> mListContent;
    ArrayList<Berita> mListBerita;
    public static List<Berita> mListisiBerita = new ArrayList<>();
    LinearLayoutManager linearLayoutManagercontent, linearLayoutManagerberita;
    LinearLayout llkategoriberita;
    SwipeRefreshLayout swipeRefreshLayout;
    PagerIndicator indicator;
    ImageView tvArrow;
    public static boolean flag = false;

    public HomeFragment() {

    }

    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home, container, false);
        //toolbar = v.findViewById(R.id.toolbar);
        rl = v.findViewById(R.id.rl);
        rlberita = v.findViewById(R.id.rlberita);
        view = v.findViewById(R.id.view);
        swipeRefreshLayout = v.findViewById(R.id.swipe);
        tv_cobalagi = v.findViewById(R.id.tv_cobalagi);
        indicator = v.findViewById(R.id.custom_indicator);
        lblkonten = v.findViewById(R.id.lblkonten);

        //((AppCompatActivity) Objects.requireNonNull(getActivity())).setSupportActionBar(toolbar);

//        if (((AppCompatActivity) getActivity()).getSupportActionBar() != null) {
//            Objects.requireNonNull(((AppCompatActivity) getActivity()).getSupportActionBar()).setTitle("Muara Enim Center");
//        }

        mSlider = v.findViewById(R.id.slider);
        rvContent = v.findViewById(R.id.rvContent);
        rvBerita = v.findViewById(R.id.rvBerita);
        rlslider = v.findViewById(R.id.rlslider);

        Display display = ((Activity) Objects.requireNonNull(getContext())).getWindowManager().getDefaultDisplay();
        final DisplayMetrics outMetrics = new DisplayMetrics();
        display.getMetrics(outMetrics);
        int viewPagerWidth = Math.round(outMetrics.widthPixels);
        int viewPagerHeight = viewPagerWidth / 2;

        rlslider.setLayoutParams(new LinearLayout.LayoutParams(viewPagerWidth, viewPagerHeight));

//        rvBerita.addItemDecoration(new DividerItemDecoration(rvBerita.getContext(), DividerItemDecoration.VERTICAL));

        if (flag) {
            getBerita1();
            flag = false;
        }
        if (MainActivity.ads.size() != 0 && MainActivity.contents.size() != 0 && MainActivity.Beritas.size() != 0) {
            List<Ad> listDataAd = MainActivity.ads;
            HashMap<String, String> url_maps = new HashMap<>();

            for (int a = 0; a < listDataAd.size(); a++) {
                url_maps.put(listDataAd.get(a).getJudul_iklan(), Utilities.getURLImageIklan() + listDataAd.get(a).getGambar_iklan());
            }

            for (String name : url_maps.keySet()) {
                TextSliderView textSliderView = new TextSliderView(getContext());
                textSliderView
                        .description(name)
                        .image(url_maps.get(name))
                        .setScaleType(BaseSliderView.ScaleType.Fit);

                textSliderView.bundle(new Bundle());
                textSliderView.getBundle()
                        .putString("extra", name);

                mSlider.addSlider(textSliderView);
            }

//            mSlider.setPresetTransformer(SliderLayout.Transformer.ZoomOutSlide);
            randomSlider();
            mSlider.setCustomIndicator(indicator);
            mSlider.setDuration(5000);

            linearLayoutManagercontent = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
            rvContent.setLayoutManager(linearLayoutManagercontent);
            ContentViewAdapter contentViewAdapter = new ContentViewAdapter(getContext(), (ArrayList<Content>) MainActivity.contents);
            rvContent.setAdapter(contentViewAdapter);

            linearLayoutManagerberita = new LinearLayoutManager(getContext());
            rvBerita.setLayoutManager(linearLayoutManagerberita);
            BeritaViewAdapter beritaViewAdapter = new BeritaViewAdapter(getContext(), (ArrayList<Berita>) MainActivity.Beritas);
            rvBerita.setAdapter(beritaViewAdapter);

            lblkonten.setVisibility(View.VISIBLE);
            view.setVisibility(View.VISIBLE);
            rlberita.setVisibility(View.VISIBLE);
            rlslider.setVisibility(View.VISIBLE);

        } else {
            getAd();
        }

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(false);
                getAd();
                MainActivity.kategoriBeritas.clear();
                MainActivity.Beritask.clear();
                MainActivity.pariwisatas.clear();
                MainActivity.tempatPariwisatas.clear();
            }
        });

        llkategoriberita = v.findViewById(R.id.llkategoriberita);
        lblBeritaselengkapnya = v.findViewById(R.id.lblBeritaselengkapnya);
        lblBeritaterbaru = v.findViewById(R.id.lblBeritaterbaru);
        tvArrow = v.findViewById(R.id.tvArrow);

        //lblBeritaselengkapnya.setTextSize(TypedValue.COMPLEX_UNIT_SP,Width/52);
        //lblBeritaterbaru.setTextSize(TypedValue.COMPLEX_UNIT_SP,Width/52);
        //tvArrow.setLayoutParams(new LinearLayout.LayoutParams(Width/19, Width/19));

        llkategoriberita.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.replaceFragment(KategoriBeritaFragment.newInstance(), 5);
            }
        });

        tv_cobalagi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getAd();
            }
        });

        return v;
    }

    private void randomSlider() {
        Random rand = new Random();
        int n = rand.nextInt(16);
//        Log.e("random", n+"");
        if (n == 0) {
            mSlider.setPresetTransformer(SliderLayout.Transformer.ZoomOutSlide);
        } else if (n == 1) {
            mSlider.setPresetTransformer(SliderLayout.Transformer.ZoomOut);
        } else if (n == 2) {
            mSlider.setPresetTransformer(SliderLayout.Transformer.Background2Foreground);
        } else if (n == 3) {
            mSlider.setPresetTransformer(SliderLayout.Transformer.ZoomIn);
        } else if (n == 4) {
            mSlider.setPresetTransformer(SliderLayout.Transformer.Accordion);
        } else if (n == 5) {
            mSlider.setPresetTransformer(SliderLayout.Transformer.CubeIn);
        } else if (n == 6) {
            mSlider.setPresetTransformer(SliderLayout.Transformer.Default);
        } else if (n == 7) {
            mSlider.setPresetTransformer(SliderLayout.Transformer.DepthPage);
        } else if (n == 8) {
            mSlider.setPresetTransformer(SliderLayout.Transformer.Fade);
        } else if (n == 9) {
            mSlider.setPresetTransformer(SliderLayout.Transformer.FlipHorizontal);
        } else if (n == 10) {
            mSlider.setPresetTransformer(SliderLayout.Transformer.FlipPage);
        } else if (n == 11) {
            mSlider.setPresetTransformer(SliderLayout.Transformer.Tablet);
        } else if (n == 12) {
            mSlider.setPresetTransformer(SliderLayout.Transformer.Stack);
        } else if (n == 13) {
            mSlider.setPresetTransformer(SliderLayout.Transformer.RotateUp);
        } else if (n == 14) {
            mSlider.setPresetTransformer(SliderLayout.Transformer.RotateDown);
        } else if (n == 15) {
            mSlider.setPresetTransformer(SliderLayout.Transformer.Foreground2Background);
        }
    }

    private void getAd() {
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
        Call<Value<Ad>> call = api.getactivead(random);
        call.enqueue(new Callback<Value<Ad>>() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onResponse(@NonNull Call<Value<Ad>> call, @NonNull Response<Value<Ad>> response) {
                if (response.body() != null) {
                    int success = Objects.requireNonNull(response.body()).getSuccess();
                    if (success == 1) {
                        List<Ad> listDataAd = Objects.requireNonNull(response.body()).getData();
                        MainActivity.ads.clear();
                        MainActivity.ads = listDataAd;
                        HashMap<String, String> url_maps = new HashMap<>();

                        if (listDataAd.size() != 0) {
                            for (int a = 0; a < listDataAd.size(); a++) {
                                url_maps.put(listDataAd.get(a).getJudul_iklan(), Utilities.getURLImageIklan() + listDataAd.get(a).getGambar_iklan());
                            }
                            mSlider.removeAllSliders();
                            mSlider.startAutoCycle();
                            for (String name : url_maps.keySet()) {
                                TextSliderView textSliderView = new TextSliderView(getContext());
                                textSliderView
                                        .description(name)
                                        .image(url_maps.get(name))
                                        .setScaleType(BaseSliderView.ScaleType.Fit);

                                textSliderView.bundle(new Bundle());
                                textSliderView.getBundle()
                                        .putString("extra", name);

                                mSlider.addSlider(textSliderView);
                            }

                            mSlider.setPresetTransformer(SliderLayout.Transformer.Accordion);
                            mSlider.setCustomIndicator(indicator);
                            mSlider.setDuration(4000);

                            rl.setVisibility(View.GONE);
                            getContent(pDialog);
                        } else {
                            mSlider.setBackgroundResource(R.drawable.defaultimage);
                            getContent(pDialog);
                        }
                    } else {
                        pDialog.dismiss();
                        Snackbar.make(Objects.requireNonNull(getActivity()).findViewById(android.R.id.content), "Gagal mengambil data. Silakan coba lagi",
                                Snackbar.LENGTH_LONG).show();
                        rl.setVisibility(View.VISIBLE);
                    }
                } else {
                    pDialog.dismiss();
                    Snackbar.make(Objects.requireNonNull(getActivity()).findViewById(android.R.id.content), "Gagal mengambil data. Silakan coba lagi",
                            Snackbar.LENGTH_LONG).show();
                    rl.setVisibility(View.VISIBLE);
                }
            }

            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onFailure(@NonNull Call<Value<Ad>> call, @NonNull Throwable t) {
                System.out.println("Retrofit Error:" + t.getMessage());
                pDialog.dismiss();
                Snackbar.make(Objects.requireNonNull(getActivity()).findViewById(android.R.id.content), "Tidak terhubung ke Internet",
                        Snackbar.LENGTH_LONG).show();
                rl.setVisibility(View.VISIBLE);
            }
        });
    }

    private void getContent(final ProgressDialog pDialog) {
        String random = Utilities.getRandom(5);

        OkHttpClient okHttpClient = Utilities.getUnsafeOkHttpClient();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Utilities.getBaseURLUser())
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();

        APIServices api = retrofit.create(APIServices.class);
        Call<Value<Content>> call = api.getContent(random);
        call.enqueue(new Callback<Value<Content>>() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onResponse(@NonNull Call<Value<Content>> call, @NonNull Response<Value<Content>> response) {
                if (response.body() != null) {
                    int success = Objects.requireNonNull(response.body()).getSuccess();
                    if (success == 1) {
                        rlslider.setVisibility(View.VISIBLE);
                        mListContent = (ArrayList<Content>) Objects.requireNonNull(response.body()).getData();
                        MainActivity.contents.clear();
                        MainActivity.contents = mListContent;

                        linearLayoutManagercontent = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
                        rvContent.setLayoutManager(linearLayoutManagercontent);
                        ContentViewAdapter contentViewAdapter = new ContentViewAdapter(getContext(), mListContent);
                        rvContent.setAdapter(contentViewAdapter);

                        rl.setVisibility(View.GONE);

                        getBerita(pDialog);
                    } else {
                        pDialog.dismiss();
                        Snackbar.make(Objects.requireNonNull(getActivity()).findViewById(android.R.id.content), "Gagal mengambil data. Silakan coba lagi",
                                Snackbar.LENGTH_LONG).show();
                        rl.setVisibility(View.VISIBLE);
                    }
                } else {
                    pDialog.dismiss();
                    Snackbar.make(Objects.requireNonNull(getActivity()).findViewById(android.R.id.content), "Gagal mengambil data. Silakan coba lagi",
                            Snackbar.LENGTH_LONG).show();
                    rl.setVisibility(View.VISIBLE);
                }
            }

            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onFailure(@NonNull Call<Value<Content>> call, @NonNull Throwable t) {
                System.out.println("Retrofit Error:" + t.getMessage());
                pDialog.dismiss();
                Snackbar.make(Objects.requireNonNull(getActivity()).findViewById(android.R.id.content), "Tidak terhubung ke Internet",
                        Snackbar.LENGTH_LONG).show();
                rl.setVisibility(View.VISIBLE);
            }
        });
    }

    private void getBerita(final ProgressDialog pDialog) {

        String random = Utilities.getRandom(5);

        OkHttpClient okHttpClient = Utilities.getUnsafeOkHttpClient();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Utilities.getBaseURLUser())
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();

        APIServices api = retrofit.create(APIServices.class);
        Call<Value<Berita>> call = api.getBerita(random);
        call.enqueue(new Callback<Value<Berita>>() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onResponse(@NonNull Call<Value<Berita>> call, @NonNull Response<Value<Berita>> response) {
                if (response.body() != null) {
                    int success = Objects.requireNonNull(response.body()).getSuccess();
                    if (success == 1) {
                        mListBerita = (ArrayList<Berita>) Objects.requireNonNull(response.body()).getData();
                        MainActivity.Beritas.clear();
                        MainActivity.Beritas = mListBerita;

                        linearLayoutManagerberita = new LinearLayoutManager(getContext());
                        rvBerita.setLayoutManager(linearLayoutManagerberita);
                        BeritaViewAdapter beritaViewAdapter = new BeritaViewAdapter(getContext(), mListBerita);
                        rvBerita.setAdapter(beritaViewAdapter);

                        lblkonten.setVisibility(View.VISIBLE);
                        view.setVisibility(View.VISIBLE);
                        rlberita.setVisibility(View.VISIBLE);
                        pDialog.dismiss();

                    } else {
                        pDialog.dismiss();
                        Snackbar.make(Objects.requireNonNull(getActivity()).findViewById(android.R.id.content), "Gagal mengambil data. Silakan coba lagi",
                                Snackbar.LENGTH_LONG).show();
                        rl.setVisibility(View.VISIBLE);
                    }
                } else {
                    pDialog.dismiss();
                    Snackbar.make(Objects.requireNonNull(getActivity()).findViewById(android.R.id.content), "Gagal mengambil data. Silakan coba lagi",
                            Snackbar.LENGTH_LONG).show();
                    rl.setVisibility(View.VISIBLE);
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
            }
        });
    }

    private void getBerita1() {

        String random = Utilities.getRandom(5);

        OkHttpClient okHttpClient = Utilities.getUnsafeOkHttpClient();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Utilities.getBaseURLUser())
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();

        APIServices api = retrofit.create(APIServices.class);
        Call<Value<Berita>> call = api.getBerita(random);
        call.enqueue(new Callback<Value<Berita>>() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onResponse(@NonNull Call<Value<Berita>> call, @NonNull Response<Value<Berita>> response) {
                if (response.body() != null) {
                    int success = Objects.requireNonNull(response.body()).getSuccess();
                    if (success == 1) {
                        mListBerita = (ArrayList<Berita>) Objects.requireNonNull(response.body()).getData();
                        MainActivity.Beritas.clear();
                        MainActivity.Beritas = mListBerita;

                        linearLayoutManagerberita = new LinearLayoutManager(getContext());
                        rvBerita.setLayoutManager(linearLayoutManagerberita);
                        BeritaViewAdapter beritaViewAdapter = new BeritaViewAdapter(getContext(), mListBerita);
                        rvBerita.setAdapter(beritaViewAdapter);

                        lblkonten.setVisibility(View.VISIBLE);
                        view.setVisibility(View.VISIBLE);
                        rlberita.setVisibility(View.VISIBLE);
                    }
                }
            }

            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onFailure(@NonNull Call<Value<Berita>> call, @NonNull Throwable t) {
                System.out.println("Retrofit Error:" + t.getMessage());
            }
        });
    }

}