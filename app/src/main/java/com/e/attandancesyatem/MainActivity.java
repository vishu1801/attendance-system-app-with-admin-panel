package com.e.attandancesyatem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    Button submit;
    EditText username, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        submit = (Button) findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkinternet()) {
                    validation();
                }else{
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


    private void validation() {
        Call<List<UserValidationResponse>> user = ApiClient.getapiInterface().saveuser(username.getText().toString(), password.getText().toString());
        user.enqueue(new Callback<List<UserValidationResponse>>() {
            @Override
            public void onResponse(Call<List<UserValidationResponse>> call, Response<List<UserValidationResponse>> response) {
                if (response.body().get(0).getMessage().equals("invalid user")) {
                    Toast.makeText(getApplicationContext(), "You are an invalid user.", Toast.LENGTH_SHORT).show();
                }else if (response.body().get(0).getMessage().equals("wrong password")) {
                    Toast.makeText(getApplicationContext(), "Please enter the valid password.", Toast.LENGTH_SHORT).show();
                }else if (response.body().get(0).getMessage().equals("empty")) {
                    Toast.makeText(getApplicationContext(), "Please Fill All the fields", Toast.LENGTH_SHORT).show();
                }
                else {
                    Intent intent = new Intent(getApplicationContext(), Home.class);
                    intent.putExtra("id", response.body().get(0).getMessage());
                    startActivity(intent);
                    finish();
                }
            }

            @Override
            public void onFailure(Call<List<UserValidationResponse>> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Failed" + t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }
}