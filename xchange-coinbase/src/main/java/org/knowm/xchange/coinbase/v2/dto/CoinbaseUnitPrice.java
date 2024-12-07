package org.knowm.xchange.coinbase.v2.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.ToString;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.utils.Assert;

import java.math.BigDecimal;

@ToString
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class CoinbaseUnitPrice {

    private final Currency currency;
    private final BigDecimal amount;
    private final int scale;

    @JsonCreator
    public CoinbaseUnitPrice(
            @JsonProperty("amount") BigDecimal amount, @JsonProperty("currency") String currency, @JsonProperty("scale") int scale) {
        this.amount = amount;
        this.currency = Currency.getInstance(currency);
        this.scale = scale;
    }

}
