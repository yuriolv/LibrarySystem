package Models;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;

public class Comments {
    
    public boolean createComment(String comentario, String fileName) {
        try {
            FileWriter fwriter = new FileWriter("src/Data/Comentários/"+fileName+".txt", true);
            BufferedWriter bWriter = new BufferedWriter(fwriter);

            bWriter.write(comentario);
            bWriter.newLine();

            bWriter.close();
            fwriter.close();
        } catch(Exception e) {
            return false;
        }

        return true;
    }

    public void read(ArrayList<String> comentarios, String fileName) {
        comentarios.clear();
        String comentario = "";
        try {
            FileReader fReader = new FileReader("src/Data/Comentários/"+fileName+".txt");
            BufferedReader bReader = new BufferedReader(fReader);

            String linha = bReader.readLine();

            while(linha != null) {
                comentarios.add(comentario);
                linha = bReader.readLine();
            }

            fReader.close();
            bReader.close();
        } catch(Exception e) {
            return;
        }
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
