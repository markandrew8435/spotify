package voosh.ai.spotify.controller;

import voosh.ai.spotify.exception.ApiException;
import voosh.ai.spotify.model.RefreshTokenForm;
import voosh.ai.spotify.service.AuthenticationService;
import voosh.ai.spotify.model.*;
import voosh.ai.spotify.service.EmailPasswordService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.http.HttpResponse;

@RequestMapping("")
@RestController()
public class UserController {

    @Autowired
    private AuthenticationService authenticationService;


    // below we can pass the access token
    @PostMapping("/login")
    public RefreshTokenResponse login(@RequestBody EmailPasswordForm loginForm) throws ApiException {
        return authenticationService.emailPasswordLogin(loginForm);
    }
    // below we can pass the access token
    @PostMapping("/signup")
    public RefreshTokenResponse signup(@RequestBody EmailPasswordForm loginForm) throws ApiException {
        return authenticationService.emailPasswordLogin(loginForm);
    }

    //the below is the controller to get the user from jwt
    @GetMapping("/verify")
    public ResponseEntity<String> verifyToken(HttpServletRequest request) throws ApiException {
        authenticationService.verifyToken(request);

        return new ResponseEntity<>("Authorized", HttpStatus.OK);
    }


// TODO add login

    @PostMapping("/refresh-token")
    public ExpirableToken refreshToken(@RequestBody RefreshTokenForm refreshTokenForm,
                                       HttpServletRequest request) throws ApiException {

        return authenticationService.refreshToken(request, refreshTokenForm);
    }

    @GetMapping("/hi")
    public String hi() {
        return "HI";
    }
}