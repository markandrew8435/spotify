package voosh.ai.spotify.model;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
public class UsernamePasswordForm {
    @NonNull
    private String username;
    @NonNull
    private String password;
}
