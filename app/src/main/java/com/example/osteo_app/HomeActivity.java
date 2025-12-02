package com.example.osteo_app;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeActivity extends AppCompatActivity {

    private CardView cardPainAssessment;
    private CardView cardBodyMap;
    private CardView cardHistory;
    private TextView tvWelcome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        cardPainAssessment = findViewById(R.id.cardPainAssessment);
        cardBodyMap = findViewById(R.id.cardBodyMap);
        cardHistory = findViewById(R.id.cardHistory);
        tvWelcome = findViewById(R.id.tvWelcome);

        loadUserProfile();

        cardPainAssessment.setOnClickListener(view ->
                startActivity(new Intent(this, PainAssessmentActivity.class))
        );

        cardBodyMap.setOnClickListener(view ->
                startActivity(new Intent(this, BodyMapActivity.class))
        );

        cardHistory.setOnClickListener(view ->
                startActivity(new Intent(this, HistoryActivity.class))
        );

        BottomNavigationView bottomNav = findViewById(R.id.bottomNav);

        bottomNav.setSelectedItemId(R.id.nav_relief);

        bottomNav.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();

            if (itemId == R.id.nav_relief) {

                return true;
            } else if (itemId == R.id.nav_pain) {
                startActivity(new Intent(this, PainAssessmentActivity.class));
                return true;
            } else if (itemId == R.id.nav_profile) {
                navigateToProfile();
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

    private void loadUserProfile() {
        SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);
        String userName = prefs.getString("userName", null);
        if (userName != null && !userName.isEmpty()) {
            tvWelcome.setText("Olá, " + userName + "!");
        } else {
            tvWelcome.setText("Olá!");
        }
    }

    private void navigateToProfile() {
        SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);
        boolean isProfileCreated = prefs.getBoolean("isProfileCreated", false);
        if (isProfileCreated) {
            startActivity(new Intent(this, PerfilActivity.class));
        } else {
            startActivity(new Intent(this, MainActivity.class));
        }
    }
}