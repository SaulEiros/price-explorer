package com.sauleiros.priceexplorer.infrastructure.adapters.output.sql.model;

import com.sauleiros.priceexplorer.domain.model.Price;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Currency;

@Entity
@Table(name = "PRICES")
public class SqlPrice {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Column(name = "brand_id", nullable = false)
        private Long brandId;

        @Column(name = "start_date", nullable = false)
        private LocalDateTime startDate;

        @Column(name = "end_date", nullable = false)
        private LocalDateTime endDate;

        @Column(name = "price_list", nullable = false)
        private Long priceList;

        @Column(name = "product_id", nullable = false)
        private Long productId;

        @Column(name = "priority", nullable = false)
        private Long priority;

        @Column(name = "price", nullable = false)
        private Double price;

        @Column(name = "currency", nullable = false)
        private String currency;

        @Column(name = "last_update", nullable = false)
        private LocalDateTime lastUpdate;

        @Column(name = "last_update_by", nullable = false)
        private String lastUpdateBy;

        public SqlPrice() {}

        public SqlPrice(Long id, Long brandId, LocalDateTime startDate, LocalDateTime endDate,
                        Long priceList, Long productId, Long priority, Double price,
                        String currency, LocalDateTime lastUpdate, String lastUpdateBy) {
                this.id = id;
                this.brandId = brandId;
                this.startDate = startDate;
                this.endDate = endDate;
                this.priceList = priceList;
                this.productId = productId;
                this.priority = priority;
                this.price = price;
                this.currency = currency;
                this.lastUpdate = lastUpdate;
                this.lastUpdateBy = lastUpdateBy;
        }

        public Long getId() {
                return id;
        }

        public void setId(Long id) {
                this.id = id;
        }

        public Long getBrandId() {
                return brandId;
        }

        public void setBrandId(Long brandId) {
                this.brandId = brandId;
        }

        public LocalDateTime getStartDate() {
                return startDate;
        }

        public void setStartDate(LocalDateTime startDate) {
                this.startDate = startDate;
        }

        public LocalDateTime getEndDate() {
                return endDate;
        }

        public void setEndDate(LocalDateTime endDate) {
                this.endDate = endDate;
        }

        public Long getPriceList() {
                return priceList;
        }

        public void setPriceList(Long priceList) {
                this.priceList = priceList;
        }

        public Long getProductId() {
                return productId;
        }

        public void setProductId(Long productId) {
                this.productId = productId;
        }

        public Long getPriority() {
                return priority;
        }

        public void setPriority(Long priority) {
                this.priority = priority;
        }

        public Double getPrice() {
                return price;
        }

        public void setPrice(Double price) {
                this.price = price;
        }

        public String getCurrency() {
                return currency;
        }

        public void setCurrency(String currency) {
                this.currency = currency;
        }

        public LocalDateTime getLastUpdate() {
                return lastUpdate;
        }

        public void setLastUpdate(LocalDateTime lastUpdate) {
                this.lastUpdate = lastUpdate;
        }

        public String getLastUpdateBy() {
                return lastUpdateBy;
        }

        public void setLastUpdateBy(String lastUpdateBy) {
                this.lastUpdateBy = lastUpdateBy;
        }

        public Price toDomain() {
                return new Price(
                        this.brandId,
                        this.startDate,
                        this.endDate,
                        this.priceList,
                        this.productId,
                        this.priority,
                        this.price,
                        Currency.getInstance(this.currency),
                        this.lastUpdate,
                        this.lastUpdateBy
                );
        }
}