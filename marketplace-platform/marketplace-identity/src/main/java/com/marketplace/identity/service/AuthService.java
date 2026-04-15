package com.marketplace.identity.service;

import com.marketplace.common.exception.DuplicateResourceException;
import com.marketplace.common.exception.ResourceNotFoundException;
import com.marketplace.identity.dto.RegistrationRequest;
import com.marketplace.identity.dto.UserDto;
import com.marketplace.identity.entity.Role;
import com.marketplace.identity.entity.User;
import com.marketplace.identity.entity.UserStatus;
import com.marketplace.identity.repository.RoleRepository;
import com.marketplace.identity.repository.UserRepository;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class AuthService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthService(UserRepository userRepository,
                       RoleRepository roleRepository,
                       PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public UserDto register(RegistrationRequest request) {
        if (userRepository.existsByEmailAndDeletedFalse(request.email())) {
            throw new DuplicateResourceException("User", "email", request.email());
        }

        Role role = roleRepository.findByName(request.role())
            .orElseThrow(() -> new ResourceNotFoundException("Role", request.role()));

        User user = new User(
            request.email(),
            request.phone(),
            passwordEncoder.encode(request.password())
        );
        user.addRole(role);

        User savedUser = userRepository.save(user);
        return toDto(savedUser);
    }

    public UserDto getUserById(UUID id) {
        User user = userRepository.findByIdAndDeletedFalse(id)
            .orElseThrow(() -> new ResourceNotFoundException("User", id));
        return toDto(user);
    }

    public UserDto getUserByEmail(String email) {
        User user = userRepository.findByEmailAndDeletedFalse(email)
            .orElseThrow(() -> new ResourceNotFoundException("User", email));
        return toDto(user);
    }

    @Transactional
    public void verifyEmail(UUID userId) {
        User user = userRepository.findByIdAndDeletedFalse(userId)
            .orElseThrow(() -> new ResourceNotFoundException("User", userId));
        user.setEmailVerified(true);
        if (user.getStatus() == UserStatus.PENDING_VERIFICATION) {
            user.setStatus(UserStatus.ACTIVE);
        }
    }

    private UserDto toDto(User user) {
        Set<String> roleNames = user.getRoles().stream()
            .map(Role::getName)
            .collect(Collectors.toSet());

        return new UserDto(
            user.getId(),
            user.getEmail(),
            user.getPhone(),
            user.getStatus().name(),
            user.getEmailVerified(),
            user.getPhoneVerified(),
            roleNames,
            user.getCreatedAt()
        );
    }
}