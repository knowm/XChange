package org.knowm.xchange.bithumb.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.knowm.xchange.bithumb.BithumbAdapters;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BithumbTransactionHistory {

    public static final SimpleDateFormat TRANSACTION_DATE_FORMAT =
            new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private final long contNo;
    private final String transactionDate;
    private final BithumbAdapters.OrderType type;
    private final BigDecimal unitsTraded;
    private final BigDecimal price;
    private final BigDecimal total;

    public BithumbTransactionHistory(
            @JsonProperty("cont_no") long contNo,
            @JsonProperty("transaction_date") String transactionDate,
            @JsonProperty("type") BithumbAdapters.OrderType type,
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

    public BithumbAdapters.OrderType getType() {
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

    public Date getTimestamp() {
        try {
            return TRANSACTION_DATE_FORMAT.parse(transactionDate);
        } catch (ParseException e) {
            return null;
        }
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
