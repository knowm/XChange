package org.knowm.xchange.bithumb.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

public class BithumbTransactionHistory {

    private final long contNo;
    private final String transactionDate;
    private final String type;
    private final BigDecimal unitsTraded;
    private final BigDecimal price;
    private final BigDecimal total;

    public BithumbTransactionHistory(
            @JsonProperty("cont_no") long contNo,
            @JsonProperty("transaction_date") String transactionDate,
            @JsonProperty("type") String type,
            @JsonProperty("units_traded") BigDecimal unitsTraded,
            @JsonProperty("price") BigDecimal price,
            @JsonProperty("total") BigDecimal total) {
        this.contNo = contNo;
        this.transactionDate = transactionDate;
        this.type = type;
        this.unitsTraded = unitsTraded;
        this.price = price;
        this.total = total;
    }

    public long getContNo() {
        return contNo;
    }

    public String getTransactionDate() {
        return transactionDate;
    }

    public String getType() {
        return type;
    }

    public BigDecimal getUnitsTraded() {
        return unitsTraded;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public BigDecimal getTotal() {
        return total;
    }

    @Override
    public String toString() {
        return "BithumbTransactionHistory{" +
                "contNo=" + contNo +
                ", transactionDate='" + transactionDate + '\'' +
                ", type='" + type + '\'' +
                ", unitsTraded=" + unitsTraded +
                ", price=" + price +
                ", total=" + total +
                '}';
    }
}
