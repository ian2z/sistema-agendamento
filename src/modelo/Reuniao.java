package modelo;

import java.util.ArrayList;

public class Reuniao {

    private int id;
    private String data;
    private String assunto;
    private ArrayList<Participante> participantes = new ArrayList<>();

    public Reuniao(String data, String assunto) {
        this.data = data;
        this.assunto = assunto;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getData() {
        return data;
    }

    public String getAssunto() {
        return assunto;
    }

    public ArrayList<Participante> getParticipantes() {
        return participantes;
    }

    public void adicionar(Participante p) {
        if (!participantes.contains(p)) {
            participantes.add(p);
        }
    }

    public void remover(Participante p) {
        participantes.remove(p);
    }
}