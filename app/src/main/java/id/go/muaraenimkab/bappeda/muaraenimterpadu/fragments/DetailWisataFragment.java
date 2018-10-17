package id.go.muaraenimkab.bappeda.muaraenimterpadu.fragments;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
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
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

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
import com.squareup.picasso.Picasso;

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

import id.go.muaraenimkab.bappeda.muaraenimterpadu.R;
import id.go.muaraenimkab.bappeda.muaraenimterpadu.activities.MainActivity;
import id.go.muaraenimkab.bappeda.muaraenimterpadu.services.DirectionsJSONParser;


public class DetailWisataFragment extends Fragment {
    Toolbar toolbar, tb1;
//    RelativeLayout rlup, rlback;
    private BottomSheetBehavior bottomSheetBehavior;
    View bottomsheet;
    DocumentView lbldeskripsiwisata;
    ImageView imgdetailwisata, imgup;
//    MainActivity mainActivity =new MainActivity();
    String idparawisata, namapariwisata, gambarpariwisata, deskripsi, lat, lng, alamat;
    private static final String ARG_alamat = "alamat", ARG_lat = "lat", ARG_lng = "lng", ARG_idparawisata = "idparawisata",ARG_namapariwisata = "namapariwisata",
            ARG_gambarpariwisata = "gambarpariwisata", ARG_deskripsi = "deskripsi";
    boolean flagSlide;

    MapView mapView;
    GoogleMap gMap;
    FusedLocationProviderClient mFusedLocationClient;
    LatLng currentLatLng;

    public DetailWisataFragment() {
        // Required empty public constructor
    }

    public static DetailWisataFragment newInstance(String idpariwisata,String namapariwisata,
                                                   String gambarpariwisata,String deskripsi,String alamat,String lat,String lng) {
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
        imgdetailwisata=v.findViewById(R.id.imgdetailwisata);
//        rlup = v.findViewById(R.id.rlup);
//        rlback=v.findViewById(R.id.rlback);
        lbldeskripsiwisata=v.findViewById(R.id.lbldeskripsiwisata);

        ((AppCompatActivity) Objects.requireNonNull(getActivity())).setSupportActionBar(toolbar);

        if (((AppCompatActivity) getActivity()).getSupportActionBar() != null) {
            Objects.requireNonNull(((AppCompatActivity) getActivity()).getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
            Objects.requireNonNull(((AppCompatActivity) getActivity()).getSupportActionBar()).setDisplayShowHomeEnabled(true);
            Objects.requireNonNull(((AppCompatActivity) getActivity()).getSupportActionBar()).setTitle(namapariwisata);
        }

        Display display = getActivity().getWindowManager().getDefaultDisplay();
        final DisplayMetrics outMetrics = new DisplayMetrics();
        display.getMetrics(outMetrics);
        int viewPagerWidth = Math.round(outMetrics.widthPixels);
        int more = viewPagerWidth/6;
        int viewPagerHeight = (Math.round(outMetrics.widthPixels)/2)+more;

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
                }else {
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

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(getActivity());

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
                if (slideOffset < 0.7){
                    mapView.setAlpha((float) 1);
                //    tb1.setVisibility(View.GONE);
                }else if (slideOffset > 0.3){
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

                if (ContextCompat.checkSelfPermission(getActivity(),
                        android.Manifest.permission.ACCESS_FINE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(getContext(),
                        android.Manifest.permission.ACCESS_COARSE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {

                    requestPermissions(new String[]{
                            android.Manifest.permission.ACCESS_FINE_LOCATION}, 1);
                }
                mFusedLocationClient.getLastLocation()
                        .addOnSuccessListener(getActivity(), new OnSuccessListener<Location>() {
                            @Override
                            public void onSuccess(Location location) {
                                if (location != null) {
                                    currentLatLng = new LatLng(location.getLatitude(), location.getLongitude());
                                    gMap.addMarker(new MarkerOptions().position(currentLatLng).title("Saya"));
                                    //gMap.animateCamera(CameraUpdateFactory.newCameraPosition(new CameraPosition.Builder().target(currentLatLng).zoom(15).build()));
//                                    gMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, 12));

                                    LatLng destlatLng = new LatLng(Double.parseDouble(lat), Double.parseDouble(lng));
                                    gMap.addMarker(new MarkerOptions().position(destlatLng).title(alamat));
                                    gMap.animateCamera(CameraUpdateFactory.newCameraPosition(new CameraPosition.Builder().target(destlatLng).zoom(15).build()));

//                                    if (currentLatLng != null && destlatLng != null) {
                                    String url = getUrl(currentLatLng, destlatLng);
                                    Log.e("url", url);
                                    DownloadTask FetchUrl = new DownloadTask();
                                    FetchUrl.execute(url);
//                                    }
                                } else {
                                    Snackbar.make(Objects.requireNonNull(getActivity()).findViewById(android.R.id.content), "Silahkan hidupkan GPS Anda",
                                            Snackbar.LENGTH_LONG).show();

                                    LatLng destlatLng = new LatLng(Double.parseDouble(lat), Double.parseDouble(lng));
                                    gMap.addMarker(new MarkerOptions().position(destlatLng).title(alamat));
                                    gMap.animateCamera(CameraUpdateFactory.newCameraPosition(new CameraPosition.Builder().target(destlatLng).zoom(15).build()));
                                }
                            }
                        });
            }
        });

        return v;
    }

    public void setslide(View view){
        TranslateAnimation animation=new TranslateAnimation(
                0, 0, 0, 0,
                Animation.ABSOLUTE,0,Animation.ABSOLUTE,view.getHeight()
        );
        animation.setDuration(500);
        animation.setFillAfter(true);
        view.startAnimation(animation);
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

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
//        Log.e("reqcode", ""+requestCode);
        switch (requestCode) {
            case 1: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ContextCompat.checkSelfPermission(getActivity(),
                            android.Manifest.permission.ACCESS_COARSE_LOCATION)
                            != PackageManager.PERMISSION_GRANTED) {
                        requestPermissions(new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION}, 2);
                    }
                    else {
                        requestPermissions(new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION}, 2);
                    }
                }else {
                    requestPermissions(new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                            1);
                }
                return;
            }
            case 2: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        Log.e("permission", "DENIED");
                        return;
                    }else{
                        Log.e("permission", "GRANTED");
                    }

                    mFusedLocationClient.getLastLocation()
                            .addOnSuccessListener(getActivity(), new OnSuccessListener<Location>() {
                                @SuppressLint("NewApi")
                                @Override
                                public void onSuccess(Location location) {
                                    if (location != null) {
                                        currentLatLng = new LatLng(location.getLatitude(), location.getLongitude());
                                        gMap.addMarker(new MarkerOptions().position(currentLatLng).title("Saya"));
                                        gMap.animateCamera(CameraUpdateFactory.newCameraPosition(new CameraPosition.Builder().target(currentLatLng).zoom(15).build()));
//                                        gMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, 12));

                                        LatLng destlatLng = new LatLng(Double.parseDouble(lat), Double.parseDouble(lng));
                                        gMap.addMarker(new MarkerOptions().position(destlatLng).title(alamat));
//                                        gMap.animateCamera(CameraUpdateFactory.newCameraPosition(new CameraPosition.Builder().target(destlatLng).zoom(15).build()));

//                                        if (currentLatLng != null && destlatLng != null) {
                                        String url = getUrl(currentLatLng, destlatLng);
                                        Log.e("url", url);
                                        DownloadTask FetchUrl = new DownloadTask();
                                        FetchUrl.execute(url);
//                                        }
                                    } else {
                                        Snackbar.make(Objects.requireNonNull(getActivity()).findViewById(android.R.id.content), "Silahkan hidupkan GPS Anda",
                                                Snackbar.LENGTH_LONG).show();

                                        LatLng destlatLng = new LatLng(Double.parseDouble(lat), Double.parseDouble(lng));
                                        gMap.addMarker(new MarkerOptions().position(destlatLng).title(alamat));
                                        gMap.animateCamera(CameraUpdateFactory.newCameraPosition(new CameraPosition.Builder().target(destlatLng).zoom(15).build()));
                                    }
                                }
                            });
//                      }
//                    }
                }else {
                    requestPermissions(new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION}, 2);
                }
                return;
            }
        }
    }

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
            }catch (Exception e){
                Log.e("polyline", e.toString());
            }
        }
    }

}
