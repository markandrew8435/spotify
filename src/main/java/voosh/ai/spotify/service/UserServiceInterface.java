package voosh.ai.spotify.service;//package voosh.ai.spotify.service;
//
//import voosh.ai.spotify.exception.ApiException;
//import voosh.ai.spotify.model.UserDetailsEntity;
//import voosh.ai.spotify.entities.EmailPasswordEntity;
//
//public interface UserServiceInterface {
//
//    public UserDetailsEntity login(EmailPasswordEntity loginForm) throws ApiException;
//
//    public boolean logout(String token);
//
//    EmailPasswordEntity register(EmailPasswordEntity user);
//
//    UserDetailsEntity validateAccessToken(String token) throws ApiException;
//
//    UserDetailsEntity loadByEmail(String s);
//}