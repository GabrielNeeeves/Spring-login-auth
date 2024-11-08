package ga.neves.social_login.controller;

import ga.neves.social_login.model.Player;
import ga.neves.social_login.model.PlayerDto;
import ga.neves.social_login.repository.PlayerRepository;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static org.springframework.security.authorization.AuthorityAuthorizationManager.hasRole;

@RestController
@RequestMapping("/players")
public class PlayerController {

    @Autowired
    private PlayerRepository repo;

    @GetMapping
    public List<Player> get() {
        return repo.findAll();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/create")
    public ResponseEntity createPl(@RequestBody PlayerDto dto) {
        try {
            Player player = new Player(dto);
            repo.save(player);
            return new ResponseEntity(player, HttpStatus.CREATED);
        } catch(Exception e) {
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity updatePl(@PathVariable Long id, @RequestBody Player playerUpd) {
            Optional<Player> playerOpt = repo.findById(id);

            if(playerOpt.isPresent()) {
                var newPlayer = playerOpt.get();
                newPlayer.setName(playerUpd.getName());
                newPlayer.setNumber(playerUpd.getNumber());
                newPlayer.setBorn(playerUpd.getBorn());

                return new ResponseEntity(repo.save(newPlayer), HttpStatus.OK);
            }

            return new ResponseEntity("Error updating Player", HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity delPl(@PathVariable Long id) {

        Optional<Player> playerOpt = repo.findById(id);
        if(playerOpt.isPresent()) {
            repo.deleteById(id);
            return new ResponseEntity("Player deleted", HttpStatus.OK);
        }
        return new ResponseEntity("Error updating Player", HttpStatus.NOT_FOUND);
    }
}
