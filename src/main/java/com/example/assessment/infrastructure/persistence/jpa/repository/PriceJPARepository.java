package com.example.assessment.infrastructure.persistence.jpa.repository;

import com.example.assessment.infrastructure.persistence.jpa.entity.Price;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;

@Repository
public interface PriceJPARepository extends JpaRepository<Price, Long> {

    @Query(nativeQuery = true, value = """
    select *
    from Price
    where :date between start_date and end_date
    and product_id = :productId
    and brand_id = :brandId
    order by priority DESC
    LIMIT 1
    """)
    Optional<Price> findFirstByDateAndProductIdAndBrandId(@Param("date") LocalDateTime date,
                                                          @Param("productId") Long productId,
                                                          @Param("brandId") Long brandId);

    @Query(nativeQuery = true, value = """
    select *
    from Price
    where :date between start_date and end_date
    and product_id = :productId
    and brand_id = :brandId
    order by priority DESC
    """)
    Set<Price> findByDateAndProductIdAndBrandId(@Param("date") LocalDateTime date,
                                                @Param("productId") Long productId,
                                                @Param("brandId") Long brandId);
}
