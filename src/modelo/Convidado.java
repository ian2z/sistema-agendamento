package modelo;

public class Convidado extends Participante {

    private String instituicao;

    public Convidado(String nome, String email, String instituicao) {
        setNome(nome);
        setEmail(email);
        this.instituicao = instituicao;
    }

    public String getInstituicao() {
        return instituicao;
    }

    public void setInstituicao(String instituicao) {
        this.instituicao = instituicao;
    }
}