package service;

import controller.dto.BankAccountRequest;
import controller.dto.BankAccountResponse;
import domain.model.BankAccount;
import domain.repository.BankAccountRepository;
import security.PasswordEncoder;

import java.util.Random;

public class BankAccountService {

    private final BankAccountRepository accountRepository = new BankAccountRepository();
    private final Random random = new Random();

    public BankAccountResponse login(String email, String password, Integer bankId) {
        BankAccount account = accountRepository.findByEmailAndBank(email, bankId);

        if (account != null && PasswordEncoder.matches(password, account.getPassword())) {
            return BankAccountResponse.fromEntity(account);
        }

        return null;
    }

    public BankAccountResponse createAccount(BankAccountRequest request, Integer userId, Integer bankId) {
        if (accountRepository.findByEmailAndBank(request.email(), bankId) != null) {
            throw new IllegalArgumentException("Este e-mail já está cadastrado neste banco.");
        }

        String agency = String.format("%04d", random.nextInt(10000));
        String number = String.format("%05d-%d", random.nextInt(100000), random.nextInt(10));

        BankAccount account = request.toEntity(number, agency, userId, bankId);

        account.setPassword(PasswordEncoder.encode(request.password()));

        accountRepository.save(account);

        return BankAccountResponse.fromEntity(account);
    }

    public BankAccountResponse deposit(Integer accountId, Double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("O valor do depósito deve ser positivo.");
        }

        BankAccount account = accountRepository.findById(accountId);
        if (account == null) {
            throw new IllegalArgumentException("Conta não encontrada.");
        }

        account.setBalance(account.getBalance() + amount);
        accountRepository.update(accountId, account);

        return BankAccountResponse.fromEntity(account);
    }

    public BankAccountResponse withdraw(Integer accountId, Double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("O valor do saque deve ser positivo.");
        }

        BankAccount account = accountRepository.findById(accountId);

        if (account == null) {
            throw new IllegalArgumentException("Conta não encontrada.");
        }

        if (account.getBalance() < amount) {
            throw new IllegalStateException("Saldo insuficiente.");
        }

        account.setBalance(account.getBalance() - amount);
        accountRepository.update(accountId, account);

        return BankAccountResponse.fromEntity(account);
    }

    public BankAccountResponse transfer(Integer originAccountId, String targetAccountNumber, Double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("O valor da transferência deve ser positivo.");
        }

        BankAccount origin = accountRepository.findById(originAccountId);

        if (origin == null) {
            throw new IllegalArgumentException("Conta de origem não encontrada.");
        }

        if (origin.getBalance() < amount) {
            throw new IllegalStateException("Saldo insuficiente.");
        }

        BankAccount target = accountRepository.getAllAccounts().stream()
                .filter(acc -> acc.getNumber().equals(targetAccountNumber))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Conta de destino não encontrada."));

        origin.setBalance(origin.getBalance() - amount);
        target.setBalance(target.getBalance() + amount);

        accountRepository.update(origin.getId(), origin);
        accountRepository.update(target.getId(), target);

        return BankAccountResponse.fromEntity(origin);
    }
}