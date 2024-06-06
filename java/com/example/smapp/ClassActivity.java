package com.example.smapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ClassActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ArrayList<Employee> employees = new ArrayList<>();
    private ClassAdapter adapter;
    private Button btnGetSelected;


    private DatabaseReference mUsersDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multiple_selection);
        this.btnGetSelected = findViewById(R.id.btnGetSelected);
        this.recyclerView = findViewById(R.id.recyclerView);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        adapter = new ClassAdapter(ClassActivity.this, employees);
        recyclerView.setAdapter(adapter);
        String s = getIntent().getStringExtra("class");
        mUsersDatabase = FirebaseDatabase.getInstance().getReference().child(s);
        mUsersDatabase.keepSynced(true);
        createList();

        btnGetSelected.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (adapter.getSelected().size() > 0) {
                    StringBuilder stringBuilder = new StringBuilder();
                    for (int i = 0; i < adapter.getSelected().size(); i++) {
                        stringBuilder.append(adapter.getSelected().get(i).getName());
                        stringBuilder.append("\n");
                    }
                    showToast(stringBuilder.toString().trim());
                } else {
                    showToast("No Selection");
                }
            }
        });
    }

    private void createList() {
        employees = new ArrayList<>();
        mUsersDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                employees.clear();
                for(DataSnapshot child: snapshot.getChildren()){
                    Employee e = new Employee();
                    e.setName(child.getValue(String.class));
                    employees.add(e);
                }
                adapter = new ClassAdapter(ClassActivity.this, employees);
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