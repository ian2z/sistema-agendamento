package modelo;

import java.util.ArrayList;

public abstract class Participante {

    private String nome;
    private String email;
    private ArrayList<Reuniao> reunioes = new ArrayList<>();

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public ArrayList<Reuniao> getReunioes() {
        return reunioes;
    }

    public void adicionar(Reuniao r) {
        if (!reunioes.contains(r)) {
            reunioes.add(r);
        }
    }

    public void remover(Reuniao r) {
        reunioes.remove(r);
    }
}