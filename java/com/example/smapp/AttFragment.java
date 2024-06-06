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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AttFragment extends Fragment {

    private RecyclerView mClasses;
    private DatabaseReference mUsersDatabase;
    private DatabaseReference mFriendsDatabase;
    private DatabaseReference mClassDatabase;

    private FirebaseAuth mAuth;

    private String mCurrent_user_id;

    private View mMainView;
    private AttAdapter userAdapter;
    private List<AttClass> user;

    private TextView tv;
    public String x;

    private FirebaseFirestore db;

    public AttFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mMainView = inflater.inflate(R.layout.fragment_att, container, false);
        mClasses = mMainView.findViewById(R.id.attendance);
        mClasses.setLayoutManager(new LinearLayoutManager(getContext()));
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mClasses.getContext(),
                LinearLayoutManager.HORIZONTAL);
        mClasses.addItemDecoration(dividerItemDecoration);
        tv = mMainView.findViewById(R.id.user);
        mAuth = FirebaseAuth.getInstance();

        db = FirebaseFirestore.getInstance();

        // list
        user = new ArrayList<>();

        mCurrent_user_id = mAuth.getCurrentUser().getUid();

        mFriendsDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(mCurrent_user_id);
        mFriendsDatabase.keepSynced(true);
        mUsersDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(mCurrent_user_id).child("name");
        mUsersDatabase.keepSynced(true);
        mClassDatabase = FirebaseDatabase.getInstance().getReference();


        //System.out.println(mUsersDatabase);


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
                        for(DataSnapshot child:snapshot.child(x).child("absent").getChildren()){
                           // user.add(new AttClass());
                            AttClass e = child.getValue(AttClass.class);
                            //attClass.setDate(child.getValue(String.class));
                            //AttClass e = new AttClass();
                            String date = child.child("date").getValue(String.class);
                            String sub = child.child("sub").getValue(String.class);
                            String sec = child.child("sec").getValue(String.class);
                            String abb = child.child("absent").getValue(String.class);
                            String size = child.child("total").getValue(String.class);
                            String type = child.child("type").getValue(String.class);
                            e.setDate(date);
                            e.setSec(sec);
                            e.setSub(sub);
                            e.setAbsent(abb);
                            e.setTotal(size);
                            e.setType(type);
                            user.add(e);

                        }
                        userAdapter = new AttAdapter(user, getContext(), x);
                        mClasses.setAdapter(userAdapter);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }

                });


                userAdapter = new AttAdapter(user, getContext(), x);
                mClasses.setAdapter(userAdapter);
                //mLoginProgress.hide();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}