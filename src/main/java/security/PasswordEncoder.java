package security;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordEncoder {

    private static final String MSG_PASSWORD_CANNOT_BE_NULL_OR_EMPTY = "A senha não pode ser nula ou vazia.";

    public static String encode(String plainPassword) {
        if (plainPassword == null || plainPassword.isBlank()) {
            throw new IllegalArgumentException(MSG_PASSWORD_CANNOT_BE_NULL_OR_EMPTY);
        }

        return BCrypt.hashpw(plainPassword, BCrypt.gensalt());
    }

    public static boolean matches(String plainPassword, String hashedPassword) {
        if (plainPassword == null || hashedPassword == null) {
            return false;
        }
        try {
            return BCrypt.checkpw(plainPassword, hashedPassword);
        } catch (Exception ex) {
            return false;
        }
    }
}
