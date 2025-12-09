package com.example.osteo_app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DatabaseError;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private EditText nomeEditText;
    private EditText idadeEditText;
    private EditText anoDiagnosticoEditText;
    private EditText celularEditText;
    private RadioGroup generoRadioGroup;
    private Button cadastrarButton;
    private List<CheckBox> comorbidadeCheckboxes;
    private UsuarioDAO usuarioDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        usuarioDAO = new UsuarioDAO();
        usuarioDAO.getUsuario(new UsuarioDAO.UsuarioCallback() {
            @Override
            public void onUsuarioLoaded(Usuario usuario) {
                if (usuario != null) {
                    startActivity(new Intent(MainActivity.this, PerfilActivity.class));
                    finish();
                } else {
                    // A IU de cadastro será exibida se não houver usuário
                    setContentView(R.layout.activity_main);
                    setupUI();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Tratar erro
                setContentView(R.layout.activity_main);
                setupUI();
            }
        });
    }

    private void setupUI() {
        nomeEditText = findViewById(R.id.edit_text_nome);
        idadeEditText = findViewById(R.id.edit_text_idade);
        anoDiagnosticoEditText = findViewById(R.id.edit_text_ano_diagnostico);
        celularEditText = findViewById(R.id.edit_text_celular);
        generoRadioGroup = findViewById(R.id.radio_group_genero);
        cadastrarButton = findViewById(R.id.botao_cadastrar);

        comorbidadeCheckboxes = Arrays.asList(
                findViewById(R.id.cb_artrite_reumatoide),
                findViewById(R.id.cb_espondilite),
                findViewById(R.id.cb_lupus),
                findViewById(R.id.cb_gota),
                findViewById(R.id.cb_fibromialgia),
                findViewById(R.id.cb_diabetes),
                findViewById(R.id.cb_hipertensao)
        );

        cadastrarButton.setOnClickListener(this::SalvarCadastro);

        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation_bar);
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

    public void SalvarCadastro(View view) {
        String nome = nomeEditText.getText().toString().trim();
        String idadeStr = idadeEditText.getText().toString().trim();
        String anoDiagnosticoStr = anoDiagnosticoEditText.getText().toString().trim();
        String celular = celularEditText.getText().toString().trim();

        Integer idade = null;
        try {
            idade = Integer.parseInt(idadeStr);
        } catch (NumberFormatException ignored) {
        }

        Integer anoDiagnostico = null;
        try {
            anoDiagnostico = Integer.parseInt(anoDiagnosticoStr);
        } catch (NumberFormatException ignored) {
        }

        if (nome.isEmpty() || idade == null || celular.isEmpty()) {
            Toast.makeText(this, "Por favor, preencha todos os campos obrigatórios.", Toast.LENGTH_LONG).show();
            return;
        }

        int selectedRadioId = generoRadioGroup.getCheckedRadioButtonId();
        String genero;
        if (selectedRadioId != -1) {
            RadioButton selectedRadioButton = findViewById(selectedRadioId);
            genero = selectedRadioButton.getText().toString();
        } else {
            genero = "Não informado";
        }

        List<String> comorbidadesList = new ArrayList<>();
        for (CheckBox cb : comorbidadeCheckboxes) {
            if (cb.isChecked()) {
                comorbidadesList.add(cb.getText().toString());
            }
        }
        String comorbidadesSelecionadas = String.join(", ", comorbidadesList);

        usuarioDAO.inserirUsuario(nome, idade, anoDiagnostico, celular, genero, comorbidadesSelecionadas, new UsuarioDAO.UsuarioSaveCallback() {
            @Override
            public void onUsuarioSaved() {
                Toast.makeText(MainActivity.this, "Usuário salvo com sucesso!", Toast.LENGTH_LONG).show();
                startActivity(new Intent(MainActivity.this, PerfilActivity.class));
                finish();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(MainActivity.this, "Erro ao salvar no banco!", Toast.LENGTH_LONG).show();
            }
        });
    }
}
