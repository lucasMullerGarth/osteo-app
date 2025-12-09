package com.example.osteo_app;

public class PainAssessment {
    private String id;
    private String dataRegistro;
    private int escalaDor;

    public PainAssessment() {
        // Construtor vazio necessário para o Firebase
    }

    public PainAssessment(String id, String dataRegistro, int escalaDor) {
        this.id = id;
        this.dataRegistro = dataRegistro;
        this.escalaDor = escalaDor;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDataRegistro() {
        return dataRegistro;
    }

    public void setDataRegistro(String dataRegistro) {
        this.dataRegistro = dataRegistro;
    }

    public int getEscalaDor() {
        return escalaDor;
    }

    public void setEscalaDor(int escalaDor) {
        this.escalaDor = escalaDor;
    }
}
