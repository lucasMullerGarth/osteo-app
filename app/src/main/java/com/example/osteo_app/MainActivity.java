package com.example.osteaoapp;

import static android.content.ContentValues.TAG;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

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

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
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
    }

    private void SalvarCadastro (View view){
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

        Log.d(TAG, "--- DADOS DO PERFIL COLETADOS ---");
        Log.d(TAG, "Nome: " + nome);
        Log.d(TAG, "Idade: " + idade);
        Log.d(TAG, "Ano de Diagnóstico: " + (anoDiagnostico != null ? anoDiagnostico : "Não informado"));
        Log.d(TAG, "Celular: " + celular);
        Log.d(TAG, "Gênero: " + genero);
        Log.d(TAG, "Comorbidades: " + comorbidadesSelecionadas);
        Log.d(TAG, "-----------------------------------");

        Toast.makeText(this, "Perfil de " + nome + " cadastrado com sucesso! (Verifique o Logcat)", Toast.LENGTH_LONG).show();
    }
}
