package com.codersquare.registration;

import com.codersquare.request.RegistrationRequest;
import com.codersquare.response.RegistrationResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("api/v1")
public class RegistrationController {

    private RegistrationService service;

    @PostMapping("/signup")
    public ResponseEntity<RegistrationResponse> signUp(@RequestBody @Valid RegistrationRequest request) {
        return service.signUp(request);
    }

    @GetMapping("/registration/confirm")
    public String confirmToken(@RequestParam String token) {
        return service.confirmToken(token);
    }
}
