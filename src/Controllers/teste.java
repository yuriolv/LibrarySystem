package Controllers;
import Utils.HashPass;

public class teste {
    //private static byte[] msgDecifrada;

    public static void main(String[] args) {
        System.out.println(HashPass.generateHash("Yuri de Oliveira Magalhães@Discente"));
        System.out.println(HashPass.generateHash("2"));
    }
}
