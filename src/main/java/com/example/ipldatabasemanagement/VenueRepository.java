package com.example.ipldatabasemanagement;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface VenueRepository extends CrudRepository<Venue, Integer> {


    @Query("select u from Venue u where u.venueId = ?1")
    Venue findByVenueId(Integer venue);

    Venue findByVenueName(String venueName);
}
