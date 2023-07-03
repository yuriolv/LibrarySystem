package Models;

import java.io.*;
import java.util.ArrayList;

import Classes.RentBook;

public class Rents {  

    public boolean create(RentBook rent){
        try {
            FileWriter fWriter = new FileWriter("src/Data/Rents.txt", true);
            BufferedWriter bWriter = new BufferedWriter(fWriter);

            if(rent.getDateDevolution().equals("")){
                bWriter.write(rent.toString());
            }else {
                bWriter.write(rent.toString());
            }

            bWriter.newLine();
            
            bWriter.close();
            fWriter.close();
        
        } catch (Exception e) {
            return false;
    
        }
        return true;
    }

    public void read(ArrayList<RentBook> rents){
        rents.clear();
        RentBook rent;
        try{
            FileReader fReader = new FileReader("src/Data/Rents.txt");
            BufferedReader bReader = new BufferedReader(fReader);
            String linha = bReader.readLine();
            
            while(linha != null){
                String[] dados = linha.split("\t");
                rent = new RentBook(dados[0], dados[1], dados[2],dados[3]);
                rents.add(rent);
                linha = bReader.readLine();

            }
            fReader.close();
            bReader.close();
        }catch(Exception e){
           return;
        }
        

    }

    public boolean update(ArrayList<RentBook> rents){
        File file = new File("src/Data/Rents.txt");  

        if(file.delete()){
            if(createFile("Rents")){
                for (RentBook rent : rents) {
                    create(rent);                 
                }
                return true;
            }
        }
        return false; 

    }
    public boolean delete(int index, ArrayList<RentBook> rents){
        File file = new File("src/Data/Rents.txt");
        
        if(file.delete()){
            if(createFile("Rents")){
                rents.remove(index);

                for (RentBook rent : rents) {
                    create(rent);                 
                }
                return true;
            }
        }
        return false; 

    }
    
    public boolean createFile(String nome){
        
        File file = new File("src/Data/"+nome+".txt");
        
        try {
            return file.createNewFile();
        } catch (Exception e) {
            return false;
        }
    }

}


