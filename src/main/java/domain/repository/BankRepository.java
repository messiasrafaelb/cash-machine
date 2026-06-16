package domain.repository;

import com.opencsv.bean.CsvToBeanBuilder;
import domain.model.Bank;

import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static common.CsvPaths.*;
import static common.Messages.*;

public class BankRepository {

    public Bank findById(Integer id) {
        return getAllBanks().stream()
                .filter(bank -> bank.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public List<Bank> getAllBanks() {
        if (!Files.exists(Paths.get(BANK_PATH))) {
            return new ArrayList<>();
        }

        try (var reader = new FileReader(BANK_PATH)) {
            return new CsvToBeanBuilder<Bank>(reader)
                    .withType(Bank.class)
                    .withThrowExceptions(false)
                    .build()
                    .parse();
        } catch (IOException ex) {
            throw new RuntimeException(MSG_CSV_READ_EXCEPTION, ex);
        }
    }
}
