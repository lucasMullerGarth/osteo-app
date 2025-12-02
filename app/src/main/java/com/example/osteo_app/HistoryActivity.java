package com.example.osteo_app;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.osteo_app.databinding.ActivityHistoryBinding;

public class HistoryActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityHistoryBinding binding;

    private Button voltarButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        voltarButton = findViewById(R.id.btnVoltarHistory);

        voltarButton.setOnClickListener(view -> voltar());
    }

    public void voltar(){

        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);

    }


}