package ga.neves.social_login.controller;

import ga.neves.social_login.model.LoginDto;
import ga.neves.social_login.model.UserDto;
import ga.neves.social_login.model.Users;
import ga.neves.social_login.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserRepository repo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    //autenticar
    @PostMapping("/login")
    public String login(@RequestBody LoginDto loginDto) {

        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginDto.username(), loginDto.password());

        //autentica o Token
        Authentication authentication = authenticationManager.authenticate(authenticationToken);

        // Se a autenticação for bem-sucedida, o usuário será autenticado automaticamente
        if(authentication.isAuthenticated()) {
            return "Authentication successful";
        }

        return "Authentication failed";
    }

    //criar user
    @PostMapping("/register")
    public String register(@RequestBody UserDto user) {
        // Verifique se o nome de usuário já existe
        if (repo.findByUsername(user.username()).isPresent()) {
            return "Username already taken";
        }

        // Crie uma nova entidade User
        Users newUser = new Users();
        newUser.setUsername(user.username());

        //encriptografar senha
        //newUser.setPassword(passwordEncoder.encode(user.password()));
        newUser.setPassword(user.password());
        newUser.setRole(user.role());

        // Salve o novo usuário no banco de dados
        repo.save(newUser);

        return "User registered successfully";
    }
}
