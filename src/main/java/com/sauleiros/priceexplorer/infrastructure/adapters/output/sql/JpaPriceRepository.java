package com.sauleiros.priceexplorer.infrastructure.adapters.output.sql;

import com.sauleiros.priceexplorer.infrastructure.adapters.output.sql.model.SqlPrice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface JpaPriceRepository extends JpaRepository<SqlPrice, Long> {
    @Query("SELECT p FROM SqlPrice p WHERE " +
            "(:date IS NULL OR :date BETWEEN p.startDate AND p.endDate) " +
            "AND (:productId IS NULL OR p.productId = :productId) " +
            "AND (:brandId IS NULL OR p.brandId = :brandId) ")
    List<SqlPrice> findPrices(
            @Param("date") LocalDateTime date,
            @Param("productId") Long productId,
            @Param("brandId") Long brandId
    );
}
