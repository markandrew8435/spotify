package voosh.ai.spotify.service;

import voosh.ai.spotify.entities.EmailPasswordEntity;
import voosh.ai.spotify.exception.ApiException;
import voosh.ai.spotify.model.UserDetailsEntity;
import voosh.ai.spotify.model.EmailPasswordForm;
import voosh.ai.spotify.repository.EmailPasswordRepository;
import voosh.ai.spotify.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class EmailPasswordService {

    @Autowired
    private EmailPasswordRepository emailPasswordRepository;

    public boolean authenticate(EmailPasswordForm user) throws ApiException {
        EmailPasswordEntity existingUser = emailPasswordRepository.findByEmail(user.getEmail());
        if(existingUser != null) {
            if(!existingUser.getPassword().equals(Utils.computeSHA256(user.getPassword())))
                throw new ApiException("Wrong email/password", HttpStatus.BAD_REQUEST);

        }
        return true;
    }

    public EmailPasswordEntity register(EmailPasswordEntity user) {
        Utils.computeSHA256(user.getPassword());
        return emailPasswordRepository.save(user);
    }

    public UserDetailsEntity loadByEmail(String s) {
        return null;
    }

}
