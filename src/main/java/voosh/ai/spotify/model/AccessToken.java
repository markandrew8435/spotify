package voosh.ai.spotify.model;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccessToken {
    private String token;
    private String authType;
}
