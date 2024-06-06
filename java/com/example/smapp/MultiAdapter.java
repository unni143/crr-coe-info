package com.example.smapp;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MultiAdapter extends RecyclerView.Adapter<MultiAdapter.MultiViewHolder> {

    private Context context;
    private ArrayList<Employee> employees;

    public MultiAdapter(Context context, ArrayList<Employee> employees) {
        this.context = context;
        this.employees = employees;
    }

    public void setEmployees(ArrayList<Employee> employees) {
        this.employees = employees;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MultiViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_employee, viewGroup, false);
        return new MultiViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MultiViewHolder multiViewHolder, int position) {
        //Employee e = employees.get(position);
        multiViewHolder.bind(employees.get(position));
       /* multiViewHolder.view.setBackgroundColor(e.isChecked() ? Color.RED : Color.WHITE);
        multiViewHolder.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                e.setChecked(!e.isChecked());
                multiViewHolder.view.setBackgroundColor(e.isChecked() ? Color.RED : Color.WHITE);
            }
        });*/
    }

    @Override
    public int getItemCount() {
        return employees.size();
    }

    class MultiViewHolder extends RecyclerView.ViewHolder {

        private TextView textView;
        private ImageView imageView;

        MultiViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.textView);
            imageView = itemView.findViewById(R.id.imageView);
        }

        void bind(final Employee employee) {
            imageView.setVisibility(employee.isChecked() ? View.VISIBLE : View.GONE);
            textView.setText(employee.getName().substring(employee.getName().length() - 3));

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