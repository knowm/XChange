package org.knowm.xchange.bitcoinde.service;

import java.io.IOException;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.bitcoinde.dto.BitcoindeException;
import org.knowm.xchange.bitcoinde.trade.BitcoindeIdResponse;
import org.knowm.xchange.bitcoinde.trade.BitcoindeMyOpenOrdersWrapper;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order.OrderType;
import org.knowm.xchange.dto.trade.LimitOrder;
import si.mazi.rescu.SynchronizedValueFactory;

/** @author kaiserfr */
public class BitcoindeTradeServiceRaw extends BitcoindeBaseService {
  private final SynchronizedValueFactory<Long> nonceFactory;

  protected BitcoindeTradeServiceRaw(Exchange exchange) {
    super(exchange);
    this.nonceFactory = exchange.getNonceFactory();
  }

  public BitcoindeMyOpenOrdersWrapper getBitcoindeOpenOrders() throws IOException {
    try {
      return bitcoinde.getOrders(apiKey, nonceFactory, signatureCreator);
    } catch (BitcoindeException e) {
      throw handleError(e);
    }
  }

  public BitcoindeIdResponse bitcoindeCancelOrders(String order_id, CurrencyPair currencyPair)
      throws IOException {
    try {
      String currPair =
          currencyPair.base.getCurrencyCode() + currencyPair.counter.getCurrencyCode();
      return bitcoinde.deleteOrder(apiKey, nonceFactory, signatureCreator, order_id, currPair);
    } catch (BitcoindeException e) {
      throw handleError(e);
    }
  }

  public BitcoindeIdResponse bitcoindePlaceLimitOrder(LimitOrder l) throws IOException {
    try {
      String side = l.getType().equals(OrderType.ASK) ? "sell" : "buy";
      String bitcoindeCurrencyPair =
          l.getCurrencyPair().base.getCurrencyCode()
              + l.getCurrencyPair().counter.getCurrencyCode();
      return bitcoinde.createOrder(
          apiKey,
          nonceFactory,
          signatureCreator,
          l.getOriginalAmount(),
          l.getLimitPrice(),
          bitcoindeCurrencyPair,
          side);
    } catch (BitcoindeException e) {
      throw handleError(e);
    }
  }
}
