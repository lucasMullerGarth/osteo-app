package com.example.osteo_app;

import android.content.Intent;
import android.content.SharedPreferences;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);
        boolean isProfileCreated = prefs.getBoolean("isProfileCreated", false);

        if (isProfileCreated) {
            startActivity(new Intent(this, PerfilActivity.class));
            finish();
            return;
        }

        setContentView(R.layout.activity_main);

        nomeEditText = findViewById(R.id.edit_text_nome);
        idadeEditText = findViewById(R.id.edit_text_idade);
        anoDiagnosticoEditText = findViewById(R.id.edit_text_ano_diagnostico);
        celularEditText = findViewById(R.id.edit_text_celular);
        generoRadioGroup = findViewById(R.id.radio_group_genero);

        comorbidadeCheckboxes = Arrays.asList(
                (CheckBox) findViewById(R.id.cb_artrite_reumatoide),
                (CheckBox) findViewById(R.id.cb_espondilite),
                (CheckBox) findViewById(R.id.cb_lupus),
                (CheckBox) findViewById(R.id.cb_gota),
                (CheckBox) findViewById(R.id.cb_fibromialgia),
                (CheckBox) findViewById(R.id.cb_diabetes),
                (CheckBox) findViewById(R.id.cb_hipertensao)
        );

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

        UsuarioDAO usuarioDAO = new UsuarioDAO(this);

        long id = usuarioDAO.inserirUsuario(
                nome,
                idade,
                anoDiagnostico,
                celular,
                genero,
                comorbidadesSelecionadas
        );

        if (id > 0) {
            SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();
            editor.putBoolean("isProfileCreated", true);
            editor.apply();

            Toast.makeText(this, "Usuário salvo com ID: " + id, Toast.LENGTH_LONG).show();
            startActivity(new Intent(this, PerfilActivity.class));
            finish();
        } else {
            Toast.makeText(this, "Erro ao salvar no banco!", Toast.LENGTH_LONG).show();
        }
    }
}
