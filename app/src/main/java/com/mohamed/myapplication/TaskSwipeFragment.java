package com.mohamed.myapplication;


import android.media.Image;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;


public class TaskSwipeFragment extends Fragment {


    private static final String ARG_TYPE = "arg_type";

    private FrameLayout frameLayout_no_data;

    public TaskSwipeFragment() {
        // Required empty public constructor
    }

    public static TaskSwipeFragment newInstance(String type) {
        Bundle arguments = new Bundle();
        arguments.putString(ARG_TYPE, type);
        TaskSwipeFragment swipeFragment = new TaskSwipeFragment();
        swipeFragment.setArguments(arguments);
        return swipeFragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_task_swipe, container, false);
        frameLayout_no_data=view.findViewById(R.id.frameLayout_no_data);
        Bundle bundle = getArguments();
        String type = bundle.getString(ARG_TYPE);
//        typeTextView.setText("TYPE: " + type);
        return view;
    }

}
