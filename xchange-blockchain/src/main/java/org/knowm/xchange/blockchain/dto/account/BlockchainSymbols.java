package org.knowm.xchange.blockchain.dto.account;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;
import org.knowm.xchange.currency.Currency;

@Data
@Builder
@Jacksonized
@JsonIgnoreProperties(ignoreUnknown = true)
public class BlockchainSymbols {

    private final Currency baseCurrency;
    private final Integer baseCurrencyScale;
    private final Currency counterCurrency;
    private final Integer counterCurrencyScale;
    private final Integer minPriceIncrement;
    private final Integer minPriceIncrementScale;
    private final Long minOrderSize;
    private final Integer minOrderSizeScale;
    private final Integer maxOrderSize;
    private final Integer maxOrderSizeScale;
    private final Integer lotSize;
    private final Integer lotSizeScale;
    private final String  status;
    private final Integer id;
    private final Integer auctionPrice;
    private final Integer auctionSize;
    private final Long auctionTime;
    private final Integer imbalance;
}
