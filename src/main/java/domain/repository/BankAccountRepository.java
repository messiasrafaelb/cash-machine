package domain.repository;

import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import domain.interfaces.RepositoryCrud;
import domain.interfaces.RepositorySequence;
import domain.model.BankAccount;
import domain.repository.sequence.BankAccountSequenceRepository;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static common.CsvPaths.*;
import static common.Messages.*;
import static common.Numbers.*;

public class BankAccountRepository implements RepositoryCrud<BankAccount> {

    public static final String MSG_ACCOUNT_NOT_FOUND_EXCEPTION = "Conta Bancária com ID %d não encontrada.";

    private final RepositorySequence sequenceRepository = new BankAccountSequenceRepository();

    @Override
    public BankAccount findById(Integer id) {
        return getAllAccounts().stream()
                .filter(account -> account.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public BankAccount findByEmailAndBank(String email, Integer bankId) {
        return getAllAccounts().stream()
                .filter(account -> account.getEmail().equalsIgnoreCase(email) && account.getBank().equals(bankId))
                .findFirst()
                .orElse(null);
    }

    @Override
    public void save(BankAccount account) {
        var accounts = getAllAccounts();
        var id = sequenceRepository.nextId(BANK_ACCOUNT_SEQUENCE_PATH);

        account.setId(id);
        accounts.add(account);

        saveAllAccounts(accounts);
    }

    @Override
    public void update(Integer id, BankAccount account) {
        var accounts = getAllAccounts();
        boolean found = false;

        for (int i = ZERO; i < accounts.size(); i++) {
            if (accounts.get(i).getId().equals(id)) {
                accounts.set(i, account);
                found = true;
                break;
            }
        }

        if (found) {
            saveAllAccounts(accounts);
        } else {
            throw new IllegalArgumentException(
                    String.format(MSG_ACCOUNT_NOT_FOUND_EXCEPTION, id));
        }
    }

    public List<BankAccount> getAllAccounts() {
        if (!Files.exists(Paths.get(BANK_ACCOUNT_PATH))) {
            return new ArrayList<>();
        }

        try (var reader = new FileReader(BANK_ACCOUNT_PATH)) {
            return new CsvToBeanBuilder<BankAccount>(reader)
                    .withType(BankAccount.class)
                    .withThrowExceptions(false)
                    .build()
                    .parse();
        } catch (IOException ex) {
            throw new RuntimeException(MSG_CSV_READ_EXCEPTION, ex);
        }
    }

    private void saveAllAccounts(List<BankAccount> accounts) {
        try (var writer = new FileWriter(BANK_ACCOUNT_PATH)) {
            var beanToCsv = new StatefulBeanToCsvBuilder<BankAccount>(writer)
                    .withApplyQuotesToAll(false)
                    .build();

            beanToCsv.write(accounts);
        } catch (Exception ex) {
            throw new RuntimeException(MSG_CSV_SAVE_EXCEPTION, ex);
        }
    }
}
