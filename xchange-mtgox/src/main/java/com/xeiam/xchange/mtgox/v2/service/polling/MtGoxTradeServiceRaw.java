package com.xeiam.xchange.mtgox.v2.service.polling;

import java.io.IOException;
import java.math.BigDecimal;

import si.mazi.rescu.RestProxyFactory;

import com.xeiam.xchange.ExchangeException;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.dto.Order;
import com.xeiam.xchange.dto.trade.MarketOrder;
import com.xeiam.xchange.mtgox.MtGoxUtils;
import com.xeiam.xchange.mtgox.v2.MtGoxV2;
import com.xeiam.xchange.mtgox.v2.dto.MtGoxException;
import com.xeiam.xchange.mtgox.v2.dto.trade.polling.MtGoxGenericResponse;
import com.xeiam.xchange.mtgox.v2.dto.trade.polling.MtGoxOpenOrder;
import com.xeiam.xchange.mtgox.v2.dto.trade.polling.MtGoxOpenOrderWrapper;
import com.xeiam.xchange.mtgox.v2.service.MtGoxV2Digest;
import com.xeiam.xchange.service.polling.BasePollingExchangeService;
import com.xeiam.xchange.utils.Assert;

/**
 * @author gnandiga
 */
public class MtGoxTradeServiceRaw extends BasePollingExchangeService {

  protected final MtGoxV2 mtGoxV2;
  protected final MtGoxV2Digest signatureCreator;

  /**
   * Initialize common properties from the exchange specification
   * 
   * @param exchangeSpecification The {@link com.xeiam.xchange.ExchangeSpecification}
   */
  protected MtGoxTradeServiceRaw(ExchangeSpecification exchangeSpecification) {

    super(exchangeSpecification);

    Assert.notNull(exchangeSpecification.getSslUri(), "Exchange specification URI cannot be null");
    this.mtGoxV2 = RestProxyFactory.createProxy(MtGoxV2.class, exchangeSpecification.getSslUri());
    this.signatureCreator = MtGoxV2Digest.createInstance(exchangeSpecification.getSecretKey());
  }

  public MtGoxOpenOrder[] getMtGoxOpenOrders() throws IOException {

    try {
      MtGoxOpenOrderWrapper mtGoxOpenOrderWrapper = mtGoxV2.getOpenOrders(MtGoxUtils.urlEncode(exchangeSpecification.getApiKey()), signatureCreator, MtGoxUtils.getNonce());

      if (mtGoxOpenOrderWrapper.getResult().equals("success")) {
        return mtGoxOpenOrderWrapper.getMtGoxOpenOrders();
      }
      else if (mtGoxOpenOrderWrapper.getResult().equals("error")) {
        throw new ExchangeException("Error calling getOpenOrders(): " + mtGoxOpenOrderWrapper.getError());
      }
      else {
        throw new ExchangeException("Error calling getOpenOrders(): Unexpected result!");
      }
    } catch (MtGoxException e) {
      throw new ExchangeException("Error calling getOpenOrders(): " + e.getError(), e);
    }
  }

  public MtGoxGenericResponse placeMtGoxMarketOrder(MarketOrder marketOrder) throws IOException {

    try {
      MtGoxGenericResponse mtGoxGenericResponse =
          mtGoxV2.placeOrder(exchangeSpecification.getApiKey(), signatureCreator, MtGoxUtils.getNonce(), marketOrder.getTradableIdentifier(), marketOrder.getTransactionCurrency(), marketOrder
              .getType().equals(Order.OrderType.BID) ? "bid" : "ask", marketOrder.getTradableAmount().multiply(new BigDecimal(MtGoxUtils.BTC_VOLUME_AND_AMOUNT_INT_2_DECIMAL_FACTOR)), null);

      if (mtGoxGenericResponse.getResult().equals("success")) {
        return mtGoxGenericResponse;
      }
      else if (mtGoxGenericResponse.getResult().equals("error")) {
        throw new ExchangeException("Error calling placeMarketOrder(): " + mtGoxGenericResponse.getError());
      }
      else {
        throw new ExchangeException("Error calling placeMarketOrder(): Unexpected result!");
      }
    } catch (MtGoxException e) {
      throw new ExchangeException("Error calling placeMarketOrder(): " + e.getError(), e);
    }
  }

  public MtGoxGenericResponse placeMtGoxLimitOrder(String tradableIdentifier, String currency, String type, BigDecimal amount, String price) throws IOException {

    try {
      MtGoxGenericResponse mtGoxGenericResponse = mtGoxV2.placeOrder(exchangeSpecification.getApiKey(), signatureCreator, MtGoxUtils.getNonce(), tradableIdentifier, currency, type, amount, price);

      if (mtGoxGenericResponse.getResult().equals("success")) {
        return mtGoxGenericResponse;
      }
      else if (mtGoxGenericResponse.getResult().equals("error")) {
        throw new ExchangeException("Error calling placeLimitOrder(): " + mtGoxGenericResponse.getError());
      }
      else {
        throw new ExchangeException("Error calling placeLimitOrder(): Unexpected result!");
      }
    } catch (MtGoxException e) {
      throw new ExchangeException("Error calling placeLimitOrder(): " + e.getError(), e);
    }
  }

  public MtGoxGenericResponse cancelMtGoxOrder(String orderId) throws IOException {

    try {

      MtGoxGenericResponse mtGoxGenericResponse = mtGoxV2.cancelOrder(exchangeSpecification.getApiKey(), signatureCreator, MtGoxUtils.getNonce(), orderId);

      if (mtGoxGenericResponse.getResult().equals("success")) {
        return mtGoxGenericResponse;
      }
      else if (mtGoxGenericResponse.getResult().equals("error")) {
        throw new ExchangeException("Error calling cancelOrder(): " + mtGoxGenericResponse.getError());
      }
      else {
        throw new ExchangeException("Error calling placeLimitOrder(): Unexpected result!");
      }
    } catch (MtGoxException e) {
      throw new ExchangeException("Error calling cancelOrder(): " + e.getError(), e);
    }
  }
}
