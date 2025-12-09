package com.example.osteo_app;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DatabaseError;

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

        usuarioDAO = new UsuarioDAO();
        textViewNome = findViewById(R.id.textViewNome);
        textViewIdade = findViewById(R.id.textViewIdade);
        textViewAnoDiagnostico = findViewById(R.id.textViewAnoDiagnostico);
        textViewCelular = findViewById(R.id.textViewCelular);
        textViewGenero = findViewById(R.id.textViewGenero);
        textViewComorbidades = findViewById(R.id.textViewComorbidades);
        buttonExcluir = findViewById(R.id.buttonExcluir);

        loadUserProfile();

        buttonExcluir.setOnClickListener(v -> {
            usuarioDAO.excluirTodosUsuarios(new UsuarioDAO.UsuarioSaveCallback() {
                @Override
                public void onUsuarioSaved() {
                    Toast.makeText(PerfilActivity.this, "Cadastro excluído com sucesso!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(PerfilActivity.this, MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    finish();
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Toast.makeText(PerfilActivity.this, "Erro ao excluir o cadastro.", Toast.LENGTH_SHORT).show();
                }
            });
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
        usuarioDAO.getUsuario(new UsuarioDAO.UsuarioCallback() {
            @Override
            public void onUsuarioLoaded(Usuario usuario) {
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
                    // Safeguard
                    Toast.makeText(PerfilActivity.this, "Nenhum perfil encontrado!", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(PerfilActivity.this, MainActivity.class));
                    finish();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(PerfilActivity.this, "Erro ao carregar o perfil.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
