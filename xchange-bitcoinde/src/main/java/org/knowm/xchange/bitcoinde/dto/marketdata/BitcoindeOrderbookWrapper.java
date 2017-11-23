package org.knowm.xchange.bitcoinde.dto.marketdata;

import org.knowm.xchange.bitcoinde.dto.BitcoindeResponse;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BitcoindeOrderbookWrapper extends BitcoindeResponse {

  private final BitcoindeOrders bitcoindeOrders;

  public BitcoindeOrderbookWrapper(@JsonProperty("orders") BitcoindeOrders bitcoindeOrders, @JsonProperty("credits") int credits, @JsonProperty("errors")
      String[]
      errors) {

    super(credits, errors);
    this.bitcoindeOrders = bitcoindeOrders;

  }

  public BitcoindeOrders getBitcoindeOrders() {
    return bitcoindeOrders;
  }

  @Override
  public String toString() {
    return "BitcoindeOrderbookWrapper{" +
        "bitcoindeOrders=" + bitcoindeOrders +
        "} " + super.toString();
  }
}
