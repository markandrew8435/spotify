package voosh.ai.spotify.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class RefreshTokenResponse {
    private ExpirableToken jwtToken;
    private ExpirableToken refreshToken;

}
