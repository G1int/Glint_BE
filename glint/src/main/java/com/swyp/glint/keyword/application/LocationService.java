package com.swyp.glint.keyword.application;

import com.swyp.glint.common.exception.NotFoundEntityException;
import com.swyp.glint.keyword.domain.Location;
import com.swyp.glint.keyword.repository.LocationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LocationService {

    private final LocationRepository locationRepository;

    private Location findById(Long locationId) { // location id를 통한 Location 엔티티 반환
        return locationRepository.findById(locationId)
                .orElseThrow(() -> new NotFoundEntityException("Location not found with id: " + locationId));
    }

    public List<Location> getAllLocation() { // 전체 조회
        return locationRepository.findAll();
    }

    public List<String> getAllLocationSate() { // 전체 state 조회
        Optional<List<String>> optionalStates = locationRepository.findAllState();
        if(optionalStates.isPresent()) {
            return optionalStates.get();
        } else {
            throw new NotFoundEntityException("State not found");
        }
    }

    public List<String> getAllCityByState(String state) { // 특정 state에 해당하는 모든 city 조회
        return locationRepository.findAllCityByState(state)
                .orElseThrow(() -> new NotFoundEntityException("Cities not dound with state: " + state));
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
}
