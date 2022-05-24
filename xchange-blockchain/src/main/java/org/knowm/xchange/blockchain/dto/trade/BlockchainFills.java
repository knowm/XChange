package org.knowm.xchange.blockchain.dto.trade;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.utils.jackson.CurrencyPairDeserializer;

import java.math.BigDecimal;
import java.util.Date;

@Data
@Builder
@Jacksonized
@JsonIgnoreProperties(ignoreUnknown = true)
public class BlockchainFills {

    @JsonDeserialize(using = CurrencyPairDeserializer.class)
    private final Currency symbol;
    private final Long exOrdId;
    private final Long tradeId;
    private final Long execId;
    private final String side;
    private final BigDecimal price;
    private final BigDecimal qty;
    private final BigDecimal fee;
    private final Date timestamp;
}
