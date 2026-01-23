/**********************************
 * IFPB - Curso Superior de Tec. em Sist. para Internet
 * Programa��o orientada a objetos
 * Prof. Fausto Maranh�o Ayres
 **********************************/
package aplicacaoConsole;

import modelo.Participante;
import modelo.Reuniao;
import requisitos.Fachada;

public class Listar {

	public Listar() {

		try {
			System.out.println("\n---------listagem de participantes-----");
			for(Participante p : Fachada.listarParticipantes()) 
				System.out.println(p.getNome());
			
			System.out.println("\n---------listagem de reunioes");
			for(Reuniao r : Fachada.listarReunioes()) 
				System.out.println(r.getAssunto());
		}
		catch(Exception e){
			System.out.println("-->" + e.getMessage());
		}

	}

	public static void main(String[] args){
		new Listar();
	}
}
