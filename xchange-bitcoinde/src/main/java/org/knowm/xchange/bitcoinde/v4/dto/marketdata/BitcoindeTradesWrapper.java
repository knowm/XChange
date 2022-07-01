package org.knowm.xchange.bitcoinde.v4.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.EqualsAndHashCode;
import lombok.Value;
import org.knowm.xchange.bitcoinde.v4.dto.BitcoindeError;
import org.knowm.xchange.bitcoinde.v4.dto.BitcoindeMaintenance;
import org.knowm.xchange.bitcoinde.v4.dto.BitcoindeResponse;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.utils.jackson.CurrencyPairDeserializer;

@Value
@EqualsAndHashCode(callSuper = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class BitcoindeTradesWrapper extends BitcoindeResponse {

  CurrencyPair tradingPair;
  BitcoindeTrade[] trades;

  @JsonCreator
  public BitcoindeTradesWrapper(
      @JsonProperty("trading_pair") @JsonDeserialize(using = CurrencyPairDeserializer.class)
          CurrencyPair tradingPair,
      @JsonProperty("trades") BitcoindeTrade[] trades,
      @JsonProperty("credits") Integer credits,
      @JsonProperty("errors") BitcoindeError[] errors,
      @JsonProperty("maintenance") BitcoindeMaintenance maintenance,
      @JsonProperty("nonce") Long nonce) {
    super(credits, errors, maintenance, nonce);
    this.tradingPair = tradingPair;
    this.trades = trades;
  }
}
