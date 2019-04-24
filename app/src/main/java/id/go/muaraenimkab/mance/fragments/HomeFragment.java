package id.go.muaraenimkab.mance.fragments;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.slider.library.Indicators.PagerIndicator;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.squareup.picasso.Picasso;
//import com.synnapps.carouselview.CarouselView;
//import com.synnapps.carouselview.ImageListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
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
import id.go.muaraenimkab.mance.services.Function;
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
//    CarouselView carouselView;
    RelativeLayout rl, rlberita, rlslider;
    TextView lblBeritaselengkapnya, lblBeritaterbaru, tv_cobalagi, lblkonten;
    RecyclerView rvContent, rvBerita;
    ArrayList<Content> mListContent;
    ArrayList<Berita> mListBerita;
    public static List<Berita> mListisiBerita = new ArrayList<>();
    LinearLayoutManager linearLayoutManagercontent, linearLayoutManagerberita;
    LinearLayout llkategoriberita, llProfil, llweather;
    SwipeRefreshLayout swipeRefreshLayout;
    PagerIndicator indicator;
    ImageView tvArrow;
    CardView cvTentang, cvVisiMisi;
    public static boolean flag = false;
    TextView cityField, currentTemperatureField, detailsField, weatherIcon, updatedField;
    Typeface weatherFont;
    String city = "Lahat, ID";
    String OPEN_WEATHER_MAP_API = "2868da8f7b4fc2e07277a5dfed5368bd";

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
        cvTentang = v.findViewById(R.id.cv1);
        cvVisiMisi = v.findViewById(R.id.cv2);
        llProfil = v.findViewById(R.id.llProfil);
        weatherIcon = v.findViewById(R.id.weather_icon);
        weatherFont = Typeface.createFromAsset(Objects.requireNonNull(getActivity()).getAssets(), "fonts/weathericons-regular-webfont.ttf");
        weatherIcon.setTypeface(weatherFont);
        cityField = v.findViewById(R.id.city_field);
        updatedField = v.findViewById(R.id.updated_field);
        currentTemperatureField = v.findViewById(R.id.current_temperature_field);
        detailsField = v.findViewById(R.id.details_field);
        llweather = v.findViewById(R.id.ll_weather);

        //((AppCompatActivity) Objects.requireNonNull(getActivity())).setSupportActionBar(toolbar);

//        if (((AppCompatActivity) getActivity()).getSupportActionBar() != null) {
//            Objects.requireNonNull(((AppCompatActivity) getActivity()).getSupportActionBar()).setTitle("Muara Enim Center");
//        }

//        carouselView = v.findViewById(R.id.carouselView);

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
        LinearLayout.LayoutParams params_tentang = new LinearLayout.LayoutParams(viewPagerHeight-125, viewPagerWidth/4);
        LinearLayout.LayoutParams params_visimisi = new LinearLayout.LayoutParams(viewPagerHeight+35, viewPagerWidth/4);
        params_tentang.setMargins(10,10,0,10);
        params_visimisi.setMargins(30,10,10,10);
        cvTentang.setLayoutParams(params_tentang);
        cvVisiMisi.setLayoutParams(params_visimisi);

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
//            randomSlider();
            mSlider.setCustomIndicator(indicator);
            mSlider.setDuration(5000);

//            linearLayoutManagercontent = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
//            rvContent.setLayoutManager(linearLayoutManagercontent);
//            ContentViewAdapter contentViewAdapter = new ContentViewAdapter(getContext(), (ArrayList<Content>) MainActivity.contents);
//            rvContent.setAdapter(contentViewAdapter);

            rvContent.setHasFixedSize(true);
            rvContent.setLayoutManager(new GridLayoutManager(getActivity(), 4));
            ContentViewAdapter rcAdapter = new ContentViewAdapter(getActivity(), (ArrayList<Content>) MainActivity.contents);
            rvContent.setAdapter(rcAdapter);


            linearLayoutManagerberita = new LinearLayoutManager(getContext());
            rvBerita.setLayoutManager(linearLayoutManagerberita);
            BeritaViewAdapter beritaViewAdapter = new BeritaViewAdapter(getContext(), (ArrayList<Berita>) MainActivity.Beritas);
            rvBerita.setAdapter(beritaViewAdapter);

            lblkonten.setVisibility(View.VISIBLE);
//            view.setVisibility(View.VISIBLE);
            rlberita.setVisibility(View.VISIBLE);
            rlslider.setVisibility(View.VISIBLE);
//            cvTentang.setVisibility(View.VISIBLE);
//            cvVisiMisi.setVisibility(View.VISIBLE);
            llProfil.setVisibility(View.VISIBLE);
            taskLoadUp(city);
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

        cvTentang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.replaceFragment(TentangFragment.newInstance(), 5);
            }
        });

        cvVisiMisi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.replaceFragment(VisiMisiFragment.newInstance(), 5);
            }
        });

        return v;
    }

//    ImageListener imageListener = new ImageListener() {
//        @Override
//        public void setImageForPosition(int position, ImageView imageView) {
////            imageView.setImageResource(MainActivity.ads[position]);
//            Picasso.with(getContext()).load(Utilities.getURLImageIklan() + MainActivity.ads.get(position).getGambar_iklan()).into(imageView);
//        }
//    };

    @SuppressLint("NewApi")
    public void taskLoadUp(String query) {
        if (Utilities.isNetworkAvailable(Objects.requireNonNull(getActivity()))) {
            DownloadWeather task = new DownloadWeather();
            task.execute(query);
        } else {
//            Snackbar.make(Objects.requireNonNull(getActivity()).findViewById(android.R.id.content), "Tidak terhubung ke Internet",
//                    Snackbar.LENGTH_LONG).show();
            llweather.setVisibility(View.GONE);
        }
    }

    @SuppressLint("StaticFieldLeak")
    class DownloadWeather extends AsyncTask< String, Void, String > {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
        protected String doInBackground(String...args) {
            String xml = Function.excuteGet("http://api.openweathermap.org/data/2.5/weather?q=" + args[0] +
                    "&units=metric&lang=id&appid=" + OPEN_WEATHER_MAP_API);
            return xml;
        }
        @SuppressLint("NewApi")
        @Override
        protected void onPostExecute(String xml) {

            try {
                JSONObject json = new JSONObject(xml);
                if (json != null) {
                    JSONObject details = json.getJSONArray("weather").getJSONObject(0);
                    JSONObject main = json.getJSONObject("main");
                    DateFormat df = DateFormat.getDateTimeInstance();

                    llweather.setVisibility(View.VISIBLE);
//                    cityField.setText(json.getString("name").toUpperCase(Locale.US) + ", " + json.getJSONObject("sys").getString("country"));
                    cityField.setText("Muara Enim, Indonesia");
                    detailsField.setText(details.getString("description").toUpperCase(Locale.US));
                    currentTemperatureField.setText(String.format("%.1f", main.getDouble("temp")) + "°C");
//                    humidity_field.setText("Humidity: " + main.getString("humidity") + "%");
//                    pressure_field.setText("Pressure: " + main.getString("pressure") + " hPa");
                    updatedField.setText(df.format(new Date(json.getLong("dt") * 1000)));
                    weatherIcon.setText(Html.fromHtml(Function.setWeatherIcon(details.getInt("id"),
                            json.getJSONObject("sys").getLong("sunrise") * 1000,
                            json.getJSONObject("sys").getLong("sunset") * 1000)));

                }
            } catch (JSONException e) {
//                Snackbar.make(Objects.requireNonNull(getActivity()).findViewById(android.R.id.content), "Kota "+ city+" tidak terdaftar",
//                        Snackbar.LENGTH_LONG).show();
                llweather.setVisibility(View.GONE);
            }
        }
    }

//    private void randomSlider() {
//        Random rand = new Random();
//        int n = rand.nextInt(16);
////        Log.e("random", n+"");
//        if (n == 0) {
//            mSlider.setPresetTransformer(SliderLayout.Transformer.ZoomOutSlide);
//        } else if (n == 1) {
//            mSlider.setPresetTransformer(SliderLayout.Transformer.ZoomOut);
//        } else if (n == 2) {
//            mSlider.setPresetTransformer(SliderLayout.Transformer.Background2Foreground);
//        } else if (n == 3) {
//            mSlider.setPresetTransformer(SliderLayout.Transformer.ZoomIn);
//        } else if (n == 4) {
//            mSlider.setPresetTransformer(SliderLayout.Transformer.Accordion);
//        } else if (n == 5) {
//            mSlider.setPresetTransformer(SliderLayout.Transformer.CubeIn);
//        } else if (n == 6) {
//            mSlider.setPresetTransformer(SliderLayout.Transformer.Default);
//        } else if (n == 7) {
//            mSlider.setPresetTransformer(SliderLayout.Transformer.DepthPage);
//        } else if (n == 8) {
//            mSlider.setPresetTransformer(SliderLayout.Transformer.Fade);
//        } else if (n == 9) {
//            mSlider.setPresetTransformer(SliderLayout.Transformer.FlipHorizontal);
//        } else if (n == 10) {
//            mSlider.setPresetTransformer(SliderLayout.Transformer.FlipPage);
//        } else if (n == 11) {
//            mSlider.setPresetTransformer(SliderLayout.Transformer.Tablet);
//        } else if (n == 12) {
//            mSlider.setPresetTransformer(SliderLayout.Transformer.Stack);
//        } else if (n == 13) {
//            mSlider.setPresetTransformer(SliderLayout.Transformer.RotateUp);
//        } else if (n == 14) {
//            mSlider.setPresetTransformer(SliderLayout.Transformer.RotateDown);
//        } else if (n == 15) {
//            mSlider.setPresetTransformer(SliderLayout.Transformer.Foreground2Background);
//        }
//    }

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

//                            carouselView.setImageListener(imageListener);
//                            carouselView.setPageCount(listDataAd.size());

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

//                        linearLayoutManagercontent = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
//                        rvContent.setLayoutManager(linearLayoutManagercontent);
//                        ContentViewAdapter contentViewAdapter = new ContentViewAdapter(getContext(), mListContent);
//                        rvContent.setAdapter(contentViewAdapter);

                        rvContent.setHasFixedSize(true);
                        rvContent.setLayoutManager(new GridLayoutManager(getActivity(), 4));
                        ContentViewAdapter rcAdapter = new ContentViewAdapter(getActivity(), (ArrayList<Content>) MainActivity.contents);
                        rvContent.setAdapter(rcAdapter);

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
//                        view.setVisibility(View.VISIBLE);
                        rlberita.setVisibility(View.VISIBLE);
//                        cvTentang.setVisibility(View.VISIBLE);
//                        cvVisiMisi.setVisibility(View.VISIBLE);
                        llProfil.setVisibility(View.VISIBLE);
//                        llweather.setVisibility(View.VISIBLE);
                        taskLoadUp(city);
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
//                        view.setVisibility(View.VISIBLE);
                        rlberita.setVisibility(View.VISIBLE);
//                        cvTentang.setVisibility(View.VISIBLE);
//                        cvVisiMisi.setVisibility(View.VISIBLE);
                        llProfil.setVisibility(View.VISIBLE);
//                        llweather.setVisibility(View.VISIBLE);
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
