package id.go.muaraenimkab.mance.fragments;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.location.Location;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.bluejamesbond.text.DocumentView;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.gun0912.tedpermission.TedPermissionResult;
import com.squareup.picasso.Picasso;
import com.tedpark.tedpermission.rx2.TedRx2Permission;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import id.go.muaraenimkab.mance.R;
import id.go.muaraenimkab.mance.services.DirectionsJSONParser;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;


public class DetailWisataFragment extends Fragment {
    Toolbar toolbar, tb1;
    //    RelativeLayout rlup, rlback;
    private BottomSheetBehavior bottomSheetBehavior;
    View bottomsheet;
    DocumentView lbldeskripsiwisata;
    ImageView imgdetailwisata, imgup;
    WebView webView;
    //    MainActivity mainActivity =new MainActivity();
    String idparawisata, namapariwisata, gambarpariwisata, deskripsi, lat, lng, alamat;
    private static final String ARG_alamat = "alamat", ARG_lat = "lat", ARG_lng = "lng", ARG_idparawisata = "idparawisata", ARG_namapariwisata = "namapariwisata",
            ARG_gambarpariwisata = "gambarpariwisata", ARG_deskripsi = "deskripsi";
    boolean flagSlide;

    MapView mapView;
    GoogleMap gMap;
//    FusedLocationProviderClient mFusedLocationClient;
//    LatLng currentLatLng;

    boolean loadingFinished = true;
    boolean redirect = false;
    ProgressDialog pDialog;

    public DetailWisataFragment() {
        // Required empty public constructor
    }

    public static DetailWisataFragment newInstance(String idpariwisata, String namapariwisata,
                                                   String gambarpariwisata, String deskripsi, String alamat, String lat, String lng) {
        DetailWisataFragment fragment = new DetailWisataFragment();
        Bundle args = new Bundle();
        args.putString(ARG_alamat, alamat);
        args.putString(ARG_lat, lat);
        args.putString(ARG_lng, lng);
        args.putString(ARG_idparawisata, idpariwisata);
        args.putString(ARG_namapariwisata, namapariwisata);
        args.putString(ARG_gambarpariwisata, gambarpariwisata);
        args.putString(ARG_deskripsi, deskripsi);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            alamat = getArguments().getString(ARG_alamat);
            lat = getArguments().getString(ARG_lat);
            lng = getArguments().getString(ARG_lng);
            idparawisata = getArguments().getString(ARG_idparawisata);
            namapariwisata = getArguments().getString(ARG_namapariwisata);
            gambarpariwisata = getArguments().getString(ARG_gambarpariwisata);
            deskripsi = getArguments().getString(ARG_deskripsi);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_detail_wisata, container, false);

        toolbar = v.findViewById(R.id.toolbar);
        //tb1 = v.findViewById(R.id.tb1);
//        tb2 = v.findViewById(R.id.tb2);
        bottomsheet = v.findViewById(R.id.bottom_sheet);
        bottomSheetBehavior = BottomSheetBehavior.from(bottomsheet);
        imgup = v.findViewById(R.id.imgup);
//        imgback = v.findViewById(R.id.imgback);
        imgdetailwisata = v.findViewById(R.id.imgdetailwisata);
//        rlup = v.findViewById(R.id.rlup);
//        rlback=v.findViewById(R.id.rlback);
        lbldeskripsiwisata = v.findViewById(R.id.lbldeskripsiwisata);

        webView = v.findViewById(R.id.wv);

        ((AppCompatActivity) Objects.requireNonNull(getActivity())).setSupportActionBar(toolbar);

        if (((AppCompatActivity) getActivity()).getSupportActionBar() != null) {
            Objects.requireNonNull(((AppCompatActivity) getActivity()).getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
            Objects.requireNonNull(((AppCompatActivity) getActivity()).getSupportActionBar()).setDisplayShowHomeEnabled(true);
            Objects.requireNonNull(((AppCompatActivity) getActivity()).getSupportActionBar()).setTitle(namapariwisata);
        }

        if (isValid(deskripsi)) {
            webView.setVisibility(View.VISIBLE);
            imgdetailwisata.setVisibility(View.GONE);
            lbldeskripsiwisata.setVisibility(View.GONE);

            pDialog = new ProgressDialog(getActivity());
            pDialog.setMessage("Loading...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);

            webView.setWebViewClient(new MyBrowser());
            webView.getSettings().setLoadsImagesAutomatically(true);
            //wvPrivacyPolicy.getSettings().setJavaScriptEnabled(true);
            webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
            if (Build.VERSION.SDK_INT >= 19) {
                webView.setLayerType(View.LAYER_TYPE_HARDWARE, null);
            } else {
                webView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
            }

            webView.loadUrl(deskripsi);
        } else {
            webView.setVisibility(View.GONE);
            imgdetailwisata.setVisibility(View.VISIBLE);
            lbldeskripsiwisata.setVisibility(View.VISIBLE);
        }

        Display display = getActivity().getWindowManager().getDefaultDisplay();
        final DisplayMetrics outMetrics = new DisplayMetrics();
        display.getMetrics(outMetrics);
        int viewPagerWidth = Math.round(outMetrics.widthPixels);
        int more = viewPagerWidth / 6;
        int viewPagerHeight = (Math.round(outMetrics.widthPixels) / 2) + more;

        imgdetailwisata.setLayoutParams(new LinearLayout.LayoutParams(viewPagerWidth, viewPagerHeight));


        Picasso.with(getContext())
                .load(gambarpariwisata)
                .into(imgdetailwisata);

        lbldeskripsiwisata.setText(deskripsi);

        flagSlide = false;

        setslide(bottomsheet);

        imgup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (flagSlide) {
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                } else {
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                }
            }
        });

//        imgback.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mainActivity.Back();
//            }
//        });

//        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(getActivity());

        mapView = v.findViewById(R.id.mapsWisata);
        mapView.onCreate(savedInstanceState);
        mapView.onResume();

        bottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                switch (newState) {
                    case BottomSheetBehavior.STATE_EXPANDED:
                        flagSlide = true;
                        imgup.setImageDrawable(getResources().getDrawable(R.drawable.baseline_keyboard_arrow_down_24));
                        break;
                    case BottomSheetBehavior.STATE_COLLAPSED:
                        flagSlide = false;
                        imgup.setImageDrawable(getResources().getDrawable(R.drawable.baseline_keyboard_arrow_up_24));
                        break;
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
                if (slideOffset < 0.1) {
                    mapView.setAlpha((float) 1);
                    //    tb1.setVisibility(View.GONE);
                } else if (slideOffset > 0.1) {
                    mapView.setAlpha((float) 0.2);
                    //    tb1.setVisibility(View.VISIBLE);
                }
            }
        });

        try {
            MapsInitializer.initialize(Objects.requireNonNull(getActivity()).getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

        mapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                gMap = googleMap;
                if (gMap != null) gMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);

                @SuppressLint("ResourceType") View navigationControl = mapView.findViewById(0x4);

                RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams)
                        navigationControl.getLayoutParams();
                //         position on right bottom
                layoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP, RelativeLayout.TRUE);
                layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, 0);
                layoutParams.setMargins(0, 40, 40, 0);

//                if (ContextCompat.checkSelfPermission(getActivity(),
//                        android.Manifest.permission.ACCESS_FINE_LOCATION)
//                        != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(Objects.requireNonNull(getContext()),
//                        android.Manifest.permission.ACCESS_COARSE_LOCATION)
//                        != PackageManager.PERMISSION_GRANTED) {

//                    TedRx2Permission.with(Objects.requireNonNull(getContext()))
//                            .setRationaleTitle("Izin Akses")
//                            .setRationaleMessage("Untuk mengakses aplikasi harap izinkan lokasi")
//                            .setPermissions(Manifest.permission.ACCESS_FINE_LOCATION)
//                            .request()
//                            .subscribe(new Observer<TedPermissionResult>() {
//                                @Override
//                                public void onSubscribe(Disposable d) {
//
//                                }
//
//                                @Override
//                                public void onNext(TedPermissionResult tedPermissionResult) {
//                                    if (tedPermissionResult.isGranted()) {
//                                        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
//                                                ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//                                            // TODO: Consider calling
//                                            //    ActivityCompat#requestPermissions
//                                            // here to request the missing permissions, and then overriding
//                                            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//                                            //                                          int[] grantResults)
//                                            // to handle the case where the user grants the permission. See the documentation
//                                            // for ActivityCompat#requestPermissions for more details.
//                                            return;
//                                        }
//                                        mFusedLocationClient.getLastLocation()
//                                                .addOnSuccessListener(getActivity(), new OnSuccessListener<Location>() {
//                                                    @Override
//                                                    public void onSuccess(Location location) {
//                                                        if (location != null) {
////                                                            currentLatLng = new LatLng(location.getLatitude(), location.getLongitude());
////                                                            gMap.addMarker(new MarkerOptions().position(currentLatLng).title("Saya"));
//
//                                                            //gMap.animateCamera(CameraUpdateFactory.newCameraPosition(new CameraPosition.Builder().target(currentLatLng).zoom(15).build()));
////                                    gMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, 12));
//
//                                                            LatLng destlatLng = new LatLng(Double.parseDouble(lat), Double.parseDouble(lng));
//                                                            gMap.addMarker(new MarkerOptions().position(destlatLng).title(alamat)).showInfoWindow();
//                                                            gMap.animateCamera(CameraUpdateFactory.newCameraPosition(new CameraPosition.Builder().target(destlatLng).zoom(15).build()));
//
////                                    if (currentLatLng != null && destlatLng != null) {
////                                                            String url = getUrl(currentLatLng, destlatLng);
////                                                            Log.e("url", url);
////                                                            DownloadTask FetchUrl = new DownloadTask();
////                                                            FetchUrl.execute(url);
////                                    }
//                                                        } else {
////                                                            Snackbar.make(Objects.requireNonNull(getActivity()).findViewById(android.R.id.content), "Silakan hidupkan GPS Anda",
////                                                                    Snackbar.LENGTH_LONG).show();
//
//                                                            LatLng destlatLng = new LatLng(Double.parseDouble(lat), Double.parseDouble(lng));
//                                                            gMap.addMarker(new MarkerOptions().position(destlatLng).title(alamat)).showInfoWindow();
//                                                            gMap.animateCamera(CameraUpdateFactory.newCameraPosition(new CameraPosition.Builder().target(destlatLng).zoom(15).build()));
//                                                        }
//                                                    }
//                                                });
//                                    } else {
//                                        Snackbar.make(Objects.requireNonNull(getActivity()).findViewById(android.R.id.content), "Harap mengaktifkan izin Lokasi",
//                                                Snackbar.LENGTH_INDEFINITE)
//                                                .setAction("OK", new View.OnClickListener() {
//                                                    @Override
//                                                    public void onClick(View v) {
//                                                        Intent intent = new Intent();
//                                                        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
//                                                        Uri uri = Uri.fromParts("package", getActivity().getPackageName(), null);
//                                                        intent.setData(uri);
//                                                        startActivity(intent);
//                                                    }
//                                                })
//                                                .show();
////                                        Snackbar.make(getActivity().getWindow().getDecorView().getRootView(),
////                                                "Harap mengaktifkan izin Lokasi",
////                                                Snackbar.LENGTH_INDEFINITE)
////                                                .setAction("OK", new View.OnClickListener() {
////                                                    @Override
////                                                    public void onClick(View v) {
////                                                        Intent intent = new Intent();
////                                                        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
////                                                        Uri uri = Uri.fromParts("package", getActivity().getPackageName(), null);
////                                                        intent.setData(uri);
////                                                        startActivity(intent);
////                                                    }
////                                                })
////                                                .show();
//                                    }
//                                }
//
//                                @Override
//                                public void onError(Throwable e) {
//
//                                }
//
//                                @Override
//                                public void onComplete() {
//
//                                }
//                            });
//                    requestPermissions(new String[]{
//                            android.Manifest.permission.ACCESS_FINE_LOCATION}, 1);
//                } else {
//                    mFusedLocationClient.getLastLocation()
//                            .addOnSuccessListener(getActivity(), new OnSuccessListener<Location>() {
//                                @Override
//                                public void onSuccess(Location location) {
//                                    if (location != null) {
//                                        currentLatLng = new LatLng(location.getLatitude(), location.getLongitude());
//                                        gMap.addMarker(new MarkerOptions().position(currentLatLng).title("Saya"));

                                        //gMap.animateCamera(CameraUpdateFactory.newCameraPosition(new CameraPosition.Builder().target(currentLatLng).zoom(15).build()));
//                                    gMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, 12));

                                        LatLng destlatLng = new LatLng(Double.parseDouble(lat), Double.parseDouble(lng));
                                        gMap.addMarker(new MarkerOptions().position(destlatLng).title(alamat)).showInfoWindow();
                                        gMap.animateCamera(CameraUpdateFactory.newCameraPosition(new CameraPosition.Builder().target(destlatLng).zoom(15).build()));

//                                    if (currentLatLng != null && destlatLng != null) {
//                                        String url = getUrl(currentLatLng, destlatLng);
//                                        Log.e("url", url);
//                                        DownloadTask FetchUrl = new DownloadTask();
//                                        FetchUrl.execute(url);
//                                    }
//                                    } else {
////                                        Snackbar.make(Objects.requireNonNull(getActivity()).findViewById(android.R.id.content), "Silakan hidupkan GPS Anda",
////                                                Snackbar.LENGTH_LONG).show();
//
//                                        LatLng destlatLng = new LatLng(Double.parseDouble(lat), Double.parseDouble(lng));
//                                        gMap.addMarker(new MarkerOptions().position(destlatLng).title(alamat)).showInfoWindow();
//                                        gMap.animateCamera(CameraUpdateFactory.newCameraPosition(new CameraPosition.Builder().target(destlatLng).zoom(15).build()));
//                                    }
//                                }
//                            });
//                }
            }
        });

        return v;
    }

    public void setslide(View view) {
        TranslateAnimation animation = new TranslateAnimation(
                0, 0, 0, 0,
                Animation.ABSOLUTE, 0, Animation.ABSOLUTE, view.getHeight()
        );
        animation.setDuration(500);
        animation.setFillAfter(true);
        view.startAnimation(animation);
    }

    public static boolean isValid(String url) {
        try {
            new URL(url).toURI();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private class MyBrowser extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String urlNewString) {
            if (!loadingFinished) {
                redirect = true;
            }

            loadingFinished = false;
            view.loadUrl(urlNewString);
            return true;
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap facIcon) {
            loadingFinished = false;
            pDialog.show();
            //SHOW LOADING IF IT ISNT ALREADY VISIBLE
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            if (!redirect) {
                loadingFinished = true;
            }

            if (loadingFinished && !redirect) {
                //HIDE LOADING IT HAS FINISHED
                pDialog.dismiss();
                //wvPrivacyPolicy.setVisibility(View.INVISIBLE);
            } else {
                redirect = false;
            }

        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

//    @SuppressLint("NewApi")
//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
//        switch (requestCode) {
//            case 1: {
//                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                    if (ContextCompat.checkSelfPermission(Objects.requireNonNull(getActivity()),
//                            android.Manifest.permission.ACCESS_COARSE_LOCATION)
//                            != PackageManager.PERMISSION_GRANTED) {
//                        requestPermissions(new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION}, 2);
//                    } else {
//                        requestPermissions(new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION}, 2);
//                    }
//                } else {
//                    requestPermissions(new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
//                            1);
//                }
//                return;
//            }
//            case 2: {
//                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                    if (ActivityCompat.checkSelfPermission(Objects.requireNonNull(getActivity()), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//                        Log.e("permission", "DENIED");
//                        return;
//                    } else {
//                        Log.e("permission", "GRANTED");
//                    }
//
////                    mFusedLocationClient.getLastLocation()
////                            .addOnSuccessListener(getActivity(), new OnSuccessListener<Location>() {
////                                @SuppressLint("NewApi")
////                                @Override
////                                public void onSuccess(Location location) {
////                                    if (location != null) {
////                                        currentLatLng = new LatLng(location.getLatitude(), location.getLongitude());
////                                        gMap.addMarker(new MarkerOptions().position(currentLatLng).title("Saya"));
////                                        gMap.animateCamera(CameraUpdateFactory.newCameraPosition(new CameraPosition.Builder().target(currentLatLng).zoom(15).build()));
////                                        gMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, 12));
//
//                    LatLng destlatLng = new LatLng(Double.parseDouble(lat), Double.parseDouble(lng));
//                    gMap.addMarker(new MarkerOptions().position(destlatLng).title(alamat)).showInfoWindow();
//                    gMap.animateCamera(CameraUpdateFactory.newCameraPosition(new CameraPosition.Builder().target(destlatLng).zoom(15).build()));
//
////                                        if (currentLatLng != null && destlatLng != null) {
////                                        String url = getUrl(currentLatLng, destlatLng);
////                                        Log.e("url", url);
////                                        DownloadTask FetchUrl = new DownloadTask();
////                                        FetchUrl.execute(url);
////                                        }
////                                    } else {
////                                        Snackbar.make(Objects.requireNonNull(getActivity()).findViewById(android.R.id.content), "Silakan hidupkan GPS Anda",
////                                                Snackbar.LENGTH_LONG).show();
////
////                                        LatLng destlatLng = new LatLng(Double.parseDouble(lat), Double.parseDouble(lng));
////                                        gMap.addMarker(new MarkerOptions().position(destlatLng).title(alamat));
////                                        gMap.animateCamera(CameraUpdateFactory.newCameraPosition(new CameraPosition.Builder().target(destlatLng).zoom(15).build()));
////                                    }
////                                }
////                            });
////                      }
////                    }
//                } else {
//                    requestPermissions(new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION}, 2);
//                }
//            }
//        }
//    }

    private String getUrl(LatLng origin, LatLng dest) {
        // Origin of route
        String str_origin = "origin=" + origin.latitude + "," + origin.longitude;
        // Destination of route
        String str_dest = "destination=" + dest.latitude + "," + dest.longitude;
        // Sensor enabled
        String sensor = "sensor=false";
        // Building the parameters to the web service
        String parameters = str_origin + "&" + str_dest + "&" + sensor;
        // Output format
        String output = "json";
        // Building the url to the web service
        String url = "https://maps.googleapis.com/maps/api/directions/" + output + "?" + parameters;

        return url;
    }

    private class DownloadTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... url) {

            String data = "";

            try {
                data = downloadUrl(url[0]);
            } catch (Exception e) {
                Log.d("Background Task", e.toString());
            }
            return data;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            ParserTask parserTask = new ParserTask();
            parserTask.execute(result);
        }
    }

    private String downloadUrl(String strUrl) throws IOException {
        String data = "";
        InputStream iStream = null;
        HttpURLConnection urlConnection = null;
        try {
            URL url = new URL(strUrl);

            urlConnection = (HttpURLConnection) url.openConnection();

            urlConnection.connect();

            iStream = urlConnection.getInputStream();

            BufferedReader br = new BufferedReader(new InputStreamReader(iStream));

            StringBuffer sb = new StringBuffer();

            String line = "";
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

            data = sb.toString();

            br.close();

        } catch (Exception e) {
            Log.d("Exception", e.toString());
        } finally {
            iStream.close();
            urlConnection.disconnect();
        }
        return data;
    }

    private class ParserTask extends AsyncTask<String, Integer, List<List<HashMap<String, String>>>> {
        @Override
        protected List<List<HashMap<String, String>>> doInBackground(String... jsonData) {

            JSONObject jObject;
            List<List<HashMap<String, String>>> routes = null;

            try {
                jObject = new JSONObject(jsonData[0]);
                DirectionsJSONParser parser = new DirectionsJSONParser();

                routes = parser.parse(jObject);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return routes;
        }

        @Override
        protected void onPostExecute(List<List<HashMap<String, String>>> result) {
            ArrayList points = null;
            PolylineOptions lineOptions = null;
//            MarkerOptions markerOptions = new MarkerOptions();

            for (int i = 0; i < result.size(); i++) {
                points = new ArrayList();
                lineOptions = new PolylineOptions();

                List<HashMap<String, String>> path = result.get(i);

                for (int j = 0; j < path.size(); j++) {
                    HashMap<String, String> point = path.get(j);

                    double lat = Double.parseDouble(point.get("lat"));
                    double lng = Double.parseDouble(point.get("lng"));
                    LatLng position = new LatLng(lat, lng);

                    points.add(position);
                }

                lineOptions.addAll(points);
                lineOptions.width(12);
                lineOptions.color(Color.BLUE);
                lineOptions.geodesic(true);

            }

            try {
                gMap.addPolyline(lineOptions);
            } catch (Exception e) {
                Log.e("polyline", e.toString());
            }
        }
    }

}
