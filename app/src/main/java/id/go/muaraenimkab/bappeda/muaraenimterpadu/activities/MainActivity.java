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

import id.go.muaraenimkab.bappeda.muaraenimterpadu.R;
import id.go.muaraenimkab.bappeda.muaraenimterpadu.fragments.EventFragment;
import id.go.muaraenimkab.bappeda.muaraenimterpadu.fragments.HomeFragment;
import id.go.muaraenimkab.bappeda.muaraenimterpadu.fragments.KontakFragment;
import id.go.muaraenimkab.bappeda.muaraenimterpadu.fragments.LaporanFragment;
import id.go.muaraenimkab.bappeda.muaraenimterpadu.fragments.ProfilFragment;

public class MainActivity extends AppCompatActivity {
    Fragment fragment;
    FragmentManager fragmentManager;
    BottomNavigationView bottomNavigationView;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        fragmentManager = getSupportFragmentManager();

        fragment = HomeFragment.newInstance();
        fragmentManager.beginTransaction().replace(R.id.container, fragment).commit();

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.navigation_home) {
                    fragment = HomeFragment.newInstance();
                } else if (item.getItemId() == R.id.navigation_kontak) {
                    fragment = KontakFragment.newInstance();
                } else if (item.getItemId() == R.id.navigation_event) {
                    fragment = EventFragment.newInstance();
                } else if (item.getItemId() == R.id.navigation_laporan) {
                    fragment = LaporanFragment.newInstance();
                } else if (item.getItemId() == R.id.navigation_profil) {
                    fragment = ProfilFragment.newInstance();
                }
                fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                fragmentManager.beginTransaction().replace(R.id.container, fragment).commit();
                return true;
            }
        });
    }
}
