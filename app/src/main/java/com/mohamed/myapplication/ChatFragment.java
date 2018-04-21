package com.mohamed.myapplication;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class ChatFragment extends Fragment {
    private ImageView imageView_nochat;
    private FirebaseFirestore mFirebaseFirestore;
    private RecyclerView mRecyclerView_chat;
    private ArrayList<MessageReqModel> messageReqModels = new ArrayList<>();
    private MessageReqAdapter mAdapter;

    public ChatFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_chat, container, false);
        imageView_nochat = rootView.findViewById(R.id.iv_no_chat);
        mRecyclerView_chat = rootView.findViewById(R.id.recycler_chat);
        mRecyclerView_chat.setLayoutManager(new GridLayoutManager(getContext(), 1));
        mRecyclerView_chat.setHasFixedSize(true);
        mFirebaseFirestore = FirebaseFirestore.getInstance();
        if (FirebaseAuth.getInstance().getCurrentUser() != null)
            getRequests();

        return rootView;
    }

    private void getRequests() {
        messageReqModels.clear();
        mFirebaseFirestore.collection("MessageRequest").document(FirebaseAuth.getInstance().getCurrentUser().getUid()).addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(DocumentSnapshot documentSnapshot, FirebaseFirestoreException e) {
                if (documentSnapshot.exists()) {
                    MessageReqModel model = documentSnapshot.toObject(MessageReqModel.class);
                    messageReqModels.add(model);

                    mAdapter.notifyDataSetChanged();
                    if (messageReqModels.size() > 0)
                        imageView_nochat.setVisibility(View.GONE);
                }
            }
        });
        mAdapter = new MessageReqAdapter(getActivity(), messageReqModels);
        mRecyclerView_chat.setAdapter(mAdapter);
    }
}

