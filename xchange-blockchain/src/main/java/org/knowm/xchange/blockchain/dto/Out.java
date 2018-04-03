package org.knowm.xchange.blockchain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

/** @author timmolter */
public final class Out {

  private final String addr;
  private final long n;
  private final long txIndex;
  private final int type;
  private final long value;

  /**
   * Constructor
   *
   * @param addr
   * @param n
   * @param tx_index
   * @param type
   * @param value
   */
  public Out(
      @JsonProperty("addr") String addr,
      @JsonProperty("n") long n,
      @JsonProperty("tx_index") long tx_index,
      @JsonProperty("type") int type,
      @JsonProperty("value") long value) {

    this.addr = addr;
    this.n = n;
    this.txIndex = tx_index;
    this.type = type;
    this.value = value;
  }

  public String getAddr() {

    return this.addr;
  }

  public long getN() {

    return this.n;
  }

  public long getTxIndex() {

    return this.txIndex;
  }

  public int getType() {

    return this.type;
  }

  public long getValue() {

    return this.value;
  }
}
