package com.example.ipldatabasemanagement;

import org.springframework.data.repository.CrudRepository;

public interface TeamRepository extends CrudRepository<Team, Integer> {
    Team findByTeamName(String name);

    Team findByPetName(String name);
}
