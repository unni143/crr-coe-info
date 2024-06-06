package com.example.smapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class TotAdapter extends RecyclerView.Adapter<TotAdapter.ViewHolder> {
    Context context;
    List<AttClass> pre;
    Object present;
    String x;

    public TotAdapter(List<AttClass> pre, Context context, String x) {
        this.context = context;
        this.pre = pre;
        this.x = x;
    }

    @NonNull
    @Override
    public TotAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.att_adapt, parent, false);
        return new TotAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        AttClass attClass = pre.get(position);
        holder.bind(attClass);
    }


    @Override
    public int getItemCount() {
        return pre.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView date;
        private TextView sub;
        private TextView sec;
        private TextView abb;
        private TextView size;

        public ViewHolder(View itemView) {
            super(itemView);
            //attend = itemView.findViewById(R.id.att_);
            date = itemView.findViewById(R.id.date);
            sub = itemView.findViewById(R.id.sub);
            sec = itemView.findViewById(R.id.sec);
            abb = itemView.findViewById(R.id.abb);
            size = itemView.findViewById(R.id.size);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Toast.makeText(context.getApplicationContext(), attend.getText(), Toast.LENGTH_SHORT).show();
                }
            });

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    return false;
                }
            });
        }

        void bind(final AttClass employee) {
            date.setText(employee.getAbsent());
            sub.setText(employee.getDate());
            sec.setText(employee.getSec());
            abb.setText(employee.getSub());
            size.setText(employee.getTotal());

            sec.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    date.setVisibility(date.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
                }
            });
        }
    }
}
