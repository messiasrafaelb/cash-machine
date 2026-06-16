package service;

import controller.dto.BankResponse;
import domain.repository.BankRepository;
import java.util.List;
import java.util.stream.Collectors;

public class BankService {

    private final BankRepository bankRepository = new BankRepository();

    public List<BankResponse> listAllBanks() {
        return bankRepository.getAllBanks().stream()
                .map(BankResponse::fromEntity)
                .collect(Collectors.toList());
    }

    public BankResponse findBankById(Integer id) {
        var bank = bankRepository.findById(id);
        return bank != null ? BankResponse.fromEntity(bank) : null;
    }
}