package com.example.ipldatabasemanagement;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface MatchesRepository extends CrudRepository<Matches,Integer> {

    @Query("select u from Matches u where u.opponent1.season = ?1")
    Iterable<Matches> findBySeason(Integer season);

    @Query("select u from Matches u where u.matchId = ?1")
    Matches findByMatchId(Integer matchId);

}
