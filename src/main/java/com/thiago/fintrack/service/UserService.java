package com.thiago.fintrack.service;

import com.thiago.fintrack.dto.request.UpdateUserRequest;
import com.thiago.fintrack.dto.request.UserRequest;
import com.thiago.fintrack.dto.response.UserResponse;
import com.thiago.fintrack.entity.User;
import com.thiago.fintrack.exception.AlreadyExistsException;
import com.thiago.fintrack.exception.NotFoundException;
import com.thiago.fintrack.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    @Transactional
    public UserResponse createUser(UserRequest request){
        if (userRepository.existsByEmail(request.email())){
            throw new AlreadyExistsException("Email já cadastrado");
        }

        User newUser = new User();
        newUser.setName(request.name());
        newUser.setEmail(request.email());

        // TODO: aplicar hash BCrypt quando entrar o Spring Security
        newUser.setPassword(request.password());

        User saved = userRepository.save(newUser);
        return toResponse(saved);
    }

    @Transactional
    public UserResponse updateUser(Long userId, UpdateUserRequest userRequest){
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Usuario não encontrado"));

        if (!user.getEmail().equals(userRequest.email())
                && userRepository.existsByEmail(userRequest.email())){

            throw new AlreadyExistsException("Email já cadastrado");
        }

        user.setName(userRequest.name());
        user.setEmail(userRequest.email());

        return toResponse(user);
    }

    @Transactional
    public void deleteUser(Long userId){
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Usuario não encontrado"));

        userRepository.delete(user);
    }

    private UserResponse toResponse(User user){
        return new UserResponse(user.getId(), user.getName(), user.getEmail());
    }
}
