package com.thiago.fintrack.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UpdateUserRequest(@NotBlank @Size(max = 100) String name,
                                @NotBlank @Email @Size(max = 100) String email) {
}
