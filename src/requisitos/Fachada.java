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
		return null;
	}

	public static void criarEmpregado(String nome, String email, String setor) throws Exception {
		if (repositorio.localizarParticipante(nome) != null) {
			throw new RuntimeException("Nome já cadastrado")
		}

		String regexEmail = "^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$";
		if (!email.matches(regexEmail)) {
			throw new RuntimeException("Email inválido");
		}

		Empregado empregado = new Empregado(nome, email, setor);
		repositorio.aicionar(empregado);
	}

	public static void criarConvidado(String nome, String email, String instituicao) throws Exception {
		if (repositorio.localizarParticipante(nome) != null) {
			throw new RuntimeException("Nome já cadastrado")
		}

		String regexEmail = "^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$";
		if (!email.matches(regexEmail)) {
			throw new RuntimeException("Email inválido");
		}

		Convidado convidado = new Convidado(nome, email, instituicao);
		repositorio.aicionar(convidado);
	}

	public static void criarReuniao(String data, String assunto, ArrayList<String> nomes) throws Exception {
		
	}

	public static void adicionarParticipanteReuniao(String nome, int id) throws Exception {
		Participante participante = new localizarParticipante(nome);
		Reuniao reuniao = new localizarReuniao(id);

		reuniao.adicionar(participante);
		participante.adicionar(reuniao);
	}

	public static void removerParticipanteReuniao(String nome, int id) throws Exception {
		Participante participante = new localizarParticipante(nome);
		Reuniao reuniao = new localizarReuniao(id);

		reuniao.remover(participante);
		participante.remover(reuniao);
	}

	public static void cancelarReuniao(int id) throws Exception {
		Reuniao reuniao = new localizarReuniao(id);

		for (Participante p : reuniao) {
			p.remover(reuniao);
		}
		repositorio.remover(reuniao);
	}

	/*
	 * 
	 * Consultas
	 * 
	 */
	public static ArrayList<Participante> consulta1(int n) {
		
	}
	public static int consulta2(String mes, String ano) {
		
	}
}
