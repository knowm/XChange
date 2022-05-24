package org.knowm.xchange.blockchain.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.utils.jackson.CurrencyPairDeserializer;

import java.math.BigDecimal;

@Data
@Builder
@Jacksonized
@JsonIgnoreProperties(ignoreUnknown = true)
public class BlockchainTicker {

    @JsonDeserialize(using = CurrencyPairDeserializer.class)
    private final CurrencyPair symbol;
    @JsonProperty("price_24h")
    private final BigDecimal price;
    @JsonProperty("volume_24h")
    private final BigDecimal volume;
    @JsonProperty("last_trade_price")
    private final BigDecimal lastTradePrice;
}
