import java.util.Scanner;

import controller.dto.BankAccountResponse;
import controller.dto.BankResponse;
import view.BankAccountView;
import view.BankView;

public class CashMachineApplication {
    public static void main(String[] args) {
        BankView bankView = new BankView();
        BankAccountView accountView = new BankAccountView();

        try (Scanner scanner = new Scanner(System.in)) {
            boolean running = true;

            while (running) {
                BankResponse selectedBank = bankView.selectBank(scanner);

                if (selectedBank == null) {
                    System.out.println("\nObrigado por utilizar o Caixa Eletrônico Multibanco. Até mais!");
                    running = false;
                    continue;
                }

                boolean backToBankSelection = false;

                while (!backToBankSelection) {
                    BankAccountResponse loggedAccount = accountView.showAuthMenu(scanner, selectedBank);

                    if (loggedAccount == null) {
                        backToBankSelection = true;
                    } else {
                        accountView.showTransactionMenu(scanner, loggedAccount);
                        backToBankSelection = true;
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("\nOcorreu um erro inesperado no sistema: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
