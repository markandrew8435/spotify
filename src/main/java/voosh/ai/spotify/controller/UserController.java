package voosh.ai.spotify.controller;

import jakarta.servlet.http.HttpServletRequest;
import mes.job_cron.constants.Constants;
import mes.job_cron.entities.EmailPasswordEntity;
import mes.job_cron.exception.ApiException;
import mes.job_cron.model.*;
import mes.job_cron.service.AuthenticationFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("")
@RestController()
public class UserController {

    @Autowired
    private AuthenticationFacade authenticationFacade;

    @PostMapping("/users/add-user")
    public CustomResponse register(@RequestBody EmailPasswordEntity loginForm) throws ApiException {

        authenticationFacade.register(loginForm);
        return CustomResponse.builder()
                .status(201)
                .message(Constants.USER_CREATED).build();
    }

    // below we can pass the access token
    @PostMapping("/login")
    public CustomResponse login(@RequestBody LoginForm loginForm) throws ApiException {
         TokenResponse data = authenticationFacade.emailPasswordLogin(loginForm);
         return CustomResponse.builder()
                 .data(data)
                 .status(200)
                 .message(Constants.LOGIN_SUCCESS)
                 .build();
    }

    // below we can pass the access token
    @PostMapping("/signup")
    public CustomResponse signup(@RequestBody LoginForm loginForm) throws ApiException {
        authenticationFacade.signup(loginForm);
        return CustomResponse.builder()
                .message(Constants.USER_CREATED)
                .status(201).build();
    }

    //the below is the controller to get the user from jwt
    @GetMapping("/verify")
    public ResponseEntity<String> verifyToken(HttpServletRequest request) throws ApiException {
        authenticationFacade.verifyToken(request);

        return new ResponseEntity<>("Authorized", HttpStatus.OK);
    }

// TODO add login

    @PostMapping("/refresh-token")
    public ExpirableToken refreshToken(@RequestBody RefreshTokenForm refreshTokenForm,
                                       HttpServletRequest request) throws ApiException {

        return authenticationFacade.refreshToken(request, refreshTokenForm);
    }

    @PostMapping("/users/update-password")
    public CustomResponse changePassword(@RequestBody PasswordChangeRequest passwordChangeRequest, HttpServletRequest request) throws ApiException {
        String data = authenticationFacade.changePassword(passwordChangeRequest, request);
        return CustomResponse.builder()
                .data(data)
                .status(204)
                .message(Constants.PASSWORD_CHANGED)
                .build();
    }

    @GetMapping("/logout")
    public CustomResponse logout(HttpServletRequest request) throws ApiException {
        String data = authenticationFacade.logout(request);

        return CustomResponse.builder()
                .status(200)
                .message(Constants.LOGOUT_SUCCESS).build();
    }

    @GetMapping("/delete")
    public CustomResponse delete(HttpServletRequest request, @PathVariable Long id) throws ApiException {
        authenticationFacade.deleteUser(request, id);
        return CustomResponse.builder()
                .status(200)
                .message(Constants.LOGOUT_SUCCESS).build();
    }

    @GetMapping("/users")
    public CustomResponse getAllUsers(HttpServletRequest request){
        Object data = authenticationFacade.getAllUsers();

        return CustomResponse.builder()
                .status(200)
                .data(data)
                .message(Constants.USERS_FETCHED).build();
    }

}