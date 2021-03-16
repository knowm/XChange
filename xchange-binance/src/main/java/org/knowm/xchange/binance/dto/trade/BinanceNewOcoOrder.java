package org.knowm.xchange.binance.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class BinanceNewOcoOrder {
  public final Long orderListId;
  public final ContingencyType contingencyType;
  public final OcoStatus listStatusType;
  public final OcoOrderStatus listOrderStatus;
  public final String listClientOrderId;
  public final Long transactionTime;
  public final String symbol;
  public final List<BinanceOrder> orders;
  public final List<BinanceOrder> orderReports;

  public BinanceNewOcoOrder(
      @JsonProperty("orderListId") Long orderListId,
      @JsonProperty("contingencyType") ContingencyType contingencyType,
      @JsonProperty("listStatusType") OcoStatus listStatusType,
      @JsonProperty("listOrderStatus") OcoOrderStatus listOrderStatus,
      @JsonProperty("listClientOrderId") String listClientOrderId,
      @JsonProperty("transactionTime") Long transactionTime,
      @JsonProperty("symbol") String symbol,
      @JsonProperty("orders") List<BinanceOrder> orders,
      @JsonProperty("orderReports") List<BinanceOrder> orderReports) {
    this.orderListId = orderListId;
    this.contingencyType = contingencyType;
    this.listStatusType = listStatusType;
    this.listOrderStatus = listOrderStatus;
    this.listClientOrderId = listClientOrderId;
    this.transactionTime = transactionTime;
    this.symbol = symbol;
    this.orders = orders;
    this.orderReports = orderReports;
  }
}
