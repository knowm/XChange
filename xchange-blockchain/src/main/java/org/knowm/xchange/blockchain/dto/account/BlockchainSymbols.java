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
    private final Currency base_currency;
    private final Integer base_currency_scale;
    private final Currency counter_currency;
    private final Integer counter_currency_scale;
    private final Integer min_price_increment;
    private final Integer min_price_increment_scale;
    private final Long min_order_size;
    private final Integer min_order_size_scale;
    private final Integer max_order_size;
    private final Integer max_order_size_scale;
    private final Integer lot_size;
    private final Integer lot_size_scale;
    private final String  status;
    private final Integer id;
    private final Integer auction_price;
    private final Integer auction_size;
    private final Long    auction_time;
    private final Integer imbalance;
}
