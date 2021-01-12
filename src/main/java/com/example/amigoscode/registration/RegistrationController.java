package com.example.amigoscode.registration;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequestMapping(path = "api/v1/registration")
@RestController
@AllArgsConstructor
public class RegistrationController {

    private final RegistrationService registrationService;

    @PutMapping
    public String register(@RequestBody RegistrationRequest registrationRequest) {
        return registrationService.registerUser(registrationRequest);
    }

    @GetMapping(path = "confirm")
    public String string(@RequestParam(value = "token") String token) {
        registrationService.confirmUser(token);
        return "confirmed";
    }
}
