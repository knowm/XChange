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
import org.knowm.xchange.bitmex.dto.marketdata.BitmexPrivateOrder;
import org.knowm.xchange.bitmex.dto.trade.BitmexPlaceOrderParameters;
import org.knowm.xchange.bitmex.dto.trade.BitmexPosition;
import org.knowm.xchange.bitmex.dto.trade.BitmexReplaceOrderParameters;
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
    try {
      return updateRateLimit(
          bitmex.getPositions(apiKey, exchange.getNonceFactory(), signatureCreator));
    } catch (Exception e) {
      throw handleError(e);
    }
  }

  public List<BitmexPosition> getBitmexPositions(String symbol) throws ExchangeException {
    try {
      return updateRateLimit(
          bitmex.getPositions(apiKey, exchange.getNonceFactory(), signatureCreator, symbol, null));
    } catch (Exception e) {
      throw handleError(e);
    }
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
    try {
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
                    columns,
                    500,
                    i * 500,
                    true,
                    startTime,
                    endTime));
        orders.addAll(orderResponse);
        // Prevent loop when no orders found
        if (orderResponse.size() == 0) break;
      }

      return orders;
    } catch (Exception e) {
      throw handleError(e);
    }
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
    try {
      return updateRateLimit(
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
              parameters.getOrderType() != null ? parameters.getOrderType().toApiParameter() : null,
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
    } catch (Exception e) {
      throw handleError(e);
    }
  }

  /**
   * See {@link Bitmex#replaceOrder}
   *
   * @return {@link BitmexPrivateOrder} contains the results of the call.
   */
  @Nonnull
  public BitmexPrivateOrder replaceOrder(@Nonnull final BitmexReplaceOrderParameters parameters)
      throws ExchangeException {
    try {
      return updateRateLimit(
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
    } catch (Exception e) {
      throw handleError(e);
    }
  }

  @Nonnull
  public List<BitmexPrivateOrder> placeOrderBulk(
      @Nonnull Collection<Bitmex.PlaceOrderCommand> commands) throws ExchangeException {
    try {
      String s = ObjectMapperHelper.toCompactJSON(commands);
      return updateRateLimit(
          bitmex.placeOrderBulk(apiKey, exchange.getNonceFactory(), signatureCreator, s));
    } catch (Exception e) {
      throw handleError(e);
    }
  }

  @Nonnull
  public List<BitmexPrivateOrder> replaceOrderBulk(
      @Nonnull Collection<Bitmex.ReplaceOrderCommand> commands) throws ExchangeException {
    try {
      String s = ObjectMapperHelper.toCompactJSON(commands);
      return bitmex.replaceOrderBulk(apiKey, exchange.getNonceFactory(), signatureCreator, s);
    } catch (Exception e) {
      throw handleError(e);
    }
  }

  @Nonnull
  public List<BitmexPrivateOrder> cancelAllOrders() throws ExchangeException {
    return cancelAllOrders(null, null, null);
  }

  @Nonnull
  public List<BitmexPrivateOrder> cancelAllOrders(String symbol, String filter, String text)
      throws ExchangeException {
    try {
      return updateRateLimit(
          bitmex.cancelAllOrders(
              apiKey, exchange.getNonceFactory(), signatureCreator, symbol, filter, text));
    } catch (Exception e) {
      throw handleError(e);
    }
  }

  @Nonnull
  public List<BitmexPrivateOrder> cancelBitmexOrder(String orderId) throws ExchangeException {
    return cancelBitmexOrder(orderId, null);
  }

  @Nonnull
  public List<BitmexPrivateOrder> cancelBitmexOrder(String orderId, String clOrdId)
      throws ExchangeException {
    try {
      return updateRateLimit(
          bitmex.cancelOrder(
              apiKey,
              exchange.getNonceFactory(),
              signatureCreator,
              clOrdId != null ? null : orderId,
              clOrdId));
    } catch (Exception e) {
      throw handleError(e);
    }
  }

  @Nonnull
  public BitmexPosition updateLeveragePosition(String symbol, BigDecimal leverage)
      throws ExchangeException {
    try {
      return updateRateLimit(
          bitmex.updateLeveragePosition(
              apiKey, exchange.getNonceFactory(), signatureCreator, symbol, leverage));
    } catch (Exception e) {
      throw handleError(e);
    }
  }
}
