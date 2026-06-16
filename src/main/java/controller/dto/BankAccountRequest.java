package controller.dto;

import domain.model.BankAccount;

public record BankAccountRequest(
    String email,
    String password
) {
    public BankAccount toEntity(String generatedNumber, String generatedAgency, Integer fkUser, Integer fkBank) {
        return new BankAccount(
            null, 
            this.email, 
            generatedNumber, 
            "ACTIVE", 
            generatedAgency, 
            0.0,
            this.password,
            fkUser, 
            fkBank
        );
    }
}