package com.example.osteo_app;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.Button;

public class ReliefTechniquesActivity extends AppCompatActivity {

    private Button voltarButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_relief_techniques);
        /*voltarButton = findViewById(R.id.btnVoltar);

        // Configura o botão para finalizar a activity atual e voltar para a anterior
        voltarButton.setOnClickListener(view -> finish());*/
    }
}