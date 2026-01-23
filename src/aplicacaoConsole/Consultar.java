/**********************************
 * IFPB - Curso Superior de Tec. em Sist. para Internet
 * Programa��o orientada a objetos
 * Prof. Fausto Maranh�o Ayres
 **********************************/
package aplicacaoConsole;

import modelo.Participante;
import modelo.Reuniao;
import requisitos.Fachada;

public class Consultar {

	public Consultar() {
		try {
			System.out.println("\n---------consulta1-----");
			System.out.println("---obter os participantes que participaram de 2 reuni�es ou mais");
			for(Participante p : Fachada.consulta1(2)) 
				System.out.println(p.getNome());
			
			System.out.println("\n---obter a quantidade de reuni�es no m�s �02� e ano �2026�");
			int total = Fachada.consulta2(02,2026);
				System.out.println(total);
		}
		catch(Exception e){
			System.out.println("-->" + e.getMessage());
		}

	}

	public static void main(String[] args){
		new Consultar();
	}
}
