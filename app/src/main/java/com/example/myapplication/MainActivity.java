package com.example.myapplication;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import com.google.android.gms.ads.MobileAds;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.core.view.GravityCompat;
import androidx.appcompat.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @SuppressLint("WrongConstant")
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
            toggle.syncState();
            navigationView.setNavigationItemSelectedListener(this);

        if(CheckNetwork.isInternetAvailable(MainActivity.this)) //returns true if internet available
        {
            MobileAds.initialize(this, "ca-app-pub-7699153984954796~8295536303");
            //TabLayout
            TabLayout tabLayout = findViewById(R.id.tabLayout_id);
            ViewPager viewPager = findViewById(R.id.viewpager_id);
            ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
            Bundle bundle1 = new Bundle();
            bundle1.putString("my_key", "stuff");
            FragmentPets myFrag1 = new FragmentPets();
            myFrag1.setArguments(bundle1);
            adapter.AddFragment(myFrag1, "stuff");
            Bundle bundle2 = new Bundle();
            bundle2.putString("my_key", "aquarium");
            FragmentPets myFrag2 = new FragmentPets();
            myFrag2.setArguments(bundle2);
            adapter.AddFragment(myFrag2, "aquarium");
            Bundle bundle3 = new Bundle();
            bundle3.putString("my_key", "pet");
            FragmentPets myFrag3 = new FragmentPets();
            myFrag3.setArguments(bundle3);
            adapter.AddFragment(myFrag3, "pet");
            viewPager.setAdapter(adapter);
            tabLayout.setupWithViewPager(viewPager);
            tabLayout.getTabAt(0);//.setIcon(R.drawable.ic_pets_black_24dp);
            tabLayout.getTabAt(1);//.setIcon(R.mipmap.ic_launcher);
            tabLayout.getTabAt(2);//.setIcon(R.drawable.ic_pets_black_24dp);
        }
        else{
            open(getWindow().getDecorView().findViewById(android.R.id.content));
        }
    }


    public void open(View view){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("No Internet connection try again");
        alertDialogBuilder.setPositiveButton("yes",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {
                                transfer();
                            }
                        });

        alertDialogBuilder.setNegativeButton("No",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    private void transfer() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    void signIn(){
        Intent intent = new Intent(this, Developer.class);
        startActivity(intent);
    }

    @SuppressLint("IntentReset")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.nav_home) {
            TabLayout tabLayout = findViewById(R.id.tabLayout_id); // get the reference of TabLayout
            TabLayout.Tab tab = tabLayout.getTabAt(0); //  get the tab at 1th in index
            assert tab != null;
            tab.select();
        } else if (id == R.id.nav_gallery) {
            TabLayout tabLayout = findViewById(R.id.tabLayout_id); // get the reference of TabLayout
            TabLayout.Tab tab = tabLayout.getTabAt(1); //  get the tab at 1th in index
            assert tab != null;
            tab.select();
        } else if (id == R.id.nav_slideshow) {
            TabLayout tabLayout = findViewById(R.id.tabLayout_id); // get the reference of TabLayout
            TabLayout.Tab tab = tabLayout.getTabAt(2); //  get the tab at 1th in index
            assert tab != null;
            tab.select();
        } else if (id == R.id.nav_share) {
            String number = getResources().getString(R.string.number);
            String url="https://wa.me/"+number+"/?text=FEEDBACK \n";
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.putExtra(Intent.EXTRA_TEXT, "This is my text to send.");
            i.setType("text/plain");
            i.setData(Uri.parse(url));
            startActivity(i);
        } else if (id == R.id.nav_send) {
            signIn();
        }
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}