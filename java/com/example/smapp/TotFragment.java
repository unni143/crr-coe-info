package com.example.smapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TotFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TotFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private DatabaseReference mUsersDatabase;
    private RecyclerView mClasses;
    private View mMainView;
    private Map<String, Object> map;
    private List<Total> user;


    public TotFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static TotFragment newInstance(String param1, String param2) {
        TotFragment fragment = new TotFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mMainView = inflater.inflate(R.layout.fragment_tot, container, false);
        user = new ArrayList<>();
        // Inflate the layout for this fragment
        mClasses = mMainView.findViewById(R.id.total);
        mUsersDatabase = FirebaseDatabase.getInstance().getReference();
        mUsersDatabase.keepSynced(true);

        getData();
        value();
        return mMainView;
    }

    private void getData() {

        mUsersDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                long i = snapshot.child("count").getChildrenCount();
                System.out.println(i);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        mUsersDatabase.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                user.clear();

                long i = snapshot.child("student").child("roll").getChildrenCount();
                for (DataSnapshot child : snapshot.child("student").getChildren()) {

                }
              /*  userAdapter = new AttAdapter(user, getContext(), x);
                mClasses.setAdapter(userAdapter);*/
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void setValue(Object roll, int i) {
        map = new HashMap<>();
        String a = roll.toString();
        map.put(String.valueOf(i), roll);

    }

    private void value(){
        Total t = new Total();
        System.out.println(t.getRoll());
    }


}