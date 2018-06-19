package br.com.rodolfo.loja.models;

import com.google.gson.Gson;
import com.thoughtworks.xstream.XStream;

public class Projeto {
    
    private String nome;
    private Long id;
    private int anoDeInicio;

    public Projeto() {}

    public Projeto(String nome, long id, int anoDeInicio) {

        this.nome = nome;
        this.id = id;
        this.anoDeInicio = anoDeInicio;

    }


    /**
     * @return String return the nome
     */
    public String getNome() {
        return nome;
    }


    /**
     * @return Long return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return int return the anoDeInicio
     */
    public int getAnoDeInicio() {
        return anoDeInicio;
    }

    public String toXML() {
        return new XStream().toXML(this);
    }

    public String toJSON() {
        return new Gson().toJson(this);
    }

}