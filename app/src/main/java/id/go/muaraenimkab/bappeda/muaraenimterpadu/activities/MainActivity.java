package id.go.muaraenimkab.bappeda.muaraenimterpadu.activities;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import java.util.ArrayList;
import java.util.List;

import id.go.muaraenimkab.bappeda.muaraenimterpadu.R;
import id.go.muaraenimkab.bappeda.muaraenimterpadu.fragments.EventFragment;
import id.go.muaraenimkab.bappeda.muaraenimterpadu.fragments.HomeFragment;
import id.go.muaraenimkab.bappeda.muaraenimterpadu.fragments.KontakFragment;
import id.go.muaraenimkab.bappeda.muaraenimterpadu.fragments.LaporanFragment;
import id.go.muaraenimkab.bappeda.muaraenimterpadu.fragments.ProfilFragment;
import id.go.muaraenimkab.bappeda.muaraenimterpadu.models.Ad;
import id.go.muaraenimkab.bappeda.muaraenimterpadu.models.Berita;
import id.go.muaraenimkab.bappeda.muaraenimterpadu.models.Content;
import id.go.muaraenimkab.bappeda.muaraenimterpadu.models.KategoriBerita;
import id.go.muaraenimkab.bappeda.muaraenimterpadu.models.Kontak;

public class MainActivity extends AppCompatActivity {
    Fragment fragment;
    public static int flag2;
    static Fragment lastFragment,lastFragment2;
    static FragmentManager fragmentManager;
    static FragmentTransaction fragmentTransaction;
    @SuppressLint("StaticFieldLeak")
    static BottomNavigationViewEx bottomNavigationView;
    static int currentFragment = 0, nowFragment = 0;
    public static List<Ad> ads = new ArrayList<>();
    public static List<Content> contents = new ArrayList<>();
    public static List<Kontak> kontaks = new ArrayList<>();
    public static List<KategoriBerita> kategoriBeritas= new ArrayList<>();
    public static List<Berita> Beritas= new ArrayList<>();


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        fragmentManager = getSupportFragmentManager();

        fragment = HomeFragment.newInstance();
        currentFragment = 0;
        nowFragment = 0;
        fragmentManager.beginTransaction().replace(R.id.container, fragment).commit();

        bottomNavigationView.enableAnimation(true);
        bottomNavigationView.enableShiftingMode(false);
        bottomNavigationView.enableItemShiftingMode(false);
        bottomNavigationView.setIconSize(27, 27);
        bottomNavigationView.setTextSize(11);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_home:
                        currentFragment = 0;
                        nowFragment = 0;
                        fragment = HomeFragment.newInstance();
                        lastFragment = fragment;
                        fragmentManager.beginTransaction().replace(R.id.container, fragment).commit();
                        break;
                    case R.id.navigation_kontak:
                        currentFragment = 1;
                        nowFragment = 1;
                        fragment = KontakFragment.newInstance();
                        lastFragment = fragment;
                        fragmentManager.beginTransaction().replace(R.id.container, fragment).commit();
                        break;
                    case R.id.navigation_event:
                        currentFragment = 2;
                        nowFragment = 2;
                        fragment = EventFragment.newInstance();
                        lastFragment = fragment;
                        fragmentManager.beginTransaction().replace(R.id.container, fragment).commit();
                        break;
                    case R.id.navigation_laporan:
                        currentFragment = 3;
                        nowFragment = 3;
                        fragment = LaporanFragment.newInstance();
                        lastFragment = fragment;
                        fragmentManager.beginTransaction().replace(R.id.container, fragment).commit();
                        break;
                    case R.id.navigation_profil:
                        currentFragment = 4;
                        nowFragment = 4;
                        fragment = ProfilFragment.newInstance();
                        lastFragment = fragment;
                        fragmentManager.beginTransaction().replace(R.id.container, fragment).commit();
                        break;
                }
                return true;
            }
        });
    }

    public static void replaceFragment(Fragment fragment, int flag) {
        if (flag == 7) {
            nowFragment = flag;
        }
        else if (flag == 6) {
            nowFragment = flag;
            lastFragment2 = fragment;
        }
        else {
            nowFragment = flag;
            lastFragment = fragment;
        }
        flag2 = flag;
        fragmentTransaction=fragmentManager.beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.enter_from_right,R.anim.enter_from_right);
        fragmentTransaction.replace(R.id.container, fragment).commit();
        //fragmentManager.beginTransaction().replace(R.id.container, fragment).commit();
    }


    @Override
    public void onBackPressed() {
        if (nowFragment != currentFragment) {
            if (nowFragment == 7) {
                fragmentTransaction=fragmentManager.beginTransaction();
                fragmentTransaction.setCustomAnimations(R.anim.exit_to_right,R.anim.exit_to_right);
                fragmentTransaction.replace(R.id.container, lastFragment2).commit();
                //fragmentManager.beginTransaction().replace(R.id.container, lastFragment2).commit();
                nowFragment = 6;
            } else if (nowFragment == 6) {
                fragmentTransaction=fragmentManager.beginTransaction();
                fragmentTransaction.setCustomAnimations(R.anim.exit_to_right,R.anim.exit_to_right);
                fragmentTransaction.replace(R.id.container, lastFragment).commit();
                //fragmentManager.beginTransaction().replace(R.id.container, lastFragment).commit();
                nowFragment = 5;
            } else {
                fragmentTransaction=fragmentManager.beginTransaction();
                fragmentTransaction.setCustomAnimations(R.anim.exit_to_right,R.anim.exit_to_right);
                fragmentTransaction.replace(R.id.container, fragment).commit();
                nowFragment = currentFragment;
                //bottomNavigationView.setCurrentItem(currentFragment);
            }
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        if (nowFragment != currentFragment) {
            if (nowFragment == 7) {
                fragmentTransaction=fragmentManager.beginTransaction();
                fragmentTransaction.setCustomAnimations(R.anim.exit_to_right,R.anim.exit_to_right);
                fragmentTransaction.replace(R.id.container, lastFragment2).commit();
                //fragmentManager.beginTransaction().replace(R.id.container, lastFragment2).commit();
                nowFragment = 6;
            } else if (nowFragment == 6) {
                fragmentTransaction=fragmentManager.beginTransaction();
                fragmentTransaction.setCustomAnimations(R.anim.exit_to_right,R.anim.exit_to_right);
                fragmentTransaction.replace(R.id.container, lastFragment).commit();
                //fragmentManager.beginTransaction().replace(R.id.container, lastFragment).commit();
                nowFragment = 5;
            } else {
                fragmentTransaction=fragmentManager.beginTransaction();
                fragmentTransaction.setCustomAnimations(R.anim.exit_to_right,R.anim.exit_to_right);
                fragmentTransaction.replace(R.id.container, fragment).commit();
                nowFragment = currentFragment;
                //bottomNavigationView.setCurrentItem(currentFragment);
            }
        } else {
            super.onBackPressed();
        }
        return true;
    }
}
