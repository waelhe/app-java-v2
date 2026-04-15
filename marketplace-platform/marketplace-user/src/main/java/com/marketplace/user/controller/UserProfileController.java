package com.marketplace.user.controller;

import com.marketplace.common.dto.ApiResponse;
import com.marketplace.user.entity.ConsumerProfile;
import com.marketplace.user.entity.ProviderProfile;
import com.marketplace.user.entity.ProviderType;
import com.marketplace.user.service.UserProfileService;
import java.util.List;
import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/profiles")
public class UserProfileController {

    private final UserProfileService userProfileService;

    public UserProfileController(UserProfileService userProfileService) {
        this.userProfileService = userProfileService;
    }

    @PostMapping("/provider")
    public ResponseEntity<ApiResponse<ProviderProfile>> createProviderProfile(
            @RequestParam UUID userId, 
            @RequestParam String displayName,
            @RequestParam(defaultValue = "SELLER") ProviderType type) {
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(ApiResponse.success("Provider profile created",
                userProfileService.createProviderProfile(userId, displayName, type)));
    }

    @GetMapping("/provider/{userId}")
    public ResponseEntity<ApiResponse<ProviderProfile>> getProviderProfile(@PathVariable UUID userId) {
        return ResponseEntity.ok(ApiResponse.success(userProfileService.getProviderProfile(userId)));
    }

    @PatchMapping("/provider/{userId}")
    public ResponseEntity<ApiResponse<ProviderProfile>> updateProviderProfile(
            @PathVariable UUID userId,
            @RequestParam(required = false) String displayName,
            @RequestParam(required = false) String bio,
            @RequestParam(required = false) List<String> specialties) {
        return ResponseEntity.ok(ApiResponse.success("Profile updated",
            userProfileService.updateProviderProfile(userId, displayName, bio, specialties)));
    }

    @PostMapping("/consumer")
    public ResponseEntity<ApiResponse<ConsumerProfile>> createConsumerProfile(
            @RequestParam UUID userId, @RequestParam String displayName) {
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(ApiResponse.success("Consumer profile created",
                userProfileService.createConsumerProfile(userId, displayName)));
    }

    @GetMapping("/consumer/{userId}")
    public ResponseEntity<ApiResponse<ConsumerProfile>> getConsumerProfile(@PathVariable UUID userId) {
        return ResponseEntity.ok(ApiResponse.success(userProfileService.getConsumerProfile(userId)));
    }

    @PatchMapping("/consumer/{userId}")
    public ResponseEntity<ApiResponse<ConsumerProfile>> updateConsumerProfile(
            @PathVariable UUID userId,
            @RequestParam(required = false) String displayName,
            @RequestParam(required = false) String preferences) {
        return ResponseEntity.ok(ApiResponse.success("Profile updated",
            userProfileService.updateConsumerProfile(userId, displayName, preferences)));
    }

    @PatchMapping("/provider/{userId}/verify")
    public ResponseEntity<ApiResponse<Void>> verifyProvider(@PathVariable UUID userId) {
        userProfileService.verifyProvider(userId);
        return ResponseEntity.ok(ApiResponse.success("Provider verified"));
    }
}