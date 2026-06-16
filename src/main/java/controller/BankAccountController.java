package controller;

import controller.dto.BankAccountRequest;
import controller.dto.BankAccountResponse;
import service.BankAccountService;

public class BankAccountController {

    private final BankAccountService bankAccountService = new BankAccountService();

    public BankAccountResponse login(String email, String password, Integer bankId) {
        return bankAccountService.login(email, password, bankId);
    }

    public BankAccountResponse createAccount(BankAccountRequest request, Integer userId, Integer bankId) {
        try {
            return bankAccountService.createAccount(request, userId, bankId);
        } catch (IllegalArgumentException e) {
            System.err.println("\nErro: " + e.getMessage());
            return null;
        }
    }

    public BankAccountResponse deposit(Integer accountId, Double amount) {
        try {
            return bankAccountService.deposit(accountId, amount);
        } catch (IllegalArgumentException e) {
            System.err.println("\nErro no depósito: " + e.getMessage());
            return null;
        }
    }

    public BankAccountResponse withdraw(Integer accountId, Double amount) {
        try {
            return bankAccountService.withdraw(accountId, amount);
        } catch (IllegalArgumentException | IllegalStateException e) {
            System.err.println("\nErro no saque: " + e.getMessage());
            return null;
        }
    }

    public BankAccountResponse transfer(Integer originAccountId, String targetAccountNumber, Double amount) {
        try {
            return bankAccountService.transfer(originAccountId, targetAccountNumber, amount);
        } catch (IllegalArgumentException | IllegalStateException e) {
            System.err.println("\nErro na transferência: " + e.getMessage());
            return null;
        }
    }
}