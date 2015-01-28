package com.xeiam.xchange.anx.v2.service.polling;

import java.io.IOException;
import java.math.BigDecimal;

import si.mazi.rescu.HttpStatusIOException;
import si.mazi.rescu.RestProxyFactory;

import com.xeiam.xchange.Exchange;
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
   * Constructor
   *
   * @param exchange
   */
  protected ANXTradeServiceRaw(Exchange exchange) {

    super(exchange);

    Assert.notNull(exchange.getExchangeSpecification().getSslUri(), "Exchange specification URI cannot be null");
    this.anxV2 = RestProxyFactory.createProxy(ANXV2.class, exchange.getExchangeSpecification().getSslUri());
    this.signatureCreator = ANXV2Digest.createInstance(exchange.getExchangeSpecification().getSecretKey());
  }

  public ANXOpenOrder[] getANXOpenOrders(String baseCurrency, String counterCurrency) throws IOException {

    try {
      ANXOpenOrderWrapper anxOpenOrderWrapper = anxV2.getOpenOrders(ANXUtils.urlEncode(exchange.getExchangeSpecification().getApiKey()),
          signatureCreator, exchange.getNonceFactory(), baseCurrency, counterCurrency);
      return anxOpenOrderWrapper.getANXOpenOrders();
    } catch (ANXException e) {
      throw handleError(e);
    } catch (HttpStatusIOException e) {
      throw handleHttpError(e);
    }
  }

  public ANXOpenOrder[] getANXOpenOrders() throws IOException {

    try {
      ANXOpenOrderWrapper anxOpenOrderWrapper = anxV2.getOpenOrders(ANXUtils.urlEncode(exchange.getExchangeSpecification().getApiKey()),
          signatureCreator, exchange.getNonceFactory());
      return anxOpenOrderWrapper.getANXOpenOrders();
    } catch (ANXException e) {
      throw handleError(e);
    } catch (HttpStatusIOException e) {
      throw handleHttpError(e);
    }
  }

  public ANXGenericResponse placeANXMarketOrder(MarketOrder marketOrder) throws IOException {

    try {
      ANXGenericResponse anxGenericResponse = anxV2.placeOrder(exchange.getExchangeSpecification().getApiKey(), signatureCreator, exchange
          .getNonceFactory(), marketOrder.getCurrencyPair().baseSymbol, marketOrder.getCurrencyPair().counterSymbol,
          marketOrder.getType().equals(Order.OrderType.BID) ? "bid" : "ask", marketOrder.getTradableAmount(), null);
      return anxGenericResponse;
    } catch (ANXException e) {
      throw handleError(e);
    } catch (HttpStatusIOException e) {
      throw handleHttpError(e);
    }
  }

  public ANXGenericResponse placeANXLimitOrder(CurrencyPair currencyPair, String type, BigDecimal amount, BigDecimal price) throws IOException {

    try {
      ANXGenericResponse anxGenericResponse = anxV2.placeOrder(exchange.getExchangeSpecification().getApiKey(), signatureCreator,
          exchange.getNonceFactory(), currencyPair.baseSymbol, currencyPair.counterSymbol, type, amount, price);

      return anxGenericResponse;
    } catch (ANXException e) {
      throw handleError(e);
    } catch (HttpStatusIOException e) {
      throw handleHttpError(e);
    }
  }

  public ANXGenericResponse cancelANXOrder(String orderId, String baseCurrency, String counterCurrency) throws IOException {

    try {

      ANXGenericResponse anxGenericResponse = anxV2.cancelOrder(exchange.getExchangeSpecification().getApiKey(), signatureCreator,
          exchange.getNonceFactory(), orderId, baseCurrency, counterCurrency);
      return anxGenericResponse;
    } catch (ANXException e) {
      throw handleError(e);
    } catch (HttpStatusIOException e) {
      throw handleHttpError(e);
    }
  }

  public ANXTradeResultWrapper getExecutedANXTrades(Long from, Long to) throws IOException {

    try {

      ANXTradeResultWrapper anxTradeResultWrapper = anxV2.getExecutedTrades(exchange.getExchangeSpecification().getApiKey(), signatureCreator,
          exchange.getNonceFactory(), from, to);
      return anxTradeResultWrapper;
    } catch (ANXException e) {
      throw handleError(e);
    } catch (HttpStatusIOException e) {
      throw handleHttpError(e);
    }
  }

  public ANXOrderResultWrapper getANXOrderResult(String orderId, String type, String baseCurrency, String counterCurrency) throws IOException {

    try {

      ANXOrderResultWrapper anxOrderResultWrapper = anxV2.getOrderResult(exchange.getExchangeSpecification().getApiKey(), signatureCreator,
          exchange.getNonceFactory(), baseCurrency, counterCurrency, orderId, type);
      return anxOrderResultWrapper;
    } catch (ANXException e) {
      throw handleError(e);
    } catch (HttpStatusIOException e) {
      throw handleHttpError(e);
    }
  }

  //  /**
  //   * Fetch the {@link com.xeiam.xchange.service.polling.trade.TradeMetaData}
  //   * from the exchange.
  //   *
  //   * @return Map of currency pairs to their corresponding metadata.
  //   * @see com.xeiam.xchange.service.polling.trade.TradeMetaData
  //   */
  //  public Map<CurrencyPair, ANXTradeMetaData> getTradeMetaDataMap() throws IOException {
  //
  //    Map<CurrencyPair, ANXTradeMetaData> meta = new HashMap<CurrencyPair, ANXTradeMetaData>();
  //    int amountScale = CFG.getIntProperty(KEY_ORDER_SIZE_SCALE_DEFAULT);
  //    int priceScale = CFG.getIntProperty(KEY_ORDER_PRICE_SCALE_DEFAULT);
  //    BigDecimal defAmountMin = CFG.getBigDecimalProperty(KEY_ORDER_SIZE_MIN_DEFAULT).setScale(amountScale, BigDecimal.ROUND_UNNECESSARY);
  //    BigDecimal defAmountMax = CFG.getBigDecimalProperty(KEY_ORDER_SIZE_MAX_DEFAULT).setScale(amountScale, BigDecimal.ROUND_UNNECESSARY);
  //
  //    for (CurrencyPair pair : exchange.getMetaData().getCurrencyPairs()) {
  //      BigDecimal amountMinimum = CFG.getBigDecimalProperty(PREKEY_ORDER_SIZE_MIN + pair.baseSymbol);
  //      if (amountMinimum == null) {
  //        amountMinimum = defAmountMin;
  //      } else {
  //        amountMinimum = amountMinimum.setScale(amountScale, BigDecimal.ROUND_UNNECESSARY);
  //      }
  //
  //      BigDecimal amountMaximum = CFG.getBigDecimalProperty(PREKEY_ORDER_SIZE_MAX + pair.baseSymbol);
  //      if (amountMaximum == null) {
  //        amountMaximum = defAmountMax;
  //      } else {
  //        amountMaximum = amountMaximum.setScale(amountScale, BigDecimal.ROUND_UNNECESSARY);
  //      }
  //
  //      meta.put(pair, new ANXTradeMetaData(amountMinimum, amountMaximum, priceScale));
  //    }
  //    return meta;
  //  }
}
