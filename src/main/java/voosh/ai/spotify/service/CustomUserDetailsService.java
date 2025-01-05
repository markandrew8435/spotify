package voosh.ai.spotify.service;//package voosh.ai.spotify.service;
//
//import voosh.ai.spotify.model.UserPrincipal;
//import voosh.ai.spotify.model.Users;
//import voosh.ai.spotify.repository.UserRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.EmailNotFoundException;
//import org.springframework.stereotype.Service;
//
//@Service
//public class CustomUserDetailsService implements UserDetailsService {
//
//    @Autowired
//    private UserRepository usersRepo;
//
//    @Override
//    public UserDetails loadUserByEmail(String email) throws EmailNotFoundException {
//        Users user = usersRepo.findByEmail(email);
//        if(user == null) throw new EmailNotFoundException(email);
//
//        return new UserPrincipal(user);
//    }
//}
