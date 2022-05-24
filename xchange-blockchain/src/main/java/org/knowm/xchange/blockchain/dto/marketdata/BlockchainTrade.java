package org.knowm.xchange.blockchain.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

import java.math.BigDecimal;
import java.util.Date;

import static org.knowm.xchange.blockchain.BlockchainConstants.BUY;

@Data
@Builder
@Jacksonized
@JsonIgnoreProperties(ignoreUnknown = true)
public class BlockchainTrade {

    @JsonProperty("trade_id")
    private final String id;
    private final BigDecimal price;
    @JsonProperty("qty")
    private final BigDecimal quantity;
    private final String side;
    private final Date timestamp;

    public boolean isBuyer() {
        return BUY.equals(this.side.toUpperCase());
    }
}
