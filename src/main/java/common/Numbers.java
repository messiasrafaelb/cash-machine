package common;

import static common.Messages.MSG_UTIL_CLASS_EXCEPTION;

public final class Numbers {
    
    private Numbers() {
        throw new UnsupportedOperationException(MSG_UTIL_CLASS_EXCEPTION);
    }

    public static final int ONE = 1;
    public static final int ZERO = 0;
}
