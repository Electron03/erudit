package com.game.erudut.repository;

import com.game.erudut.model.Player;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PlayerRepository extends JpaRepository<Player,Long> {
    Player findByName(String name);
    boolean existsByName(String name);
    List<Player> findAllByOrderByScoreDesc();
}
