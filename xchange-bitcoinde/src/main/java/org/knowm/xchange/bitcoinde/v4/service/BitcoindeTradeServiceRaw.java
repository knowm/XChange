package org.knowm.xchange.bitcoinde.v4.service;

import org.knowm.xchange.bitcoinde.v4.BitcoindeExchange;
import org.knowm.xchange.bitcoinde.v4.dto.BitcoindeException;
import org.knowm.xchange.bitcoinde.v4.dto.BitcoindeOrderState;
import org.knowm.xchange.bitcoinde.v4.dto.BitcoindeResponse;
import org.knowm.xchange.bitcoinde.v4.dto.BitcoindeType;
import org.knowm.xchange.bitcoinde.v4.dto.trade.BitcoindeMyOrdersWrapper;
import org.knowm.xchange.currency.CurrencyPair;
import si.mazi.rescu.SynchronizedValueFactory;

import java.io.IOException;
import java.util.Date;

import static org.knowm.xchange.bitcoinde.BitcoindeUtils.createBitcoindePair;
import static org.knowm.xchange.bitcoinde.BitcoindeUtils.rfc3339Timestamp;

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
  
}
