package id.go.muaraenimkab.bappeda.muaraenimterpadu.activities;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.Toast;

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

public class MainActivity extends AppCompatActivity {
    Fragment fragment;
    public static int flag2;
    static Fragment lastFragment,lastFragment2;
    static FragmentManager fragmentManager;
    static BottomNavigationViewEx bottomNavigationView;
    static int currentFragment = 0, nowFragment = 0;
    public static List<Ad> ads = new ArrayList<>();

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
//                if (item.getItemId() == R.id.navigation_home) {
//                    fragment = HomeFragment.newInstance();
//                } else if (item.getItemId() == R.id.navigation_kontak) {
//                    fragment = KontakFragment.newInstance();
//                } else if (item.getItemId() == R.id.navigation_event) {
//                    fragment = EventFragment.newInstance();
//                } else if (item.getItemId() == R.id.navigation_laporan) {
//                    fragment = LaporanFragment.newInstance();
//                } else if (item.getItemId() == R.id.navigation_profil) {
//                    fragment = ProfilFragment.newInstance();
//                }
//                fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
//                fragmentManager.beginTransaction().replace(R.id.container, fragment).commit();
//
//                return true;
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
        fragmentManager.beginTransaction().replace(R.id.container, fragment).commit();
    }

    public static void setCurrenBNV(int flag) {
        bottomNavigationView.setCurrentItem(flag);
    }

    @Override
    public void onBackPressed() {
        if (nowFragment != currentFragment) {
            if (nowFragment == 7) {
                fragmentManager.beginTransaction().replace(R.id.container, lastFragment2).commit();
                nowFragment = 6;
            } else if (nowFragment == 6) {
                fragmentManager.beginTransaction().replace(R.id.container, lastFragment).commit();
                nowFragment = 5;
            } else {
                bottomNavigationView.setCurrentItem(currentFragment);
            }
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        if (nowFragment != currentFragment) {
            if (nowFragment == 7) {
                fragmentManager.beginTransaction().replace(R.id.container, lastFragment2).commit();
                nowFragment = 6;
            } else if (nowFragment == 6) {
                fragmentManager.beginTransaction().replace(R.id.container, lastFragment).commit();
                nowFragment = 5;
            } else {
                bottomNavigationView.setCurrentItem(currentFragment);
            }
        } else {
            super.onBackPressed();
        }
        return true;
    }
}
