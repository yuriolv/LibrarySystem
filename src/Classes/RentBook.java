package Classes;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class RentBook {
    private String matricula;
    private String tipo;
    
    public String getTipo() {
        return tipo;
    }

    private String titulo;
    private String dateRent;
    private String dateDevolution;
    
    
    public RentBook(String matricula, String titulo, String tipo) {
        this.matricula = matricula;
        this.titulo = titulo;
        this.tipo = tipo;
        dateDevolution = null;
    }
    
    public RentBook(String matricula, String titulo, String tipo, String dateRent, String dateDevolution) {
        this.matricula = matricula;
        this.titulo = titulo;
        this.tipo = tipo;
        this.dateRent = dateRent;
        this.dateDevolution = dateDevolution;
        
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
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
    
    public String setDateForDevolution() {
        DateTimeFormatter dateTimeFormatter =  DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate dateForDevolution = LocalDate.parse( getDateRent(), dateTimeFormatter);
        return dateForDevolution.plusDays(7).format(dateTimeFormatter).toString();
    }

    public double getMulta(){
        try {
            if(tipo.equals("Discente") ){
                DateTimeFormatter dateTimeFormatter =  DateTimeFormatter.ofPattern("dd/MM/yyyy");
                LocalDate dateForDevolution = LocalDate.parse( dateDevolution , dateTimeFormatter);
                LocalDate dateOfRent = LocalDate.parse(getDateRent(), dateTimeFormatter);
                int dias = dateForDevolution.getDayOfMonth() - dateOfRent.plusDays(7).getDayOfMonth();
                
                if(dias>0)
                    return dias*0.5;
                else
                    return 0;
                
            } else {
                DateTimeFormatter dateTimeFormatter =  DateTimeFormatter.ofPattern("dd/MM/yyyy");
                LocalDate dateForDevolution = LocalDate.parse( setDateForDevolution() , dateTimeFormatter);
                LocalDate dateOfRent = LocalDate.parse(getDateRent(), dateTimeFormatter);
                int dias = dateForDevolution.getDayOfMonth() - dateOfRent.plusDays(7).getDayOfMonth();
                
                if(dias>0)
                    return dias*0.8;
                else
                    return 0;
            } 
            
        } catch (Exception e) {
            System.out.println(e);
            return 0;
        }
     
    }
    
    public String toString(){
        return matricula + "\t" + titulo + "\t" + tipo + "\t" + dateRent  + "\t" + dateDevolution;
    }

    

    
}
