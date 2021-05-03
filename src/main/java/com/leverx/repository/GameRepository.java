package com.leverx.repository;

import com.leverx.entity.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GameRepository extends JpaRepository<Game, Integer> {

    Optional<Game> findByNameAndDescription(String name, String description);
}
