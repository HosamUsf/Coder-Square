package com.codersquare.registration;

import com.codersquare.exceptions.UserAlreadyExistsException;
import com.codersquare.registration.email.EmailGenerator;
import com.codersquare.registration.email.EmailSender;
import com.codersquare.registration.token.Token;
import com.codersquare.registration.token.TokenService;
import com.codersquare.request.RegistrationRequest;
import com.codersquare.response.RegistrationResponse;
import com.codersquare.user.User;
import com.codersquare.user.UserRepository;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;
import java.util.regex.Pattern;

@Service
@AllArgsConstructor
public class RegistrationService {
    private UserRepository userRepository;
    private TokenService tokenService;
    private final EmailSender emailSender;
    private PasswordEncoder passwordEncoder;

    private static final Logger logger = LoggerFactory.getLogger(RegistrationService.class);

    public ResponseEntity<RegistrationResponse> signUp(RegistrationRequest request) {
        try {
            // Validate email format
            validateEmail(request.email());

            // Check if the user with the provided email or username already exists
            checkIfUsernameOrEmailExists(request.email(), request.userName());

            // Create a new user
            User user = new User(request);
            user.setPassword(passwordEncoder.encode(user.getPassword()));

            // Save the user to the database
            userRepository.save(user);

            // Generate a confirmation token
            String token = generateToken(user);
            String link = "http://localhost:3001/api/v1/registration/confirm?token=" + token;

            emailSender.sendEmail(
                    request.email(),
                    EmailGenerator.generateConfirmationEmail(request.firstName(), link)
            );


            // Log successful registration
            logger.info("User registered successfully with email: {}", user.getEmail());

            // Return a success response with a confirmation message
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(RegistrationResponse.success("Please confirm your email"));
        } catch (UserAlreadyExistsException e) {
            // Log registration failure due to existing user
            logger.error("User registration failed. Reason: {}", e.getMessage());

            // Return a conflict response with an error message
            return ResponseEntity.status(HttpStatus.CONFLICT).body(RegistrationResponse.error(e.getMessage()));
        }
    }

    public String confirmToken(String confirmationToken) {
        Token token = tokenService.getToken(confirmationToken);

        // Check if the email is already confirmed
        if (token.getConfirmedAt() != null) {
            throw new IllegalStateException("Email already Confirmed");
        }

        // Check if the token has expired
        if (token.getExpiredAt().isBefore(LocalDateTime.now())) {
            throw new IllegalStateException("Token expired");
        }

        // Set the confirmation time for the token
        tokenService.setConfiramedAt(confirmationToken);

        // Enable the user in the database
        userRepository.enableuser(token.getUser().getEmail());

        // Log successful token confirmation
        logger.info("User email confirmed successfully: {}", token.getUser().getEmail());

        return EmailGenerator.generateConfirmationSuccessEmail(token.getUser().getFirstName());
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

    private String generateToken(User user) {
        String confirmationToken = UUID.randomUUID().toString();
        Token token = new Token(user, confirmationToken);
        tokenService.saveConfirmationToken(token);
        return confirmationToken;

    }
}
