package org.knowm.xchange.bitcoinde.v4.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Value;
import org.knowm.xchange.bitcoinde.v4.dto.BitcoindeError;
import org.knowm.xchange.bitcoinde.v4.dto.BitcoindeMaintenance;
import org.knowm.xchange.bitcoinde.v4.dto.BitcoindeResponse;

@Value
@EqualsAndHashCode(callSuper = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class BitcoindeOrderbookWrapper extends BitcoindeResponse {

  BitcoindeOrder[] bitcoindeOrders;

  @JsonCreator
  public BitcoindeOrderbookWrapper(
      @JsonProperty("orders") BitcoindeOrder[] bitcoindeOrders,
      @JsonProperty("credits") Integer credits,
      @JsonProperty("errors") BitcoindeError[] errors,
      @JsonProperty("maintenance") BitcoindeMaintenance maintenance,
      @JsonProperty("nonce") Long nonce) {
    super(credits, errors, maintenance, nonce);
    this.bitcoindeOrders = bitcoindeOrders;
  }
}
