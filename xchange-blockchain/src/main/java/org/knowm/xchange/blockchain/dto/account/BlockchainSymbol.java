package org.knowm.xchange.blockchain.dto.account;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;
import org.knowm.xchange.currency.Currency;

@Data
@Builder
@Jacksonized
@JsonIgnoreProperties(ignoreUnknown = true)
public class BlockchainSymbol {

  @JsonProperty("base_currency")
  private final Currency baseCurrency;

  @JsonProperty("base_currency_scale")
  private final Integer baseCurrencyScale;

  @JsonProperty("counter_currency")
  private final Currency counterCurrency;

  @JsonProperty("counter_currency_scale")
  private final Integer counterCurrencyScale;

  @JsonProperty("min_price_increment")
  private final Integer minPriceIncrement;

  @JsonProperty("min_price_increment_scale")
  private final Integer minPriceIncrementScale;

  @JsonProperty("min_order_size")
  private final BigDecimal minOrderSize;

  @JsonProperty("min_order_size_scale")
  private final Integer minOrderSizeScale;

  @JsonProperty("max_order_size")
  private final BigDecimal maxOrderSize;

  @JsonProperty("max_order_size_scale")
  private final Integer maxOrderSizeScale;

  @JsonProperty("lot_size")
  private final Integer lotSize;

  @JsonProperty("lot_size_scale")
  private final Integer lotSizeScale;

  private final String status;
  private final Integer id;

  @JsonProperty("auction_price")
  private final Integer auctionPrice;

  @JsonProperty("auction_size")
  private final Integer auctionSize;

  @JsonProperty("auction_time")
  private final Long auctionTime;

  private final Integer imbalance;
}
