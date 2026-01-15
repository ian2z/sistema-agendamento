package requisitos;


public class Fachada {
	private static Repositorio repositorio = new Repositorio();
	
	private Fachada() {} // classe nao instanciavel


	public static ArrayList<Participante> listarParticipantes() {

	}

	public static ArrayList<Empregado> listarEmpregados() {

	}

	public static ArrayList<Convidado> listarConvidados() {

	}

	public static ArrayList<Reuniao> listarReunioes() {

	}

	public static void criarEmpregado(String nome, String email, String setor) throws Exception {

	}

	public static void criarConvidado(String nome, String email, String instituicao) throws Exception {

	}

	public static void criarReuniao(String data, String assunto, ArrayList<String> nomes) throws Exception {
		
	}

	public static void adicionarParticipanteReuniao(String nome, int id) throws Exception {
		
	}

	public static void removerParticipanteReuniao(String nome, int id) throws Exception {
		
	}

	public static void cancelarReuniao(int id) throws Exception {
		
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
