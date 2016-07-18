package org.knowm.xchange.cryptofacilities.service.polling;

import java.io.IOException;
import java.math.BigDecimal;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.cryptofacilities.dto.marketdata.CryptoFacilitiesCancel;
import org.knowm.xchange.cryptofacilities.dto.marketdata.CryptoFacilitiesFills;
import org.knowm.xchange.cryptofacilities.dto.marketdata.CryptoFacilitiesOpenOrders;
import org.knowm.xchange.cryptofacilities.dto.marketdata.CryptoFacilitiesOpenPositions;
import org.knowm.xchange.cryptofacilities.dto.marketdata.CryptoFacilitiesOrder;
import org.knowm.xchange.dto.Order.OrderType;
import org.knowm.xchange.dto.trade.LimitOrder;

/**
 * @author Jean-Christophe Laruelle
 */

public class CryptoFacilitiesTradeServiceRaw extends CryptoFacilitiesBasePollingService {

  /**
   * Constructor
   *
   * @param exchange
   */
  public CryptoFacilitiesTradeServiceRaw(Exchange exchange) {

    super(exchange);
  }

  public CryptoFacilitiesOrder sendCryptoFacilitiesLimitOrder(LimitOrder order) throws IOException {
    String orderType = "lmt";
    String symbol = order.getCurrencyPair().base.toString();
    String side = "buy";
    if (order.getType().equals(OrderType.ASK)) {
      side = "sell";
    }
    BigDecimal size = order.getTradableAmount();
    BigDecimal limitPrice = order.getLimitPrice();

    CryptoFacilitiesOrder ord = cryptoFacilities.sendOrder(exchange.getExchangeSpecification().getApiKey(), signatureCreator,
        exchange.getNonceFactory(), orderType, symbol, side, size, limitPrice);

    return ord;
  }

  public CryptoFacilitiesCancel cancelCryptoFacilitiesOrder(String uid) throws IOException {
    CryptoFacilitiesCancel res = cryptoFacilities.cancelOrder(exchange.getExchangeSpecification().getApiKey(), signatureCreator,
        exchange.getNonceFactory(), uid);

    return res;
  }

  public CryptoFacilitiesOpenOrders getCryptoFacilitiesOpenOrders() throws IOException {
    CryptoFacilitiesOpenOrders openOrders = null;
    try {
      openOrders = cryptoFacilities.openOrders(exchange.getExchangeSpecification().getApiKey(), signatureCreator, exchange.getNonceFactory());
    } catch (Exception e) {
      return null;
    }

    return openOrders;
  }

  public CryptoFacilitiesFills getCryptoFacilitiesFills() throws IOException {
    CryptoFacilitiesFills fills = null;

    try {
      fills = cryptoFacilities.fills(exchange.getExchangeSpecification().getApiKey(), signatureCreator, exchange.getNonceFactory());
    } catch (Exception e) {
      return null;
    }

    return fills;
  }

  public CryptoFacilitiesOpenPositions getCryptoFacilitiesOpenPositions() throws IOException {
    CryptoFacilitiesOpenPositions openPositions = null;

    try {
      openPositions = cryptoFacilities.openPositions(exchange.getExchangeSpecification().getApiKey(), signatureCreator, exchange.getNonceFactory());
    } catch (Exception e) {
      return null;
    }

    return openPositions;
  }
}
