package com.mohamed.myapplication;

import android.content.res.Configuration;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private Toolbar mToolbar;
    private DrawerLayout mDrawerLayout;
    private NavigationView mNavigationView;
    private ActionBarDrawerToggle mDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        mDrawerLayout = findViewById(R.id.main_drawer_layout);

        mNavigationView = findViewById(R.id.main_nav_view);
        mDrawerToggle = new ActionBarDrawerToggle(
                this,
                mDrawerLayout,
                R.string.drawer_open,
                R.string.drawer_close);
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();
        mNavigationView.setNavigationItemSelectedListener(this);

    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        switch (itemId){
            case R.id.action_home:
                Toast.makeText(this, "TILTLE: " + item.getTitle(), Toast.LENGTH_SHORT).show();
                return  true;
            case R.id.action_favorites:
                Toast.makeText(this, "TILTLE: " + item.getTitle(), Toast.LENGTH_SHORT).show();
                return true;
            case R.id.action_me:
                Toast.makeText(this, "TILTLE: " + item.getTitle(), Toast.LENGTH_SHORT).show();
                return true;
            case R.id.action_payment_history:
                Toast.makeText(this, "TILTLE: " + item.getTitle(), Toast.LENGTH_SHORT).show();
                return true;
            case R.id.action_login:
                Toast.makeText(this, "TILTLE: " + item.getTitle(), Toast.LENGTH_SHORT).show();
                return true;

        }
        return false;
    }
}
