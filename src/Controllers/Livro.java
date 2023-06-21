package Controllers;

public class Livro {
    private String titulo;
    private String autor;
    private String assunto;
    private int qtdEstoque;

    public Livro(String titulo, String autor, String assunto, int qtdEstoque){
        this.titulo = titulo;
        this.autor = autor;
        this.assunto = assunto;
        this.qtdEstoque = qtdEstoque;

    }

    public String toString(){
        return titulo+"\t"+autor+"\t"+assunto+"\t"+qtdEstoque;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getAssunto() {
        return assunto;
    }

    public void setAssunto(String assunto) {
        this.assunto = assunto;
    }

    public int getQtdEstoque() {
        return qtdEstoque;
    }

    public void setQtdEstoque(int qtdEstoque) {
        this.qtdEstoque = qtdEstoque;
    }

    
}
