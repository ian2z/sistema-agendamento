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
            // se falhar, continuamos com repositório vazio
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

    public static void criarReuniao(String data, String assunto, ArrayList<String> nomes) throws Exception {
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

        reuniao.remover(participante);
        participante.remover(reuniao);
        repositorio.gravarObjetos();
    }

    public static void cancelarReuniao(int id) throws Exception {
        Reuniao reuniao = repositorio.localizarReuniao(id);
        if (reuniao == null) {
            throw new RuntimeException("Reuniao nao encontrada: " + id);
        }

        // remover referencia em cada participante
        ArrayList<Participante> copia = new ArrayList<>(reuniao.getParticipantes());
        for (Participante p : copia) {
            p.remover(reuniao);
        }
        repositorio.remover(reuniao);
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
            if (p.getReunioes().size() >= n) resultado.add(p);
        }
        return resultado;
    }
    public static int consulta2(String mes, String ano) {
        int total = 0;
        for (Reuniao r : repositorio.getReunioes()) {
            String[] partes = r.getData().split("/");
            if (partes.length >= 3) {
                String m = partes[1];
                String a = partes[2];
                if (m.equals(mes) && a.equals(ano)) total++;
            }
        }
        return total;
    }
}
