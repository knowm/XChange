package org.knowm.xchange.bibox.dto.marketdata;

/** @author odrotleff */
public class BiboxOrderBookCommandBody {
  private String pair;
  private Integer size;

  public BiboxOrderBookCommandBody(String pair, Integer size) {
    super();
    this.pair = pair;
    this.size = size;
  }

  public String getPair() {
    return pair;
  }

  public Integer getSize() {
    return size;
  }
}
