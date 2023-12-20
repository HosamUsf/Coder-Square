package com.codersquare.registration;

import com.codersquare.request.RegistrationRequest;
import com.codersquare.response.RegistrationResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("api/v1")
public class RegistrationController {

    private RegistrationService service;

    @PostMapping("/signup")
    public ResponseEntity<RegistrationResponse> signUp(@RequestBody @Valid RegistrationRequest request) {
        return service.signUp(request);
    }
}
