package com.swyp.glint.keyword.application;

import com.swyp.glint.common.exception.NotFoundEntityException;
import com.swyp.glint.keyword.domain.Religion;
import com.swyp.glint.keyword.repository.ReligionRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReligionService {

    private final ReligionRepository religionRepository;

    public Religion findById(Long religionId) { // religion id를 통한 Religion 엔티티 반환
        return religionRepository.findById(religionId)
                .orElseThrow(() -> new NotFoundEntityException("Religion not found with id: " + religionId));
    }

    public Religion findByName(String religionName) { // 종교명을 통한 Religion 엔티티 반환
        return religionRepository.findByReligionName(religionName)
                .orElseThrow(() -> new NotFoundEntityException("Religion not found with name: " + religionName));
    }

    public List<Religion> getAllReligion() { // 전체 조회
        return religionRepository.findAll();
    }

    public String getReligionNameById(Long religionId) { // religion id를 통한 종교명 반환
        return religionRepository.findById(religionId)
                .map(Religion::getReligionName)
                .orElseThrow(() -> new NotFoundEntityException("Religion name not found with religionId: " + religionId));
    }

    public Long getReligionIdByName(String religionName) { // 종교명을 통한 religion id 반환
        return religionRepository.findByReligionName(religionName)
                .map(Religion::getId)
                .orElseThrow(() -> new NotFoundEntityException("Religion id not found with name: " + religionName));
    }

    @Transactional
    public Religion createNewReligion(String religionName) {
        if (religionName == null) {
            throw new IllegalArgumentException("Religion name must not be null");
        }
        return religionRepository.save(Religion.createNewReligion(religionName));
    }

    @Transactional
    public Religion updateReligionById(Long religionId, String religionName) {
        Religion religion = religionRepository.findById(religionId)
                .orElseThrow(() -> new NotFoundEntityException("Religion not found with religion id: " + religionId));
        religion.updateReligion(religionName);
        return religionRepository.save(religion);
    }

    @Transactional
    public void deleteReligion(Long religionId) {
        Religion religion = religionRepository.findById(religionId)
                .orElseThrow(() -> new NotFoundEntityException("Religion not found with religion id: " + religionId));
        religionRepository.delete(religion);
    }

}
