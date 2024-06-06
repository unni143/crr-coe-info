package com.example.smapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

public class RegistrationActivity extends AppCompatActivity {

    private TextInputEditText mDisplayName;
    private TextInputEditText mEmail;
    private TextInputEditText mPassword;
    private Button mCreateBtn;
    private TextInputLayout mSurname;

    //Unni
    private TextInputLayout mAge;


    private Toolbar mToolbar;

    private DatabaseReference mDatabase;
    // TODO: 09-12-2022
    private DatabaseReference mUserData;
    private DatabaseReference secondary;

    //ProgressDialog
    private ProgressDialog mRegProgress;

    //Firebase Auth
    private FirebaseAuth mAuth;

    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        //Toolbar Set
        mToolbar = findViewById(R.id.register_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Sign Up");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        mRegProgress = new ProgressDialog(this);


        // Firebase Auth

        mAuth = FirebaseAuth.getInstance();


        // Android Fields
        mDisplayName = findViewById(R.id.id);
        mEmail = findViewById(R.id.email);
        mPassword = findViewById(R.id.passwd);
        mCreateBtn = findViewById(R.id.btnregister);

        dbHelper = new DBHelper(this);


        mCreateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String display_name = mDisplayName.getText().toString();
                String email = mEmail.getText().toString();
                String password = mPassword.getText().toString();
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                String date = day + "-" + month+1 + "-" + year;

                if (!TextUtils.isEmpty(display_name) || !TextUtils.isEmpty(email) ||
                        !TextUtils.isEmpty(password)) {
                    mRegProgress.setTitle("Registering User");
                    mRegProgress.setMessage("Please wait while we create your account !");
                    mRegProgress.setCanceledOnTouchOutside(false);
                    mRegProgress.show();

                    boolean b = dbHelper.insetUserData(display_name,email,password,date);
                    if (b){
                        Toast.makeText(RegistrationActivity.this,"Data inserted",Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(RegistrationActivity.this,"Failed To insert Data",Toast.LENGTH_SHORT).show();
                    }
                    register_user(display_name, email, password);

                } else {
                    Toast.makeText(getApplicationContext(), "All Fields Required",
                            Toast.LENGTH_LONG).show();
                }

            }
        });


    }

    private void register_user(final String display_name, String email, String password) {

        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()) {


                    FirebaseUser current_user = FirebaseAuth.getInstance().getCurrentUser();
                    String uid = current_user.getUid();

                    mDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(uid);

                    secondary = FirebaseDatabase.getInstance("https://smapp-767ea-default-rtdb.firebaseio.com/")
                            .getReference().child("Users").child(uid);
                    // TODO: 09-12-2022  
                    mUserData = FirebaseDatabase.getInstance().getReference().child("ids");
                    String currentDate = DateFormat.getDateTimeInstance().format(new Date());
                    // TODO: 09-12-2022
                   /* HashMap<String, String> userMap = new HashMap<>();
                    userMap.put("name", display_name);
                    userMap.put("date", currentDate);
                    userMap.put("image", "default");
                    userMap.put("thumb_image", "default");*/
                    //mUserData.push().setValue(display_name);
                    Post post = new Post(currentDate, display_name, email);

                    secondary.setValue(post);

                    mDatabase.setValue(post).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            if (task.isSuccessful()) {
                                mRegProgress.dismiss();
                                Intent mainIntent = new Intent(RegistrationActivity.this, LoginActivity.class);
                                mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(mainIntent);
                                finish();

                            }

                        }
                    });


                } else {

                    mRegProgress.hide();
                    Toast.makeText(RegistrationActivity.this, "Cannot Sign in. Please check the form and try again. contact admin", Toast.LENGTH_LONG).show();

                }

            }
        });

    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder =
                new AlertDialog.Builder(RegistrationActivity.this, R.style.AlertDialogCustom2);
        builder.setTitle("BACK");
        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                Intent intent = new Intent(RegistrationActivity.this, ChooseActivity.class);
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
}