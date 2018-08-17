package org.knowm.xchange.coinsuper.dto.marketdata;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class CoinsuperOrderbook {

  @JsonProperty("asks")
  private List<Ask> asks = null;

  @JsonProperty("bids")
  private List<Bid> bids = null;

  /** No args constructor for use in serialization */
  public CoinsuperOrderbook() {}

  /**
   * @param asks
   * @param bids
   */
  public CoinsuperOrderbook(List<Ask> asks, List<Bid> bids) {
    super();
    this.asks = asks;
    this.bids = bids;
  }

  @JsonProperty("asks")
  public List<Ask> getAsks() {
    return asks;
  }

  @JsonProperty("asks")
  public void setAsks(List<Ask> asks) {
    this.asks = asks;
  }

  @JsonProperty("bids")
  public List<Bid> getBids() {
    return bids;
  }

  @JsonProperty("bids")
  public void setBids(List<Bid> bids) {
    this.bids = bids;
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this).append("asks", asks).append("bids", bids).toString();
  }

  public static class Ask {

    @JsonProperty("limitPrice")
    private String limitPrice;

    @JsonProperty("quantity")
    private String quantity;

    /** No args constructor for use in serialization */
    //        public Ask() {
    //        }

    //        /**
    //         *
    //         * @param quantity
    //         * @param limitPrice
    //         */
    //        public Ask(String limitPrice, String quantity) {
    //            super();
    //            this.limitPrice = limitPrice;
    //            this.quantity = quantity;
    //        }

    @JsonProperty("limitPrice")
    public String getLimitPrice() {
      return limitPrice;
    }

    @JsonProperty("limitPrice")
    public void setLimitPrice(String limitPrice) {
      this.limitPrice = limitPrice;
    }

    @JsonProperty("quantity")
    public String getQuantity() {
      return quantity;
    }

    @JsonProperty("quantity")
    public void setQuantity(String quantity) {
      this.quantity = quantity;
    }

    @Override
    public String toString() {
      return new ToStringBuilder(this)
          .append("limitPrice", limitPrice)
          .append("quantity", quantity)
          .toString();
    }
  }
  /// ---------------
  public static class Bid {
    @JsonProperty("limitPrice")
    private String limitPrice;

    @JsonProperty("quantity")
    private String quantity;

    /** No args constructor for use in serialization */
    //        public Bid() {
    //        }

    //        /**
    //         *
    //         * @param quantity
    //         * @param limitPrice
    //         */
    //        public Bid(String limitPrice, String quantity) {
    //            super();
    //            this.limitPrice = limitPrice;
    //            this.quantity = quantity;
    //        }

    @JsonProperty("limitPrice")
    public String getLimitPrice() {
      return limitPrice;
    }

    @JsonProperty("limitPrice")
    public void setLimitPrice(String limitPrice) {
      this.limitPrice = limitPrice;
    }

    @JsonProperty("quantity")
    public String getQuantity() {
      return quantity;
    }

    @JsonProperty("quantity")
    public void setQuantity(String quantity) {
      this.quantity = quantity;
    }

    @Override
    public String toString() {
      return new ToStringBuilder(this)
          .append("limitPrice", limitPrice)
          .append("quantity", quantity)
          .toString();
    }
  }
}
