package Classes;

public class Comment {
    private int id_livro;
    private String matricula_usuario;

    public String getMatricula_usuario() {
        return matricula_usuario;
    }

    public void setMatricula_usuario(String matricula_usuario) {
        this.matricula_usuario = matricula_usuario;
    }
    private String comentario;
    

 
    
    public Comment(int id_livro, String matricula_usuario, String comentario) {
        this.id_livro = id_livro;
        this.matricula_usuario = matricula_usuario;
        this.comentario = comentario;
    }

    public int getId_livro() {
        return id_livro;
    }
    public void setId_livro(int id_livro) {
        this.id_livro = id_livro;
    }
    public String getComentario() {
        return comentario;
    }
    public void setComentario(String comentario) {
        this.comentario = comentario;
    }
}
