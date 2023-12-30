package com.codersquare.registration;

import com.codersquare.exceptions.UserAlreadyExistsException;
import com.codersquare.request.RegistrationRequest;
import com.codersquare.response.RegistrationResponse;
import com.codersquare.user.User;
import com.codersquare.user.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.regex.Pattern;

@Service
@AllArgsConstructor
public class RegistrationService {
    private UserRepository userRepository;

    public ResponseEntity<RegistrationResponse> signUp(RegistrationRequest request) {
        try {
            validateEmail(request.email());
            checkIfUsernameOrEmailExists(request.email(), request.userName());
            User user = new User(request);
            userRepository.save(user);
            return ResponseEntity.status(HttpStatus.CREATED).body(RegistrationResponse
                    .success("User registered successfully", String.valueOf(user.getUserId())));
        } catch (UserAlreadyExistsException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(RegistrationResponse.error(e.getMessage()));
        }
    }


    private void checkIfUsernameOrEmailExists(String email, String username) {
        if (userRepository.existsByEmail(email)) {
            throw new UserAlreadyExistsException("User with email " + email + " already exists");
        }
        if (userRepository.existsByUsername(username)) {
            throw new UserAlreadyExistsException("User with user name " + username + " already exists\"");
        }
    }

    private void validateEmail(String email) {
        // Define a regular expression for a basic email format
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";

        // Compile the regex pattern
        Pattern pattern = Pattern.compile(emailRegex);

        // Validate the email against the pattern
        if (!pattern.matcher(email).matches()) {
            throw new IllegalArgumentException("Invalid email format");
        }
    }

}
