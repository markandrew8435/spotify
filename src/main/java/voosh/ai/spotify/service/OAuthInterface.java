package voosh.ai.spotify.service;

import voosh.ai.spotify.exception.ApiException;
import voosh.ai.spotify.model.UserDetailsEntity;

public interface OAuthInterface {
    UserDetailsEntity validateAccessToken(String token) throws ApiException;

}
