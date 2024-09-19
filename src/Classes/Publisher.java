package Classes;

public class Publisher {
    private int id;
    private String cnpj;
    private String nome;
    private String telefone;
    
    
    
    public Publisher(String nome, String cnpj, String telefone) {
        this.nome = nome;
        this.cnpj = cnpj;
        this.telefone = telefone;
    }
    public Publisher(int id, String nome, String cnpj, String telefone) {
        this.id = id;
        this.nome = nome;
        this.cnpj = cnpj;
        this.telefone = telefone;
    }

    @Override
    public String toString() {
        return getNome();
    }
    
    public int getId() {
        return id;
    }
    public void setNome(int id) {
        this.id = id;
    }
    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public String getCnpj() {
        return cnpj;
    }
    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }
    public String getTelefone() {
        return telefone;
    }
    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }
}
