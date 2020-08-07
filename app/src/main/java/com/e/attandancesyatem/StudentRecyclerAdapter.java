package com.e.attandancesyatem;

import android.content.Context;
import android.icu.text.MessagePattern;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StudentRecyclerAdapter extends RecyclerView.Adapter<StudentRecyclerAdapter.viewHolder> {

    private String date,classes,subject;
    private ArrayList<StudentInfo> data;
    Context context;

    public StudentRecyclerAdapter(ArrayList<StudentInfo> data, Context context,String date,String classes,String subject) {
        this.data = data;
        this.date = date;
        this.classes = classes;
        this.subject = subject;
        this.context = context;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.student_adapter_list_view, parent, false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final viewHolder holder, final int position) {
        if(data.get(position).isIsclicked()){
            holder.absent.setClickable(false);
            holder.present.setClickable(false);
            holder.absent.setAlpha(Float.parseFloat("0.1"));
            holder.present.setAlpha(Float.parseFloat("0.1"));
        }
        else{
            holder.absent.setClickable(true);
            holder.present.setClickable(true);
            holder.absent.setAlpha(Float.parseFloat("1"));
            holder.present.setAlpha(Float.parseFloat("1"));
        }
        holder.firstname.setText(data.get(position).getFirstname());
        holder.lastname.setText(data.get(position).getLastname());

        holder.absent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Call<List<Temp>> user = ApiClient.getapiInterface().startattendance(data.get(position).getFirstname(),data.get(position).getLastname(),date,classes,subject,"Absent");
                user.enqueue(new Callback<List<Temp>>() {
                    @Override
                    public void onResponse(Call<List<Temp>> call, Response<List<Temp>> response) {

                        if(response.body().get(0).getMessage().equals("success")) {
                            holder.absent.setClickable(false);
                            holder.present.setClickable(false);
                            holder.absent.setAlpha(Float.parseFloat("0.1"));
                            holder.present.setAlpha(Float.parseFloat("0.1"));
                            data.get(position).setIsclicked(true);
                            Toast.makeText(context, "Marked: Absent", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
                        }

                    }

                    @Override
                    public void onFailure(Call<List<Temp>> call, Throwable t) {

                        Toast.makeText(context,"Failed" + t.getMessage(),Toast.LENGTH_LONG).show();

                    }
                });
            }
        });
        holder.present.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Call<List<Temp>> user = ApiClient.getapiInterface().startattendance(data.get(position).getFirstname(),data.get(position).getLastname(),date,classes,subject,"Present");
                user.enqueue(new Callback<List<Temp>>() {
                    @Override
                    public void onResponse(Call<List<Temp>> call, Response<List<Temp>> response) {

                        if(response.body().get(0).getMessage().equals("success")) {
                            holder.absent.setClickable(false);
                            holder.present.setClickable(false);
                            holder.absent.setAlpha(Float.parseFloat("0.1"));
                            holder.present.setAlpha(Float.parseFloat("0.1"));
                            data.get(position).setIsclicked(true);
                            Toast.makeText(context, "Marked: Present", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
                        }

                    }

                    @Override
                    public void onFailure(Call<List<Temp>> call, Throwable t) {

                        Toast.makeText(context,"Failed" + t.getMessage(),Toast.LENGTH_LONG).show();

                    }
                });

            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        TextView firstname, lastname;
        Button absent, present;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            firstname = itemView.findViewById(R.id.firstname);
            lastname = itemView.findViewById(R.id.lastname);
            absent = itemView.findViewById(R.id.absent);
            present = itemView.findViewById(R.id.present);
        }
    }
}
