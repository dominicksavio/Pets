package com.example.myapplication;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import static androidx.constraintlayout.motion.widget.MotionScene.TAG;

public class FragmentPets extends Fragment {

    private RecyclerView myRecyclerView;
    private List<Pet> listPet = new ArrayList<>();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.pets_fragment, container, false);
        myRecyclerView = view.findViewById(R.id.pets_recycler_view);
        refresh();
        return view;
    }


    private void refresh() {
        if (listPet.size() > 0) {
            RecyclerViewAdapter recyclerViewAdapter = new RecyclerViewAdapter(getContext(), listPet);
            myRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            myRecyclerView.setAdapter(recyclerViewAdapter);




            Log.d(TAG, "refresh: ");
        }
    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        assert getArguments() != null;
        String title = getArguments().getString("my_key");
        Log.d(TAG, "\nhi onCreate: "+ title +"\n");

        assert title != null;
        Query query = FirebaseDatabase.getInstance()
                .getReference()
                .child(title)
                .limitToLast(50);


        ChildEventListener childEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String previousChildName) {
                Log.d(TAG, "\nStart onChildAdded:" + dataSnapshot.getKey());

                // A new comment has been added, add it to the displayed list
                Pet pet = dataSnapshot.getValue(Pet.class);

                listPet.add(pet);
                // ...
                refresh();

                AdRequest adRequest = new AdRequest.Builder()
                        .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                        .build();
                AdView mAdView = (AdView) Objects.requireNonNull(Objects.requireNonNull(getView()).findViewById(R.id.adView));
                mAdView.loadAd(adRequest);

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                Log.d(TAG, "onChildChanged: ");
                Pet pet = dataSnapshot.getValue(Pet.class);
                listPet.add(pet);
                AdRequest adRequest = new AdRequest.Builder()
                        .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                        .build();
                AdView mAdView = (AdView) Objects.requireNonNull(Objects.requireNonNull(getView()).findViewById(R.id.adView));
                mAdView.loadAd(adRequest);
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
                Log.d(TAG, "onChildMoved: ");
                Pet pet = dataSnapshot.getValue(Pet.class);
                listPet.add(pet);
                AdRequest adRequest = new AdRequest.Builder()
                        .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                        .build();
                AdView mAdView = (AdView) Objects.requireNonNull(Objects.requireNonNull(getView()).findViewById(R.id.adView));
                mAdView.loadAd(adRequest);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        };
        query.addChildEventListener(childEventListener);
    }
}