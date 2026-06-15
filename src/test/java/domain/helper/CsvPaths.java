package domain.helper;

import static common.Messages.MSG_UTIL_CLASS_EXCEPTION;

public final class CsvPaths {

    private CsvPaths() {
        throw new UnsupportedOperationException(MSG_UTIL_CLASS_EXCEPTION);
    }

    public static final String USER_SEQUENCE = "src\\test\\java\\data\\sequence\\user_sequence_test.csv";
}
