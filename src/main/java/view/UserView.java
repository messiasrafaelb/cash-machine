package view;

import controller.UserController;
import controller.dto.UserRequest;
import controller.dto.UserResponse;
import java.util.Scanner;

public class UserView {

    private final UserController userController = new UserController();

    public UserResponse registerNewUser(Scanner scanner) {
        System.out.println("\n========= CADASTRO DE TITULAR =========");
        System.out.print("Digite o seu Nome Completo: ");
        String name = scanner.nextLine();
        
        System.out.print("Digite o seu CPF: ");
        String cpf = scanner.nextLine();
        
        System.out.print("Digite o seu Telefone/Celular: ");
        String phone = scanner.nextLine();

        UserRequest request = new UserRequest(cpf, name, phone);
        UserResponse response = userController.createUser(request);

        if (response != null) {
            System.out.println("Titular cadastrado com sucesso!");
        }
        return response;
    }
}