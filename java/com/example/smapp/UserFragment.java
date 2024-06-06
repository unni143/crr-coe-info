package com.example.smapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class UserFragment extends Fragment {

    private RecyclerView mClasses;
    private DatabaseReference mUsersDatabase;
    private DatabaseReference mFriendsDatabase;
    private DatabaseReference mClassDatabase;

    private FirebaseAuth mAuth;

    private String mCurrent_user_id;

    private View mMainView;
    private UserAdapter userAdapter;
    private List<GetSubjects> user;

    private TextView tv;
    public String x;

    public UserFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mMainView = inflater.inflate(R.layout.fragment_user, container, false);
        mClasses = mMainView.findViewById(R.id.classes);

        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 2);
        mClasses.setLayoutManager(layoutManager);

        tv = mMainView.findViewById(R.id.user);
        mAuth = FirebaseAuth.getInstance();

        // list
        user = new ArrayList<>();

        mCurrent_user_id = mAuth.getCurrentUser().getUid();

        mFriendsDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(mCurrent_user_id);
        mFriendsDatabase.keepSynced(true);
        mUsersDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(mCurrent_user_id).child("name");
        mUsersDatabase.keepSynced(true);
        mClassDatabase = FirebaseDatabase.getInstance().getReference();


        mClasses.setHasFixedSize(true);
        mClasses.setLayoutManager(new LinearLayoutManager(getContext()));

        // Inflate the layout for this fragment
        getClasses();
        return mMainView;
    }

    private void getClasses() {
        mUsersDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                x = snapshot.getValue(String.class);
                Log.i("user", String.valueOf(x));
                tv.setText("HI " + x);
                //Checking class data base
                mClassDatabase.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        user.clear();
                        for(DataSnapshot child:snapshot.child(x).child("data").getChildren()){
                            //user.add(String.valueOf(child.getValue()));
                            GetSubjects e = new GetSubjects();
                            try {
                                e.setSec(child.child("sec").getValue(String.class));
                                e.setSub(child.child("sub").getValue(String.class));
                                e.setType(child.child("type").getValue(String.class));
                                user.add(e);
                            }catch (Exception exception){
                                System.out.println(exception);
                            }
                        }
                        userAdapter = new UserAdapter(user, getContext(), x);
                        mClasses.setAdapter(userAdapter);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }

                });
                userAdapter = new UserAdapter(user, getContext(), x);
                mClasses.setAdapter(userAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}