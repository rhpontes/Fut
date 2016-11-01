package br.com.robson.fut;

/**
 * Created by Robson on 03/10/2016.
 */

public class Jogo implements ICallback {

    private String nome;

    @Override
    public void notifyDataRefreshed(Object resultado) {

    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
