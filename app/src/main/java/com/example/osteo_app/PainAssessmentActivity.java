package com.example.osteo_app;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.example.osteo_app.databinding.ActivityPainAssessmentBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class PainAssessmentActivity extends AppCompatActivity {

    private ActivityPainAssessmentBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPainAssessmentBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation_bar);
        bottomNav.setSelectedItemId(R.id.nav_pain);
        bottomNav.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.nav_relief) {
                startActivity(new Intent(this, HomeActivity.class));
                return true;
            } else if (itemId == R.id.nav_pain) {
                return true; // Already on this screen
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
}
