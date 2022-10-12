package org.knowm.xchange.binance.dto.trade.margin;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.knowm.xchange.binance.dto.trade.*;

import java.math.BigDecimal;
import java.util.List;

public final class BinanceNewMarginOrder extends BinanceNewOrder {

  public final BigDecimal marginBuyBorrowAmount;
  public final String marginBuyBorrowAsset;
  public final MarginAccountType marginAccountType;

  public BinanceNewMarginOrder(
          @JsonProperty("symbol") String symbol,
          @JsonProperty("orderId") long orderId,
          @JsonProperty("clientOrderId") String clientOrderId,
          @JsonProperty("transactTime") long transactTime,
          @JsonProperty("price") BigDecimal price,
          @JsonProperty("origQty") BigDecimal origQty,
          @JsonProperty("executedQty") BigDecimal executedQty,
          @JsonProperty("status") OrderStatus status,
          @JsonProperty("timeInForce") TimeInForce timeInForce,
          @JsonProperty("type") OrderType type,
          @JsonProperty("side") OrderSide side,
          @JsonProperty("fills") List<BinanceTrade> fills,
          @JsonProperty("marginBuyBorrowAmount") BigDecimal marginBuyBorrowAmount,
          @JsonProperty("marginBuyBorrowAsset") String marginBuyBorrowAsset,
          @JsonProperty("isIsolated") MarginAccountType marginAccountType) {
    super(symbol, orderId, clientOrderId, transactTime, price, origQty, executedQty, status, timeInForce, type, side, fills);
    this.marginBuyBorrowAmount = marginBuyBorrowAmount;
    this.marginBuyBorrowAsset = marginBuyBorrowAsset;
    this.marginAccountType = marginAccountType;
  }
}
