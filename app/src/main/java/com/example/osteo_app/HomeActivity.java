package com.example.osteo_app;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeActivity extends AppCompatActivity {

    private Button tecnicasdealivioButton;
    private Button educacaoButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        tecnicasdealivioButton = findViewById(R.id.btnRelief);
        educacaoButton = findViewById(R.id.btnEducation);

        tecnicasdealivioButton.setOnClickListener(view -> chamarTelaAlivio(view));
        educacaoButton.setOnClickListener(view -> chamarTelaEducacao(view));

        BottomNavigationView bottomNav = findViewById(R.id.bottomNav);

        bottomNav.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.nav_relief) {
                return true;
            } else if (itemId == R.id.nav_pain) {
                startActivity(new Intent(this, PainAssessmentActivity.class));
                return true;
            } else if (itemId == R.id.nav_profile) {
                SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);
                boolean isProfileCreated = prefs.getBoolean("isProfileCreated", false);
                if (isProfileCreated) {
                    startActivity(new Intent(this, PerfilActivity.class));
                } else {
                    startActivity(new Intent(this, MainActivity.class));
                }
                return true;
            }
            return false;
        });
    }

    public void chamarTelaAlivio(View view) {
        Intent intent = new Intent(this, ReliefTechniquesActivity.class);
        startActivity(intent);
    }

    public void chamarTelaEducacao(View view) {
        Intent intent = new Intent(this, EducationActivity.class);
        startActivity(intent);
    }
}
