package com.example.osteo_app;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import com.example.osteo_app.databinding.ActivityPainAssessmentBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class PainAssessmentActivity extends AppCompatActivity {
    private SeekBar seekPain;
    private TextView txtPainLevel, txtPainDescription;
    private TextView txtResumoData, txtResumoNivel, txtResumoPontos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pain_assessment);

        seekPain = findViewById(R.id.seekPain);
        txtPainLevel = findViewById(R.id.txtPainLevel);
        txtPainDescription = findViewById(R.id.txtPainDescription);
        txtResumoData = findViewById(R.id.txtResumoData);
        txtResumoNivel = findViewById(R.id.txtResumoNivel);
        txtPainLevel.setText("0");
        txtPainDescription.setText("Sem dor");

        String dataAtual = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(new Date());
        txtResumoData.setText("Data: " + dataAtual);

        seekPain.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                txtPainLevel.setText(String.valueOf(progress));
                txtResumoNivel.setText("Nível de dor numérico: " + progress + "/10");

                if (progress == 0) {
                    txtPainDescription.setText("Sem dor");
                } else if (progress <= 3) {
                    txtPainDescription.setText("Dor leve");
                } else if (progress <= 6) {
                    txtPainDescription.setText("Dor moderada");
                } else if (progress <= 8) {
                    txtPainDescription.setText("Dor forte");
                } else {
                    txtPainDescription.setText("Dor intensa");
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });
    }

    
}
