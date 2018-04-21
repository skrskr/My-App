package com.mohamed.myapplication;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import cn.trinea.android.view.autoscrollviewpager.AutoScrollViewPager;
import me.relex.circleindicator.CircleIndicator;


public class HomeFragment extends Fragment implements ServiceAdapter.OnItemClickListener {

    private FirebaseFirestore mFirebaseFirestore;
    private RecyclerView servicesRecycelerView;
    private ServiceAdapter serviceAdapter;
    private List<ServiceModel> serviceModelList;
    private AutoScrollViewPager mViewPager_images;
    private ArrayList<String> image_url = new ArrayList<>();


    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        mFirebaseFirestore = FirebaseFirestore.getInstance();
        serviceModelList = new ArrayList<>();
        servicesRecycelerView = view.findViewById(R.id.recycler_services);
        mViewPager_images = view.findViewById(R.id.viewPager);
        servicesRecycelerView.setHasFixedSize(true);
        servicesRecycelerView.setNestedScrollingEnabled(false);
        servicesRecycelerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        mFirebaseFirestore.collection("Services").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (DocumentSnapshot doc : task.getResult()) {
                        ServiceModel model = doc.toObject(ServiceModel.class);
                        serviceModelList.add(model);
                    }
                    serviceAdapter.notifyDataSetChanged();
                }
            }
        });
        serviceAdapter = new ServiceAdapter(getActivity(), serviceModelList);
        servicesRecycelerView.setAdapter(serviceAdapter);
        serviceAdapter.setOnItemClickListener(this);


        image_url.add("https://www.w3schools.com/w3images/lights.jpg");
        image_url.add("https://www.w3schools.com/w3images/lights.jpg");
        image_url.add("https://www.w3schools.com/w3images/lights.jpg");
        image_url.add("https://www.w3schools.com/w3images/lights.jpg");
        image_url.add("https://www.w3schools.com/w3images/lights.jpg");
        image_url.add("https://www.w3schools.com/w3images/lights.jpg");

        mViewPager_images.setAdapter(new ImagesViewpagerAdapter(image_url, getContext()));
        mViewPager_images.startAutoScroll(2000);
        mViewPager_images.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        mViewPager_images.setBorderAnimation(false);
        mViewPager_images.setCycle(true);
        CircleIndicator indicator = (CircleIndicator) view.findViewById(R.id.indicator);
        indicator.setViewPager(mViewPager_images);


        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .setPersistenceEnabled(true)
                .build();
        mFirebaseFirestore.setFirestoreSettings(settings);
        return view;
    }


    @Override
    public void onFavoriteItemClick(int position) {
        Toast.makeText(getActivity(), "FAV: " + position, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBookButtonClick(int position) {
        Toast.makeText(getActivity(), "Pos: " + position, Toast.LENGTH_SHORT).show();
    }
}
