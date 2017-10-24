package org.knowm.xchange.cexio.dto.trade;

import com.fasterxml.jackson.annotation.JsonProperty;

/*
{
  "user": "up106404164",
  "type": "buy",
  "symbol1": "ETH",
  "symbol2": "BTC",
  "amount": "1.00000000",
  "remains": "1.00000000",
  "price": "0.001",
  "time": 1498106969790,
  "lastTxTime": "1498106969790",
  "tradingFeeStrategy": "userVolumeAmount",
  "tradingFeeTaker": "0.20",
  "tradingFeeMaker": "0",
  "tradingFeeUserVolumeAmount": "239273248",
  "a:BTC:c": "0.00100200",
  "a:BTC:s": "0.00100000",
  "a:BTC:d": "0.00000200",
  "lastTx": "3918995112",
  "status": "a",
  "orderId": "3918995110",
  "id": "3918995110"
}
*/

public class CexIOOpenOrder {
  public final String user;
  public final String type;
  public final String symbol1;
  public final String symbol2;
  public final String amount;
  public final String remains;
  public final String price;
  public final long time;
  public final String lastTxTime;
  public final String tradingFeeStrategy;
  public final String tradingFeeTaker;
  public final String tradingFeeMaker;
  public final String tradingFeeUserVolumeAmount;
  public final String lastTx;
  public final String status;
  public final String orderId;
  public final String id;

  public CexIOOpenOrder(@JsonProperty("user") String user, @JsonProperty("type") String type, @JsonProperty("symbol1") String symbol1, @JsonProperty("symbol2") String symbol2,
      @JsonProperty("amount") String amount, @JsonProperty("remains") String remains, @JsonProperty("price") String price, @JsonProperty("time") long time,
      @JsonProperty("lastTxTime") String lastTxTime, @JsonProperty("tradingFeeStrategy") String tradingFeeStrategy, @JsonProperty("tradingFeeTaker") String tradingFeeTaker,
      @JsonProperty("tradingFeeMaker") String tradingFeeMaker, @JsonProperty("tradingFeeUserVolumeAmount") String tradingFeeUserVolumeAmount, @JsonProperty("lastTx") String lastTx,
      @JsonProperty("status") String status, @JsonProperty("orderId") String orderId, @JsonProperty("id") String id) {
    this.user = user;
    this.type = type;
    this.symbol1 = symbol1;
    this.symbol2 = symbol2;
    this.amount = amount;
    this.remains = remains;
    this.price = price;
    this.time = time;
    this.lastTxTime = lastTxTime;
    this.tradingFeeStrategy = tradingFeeStrategy;
    this.tradingFeeTaker = tradingFeeTaker;
    this.tradingFeeMaker = tradingFeeMaker;
    this.tradingFeeUserVolumeAmount = tradingFeeUserVolumeAmount;
    this.lastTx = lastTx;
    this.status = status;
    this.orderId = orderId;
    this.id = id;
  }

}
