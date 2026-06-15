package domain.util;

import static common.Messages.MSG_UTIL_CLASS_EXCEPTION;

public final class CpfMask {

    private CpfMask() {
        throw new UnsupportedOperationException(MSG_UTIL_CLASS_EXCEPTION);
    }

    public static String removeMask(String cpf) {
        if (cpf == null) {
            return null;
        }
        return cpf.replaceAll("\\D", "");
    }

    public static String formatCpf(String cpfClean) {
        return "%s.%s.%s-%s".formatted(
            cpfClean.substring(0, 3),
            cpfClean.substring(3, 6),
            cpfClean.substring(6, 9),
            cpfClean.substring(9)
        );
    }
}