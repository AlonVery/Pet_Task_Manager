package infra.security;

import domain.repository.PasswordEncoder;
import org.mindrot.jbcrypt.BCrypt;

public class PasswordEncoderBCrypt implements PasswordEncoder {

    public String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt(12));
    }

    public boolean checkPassword(String rawPasswd, String hashedPasswd) {
        String x = hashPassword(rawPasswd);
        return x.equals(hashedPasswd);
    }


}
