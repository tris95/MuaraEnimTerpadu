package id.go.muaraenimkab.bappeda.muaraenimterpadu.fragments;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
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
import id.go.muaraenimkab.bappeda.muaraenimterpadu.services.DirectionsJSONParser;


public class DetailKontakFragment extends Fragment implements OnMapReadyCallback {
    Toolbar toolbar;
    private static final String ARG_kontak = "kontak",ARG_lat = "latitude",ARG_lng = "longitude",
            ARG_alamat = "alamat",ARG_notlp = "notlp",ARG_gambar = "gambar";
    String kontak, no_tlp, alamat, gambar, lat, lng;
    TextView lblnotlp, lblalamatkontak;
    ImageView imgKontak;
    CollapsingToolbarLayout collapsingKontak;
    private GoogleMap mMap;
    SupportMapFragment mapskontak;
    FusedLocationProviderClient mFusedLocationClient;
    LatLng currentLatLng;

    public DetailKontakFragment() {
        // Required empty public constructor
    }

    public static DetailKontakFragment newInstance(String kontak,String lat,String lng,
                                                   String alamat,String no_tlp,String gambar) {
        DetailKontakFragment fragment = new DetailKontakFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        args.putString(ARG_kontak, kontak);
        args.putString(ARG_lat, lat);
        args.putString(ARG_lng, lng);
        args.putString(ARG_alamat, alamat);
        args.putString(ARG_notlp, no_tlp);
        args.putString(ARG_gambar, gambar);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            kontak = getArguments().getString(ARG_kontak);
            lat = getArguments().getString(ARG_lat);
            lng = getArguments().getString(ARG_lng);
            alamat = getArguments().getString(ARG_alamat);
            no_tlp = getArguments().getString(ARG_notlp);
            gambar = getArguments().getString(ARG_gambar);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_detail_kontak, container, false);
        toolbar = v.findViewById(R.id.toolbar);
        //mapskontak = (SupportMapFragment) Objects.requireNonNull(getActivity()).getSupportFragmentManager().findFragmentById(R.id.mapsKontak);
        imgKontak = v.findViewById(R.id.imgkontak);
        collapsingKontak = v.findViewById(R.id.collapsingKontak);
        lblnotlp = v.findViewById(R.id.lblnotlp);
        lblalamatkontak = v.findViewById(R.id.lblalamatkontak);

        //mapskontak.getMapAsync(this);

        ((AppCompatActivity) Objects.requireNonNull(getActivity())).setSupportActionBar(toolbar);

//        if (ContextCompat.checkSelfPermission(Objects.requireNonNull(getContext()),
//                android.Manifest.permission.ACCESS_FINE_LOCATION)
//                != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(getContext(),
//                android.Manifest.permission.ACCESS_COARSE_LOCATION)
//                != PackageManager.PERMISSION_GRANTED) {
//
//            ActivityCompat.requestPermissions(getActivity(), new String[]{
//                    android.Manifest.permission.ACCESS_FINE_LOCATION}, 1);
//        }
//
//        mFusedLocationClient.getLastLocation()
//                .addOnSuccessListener(getActivity(), new OnSuccessListener<Location>() {
//                    @Override
//                    public void onSuccess(Location location) {
//                        if (location != null) {
//                            currentLatLng = new LatLng(location.getLatitude(), location.getLongitude());
//                            MarkerOptions options = new MarkerOptions();
//                            options.position(currentLatLng);
//                            options.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
//                            mMap.addMarker(options.title("Saya")).showInfoWindow();
//                            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, 15));
//
//                        } else {
//                            Snackbar.make(Objects.requireNonNull(getActivity()).findViewById(android.R.id.content), "Silahkan hidupkan GPS Anda",
//                                    Snackbar.LENGTH_LONG).show();
//
//                            currentLatLng = new LatLng(-2.9852218, 104.7538641);
//                            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, 12));
//
//                        }
//                    }
//                });
        if (((AppCompatActivity) Objects.requireNonNull(getActivity())).getSupportActionBar() != null) {
            Objects.requireNonNull(((AppCompatActivity) getActivity()).getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
            Objects.requireNonNull(((AppCompatActivity) getActivity()).getSupportActionBar()).setDisplayShowHomeEnabled(true);
            Objects.requireNonNull(((AppCompatActivity) getActivity()).getSupportActionBar()).setTitle("");
        }


        collapsingKontak.setTitle(kontak);

        Picasso.with(getContext())
                .load(gambar)
                .into(imgKontak);

        if (!no_tlp.equals(""))
            lblnotlp.setText(no_tlp);
        else
            lblnotlp.setVisibility(View.GONE);

        if (!alamat.equals(""))
            lblalamatkontak.setText(alamat);
        else
            lblalamatkontak.setVisibility(View.GONE);

        lblnotlp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!no_tlp.equals("")) {
                    ActivityCompat.requestPermissions(getActivity(),
                            new String[]{Manifest.permission.CALL_PHONE},
                            1);
                } else {
                    Snackbar.make(Objects.requireNonNull(getActivity()).findViewById(android.R.id.content), "Tidak Ada Nomor",
                            Snackbar.LENGTH_LONG).show();
                }
            }
        });
        return v;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        if (ActivityCompat.checkSelfPermission(Objects.requireNonNull(getContext()), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mFusedLocationClient.getLastLocation()
                .addOnSuccessListener(Objects.requireNonNull(getActivity()), new OnSuccessListener<Location>() {
                    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                    @Override
                    public void onSuccess(Location location) {
                        if (location != null) {
                            currentLatLng = new LatLng(location.getLatitude(), location.getLongitude());
                            MarkerOptions options = new MarkerOptions();
                            options.position(currentLatLng);
//                                            getAddress(currentLatLng.latitude, currentLatLng.longitude);
                            options.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
                            mMap.addMarker(options.title("Saya")).showInfoWindow();
                            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, 15));
//                                            Utilities.showAsToast(MapsActivity.this, "Tekan dan tahan pada peta untuk menetapkan lokasi", Toast.LENGTH_LONG);
                        } else {
                            Snackbar.make(Objects.requireNonNull(getActivity()).findViewById(android.R.id.content), "Silahkan hidupkan GPS Anda",
                                    Snackbar.LENGTH_LONG).show();
                            currentLatLng = new LatLng(-2.9852218, 104.7538641);
                            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, 12));

                        }
                    }
                });
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1: {
                if (grantResults[0] == 0) {
                    String uri = "tel:" + no_tlp;
                    Intent intent = new Intent(Intent.ACTION_CALL);
                    intent.setData(Uri.parse(uri));
                    if (ActivityCompat.checkSelfPermission(Objects.requireNonNull(getContext()), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                        // TODO: Consider calling
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.
                        return;
                    }
                    startActivity(intent);
                } else {
                    Snackbar.make(Objects.requireNonNull(getActivity()).findViewById(android.R.id.content), "Izinkan Aplikasi Mengakses Kontak",
                            Snackbar.LENGTH_LONG).show();
                }
            }
            case 2:{
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ContextCompat.checkSelfPermission(Objects.requireNonNull(getContext()),
                            android.Manifest.permission.ACCESS_COARSE_LOCATION)
                            != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(Objects.requireNonNull(getActivity()),
                                new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION}, 2);
                    } else {
                        ActivityCompat.requestPermissions(Objects.requireNonNull(getActivity()),
                                new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION}, 2);
                    }
                } else {
                    ActivityCompat.requestPermissions(Objects.requireNonNull(getActivity()),
                            new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                            1);
                }
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    mFusedLocationClient.getLastLocation()
                            .addOnSuccessListener(Objects.requireNonNull(getActivity()), new OnSuccessListener<Location>() {
                                @Override
                                public void onSuccess(Location location) {
                                    if (location != null) {
                                        currentLatLng = new LatLng(location.getLatitude(), location.getLongitude());
                                        MarkerOptions options = new MarkerOptions();
                                        options.position(currentLatLng);
//                                            getAddress(currentLatLng.latitude, currentLatLng.longitude);
                                        options.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
                                        mMap.addMarker(options.title("Saya")).showInfoWindow();
                                        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, 15));

                                    } else {
                                        Snackbar.make(Objects.requireNonNull(getActivity()).findViewById(android.R.id.content), "Silahkan hidupkan GPS Anda",
                                                Snackbar.LENGTH_LONG).show();
                                        currentLatLng = new LatLng(-2.9852218, 104.7538641);
                                        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, 12));

                                    }
                                }
                            });
                } else {
                    ActivityCompat.requestPermissions(Objects.requireNonNull(getActivity()),
                            new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION}, 2);
                }
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

        return "https://maps.googleapis.com/maps/api/directions/" + output + "?" + parameters;
    }

    private class DownloadTask extends AsyncTask<String, Void, String> {

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
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

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
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
            Objects.requireNonNull(iStream).close();
            Objects.requireNonNull(urlConnection).disconnect();
        }
        return data;
    }

    @SuppressLint("StaticFieldLeak")
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
            ArrayList<LatLng> points;
            PolylineOptions lineOptions = null;
            MarkerOptions markerOptions = new MarkerOptions();

            for (int i = 0; i < result.size(); i++) {
                points = new ArrayList<>();
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
                mMap.addPolyline(lineOptions);
            } catch (Exception e) {
                Log.e("polyline", e.toString());
            }
        }
    }
}