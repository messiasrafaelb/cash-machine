package controller.dto;

import domain.model.Bank;

public record BankResponse(
    Integer id,
    String name
) {
    public static BankResponse fromEntity(Bank bank) {
        if (bank == null) return null;
        return new BankResponse(bank.getId(), bank.getName());
    }
}