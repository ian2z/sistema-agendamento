/**********************************
 * IFPB - Curso Superior de Tec. em Sist. para Internet
 * Programa��o Orientada a Objetos
 * Prof. Fausto Maranh�o Ayres
 **********************************/

package repositorio;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

import modelo.Convidado;
import modelo.Empregado;
import modelo.Participante;
import modelo.Reuniao;
import requisitos.Fachada;
import repositorio.RepositorioParticipantes;
import repositorio.RepositorioReunioes;

public class Repositorio {
	//...

	public void lerObjetos() {
		File f1 = null;
		File f2 = null;
		try {
			// caso os arquivos nao existam, serao criados vazios
			// obter o caminho do prog. no S.O. para criacao dos arquivos
			f1 = new File(new File(".\\reunioes.csv").getCanonicalPath());
			f2 = new File(new File(".\\participantes.csv").getCanonicalPath());

			if (!f1.exists() || !f2.exists()) {
				FileWriter arquivo1 = new FileWriter(f1);
				arquivo1.close();
				FileWriter arquivo2 = new FileWriter(f2);
				arquivo2.close();
				return;
			}

		} catch (Exception ex) {
			throw new RuntimeException("problema na criacao dos arquivos csv:" + ex.getMessage());
		}

		Scanner arquivo1 = null;
		Scanner arquivo2 = null;
		try {
			arquivo1 = new Scanner(f1);// Para arquivo interno
			String linha;
			String[] partes;
			String id, data, assunto;
			Reuniao r = null;
			while (arquivo1.hasNextLine()) {
				linha = arquivo1.nextLine().trim();
				partes = linha.split(";");
				id = partes[0];
				data = partes[1];
				assunto = partes[2];
				r = new Reuniao(Integer.parseInt(id), data, assunto);
				//System.out.println("reuniao lida: " + r);
				this.adicionar(r);
			}
		} catch (Exception e) {
			throw new RuntimeException("arquivo de reunioes inexistente:");
		}
		arquivo1.close();

		try {
			arquivo2 = new Scanner(f2);// Para arquivo interno
			String linha;
			String[] partes;
			Reuniao r = null;
			Participante p = null;
			Empregado e = null;
			Convidado c = null;
			String nome, email, complemento, tipo;
			while (arquivo2.hasNextLine()) {
				linha = arquivo2.nextLine().trim();
				partes = linha.split(";");
				tipo = partes[0];
				nome = partes[1];
				email = partes[2];
				complemento = partes[3];
				if (tipo.equals("EMPREGADO")) {
					e = new Empregado(nome, email, complemento);
					empregados.add(e);
					participantes.add(e);
					p=e;
				} else if (tipo.equals("CONVIDADO")) {
					c = new Convidado(nome, email, complemento);
					convidado.add(c);
					participantes.add(c);
					p=c;
				} else
					throw new RuntimeException("participantes.csv - tipo inv�lido: " + tipo);

				// processar lista de ids de reunioes do participante lido
				if (partes.length > 4) {
					String[] idsReunioes = partes[4].split(",");
					// relacionar participante com as reunioes correspondentes
					for (String idReuniao : idsReunioes) {
						r = this.localizarReuniao(Integer.parseInt(idReuniao));
						r.adicionar(p);
						p.adicionar(r);
					}
				}
				//System.out.println("participante lido: " + p);
			}
			arquivo2.close();
		} catch (Exception e) {
			throw new RuntimeException("arquivo de participantes inexistente:");
		}

	}

	public void gravarObjetos() {
		// gravar nos arquivos textos os dados dos participantes e
		// das reuni�es que est�o no reposit�rio
		FileWriter arquivo1 = null;
		FileWriter arquivo2 = null;
		try {
			arquivo1 = new FileWriter(new File(".\\participantes.csv").getCanonicalPath());
			for (Empregado e : empregados) {
				// System.out.println("gravando empregado: " + e);
				String dados = "EMPREGADO;" + e.getNome() + ";" + e.getEmail() + ";" + e.getSetor() + ";";
				ArrayList<String> idsReunioes = new ArrayList<>();
				for (Reuniao r : e.getReunioes())
					idsReunioes.add(r.getId() + "");
				String ids = String.join(",", idsReunioes);

				arquivo1.write(dados + ids + "\n");
			}
			for (Convidado c : convidados) {
				// System.out.println("gravando convidado: " + c);
				String dados = "CONVIDADO;" + c.getNome() + ";" + c.getEmail() + ";" + c.getInstituicao() + ";";
				ArrayList<String> idsReunioes = new ArrayList<>();
				for (Reuniao r : c.getReunioes())
					idsReunioes.add(r.getId() + "");
				String ids = String.join(",", idsReunioes);

				arquivo1.write(dados + ids + "\n");
			}
			arquivo1.close();
		} catch (IOException e) {
			throw new RuntimeException("problema na grava��o de participantes.csv");
		} catch (Exception ex) {
			throw ex;
		}

		try {
			arquivo2 = new FileWriter(new File(".\\reunioes.csv").getCanonicalPath());
			for (Reuniao r : getReunioes()) {
				// System.out.println("gravando reuniao: " + r.getId());
				arquivo2.write(r.getId() + ";" + r.getData() + ";" + r.getAssunto() + "\n");
			}
			arquivo2.close();
		} catch (IOException e) {
			throw new RuntimeException("problema na grava��o de reunioes.csv");
		} catch (Exception ex) {
			throw ex;
		}

	}
}
// classe
