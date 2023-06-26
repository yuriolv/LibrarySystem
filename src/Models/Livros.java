package Models;

import java.io.*;

import Classes.Livro;
import javafx.collections.ObservableList;

public class Livros {
    


    public boolean create(Livro livro){
        try {
            FileWriter fWriter = new FileWriter("src/Data/Acervo.txt", true);
            BufferedWriter bWriter = new BufferedWriter(fWriter);

            bWriter.write(livro.toString());
            bWriter.newLine();
            
            bWriter.close();
            fWriter.close();
        
        } catch (Exception e) {
            return false;
    
        }
        return true;
    }

    public void read(ObservableList<Livro> livros){
        livros.clear();
        Livro livro;
    
        try{
            FileReader fReader = new FileReader("src/Data/Acervo.txt");
            BufferedReader bReader = new BufferedReader(fReader);
            String linha = bReader.readLine();
            
            while(linha != null){
                String[] dados = linha.split("\t");
                livro = new Livro(dados[0], dados[1], dados[2], Integer.parseInt(dados[3]));
                livros.add(livro);
                linha = bReader.readLine();

            }
            fReader.close();
            bReader.close();
        }catch(Exception e){
           return;
        }
        

    }

    public boolean update(ObservableList<Livro> livros){
        File file = new File("src/Data/Acervo.txt");  

        if(file.delete()){
            if(createFile("Acervo")){
                for (Livro livro : livros) {
                    create(livro);                 
                }
                return true;
            }
        }
        return false; 

    }
    public boolean delete(int index, ObservableList<Livro> livros){
        File file = new File("src/Data/Acervo.txt");
        
        if(file.delete()){
            if(createFile("Acervo")){
                livros.remove(index);

                for (Livro livro : livros) {
                    create(livro);                 
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



