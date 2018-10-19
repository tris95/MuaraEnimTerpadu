package id.go.muaraenimkab.bappeda.muaraenimterpadu.fragments;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
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

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.gun0912.tedpermission.TedPermissionResult;
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

import id.go.muaraenimkab.bappeda.muaraenimterpadu.R;
import id.go.muaraenimkab.bappeda.muaraenimterpadu.services.DirectionsJSONParser;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;


public class LokasiEventFragment extends Fragment {
    private static final String ARG_lat = "lat", ARG_lng = "lng", ARG_nama_lokasi = "nama_lokasi", ARG_nama_event = "nama_event";
    String lat, lng, namaLokasi, namaEvent;
    MapView mapView;
    Toolbar toolbar;
    GoogleMap gMap;
    FusedLocationProviderClient mFusedLocationClient;
    LatLng currentLatLng;

    public LokasiEventFragment() {
    }

    public static LokasiEventFragment newInstance(String lat, String lng, String namaLokasi, String namaEvent) {
        LokasiEventFragment fragment = new LokasiEventFragment();
        Bundle args = new Bundle();
        args.putString(ARG_lat, lat);
        args.putString(ARG_lng, lng);
        args.putString(ARG_nama_lokasi, namaLokasi);
        args.putString(ARG_nama_event, namaEvent);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            lat = getArguments().getString(ARG_lat);
            lng = getArguments().getString(ARG_lng);
            namaLokasi = getArguments().getString(ARG_nama_lokasi);
            namaEvent = getArguments().getString(ARG_nama_event);
        }
    }

    @SuppressLint("NewApi")
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_lokasi_event, container, false);

        toolbar = v.findViewById(R.id.toolbar);

        ((AppCompatActivity) Objects.requireNonNull(getActivity())).setSupportActionBar(toolbar);

        if (((AppCompatActivity) getActivity()).getSupportActionBar() != null) {
            Objects.requireNonNull(((AppCompatActivity) getActivity()).getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
            Objects.requireNonNull(((AppCompatActivity) getActivity()).getSupportActionBar()).setDisplayShowHomeEnabled(true);
            Objects.requireNonNull(((AppCompatActivity) getActivity()).getSupportActionBar()).setTitle(namaEvent);
        }

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(getActivity());

//        if (ContextCompat.checkSelfPermission(getActivity(),
//                android.Manifest.permission.ACCESS_FINE_LOCATION)
//                != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(getActivity(),
//                android.Manifest.permission.ACCESS_COARSE_LOCATION)
//                != PackageManager.PERMISSION_GRANTED) {
//
//            ActivityCompat.requestPermissions(getActivity(), new String[]{
//                    android.Manifest.permission.ACCESS_FINE_LOCATION}, 1);
//        }

//        mFusedLocationClient.getLastLocation()
//                .addOnSuccessListener(getActivity(), new OnSuccessListener<Location>() {
//                    @Override
//                    public void onSuccess(Location location) {
//                        if (location != null) {
//                            currentLatLng = new LatLng(location.getLatitude(), location.getLongitude());
//                            markerGPS.position(currentLatLng);
//                            gMap.addMarker(markerGPS);
//                            gMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, 12));
//                        } else {
//                            LatLng destlatLng = new LatLng(Double.parseDouble(lat), Double.parseDouble(lng));
//                            gMap.addMarker(new MarkerOptions().position(destlatLng).title(namaLokasi));
//                            gMap.animateCamera(CameraUpdateFactory.newCameraPosition(new CameraPosition.Builder().target(destlatLng).zoom(15).build()));
//                        }
//                    }
//                });

        mapView = v.findViewById(R.id.mapsEvent);
        mapView.onCreate(savedInstanceState);
        mapView.onResume();

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

                    TedRx2Permission.with(Objects.requireNonNull(getContext()))
                            .setRationaleTitle("Izin Akses")
                            .setRationaleMessage("Untuk mengakses Lokasi harap izinkan Lokasi")
                            .setPermissions(Manifest.permission.ACCESS_FINE_LOCATION)
                            .request()
                            .subscribe(new Observer<TedPermissionResult>() {
                                @Override
                                public void onSubscribe(Disposable d) {

                                }

                                @Override
                                public void onNext(TedPermissionResult tedPermissionResult) {
                                    if (tedPermissionResult.isGranted()) {
                                        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                                                ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
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
                                                .addOnSuccessListener(getActivity(), new OnSuccessListener<Location>() {
                                                    @Override
                                                    public void onSuccess(Location location) {
                                                        if (location != null) {
                                                            currentLatLng = new LatLng(location.getLatitude(), location.getLongitude());
                                                            gMap.addMarker(new MarkerOptions().position(currentLatLng).title("Saya"));
                                                            gMap.animateCamera(CameraUpdateFactory.newCameraPosition(new CameraPosition.Builder().target(currentLatLng).zoom(15).build()));
//                                    gMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, 12));

                                                            LatLng destlatLng = new LatLng(Double.parseDouble(lat), Double.parseDouble(lng));
                                                            gMap.addMarker(new MarkerOptions().position(destlatLng).title(namaLokasi));
//                                    gMap.animateCamera(CameraUpdateFactory.newCameraPosition(new CameraPosition.Builder().target(destlatLng).zoom(15).build()));

//                                    if (currentLatLng != null && destlatLng != null) {
                                                            String url = getUrl(currentLatLng, destlatLng);
                                                            DownloadTask FetchUrl = new DownloadTask();
                                                            FetchUrl.execute(url);
//                                    }
                                                        } else {
                                                            Snackbar.make(Objects.requireNonNull(getActivity()).findViewById(android.R.id.content), "Silahkan hidupkan GPS Anda",
                                                                    Snackbar.LENGTH_LONG).show();

                                                            LatLng destlatLng = new LatLng(Double.parseDouble(lat), Double.parseDouble(lng));
                                                            gMap.addMarker(new MarkerOptions().position(destlatLng).title(namaLokasi));
                                                            gMap.animateCamera(CameraUpdateFactory.newCameraPosition(new CameraPosition.Builder().target(destlatLng).zoom(15).build()));
                                                        }
                                                    }
                                                });
                                    } else {
                                        Snackbar.make(getActivity().getWindow().getDecorView().getRootView(),
                                                "Harap mengaktifkan izin Lokasi",
                                                Snackbar.LENGTH_INDEFINITE)
                                                .setAction("OK", new View.OnClickListener() {
                                                    @Override
                                                    public void onClick(View v) {
                                                        Intent intent = new Intent();
                                                        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                                        Uri uri = Uri.fromParts("package", getActivity().getPackageName(), null);
                                                        intent.setData(uri);
                                                        startActivity(intent);
                                                    }
                                                })
                                                .show();
                                    }
                                }

                                @Override
                                public void onError(Throwable e) {

                                }

                                @Override
                                public void onComplete() {

                                }
                            });
                }else {
                    mFusedLocationClient.getLastLocation()
                            .addOnSuccessListener(getActivity(), new OnSuccessListener<Location>() {
                                @Override
                                public void onSuccess(Location location) {
                                    if (location != null) {
                                        currentLatLng = new LatLng(location.getLatitude(), location.getLongitude());
                                        gMap.addMarker(new MarkerOptions().position(currentLatLng).title("Saya"));
                                        gMap.animateCamera(CameraUpdateFactory.newCameraPosition(new CameraPosition.Builder().target(currentLatLng).zoom(15).build()));
//                                    gMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, 12));

                                        LatLng destlatLng = new LatLng(Double.parseDouble(lat), Double.parseDouble(lng));
                                        gMap.addMarker(new MarkerOptions().position(destlatLng).title(namaLokasi));
//                                    gMap.animateCamera(CameraUpdateFactory.newCameraPosition(new CameraPosition.Builder().target(destlatLng).zoom(15).build()));

//                                    if (currentLatLng != null && destlatLng != null) {
                                        String url = getUrl(currentLatLng, destlatLng);
                                        DownloadTask FetchUrl = new DownloadTask();
                                        FetchUrl.execute(url);
//                                    }
                                    } else {
                                        Snackbar.make(Objects.requireNonNull(getActivity()).findViewById(android.R.id.content), "Silahkan hidupkan GPS Anda",
                                                Snackbar.LENGTH_LONG).show();

                                        LatLng destlatLng = new LatLng(Double.parseDouble(lat), Double.parseDouble(lng));
                                        gMap.addMarker(new MarkerOptions().position(destlatLng).title(namaLokasi));
                                        gMap.animateCamera(CameraUpdateFactory.newCameraPosition(new CameraPosition.Builder().target(destlatLng).zoom(15).build()));
                                    }
                                }
                            });
                }
            }
        });

        return v;
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
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        Log.e("reqcode", ""+requestCode);
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
                                        gMap.addMarker(new MarkerOptions().position(destlatLng).title(namaLokasi));
//                                        gMap.animateCamera(CameraUpdateFactory.newCameraPosition(new CameraPosition.Builder().target(destlatLng).zoom(15).build()));

//                                        if (currentLatLng != null && destlatLng != null) {
                                            String url = getUrl(currentLatLng, destlatLng);
                                            DownloadTask FetchUrl = new DownloadTask();
                                            FetchUrl.execute(url);
//                                        }
                                    } else {
                                        Snackbar.make(Objects.requireNonNull(getActivity()).findViewById(android.R.id.content), "Silahkan hidupkan GPS Anda",
                                                Snackbar.LENGTH_LONG).show();

                                        LatLng destlatLng = new LatLng(Double.parseDouble(lat), Double.parseDouble(lng));
                                        gMap.addMarker(new MarkerOptions().position(destlatLng).title(namaLokasi));
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
