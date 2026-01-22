package modelo;

public class Empregado extends Participante {

    private String setor;

    public Empregado(String nome, String email, String setor) {
        setNome(nome);
        setEmail(email);
        this.setor = setor;
    }

    public String getSetor() {
        return setor;
    }

    public void setSetor(String setor) {
        this.setor = setor;
    }
}