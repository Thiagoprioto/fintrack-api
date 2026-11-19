package com.thiago.fintrack.controller;

import com.thiago.fintrack.dto.request.UpdateUserRequest;
import com.thiago.fintrack.dto.request.UserRequest;
import com.thiago.fintrack.dto.response.UserResponse;
import com.thiago.fintrack.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/create")
    public ResponseEntity<UserResponse> createUser(@RequestBody @Valid UserRequest request){
        UserResponse created = userService.createUser(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/update")
    public ResponseEntity<UserResponse> updateUser(@RequestParam Long userId,
                                                   @RequestBody @Valid UpdateUserRequest userRequest){
        UserResponse updated = userService.updateUser(userId, userRequest);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Void> deleteUser(@RequestParam Long userId){
        userService.deleteUser(userId);
        return ResponseEntity.noContent().build();
    }
}
