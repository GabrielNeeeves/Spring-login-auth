package ga.neves.social_login.repository;

import ga.neves.social_login.model.Player;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PlayerRepository extends JpaRepository<Player, Long> {


}
