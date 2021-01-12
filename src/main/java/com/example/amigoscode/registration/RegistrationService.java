package com.example.amigoscode.registration;

import com.example.amigoscode.appuser.AppUserRole;
import com.example.amigoscode.appuser.AppUser;
import com.example.amigoscode.appuser.AppUserService;
import com.example.amigoscode.registration.token.ConfirmationToken;
import com.example.amigoscode.registration.token.ConfirmationTokenService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class RegistrationService {

    private final EmailValidator emailValidator;
    private final AppUserService appUserService;
    private final ConfirmationTokenService confirmationTokenService;

    public String registerUser(RegistrationRequest registrationRequest) {
        boolean isValidEmail = emailValidator.test(registrationRequest.getEmail());
        if (!isValidEmail) {
            throw new IllegalStateException("email not valid");
        }

        return appUserService.signUpUser(
                new AppUser(
                        registrationRequest.getFirstName(),
                        registrationRequest.getLastName(),
                        registrationRequest.getEmail(),
                        registrationRequest.getPassword(),
                        AppUserRole.USER
                )
        );
    }

    @Transactional
    public void confirmUser(String confirmationToken) {
        ConfirmationToken token = confirmationTokenService.findConfirmationTokenByToken(confirmationToken)
                .orElseThrow(() -> new IllegalStateException("Token not found"));

        if (token.getConfirmedAt() != null) {
            throw new IllegalStateException("Email confirmed already");
        }

        LocalDateTime expiredAt = token.getExpiredAt();

        if (expiredAt.isBefore(LocalDateTime.now())) {
            throw new IllegalStateException("Token expired");
        }

        appUserService.enableAppUser(token.getAppUser().getEmail());
        confirmationTokenService.setTokenConfirmed(confirmationToken);
    }
}
