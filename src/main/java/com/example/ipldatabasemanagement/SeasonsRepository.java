package com.example.ipldatabasemanagement;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface SeasonsRepository extends CrudRepository<Seasons,SeasonsPK> {

    @Query("select u from Seasons u where u.team=?1 and u.season=?2")
    Seasons findByTeamAndSeasonId( Team team, Integer season);

    @Query("select u from Seasons u where u.season=?2 and u.team.teamId=?1")
    Seasons findByTeamIdAndSeason(Integer team, Integer season);

    @Query("select u from Seasons u where u.season=?1")
    Iterable<Seasons> findAllBySeasonId(Integer season);

    @Query("select u from Seasons u where u.season=?1 order by u.totalPoints desc")
    Iterable<Seasons> findAllBySeasonIdAndPoints(Integer season);

    @Query("select distinct(u.season) from Seasons u")
    Iterable<Integer> findUniqueSeason();

}
