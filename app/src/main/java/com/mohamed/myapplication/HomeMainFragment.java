package com.mohamed.myapplication;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

public class HomeMainFragment extends Fragment {
    private BottomNavigationView bottomNavigationView;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_main_home, container, false);
        bottomNavigationView = view.findViewById(R.id.bottom_nav_main);
        replaceFragment(new HomeFragment(), "Home");
        BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.action_home)
                    replaceFragment(new HomeFragment(), "Home");
                else if (item.getItemId() == R.id.action_mytasks)
                    replaceFragment(new MyTaskFragment(), "MyTasks");
                else if (item.getItemId() == R.id.action_chat)
                    replaceFragment(new ChatFragment(), "Chat");
                else
                    replaceFragment(new AlertFragment(), "Alert");
                return false;
            }
        });
        return view;
    }

    private void replaceFragment(Fragment fragment, String title) {
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container1, fragment);
        transaction.commit();
//        if (!title.equals("Home"))
//            ((AppCompatActivity)getActivity()).getActionBar().setTitle(title);
    }
}
