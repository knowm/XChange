package org.knowm.xchange.blockchain.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author timmolter
 */
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

}
