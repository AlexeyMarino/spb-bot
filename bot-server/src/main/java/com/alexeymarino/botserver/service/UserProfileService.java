package com.alexeymarino.botserver.service;

import com.alexeymarino.botserver.domain.UserEntity;
import com.alexeymarino.botserver.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserProfileService {
    private final UserRepository userRepository;

    public UserEntity saveUserProfile(Long chatId, String name) {
        return userRepository.findById(chatId)
                .orElseGet(() -> userRepository.save(new UserEntity(chatId, name)));
    }
}
