package Classes;

public class User {
    private int matricula;
    private String nome;
    private String tipo;
    private String senha;
    
    
    
    public User(int matricula, String nome, String tipo, String senha) {
        this.matricula = matricula;
        this.nome = nome;
        this.tipo = tipo;
        this.senha = senha;
    }
    
    public int getMatricula() {
        return matricula;
    }
    public void setMatricula(int matricula) {
        this.matricula = matricula;
    }
    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public String getTipo() {
        return tipo;
    }
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
    public String getSenha() {
        return senha;
    }
    public void setSenha(String senha) {
        this.senha = senha;
    }
}
