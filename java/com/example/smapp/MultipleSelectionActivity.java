package com.example.smapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.OnMultiWindowModeChangedProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class MultipleSelectionActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ArrayList<Employee> employees = new ArrayList<>();
    private MultiAdapter adapter;
    private Button btnGetSelected;
    private DatabaseReference mUsersDatabase;
    private DatabaseReference mSaveData;
    private DatabaseReference attInfo;
    private DatabaseReference sData;
    private DatabaseReference mCount;
    private DatabaseReference att;
    private String s;

    private String date;

    private AttClass attClass;

    private StudentDB db;

    //using firestore
    private FirebaseFirestore fdb;

    private String currentDate;

    private DatabaseReference secondary;

    private String sub;
    private String subType;

    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multiple_selection);
        this.btnGetSelected = findViewById(R.id.btnGetSelected);
        this.recyclerView = findViewById(R.id.recyclerView);

        GridLayoutManager layoutManager = new GridLayoutManager(MultipleSelectionActivity.this, 3);

        db = new StudentDB(MultipleSelectionActivity.this);

        // firestore
        fdb = FirebaseFirestore.getInstance();

        currentDate = DateFormat.getDateTimeInstance().format(new Date());

        secondary = FirebaseDatabase.getInstance("https://smapp-767ea-default-rtdb.firebaseio.com/")
                .getReference();


        recyclerView.setLayoutManager(layoutManager);
       /* recyclerView.addItemDecoration(new DividerItemDecoration(this, GridLayoutManager.VERTICAL));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, GridLayoutManager.HORIZONTAL));*/
        adapter = new MultiAdapter(MultipleSelectionActivity.this, employees);
        recyclerView.setAdapter(adapter);

        // getting data from
        s = getIntent().getStringExtra("class");
        String x = getIntent().getStringExtra("user");
        sub = getIntent().getStringExtra("sub");
        subType = getIntent().getStringExtra("type");



        mUsersDatabase = FirebaseDatabase.getInstance().getReference().child(s);
        mSaveData = FirebaseDatabase.getInstance().getReference().child(x).child("absent");
        attInfo = FirebaseDatabase.getInstance().getReference().child("attinfo");
        mCount = FirebaseDatabase.getInstance().getReference().child("phone");
        sData = FirebaseDatabase.getInstance().getReference().child("student");
        att = FirebaseDatabase.getInstance().getReference().child("att");
        mUsersDatabase.keepSynced(true);
        createList();

        String[] hr = {"1HR ", "2HR", "3HR", "4HR", "5HR", "6HR", "7HR", "8HR"};
        final ArrayAdapter<String> adp = new ArrayAdapter<String>(MultipleSelectionActivity.this,
                android.R.layout.simple_spinner_item, hr);
        final Spinner sp = new Spinner(MultipleSelectionActivity.this);
        sp.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        sp.setAdapter(adp);

        // TODO
        dbHelper = new DBHelper(this);

        btnGetSelected.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alert = new AlertDialog.Builder(MultipleSelectionActivity.this);
                //AlertDialog.Builder alert1 = new AlertDialog.Builder(MultipleSelectionActivity.this);
                alert.setTitle("Select Hour and Submit");

                alert.setView(sp);

                alert.setPositiveButton("Submit", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {

                        if (adapter.getSelected().size() > 0) {
                            StringBuilder stringBuilder = new StringBuilder();
                            for (int i = 0; i < adapter.getSelected().size(); i++) {
                                stringBuilder.append(adapter.getSelected().get(i).getName());
                                stringBuilder.append(",");
                            }
                            showToast(stringBuilder.toString().trim());
                            saveData(stringBuilder.toString().trim(), sub, subType, adapter.getSelected().size());
                            saveStudentData(stringBuilder.toString().trim(), sub, adapter.getSelected().size());
                            demoData(stringBuilder.toString().trim(), sub, adapter.getSelected().size());
                            countData(stringBuilder.toString().trim(), sub, adapter.getSelected().size());
                            toSqlite(stringBuilder.toString().trim(), date, sub, s, subType, adapter.getSelected().size());
                            //getCount();
                            finish();
                        } else {
                            showToast("No Selection");
                        }
                    }
                });

                alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        // what ever you want to do with No option.
                        finish();
                    }
                });

                alert.show();
            }
        });
    }

    private void toSqlite(String trim, String date, String s, String sub, String subType, int size) {

        for (int i = 0; i < size; i++) {
            if (trim.contains(",")) {
                String[] output = trim.split(",");
                boolean b = dbHelper.consolidate(output[i], date, s, sub, subType, "A");
                if (b) {
                    //Toast.makeText(RegistrationActivity.this,"Data inserted",Toast.LENGTH_SHORT).show();
                } else {
                    //Toast.makeText(RegistrationActivity.this,"Failed To insert Data",Toast.LENGTH_SHORT).show();
                }
            }
        }

    }

    private void countData(String trim, String value, int size) {
       /* HashMap<String, String> abb = new HashMap<String, String>();
        abb.put("roll", trim);
        abb.put("absent", trim);
        abb.put("sec", s);
        abb.put("sub", subject);
        abb.put("total", String.valueOf(size));*/
        att.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void demoData(String students, String value, int size) {
        sData.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                SaveStudentData ssd = new SaveStudentData();
                if (students.contains(",")) {
                    String[] output = students.split(",");
                    for (int i = 0; i < size; i++) {
                        for (DataSnapshot child : snapshot.getChildren()) {

                        }
                    }
                }
                for (DataSnapshot child : snapshot.getChildren()) {

                    if (students.contains(",")) {
                        String[] output = students.split(",");
                        for (int i = 0; i < size; i++) {
                            if (output[i].equals(child.child("roll").getValue())) {
                                String t = child.child("total").getValue().toString();
                            }
                        }
                    }
                    //System.out.println("test");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void saveData(String trim, String subject, String type, int size) {
       /* HashMap<String, String> abb = new HashMap<String, String>();
        abb.put("date", currentDate);
        abb.put("absent", trim);
        abb.put("sec", s);
        abb.put("sub", subject);
        abb.put("total", String.valueOf(size));*/

        AttClass attClass = new AttClass(trim, currentDate, s, subject, type, String.valueOf(size));

        mSaveData.push().setValue(attClass).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
               /* Intent mainIntent = new Intent(MultipleSelectionActivity.this, UserActivity.class);
                mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(mainIntent);
                finish();*/
            }
        });
    }

    private void saveStudentData(String students, String subject, int size) {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH) + 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);

        date = day + "-" + month + "-" + year;

        int ct = 0;
        HashMap<String, String> abb = new HashMap<String, String>();


        for (int i = 0; i < size; i++) {
            if (students.contains(",")) {
                String[] output = students.split(",");

                SaveStudentData ssd = new SaveStudentData(subject, output[i], String.valueOf(ct), date);

                abb.put(output[i], "A");
                //  System.out.println("sData" + sData.getKey());
                sData.push().setValue(ssd);

                db.addData(new ToDb(output[i], s, subject, date, "A", "1"));

                att.child(date).push().setValue(output[i]);

                attInfo.child(date).child(output[i]).push().setValue(currentDate);

                CollectionReference dbatt = fdb.collection("detailed");

                ToDb dd = new ToDb(output[i], s, subject, date, "A", "1");

                /*dbatt.add(dd).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(MultipleSelectionActivity.this, "Your Course has been added to Firebase Firestore", Toast.LENGTH_SHORT).show();

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(MultipleSelectionActivity.this, "Your Course has not been added to Firebase Firestore", Toast.LENGTH_SHORT).show();

                    }
                });*/

                Query query = attInfo.child(date).child("20B81A0501");


                query.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot d : snapshot.getChildren()) {
                            String tt = d.getKey();
                            Log.d("count", tt);
                        }
                        Log.d("count", String.valueOf(snapshot.getChildrenCount()));
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


                att.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot d : snapshot.getChildren())
                            Log.d("keys", d.getKey());
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        }

        /*fdb.collection("detailed").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                       // if(document.getData().get("roll").equals())
                        Log.d("test", document.getId() + " => " + document.getData().get("roll"));
                    }
                } else {
                    Log.d("test", "Error getting documents: ", task.getException());
                }

            }
        });*/

        sData.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void createList() {
        employees = new ArrayList<>();
        mUsersDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                employees.clear();
                for (DataSnapshot child : snapshot.getChildren()) {
                    Employee e = new Employee();
                    try {
                        e.setName(child.getValue(String.class));
                        employees.add(e);
                    } catch (Exception exception) {
                        Log.e("error", String.valueOf(exception));
                    }
                }
                adapter = new MultiAdapter(MultipleSelectionActivity.this, employees);
                recyclerView.setAdapter(adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        adapter.setEmployees(employees);
    }

    private void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}