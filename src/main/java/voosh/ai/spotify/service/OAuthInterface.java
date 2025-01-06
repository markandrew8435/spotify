package voosh.ai.spotify.service;

import mes.job_cron.exception.ApiException;
import mes.job_cron.model.UserDetailsEntity;

public interface OAuthInterface {
    UserDetailsEntity validateAccessToken(String token) throws ApiException;

}
