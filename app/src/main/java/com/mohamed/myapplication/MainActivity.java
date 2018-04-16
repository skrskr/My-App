package com.mohamed.myapplication;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.wifi.WifiConfiguration;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;

import java.io.IOException;
import java.util.List;

import fr.quentinklein.slt.LocationTracker;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private Toolbar mToolbar;
    private DrawerLayout mDrawerLayout;
    private NavigationView mNavigationView;
    private ActionBarDrawerToggle mDrawerToggle;
    private HomeMainFragment homeMainFragment;
    private ChatFragment mChatFragment;
    private MyTaskFragment mMyTaskFragment;
    private AlertFragment mAlertFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        homeMainFragment = new HomeMainFragment();
        mMyTaskFragment = new MyTaskFragment();
        mAlertFragment = new AlertFragment();
        mChatFragment = new ChatFragment();
        replaceFragment(homeMainFragment, "Home");
        mToolbar.setTitleTextColor(Color.WHITE);
        mToolbar.setTitle("");
        final PlaceAutocompleteFragment autocompleteFragment = (PlaceAutocompleteFragment)
                getFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);

        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                mToolbar.setTitle(place.getName());
            }

            @Override
            public void onError(Status status) {

            }


        });
        mDrawerLayout = findViewById(R.id.main_drawer_layout);
        mNavigationView = findViewById(R.id.main_nav_view);
        mDrawerToggle = new ActionBarDrawerToggle(
                this,
                mDrawerLayout,
                mToolbar,
                R.string.drawer_open,
                R.string.drawer_close);
        mDrawerLayout.addDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();
        mNavigationView.setNavigationItemSelectedListener(this);
        checkAccessLocation();


    }

    private void checkAccessLocation() {
        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

        } else {
            LocationTracker tracker = new LocationTracker(getApplicationContext()) {
                @Override
                public void onLocationFound(Location location) {
                    Geocoder geocoder = new Geocoder(getApplicationContext(), getResources().getConfiguration().locale);
                    List<Address> addressList = null;
                    try {
                        addressList = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    if (addressList != null && addressList.size() > 0)
                        mToolbar.setTitle(addressList.get(0).getSubAdminArea());
                }

                @Override
                public void onTimeout() {
                    mToolbar.setTitle("connection problems");
                }
            };
            tracker.startListening();
        }
    }


    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    private void replaceFragment(Fragment fragment, String title) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.main_frame, fragment);
        transaction.commit();
        if (!title.equals("Home"))
            mToolbar.setTitle(title);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        switch (itemId) {
            case R.id.action_home:
                replaceFragment(homeMainFragment, "Home");
                //mBottomNavView.setSelectedItemId(R.id.action_home);
                Toast.makeText(this, "TILTLE: " + item.getTitle(), Toast.LENGTH_SHORT).show();
                break;
            case R.id.action_favorites:
                replaceFragment(new FavouriteFragment(), item.getTitle().toString());
                break;
            case R.id.action_me:
                Toast.makeText(this, "TILTLE: " + item.getTitle(), Toast.LENGTH_SHORT).show();
                break;
            case R.id.action_payment_history:
                replaceFragment(new PaymentHistoryFragment(), item.getTitle().toString());
                break;
            case R.id.action_login:
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                break;

        }
        mDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}
