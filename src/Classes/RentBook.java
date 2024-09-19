package Classes;

import DB.DataBase;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;

public class RentBook {
    private String matricula;
    private String tipo;
    
    public String getTipo() {
        return tipo;
    }

    private int id_livro;  
    private int Id;  
    
    private String dateRent;
    private String dateDevolution;
    
    
    public RentBook(String matricula, int id_livro, String tipo) {
        this.matricula = matricula;
        this.id_livro = id_livro;
        this.tipo = tipo;
        dateDevolution = null;
    }
    
    public RentBook(String matricula, int id_livro, String tipo, String dateRent, String dateDevolution) {
        this.matricula = matricula;
        this.id_livro = id_livro;
        this.tipo = tipo;
        this.dateRent = dateRent;
        this.dateDevolution = dateDevolution;
        
    }
    public RentBook(int ID, String matricula, int id_livro, String tipo, String dateRent, String dateDevolution) {
        this.Id = ID;
        this.matricula = matricula;
        this.id_livro = id_livro;
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
    
    public int getId_livro() {
        return id_livro;
    }
    
    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }
    
    public void setId_livro(int id_livro) {
        this.id_livro = id_livro;
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
    

    public double getMulta(){
        try {
            if(tipo.equals("Discente") ){
                DateTimeFormatter dateTimeFormatter =  DateTimeFormatter.ofPattern("dd/MM/yyyy");
                LocalDate dateForDevolution = LocalDate.parse( getDateDevolution() , dateTimeFormatter);
                LocalDate startDate = LocalDate.of(dateForDevolution.getYear(), dateForDevolution.getMonthValue(), dateForDevolution.getDayOfMonth());
                LocalDate endDate = LocalDate.now();

                int dias = Period.between(startDate, endDate).getDays();
                
                if(dias>0)
                    return dias*0.5;
                else
                    return 0;
                
            } else {
                DateTimeFormatter dateTimeFormatter =  DateTimeFormatter.ofPattern("dd/MM/yyyy");
                LocalDate dateForDevolution = LocalDate.parse( getDateDevolution() , dateTimeFormatter);
                LocalDate startDate = LocalDate.of(dateForDevolution.getYear(), dateForDevolution.getMonthValue(), dateForDevolution.getDayOfMonth());
                LocalDate endDate = LocalDate.now();

                int dias = Period.between(startDate, endDate).getDays();
                
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

    public String getTitulo(DataBase db) {
        try {
            ResultSet rs = db.requestSQL("SELECT titulo FROM livro WHERE id_livro = " + id_livro);
            rs.next();
            return rs.getString("titulo");
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }
    
    public String toString(){
        return matricula + "\t" + id_livro + "\t" + tipo + "\t" + dateRent  + "\t" + dateDevolution;
    }

    

    
}
