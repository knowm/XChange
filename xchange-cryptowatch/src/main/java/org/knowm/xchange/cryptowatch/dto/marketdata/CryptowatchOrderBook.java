package org.knowm.xchange.cryptowatch.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class CryptowatchOrderBook {

  private final List<CryptowatchPublicOrder> asks;
  private final List<CryptowatchPublicOrder> bids;
  private final int seqNum;

  public CryptowatchOrderBook(
      @JsonProperty("asks") List<CryptowatchPublicOrder> asks,
      @JsonProperty("bids") List<CryptowatchPublicOrder> bids,
      @JsonProperty("seqNum") int seqNum) {
    this.asks = asks;
    this.bids = bids;
    this.seqNum = seqNum;
  }
}
