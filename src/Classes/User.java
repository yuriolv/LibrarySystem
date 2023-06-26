package Classes;

public class User {
    private String matricula;
    private String nome;
    private String tipo;
    private String senha;
    
    
    
    public User(String matricula, String nome, String tipo, String senha) {
        this.matricula = matricula;
        this.nome = nome;
        this.tipo = tipo;
        this.senha = senha;
    }
    public User(String matricula, String nome, String tipo) {
        this.matricula = matricula;
        this.nome = nome;
        this.tipo = tipo;
    }

    public String toString() {
        return matricula + "\t" + nome + "\t" + tipo + "\t" + senha;
    }
    
    public String getMatricula() {
        return matricula;
    }
    public void setMatricula(String matricula) {
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
