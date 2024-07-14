package com.swyp.glint.keyword.api;

import com.swyp.glint.keyword.application.LocationService;
import com.swyp.glint.keyword.domain.Location;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/locations")
@Tag(name = "location_controller", description = "Manage locations related operations")
public class LocationController {

    private final LocationService locationService;

    public LocationController(LocationService locationService) {
        this.locationService = locationService;
    }

    @GetMapping("/{locationId}")
    @Operation(summary = "Get a location by its ID", description = "Location Id를 통한 위치 조회")
    public ResponseEntity<Location> getLocationById(@PathVariable Long locationId) {
        Location location = locationService.findById(locationId);
        return ResponseEntity.ok(location);
    }

    @GetMapping
    @Operation(summary = "List all locations", description = "모든 위치 조회")
    public ResponseEntity<List<Location>> getAllLocations() {
        List<Location> locations = locationService.getAllLocation();
        return ResponseEntity.ok(locations);
    }

    @GetMapping("/states")
    @Operation(summary = "List all states", description = "모든 [시,도] 조회")
    public ResponseEntity<List<String>> getAllStates() {
        List<String> states = locationService.getAllLocationSate();
        return ResponseEntity.ok(states);
    }

    @GetMapping("/cities")
    @Operation(summary = "List all cities by state", description = "[시,도]를 통한 모든 [시,군,구] 조회")
    public ResponseEntity<List<String>> getCitiesByState(@RequestParam String state) {
        List<String> cities = locationService.getAllCityByState(state);
        return ResponseEntity.ok(cities);
    }

    @GetMapping("/by-name")
    @Operation(summary = "Get a location by state and city", description = "[시,도]와 [시,군,구]를 통한 위치 조회")
    public ResponseEntity<Location> getLocationByStateAndCity(
            @RequestParam String state,
            @RequestParam String city) {
        Location location = locationService.findByName(state, city);
        return ResponseEntity.ok(location);
    }
}
