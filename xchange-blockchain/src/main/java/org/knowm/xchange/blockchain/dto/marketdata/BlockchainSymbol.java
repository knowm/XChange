package org.knowm.xchange.blockchain.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;
import org.knowm.xchange.currency.Currency;

@Data
@Builder
@Jacksonized
@JsonIgnoreProperties(ignoreUnknown = true)
public class BlockchainSymbol {

    private final Integer id;
    private final Currency base_currency;
    private final Currency counter_currency;
    private final String status;
}
