package com.swyp.glint.keyword.application;

import com.swyp.glint.common.exception.NotFoundEntityException;
import com.swyp.glint.keyword.domain.Location;
import com.swyp.glint.keyword.repository.LocationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LocationService {

    private final LocationRepository locationRepository;

    public Location findById(Long locationId) { // location id를 통한 Location 엔티티 반환
        return locationRepository.findById(locationId)
                .orElseThrow(() -> new NotFoundEntityException("Location not found with id: " + locationId));
    }

    public Location findByName(String state, String city) {
        return locationRepository.findByStateAndCity(state, city)
                .orElseThrow(() -> new NotFoundEntityException("Location not foun with state: " + state + " and city: " + city));
    }

    public List<Location> getAllLocation() { // 전체 조회
        return locationRepository.findAll();
    }

    public List<String> getAllLocationSate() { // 전체 state 조회
        List<String> states = locationRepository.findAllState();
        if(states.isEmpty()) {
            throw new NotFoundEntityException("State not found");
        }
        return states;
    }

    public List<String> getAllCityByState(String state) { // 특정 state에 해당하는 모든 city 조회
        List<String> cities = locationRepository.findAllCityByState(state);
        if(cities.isEmpty()) {
            throw new NotFoundEntityException("cities not found with state: " + state);
        }
        return cities;
    }

    public String getStateByCity(String city) { // 특정 city에 해당하는 state 조회
        return locationRepository.findStateByCity(city)
                .orElseThrow(() -> new NotFoundEntityException("State not found with city: " + city));
    }

    public String getLocationNameById(Long locationId) { // location id를 통한 전체 위치명 문자열로 반환
        return locationRepository.findById(locationId)
                .map(location -> location.getState() + " " + location.getCity())
                .orElseThrow(() -> new NotFoundEntityException("Location not found with locationId: " + locationId));
    }

    public Location getLocationByStateAndCityName(String state, String city) { // 특정 state와 city에 해당하는 Location 반환
        return locationRepository.findByStateAndCity(state, city)
                .orElseThrow(() -> new NotFoundEntityException("Location not found with state: " + state + " and city: " + city));
    }

    public Long getLocationIdByStateAndCityName(String state, String city) { // 특정 state와 city에 해당하는 location id 반환
        return locationRepository.findByStateAndCity(state, city)
                .map(Location::getId)
                .orElseThrow(() -> new NotFoundEntityException("Location id not found with state: " + state + " and city: " + city));
    }

    public Location createNewLocation(String state, String city) {
        return locationRepository.findByStateAndCity(state, city)
                .orElseGet(() -> {
                    Location newWLocation = Location.createNewLocation(state, city);
                    return locationRepository.save(newWLocation);
                });
    }

    public Location updateLocationById(Long locationId, String state, String city) {
        Location location = locationRepository.findById(locationId)
                .orElseThrow(() -> new NotFoundEntityException("Location not found with location id: " + locationId));
        location.updateLocation(state,city);
        return locationRepository.save(location);
    }

    public void deleteLocation(Long locationId) {
        Location location = locationRepository.findById(locationId)
                .orElseThrow(() -> new NotFoundEntityException("Location not found with location id: " + locationId));
        locationRepository.delete(location);
    }

}
