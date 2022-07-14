package org.knowm.xchange.ascendex.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

/**
 * Package:org.knowm.xchange.ascendex.dto.marketdata
 * Description:
 *
 * @date:2022/7/14 18:07
 * @author:wodepig
 */
public class AscendexProductBaseDto {
    private final String	symbol;
    private final BigDecimal	minNotional;
    private final BigDecimal	maxNotional;
    private final BigDecimal	tickSize;
    private final BigDecimal	lotSize;
    private final AscendexProductDto.AscendexProductCommissionType	commissionType;
    private final BigDecimal	commissionReserveRate;

    public AscendexProductBaseDto(
            @JsonProperty("symbol") String symbol,
            @JsonProperty("minNotional") BigDecimal minNotional,
            @JsonProperty("maxNotional") BigDecimal maxNotional,
            @JsonProperty("commissionType") AscendexProductDto.AscendexProductCommissionType commissionType,
            @JsonProperty("commissionReserveRate") BigDecimal commissionReserveRate,
            @JsonProperty("tickSize") BigDecimal tickSize,
            @JsonProperty("lotSize") BigDecimal lotSize) {
        this.symbol = symbol;
        this.minNotional = minNotional;
        this.maxNotional = maxNotional;
        this.commissionType = commissionType;
        this.commissionReserveRate = commissionReserveRate;
        this.tickSize = tickSize;
        this.lotSize = lotSize;
    }


    public String getSymbol() {
        return symbol;
    }

    public BigDecimal getMinNotional() {
        return minNotional;
    }

    public BigDecimal getMaxNotional() {
        return maxNotional;
    }

    public BigDecimal getTickSize() {
        return tickSize;
    }

    public BigDecimal getLotSize() {
        return lotSize;
    }

    public AscendexProductDto.AscendexProductCommissionType getCommissionType() {
        return commissionType;
    }

    public BigDecimal getCommissionReserveRate() {
        return commissionReserveRate;
    }
}
