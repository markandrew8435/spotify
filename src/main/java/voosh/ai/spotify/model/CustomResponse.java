package voosh.ai.spotify.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CustomResponse {
    private int status;
    private String message;
    private Object data;
    private String error;
}
