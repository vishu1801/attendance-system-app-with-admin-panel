package com.e.attandancesyatem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import com.google.android.material.snackbar.Snackbar;

public class Launch extends AppCompatActivity {
    private static int SPLASH_TIME_OUT = 2500;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);


        if (checkinternet()) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent homeIntent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(homeIntent);
                    finish();
                }
            }, SPLASH_TIME_OUT);

            } else {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {

                    //No internet action
                    Intent homeIntent = new Intent(getApplicationContext(), No_internet.class);
                    startActivity(homeIntent);
                    finish();
                }
            }, SPLASH_TIME_OUT);
        }


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

}

