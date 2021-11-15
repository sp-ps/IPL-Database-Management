package com.example.ipldatabasemanagement;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import javax.persistence.Tuple;
import java.util.List;
import java.util.Set;

public interface BowlingPerformanceRepository extends CrudRepository<BowlingPerformance, BowlingPerformancePK> {

    @Query("select u from BowlingPerformance  u where u.match =?1 and u.player in ?2")
    Iterable<BowlingPerformance> findByMatchAndTeam(Matches match, Set<Player> team);

    @Query("select u.player, sum(u.wickets) from BowlingPerformance u where u.match.opponent1.season = ?1 group by u.player order by sum(u.wickets)")
    List<Tuple> countWicketsByPlayer(Integer Season);

    List<BowlingPerformance> findByMatch(Matches match);

    @Query("select u from BowlingPerformance  u where u.match.matchId =?1 and u.player.playerId in ?2")
    BowlingPerformance findByMatchIdAndPlayerId(Integer matchId, Integer playerId);

    List<BowlingPerformance> findByMatchMatchId(Integer matchId);


}

