package com.example.amigoscode.registration.token;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ConfirmationTokenService {
    private final ConfirmationTokenRepository confirmationTokenRepository;

    public void saveConfirmationToken(ConfirmationToken confirmationToken) {
        confirmationTokenRepository.save(confirmationToken);
    }

    public Optional<ConfirmationToken>
        findConfirmationTokenByToken(String token) {
        return confirmationTokenRepository.findByConfirmationToken(token);
    }

    public void setTokenConfirmed(String confirmationToken) {
        confirmationTokenRepository.updateConfirmedAt(confirmationToken, LocalDateTime.now());
    }
}
