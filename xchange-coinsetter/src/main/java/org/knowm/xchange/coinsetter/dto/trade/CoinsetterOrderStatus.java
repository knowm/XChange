package org.knowm.xchange.coinsetter.dto.trade;

import java.math.BigDecimal;
import java.util.UUID;

import org.knowm.xchange.coinsetter.service.streaming.CoinsetterSocketIOServiceRaw;

/**
 * Order status pushed from {@link CoinsetterSocketIOServiceRaw}.
 */
public class CoinsetterOrderStatus {

  private final UUID uuid;
  private final UUID customerUuid;
  private final BigDecimal filledQuantity;
  private final String orderType;
  private final String stage;
  private final BigDecimal requestedQuantity;
  private final BigDecimal requestedPrice;
  private final String side;
  private final String symbol;
  private final String exchId;

  public CoinsetterOrderStatus(UUID uuid, UUID customerUuid, BigDecimal filledQuantity, String orderType, String stage, BigDecimal requestedQuantity,
      BigDecimal requestedPrice, String side, String symbol, String exchId) {

    this.uuid = uuid;
    this.customerUuid = customerUuid;
    this.filledQuantity = filledQuantity;
    this.orderType = orderType;
    this.stage = stage;
    this.requestedQuantity = requestedQuantity;
    this.requestedPrice = requestedPrice;
    this.side = side;
    this.symbol = symbol;
    this.exchId = exchId;
  }

  public UUID getUuid() {

    return uuid;
  }

  public UUID getCustomerUuid() {

    return customerUuid;
  }

  public BigDecimal getFilledQuantity() {

    return filledQuantity;
  }

  public String getOrderType() {

    return orderType;
  }

  public String getStage() {

    return stage;
  }

  public BigDecimal getRequestedQuantity() {

    return requestedQuantity;
  }

  public BigDecimal getRequestedPrice() {

    return requestedPrice;
  }

  public String getSide() {

    return side;
  }

  public String getSymbol() {

    return symbol;
  }

  public String getExchId() {

    return exchId;
  }

}
