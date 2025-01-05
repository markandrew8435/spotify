package voosh.ai.spotify.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class ExpirableToken {
    private String token;
    private Date expiration;

    public ExpirableToken(String token, Date expiration) {
        this.token = token;
        this.expiration = expiration;
    }
}
