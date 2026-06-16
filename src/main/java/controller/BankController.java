package controller;

import controller.dto.BankResponse;
import service.BankService;
import java.util.List;

public class BankController {

    private final BankService bankService = new BankService();

    public List<BankResponse> listAllBanks() {
        return bankService.listAllBanks();
    }

    public BankResponse findBankById(Integer id) {
        return bankService.findBankById(id);
    }
}