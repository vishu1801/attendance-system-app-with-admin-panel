package com.e.attandancesyatem;

import androidx.activity.OnBackPressedDispatcher;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Attendance extends AppCompatActivity {

    TextView subject, clas, dated;
    String classs, sub, formatter, id;
    RecyclerView recycle;
    StudentRecyclerAdapter recycleadapter;
    ArrayList<StudentInfo> arrayList = new ArrayList();
    Toolbar toolbar;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.action,menu);
        MenuItem menuItem = menu.findItem(R.id.check);
        menuItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.check:
                        Intent intent = new Intent(getApplicationContext(),Home.class);
                        intent.putExtra("id",id);
                        startActivity(intent);
                        finish();
                }
                return true;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(),Home.class);
        intent.putExtra("id",id);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance);


        init();

        toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        id = (String) getIntent().getExtras().get("id");
        classs = (String) getIntent().getExtras().get("class");
        sub = (String) getIntent().getExtras().get("sub");

        formatter = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(new Date());
        dated.setText(formatter);


        recycle.setLayoutManager(new LinearLayoutManager(this));
        recycleadapter = new StudentRecyclerAdapter(arrayList, this, formatter, classs, sub);
        recycle.setAdapter(recycleadapter);


        subject.setText(sub);
        clas.setText(classs);

        getstudentinfo();
    }

    private void getstudentinfo() {
        Call<List<StudentInfoResponse>> user = ApiClient.getapiInterface().studentinfo(classs, sub, formatter);
        user.enqueue(new Callback<List<StudentInfoResponse>>() {
            @Override
            public void onResponse(Call<List<StudentInfoResponse>> call, Response<List<StudentInfoResponse>> response) {
                if (response.body().get(0).getMessage().equals("no attempt left")) {
                    Toast.makeText(getApplicationContext(), "You Have no attempt left", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), Home.class);
                    intent.putExtra("id", id);
                    startActivity(intent);
                    finish();
                } else if (response.body().get(0).getMessage().equals("no student found")) {
                    Toast.makeText(Attendance.this, "There is no student in this class", Toast.LENGTH_SHORT).show();
                } else {
                    for (int i = 0; i < response.body().size(); i++) {
                        StudentInfo studentInfo = new StudentInfo(response.body().get(i).getFirstname(), response.body().get(i).getLastname());
                        arrayList.add(studentInfo);
                    }
                    recycleadapter.notifyDataSetChanged();
                }

            }

            @Override
            public void onFailure(Call<List<StudentInfoResponse>> call, Throwable t) {
                Toast.makeText(Attendance.this, "failed" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void init() {
        subject = findViewById(R.id.subject);
        clas = findViewById(R.id.classs);
        dated = findViewById(R.id.date);
        recycle = findViewById(R.id.recycle);
    }
}