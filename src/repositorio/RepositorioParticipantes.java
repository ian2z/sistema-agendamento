package repositorio;

import modelo.Convidado;
import modelo.Empregado;
import modelo.Participante;

import java.util.ArrayList;
import java.util.Comparator;


public class RepositorioParticipantes {

    private ArrayList<Participante> participantes;

    public RepositorioParticipantes() {
        this.participantes = new ArrayList<>();
    }

    public void adicionar(Participante participante) {
        participantes.add(participante);
    }

    public Participante buscarPorNome(String nome) {
        for (Participante p : participantes) {
            if (p.getNome().equals(nome)) {
                return p;
            }
        }
        return null;
    }

    public boolean existePorNome(String nome) {
        return buscarPorNome(nome) != null;
    }

    public ArrayList<Participante> listarTodos() {
        ArrayList<Participante> copia = new ArrayList<>(participantes);
        copia.sort(Comparator.comparing(Participante::getNome));
        return copia;
    }

    public ArrayList<Participante> listarEmpregados() {
        ArrayList<Participante> empregados = new ArrayList<>();

        for (Participante p : participantes) {
            if (p instanceof Empregado) {
                empregados.add(p);
            }
        }

        empregados.sort(Comparator.comparing(Participante::getNome));
        return empregados;
    }

    public ArrayList<Participante> listarConvidados() {
        ArrayList<Participante> convidados = new ArrayList<>();

        for (Participante p : participantes) {
            if (p instanceof Convidado) {
                convidados.add(p);
            }
        }

        convidados.sort(Comparator.comparing(Participante::getNome));
        return convidados;
    }

    public int getTotalParticipantes() {
        return participantes.size();
    }
}
