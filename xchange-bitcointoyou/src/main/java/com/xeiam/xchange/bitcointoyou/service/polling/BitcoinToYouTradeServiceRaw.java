package com.xeiam.xchange.bitcointoyou.service.polling;

import java.io.IOException;

import javax.annotation.Nonnull;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.bitcointoyou.dto.BitcoinToYouBaseTradeApiResult;
import com.xeiam.xchange.bitcointoyou.dto.trade.BitcoinToYouOrder;
import com.xeiam.xchange.bitcointoyou.service.BitcoinToYouDigest;
import com.xeiam.xchange.dto.Order;
import com.xeiam.xchange.dto.trade.LimitOrder;
import com.xeiam.xchange.exceptions.ExchangeException;

/**
 * @author Felipe Micaroni Lalli
 */
public class BitcoinToYouTradeServiceRaw extends BitcoinToYouBasePollingService {

  /**
   * Constructor
   *
   * @param exchange
   */
  public BitcoinToYouTradeServiceRaw(Exchange exchange) {

    super(exchange);
  }

  public BitcoinToYouBaseTradeApiResult<BitcoinToYouOrder[]> getBitcoinToYouUserOrders(@Nonnull String status) throws IOException {

    Long nonce = exchange.getNonceFactory().createValue();

    BitcoinToYouDigest signatureCreator = BitcoinToYouDigest.createInstance(exchange.getExchangeSpecification().getApiKey(), exchange
        .getExchangeSpecification().getSecretKey(), nonce);

    BitcoinToYouBaseTradeApiResult<BitcoinToYouOrder[]> userOrders = bitcoinToYouAuthenticated.getOrders(exchange.getExchangeSpecification()
        .getApiKey(), nonce, signatureCreator, nonce, status);

    if (userOrders.getSuccess() == 0) {
      throw new ExchangeException("Error getting user orders: " + userOrders.getError());
    }

    return userOrders;
  }

  public BitcoinToYouOrder[] placeBitcoinToYouLimitOrder(LimitOrder limitOrder) {

    Long nonce = exchange.getNonceFactory().createValue();

    BitcoinToYouDigest signatureCreator = BitcoinToYouDigest.createInstance(exchange.getExchangeSpecification().getApiKey(), exchange
        .getExchangeSpecification().getSecretKey(), nonce);

    BitcoinToYouBaseTradeApiResult<BitcoinToYouOrder[]> userOrders = bitcoinToYouAuthenticated.createOrder(exchange.getExchangeSpecification()
        .getApiKey(), nonce, signatureCreator, nonce, limitOrder.getCurrencyPair().baseSymbol, limitOrder.getType() == Order.OrderType.BID ? "buy"
            : "sell", limitOrder.getTradableAmount(), limitOrder.getLimitPrice());

    if (userOrders.getSuccess() == 0) {
      throw new ExchangeException("Error placing limit order: " + userOrders.getError());
    }

    return userOrders.getTheReturn();
  }

  public BitcoinToYouOrder cancelBitcoinToYouLimitOrder(String orderId) {

    Long nonce = exchange.getNonceFactory().createValue();

    BitcoinToYouDigest signatureCreator = BitcoinToYouDigest.createInstance(exchange.getExchangeSpecification().getApiKey(), exchange
        .getExchangeSpecification().getSecretKey(), nonce);

    BitcoinToYouBaseTradeApiResult<BitcoinToYouOrder> userOrder = bitcoinToYouAuthenticated.deleteOrder(exchange.getExchangeSpecification()
        .getApiKey(), nonce, signatureCreator, nonce, orderId);

    if (userOrder.getSuccess() == 0) {
      throw new ExchangeException("Error canceling order: " + userOrder.getError());
    }

    return userOrder.getTheReturn();
  }

}
