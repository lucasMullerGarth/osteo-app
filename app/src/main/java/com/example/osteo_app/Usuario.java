package com.example.osteo_app;

public class Usuario {
    private String id;
    private String nome;
    private int idade;
    private Integer anoDiagnostico;
    private String celular;
    private String genero;
    private String comorbidades;

    public Usuario() {
        // Construtor vazio necessário para o Firebase
    }

    public Usuario(String id, String nome, int idade, Integer anoDiagnostico, String celular, String genero, String comorbidades) {
        this.id = id;
        this.nome = nome;
        this.idade = idade;
        this.anoDiagnostico = anoDiagnostico;
        this.celular = celular;
        this.genero = genero;
        this.comorbidades = comorbidades;
    }

    public String getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public int getIdade() {
        return idade;
    }

    public Integer getAnoDiagnostico() {
        return anoDiagnostico;
    }

    public String getCelular() {
        return celular;
    }

    public String getGenero() {
        return genero;
    }

    public String getComorbidades() {
        return comorbidades;
    }
}
