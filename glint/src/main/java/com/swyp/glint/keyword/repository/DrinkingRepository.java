package com.swyp.glint.keyword.repository;

import com.swyp.glint.keyword.domain.Drinking;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DrinkingRepository extends JpaRepository<Drinking, Long> {

    @Query("""
                SELECT d
                FROM Drinking d
                WHERE d.drinkingName = :drinkingName
            """)
    Optional<Drinking> findByDrinkingName(@Param("drinkingState") String drinkingName);

}
