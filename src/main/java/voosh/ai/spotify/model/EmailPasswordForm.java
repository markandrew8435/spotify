package voosh.ai.spotify.model;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
public class EmailPasswordForm {
    @NonNull
    private String email;
    @NonNull
    private String password;
}
