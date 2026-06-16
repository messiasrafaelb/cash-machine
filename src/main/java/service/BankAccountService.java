package service;

import controller.dto.BankAccountRequest;
import controller.dto.BankAccountResponse;
import domain.model.BankAccount;
import domain.repository.BankAccountRepository;
import security.PasswordEncoder;

import static common.Numbers.ZERO;

import java.util.Random;

public class BankAccountService {

    private static final String MSG_EMAIL_ALREADY_REGISTERED_EXCEPTION = "Este e-mail já está cadastrado neste banco.";
    private static final String MSG_ACCOUNT_TARGET_NOT_FOUND_EXCEPTION = "Conta de destino não encontrada.";
    private static final String MSG_BALANCE_INSUFFICIENT_EXCEPTION = "Saldo insuficiente.";
    private static final String MSG_AMOUNT_EXCEPTION = "O valor deve ser positivo.";

    private final BankAccountRepository accountRepository = new BankAccountRepository();
    private final Random random = new Random();

    public BankAccountResponse login(String email, String password, Integer bankId) {
        var account = accountRepository.findByEmailAndBank(email, bankId);

        if (account != null && PasswordEncoder.matches(password, account.getPassword())) {
            return BankAccountResponse.fromEntity(account);
        }

        return null;
    }

    public BankAccountResponse createAccount(BankAccountRequest request, Integer userId, Integer bankId) {
        if (accountRepository.findByEmailAndBank(request.email(), bankId) != null) {
            throw new IllegalArgumentException(MSG_EMAIL_ALREADY_REGISTERED_EXCEPTION);
        }

        var agency = String.format("%04d", random.nextInt(10000));
        var number = String.format("%05d-%d", random.nextInt(100000), random.nextInt(10));
        var account = request.toEntity(number, agency, userId, bankId);

        account.setPassword(PasswordEncoder.encode(request.password()));

        accountRepository.save(account);

        return BankAccountResponse.fromEntity(account);
    }

    public BankAccountResponse deposit(Integer accountId, Double amount) {
        validateAmount(amount);

        var account = accountRepository.findById(accountId);

        validateIfAccountExists(account, accountId);

        account.setBalance(account.getBalance() + amount);
        accountRepository.update(accountId, account);

        return BankAccountResponse.fromEntity(account);
    }

    public BankAccountResponse withdraw(Integer accountId, Double amount) {
        validateAmount(amount);

        var account = accountRepository.findById(accountId);

        validateIfAccountExists(account, accountId);
        validateBalance(account, amount);

        account.setBalance(account.getBalance() - amount);
        accountRepository.update(accountId, account);

        return BankAccountResponse.fromEntity(account);
    }

    public BankAccountResponse transfer(Integer originAccountId, String targetAccountNumber, Double amount) {
        validateAmount(amount);

        var origin = accountRepository.findById(originAccountId);

        validateIfAccountExists(origin, originAccountId);
        validateBalance(origin, amount);

        var target = accountRepository.getAllAccounts().stream()
                .filter(acc -> acc.getNumber().equals(targetAccountNumber))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(MSG_ACCOUNT_TARGET_NOT_FOUND_EXCEPTION));

        origin.setBalance(origin.getBalance() - amount);
        target.setBalance(target.getBalance() + amount);

        accountRepository.update(origin.getId(), origin);
        accountRepository.update(target.getId(), target);

        return BankAccountResponse.fromEntity(origin);
    }

    private static void validateAmount(Double amount) {
        if (amount <= ZERO) {
            throw new IllegalArgumentException(MSG_AMOUNT_EXCEPTION);
        }
    }

    private static void validateIfAccountExists(BankAccount account, Integer accountId) {
        if (account == null) {
            throw new IllegalArgumentException(
                String.format(BankAccountRepository.MSG_ACCOUNT_NOT_FOUND_EXCEPTION, accountId));
        }
    }

    private static void validateBalance(BankAccount origin, Double amount) {
        if (origin.getBalance() < amount) {
            throw new IllegalStateException(MSG_BALANCE_INSUFFICIENT_EXCEPTION);
        }
    }
}