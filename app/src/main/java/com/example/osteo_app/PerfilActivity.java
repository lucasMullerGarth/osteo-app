package com.example.osteo_app;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class PerfilActivity extends AppCompatActivity {

    private UsuarioDAO usuarioDAO;
    private TextView textViewNome;
    private TextView textViewIdade;
    private TextView textViewAnoDiagnostico;
    private TextView textViewCelular;
    private TextView textViewGenero;
    private TextView textViewComorbidades;
    private Button buttonExcluir;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        usuarioDAO = new UsuarioDAO(this);
        textViewNome = findViewById(R.id.textViewNome);
        textViewIdade = findViewById(R.id.textViewIdade);
        textViewAnoDiagnostico = findViewById(R.id.textViewAnoDiagnostico);
        textViewCelular = findViewById(R.id.textViewCelular);
        textViewGenero = findViewById(R.id.textViewGenero);
        textViewComorbidades = findViewById(R.id.textViewComorbidades);
        buttonExcluir = findViewById(R.id.buttonExcluir);

        loadUserProfile();

        buttonExcluir.setOnClickListener(v -> {
            usuarioDAO.excluirTodosUsuarios();

            SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();
            editor.putBoolean("isProfileCreated", false);
            editor.apply();

            Toast.makeText(this, "Cadastro excluído com sucesso!", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        });

        BottomNavigationView bottomNav = findViewById(R.id.bottomNav);
        bottomNav.setSelectedItemId(R.id.nav_profile);
        bottomNav.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.nav_relief) {
                startActivity(new Intent(this, HomeActivity.class));
                return true;
            } else if (itemId == R.id.nav_pain) {
                startActivity(new Intent(this, PainAssessmentActivity.class));
                return true;
            } else if (itemId == R.id.nav_profile) {
                return true; // Already on this screen
            }
            return false;
        });
    }

    private void loadUserProfile() {
        Usuario usuario = usuarioDAO.getUsuario();
        if (usuario != null) {
            textViewNome.setText("Nome: " + usuario.getNome());
            textViewIdade.setText("Idade: " + usuario.getIdade());
            if (usuario.getAnoDiagnostico() != null) {
                textViewAnoDiagnostico.setText("Ano do Diagnóstico: " + usuario.getAnoDiagnostico());
            } else {
                textViewAnoDiagnostico.setText("Ano do Diagnóstico: Não informado");
            }
            textViewCelular.setText("Celular: " + usuario.getCelular());
            textViewGenero.setText("Gênero: " + usuario.getGenero());
            textViewComorbidades.setText("Comorbidades: " + usuario.getComorbidades());
        } else {
            // This part is a safeguard, as the user should only get here if a profile exists.
            Toast.makeText(this, "Nenhum perfil encontrado! Redirecionando para o cadastro.", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        }
    }

    public void chamarHistorico(View view){
        Intent intent = new Intent(this, HistoryActivity.class);
        startActivity(intent);
    }
}
