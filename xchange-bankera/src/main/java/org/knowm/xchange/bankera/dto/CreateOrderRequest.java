package org.knowm.xchange.bankera.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import org.apache.commons.lang3.StringUtils;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class CreateOrderRequest {

  private static final String LIMIT_ORDER_TYPE = "limit";
  private static final String MARKET_ORDER_TYPE = "market";

  @JsonProperty("market")
  private final String market;

  @JsonProperty("side")
  private final String side;

  @JsonProperty("type")
  private final String type;

  @JsonProperty("price")
  private final String price;

  @JsonProperty("amount")
  private final String amount;

  @JsonProperty("client_order_id")
  private final String clientOrderId;

  @JsonProperty("nonce")
  private final Long nonce;

  public CreateOrderRequest(
      String market, String side, BigDecimal amount, String clientOrderId, Long nonce) {
    this.market = market;
    this.side = side;
    this.amount = amount.toPlainString();
    this.type = MARKET_ORDER_TYPE;
    this.price = StringUtils.EMPTY;
    this.clientOrderId = clientOrderId;
    this.nonce = nonce;
  }

  public CreateOrderRequest(
      String market,
      String side,
      BigDecimal amount,
      BigDecimal price,
      String clientOrderId,
      Long nonce) {
    this.market = market;
    this.side = side;
    this.amount = amount.toPlainString();
    this.type = LIMIT_ORDER_TYPE;
    this.price = price.toPlainString();
    this.clientOrderId = clientOrderId;
    this.nonce = nonce;
  }

  public enum Side {
    BUY("buy"),
    SELL("sell");

    private String side;

    Side(String side) {
      this.side = side;
    }

    public String getSide() {
      return this.side;
    }

    public static Side getEnum(String side) {
      for (Side currentEnum : Side.values()) {
        if (currentEnum.getSide().equalsIgnoreCase(side)) {
          return currentEnum;
        }
      }
      throw new UnsupportedOperationException("Unknown order side: " + side);
    }
  }
}
