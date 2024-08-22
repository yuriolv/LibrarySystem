package Classes;

public class Book {
    private String titulo;
    private String autor;
    private String assunto;
    private Integer qtdEstoque;
    private byte[] image;
    private String coleção;

    public Book(String autor, String titulo, String assunto, int qtdEstoque ,String coleção , byte[] image){
        this.autor = autor;
        this.titulo = titulo;
        this.assunto = assunto;
        this.qtdEstoque = qtdEstoque;
        this.image = image;
        this.coleção = coleção;
    }

   

    public String toString(){
        return autor+"\t"+titulo+"\t"+assunto+"\t"+qtdEstoque+"\t"+coleção+"\t"+image;
    }

    public String getTitulo() {
        return titulo;
    }

    public byte[] getImage(){
        return image;
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
    
    public String getColeção() {
        return coleção;
    }

    public void setColeção(String coleção) {
        this.coleção = coleção;
    }
}
