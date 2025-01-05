package voosh.ai.spotify.service;

import voosh.ai.spotify.constants.AuthType;
import voosh.ai.spotify.model.OTPRequestForm;
import voosh.ai.spotify.exception.ApiException;
import voosh.ai.spotify.model.*;
import voosh.ai.spotify.entities.RefreshToken;
import jakarta.servlet.http.HttpServletRequest;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class AuthenticationService {

    @Autowired
    private EmailPasswordService userService;

    @Autowired
    private JWTService jwtService;

    @Autowired
    private RefreshTokenService refreshTokenService;


    @Autowired
    private EmailPasswordService emailPasswordService;


    public void verifyToken(HttpServletRequest request) throws ApiException {
         String jwt = getJwt(request);

         if(jwtService.isTokenExpired(jwt))
             throw new ApiException("Invalid token", HttpStatus.UNAUTHORIZED);

    }

    public ExpirableToken refreshToken(HttpServletRequest request, RefreshTokenForm refreshTokenForm) throws ApiException {
        String jwt = getJwt(request);
        if(!jwtService.isTokenExpired(jwt))
            return new ExpirableToken(jwt, jwtService.extractExpiration(jwt));

        RefreshToken refreshToken = refreshTokenService.findByToken(refreshTokenForm.getRefreshToken());
        if(Objects.isNull(refreshToken))
            throw new ApiException("Refresh Token not found", HttpStatus.NOT_FOUND);

        refreshTokenService.verifyExpiration(refreshToken);
        String email = refreshToken.getEmail();
        return jwtService.generateToken(email);
    }

    private String getJwt(HttpServletRequest request) throws ApiException {
        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            throw new ApiException("Invalid Bearer Token", HttpStatus.UNAUTHORIZED);
        }
        return authorizationHeader
                    .substring(7);
    }


    public RefreshTokenResponse emailPasswordLogin(EmailPasswordForm loginForm) throws ApiException {
        emailPasswordService.authenticate(loginForm);

        return getTokenResponse(loginForm.getEmail());
    }

    private RefreshTokenResponse getTokenResponse(@NonNull String email) {
        ExpirableToken jwt = jwtService.generateToken(email);
        RefreshToken refreshToken = refreshTokenService.createRefreshToken(email);

        return RefreshTokenResponse.builder()
                .refreshToken(new ExpirableToken(refreshToken.getToken(), refreshToken.getExpiration()))
                .jwtToken(jwt)
                .build();
    }

}
