package br.com.robson.fut;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Robson on 02/10/2016.
 */

public class Campeonato {

    private String nome;

    private String cidade;

    private String estado;

    private String pais;

    private List<Divisao> divisoes;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public List<Divisao> getDivisoes() {
        return divisoes;
    }

    public void setDivisoes(List<Divisao> divisoes) {
        this.divisoes = divisoes;
    }
}
