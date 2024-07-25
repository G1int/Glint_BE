package com.swyp.glint.keyword.repository;

import com.swyp.glint.keyword.domain.Location;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LocationRepository extends JpaRepository<Location, Long> {

    //'시도(state)'와 '시군구(city)'로 Location 찾기
    @Query("""
                SELECT l
                FROM Location l
                WHERE l.state = :state AND l.city = :city
            """)
    Optional<Location> findByStateAndCity(@Param("state") String state, @Param("city") String city);

    //모든 '시도(state)' 찾기
    @Query("""
                SELECT DISTINCT l.state
                FROM Location l
            """)
    List<String> findAllState();

    //'시도(state)'로 모든 '시군구(city)' 찾기
    @Query("""
                SELECT l.city
                FROM Location l
                WHERE l.state = :state
            """)
    List<String> findAllCityByState(@Param("state") String state);

    //'시군구(city)'로 해당하는 '시도(state)' 찾기
    @Query("""
                SELECT l.state
                FROM Location l
                WHERE l.city = :city
            """)
    Optional<String> findStateByCity(@Param("city") String city);

    //특정 '시군구(city)'가 '시도(state)'에 존재하는지 확인
    @Query("""
                SELECT CASE
                    WHEN COUNT(l) > 0 THEN true
                    ELSE false
                END
                FROM Location l
                WHERE l.state = :state AND l.city = :city
            """)
    Optional<Boolean> existsByStateAndCity(@Param("state") String state, @Param("city") String city);

}
