package voosh.ai.spotify;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

public class Utils {
    private static final Random random = new Random();
    public static final long regenerationTimeThreshold = 1;   // the time after which the otp can regenerate for the given number
    public static final long otpExpirationLimit = 10;

    // Generate a random OTP
    public static String generateOTP() {
        int otp = 100000 + random.nextInt(900000); // Generate a 6-digit OTP
        return String.valueOf(otp);
    }

    // Compute SHA-256 hash of the OTP
    public static String computeSHA256(String otp) {
        MessageDigest digest = null;
        try {
            digest = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        byte[] hashBytes = digest.digest(otp.getBytes());
        StringBuilder hexString = new StringBuilder();
        for (byte b : hashBytes) {
            hexString.append(String.format("%02x", b));
        }
        return hexString.toString();
    }


}

