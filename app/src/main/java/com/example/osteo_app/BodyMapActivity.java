package com.example.osteo_app;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import androidx.navigation.ui.AppBarConfiguration;

import com.example.osteo_app.databinding.ActivityBodyMapBinding;

public class BodyMapActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityBodyMapBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_body_map);
    }
}