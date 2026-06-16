package controller;

import controller.dto.UserRequest;
import controller.dto.UserResponse;
import service.UserService;

public class UserController {

    private final UserService userService = new UserService();

    public UserResponse createUser(UserRequest userRequest) {
        try {
            return userService.createUser(userRequest);
        } catch (Exception e) {
            System.err.println("Erro ao criar usuário: " + e.getMessage());
            return null;
        }
    }

    public UserResponse findUserById(Integer id) {
        return userService.findUserById(id);
    }
}