package org.knowm.xchange.blockchain.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.utils.jackson.CurrencyPairDeserializer;

import java.util.List;

@Data
@Builder
@Jacksonized
@JsonIgnoreProperties(ignoreUnknown = true)
public class BlockchainOrderBook {

    @JsonDeserialize(using = CurrencyPairDeserializer.class)
    private final CurrencyPair symbol;
    private final List<BlockchainMarketDataOrder> bids;
    private final List<BlockchainMarketDataOrder> asks;
}
