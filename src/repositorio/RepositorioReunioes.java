package repositorio;

import modelo.Reuniao;

import java.util.ArrayList;

public class RepositorioReunioes {

    // Estado interno
    private ArrayList<Reuniao> reunioes;
    private int proximoId;


    public RepositorioReunioes() {
        this.reunioes = new ArrayList<>();
        this.proximoId = 1;
    }

    public void adicionar(Reuniao reuniao) {
        reuniao.setId(proximoId);
        reunioes.add(reuniao);
        proximoId++;
    }

    public Reuniao buscarPorId(int id) {
        for (Reuniao r : reunioes) {
            if (r.getId() == id) {
                return r;
            }
        }
        return null;
    }

    public Reuniao buscarPorData(String data) {
        for (Reuniao r : reunioes) {
            if (r.getData().equals(data)) {
                return r;
            }
        }
        return null;
    }

    public boolean existePorData(String data) {
        return buscarPorData(data) != null;
    }

    public boolean remover(Reuniao reuniao) {
        return reunioes.remove(reuniao);
    }

    public boolean removerPorId(int id) {
        Reuniao reuniao = buscarPorId(id);
        if (reuniao != null) {
            return reunioes.remove(reuniao);
        }
        return false;
    }

    public ArrayList<Reuniao> listarTodas() {
        return new ArrayList<>(reunioes);
    }

    public int getTotalReunioes() {
        return reunioes.size();
    }

    public int getProximoId() {
        return proximoId;
    }
}