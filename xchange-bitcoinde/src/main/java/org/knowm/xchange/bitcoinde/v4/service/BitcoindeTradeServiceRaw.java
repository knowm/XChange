package org.knowm.xchange.bitcoinde.v4.service;

import static org.knowm.xchange.bitcoinde.BitcoindeUtils.createBitcoindeBoolean;
import static org.knowm.xchange.bitcoinde.BitcoindeUtils.createBitcoindePair;
import static org.knowm.xchange.bitcoinde.BitcoindeUtils.createBitcoindeType;
import static org.knowm.xchange.bitcoinde.BitcoindeUtils.rfc3339Timestamp;

import java.io.IOException;
import java.util.Date;
import org.knowm.xchange.bitcoinde.v4.BitcoindeExchange;
import org.knowm.xchange.bitcoinde.v4.dto.BitcoindeException;
import org.knowm.xchange.bitcoinde.v4.dto.BitcoindeOrderState;
import org.knowm.xchange.bitcoinde.v4.dto.BitcoindeResponse;
import org.knowm.xchange.bitcoinde.v4.dto.BitcoindeType;
import org.knowm.xchange.bitcoinde.v4.dto.trade.BitcoindeIdResponse;
import org.knowm.xchange.bitcoinde.v4.dto.trade.BitcoindeMyOrdersWrapper;
import org.knowm.xchange.bitcoinde.v4.dto.trade.BitcoindeMyTrade;
import org.knowm.xchange.bitcoinde.v4.dto.trade.BitcoindeMyTradesWrapper;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.trade.LimitOrder;
import si.mazi.rescu.SynchronizedValueFactory;

public class BitcoindeTradeServiceRaw extends BitcoindeBaseService {

  private final SynchronizedValueFactory<Long> nonceFactory;

  protected BitcoindeTradeServiceRaw(BitcoindeExchange exchange) {
    super(exchange);
    this.nonceFactory = exchange.getNonceFactory();
  }

  public BitcoindeMyOrdersWrapper getBitcoindeMyOrders(
      CurrencyPair currencyPair,
      BitcoindeType type,
      BitcoindeOrderState state,
      Date start,
      Date end,
      Integer page)
      throws IOException {
    try {
      String typeAsString = type != null ? type.getValue() : null;
      Integer stateAsInteger = state != null ? state.getValue() : null;
      String startAsString = start != null ? rfc3339Timestamp(start) : null;
      String endAsString = end != null ? rfc3339Timestamp(end) : null;

      if (currencyPair == null) {
        return bitcoinde.getMyOrders(
            apiKey,
            nonceFactory,
            signatureCreator,
            typeAsString,
            stateAsInteger,
            startAsString,
            endAsString,
            page);
      }

      return bitcoinde.getMyOrders(
          apiKey,
          nonceFactory,
          signatureCreator,
          createBitcoindePair(currencyPair),
          typeAsString,
          stateAsInteger,
          startAsString,
          endAsString,
          page);
    } catch (BitcoindeException e) {
      throw handleError(e);
    }
  }

  public BitcoindeResponse bitcoindeCancelOrders(String orderId, CurrencyPair currencyPair)
      throws IOException {
    try {
      String currPair = createBitcoindePair(currencyPair);

      return bitcoinde.deleteOrder(apiKey, nonceFactory, signatureCreator, orderId, currPair);
    } catch (BitcoindeException e) {
      throw handleError(e);
    }
  }

  public BitcoindeIdResponse bitcoindePlaceLimitOrder(LimitOrder order) throws IOException {
    try {
      String side = createBitcoindeType(order.getType());
      String bitcoindeCurrencyPair = createBitcoindePair(order.getCurrencyPair());

      return bitcoinde.createOrder(
          apiKey,
          nonceFactory,
          signatureCreator,
          order.getOriginalAmount(),
          order.getLimitPrice(),
          bitcoindeCurrencyPair,
          side);
    } catch (BitcoindeException e) {
      throw handleError(e);
    }
  }

  /**
   * Calls the API function Bitcoinde.getMyTrades().
   *
   * @param tradingPair optional (default: all)
   * @param type optional (default: all)
   * @param state optional (default: all)
   * @param actionRequired (default: all)
   * @param paymentMethod (default: all)
   * @param start optional (default: 10 days ago)
   * @param end optional (default: yesterday)
   * @param page optional (default: 1)
   * @return BitcoindeAccountLedgerWrapper
   * @throws IOException
   */
  public BitcoindeMyTradesWrapper getBitcoindeMyTrades(
      CurrencyPair tradingPair,
      BitcoindeType type,
      BitcoindeMyTrade.State state,
      Boolean actionRequired,
      BitcoindeMyTrade.PaymentMethod paymentMethod,
      Date start,
      Date end,
      Integer page)
      throws IOException {

    String typeAsString = type != null ? type.getValue() : null;
    Integer stateAsInteger = state != null ? state.getValue() : null;
    Integer actionRequiredAsInteger =
        actionRequired != null ? createBitcoindeBoolean(actionRequired) : null;
    Integer paymentMethodeAsInteger = paymentMethod != null ? paymentMethod.getValue() : null;
    String startAsString = start != null ? rfc3339Timestamp(start) : null;
    String endAsString = end != null ? rfc3339Timestamp(end) : null;

    try {
      if (tradingPair == null) {
        return bitcoinde.getMyTrades(
            apiKey,
            nonceFactory,
            signatureCreator,
            typeAsString,
            stateAsInteger,
            actionRequiredAsInteger,
            paymentMethodeAsInteger,
            startAsString,
            endAsString,
            page);
      }

      return bitcoinde.getMyTrades(
          apiKey,
          nonceFactory,
          signatureCreator,
          createBitcoindePair(tradingPair),
          typeAsString,
          stateAsInteger,
          actionRequiredAsInteger,
          paymentMethodeAsInteger,
          startAsString,
          endAsString,
          page);
    } catch (BitcoindeException e) {
      throw handleError(e);
    }
  }
}
