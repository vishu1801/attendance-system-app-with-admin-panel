package com.e.attandancesyatem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Home extends AppCompatActivity {

    ListView listView;
    ArrayList<ClassSubject> arrayList = new ArrayList();
    ClassSubjectAdapter adapter;
    String teacher_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        teacher_id = (String) getIntent().getExtras().get("id");
        listView = findViewById(R.id.list);
        adapter = new ClassSubjectAdapter(arrayList, getApplicationContext(), Home.this);
        listView.setAdapter(adapter);
        allotted();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (checkinternet()) {
                    Intent intent = new Intent(getApplicationContext(), Attendance.class);
                    intent.putExtra("id", teacher_id);
                    intent.putExtra("class", arrayList.get(position).getClasss());
                    intent.putExtra("sub", arrayList.get(position).getSubject());
                    startActivity(intent);
                    finish();
                } else {
                    nointernetconnection();
                }
            }
        });
    }

    public boolean checkinternet() {
        boolean connected = false;
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            //we are connected to a network
            connected = true;
        } else
            connected = false;
        return connected;
    }

    private void nointernetconnection() {

        View parentLayout = findViewById(android.R.id.content);
        Snackbar.make(parentLayout, "No Internet Connection!!", Snackbar.LENGTH_INDEFINITE)
                .setAction("Retry", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (checkinternet() == true) {
                            finish();
                            startActivity(getIntent());
                        } else {
                            nointernetconnection();
                        }
                    }
                })
                .setActionTextColor(getResources().getColor(android.R.color.holo_red_light))
                .show();
    }



    private void allotted() {
        Call<List<TeachersAllottedResponse>> user = ApiClient.getapiInterface().allotted(teacher_id);
        user.enqueue(new Callback<List<TeachersAllottedResponse>>() {
            @Override
            public void onResponse(Call<List<TeachersAllottedResponse>> call, Response<List<TeachersAllottedResponse>> response) {
                if(response.body().get(0).getMessage().equals("no record found")){
                    Toast.makeText(getApplicationContext(), "You have not alloted any class", Toast.LENGTH_SHORT).show();
                }else if(response.body().get(0).getMessage().equals("please send the id")){
                    Toast.makeText(getApplicationContext(), "Check your internet connection", Toast.LENGTH_SHORT).show();
                }
                else {
                    for (int i = 0; i < response.body().size(); i++) {
                        ClassSubject classSubject = new ClassSubject(response.body().get(i).getClasss(),response.body().get(i).getSubject());
                        arrayList.add(classSubject);
                    }
                    adapter.notifyDataSetChanged();
                }

            }

            @Override
            public void onFailure(Call<List<TeachersAllottedResponse>> call, Throwable t) {

            }
        });
    }
}