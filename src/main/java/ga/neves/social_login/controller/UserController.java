package ga.neves.social_login.controller;

import ga.neves.social_login.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserRepository repo;

    @GetMapping("/comum")
    public String getComum() {
        return "Usuario Comum";
    }

    @GetMapping("/admin")
    public String getAdm() {
        return "Usuario Admin";
    }

}
