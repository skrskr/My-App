package com.mohamed.myapplication;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment {

    private FirebaseFirestore mFirebaseFirestore;
    private RecyclerView servicesRecycelerView;
    private ServiceAdapter serviceAdapter;
    private List<ServiceModel> serviceModelList;


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
        servicesRecycelerView.setHasFixedSize(true);
        servicesRecycelerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        mFirebaseFirestore.collection("Services").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    for(DocumentSnapshot doc:task.getResult()){
                        ServiceModel model = doc.toObject(ServiceModel.class);
                        serviceModelList.add(model);
                    }
                    serviceAdapter = new ServiceAdapter(getActivity(),serviceModelList);
                    servicesRecycelerView.setAdapter(serviceAdapter);
                }
            }
        });

        return view;
    }


}
