package com.mohamed.myapplication;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


/**
 * A simple {@link Fragment} subclass.
 */
public class ChatFragment extends Fragment {
    private ImageView imageView_nochat;
    private RecyclerView mRecyclerView_chat;
    public ChatFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_chat, container, false);
        imageView_nochat = rootView.findViewById(R.id.iv_no_chat);
        mRecyclerView_chat=rootView.findViewById(R.id.recycler_chat);
        return rootView;
    }

}
