package org.knowm.xchange.bitmex.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import javax.annotation.Nullable;
import org.apache.commons.lang3.StringUtils;
import org.knowm.xchange.bitmex.Bitmex;
import org.knowm.xchange.bitmex.BitmexException;
import org.knowm.xchange.bitmex.BitmexExchange;
import org.knowm.xchange.bitmex.dto.marketdata.BitmexPrivateOrder;
import org.knowm.xchange.bitmex.dto.trade.*;
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

  public List<BitmexPosition> getBitmexPositions() throws IOException, ExchangeException {
    try {
      return updateRateLimit(
          bitmex.getPositions(apiKey, exchange.getNonceFactory(), signatureCreator));
    } catch (BitmexException e) {
      throw handleError(e);
    }
  }

  public List<BitmexPosition> getBitmexPositions(String symbol)
      throws IOException, ExchangeException {
    try {
      return updateRateLimit(
          bitmex.getPositions(apiKey, exchange.getNonceFactory(), signatureCreator, symbol, null));
    } catch (BitmexException e) {
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
      throws IOException {
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
    } catch (BitmexException e) {
      throw handleError(e);
    }
  }

  /**
   * See {@link Bitmex#getOrders}
   *
   * @return List of {@link BitmexPrivateOrder}s.
   */
  public List<BitmexPrivateOrder> getBitmexOrders() throws IOException, ExchangeException {
    return getBitmexOrders(null, null, null, null, null);
  }

  /**
   * See {@link Bitmex#placeOrder}
   *
   * @return {@link BitmexPrivateOrder} contains the results of the call.
   */
  public BitmexPrivateOrder placeMarketOrder(
      String symbol,
      @Nullable BitmexSide side,
      @Nullable BigDecimal orderQuantity,
      @Nullable BigDecimal simpleOrderQuantity,
      @Nullable String clOrdId,
      @Nullable String clOrdLinkId,
      @Nullable BitmexContingencyType contingencyType,
      @Nullable String text)
      throws IOException, ExchangeException {
    try {
      return bitmex.placeOrder(
          apiKey,
          exchange.getNonceFactory(),
          signatureCreator,
          symbol,
          side == null ? null : side.getCapitalized(),
          orderQuantity,
          simpleOrderQuantity,
          null,
          null,
          null,
          BitmexOrderType.MARKET.toApiParameter(),
          clOrdId,
          null,
          clOrdLinkId,
          contingencyType != null ? contingencyType.toApiParameter() : null,
          null,
          null,
          null,
          text);
    } catch (BitmexException e) {
      throw handleError(e);
    }
  }

  /**
   * See {@link Bitmex#placeOrder}
   *
   * @return {@link BitmexPrivateOrder} contains the results of the call.
   */
  public BitmexPrivateOrder placeLimitOrder(
      String symbol,
      @Nullable BigDecimal orderQuantity,
      @Nullable BigDecimal simpleOrderQuantity,
      @Nullable BigDecimal displayQuantity,
      @Nullable BigDecimal price,
      @Nullable BitmexSide side,
      @Nullable String clOrdId,
      @Nullable List<BitmexExecutionInstruction> executionInstructions,
      @Nullable String clOrdLinkId,
      @Nullable BitmexContingencyType contingencyType,
      @Nullable BigDecimal pegOffsetValue,
      @Nullable BitmexPegPriceType pegPriceType,
      @Nullable BitmexTimeInForce timeInForce,
      @Nullable String text)
      throws IOException, ExchangeException {
    try {
      return updateRateLimit(
          bitmex.placeOrder(
              apiKey,
              exchange.getNonceFactory(),
              signatureCreator,
              symbol,
              side != null ? side.getCapitalized() : null,
              orderQuantity,
              simpleOrderQuantity,
              displayQuantity,
              price,
              null,
              BitmexOrderType.LIMIT.toApiParameter(),
              clOrdId,
              executionInstructions != null ? StringUtils.join(executionInstructions, ",  ") : null,
              clOrdLinkId,
              contingencyType != null ? contingencyType.toApiParameter() : null,
              pegOffsetValue,
              pegPriceType != null ? pegPriceType.toApiParameter() : null,
              timeInForce != null ? timeInForce.toApiParameter() : null,
              text));
    } catch (BitmexException e) {
      throw handleError(e);
    }
  }

  /**
   * See {@link Bitmex#replaceOrder}
   *
   * @return {@link BitmexPrivateOrder} contains the results of the call.
   */
  public BitmexPrivateOrder replaceLimitOrder(
      @Nullable String orderId,
      @Nullable String origClOrdId,
      @Nullable String clOrdId,
      @Nullable BigDecimal simpleOrderQuantity,
      @Nullable BigDecimal orderQuantity,
      @Nullable BigDecimal simpleLeavesQuantity,
      @Nullable BigDecimal leavesQuantity,
      @Nullable BigDecimal price,
      @Nullable BigDecimal pegOffsetValue,
      @Nullable String text)
      throws IOException, ExchangeException {
    try {
      return updateRateLimit(
          bitmex.replaceOrder(
              apiKey,
              exchange.getNonceFactory(),
              signatureCreator,
              clOrdId != null ? null : orderId,
              origClOrdId,
              clOrdId,
              simpleOrderQuantity,
              orderQuantity,
              simpleLeavesQuantity,
              leavesQuantity,
              price,
              null,
              pegOffsetValue,
              text));
    } catch (BitmexException e) {
      throw handleError(e);
    }
  }

  /**
   * See {@link Bitmex#placeOrder}
   *
   * @return {@link BitmexPrivateOrder} contains the results of the call.
   */
  public BitmexPrivateOrder placeStopOrder(
      String symbol,
      @Nullable BitmexSide side,
      @Nullable BigDecimal orderQuantity,
      @Nullable BigDecimal simpleOrderQuantity,
      @Nullable BigDecimal stopPrice,
      @Nullable List<BitmexExecutionInstruction> executionInstructions,
      @Nullable String clOrdId,
      @Nullable String clOrdLinkId,
      @Nullable BitmexContingencyType contingencyType,
      @Nullable String text)
      throws IOException, ExchangeException {
    try {
      return updateRateLimit(
          bitmex.placeOrder(
              apiKey,
              exchange.getNonceFactory(),
              signatureCreator,
              symbol,
              side != null ? side.getCapitalized() : null,
              orderQuantity,
              simpleOrderQuantity,
              null,
              null,
              stopPrice,
              BitmexOrderType.STOP.toApiParameter(),
              clOrdId,
              executionInstructions != null ? StringUtils.join(executionInstructions, ",  ") : null,
              clOrdLinkId,
              contingencyType != null ? contingencyType.toApiParameter() : null,
              null,
              null,
              null,
              text));
    } catch (BitmexException e) {
      throw handleError(e);
    }
  }

  /**
   * See {@link Bitmex#replaceOrder}
   *
   * @return {@link BitmexPrivateOrder} contains the results of the call.
   */
  public BitmexPrivateOrder replaceStopOrder(
      @Nullable String orderId,
      @Nullable String origClOrdId,
      @Nullable String clOrdId,
      @Nullable BigDecimal orderQuantity,
      @Nullable BigDecimal simpleOrderQuantity,
      @Nullable BigDecimal stopPrice,
      @Nullable String text)
      throws IOException, ExchangeException {
    try {
      return updateRateLimit(
          bitmex.replaceOrder(
              apiKey,
              exchange.getNonceFactory(),
              signatureCreator,
              clOrdId != null ? null : orderId,
              origClOrdId,
              clOrdId,
              simpleOrderQuantity,
              orderQuantity,
              null,
              null,
              null,
              stopPrice,
              null,
              text));
    } catch (BitmexException e) {
      throw handleError(e);
    }
  }

  /**
   * See {@link Bitmex#placeOrder}
   *
   * @return {@link BitmexPrivateOrder} contains the results of the call.
   */
  public BitmexPrivateOrder placeStopLimitOrder(
      String symbol,
      @Nullable BitmexSide side,
      @Nullable BigDecimal orderQuantity,
      @Nullable BigDecimal simpleOrderQuantity,
      @Nullable BigDecimal displayQuantity,
      @Nullable BigDecimal limitPrice,
      @Nullable BigDecimal stopPrice,
      @Nullable List<BitmexExecutionInstruction> executionInstructions,
      @Nullable String clOrdId,
      @Nullable String clOrdLinkId,
      @Nullable BitmexContingencyType contingencyType,
      @Nullable BitmexTimeInForce timeInForce,
      @Nullable String text)
      throws IOException, ExchangeException {
    try {
      return updateRateLimit(
          bitmex.placeOrder(
              apiKey,
              exchange.getNonceFactory(),
              signatureCreator,
              symbol,
              side != null ? side.getCapitalized() : null,
              orderQuantity,
              simpleOrderQuantity,
              displayQuantity,
              limitPrice,
              stopPrice,
              BitmexOrderType.STOP_LIMIT.toApiParameter(),
              clOrdId,
              executionInstructions != null ? StringUtils.join(executionInstructions, ",  ") : null,
              clOrdLinkId,
              contingencyType != null ? contingencyType.toApiParameter() : null,
              null,
              null,
              timeInForce != null ? timeInForce.toApiParameter() : null,
              text));
    } catch (BitmexException e) {
      throw handleError(e);
    }
  }

  /**
   * See {@link Bitmex#replaceOrder}
   *
   * @return {@link BitmexPrivateOrder} contains the results of the call.
   */
  public BitmexPrivateOrder replaceStopLimitOrder(
      @Nullable String orderId,
      @Nullable String origClOrdId,
      @Nullable String clOrdId,
      @Nullable BigDecimal orderQuantity,
      @Nullable BigDecimal simpleOrderQuantity,
      @Nullable BigDecimal limitPrice,
      @Nullable BigDecimal stopPrice,
      @Nullable String text)
      throws IOException, ExchangeException {
    try {
      return updateRateLimit(
          bitmex.replaceOrder(
              apiKey,
              exchange.getNonceFactory(),
              signatureCreator,
              clOrdId != null ? null : orderId,
              origClOrdId,
              clOrdId,
              simpleOrderQuantity,
              orderQuantity,
              null,
              null,
              limitPrice,
              stopPrice,
              null,
              text));
    } catch (BitmexException e) {
      throw handleError(e);
    }
  }

  /**
   * See {@link Bitmex#placeOrder}
   *
   * @return {@link BitmexPrivateOrder} contains the results of the call.
   */
  public BitmexPrivateOrder placeTrailingStopOrder(
      String symbol,
      @Nullable BitmexSide side,
      @Nullable BigDecimal orderQuantity,
      @Nullable BigDecimal simpleOrderQuantity,
      @Nullable BigDecimal trailingStop,
      @Nullable List<BitmexExecutionInstruction> executionInstructions,
      @Nullable String clOrdId,
      @Nullable String clOrdLinkId,
      @Nullable BitmexContingencyType contingencyType,
      @Nullable String text)
      throws IOException, ExchangeException {
    try {
      return updateRateLimit(
          bitmex.placeOrder(
              apiKey,
              exchange.getNonceFactory(),
              signatureCreator,
              symbol,
              side != null ? side.getCapitalized() : null,
              orderQuantity,
              simpleOrderQuantity,
              null,
              null,
              null,
              BitmexOrderType.PEGGED.toApiParameter(),
              clOrdId,
              executionInstructions != null ? StringUtils.join(executionInstructions, ",  ") : null,
              clOrdLinkId,
              contingencyType != null ? contingencyType.toApiParameter() : null,
              trailingStop,
              BitmexPegPriceType.TRAILING_STOP_PEG.toApiParameter(),
              null,
              text));
    } catch (BitmexException e) {
      throw handleError(e);
    }
  }

  /**
   * See {@link Bitmex#replaceOrder}
   *
   * @return {@link BitmexPrivateOrder} contains the results of the call.
   */
  public BitmexPrivateOrder replaceTrailingStopOrder(
      @Nullable String orderId,
      @Nullable String origClOrdId,
      @Nullable String clOrdId,
      @Nullable BigDecimal orderQuantity,
      @Nullable BigDecimal simpleOrderQuantity,
      @Nullable BigDecimal trailingStop,
      @Nullable String text)
      throws IOException, ExchangeException {
    try {
      return updateRateLimit(
          bitmex.replaceOrder(
              apiKey,
              exchange.getNonceFactory(),
              signatureCreator,
              clOrdId != null ? null : orderId,
              origClOrdId,
              clOrdId,
              simpleOrderQuantity,
              orderQuantity,
              null,
              null,
              null,
              null,
              trailingStop,
              text));
    } catch (BitmexException e) {
      throw handleError(e);
    }
  }

  public List<BitmexPrivateOrder> placeOrderBulk(Collection<Bitmex.PlaceOrderCommand> commands)
      throws IOException, ExchangeException {
    try {
      String s = ObjectMapperHelper.toCompactJSON(commands);
      return updateRateLimit(
          bitmex.placeOrderBulk(apiKey, exchange.getNonceFactory(), signatureCreator, s));
    } catch (BitmexException e) {
      throw handleError(e);
    }
  }

  public List<BitmexPrivateOrder> replaceOrderBulk(Collection<Bitmex.ReplaceOrderCommand> commands)
      throws IOException, ExchangeException {
    try {
      String s = ObjectMapperHelper.toCompactJSON(commands);
      return bitmex.replaceOrderBulk(apiKey, exchange.getNonceFactory(), signatureCreator, s);
    } catch (BitmexException e) {
      throw handleError(e);
    }
  }

  public List<BitmexPrivateOrder> cancelAllOrders() throws IOException, ExchangeException {
    try {
      return cancelAllOrders(null, null, null);
    } catch (BitmexException e) {
      throw handleError(e);
    }
  }

  public List<BitmexPrivateOrder> cancelAllOrders(String symbol, String filter, String text)
      throws IOException, ExchangeException {
    try {
      return updateRateLimit(
          bitmex.cancelAllOrders(
              apiKey, exchange.getNonceFactory(), signatureCreator, symbol, filter, text));
    } catch (BitmexException e) {
      throw handleError(e);
    }
  }

  public List<BitmexPrivateOrder> cancelBitmexOrder(String orderId)
      throws IOException, ExchangeException {
    try {
      return cancelBitmexOrder(orderId, null);
    } catch (BitmexException e) {
      throw handleError(e);
    }
  }

  public List<BitmexPrivateOrder> cancelBitmexOrder(String orderId, String clOrdId)
      throws IOException, ExchangeException {
    try {
      return updateRateLimit(
          bitmex.cancelOrder(
              apiKey,
              exchange.getNonceFactory(),
              signatureCreator,
              clOrdId != null ? null : orderId,
              clOrdId));
    } catch (BitmexException e) {
      throw handleError(e);
    }
  }

  public BitmexPosition updateLeveragePosition(String symbol, BigDecimal leverage)
      throws IOException, ExchangeException {
    try {
      return updateRateLimit(
          bitmex.updateLeveragePosition(
              apiKey, exchange.getNonceFactory(), signatureCreator, symbol, leverage));
    } catch (BitmexException e) {
      throw handleError(e);
    }
  }

  public class PlaceMarketOrderCommand extends Bitmex.PlaceOrderCommand {

    /** See {@link Bitmex#placeOrder}. */
    public PlaceMarketOrderCommand(
        String symbol,
        @Nullable BitmexSide side,
        @Nullable BigDecimal orderQuantity,
        @Nullable BigDecimal simpleOrderQuantity,
        @Nullable String clOrdId,
        @Nullable String clOrdLinkId,
        @Nullable BitmexContingencyType contingencyType,
        @Nullable String text) {
      super(
          symbol,
          side,
          orderQuantity,
          simpleOrderQuantity,
          null,
          null,
          null,
          BitmexOrderType.MARKET,
          clOrdId,
          null,
          clOrdLinkId,
          contingencyType,
          null,
          null,
          null,
          text);
    }
  }

  public class PlaceLimitOrderCommand extends Bitmex.PlaceOrderCommand {

    /** See {@link Bitmex#placeOrder}. */
    public PlaceLimitOrderCommand(
        String symbol,
        @Nullable BigDecimal orderQuantity,
        @Nullable BigDecimal simpleOrderQuantity,
        @Nullable BigDecimal displayQuantity,
        @Nullable BigDecimal price,
        @Nullable BitmexSide side,
        @Nullable String clOrdId,
        @Nullable List<BitmexExecutionInstruction> executionInstructions,
        @Nullable String clOrdLinkId,
        @Nullable BitmexContingencyType contingencyType,
        @Nullable BigDecimal pegOffsetValue,
        @Nullable BitmexPegPriceType pegPriceType,
        @Nullable BitmexTimeInForce timeInForce,
        @Nullable String text) {
      super(
          symbol,
          side,
          orderQuantity,
          simpleOrderQuantity,
          displayQuantity,
          price,
          null,
          BitmexOrderType.LIMIT,
          clOrdId,
          executionInstructions,
          clOrdLinkId,
          contingencyType,
          pegOffsetValue,
          pegPriceType,
          timeInForce,
          text);
    }
  }

  public class ReplaceLimitOrderCommand extends Bitmex.ReplaceOrderCommand {

    /** See {@link Bitmex#replaceOrder}. */
    public ReplaceLimitOrderCommand(
        @Nullable String orderId,
        @Nullable String origClOrdId,
        @Nullable String clOrdId,
        @Nullable BigDecimal simpleOrderQuantity,
        @Nullable BigDecimal orderQuantity,
        @Nullable BigDecimal simpleLeavesQuantity,
        @Nullable BigDecimal leavesQuantity,
        @Nullable BigDecimal price,
        @Nullable BigDecimal pegOffsetValue,
        @Nullable String text) {
      super(
          orderId,
          origClOrdId,
          clOrdId,
          simpleOrderQuantity,
          orderQuantity,
          simpleLeavesQuantity,
          leavesQuantity,
          price,
          null,
          pegOffsetValue,
          text);
    }
  }

  public class PlaceStopOrderCommand extends Bitmex.PlaceOrderCommand {

    /** See {@link Bitmex#placeOrder}. */
    public PlaceStopOrderCommand(
        String symbol,
        @Nullable BitmexSide side,
        @Nullable BigDecimal orderQuantity,
        @Nullable BigDecimal simpleOrderQuantity,
        @Nullable BigDecimal stopPrice,
        @Nullable List<BitmexExecutionInstruction> executionInstructions,
        @Nullable String clOrdId,
        @Nullable String clOrdLinkId,
        @Nullable BitmexContingencyType contingencyType,
        @Nullable String text) {
      super(
          symbol,
          side,
          orderQuantity,
          simpleOrderQuantity,
          null,
          null,
          stopPrice,
          BitmexOrderType.STOP,
          clOrdId,
          executionInstructions,
          clOrdLinkId,
          contingencyType,
          null,
          null,
          null,
          text);
    }
  }

  public class ReplaceStopOrderCommand extends Bitmex.ReplaceOrderCommand {

    /** See {@link Bitmex#replaceOrder}. */
    public ReplaceStopOrderCommand(
        @Nullable String orderId,
        @Nullable String origClOrdId,
        @Nullable String clOrdId,
        @Nullable BigDecimal orderQuantity,
        @Nullable BigDecimal simpleOrderQuantity,
        @Nullable BigDecimal stopPrice,
        @Nullable String text) {
      super(
          orderId,
          origClOrdId,
          clOrdId,
          simpleOrderQuantity,
          orderQuantity,
          null,
          null,
          null,
          stopPrice,
          null,
          text);
    }
  }

  public class PlaceStopLimitOrderCommand extends Bitmex.PlaceOrderCommand {

    /** See {@link Bitmex#placeOrder}. */
    public PlaceStopLimitOrderCommand(
        String symbol,
        @Nullable BitmexSide side,
        @Nullable BigDecimal orderQuantity,
        @Nullable BigDecimal simpleOrderQuantity,
        @Nullable BigDecimal displayQuantity,
        @Nullable BigDecimal limitPrice,
        @Nullable BigDecimal stopPrice,
        @Nullable List<BitmexExecutionInstruction> executionInstructions,
        @Nullable String clOrdId,
        @Nullable String clOrdLinkId,
        @Nullable BitmexContingencyType contingencyType,
        @Nullable BitmexTimeInForce timeInForce,
        @Nullable String text) {
      super(
          symbol,
          side,
          orderQuantity,
          simpleOrderQuantity,
          displayQuantity,
          limitPrice,
          stopPrice,
          BitmexOrderType.STOP_LIMIT,
          clOrdId,
          executionInstructions,
          clOrdLinkId,
          contingencyType,
          null,
          null,
          timeInForce,
          text);
    }
  }

  public class ReplaceStopLimitOrderCommand extends Bitmex.ReplaceOrderCommand {

    /** See {@link Bitmex#replaceOrder}. */
    public ReplaceStopLimitOrderCommand(
        @Nullable String orderId,
        @Nullable String origClOrdId,
        @Nullable String clOrdId,
        @Nullable BigDecimal orderQuantity,
        @Nullable BigDecimal simpleOrderQuantity,
        @Nullable BigDecimal limitPrice,
        @Nullable BigDecimal stopPrice,
        @Nullable String text) {
      super(
          orderId,
          origClOrdId,
          clOrdId,
          simpleOrderQuantity,
          orderQuantity,
          null,
          null,
          limitPrice,
          stopPrice,
          null,
          text);
    }
  }

  public class PlaceTrailingStopOrderCommand extends Bitmex.PlaceOrderCommand {

    /** See {@link Bitmex#placeOrder}. */
    public PlaceTrailingStopOrderCommand(
        String symbol,
        @Nullable BitmexSide side,
        @Nullable BigDecimal orderQuantity,
        @Nullable BigDecimal simpleOrderQuantity,
        @Nullable BigDecimal trailingStop,
        @Nullable List<BitmexExecutionInstruction> executionInstructions,
        @Nullable String clOrdId,
        @Nullable String clOrdLinkId,
        @Nullable BitmexContingencyType contingencyType,
        @Nullable String text) {
      super(
          symbol,
          side,
          orderQuantity,
          simpleOrderQuantity,
          null,
          null,
          null,
          BitmexOrderType.PEGGED,
          clOrdId,
          executionInstructions,
          clOrdLinkId,
          contingencyType,
          trailingStop,
          BitmexPegPriceType.TRAILING_STOP_PEG,
          null,
          text);
    }
  }

  public class ReplaceTrailingStopOrderCommand extends Bitmex.ReplaceOrderCommand {

    /** See {@link Bitmex#replaceOrder}. */
    public ReplaceTrailingStopOrderCommand(
        @Nullable String orderId,
        @Nullable String origClOrdId,
        @Nullable String clOrdId,
        @Nullable BigDecimal orderQuantity,
        @Nullable BigDecimal simpleOrderQuantity,
        @Nullable BigDecimal trailingStop,
        @Nullable String text) {
      super(
          orderId,
          origClOrdId,
          clOrdId,
          simpleOrderQuantity,
          orderQuantity,
          null,
          null,
          null,
          null,
          null,
          text);
    }
  }
}
