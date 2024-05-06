package ara.main.Controller;

import ara.main.Dto.PersonsDto;
import ara.main.Dto.RegisterGoogleResponse;
import ara.main.Dto.RegisterRequest;
import ara.main.Dto.UpdatedRegisterRequest;
import ara.main.Entity.persons;
import ara.main.Service.JwtService;
import ara.main.Service.PersonasService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/personas")
public class PersonasController {
    @Autowired
    private PersonasService personasService;
    @Autowired
    private JwtService jwtService;
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody @Valid RegisterRequest authRequest){
        return personasService.register(authRequest);
    }
    @GetMapping
    public ResponseEntity<List<persons>> getAll(){
        return  personasService.getAll();
    }
    @GetMapping("/info")
    public ResponseEntity<PersonsDto> getProfile(@RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader){
        String token = authorizationHeader.replace("Bearer ", "");
        System.out.print(token);
        if(jwtService.isTokenValid(token).getBody()){
            return personasService.getId(jwtService.extractID(token));
        }else{
            return ResponseEntity.notFound().build();
        }
    }
    @PutMapping("/continueRegister")
    public ResponseEntity<String> continueRegister(@RequestBody UpdatedRegisterRequest request){
        return personasService.continueRegister(request);
    }
}
