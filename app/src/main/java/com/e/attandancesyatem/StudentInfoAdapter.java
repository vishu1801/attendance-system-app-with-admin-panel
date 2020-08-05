package com.e.attandancesyatem;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StudentInfoAdapter extends ArrayAdapter {
    Context mContext;
    Activity mActivity;
    private ArrayList<StudentInfo> dataSet;
    private String date,clas,subject;
//    private ArrayList<DatabaseReference> databaseReferenceArrayList;

    private class ViewHolder {
        TextView firstname, lastname;
        Button absent;
        Button present;
    }

    public StudentInfoAdapter(ArrayList<StudentInfo> data,String subject,String clas,String date, Context context, Activity mActivity) {
        super(context, R.layout.student_adapter_list_view, data);
        this.dataSet = data;
        this.date=date;
        this.clas=clas;
        this.subject=subject;
        this.mContext = context;
        this.mActivity = mActivity;
//      this.databaseReferenceArrayList = databaseList;
    }

    @Override
    public int getCount() {
        return dataSet.size();
    }

    @Override
    public StudentInfo getItem(int i) {
        return dataSet.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {


        final ViewHolder viewHolder;

        if (view == null) {
            viewHolder = new ViewHolder();

            view = mActivity.getLayoutInflater().inflate(R.layout.student_adapter_list_view, null);
            viewHolder.firstname = view.findViewById(R.id.firstname);
            viewHolder.lastname = view.findViewById(R.id.lastname);
            viewHolder.absent = view.findViewById(R.id.absent);
            viewHolder.present = view.findViewById(R.id.present);

            view.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) view.getTag();
        }


        StudentInfo item = getItem(i);

        viewHolder.firstname.setText(item.getFirstname());
        viewHolder.lastname.setText(item.getLastname());

        viewHolder.absent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Call<List<Temp>> user = ApiClient.getapiInterface().startattendance(getItem(i).getFirstname(),getItem(i).getLastname(), date,clas,subject,"Absent");
                user.enqueue(new Callback<List<Temp>>() {
                    @Override
                    public void onResponse(Call<List<Temp>> call, Response<List<Temp>> response) {
                        if(response.body().get(0).getMessage().equals("success")){
                            Toast.makeText(mContext, "Marked: Absent", Toast.LENGTH_SHORT).show();
                            viewHolder.absent.setClickable(false);
                            viewHolder.present.setClickable(false);
                            viewHolder.present.setAlpha(Float.parseFloat("0.1"));
                            viewHolder.absent.setAlpha(Float.parseFloat("0.1"));
                        }else{
                            Toast.makeText(mContext, "Attendance didn't marked", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Temp>> call, Throwable t) {
                        Toast.makeText(mContext, "Failed to Connect", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        viewHolder.present.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Call<List<Temp>> user = ApiClient.getapiInterface().startattendance(getItem(i).getFirstname(),getItem(i).getLastname(), date,clas,subject,"Present");
                user.enqueue(new Callback<List<Temp>>() {
                    @Override
                    public void onResponse(Call<List<Temp>> call, Response<List<Temp>> response) {
                        if(response.body().get(0).getMessage().equals("success")){
                            Toast.makeText(mContext, "Marked: Present", Toast.LENGTH_SHORT).show();
                            viewHolder.absent.setClickable(false);
                            viewHolder.present.setClickable(false);
                            viewHolder.present.setAlpha(Float.parseFloat("0.1"));
                            viewHolder.absent.setAlpha(Float.parseFloat("0.1"));
                        }else{
                            Toast.makeText(mContext, "Attendance didn't marked", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Temp>> call, Throwable t) {
                        Toast.makeText(mContext, "Failed to Connect", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        return view;
    }
}
