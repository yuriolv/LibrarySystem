package Models;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;

import Classes.User;

public class Users {
    

    public boolean create(User user) {
        try {
            FileWriter fWriter = new FileWriter("src/Data/Users.txt", true);
            BufferedWriter bWriter = new BufferedWriter(fWriter);

            bWriter.write(user.toString());
            bWriter.newLine();

            bWriter.close();
            fWriter.close();
        } catch(Exception e) {
            return false;
        }

        return true;
    }

    public void read(ArrayList<User> users) {
        users.clear();
        User user;

        try {
            FileReader fReader = new FileReader("src/Data/Users.txt");
            BufferedReader bReader = new BufferedReader(fReader);
            String linha = bReader.readLine();
            while(linha != null) {
                String dados[] = linha.split("\t");
                user = new User(dados[0], dados[1], dados[2], dados[3]);

                users.add(user);
                linha = bReader.readLine();
            }

            bReader.close();
            fReader.close();
        } catch(Exception e) {
            System.out.println("erro");
            return;
        }
    }

    public boolean update(ArrayList<User> users) {
        File file = new File("src/Data/Users.txt");

        if(file.delete()) {
            if(createFile("Users")) {
                for(User user: users) {
                    create(user);
                    return true;
                }
            }
        }

        return false;
    }


    public boolean delete(int index, ArrayList<User> users) {
        File file = new File("src/Data/Users.txt");

        if(file.delete()) {
            if(createFile("Users")) {
                users.remove(index);
                
                for(User user: users) {
                    create(user);
                    return true;
                }
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
