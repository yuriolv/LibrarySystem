package Classes;

import DB.DataBase;
import Models.Publishers;
import java.util.ArrayList;
import java.util.Optional;

public class Book {
    private int ID;
    private String titulo;
    private String autor;
    private String assunto;
    private Integer qtdEstoque;
    private byte[] image;
    private String coleção;
    private int editora;

    public Book(String autor, String titulo, String assunto, int qtdEstoque ,String coleção , byte[] image, int editora){
        this.autor = autor;
        this.titulo = titulo;
        this.assunto = assunto;
        this.qtdEstoque = qtdEstoque;
        this.image = image;
        this.coleção = coleção;
        this.editora = editora;
    }
    public Book(int ID, String autor, String titulo, String assunto, int qtdEstoque ,String coleção , byte[] image, int editora){
        this.ID = ID;
        this.autor = autor;
        this.titulo = titulo;
        this.assunto = assunto;
        this.qtdEstoque = qtdEstoque;
        this.image = image;
        this.coleção = coleção;
        this.editora = editora;
    }
    
    public void setImage(byte[] image) {
        this.image = image;
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
    
    public int getID() {
        return ID;
    }
    
    public void setID(int iD) {
        ID = iD;
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

    public int getEditora() {        
        return editora;
    }
    
    public void setEditora(int editora) {
        this.editora = editora;
    }
    public String getEditoraNome(DataBase db) {        
        Publishers crud = new Publishers();
        ArrayList<String> conditions = new ArrayList<>();
         
        conditions.add(String.format("id_editora = "+ editora));

        ArrayList<Publisher> editoras = crud.read(db, Optional.of(conditions), Optional.of(""));
        
        if(editoras != null)
        return editoras.get(0).getNome();
        else
        return "Vazio";
    }
}

