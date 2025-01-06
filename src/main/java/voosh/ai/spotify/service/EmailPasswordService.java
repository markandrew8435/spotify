package voosh.ai.spotify.service;

import mes.job_cron.Utils;
import mes.job_cron.constants.Constants;
import mes.job_cron.entities.EmailPasswordEntity;
import mes.job_cron.exception.ApiException;
import mes.job_cron.model.LoginForm;
import mes.job_cron.repository.EmailPasswordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmailPasswordService {

    @Autowired
    private EmailPasswordRepository emailPasswordRepository;

    public void authenticate(LoginForm user) throws ApiException {
        EmailPasswordEntity existingUser = emailPasswordRepository.findByEmail(user.getEmail());
        if(existingUser != null) {
            if(!existingUser.getPassword().equals(Utils.computeSHA256(user.getPassword())))
                throw new ApiException("Wrong email/password", HttpStatus.BAD_REQUEST);
        }else {
            throw new ApiException(Constants.USER_NOT_FOUND, HttpStatus.BAD_REQUEST);
        }
    }

    public EmailPasswordEntity register(EmailPasswordEntity user) throws ApiException {
        if(emailPasswordRepository.findByEmail(user.getEmail())!=null){
            throw new ApiException(Constants.EMAIL_EXISTS, HttpStatus.CONFLICT);
        }
        user.setPassword(Utils.computeSHA256(user.getPassword()));
        return emailPasswordRepository.save(user);
    }

    public EmailPasswordEntity loadByEmail(String s) {
        return emailPasswordRepository.findByEmail(s);
    }

    public List<EmailPasswordEntity> getAllUsers() {
        return emailPasswordRepository.findAll();
    }

    public void delete(EmailPasswordEntity userEmail) {
        emailPasswordRepository.deleteById(userEmail.getId());
    }

    public Optional<EmailPasswordEntity> get(Long id) {
        return emailPasswordRepository.findById(id);
    }
}
