package com.example.smapp;

import android.app.Application;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;

public class Capabilities extends Application
{
    private final String TAG = "CA/Capabilities";

    @Override
    public void onCreate()
    {
        super.onCreate();

        FirebaseDatabase.getInstance().setPersistenceEnabled(true);

        // If user disconnect

        if(FirebaseAuth.getInstance().getCurrentUser() != null)
        {
            final DatabaseReference userDatabase = FirebaseDatabase.getInstance().getReference().child(FirebaseAuth.getInstance().getCurrentUser().getUid());
            userDatabase.addValueEventListener(new ValueEventListener()
            {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot)
                {
                    if(dataSnapshot != null)
                    {
                        userDatabase.child("details").onDisconnect().setValue(ServerValue.TIMESTAMP);
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError)
                {
                    Log.d(TAG, "usersDatabase failed: " + databaseError.getMessage());
                }
            });
        }
    }
}