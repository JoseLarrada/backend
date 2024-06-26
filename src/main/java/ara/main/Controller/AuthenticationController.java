package ara.main.Controller;

import ara.main.Dto.AuthenticationRequest;
import ara.main.Dto.AuthenticationResponse;
import ara.main.Dto.JwtDto;
import ara.main.Service.AuthenticationService;
import ara.main.Service.JwtService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {
    @Autowired
    private AuthenticationService authenticationService;
    @Autowired
    private JwtService jwtService;
    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody @Valid AuthenticationRequest authRequest){
        AuthenticationResponse jwtDto= authenticationService.login(authRequest);
        return ResponseEntity.ok(jwtDto);
    }
    @GetMapping("/tokenValid")
    public ResponseEntity<Boolean> tokenIsValid(@RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader) {
        String token = authorizationHeader.replace("Bearer ", "");
        return jwtService.isTokenValid(token);
    }

    @GetMapping("/tokenGoogle")
    public ResponseEntity<Boolean> tokenIsValidWithGoogle(@RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader) {
        String token = authorizationHeader.replace("Bearer ", "");
        return jwtService.isTokenValidWithGoogle(token);
    }
}
