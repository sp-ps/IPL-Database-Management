package com.example.ipldatabasemanagement;

import org.springframework.data.repository.CrudRepository;

public interface PlayerRepository extends CrudRepository<Player, Integer> {


    Player findByPlayerId(Integer PlayerId);

    Player findByPlayerName(String PlayerName);

}
