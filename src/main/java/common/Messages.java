package common;

public final class Messages {
    
    private Messages() {
        throw new UnsupportedOperationException(MSG_UTIL_CLASS_EXCEPTION);
    }

    public static final String MSG_UTIL_CLASS_EXCEPTION = "Esta classe utilitária não pode ser instanciada.";
    public static final String MSG_ID_EXCEPTION = "Erro ao obter id de %s.";
    public static final String MSG_CSV_SAVE_EXCEPTION = "Erro ao salvar dados no CSV";
    public static final String MSG_CSV_READ_EXCEPTION = "Erro ao ler o arquivo CSV";
    public static final String MSG_USER_NOT_FOUND_EXCEPTION = "Usuário com ID %d não encontrado.";
    public static final String MSG_ACCOUNT_NOT_FOUND_EXCEPTION = "Conta Bancária com ID %d não encontrada.";
}
