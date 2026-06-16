package view;

import controller.BankAccountController;
import controller.dto.BankAccountRequest;
import controller.dto.BankAccountResponse;
import controller.dto.BankResponse;
import controller.dto.UserResponse;
import java.util.Scanner;

public class BankAccountView {

    private final BankAccountController accountController = new BankAccountController();
    private final UserView userView = new UserView();

    public BankAccountResponse showAuthMenu(Scanner scanner, BankResponse selectedBank) {
        while (true) {
            System.out.printf("%n========= %s =========%n", selectedBank.name().toUpperCase());
            System.out.println("[1] - Realizar Login");
            System.out.println("[2] - Criar Nova Conta");
            System.out.println("[3] - Voltar ao Menu Anterior (Trocar de Banco)");
            System.out.print("Opção: ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1" -> {
                    BankAccountResponse account = loginFlow(scanner, selectedBank.id());
                    if (account != null) return account;
                }
                case "2" -> {
                    BankAccountResponse account = registerFlow(scanner, selectedBank.id());
                    if (account != null) return account;
                }
                case "3" -> {
                    return null;
                }
                default -> System.out.println("Opção invalida.");
            }
        }
    }

    private BankAccountResponse loginFlow(Scanner scanner, Integer bankId) {
        System.out.println("\n--- LOGIN ---");
        System.out.print("E-mail da Conta: ");
        String email = scanner.nextLine();
        System.out.print("Senha: ");
        String password = scanner.nextLine();

        BankAccountResponse account = accountController.login(email, password, bankId);
        if (account == null) {
            System.out.println("Credenciais inválidas para este banco!");
        } else {
            System.out.println("Login efetuado com sucesso!");
        }
        return account;
    }

    private BankAccountResponse registerFlow(Scanner scanner, Integer bankId) {
        UserResponse user = userView.registerNewUser(scanner);
        if (user == null) return null;

        System.out.println("\n--- CREDENCIAIS DA CONTA BANCÁRIA ---");
        System.out.print("Defina o e-mail de acesso da conta: ");
        String email = scanner.nextLine();
        System.out.print("Defina a senha da conta: ");
        String password = scanner.nextLine();

        BankAccountRequest request = new BankAccountRequest(email, password);
        BankAccountResponse newAccount = accountController.createAccount(request, user.id(), bankId);

        if (newAccount != null) {
            System.out.println("\nConta criada com sucesso!");
            System.out.printf("Agencia: %s | Conta: %s%n", newAccount.agency(), newAccount.number());
        }
        return newAccount;
    }

    public void showTransactionMenu(Scanner scanner, BankAccountResponse loggedAccount) {
        BankAccountResponse currentAccount = loggedAccount;
        boolean sessionActive = true;

        while (sessionActive) {
            System.out.println("\n========= OPERAÇÕES BANCARIAS =========");
            System.out.printf("Conta: %s | Agencia: %s%n", currentAccount.number(), currentAccount.agency());
            System.out.printf("Saldo Atual: R$ %.2f%n", currentAccount.balance());
            System.out.println("------------------------------------------");
            System.out.println("[1] - Realizar Deposito");
            System.out.println("[2] - Realizar Saque");
            System.out.println("[3] - Realizar Transferencia");
            System.out.println("[4] - Logout");
            System.out.print("Opção: ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1" -> {
                    System.out.print("Digite o valor do depósito: R$ ");
                    double amount = Double.parseDouble(scanner.nextLine());
                    BankAccountResponse updated = accountController.deposit(currentAccount.id(), amount);
                    if (updated != null) {
                        currentAccount = updated;
                        System.out.println("Depósito realizado com sucesso!");
                    }
                }
                case "2" -> {
                    System.out.print("Digite o valor do saque: R$ ");
                    double amount = Double.parseDouble(scanner.nextLine());
                    BankAccountResponse updated = accountController.withdraw(currentAccount.id(), amount);
                    if (updated != null) {
                        currentAccount = updated;
                        System.out.println("Saque retirado com sucesso!");
                    }
                }
                case "3" -> {
                    System.out.print("Digite o número da conta de destino: ");
                    String targetNum = scanner.nextLine();
                    System.out.print("Digite o valor da transferência: R$ ");
                    double amount = Double.parseDouble(scanner.nextLine());
                    
                    BankAccountResponse updated = accountController.transfer(currentAccount.id(), targetNum, amount);
                    if (updated != null) {
                        currentAccount = updated;
                        System.out.println("Transferência concluída com sucesso!");
                    }
                }
                case "4" -> {
                    System.out.println("Efetuando logout... Até logo!");
                    sessionActive = false;
                }
                default -> System.out.println("Opção inválida.");
            }
        }
    }
}