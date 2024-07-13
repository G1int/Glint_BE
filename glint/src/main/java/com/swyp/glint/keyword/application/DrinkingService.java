package com.swyp.glint.keyword.application;

import com.swyp.glint.common.exception.NotFoundEntityException;
import com.swyp.glint.keyword.domain.Drinking;
import com.swyp.glint.keyword.repository.DrinkingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DrinkingService {

    private final DrinkingRepository drinkingRepository;

    public Drinking findById(Long drinkingId) {
        return drinkingRepository.findById(drinkingId)
                .orElseThrow(() -> new NotFoundEntityException("Drinking not found with id: " + drinkingId));
    }

    public List<Drinking> getAllDrinking() { // 전체 조회
        return drinkingRepository.findAll();
    }

    public String getDrinkingNameById(Long drinkingId) { // drinking id를 통한 음주명 반환
        return drinkingRepository.findById(drinkingId)
                .map(Drinking::getDrinkingName)
                .orElseThrow(() -> new NotFoundEntityException("Drinking name with drinkingId: " + drinkingId + " not found"));
    }

    public Long getDrinkingIdByName(String drinkingName) { // 음주명을 통한 drinking id 반환
        return drinkingRepository.findByDrinkingName(drinkingName)
                .map(Drinking::getId)
                .orElseThrow(() -> new NotFoundEntityException("Drinking id not found with name: " + drinkingName));
    }

}
