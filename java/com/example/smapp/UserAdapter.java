package com.example.smapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder>{

    Context context;
    List<GetSubjects> pre;
    Object present;
    String x;

    public UserAdapter(List<GetSubjects> pre, Context context, String x) {
        this.context = context;
        this.pre = pre;
        this.x = x;
    }

    @NonNull
    @Override
    public UserAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.att_item, parent, false);
        return new UserAdapter.ViewHolder(view);
    }



    @Override
    public void onBindViewHolder(@NonNull UserAdapter.ViewHolder holder, int position) {

       /* present = pre.get(position);
        holder.attend.setText(String.valueOf(present));*/
        holder.bind(pre.get(position));
    }

    @Override
    public int getItemCount() {
        return pre.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView attend;
        private Context mContext;

        public ViewHolder(View itemView) {
            super(itemView);
            attend = itemView.findViewById(R.id.att_att);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(context.getApplicationContext(), MultipleSelectionActivity.class);
                    Toast.makeText(context.getApplicationContext(), attend.getText(), Toast.LENGTH_SHORT).show();
                    i.putExtra("class", attend.getText().toString().substring(
                            attend.getText().toString().lastIndexOf(",") + 1).trim());
                    i.putExtra("user", x);
                    i.putExtra("sub", attend.getText().toString().split(",")[0]);
                    i.putExtra("type", attend.getText().toString().split(",")[1]);
                    context.startActivity(i);
                }
            });
        }

        void bind(final GetSubjects employee) {
            //attend.setText(employee.getSec());
            //attend.setText(employee.getSub());
            attend.setText(employee.getSub() +","+ employee.getType() + "," + employee.getSec());

           /* itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    employee.setChecked(!employee.isChecked());
                    imageView.setVisibility(employee.isChecked() ? View.VISIBLE : View.GONE);
                }
            });*/
        }
    }
}
