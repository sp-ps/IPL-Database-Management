package com.example.ipldatabasemanagement;

import javassist.compiler.ast.Pair;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import javax.persistence.Tuple;
import java.util.List;
import java.util.Set;

public interface BattingPerformanceRepository extends CrudRepository<BattingPerformance, BattingPerformancePK> {

    @Query("select u from BattingPerformance u where u.match=?1 and u.player in ?2")
    Iterable<BattingPerformance> findByMatchAndTeam(Matches match, Set<Player> team);

    @Query("select sum(u.sixes) from BattingPerformance u where u.match.opponent2.season = ?1")
    Integer countSixesBySeason(Integer season);

    @Query("select sum(u.fours) from BattingPerformance u where u.match.opponent2.season = ?1")
    Integer countFoursBySeason(Integer season);

    @Query("select u.player, sum(u.runs) as totalRuns from BattingPerformance u where u.match.opponent2.season = ?1 group by u.player order by sum(u.runs) desc")
    List<Tuple> countRunsByPlayer(Integer season);

    @Query("select u.player, sum(u.sixes) as totalSixes from BattingPerformance u where u.match.opponent2.season = ?1 group by u.player order by sum(u.sixes) desc")
    List<Tuple> countSixesByPlayer(Integer season);

    @Query("select u.player, sum(u.fours) as totalFours from BattingPerformance u where u.match.opponent2.season = ?1 group by u.player order by sum(u.fours) desc")
    List<Tuple> countFoursByPlayer(Integer season);

    @Query("select u.player, u.runs as t from BattingPerformance u where u.match.opponent2.season = ?1 order by u.runs desc")
    List<Tuple> highestRunsByPlayer(Integer season);

    List<BattingPerformance> findByMatch(Matches match);

    @Query("select u from BattingPerformance  u where u.match.matchId =?1 and u.player.playerId in ?2")
    BattingPerformance findByMatchIdAndPlayerId(Integer matchId, Integer playerId);

    List<BattingPerformance> findByMatchMatchId(Integer matchId);





}
