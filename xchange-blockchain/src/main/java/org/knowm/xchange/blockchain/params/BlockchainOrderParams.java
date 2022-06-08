package org.knowm.xchange.blockchain.params;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;
import org.knowm.xchange.currency.CurrencyPair;

import java.math.BigDecimal;

@Data
@Builder
@Jacksonized
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BlockchainOrderParams {
    private final String clOrdId;
    private final String ordType;
    private final CurrencyPair symbol;
    private final BigDecimal orderQty;
    private final String side;
    private final BigDecimal price;
}
