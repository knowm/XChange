package info.bitrich.xchangestream.bitflyer.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import org.knowm.xchange.currency.CurrencyPair;

/** Created by Lukas Zaoralek on 14.11.17. */
public class BitflyerPubNubOrderbookTransaction {
  private final BigDecimal midPrice;
  private final BitflyerLimitOrder[] bids;
  private final BitflyerLimitOrder[] asks;

  public BitflyerPubNubOrderbookTransaction(
      @JsonProperty("mid_price") BigDecimal midPrice,
      @JsonProperty("bids") BitflyerLimitOrder[] bids,
      @JsonProperty("asks") BitflyerLimitOrder[] asks) {
    this.midPrice = midPrice;
    this.bids = bids;
    this.asks = asks;
  }

  public BitflyerOrderbook toBitflyerOrderbook(CurrencyPair pair) {
    return new BitflyerOrderbook(pair, asks, bids);
  }

  public BigDecimal getMidPrice() {
    return midPrice;
  }

  public BitflyerLimitOrder[] getBids() {
    return bids;
  }

  public BitflyerLimitOrder[] getAsks() {
    return asks;
  }
}
