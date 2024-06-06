package com.example.smapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class UserActivity extends AppCompatActivity {

    private TextView user;
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    private String mCurrentUserId;
    private DatabaseReference mRootRef;
    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        Intent intent = getIntent();
        Bundle extras = getIntent().getExtras();


        mToolbar = findViewById(R.id.register_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Hi");

        DatabaseReference referenceSub = FirebaseDatabase.getInstance().
                getReference().child("subject");

        mAuth = FirebaseAuth.getInstance();
        mCurrentUserId = mAuth.getCurrentUser().getUid();

        mDatabase = FirebaseDatabase.getInstance().getReference("employee").child("id");
        System.out.println(mDatabase);

        mRootRef = FirebaseDatabase.getInstance().getReference().child("employee").child(mCurrentUserId);
        //mRootRef.child("employee").child(mCurrentUserId);

        System.out.println(mRootRef);


        TabLayout tabLayout = findViewById(R.id.tab_layout);
        ViewPager viewPager = findViewById(R.id.view_pager);

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());

        viewPagerAdapter.add(new UserFragment(), "CLASSES");
        viewPagerAdapter.add(new AttFragment(), "ATTENDANCE");
        viewPagerAdapter.add(new TotFragment(), "BY DATE");

        viewPager.setAdapter(viewPagerAdapter);
       // viewPager.setPageTransformer(true, new ZoomOutPageTransformer());
        tabLayout.setupWithViewPager(viewPager);

        mRootRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String x = String.valueOf(snapshot.child("id").getValue());
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();

        // we will get a DatabaseReference for the database root node
        DatabaseReference databaseReference = firebaseDatabase.getReference();
        DatabaseReference getEmp = databaseReference.child("employee").child(mCurrentUserId).child("id");

        getEmp.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User x = snapshot.getValue(User.class);
               // System.out.println(x.getId());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        /*DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("employee");
        User getUserName = new User();
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        databaseReference.child(uid).setValue(getUserName.getId());*/

    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder =
                new AlertDialog.Builder(UserActivity.this, R.style.AlertDialogCustom);
        builder.setTitle("BACK");
        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                Intent intent = new Intent(UserActivity.this, ChooseActivity.class);
                startActivity(intent);
                finish();
            }
        });
        builder.setNegativeButton("NO", (dialog, id) -> dialog.dismiss());
        AlertDialog dialog = builder.create();
        dialog.show();
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.copyFrom(dialog.getWindow().getAttributes());
        layoutParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        dialog.getWindow().setAttributes(layoutParams);
    }

    static class ViewPagerAdapter extends FragmentPagerAdapter {

        private final List<Fragment> fragments = new ArrayList<>();
        private final List<String> fragmentTitle = new ArrayList<>();

        public ViewPagerAdapter(@NonNull FragmentManager fm) {
            super(fm);
        }

        public void add(Fragment fragment, String title) {
            fragments.add(fragment);
            fragmentTitle.add(title);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return fragmentTitle.get(position);
        }

    }
}