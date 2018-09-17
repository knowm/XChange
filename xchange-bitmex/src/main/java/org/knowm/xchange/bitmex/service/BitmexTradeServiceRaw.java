package org.knowm.xchange.bitmex.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.knowm.xchange.bitmex.Bitmex;
import org.knowm.xchange.bitmex.BitmexException;
import org.knowm.xchange.bitmex.BitmexExchange;
import org.knowm.xchange.bitmex.dto.marketdata.BitmexPrivateOrder;
import org.knowm.xchange.bitmex.dto.trade.BitmexPosition;
import org.knowm.xchange.bitmex.dto.trade.BitmexSide;
import org.knowm.xchange.utils.ObjectMapperHelper;

public class BitmexTradeServiceRaw extends BitmexBaseService {

  public static final String ORDER_TYPE_LIMIT = "Limit";
  public static final String ORDER_TYPE_STOP = "Stop";

  /**
   * Constructor
   *
   * @param exchange
   */
  String apiKey = exchange.getExchangeSpecification().getApiKey();

  public BitmexTradeServiceRaw(BitmexExchange exchange) {

    super(exchange);
  }

  public List<BitmexPosition> getBitmexPositions() throws IOException {

    try {
      return updateRateLimit(
          bitmex.getPositions(apiKey, exchange.getNonceFactory(), signatureCreator));
    } catch (BitmexException e) {
      throw handleError(e);
    }
  }

  public List<BitmexPosition> getBitmexPositions(String symbol) throws IOException {

    try {
      return updateRateLimit(
          bitmex.getPositions(apiKey, exchange.getNonceFactory(), signatureCreator, symbol, null));
    } catch (BitmexException e) {
      throw handleError(e);
    }
  }

  public List<BitmexPrivateOrder> getBitmexOrders(String symbol, String filter) throws IOException {
    ArrayList<BitmexPrivateOrder> orders = new ArrayList<>();

    for (int i = 0; orders.size() % 500 == 0; i++) {
      List<BitmexPrivateOrder> orderResponse =
          updateRateLimit(
              bitmex.getOrders(
                  apiKey,
                  exchange.getNonceFactory(),
                  signatureCreator,
                  symbol,
                  filter,
                  500,
                  i * 500,
                  true,
                  null,
                  null));
      orders.addAll(orderResponse);
      // Prevent loop when no orders found
      if (orderResponse.size() == 0) break;
    }

    return orders;
  }

  public List<BitmexPrivateOrder> getBitmexOrders() throws IOException {
    return getBitmexOrders(null, null);
  }

  public BitmexPrivateOrder placeMarketOrder(
      String symbol, BitmexSide side, BigDecimal orderQuantity, String executionInstructions)
      throws IOException {
    return bitmex.placeOrder(
        apiKey,
        exchange.getNonceFactory(),
        signatureCreator,
        symbol,
        side == null ? null : side.getCapitalized(),
        orderQuantity,
        null,
        null,
        null,
        "Market",
        null,
        executionInstructions,
        null,
        null);
  }

  public BitmexPrivateOrder placeLimitOrder(
      String symbol,
      BigDecimal orderQuantity,
      BigDecimal price,
      BitmexSide side,
      String clOrdID,
      String executionInstructions)
      throws IOException {
    return placeLimitOrder(
        symbol, orderQuantity, price, side, clOrdID, executionInstructions, null, null);
  }

  public BitmexPrivateOrder placeLimitOrder(
      String symbol,
      BigDecimal orderQuantity,
      BigDecimal price,
      BitmexSide side,
      String clOrdID,
      String executionInstructions,
      String clOrdLinkID,
      String contingencyType)
      throws IOException {
    return updateRateLimit(
        bitmex.placeOrder(
            apiKey,
            exchange.getNonceFactory(),
            signatureCreator,
            symbol,
            side == null ? null : side.getCapitalized(),
            orderQuantity,
            null,
            price,
            null,
            ORDER_TYPE_LIMIT,
            clOrdID,
            executionInstructions,
            clOrdLinkID,
            contingencyType));
  }

  public List<BitmexPrivateOrder> placeLimitOrderBulk(Collection<Bitmex.PlaceOrderCommand> commands)
      throws IOException {
    String s = ObjectMapperHelper.toCompactJSON(commands);
    return updateRateLimit(
        bitmex.placeOrderBulk(apiKey, exchange.getNonceFactory(), signatureCreator, s));
  }

  public List<BitmexPrivateOrder> replaceLimitOrderBulk(
      Collection<Bitmex.ReplaceOrderCommand> commands) {
    String s = ObjectMapperHelper.toCompactJSON(commands);
    return bitmex.replaceOrderBulk(apiKey, exchange.getNonceFactory(), signatureCreator, s);
  }

  public BitmexPrivateOrder replaceLimitOrder(
      BigDecimal orderQuantity,
      BigDecimal price,
      String orderId,
      String clOrdID,
      String origClOrdID,
      String clOrdLinkID,
      String contingencyType)
      throws IOException {

    return updateRateLimit(
        bitmex.replaceOrder(
            apiKey,
            exchange.getNonceFactory(),
            signatureCreator,
            orderQuantity,
            price,
            null,
            ORDER_TYPE_LIMIT,
            // if clOrdID is not null we should not send orderID
            clOrdID != null ? null : orderId,
            clOrdID,
            origClOrdID,
            clOrdLinkID,
            contingencyType));
  }

  public BitmexPrivateOrder replaceStopOrder(
      BigDecimal orderQuantity,
      BigDecimal price,
      String orderID,
      String clOrdID,
      String origClOrdId,
      String clOrdLinkID,
      String contingencyType)
      throws IOException {
    return updateRateLimit(
        bitmex.replaceOrder(
            apiKey,
            exchange.getNonceFactory(),
            signatureCreator,
            orderQuantity,
            null,
            price,
            ORDER_TYPE_LIMIT,
            clOrdID != null ? null : orderID,
            clOrdID,
            origClOrdId,
            clOrdLinkID,
            contingencyType));
  }

  public BitmexPrivateOrder placeStopOrder(
      String symbol,
      BitmexSide side,
      BigDecimal orderQuantity,
      BigDecimal stopPrice,
      String executionInstructions,
      String clOrdID,
      String clOrdLinkID,
      String contingencyType)
      throws IOException {
    return updateRateLimit(
        bitmex.placeOrder(
            apiKey,
            exchange.getNonceFactory(),
            signatureCreator,
            symbol,
            side == null ? null : side.getCapitalized(),
            orderQuantity,
            null,
            null,
            stopPrice,
            ORDER_TYPE_STOP,
            clOrdID,
            executionInstructions,
            clOrdLinkID,
            contingencyType));
  }

  /**
   * @param symbol
   * @param orderQuantity Order quantity in units of the instrument (i.e. contracts).
   * @param side Order side. Valid options: Buy, Sell. Defaults to 'Buy' unless orderQty or
   *     simpleOrderQty is negative.
   * @param simpleOrderQuantity Order quantity in units of the underlying instrument (i.e. Bitcoin).
   * @param price
   * @param executionInstructions Optional execution instructions. Valid options:
   *     ParticipateDoNotInitiate, AllOrNone, MarkPrice, IndexPrice, LastPrice, Close, ReduceOnly,
   *     Fixed. 'AllOrNone' instruction requires displayQty to be 0. 'MarkPrice', 'IndexPrice' or
   *     'LastPrice' instruction valid for 'Stop', 'StopLimit', 'MarketIfTouched', and
   *     'LimitIfTouched' orders.
   * @param clOrdLinkID Optional Client Order Link ID for contingent orders.
   * @param contingencyType Optional contingency type for use with clOrdLinkID. Valid options:
   *     OneCancelsTheOther, OneTriggersTheOther, OneUpdatesTheOtherAbsolute,
   *     OneUpdatesTheOtherProportional.
   * @return
   */
  public BitmexPrivateOrder placeLimitOrder(
      String symbol,
      BitmexSide side,
      BigDecimal orderQuantity,
      BigDecimal simpleOrderQuantity,
      BigDecimal price,
      String executionInstructions,
      String clOrdLinkID,
      String contingencyType)
      throws IOException {
    return updateRateLimit(
        bitmex.placeOrder(
            apiKey,
            exchange.getNonceFactory(),
            signatureCreator,
            symbol,
            side == null ? null : side.getCapitalized(),
            orderQuantity,
            simpleOrderQuantity,
            price,
            null,
            ORDER_TYPE_LIMIT,
            null,
            executionInstructions,
            clOrdLinkID,
            contingencyType));
  }

  public List<BitmexPrivateOrder> cancelAllOrders() throws IOException {
    return cancelAllOrders(null, null, null);
  }

  public List<BitmexPrivateOrder> cancelAllOrders(String symbol, String filter, String text)
      throws IOException {
    return updateRateLimit(
        bitmex.cancelAllOrders(
            apiKey, exchange.getNonceFactory(), signatureCreator, symbol, filter, text));
  }

  public List<BitmexPrivateOrder> cancelBitmexOrder(String orderID) throws IOException {
    return cancelBitmexOrder(orderID, null);
  }

  public List<BitmexPrivateOrder> cancelBitmexOrder(String orderID, String clOrdID)
      throws IOException {
    List<BitmexPrivateOrder> orders =
        updateRateLimit(
            bitmex.cancelOrder(
                apiKey,
                exchange.getNonceFactory(),
                signatureCreator,
                clOrdID != null ? null : orderID,
                clOrdID));
    return orders;
  }

  public BitmexPosition updateLeveragePosition(String symbol, BigDecimal leverage)
      throws IOException {
    BitmexPosition order =
        updateRateLimit(
            bitmex.updateLeveragePosition(
                apiKey, exchange.getNonceFactory(), signatureCreator, symbol, leverage));
    return order;
  }
}
