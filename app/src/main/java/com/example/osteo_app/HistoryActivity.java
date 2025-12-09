package com.example.osteo_app;

import android.os.Bundle;
import android.util.Pair;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.google.firebase.database.DatabaseError;

import java.util.ArrayList;
import java.util.List;

public class HistoryActivity extends AppCompatActivity {

    private PainAssessmentDAO painAssessmentDAO;
    private TextView txtMediaDor, txtTendencia;
    private LineChart lineChart;
    private TextView txtRegistrosRecentes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        painAssessmentDAO = new PainAssessmentDAO();

        txtMediaDor = findViewById(R.id.txtMediaDor);
        txtTendencia = findViewById(R.id.txtTendencia);
        lineChart = findViewById(R.id.lineChart);
        txtRegistrosRecentes = findViewById(R.id.txtRegistrosRecentes);
        Button btnVoltar = findViewById(R.id.btnVoltarHistory);

        btnVoltar.setOnClickListener(v -> finish());

        loadHistoryData();
    }

    private void loadHistoryData() {
        painAssessmentDAO.getAllPainAssessments(new PainAssessmentDAO.PainAssessmentCallback() {
            @Override
            public void onAssessmentsLoaded(List<Pair<String, Integer>> assessments) {
                if (assessments.isEmpty()) {
                    // Tratar caso não haja avaliações
                    return;
                }

                // Calcular Média
                float media = 0;
                for (Pair<String, Integer> assessment : assessments) {
                    media += assessment.second;
                }
                media /= assessments.size();
                txtMediaDor.setText(String.format("%.1f/10", media));

                // Calcular Tendência
                if (assessments.size() > 1) {
                    float tendencia = (float) (assessments.get(assessments.size() - 1).second - assessments.get(0).second) / assessments.size();
                    txtTendencia.setText(String.format("%+.1f", tendencia));
                }

                // Popular Gráfico
                ArrayList<Entry> entries = new ArrayList<>();
                final ArrayList<String> labels = new ArrayList<>();
                for (int i = 0; i < assessments.size(); i++) {
                    entries.add(new Entry(i, assessments.get(i).second));
                    labels.add(assessments.get(i).first);
                }

                LineDataSet dataSet = new LineDataSet(entries, "Nível de Dor");
                LineData lineData = new LineData(dataSet);
                lineChart.setData(lineData);

                XAxis xAxis = lineChart.getXAxis();
                xAxis.setValueFormatter(new IndexAxisValueFormatter(labels));
                xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
                xAxis.setGranularity(1f);
                xAxis.setLabelRotationAngle(-45);

                lineChart.invalidate(); // refresh

                // Popular Registros Recentes
                StringBuilder registros = new StringBuilder();
                for (int i = assessments.size() - 1; i >= 0 && i >= assessments.size() - 5; i--) {
                    registros.append(assessments.get(i).first).append(" - Nível: ").append(assessments.get(i).second).append("\n");
                }
                txtRegistrosRecentes.setText(registros.toString());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(HistoryActivity.this, "Erro ao carregar o histórico.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
