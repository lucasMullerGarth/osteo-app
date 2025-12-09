package com.example.osteo_app;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DatabaseError;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class PainAssessmentActivity extends AppCompatActivity {
    private SeekBar seekPain;
    private TextView txtPainLevel, txtPainDescription;
    private TextView txtResumoData, txtResumoNivel;
    private Button btnSalvar;
    private PainAssessmentDAO painAssessmentDAO;
    private UsuarioDAO usuarioDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pain_assessment);

        painAssessmentDAO = new PainAssessmentDAO();
        usuarioDAO = new UsuarioDAO();

        seekPain = findViewById(R.id.seekPain);
        txtPainLevel = findViewById(R.id.txtPainLevel);
        txtPainDescription = findViewById(R.id.txtPainDescription);
        txtResumoData = findViewById(R.id.txtResumoData);
        txtResumoNivel = findViewById(R.id.txtResumoNivel);
        btnSalvar = findViewById(R.id.btnSalvar);

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

        btnSalvar.setOnClickListener(v -> {
            usuarioDAO.getUsuario(new UsuarioDAO.UsuarioCallback() {
                @Override
                public void onUsuarioLoaded(Usuario usuario) {
                    if (usuario == null) {
                        Toast.makeText(PainAssessmentActivity.this, "Crie um perfil antes de salvar a avaliação.", Toast.LENGTH_LONG).show();
                        startActivity(new Intent(PainAssessmentActivity.this, MainActivity.class));
                        return;
                    }

                    int nivelDor = seekPain.getProgress();
                    painAssessmentDAO.inserirAvaliacao(dataAtual, nivelDor, new PainAssessmentDAO.PainAssessmentSaveCallback() {
                        @Override
                        public void onAssessmentSaved() {
                            Toast.makeText(PainAssessmentActivity.this, "Avaliação de dor salva com sucesso!", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            Toast.makeText(PainAssessmentActivity.this, "Erro ao salvar a avaliação.", Toast.LENGTH_SHORT).show();
                        }
                    });
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Toast.makeText(PainAssessmentActivity.this, "Erro ao verificar o perfil.", Toast.LENGTH_SHORT).show();
                }
            });
        });

        BottomNavigationView bottomNav = findViewById(R.id.bottomNav);
        bottomNav.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.nav_relief) {
                startActivity(new Intent(this, HomeActivity.class));
                return true;
            } else if (itemId == R.id.nav_pain) {
                return true;
            } else if (itemId == R.id.nav_profile) {
                usuarioDAO.getUsuario(new UsuarioDAO.UsuarioCallback() {
                    @Override
                    public void onUsuarioLoaded(Usuario usuario) {
                        if (usuario != null) {
                            startActivity(new Intent(PainAssessmentActivity.this, PerfilActivity.class));
                        } else {
                            startActivity(new Intent(PainAssessmentActivity.this, MainActivity.class));
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        // Tratar erro
                    }
                });
                return true;
            }
            return false;
        });
    }
}
