package Models;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;

public class Comments {
    
    public boolean createComment(String comentario) {
        try {
            FileWriter fwriter = new FileWriter("src/Data/Comentários", true);
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

    public void read(ArrayList<String> comentarios) {
        comentarios.clear();
        String comentario = "";
        try {
            FileReader fReader = new FileReader("src/Data/Comentários");
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
}
