package com.swyp.glint.meeting.domain;

import com.swyp.glint.keyword.domain.Location;

import java.util.List;

public class LocationList {
    private final List<Location> locations;

    public LocationList(List<Location> locations) {
        this.locations = locations;
    }

    public List<String> getLocationNames() {
        return locations.stream().map(location -> location.getState() + " " + location.getCity()).toList();
    }
}
