package service;

import controller.dto.UserRequest;
import controller.dto.UserResponse;
import domain.model.User;
import domain.repository.UserRepository;

public class UserService {

    private final UserRepository userRepository = new UserRepository();

    public UserResponse createUser(UserRequest userRequest) {        
        User user = userRequest.toEntity();
        userRepository.save(user);
        
        return UserResponse.fromEntity(user);
    }

    public UserResponse findUserById(Integer id) {
        User user = userRepository.findById(id);
        return user != null ? UserResponse.fromEntity(user) : null;
    }
}