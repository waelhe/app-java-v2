package com.marketplace.user.service;

import com.marketplace.common.exception.BusinessException;
import com.marketplace.common.exception.ResourceNotFoundException;
import com.marketplace.user.entity.ConsumerProfile;
import com.marketplace.user.entity.ProviderProfile;
import com.marketplace.user.repository.ConsumerProfileRepository;
import com.marketplace.user.repository.ProviderProfileRepository;
import com.marketplace.user.entity.ProviderType;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class UserProfileService {

    private final ProviderProfileRepository providerProfileRepository;
    private final ConsumerProfileRepository consumerProfileRepository;

    public UserProfileService(ProviderProfileRepository providerProfileRepository,
                              ConsumerProfileRepository consumerProfileRepository) {
        this.providerProfileRepository = providerProfileRepository;
        this.consumerProfileRepository = consumerProfileRepository;
    }

    @Transactional
    public ProviderProfile createProviderProfile(UUID userId, String displayName, ProviderType type) {
        if (providerProfileRepository.existsByUserIdAndDeletedFalse(userId)) {
            throw new BusinessException("Provider profile already exists for user: " + userId);
        }
        ProviderProfile profile = new ProviderProfile(userId, displayName);
        profile.setType(type);
        return providerProfileRepository.save(profile);
    }

    public ProviderProfile getProviderProfile(UUID userId) {
        return providerProfileRepository.findByUserIdAndDeletedFalse(userId)
            .orElseThrow(() -> new ResourceNotFoundException("Provider profile", userId));
    }

    @Transactional
    public ProviderProfile updateProviderProfile(UUID userId, String displayName, String bio, List<String> specialties) {
        ProviderProfile profile = getProviderProfile(userId);
        if (displayName != null) profile.setDisplayName(displayName);
        if (bio != null) profile.setBio(bio);
        if (specialties != null) profile.setSpecialties(specialties);
        return providerProfileRepository.save(profile);
    }

    @Transactional
    public ConsumerProfile createConsumerProfile(UUID userId, String displayName) {
        if (consumerProfileRepository.existsByUserIdAndDeletedFalse(userId)) {
            throw new BusinessException("Consumer profile already exists for user: " + userId);
        }
        return consumerProfileRepository.save(new ConsumerProfile(userId, displayName));
    }

    public ConsumerProfile getConsumerProfile(UUID userId) {
        return consumerProfileRepository.findByUserIdAndDeletedFalse(userId)
            .orElseThrow(() -> new ResourceNotFoundException("Consumer profile", userId));
    }

    @Transactional
    public ConsumerProfile updateConsumerProfile(UUID userId, String displayName, String preferences) {
        ConsumerProfile profile = getConsumerProfile(userId);
        if (displayName != null) profile.setDisplayName(displayName);
        if (preferences != null) profile.setPreferences(preferences);
        return consumerProfileRepository.save(profile);
    }

    @Transactional
    public void verifyProvider(UUID userId) {
        ProviderProfile profile = getProviderProfile(userId);
        profile.setVerified(true);
        providerProfileRepository.save(profile);
    }
}