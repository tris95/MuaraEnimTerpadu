package id.go.muaraenimkab.bappeda.muaraenimterpadu.fragments;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gun0912.tedpermission.TedPermissionResult;
import com.tedpark.tedpermission.rx2.TedRx2Permission;

import java.util.Objects;

import id.go.muaraenimkab.bappeda.muaraenimterpadu.R;
import id.go.muaraenimkab.bappeda.muaraenimterpadu.activities.SignInActivity;
import id.go.muaraenimkab.bappeda.muaraenimterpadu.utils.Utilities;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;


public class LaporanFragment extends Fragment {
    private static final String ARG_idlaporan = "idlaporan", ARG_flag = "flag";
    String idlaporan, flag,idp;
    TabLayout tabLayout;
    ViewPager mViewPager;
    Toolbar toolbar;

    public static LaporanFragment newInstance() {
        LaporanFragment fragment = new LaporanFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public static LaporanFragment newInstance(String idlaporan, String flag) {
        LaporanFragment fragment = new LaporanFragment();
        Bundle args = new Bundle();
        args.putString(ARG_idlaporan, idlaporan);
        args.putString(ARG_flag, flag);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            idlaporan = getArguments().getString(ARG_idlaporan);
            flag = getArguments().getString(ARG_flag);
        }
    }

    @SuppressLint("HardwareIds")
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_laporan, container, false);
        toolbar = v.findViewById(R.id.toolbar);
        ((AppCompatActivity) Objects.requireNonNull(getActivity())).setSupportActionBar(toolbar);

        if (((AppCompatActivity) getActivity()).getSupportActionBar() != null) {
            Objects.requireNonNull(((AppCompatActivity) getActivity()).getSupportActionBar()).setTitle("Laporan");
        }
        tabLayout = v.findViewById(R.id.tabs);
        mViewPager = v.findViewById(R.id.container);

        tabLayout.setTabTextColors(Color.parseColor("#E0E0E0"), Color.parseColor("#FFFFFF"));
        tabLayout.setSelectedTabIndicatorColor(Color.parseColor("#FFFFFF"));
        mViewPager.setAdapter(new MyAdapter(getChildFragmentManager()));

        tabLayout.post(new Runnable() {
            @Override
            public void run() {
                tabLayout.setupWithViewPager(mViewPager);
            }
        });
        if (flag != null) {
            if (flag.equals("3")) {
                mViewPager.setCurrentItem(1);
            }
        }

        TedRx2Permission.with(Objects.requireNonNull(getContext()))
                .setRationaleTitle("Izin Akses")
                .setRationaleMessage("Untuk mengakses Aplikasi harap izinkan Telepon")
                .setPermissions(Manifest.permission.READ_PHONE_STATE)
                .request()
                .subscribe(new Observer<TedPermissionResult>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @SuppressLint("HardwareIds")
                    @Override
                    public void onNext(TedPermissionResult tedPermissionResult) {
                        if (tedPermissionResult.isGranted()) {
                            if (ActivityCompat.checkSelfPermission(Objects.requireNonNull(getActivity()), Manifest.permission.READ_PHONE_STATE) !=
                                    PackageManager.PERMISSION_GRANTED) {
                                // TODO: Consider calling
                                //    ActivityCompat#requestPermissions
                                // here to request the missing permissions, and then overriding
                                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                //                                          int[] grantResults)
                                // to handle the case where the user grants the permission. See the documentation
                                // for ActivityCompat#requestPermissions for more details.
                                return;
                            }
                            //ime = Objects.requireNonNull(telephonyManager).getDeviceId();
                            idp = Settings.Secure.getString(Objects.requireNonNull(getActivity()).getContentResolver(), Settings.Secure.ANDROID_ID);
                        } else {
                            Snackbar.make(Objects.requireNonNull(getActivity()).findViewById(android.R.id.content), "Harap mengaktifkan izin Telepon",
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
//                            Snackbar.make(getActivity().getWindow().getDecorView().getRootView(),
//                                    "Harap mengaktifkan izin Telepon",
//                                    Snackbar.LENGTH_INDEFINITE)
//                                    .setAction("OK", new View.OnClickListener() {
//                                        @Override
//                                        public void onClick(View v) {
//                                            Intent intent = new Intent();
//                                            intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
//                                            Uri uri = Uri.fromParts("package", getActivity().getPackageName(), null);
//                                            intent.setData(uri);
//                                            startActivity(intent);
//                                        }
//                                    })
//                                    .show();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
        try {
            idp = Settings.Secure.getString(Objects.requireNonNull(getActivity()).getContentResolver(), Settings.Secure.ANDROID_ID);
        }catch (Exception e) {
            e.printStackTrace();
        }
        Utilities.setLogin(getActivity(),Utilities.getUser(getActivity()).getEmail(),idp);
        if (!Utilities.isLogin(getContext())) {
            AlertDialog.Builder builder = new AlertDialog.Builder(Objects.requireNonNull(getContext()));
            builder.setCancelable(true)
                    .setTitle("Pemberitahuan")
                    .setCancelable(false)
                    .setMessage("Untuk Akses Menu Laporan Lakukan Login Terlebih Dahulu")
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            startActivity(new Intent(getContext(), SignInActivity.class));
                            getActivity().finish();
                        }
                    })
                    .show();
        }

        return v;
    }

//    public void onButtonPressed(Uri uri) {
//    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu,inflater);
        inflater.inflate(R.menu.navigation_profil, menu);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

//    public interface OnFragmentInteractionListener {
//        void onFragmentInteraction(Uri uri);
//    }

    class MyAdapter extends FragmentPagerAdapter {
        MyAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return new KirimLaporanFragment();
                case 1:
                    return new LaporanSayaFragment(flag);
            }
            return null;
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Kirim Laporan";
                case 1:
                    return "Laporan Saya";
            }
            return null;
        }
    }

}
