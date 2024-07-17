package com.swyp.glint.keyword.application;

import com.swyp.glint.common.exception.NotFoundEntityException;
import com.swyp.glint.keyword.domain.Smoking;
import com.swyp.glint.keyword.repository.SmokingRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SmokingService {

    private final SmokingRepository smokingRepository;

    public Smoking findById(Long smokingId) { // smoking id를 통한 Smoking 엔티티 반환
        return smokingRepository.findById(smokingId)
                .orElseThrow(() -> new NotFoundEntityException("Smoking not found with id: " + smokingId));
    }

    public Smoking findByName(String smokingName) { // 흡연명을 통한 Smoking 엔티티 반환
        return smokingRepository.findBySmokingName(smokingName)
                .orElseThrow(() -> new NotFoundEntityException("Smoking not found with name: " + smokingName));
    }

    public List<Smoking> getAllSmoking() {
        return smokingRepository.findAll();
    }

    public String getSmokingNameById(Long smokingId) { // smoking id를 통한 흡연명 반환
        return smokingRepository.findById(smokingId)
                .map(Smoking::getSmokingName)
                .orElseThrow(() -> new NotFoundEntityException("Smoking name not found with smokingId: " + smokingId));
    }

    public Long getSmokingIdByName(String smokingName) { // 흡연명을 통한 smoking id 반환
        return smokingRepository.findBySmokingName(smokingName)
                .map(Smoking::getId)
                .orElseThrow(() -> new NotFoundEntityException("Smoking id not found with name: " + smokingName));
    }

    @Transactional
    public Smoking createNewSmoking(String smokingName) {
        return smokingRepository.findBySmokingName(smokingName)
                .orElseGet(() -> {
                    Smoking newSmoking = Smoking.createNewSmoking(smokingName);
                    return smokingRepository.save(newSmoking);
                });
    }

    @Transactional
    public Smoking updateSmokingById(Long smokingId, String smokingName) {
        Smoking smoking = smokingRepository.findById(smokingId)
                .orElseThrow(() -> new NotFoundEntityException("Smoking not found with smoking id: " + smokingId));
        smoking.updateSmoking(smokingName);
        return smokingRepository.save(smoking);
    }

    @Transactional
    public void deleteSmoking(Long smokingId) {
        Smoking smoking = smokingRepository.findById(smokingId)
                .orElseThrow(() -> new NotFoundEntityException("Smoking not found with smoking id: " + smokingId));
        smokingRepository.delete(smoking);
    }

}
