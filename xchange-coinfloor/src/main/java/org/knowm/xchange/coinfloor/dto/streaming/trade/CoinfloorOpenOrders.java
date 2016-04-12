package org.knowm.xchange.coinfloor.dto.streaming.trade;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.knowm.xchange.coinfloor.dto.streaming.CoinfloorOrder;

/**
 * @author obsessiveOrange
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class CoinfloorOpenOrders {

  private final int tag;
  private final int errorCode;
  private final List<CoinfloorOrder> orders;

  /**
   * Constructor
   * 
   * @param balance
   * @param op
   * @param amount
   */
  public CoinfloorOpenOrders(@JsonProperty("tag") int tag, @JsonProperty("error_code") int errorCode,
      @JsonProperty("orders") List<CoinfloorOrder> orders) {

    this.tag = tag;
    this.errorCode = errorCode;
    this.orders = orders;
  }

  public int getTag() {

    return tag;
  }

  public int getErrorCode() {

    return errorCode;
  }

  public List<CoinfloorOrder> getOrders() {

    return orders;
  }

  @Override
  public String toString() {

    return "CoinfloorOpenOrders{tag='" + tag + "',errorCode='" + errorCode + "',orders='" + orders + "}";
  }
}
