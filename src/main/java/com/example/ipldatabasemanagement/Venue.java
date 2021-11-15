package com.example.ipldatabasemanagement;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;


@Entity
public class Venue {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer venueId;

    private String venueName;

    @OneToMany(mappedBy = "venue")
    private Set<Matches> matches = new HashSet<>();

    public Integer getVenueId() {
        return venueId;
    }

    public void setVenueId(Integer venueId) {
        this.venueId = venueId;
    }

    public String getVenueName() {
        return venueName;
    }

    public void setVenueName(String venueName) {
        this.venueName = venueName;
    }
}
