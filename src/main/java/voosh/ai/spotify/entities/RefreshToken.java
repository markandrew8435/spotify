package voosh.ai.spotify.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import voosh.ai.spotify.model.ExpirableToken;

import lombok.*;

import java.time.Instant;
import java.util.Date;

@Entity
@Setter
@Getter
@Builder
@AllArgsConstructor
public class RefreshToken extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String token;
    private Date expiration;
    private String  email;

    public RefreshToken(){}
}