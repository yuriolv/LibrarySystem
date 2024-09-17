package Utils;
import org.mindrot.jbcrypt.*;

public class HashPass {
    public static String generateHash(String password){
        String hashedPass = BCrypt.hashpw(password, BCrypt.gensalt(12));
        return hashedPass;
    }

    public static boolean verifyPass(String hashedPass, String password){
        boolean result = BCrypt.checkpw(password, hashedPass);

        return result;
    }
}
