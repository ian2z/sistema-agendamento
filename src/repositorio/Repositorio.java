/**********************************
 * IFPB - Curso Superior de Tec. em Sist. para Internet
 * Programa��o Orientada a Objetos
 * Prof. Fausto Maranh�o Ayres
 **********************************/

package repositorio;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;

import modelo.Convidado;
import modelo.Empregado;
import modelo.Participante;
import modelo.Reuniao;

public class Repositorio {

    // ==================== ESTADO INTERNO ====================

    private ArrayList<Participante> participantes;
    private ArrayList<Reuniao> reunioes;
    private int proximoId;

    public Repositorio() {
        this.participantes = new ArrayList<>();
        this.reunioes = new ArrayList<>();
        this.proximoId = 1;
    }

    // ==================== ADICIONAR ====================

    public void adicionar(Participante participante) {
        participantes.add(participante);
    }

    public void adicionar(Reuniao reuniao) {
        reuniao.setId(proximoId);
        reunioes.add(reuniao);
        proximoId++;
    }

    // metodos para localização

    public Participante localizarParticipante(String nome) {
        for (Participante p : participantes) {
            if (p.getNome().equals(nome)) {
                return p;
            }
        }
        return null;
    }

    public Reuniao localizarReuniao(int id) {
        for (Reuniao r : reunioes) {
            if (r.getId() == id) {
                return r;
            }
        }
        return null;
    }

    public Reuniao localizarReuniao(String data) {
        for (Reuniao r : reunioes) {
            if (r.getData().equals(data)) {
                return r;
            }
        }
        return null;
    }

    // metodos para remover

    public void remover(Reuniao reuniao) {
        reunioes.remove(reuniao);
    }

    // getters

    public ArrayList<Participante> getParticipantes() {
        ArrayList<Participante> copia = new ArrayList<>(participantes);
        Collections.sort(copia, Comparator.comparing(Participante::getNome));
        return copia;
    }

    public ArrayList<Empregado> getEmpregados() {
        ArrayList<Empregado> empregados = new ArrayList<>();
        for (Participante p : participantes) {
            if (p instanceof Empregado) {
                empregados.add((Empregado) p);
            }
        }
        empregados.sort(Comparator.comparing(Empregado::getNome));
        return empregados;
    }

    public ArrayList<Convidado> getConvidados() {
        ArrayList<Convidado> convidados = new ArrayList<>();
        for (Participante p : participantes) {
            if (p instanceof Convidado) {
                convidados.add((Convidado) p);
            }
        }
        convidados.sort(Comparator.comparing(Convidado::getNome));
        return convidados;
    }

    public ArrayList<Reuniao> getReunioes() {
        return new ArrayList<>(reunioes);
    }

    // tratamento do CSV (modificado)

    public void lerObjetos() {
        File f1;
        File f2;

        try {
            f1 = new File(new File(".\\reunioes.csv").getCanonicalPath());
            f2 = new File(new File(".\\participantes.csv").getCanonicalPath());

            if (!f1.exists()) f1.createNewFile();
            if (!f2.exists()) f2.createNewFile();

        } catch (Exception e) {
            throw new RuntimeException("Erro ao criar arquivos CSV");
        }

        // ---- Ler reuniões ----
        try (Scanner sc = new Scanner(f1)) {
            while (sc.hasNextLine()) {
                String linha = sc.nextLine().trim();
                if (linha.isEmpty()) continue;

                String[] p = linha.split(";");
                int id = Integer.parseInt(p[0]);
                String data = p[1];
                String assunto = p[2];

                Reuniao r = new Reuniao(data, assunto);
                r.setId(id);
                reunioes.add(r);

                if (id >= proximoId) {
                    proximoId = id + 1;
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Erro ao ler reunioes.csv");
        }

        // ---- Ler participantes ----
        try (Scanner sc = new Scanner(f2)) {
            while (sc.hasNextLine()) {
                String linha = sc.nextLine().trim();
                if (linha.isEmpty()) continue;

                String[] p = linha.split(";");
                String tipo = p[0];
                String nome = p[1];
                String email = p[2];
                String complemento = p[3];

                Participante part;

                if (tipo.equals("EMPREGADO")) {
                    part = new Empregado(nome, email, complemento);
                } else if (tipo.equals("CONVIDADO")) {
                    part = new Convidado(nome, email, complemento);
                } else {
                    throw new RuntimeException("Tipo inválido em participantes.csv");
                }

                participantes.add(part);

                if (p.length > 4 && !p[4].isEmpty()) {
                    String[] ids = p[4].split(",");
                    for (String idStr : ids) {
                        Reuniao r = localizarReuniao(Integer.parseInt(idStr));
                        if (r != null) {
                            r.adicionar(part);
                            part.adicionar(r);
                        }
                    }
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Erro ao ler participantes.csv");
        }
    }

    public void gravarObjetos() {
        try (FileWriter fw = new FileWriter(".\\participantes.csv")) {
            for (Participante p : participantes) {
                String tipo = (p instanceof Empregado) ? "EMPREGADO" : "CONVIDADO";
                String complemento = (p instanceof Empregado)
                        ? ((Empregado) p).getSetor()
                        : ((Convidado) p).getInstituicao();

                ArrayList<String> ids = new ArrayList<>();
                for (Reuniao r : p.getReunioes()) {
                    ids.add(String.valueOf(r.getId()));
                }

                fw.write(tipo + ";" +
                                p.getNome() + ";" +
                                p.getEmail() + ";" +
                                complemento + ";" +
                                String.join(",", ids) +
                                "\n");
            }
        } catch (Exception e) {
            throw new RuntimeException("Erro ao gravar participantes.csv");
        }

        try (FileWriter fw = new FileWriter(".\\reunioes.csv")) {
            for (Reuniao r : reunioes) {
                fw.write(r.getId() + ";" + r.getData() + ";" + r.getAssunto() + "\n");
            }
        } catch (Exception e) {
            throw new RuntimeException("Erro ao gravar reunioes.csv");
        }
    }

    public int getProximoId() {
        return proximoId;
    }
}
// classe
