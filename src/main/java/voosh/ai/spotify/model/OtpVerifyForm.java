package voosh.ai.spotify.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OtpVerifyForm {
    private String phone;
    private String otp;
}
