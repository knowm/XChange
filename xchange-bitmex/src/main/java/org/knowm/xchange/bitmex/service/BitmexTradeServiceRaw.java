package org.knowm.xchange.bitmex.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import org.knowm.xchange.bitmex.Bitmex;
import org.knowm.xchange.bitmex.BitmexExchange;
import org.knowm.xchange.bitmex.HttpResponseAwareList;
import org.knowm.xchange.bitmex.dto.marketdata.BitmexPrivateOrder;
import org.knowm.xchange.bitmex.dto.trade.BitmexCancelAll;
import org.knowm.xchange.bitmex.dto.trade.BitmexPlaceOrderParameters;
import org.knowm.xchange.bitmex.dto.trade.BitmexPosition;
import org.knowm.xchange.bitmex.dto.trade.BitmexPrivateExecution;
import org.knowm.xchange.bitmex.dto.trade.BitmexReplaceOrderParameters;
import org.knowm.xchange.bitmex.dto.trade.PlaceOrderCommand;
import org.knowm.xchange.bitmex.dto.trade.ReplaceOrderCommand;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.utils.ObjectMapperHelper;

@SuppressWarnings({"unused", "WeakerAccess", "UnusedReturnValue"})
public class BitmexTradeServiceRaw extends BitmexBaseService {

  private String apiKey = exchange.getExchangeSpecification().getApiKey();
  /**
   * Constructor
   *
   * @param exchange {@link BitmexExchange} to be used.
   */
  BitmexTradeServiceRaw(BitmexExchange exchange) {
    super(exchange);
  }

  public List<BitmexPosition> getBitmexPositions() throws ExchangeException {
    return updateRateLimit(
        () -> bitmex.getPositions(apiKey, exchange.getNonceFactory(), signatureCreator));
  }

  public List<BitmexPosition> getBitmexPositions(String symbol) throws ExchangeException {
    return updateRateLimit(
        () ->
            bitmex.getPositions(
                apiKey,
                exchange.getNonceFactory(),
                signatureCreator,
                "{\"symbol\":\"" + symbol + "\"}"));
  }

  /**
   * See {@link Bitmex#getOrders}
   *
   * @return List of {@link BitmexPrivateOrder}s.
   */
  public List<BitmexPrivateOrder> getBitmexOrders(
      @Nullable String symbol,
      @Nullable String filter,
      @Nullable String columns,
      @Nullable Date startTime,
      @Nullable Date endTime)
      throws ExchangeException {
    ArrayList<BitmexPrivateOrder> orders = new ArrayList<>();

    for (int i = 0; orders.size() % 500 == 0; i++) {
      final int j = i;
      List<BitmexPrivateOrder> orderResponse =
          updateRateLimit(
              () ->
                  bitmex.getOrders(
                      apiKey,
                      exchange.getNonceFactory(),
                      signatureCreator,
                      symbol,
                      filter,
                      columns,
                      500,
                      (long) (j * 500),
                      true,
                      startTime,
                      endTime));
      orders.addAll(orderResponse);
      // Prevent loop when no orders found
      if (orderResponse.size() == 0) break;
    }

    return orders;
  }

  /**
   * See {@link Bitmex#getOrders}
   *
   * @return List of {@link BitmexPrivateOrder}s.
   */
  public List<BitmexPrivateOrder> getBitmexOrders() throws ExchangeException {
    return getBitmexOrders(null, null, null, null, null);
  }

  /**
   * See {@link Bitmex#placeOrder}
   *
   * @return {@link BitmexPrivateOrder} contains the results of the call.
   */
  @Nonnull
  public BitmexPrivateOrder placeOrder(@Nonnull final BitmexPlaceOrderParameters parameters)
      throws ExchangeException {
    return updateRateLimit(
        () ->
            bitmex.placeOrder(
                apiKey,
                exchange.getNonceFactory(),
                signatureCreator,
                parameters.getSymbol(),
                parameters.getSide() != null ? parameters.getSide().getCapitalized() : null,
                parameters.getOrderQuantity(),
                parameters.getSimpleOrderQuantity(),
                parameters.getDisplayQuantity(),
                parameters.getPrice(),
                parameters.getStopPrice(),
                parameters.getOrderType() != null
                    ? parameters.getOrderType().toApiParameter()
                    : null,
                parameters.getClOrdId(),
                parameters.getExecutionInstructionsAsParameter(),
                parameters.getClOrdLinkId(),
                parameters.getContingencyType() != null
                    ? parameters.getContingencyType().toApiParameter()
                    : null,
                parameters.getPegOffsetValue(),
                parameters.getPegPriceType() != null
                    ? parameters.getPegPriceType().toApiParameter()
                    : null,
                parameters.getTimeInForce() != null
                    ? parameters.getTimeInForce().toApiParameter()
                    : null,
                parameters.getText()));
  }

  /**
   * See {@link Bitmex#replaceOrder}
   *
   * @return {@link BitmexPrivateOrder} contains the results of the call.
   */
  @Nonnull
  public BitmexPrivateOrder replaceOrder(@Nonnull final BitmexReplaceOrderParameters parameters)
      throws ExchangeException {
    return updateRateLimit(
        () ->
            bitmex.replaceOrder(
                apiKey,
                exchange.getNonceFactory(),
                signatureCreator,
                parameters.getClOrdId() != null ? null : parameters.getOrderId(),
                parameters.getOrigClOrdId(),
                parameters.getClOrdId(),
                parameters.getSimpleOrderQuantity(),
                parameters.getOrderQuantity(),
                parameters.getSimpleLeavesQuantity(),
                parameters.getLeavesQuantity(),
                parameters.getPrice(),
                parameters.getStopPrice(),
                parameters.getPegOffsetValue(),
                parameters.getText()));
  }

  @Nonnull
  public List<BitmexPrivateOrder> placeOrderBulk(@Nonnull Collection<PlaceOrderCommand> commands)
      throws ExchangeException {
    String s = ObjectMapperHelper.toCompactJSON(commands);
    return updateRateLimit(
        () -> bitmex.placeOrderBulk(apiKey, exchange.getNonceFactory(), signatureCreator, s));
  }

  @Nonnull
  public List<BitmexPrivateOrder> replaceOrderBulk(
      @Nonnull Collection<ReplaceOrderCommand> commands) throws ExchangeException {
    String s = ObjectMapperHelper.toCompactJSON(commands);
    return updateRateLimit(
        () -> bitmex.replaceOrderBulk(apiKey, exchange.getNonceFactory(), signatureCreator, s));
  }

  @Nonnull
  public List<BitmexPrivateOrder> cancelAllOrders() throws ExchangeException {
    return cancelAllOrders(null, null, null);
  }

  @Nonnull
  public List<BitmexPrivateOrder> cancelAllOrders(String symbol, String filter, String text)
      throws ExchangeException {
    return updateRateLimit(
        () ->
            bitmex.cancelAllOrders(
                apiKey, exchange.getNonceFactory(), signatureCreator, symbol, filter, text));
  }

  @Nonnull
  public List<BitmexPrivateOrder> cancelBitmexOrder(String orderId) throws ExchangeException {
    return cancelBitmexOrder(orderId, null);
  }

  @Nonnull
  public List<BitmexPrivateOrder> cancelBitmexOrder(String orderId, String clOrdId)
      throws ExchangeException {
    return updateRateLimit(
        () ->
            bitmex.cancelOrder(
                apiKey,
                exchange.getNonceFactory(),
                signatureCreator,
                clOrdId != null ? null : orderId,
                clOrdId));
  }

  @Nonnull
  public BitmexPosition updateLeveragePosition(String symbol, BigDecimal leverage)
      throws ExchangeException {
    return updateRateLimit(
        () ->
            bitmex.updateLeveragePosition(
                apiKey, exchange.getNonceFactory(), signatureCreator, symbol, leverage));
  }

  public HttpResponseAwareList<BitmexPrivateExecution> getExecutions(
      String symbol,
      String filter,
      String columns,
      Integer count,
      Long start,
      Boolean reverse,
      Date startTime,
      Date endTime)
      throws ExchangeException {
    return updateRateLimit(
        () ->
            bitmex.getExecutions(
                apiKey,
                exchange.getNonceFactory(),
                signatureCreator,
                symbol,
                filter,
                columns,
                count,
                start,
                reverse,
                startTime,
                endTime));
  }

  public HttpResponseAwareList<BitmexPrivateExecution> getTradeHistory(
      String symbol,
      String filter,
      String columns,
      Integer count,
      Long start,
      Boolean reverse,
      Date startTime,
      Date endTime)
      throws ExchangeException {
    return updateRateLimit(
        () ->
            bitmex.getTradeHistory(
                apiKey,
                exchange.getNonceFactory(),
                signatureCreator,
                symbol,
                filter,
                columns,
                count,
                start,
                reverse,
                startTime,
                endTime));
  }

  public BitmexCancelAll cancelAllAfter(long timeoutMillis) throws ExchangeException {
    return updateRateLimit(
        () ->
            bitmex.cancelAllAfter(
                apiKey, exchange.getNonceFactory(), signatureCreator, timeoutMillis));
  }
}
