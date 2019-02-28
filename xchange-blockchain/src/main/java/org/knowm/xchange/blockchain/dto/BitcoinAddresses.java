package org.knowm.xchange.blockchain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

/** @author timmolter */
public final class BitcoinAddresses {

  private final List<BitcoinAddress> bitcoinAddresses;

  /**
   * Constructor
   *
   * @param bitcoinAddresses
   */
  public BitcoinAddresses(@JsonProperty("addresses") List<BitcoinAddress> bitcoinAddresses) {

    this.bitcoinAddresses = bitcoinAddresses;
  }

  public List<BitcoinAddress> getBitcoinAddresses() {

    return bitcoinAddresses;
  }

  @Override
  public String toString() {
    return "BitcoinAddresses{" +
            "bitcoinAddresses=" + bitcoinAddresses +
            '}';
  }
}
