package ara.main.Controller;
import ara.main.Dto.AuthenticationResponse;
import ara.main.Dto.PersonsDto;
import ara.main.Dto.RegisterGoogleResponse;
import ara.main.Dto.util.Role;
import ara.main.Service.AuthenticationService;
import ara.main.Entity.persons;
import ara.main.Service.OAuth2Service;
import ara.main.Service.PersonasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;


@RestController
@RequestMapping("/Oauth")
public class OAuth2Controller {
    @Autowired
    private OAuth2Service oAuth2Service;
    @GetMapping("/user")
    public ResponseEntity<String> getUserInfo(@AuthenticationPrincipal OAuth2User principal) {
        return oAuth2Service.getUserInfo(principal);
    }
    @PostMapping("/register")
    public ResponseEntity<RegisterGoogleResponse> register(@RequestBody persons principal){
        return oAuth2Service.registerUser(principal);
    }
    @PostMapping("/login")
    public  ResponseEntity<AuthenticationResponse> login(@RequestBody String principal) {
        return oAuth2Service.loginUser(principal);
    }
}