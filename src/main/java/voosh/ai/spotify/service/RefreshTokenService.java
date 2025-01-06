package voosh.ai.spotify.service;

import mes.job_cron.entities.RefreshToken2;
import mes.job_cron.exception.ApiException;
import mes.job_cron.repository.RefreshTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

@Service
public class RefreshTokenService {

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    @Value("${auth.refresh_token_timeout}")
    private long refreshTokenTimeout;

    public RefreshToken2 createRefreshToken(String email) {
        Date now = new Date(System.currentTimeMillis());
        Date expiration = new Date(now.getTime() + refreshTokenTimeout);
        RefreshToken2 refreshToken = RefreshToken2.builder()
                .token(UUID.randomUUID().toString())
                .expiration(expiration)
//                .email(email)
                .build();

        return refreshTokenRepository.save(refreshToken);
    }

    public RefreshToken2 findByToken(String token) {
        return refreshTokenRepository.findByToken(token);
    }

    public void verifyExpiration(RefreshToken2 token) throws ApiException {
        if (token.getExpiration().compareTo(new Date()) < 0) {
            refreshTokenRepository.delete(token);
            throw new ApiException(token.getToken() +
                    " Refresh token was expired. Please make a new signin request", HttpStatus.UNAUTHORIZED);
        }
    }

}