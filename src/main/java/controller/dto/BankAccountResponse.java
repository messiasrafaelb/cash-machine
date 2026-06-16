package controller.dto;

import domain.model.BankAccount;

public record BankAccountResponse(
    Integer id,
    String email,
    String number,
    String status,
    String agency,
    Double balance,
    Integer fkUser,
    Integer fkBank
) {
    public static BankAccountResponse fromEntity(BankAccount account) {
        if (account == null) return null;
        return new BankAccountResponse(
            account.getId(),
            account.getEmail(),
            account.getNumber(),
            account.getStatus(),
            account.getAgency(),
            account.getBalance(),
            account.getUser(),
            account.getBank()
        );
    }
}