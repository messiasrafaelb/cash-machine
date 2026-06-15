package common;

import static common.Messages.MSG_UTIL_CLASS_EXCEPTION;

public final class CsvPaths {

    private CsvPaths() {
        throw new UnsupportedOperationException(MSG_UTIL_CLASS_EXCEPTION);
    }

    public static final String BANK_ACCOUNT_SEQUENCE_PATH = "data\\sequence\\bank_account_sequence.csv";
    public static final String BANK_SEQUENCE_PATH = "data\\sequence\\bank_sequence.csv";
    public static final String USER_SEQUENCE_PATH = "data\\sequence\\user_sequence.csv";
    public static final String BANK_ACCOUNT_PATH = "data\\sequence\\bank_account.csv";
    public static final String BANK_PATH = "data\\sequence\\bank.csv";
    public static final String USER_PATH = "data\\sequence\\user.csv";

}
