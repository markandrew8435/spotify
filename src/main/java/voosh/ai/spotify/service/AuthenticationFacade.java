package voosh.ai.spotify.service;

import jakarta.servlet.http.HttpServletRequest;
import lombok.NonNull;
import mes.job_cron.Utils;
import mes.job_cron.constants.Constants;
import mes.job_cron.constants.Roles;
import mes.job_cron.entities.EmailPasswordEntity;
import mes.job_cron.entities.RefreshToken2;
import mes.job_cron.exception.ApiException;
import mes.job_cron.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

@Component
public class AuthenticationFacade {


    @Autowired
    private JWTService jwtService;

    @Autowired
    private RefreshTokenService refreshTokenService;

    @Autowired
    private EmailPasswordService emailPasswordService;


    public void verifyToken(HttpServletRequest request) throws ApiException {
         String jwt = getJwt(request);

         if(jwtService.isTokenValid(jwt))
             throw new ApiException("Invalid token", HttpStatus.UNAUTHORIZED);

    }

    public ExpirableToken refreshToken(HttpServletRequest request, RefreshTokenForm refreshTokenForm) throws ApiException {
        String jwt = getJwt(request);
        if(!jwtService.isTokenValid(jwt))
            return new ExpirableToken(jwt, jwtService.extractExpiration(jwt));

        RefreshToken2 refreshToken = refreshTokenService.findByToken(refreshTokenForm.getRefreshToken());
        if(Objects.isNull(refreshToken))
            throw new ApiException("Refresh Token not found", HttpStatus.NOT_FOUND);

        refreshTokenService.verifyExpiration(refreshToken);
//        String email = refreshToken.getEmail();
        return jwtService.generateToken("email");
    }

    private String getJwt(HttpServletRequest request) throws ApiException {
        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            throw new ApiException("Invalid Bearer Token", HttpStatus.UNAUTHORIZED);
        }

        System.out.println(authorizationHeader);
        String temp =  authorizationHeader
                    .substring(7);

        System.out.println(temp);
        return temp;
    }


    public TokenResponse emailPasswordLogin(LoginForm loginForm) throws ApiException {
        validateLoginForm(loginForm);
        emailPasswordService.authenticate(loginForm);
        return getTokenResponse(loginForm.getEmail());
    }

    public EmailPasswordEntity register(EmailPasswordEntity loginForm) throws ApiException {
        return emailPasswordService.register(loginForm);
    }

    private TokenResponse getTokenResponse(@NonNull String email) {
        ExpirableToken jwt = jwtService.generateToken(email);
        RefreshToken2 refreshToken = refreshTokenService.createRefreshToken(email);

        return new TokenResponse(jwt.getToken());
    }

    public EmailPasswordEntity getUser(HttpServletRequest httpRequest) throws ApiException {
        verifyToken(httpRequest);
        return emailPasswordService.loadByEmail( jwtService.extractUserName(getJwt(httpRequest)));
    }

    public String changePassword(PasswordChangeRequest passwordChangeRequest, HttpServletRequest request) throws ApiException {
        EmailPasswordEntity user = getUser(request);
        if(!user.getPassword().equals(Utils.computeSHA256(passwordChangeRequest.getOldPassword()))){
            throw new ApiException(Constants.UNAUTHORIZED_ACCESS, HttpStatus.UNAUTHORIZED);
        }
        user.setPassword(Utils.computeSHA256(passwordChangeRequest.getNewPassword()));
        return Constants.PASSWORD_CHANGED;
    }

    public List<EmailPasswordEntity> getAllUsers() {
        return emailPasswordService.getAllUsers();
    }

    public String logout(HttpServletRequest request) throws ApiException {
        String token = getJwt(request);
        if(!jwtService.isTokenValid(token))
            throw new ApiException(Constants.BAD_REQUEST, HttpStatus.BAD_REQUEST);

        jwtService.invalidateToken(token);
        return Constants.LOGOUT_SUCCESS;
    }

    public void signup(LoginForm loginForm) throws ApiException {
        validateLoginForm(loginForm);
        EmailPasswordEntity emailPasswordEntity = new EmailPasswordEntity();
        emailPasswordEntity.setEmail(loginForm.getEmail());
        emailPasswordEntity.setPassword(loginForm.getPassword());
        emailPasswordEntity.setRole(Roles.VIEWER);
        register(emailPasswordEntity);
    }

    private void validateLoginForm(LoginForm loginForm) throws ApiException {
        if(loginForm.getEmail()==null) throw new ApiException(Constants.MISSING_EMAIL, HttpStatus.BAD_REQUEST);
        if(loginForm.getPassword()==null) throw new ApiException(Constants.MISSING_PASSWORD, HttpStatus.BAD_REQUEST);
    }

    public void deleteUser(HttpServletRequest request, Long id) throws ApiException {
        // Verify the authorization token
        verifyToken(request);

        // Extract the requesting user's details
        String requestingUserEmail = jwtService.extractUserName(getJwt(request));
        EmailPasswordEntity requestingUser = emailPasswordService.loadByEmail(requestingUserEmail);

        // Ensure the requesting user has the Admin role
        if (!Roles.ADMIN.equals(requestingUser.getRole())) {
            throw new ApiException(Constants.FORBIDDEN_ACCESS, HttpStatus.FORBIDDEN);
        }

        // Prevent the Admin from deleting themselves
        if (Objects.equals(requestingUser.getId(), id)) {
            throw new ApiException(Constants.BAD_REQUEST, HttpStatus.FORBIDDEN);
        }

        // Load the user to be deleted
        if (emailPasswordService.get(id).isEmpty()) {
            throw new ApiException(Constants.USER_NOT_FOUND, HttpStatus.NOT_FOUND);
        }

        // Perform the delete operation
        emailPasswordService.delete(requestingUser);
    }

}
