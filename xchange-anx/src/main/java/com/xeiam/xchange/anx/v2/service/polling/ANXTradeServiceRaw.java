package com.xeiam.xchange.anx.v2.service.polling;

import java.io.IOException;
import java.math.BigDecimal;

import si.mazi.rescu.RestProxyFactory;
import si.mazi.rescu.SynchronizedValueFactory;

import com.xeiam.xchange.ExchangeException;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.anx.ANXUtils;
import com.xeiam.xchange.anx.v2.ANXV2;
import com.xeiam.xchange.anx.v2.dto.ANXException;
import com.xeiam.xchange.anx.v2.dto.trade.polling.ANXGenericResponse;
import com.xeiam.xchange.anx.v2.dto.trade.polling.ANXOpenOrder;
import com.xeiam.xchange.anx.v2.dto.trade.polling.ANXOpenOrderWrapper;
import com.xeiam.xchange.anx.v2.dto.trade.polling.ANXOrderResultWrapper;
import com.xeiam.xchange.anx.v2.dto.trade.polling.ANXTradeResultWrapper;
import com.xeiam.xchange.anx.v2.service.ANXV2Digest;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.Order;
import com.xeiam.xchange.dto.trade.MarketOrder;
import com.xeiam.xchange.utils.Assert;

public class ANXTradeServiceRaw extends ANXBasePollingService {

  private final ANXV2 anxV2;
  private final ANXV2Digest signatureCreator;

  /**
   * Initialize common properties from the exchange specification
   * 
   * @param exchangeSpecification The {@link com.xeiam.xchange.ExchangeSpecification}
   */
  protected ANXTradeServiceRaw(ExchangeSpecification exchangeSpecification, SynchronizedValueFactory<Long> nonceFactory) {

    super(exchangeSpecification, nonceFactory);

    Assert.notNull(exchangeSpecification.getSslUri(), "Exchange specification URI cannot be null");
    this.anxV2 = RestProxyFactory.createProxy(ANXV2.class, exchangeSpecification.getSslUri());
    this.signatureCreator = ANXV2Digest.createInstance(exchangeSpecification.getSecretKey());
  }

  public ANXOpenOrder[] getANXOpenOrders(String baseCurrency, String counterCurrency) throws IOException {

    try {
      ANXOpenOrderWrapper anxOpenOrderWrapper = anxV2.getOpenOrders(ANXUtils.urlEncode(exchangeSpecification.getApiKey()), signatureCreator, getNonce(), baseCurrency, counterCurrency);
      return anxOpenOrderWrapper.getANXOpenOrders();
    } catch (ANXException e) {
      throw new ExchangeException("Error calling getOpenOrders(): " + e.getError(), e);
    }
  }

  public ANXOpenOrder[] getANXOpenOrders() throws IOException {

    try {
      ANXOpenOrderWrapper anxOpenOrderWrapper = anxV2.getOpenOrders(ANXUtils.urlEncode(exchangeSpecification.getApiKey()), signatureCreator, getNonce());
      return anxOpenOrderWrapper.getANXOpenOrders();
    } catch (ANXException e) {
      throw new ExchangeException("Error calling getOpenOrders(): " + e.getError(), e);
    }
  }

  public ANXGenericResponse placeANXMarketOrder(MarketOrder marketOrder) throws IOException {

    try {
      ANXGenericResponse anxGenericResponse =
          anxV2.placeOrder(exchangeSpecification.getApiKey(), signatureCreator, getNonce(), marketOrder.getCurrencyPair().baseSymbol, marketOrder.getCurrencyPair().counterSymbol, marketOrder
              .getType().equals(Order.OrderType.BID) ? "bid" : "ask", marketOrder.getTradableAmount(), null);
      return anxGenericResponse;
    } catch (ANXException e) {
      throw new ExchangeException("Error calling placeMarketOrder(): " + e.getError(), e);
    }
  }

  public ANXGenericResponse placeANXLimitOrder(CurrencyPair currencyPair, String type, BigDecimal amount, BigDecimal price) throws IOException {

    try {
      ANXGenericResponse anxGenericResponse =
          anxV2.placeOrder(exchangeSpecification.getApiKey(), signatureCreator, getNonce(), currencyPair.baseSymbol, currencyPair.counterSymbol, type, amount, price);

      return anxGenericResponse;
    } catch (ANXException e) {
      throw new ExchangeException("Error calling placeLimitOrder(): " + e.getError(), e);
    }
  }

  public ANXGenericResponse cancelANXOrder(String orderId, String baseCurrency, String counterCurrency) throws IOException {

    try {

      ANXGenericResponse anxGenericResponse = anxV2.cancelOrder(exchangeSpecification.getApiKey(), signatureCreator, getNonce(), orderId, baseCurrency, counterCurrency);
      return anxGenericResponse;
    } catch (ANXException e) {
      throw new ExchangeException("Error calling cancelOrder(): " + e.getError(), e);
    }
  }

  public ANXTradeResultWrapper getExecutedANXTrades(Long from, Long to) throws IOException {

    try {

      ANXTradeResultWrapper anxTradeResultWrapper = anxV2.getExecutedTrades(exchangeSpecification.getApiKey(), signatureCreator, getNonce(), from, to);
      return anxTradeResultWrapper;
    } catch (ANXException e) {
      throw new ExchangeException("Error calling getExecutedANXTrades(): " + e.getError(), e);
    }
  }

  public ANXOrderResultWrapper getANXOrderResult(String orderId, String type, String baseCurrency, String counterCurrency) throws IOException {

    try {

      ANXOrderResultWrapper anxOrderResultWrapper = anxV2.getOrderResult(exchangeSpecification.getApiKey(), signatureCreator, getNonce(), baseCurrency, counterCurrency, orderId, type);
      return anxOrderResultWrapper;
    } catch (ANXException e) {
      throw new ExchangeException("Error calling getANXOrderResult(): " + e.getError(), e);
    }
  }
}
