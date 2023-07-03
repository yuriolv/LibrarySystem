package Classes;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class RentBook {
    private String matricula;
    private String titulo;
    private String dateRent;
    private String dateDevolution;
    //não colocar devolução


    public RentBook(String matricula, String titulo) {
        this.matricula = matricula;
        this.titulo = titulo;
    }

    public RentBook(String matricula, String titulo, String dateRent, String dateDevolution) {
        this.matricula = matricula;
        this.titulo = titulo;
        this.dateRent = dateRent;
        this.dateDevolution = dateDevolution;

    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDateRent() {
        return dateRent;
    }

    public void setDateRent() {
        DateTimeFormatter dateTimeFormatter =  DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate today = LocalDate.now();
        dateRent = today.format(dateTimeFormatter);
    }

    public String getDateDevolution() {
        return dateDevolution;
    }

    public void setDateDevolution() {
        DateTimeFormatter dateTimeFormatter =  DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate today = LocalDate.now();
        dateDevolution = today.format(dateTimeFormatter);
    }

    public String toStringRent(){
        return matricula + "\t" + titulo + "\t" + dateRent;
    }

    public String toStringDevolution(){
        return  matricula + "\t" + titulo + "\t" + dateRent + "\t" + dateDevolution;
    }

    
}
