package controller.dto;

import domain.model.User;

public record UserRequest(
    String cpf,
    String name,
    String phone
) {
    public User toEntity() {
        return new User(null, this.cpf, this.name, this.phone);
    }
}