package voosh.ai.spotify.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import mes.job_cron.model.ExpirableToken;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

@Service
public class JWTService {

    private static final String keyGenerateAlgorithm = "HmacSHA256";

    private String secretkey;

    @Value("${auth.jwt_token_timeout}")
    private long timeout;

    // Set to store invalidated tokens
    private final Set<String> invalidatedTokens = ConcurrentHashMap.newKeySet();


    public JWTService() {
        try {
            KeyGenerator keyGen = KeyGenerator.getInstance(keyGenerateAlgorithm);
            SecretKey sk = keyGen.generateKey();
            secretkey = Base64.getEncoder().encodeToString(sk.getEncoded());
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public ExpirableToken generateToken(String username) {
        Date now = new Date(System.currentTimeMillis());
        Date expiration = new Date(now.getTime() + timeout);
        Map<String, Object> claims = new HashMap<>();
        String token = Jwts.builder()
                .claims()
                .add(claims)
                .subject(username)
                .issuedAt(now)
                .expiration(expiration)
                .and()
                .signWith(getKey())
                .compact();
        return new ExpirableToken(token, expiration);
    }

    private SecretKey getKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretkey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String extractUserName(String token) {
        // extract the username from jwt token
        return extractClaim(token, Claims::getSubject);
    }

    public boolean isTokenValid(String token) {
        if (invalidatedTokens.contains(token)) {
            return false; // Token is invalidated
        }
        try{
            return extractExpiration(token).before(new Date());
        }catch (Exception e){
            return true;
        }
    }
    /**
     * Invalidate a token by adding it to the invalidated token set.
     *
     * @return
     */
    public void invalidateToken(String token) {
        invalidatedTokens.add(token);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimResolver) {
        final Claims claims = extractAllClaims(token);
        return claimResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(getKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}