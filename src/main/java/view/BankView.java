package view;

import controller.BankController;
import controller.dto.BankResponse;
import java.util.List;
import java.util.Scanner;

public class BankView {

    private final BankController bankController = new BankController();

    public BankResponse selectBank(Scanner scanner) {
        while (true) {
            System.out.println("\n========= CAIXA ELETRÔNICO MULTIBANCO =========");
            System.out.println("Selecione o banco desejado para operar:");
            
            List<BankResponse> banks = bankController.listAllBanks();
            
            if (banks.isEmpty()) {
                System.out.println("Nenhum banco cadastrado no sistema.");
                return null;
            }

            for (BankResponse bank : banks) {
                System.out.printf("[%d] - %s%n", bank.id(), bank.name());
            }
            System.out.println("[0] - Sair do Sistema");
            System.out.print("Opção: ");

            try {
                int choice = Integer.parseInt(scanner.nextLine());
                if (choice == 0) {
                    return null;
                }

                BankResponse selected = bankController.findBankById(choice);
                if (selected != null) {
                    return selected;
                }
                System.out.println("Opção inválida! Selecione um banco da lista.");
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida! Digite apenas números.");
            }
        }
    }
}