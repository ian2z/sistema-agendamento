package requisitos;


import java.util.ArrayList;

import modelo.Convidado;
import modelo.Empregado;
import modelo.Participante;
import modelo.Reuniao;
import repositorio.Repositorio;

public class Fachada {
    private static Repositorio repositorio = new Repositorio();

    private Fachada() {} // classe nao instanciavel

    // carregar objetos existentes ao iniciar
    static {
        try {
            repositorio.lerObjetos();
        } catch (Exception e) {
            // se falhar vai continuamos com repositório vazio
        }
    }

    public static ArrayList<Participante> listarParticipantes() {
        return repositorio.getParticipantes();
    }

    public static ArrayList<Empregado> listarEmpregados() {
        return repositorio.getEmpregados();
    }

    public static ArrayList<Convidado> listarConvidados() {
        return repositorio.getConvidados();
    }

    public static ArrayList<Reuniao> listarReunioes() {
        return repositorio.getReunioes();
    }

    //CRIAR O EMPREGADO
    public static void criarEmpregado(String nome, String email, String setor) throws Exception {
        if (repositorio.localizarParticipante(nome) != null) {
            throw new RuntimeException("Nome já cadastrado");
        }

        String regexEmail = "^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$";
        if (!email.matches(regexEmail)) {
            throw new RuntimeException("Email inválido");
        }

        Empregado empregado = new Empregado(nome, email, setor);
        repositorio.adicionar(empregado);
        repositorio.gravarObjetos();
    }

    //CRIAR O CONVIDADO
    public static void criarConvidado(String nome, String email, String instituicao) throws Exception {
        if (repositorio.localizarParticipante(nome) != null) {
            throw new RuntimeException("Nome já cadastrado");
        }

        String regexEmail = "^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$";
        if (!email.matches(regexEmail)) {
            throw new RuntimeException("Email inválido");
        }

        Convidado convidado = new Convidado(nome, email, instituicao);
        repositorio.adicionar(convidado);
        repositorio.gravarObjetos();
    }

    //CRIAR REUNIAO
    public static void criarReuniao(String data, String assunto, ArrayList<String> nomes) throws Exception {
        // verifica se a data é unica
        if (repositorio.localizarReuniaoData(data) != null) {
            throw new RuntimeException("Já existe reunião cadastrada nessa data");
        }

        // verifica se tem mais de 2 participantes
        if (nomes == null || nomes.size() < 2) {
            throw new RuntimeException("Uma reunião deve ter pelo menos 2 participantes");
        }

        // verifica duplicidade e existência de participantes
        ArrayList<Participante> participantesReuniao = new ArrayList<>();

        for (String nome : nomes) {
            Participante p = repositorio.localizarParticipante(nome);

            if (p == null) {
                throw new RuntimeException("Participante não encontrado: " + nome);
            }

            if (participantesReuniao.contains(p)) {
                throw new RuntimeException("Participante duplicado na reunião: " + nome);
            }

            participantesReuniao.add(p);
        }

        Reuniao r = new Reuniao(data, assunto);
        // adiciona na lista e define id
        repositorio.adicionar(r);

        for (String nome : nomes) {
            Participante p = repositorio.localizarParticipante(nome);
            if (p == null) {
                throw new RuntimeException("Participante " + nome + " nao encontrado");
            }
            r.adicionar(p);
            p.adicionar(r);
        }

        repositorio.gravarObjetos();
    }

    public static void adicionarParticipanteReuniao(String nome, int id) throws Exception {
        Participante participante = repositorio.localizarParticipante(nome);
        Reuniao reuniao = repositorio.localizarReuniao(id);

        if (participante == null) {
            throw new RuntimeException("Participante nao encontrado: " + nome);
        }
        if (reuniao == null) {
            throw new RuntimeException("Reuniao nao encontrada: " + id);
        }

        reuniao.adicionar(participante);
        participante.adicionar(reuniao);
        repositorio.gravarObjetos();
    }

    public static void removerParticipanteReuniao(String nome, int id) throws Exception {
        Participante participante = repositorio.localizarParticipante(nome);
        Reuniao reuniao = repositorio.localizarReuniao(id);

        if (participante == null) {
            throw new RuntimeException("Participante nao encontrado: " + nome);
        }

        if (reuniao == null) {
            throw new RuntimeException("Reuniao nao encontrada: " + id);
        }

        //verifica o relacionamento
        if (!reuniao.getParticipantes().contains(participante)) {
            throw new RuntimeException("Participante não participa dessa reunião");
        }

        reuniao.remover(participante);
        participante.remover(reuniao);

        //mínimo de 2 participantes
        if (reuniao.getParticipantes().size() < 2) {
            cancelarReuniao(id);
        } else {
            repositorio.gravarObjetos();
        }
    }

    public static void cancelarReuniao(int id) {

        Reuniao r = repositorio.localizarReuniao(id);
        if (r == null) {
            throw new RuntimeException("Reunião não encontrada: " + id);
        }

        ArrayList<Participante> copia = new ArrayList<>(r.getParticipantes());
        for (Participante p : copia) {
            p.remover(r);
            r.remover(p);
        }
        repositorio.remover(r);
        repositorio.gravarObjetos();
    }

 /*
     *
     * Consultas
     *
     */

    public static ArrayList<Participante> consulta1(int n) {

        ArrayList<Participante> resultado = new ArrayList<>();

        for (Participante p : repositorio.getParticipantes()) {
            if (p.getReunioes().size() >= n) {
                resultado.add(p);
            }
        }
        return resultado;
    }


    public static int consulta2(int mes, int ano) {

        int contador = 0;

        for (Reuniao r : repositorio.getReunioes()) {
            String[] partes = r.getData().split("/");

            int mesReuniao = Integer.parseInt(partes[1]);
            int anoReuniao = Integer.parseInt(partes[2]);

            if (mesReuniao == mes && anoReuniao == ano) {
                contador++;
            }
        }

        return contador;
    }
}
