package voosh.ai.spotify.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class PasswordChangeRequest {

    @JsonProperty("new_password")
    private String newPassword;

    @JsonProperty("old_password")
    private String oldPassword;
}
