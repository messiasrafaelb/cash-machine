package controller.dto;

import domain.model.User;

public record UserResponse(
    Integer id,
    String cpf,
    String name,
    String phone
) {
    public static UserResponse fromEntity(User user) {
        if (user == null) return null;
        return new UserResponse(user.getId(), user.getCpf(), user.getName(), user.getPhone());
    }
}