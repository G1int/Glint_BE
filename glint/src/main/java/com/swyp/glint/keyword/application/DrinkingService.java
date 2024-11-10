package com.swyp.glint.keyword.application;

import com.swyp.glint.core.common.exception.NotFoundEntityException;
import com.swyp.glint.keyword.domain.Drinking;
import com.swyp.glint.keyword.repository.DrinkingRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DrinkingService {

    private final DrinkingRepository drinkingRepository;

    public Drinking getDrinkBy(Long drinkingId) {
        return drinkingRepository.findById(drinkingId)
                .orElseThrow(() -> new NotFoundEntityException("Drinking not found with id: " + drinkingId));
    }

    public Drinking findByName(String drinkingName) {
        return drinkingRepository.findByDrinkingName(drinkingName)
                .orElseThrow(() -> new NotFoundEntityException("Drinking not found with id: " + drinkingName));
    }

    public List<Drinking> getAllDrinking() { // 전체 조회
        return drinkingRepository.findAll();
    }

    public String getDrinkingNameById(Long drinkingId) { // drinking id를 통한 음주명 반환
        return drinkingRepository.findById(drinkingId)
                .map(Drinking::getDrinkingName)
                .orElseThrow(() -> new NotFoundEntityException("Drinking name with drinking id: " + drinkingId + " not found"));
    }

    public Long getDrinkingIdByName(String drinkingName) { // 음주명을 통한 drinking id 반환
        return drinkingRepository.findByDrinkingName(drinkingName)
                .map(Drinking::getId)
                .orElseThrow(() -> new NotFoundEntityException("Drinking id not found with name: " + drinkingName));
    }

    @Transactional
    public Drinking createNewDrinking(String drinkingName) {
        return drinkingRepository.findByDrinkingName(drinkingName)
                .orElseGet(() -> {
                    Drinking newDrinking = Drinking.createNewDrinking(drinkingName);
                    return drinkingRepository.save(newDrinking);
                });
    }

    @Transactional
    public Drinking updateDrinkingById(Long drinkingId, String drinkingName) {
        Drinking drinking = drinkingRepository.findById(drinkingId)
                .orElseThrow(() -> new NotFoundEntityException("Drinking not found with drinking id: " + drinkingId));
        drinking.updateDrinking(drinkingName);
        return drinkingRepository.save(drinking);
    }

    @Transactional
    public void deleteDrinking(Long drinkingId) {
        Drinking drinking = drinkingRepository.findById(drinkingId)
                .orElseThrow(() -> new NotFoundEntityException("Drinking not found with drinking id:" + drinkingId));
        drinkingRepository.delete(drinking);
    }

}
