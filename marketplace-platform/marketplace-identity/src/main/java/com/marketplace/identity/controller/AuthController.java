package com.marketplace.identity.controller;

import com.marketplace.common.dto.ApiResponse;
import com.marketplace.identity.dto.RegistrationRequest;
import com.marketplace.identity.dto.UserDto;
import com.marketplace.identity.service.AuthService;
import jakarta.validation.Valid;
import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<UserDto>> register(
            @Valid @RequestBody RegistrationRequest request) {
        UserDto userDto = authService.register(request);
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(ApiResponse.success("User registered successfully", userDto));
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<ApiResponse<UserDto>> getUserById(@PathVariable UUID id) {
        UserDto userDto = authService.getUserById(id);
        return ResponseEntity.ok(ApiResponse.success(userDto));
    }

    @PostMapping("/users/{id}/verify-email")
    public ResponseEntity<ApiResponse<Void>> verifyEmail(@PathVariable UUID id) {
        authService.verifyEmail(id);
        return ResponseEntity.ok(ApiResponse.success("Email verified successfully"));
    }
}