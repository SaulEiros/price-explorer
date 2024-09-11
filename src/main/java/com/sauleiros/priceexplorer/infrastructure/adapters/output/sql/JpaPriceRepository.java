package com.sauleiros.priceexplorer.infrastructure.adapters.output.sql;

import com.sauleiros.priceexplorer.infrastructure.adapters.output.sql.model.SqlPrice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface JpaPriceRepository extends JpaRepository<SqlPrice, Long> {
    @Query("SELECT p FROM SqlPrice p WHERE " +
            ":date BETWEEN p.startDate AND p.endDate " +
            "AND p.brandId = :brandId " +
            "AND p.productId = :productId")
    List<SqlPrice> findPrices(
            @Param("date") LocalDateTime date,
            @Param("brandId") Long brandId,
            @Param("productId") Long productId
    );
}
