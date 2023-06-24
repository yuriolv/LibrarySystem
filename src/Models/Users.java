package Models;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;

import Classes.User;

public class Users {
    

    public boolean createUser(User user) {
        try {
            FileWriter fWriter = new FileWriter("src/Data/Users.txt", true);
            BufferedWriter bWriter = new BufferedWriter(fWriter);

            bWriter.write(user.toString());
            bWriter.newLine();

            fWriter.close();
            bWriter.close();
        } catch(Exception e) {
            return false;
        }

        return true;
    }

    public void readUser(ArrayList<User> users) {
        users.clear();
        User user;

        try {
            FileReader fReader = new FileReader("src/Data/Users.txt");
            BufferedReader bReader = new BufferedReader(fReader);
            String linha;

            linha = bReader.readLine();
            while(linha != null) {
                String dados[] = linha.split("\t");
                user = new User(Integer.parseInt(dados[0]), dados[1], dados[3], dados[4]);

                users.add(user);
                linha = bReader.readLine();
            }

            fReader.close();
            bReader.close();
        } catch(Exception e) {
            return;
        }
    }

    public boolean updateUser(ArrayList<User> users) {
        File file = new File("src/Data/Users.txt");

        if(file.delete()) {
            if(createFile("Users")) {
                for(User user: users) {
                    createUser(user);
                    return true;
                }
            }
        }

        return false;
    }


    public boolean deleteUser(int index, ArrayList<User> users) {
        File file = new File("src/Data/Users.txt");

        if(file.delete()) {
            if(createFile("Users")) {
                users.remove(index);
                
                for(User user: users) {
                    createUser(user);
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
