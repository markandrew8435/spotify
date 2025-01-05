//package voosh.ai.spotify.service;
//
//import voosh.ai.spotify.exception.ApiException;
//import voosh.ai.spotify.model.OtpVerifyForm;
//import voosh.ai.spotify.model.UserDetailsEntity;
//import voosh.ai.spotify.utils.Utils;
//import org.springframework.http.HttpStatus;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Service;
//
//import org.springframework.beans.factory.annotation.Autowired;
//
//import java.time.LocalDateTime;
//import java.util.Objects;
//import java.util.regex.Pattern;
//
//@Service
//public class OtpService {
//
//    @Autowired
//    private OtpRepository otpRepository;
//
//
//    public String generateOtp(String phoneNumber) throws ApiException {
//        phoneNumber = phoneNumber.trim();
//        if (!isValidPhoneNumber(phoneNumber)) throw  new ApiException("Invalid phone number format.", HttpStatus.BAD_REQUEST);
//
//
//        OtpEntity otpEntity = otpRepository.findByPhoneNumber(phoneNumber);
//        if(Objects.nonNull(otpEntity) && otpEntity.getUpdatedAt().isAfter(LocalDateTime.now().minusMinutes(Utils.regenerationTimeThreshold)))
//            throw new ApiException("Otp cannot be generated again within %d minute(s) for the same phone number"
//                    .formatted(Utils.regenerationTimeThreshold), HttpStatus.BAD_REQUEST);
//
//        if(Objects.isNull(otpEntity)) otpEntity = new OtpEntity();
//
//        String otp = Utils.generateOTP();
//        otpEntity.setPhoneNumber(phoneNumber);
//        otpEntity.setHashCode(Utils.computeSHA256(otp));
//        otpRepository.save(otpEntity);
//        sendOtp(phoneNumber, otp);
//        return otp;
//    }
//
//
//
//    private void sendOtp(String phoneNumber, String otp) {
////        send otp via sms
//    }
//
//    // Scheduled cleanup task
//
//    @Scheduled(fixedRate = 5*60000) // Run every 5 minute
//    public void cleanupExpiredOtps() {
//        otpRepository.deleteAll(otpRepository.findByCreatedAtBefore(LocalDateTime.now().minusMinutes(5)));
//    }
//    private boolean isValidPhoneNumber(String phoneNumber) {
//        return Pattern.compile("^\\+?[1-9]\\d{1,14}$").matcher(phoneNumber).matches();
//    }
//
//    private boolean isValidOtp(String otp) {
//        return Pattern.compile("^\\d{6}$").matcher(otp).matches();
//    }
//
//    public UserDetailsEntity verifyOtp(OtpVerifyForm loginForm) throws ApiException {
//        String phoneNumber = loginForm.getPhone();
//        String otp = loginForm.getOtp();
//        phoneNumber = phoneNumber.trim();
//        if (!isValidOtp(otp)) throw  new ApiException("Invalid OTP.", HttpStatus.BAD_REQUEST);
//
//        OtpEntity otpEntity = otpRepository.findById(phoneNumber).orElse(null);
//        if (otpEntity == null) throw new ApiException("otp is either expired or not generated for the given phoneNumber", HttpStatus.NOT_ACCEPTABLE);
//
//        if(!otpEntity.getHashCode().equals(Utils.computeSHA256(otp))) throw new ApiException("Incorrect OTP", HttpStatus.BAD_REQUEST);
//        otpRepository.delete(otpEntity);
//
//        if(!otpEntity.getCreatedAt().isAfter(LocalDateTime.now().minusMinutes(Utils.otpExpirationLimit)))
//            throw new ApiException("OTP is Expired", HttpStatus.REQUEST_TIMEOUT);
//
//        return UserDetailsEntity.builder().email(phoneNumber).build();
//    }
//}
