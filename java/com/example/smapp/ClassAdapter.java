package com.example.smapp;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ClassAdapter extends RecyclerView.Adapter<ClassAdapter.ViewHolder>{

    private Context context;
    private ArrayList<Employee> employees;

    public ClassAdapter(Context context, ArrayList<Employee> employees) {
        this.context = context;
        this.employees = employees;
    }

    public void setEmployees(ArrayList<Employee> employees) {
        this.employees = employees;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ClassAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.class_item, viewGroup, false);
        return new ClassAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ClassAdapter.ViewHolder multiViewHolder, int position) {
        multiViewHolder.bind(employees.get(position));
    }

    @Override
    public int getItemCount() {
        return employees.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView textView;
        private ImageView imageView;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.textView);
            imageView = itemView.findViewById(R.id.imageView);
        }

        void bind(final Employee employee) {
            imageView.setVisibility(employee.isChecked() ? View.VISIBLE : View.GONE);
            textView.setText(employee.getName());

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    employee.setChecked(!employee.isChecked());
                    imageView.setVisibility(employee.isChecked() ? View.VISIBLE : View.GONE);
                }
            });
        }
    }

    public ArrayList<Employee> getAll() {
        return employees;
    }

    public ArrayList<Employee> getSelected() {
        ArrayList<Employee> selected = new ArrayList<>();
        for (int i = 0; i < employees.size(); i++) {
            if (employees.get(i).isChecked()) {
                selected.add(employees.get(i));
            }
        }
        return selected;
    }
}