package com.xeiam.xchange.bitcointoyou.service.polling.trade;

import com.xeiam.xchange.ExchangeException;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.bitcointoyou.BitcoinToYouAuthenticated;
import com.xeiam.xchange.bitcointoyou.BitcoinToYouUtils;
import com.xeiam.xchange.bitcointoyou.dto.BitcoinToYouBaseTradeApiResult;
import com.xeiam.xchange.bitcointoyou.dto.trade.BitcoinToYouOrder;
import com.xeiam.xchange.bitcointoyou.service.BitcoinToYouDigest;
import com.xeiam.xchange.bitcointoyou.service.polling.BitcoinToYouBasePollingService;
import com.xeiam.xchange.dto.Order;
import com.xeiam.xchange.dto.trade.LimitOrder;
import si.mazi.rescu.RestProxyFactory;

import javax.annotation.Nonnull;
import java.io.IOException;

/**
 * @author Felipe Micaroni Lalli
 */
public class BitcoinToYouTradeServiceRaw extends BitcoinToYouBasePollingService {

  private final BitcoinToYouAuthenticated bitcoinToYouAuthenticated;

  /**
   * Initialize common properties from the exchange specification
   * 
   * @param exchangeSpecification The {@link com.xeiam.xchange.ExchangeSpecification}
   */
  public BitcoinToYouTradeServiceRaw(ExchangeSpecification exchangeSpecification) {

    super(exchangeSpecification);
    this.bitcoinToYouAuthenticated = RestProxyFactory.createProxy(BitcoinToYouAuthenticated.class, exchangeSpecification.getSslUri());
  }

  public BitcoinToYouBaseTradeApiResult<BitcoinToYouOrder[]> getBitcoinToYouUserOrders(@Nonnull String status) throws IOException {

    Long nonce = BitcoinToYouUtils.getNonce();

    BitcoinToYouDigest signatureCreator = BitcoinToYouDigest.createInstance(exchangeSpecification.getApiKey(), exchangeSpecification.getSecretKey(), nonce);

    BitcoinToYouBaseTradeApiResult<BitcoinToYouOrder[]> userOrders = bitcoinToYouAuthenticated.getOrders(exchangeSpecification.getApiKey(), nonce, signatureCreator, nonce, status);

    if (userOrders.getSuccess() == 0) {
      throw new ExchangeException("Error getting user orders: " + userOrders.getError());
    }

    return userOrders;
  }

  public BitcoinToYouOrder[] placeBitcoinToYouLimitOrder(LimitOrder limitOrder) {

    Long nonce = BitcoinToYouUtils.getNonce();

    BitcoinToYouDigest signatureCreator = BitcoinToYouDigest.createInstance(exchangeSpecification.getApiKey(), exchangeSpecification.getSecretKey(), nonce);

    BitcoinToYouBaseTradeApiResult<BitcoinToYouOrder[]> userOrders =
        bitcoinToYouAuthenticated.createOrder(exchangeSpecification.getApiKey(), nonce, signatureCreator, nonce, limitOrder.getCurrencyPair().baseSymbol, limitOrder.getType() == Order.OrderType.BID
            ? "buy" : "sell", limitOrder.getTradableAmount(), limitOrder.getLimitPrice());

    if (userOrders.getSuccess() == 0) {
      throw new ExchangeException("Error placing limit order: " + userOrders.getError());
    }

    return userOrders.getTheReturn();
  }

  public BitcoinToYouOrder cancelBitcoinToYouLimitOrder(String orderId) {

    Long nonce = BitcoinToYouUtils.getNonce();

    BitcoinToYouDigest signatureCreator = BitcoinToYouDigest.createInstance(exchangeSpecification.getApiKey(), exchangeSpecification.getSecretKey(), nonce);

    BitcoinToYouBaseTradeApiResult<BitcoinToYouOrder> userOrder = bitcoinToYouAuthenticated.deleteOrder(exchangeSpecification.getApiKey(), nonce, signatureCreator, nonce, orderId);

    if (userOrder.getSuccess() == 0) {
      throw new ExchangeException("Error canceling order: " + userOrder.getError());
    }

    return userOrder.getTheReturn();
  }

}
