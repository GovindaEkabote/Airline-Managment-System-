package com.airline.repository;

import com.airline.model.City;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CityRepository extends JpaRepository<City, Long> {

    boolean existsByCityCode(String cityCode);
    boolean existsByCountryCode(String countryCode);

    boolean existsByCityCodeAndIdNot(String cityCode, Long id);

    Page<City> findByNameContainingIgnoreCase(
            String name,
            Pageable pageable
    );

    Page<City> findByCountryCodeIgnoreCase(
            String countryCode,
            Pageable pageable
    );

    Page<City> findByActive(
            Boolean active,
            Pageable pageable
    );

    @Query("""
            SELECT c FROM City c
            WHERE LOWER(c.name) LIKE LOWER(CONCAT('%', :keyword, '%'))
               OR LOWER(c.cityCode) LIKE LOWER(CONCAT('%', :keyword, '%'))
               OR LOWER(c.countryCode) LIKE LOWER(CONCAT('%', :keyword, '%'))
               OR LOWER(c.regionCode) LIKE LOWER(CONCAT('%', :keyword, '%'))
            """)
    Page<City> searchByKeyword(
            @Param("keyword") String keyword,
            Pageable pageable
    );
}
