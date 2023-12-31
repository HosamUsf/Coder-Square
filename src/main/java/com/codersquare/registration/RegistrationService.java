package com.codersquare.registration;

import com.codersquare.exceptions.UserAlreadyExistsException;
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
                    buildEmail(request.firstName(), link)
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

        return "confirmed, you can close this page now";
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

    private String buildEmail(String name, String link) {

        return "<div style=\"font-family:Helvetica,Arial,sans-serif;font-size:16px;margin:0;color:#0b0c0c\">\n" +
                "\n" +
                "<span style=\"display:none;font-size:1px;color:#fff;max-height:0\"></span>\n" +
                "\n" +
                "  <table role=\"presentation\" width=\"100%\" style=\"border-collapse:collapse;min-width:100%;width:100%!important\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\n" +
                "    <tbody><tr>\n" +
                "      <td width=\"100%\" height=\"53\" bgcolor=\"#0b0c0c\">\n" +
                "        \n" +
                "        <table role=\"presentation\" width=\"100%\" style=\"border-collapse:collapse;max-width:580px\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" align=\"center\">\n" +
                "          <tbody><tr>\n" +
                "            <td width=\"70\" bgcolor=\"#0b0c0c\" valign=\"middle\">\n" +
                "                <table role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse\">\n" +
                "                  <tbody><tr>\n" +
                "                    <td style=\"padding-left:10px\">\n" +
                "                  \n" +
                "                    </td>\n" +
                "                    <td style=\"font-size:28px;line-height:1.315789474;Margin-top:4px;padding-left:10px\">\n" +
                "                      <span style=\"font-family:Helvetica,Arial,sans-serif;font-weight:700;color:#ffffff;text-decoration:none;vertical-align:top;display:inline-block\">Confirm your email</span>\n" +
                "                    </td>\n" +
                "                  </tr>\n" +
                "                </tbody></table>\n" +
                "              </a>\n" +
                "            </td>\n" +
                "          </tr>\n" +
                "        </tbody></table>\n" +
                "        \n" +
                "      </td>\n" +
                "    </tr>\n" +
                "  </tbody></table>\n" +
                "  <table role=\"presentation\" class=\"m_-6186904992287805515content\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse;max-width:580px;width:100%!important\" width=\"100%\">\n" +
                "    <tbody><tr>\n" +
                "      <td width=\"10\" height=\"10\" valign=\"middle\"></td>\n" +
                "      <td>\n" +
                "        \n" +
                "                <table role=\"presentation\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse\">\n" +
                "                  <tbody><tr>\n" +
                "                    <td bgcolor=\"#1D70B8\" width=\"100%\" height=\"10\"></td>\n" +
                "                  </tr>\n" +
                "                </tbody></table>\n" +
                "        \n" +
                "      </td>\n" +
                "      <td width=\"10\" valign=\"middle\" height=\"10\"></td>\n" +
                "    </tr>\n" +
                "  </tbody></table>\n" +
                "\n" +
                "\n" +
                "\n" +
                "  <table role=\"presentation\" class=\"m_-6186904992287805515content\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse;max-width:580px;width:100%!important\" width=\"100%\">\n" +
                "    <tbody><tr>\n" +
                "      <td height=\"30\"><br></td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "      <td width=\"10\" valign=\"middle\"><br></td>\n" +
                "      <td style=\"font-family:Helvetica,Arial,sans-serif;font-size:19px;line-height:1.315789474;max-width:560px\">\n" +
                "        \n" +
                "            <p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\">Hi " + name + ",</p><p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\"> Thank you for registering. Please click on the below link to activate your account: </p><blockquote style=\"Margin:0 0 20px 0;border-left:10px solid #b1b4b6;padding:15px 0 0.1px 15px;font-size:19px;line-height:25px\"><p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\"> <a href=\"" + link + "\">Activate Now</a> </p></blockquote>\n Link will expire in 15 minutes. <p>See you soon</p>" +
                "        \n" +
                "      </td>\n" +
                "      <td width=\"10\" valign=\"middle\"><br></td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "      <td height=\"30\"><br></td>\n" +
                "    </tr>\n" +
                "  </tbody></table><div class=\"yj6qo\"></div><div class=\"adL\">\n" +
                "\n" +
                "</div></div>";

    }

}
