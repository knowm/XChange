package org.knowm.xchange.cryptofacilities.service.polling;

import java.io.IOException;
import java.util.Collection;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.cryptofacilities.CryptoFacilitiesAdapters;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.MarketOrder;
import org.knowm.xchange.dto.trade.OpenOrders;
import org.knowm.xchange.dto.trade.UserTrades;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.exceptions.NotAvailableFromExchangeException;
import org.knowm.xchange.exceptions.NotYetImplementedForExchangeException;
import org.knowm.xchange.service.polling.trade.PollingTradeService;
import org.knowm.xchange.service.polling.trade.params.TradeHistoryParams;

/**
 * @author Jean-Christophe Laruelle
 */

public class CryptoFacilitiesTradeService extends CryptoFacilitiesTradeServiceRaw implements PollingTradeService {

  /**
   * Constructor
   *
   * @param exchange
   */
  public CryptoFacilitiesTradeService(Exchange exchange) {

    super(exchange);
  }

  @Override
  public OpenOrders getOpenOrders() throws IOException {

    return CryptoFacilitiesAdapters.adaptOpenOrders(super.getCryptoFacilitiesOpenOrders());

  }

  @Override
  public String placeMarketOrder(MarketOrder marketOrder) throws IOException {

    throw new NotAvailableFromExchangeException();

  }

  @Override
  public String placeLimitOrder(LimitOrder limitOrder) throws IOException {

    return CryptoFacilitiesAdapters.adaptOrderId(super.placeCryptoFacilitiesLimitOrder(limitOrder));
  }

  @Override
  public boolean cancelOrder(String orderId) throws IOException {

    OpenOrders openOrders = getOpenOrders();
    CurrencyPair ccyPair = new CurrencyPair("", "");

    for (LimitOrder order : openOrders.getOpenOrders()) {
      if (orderId.equals(order.getId())) {
        ccyPair = order.getCurrencyPair();
      }
    }

    return CryptoFacilitiesAdapters.adaptCryptoFacilitiesCancel(super.cancelCryptoFacilitiesOrder(orderId, ccyPair));
  }

  @Override
  public UserTrades getTradeHistory(TradeHistoryParams params) throws IOException {

    return CryptoFacilitiesAdapters.adaptFills(super.getCryptoFacilitiesFills());
  }

  @Override
  public org.knowm.xchange.service.polling.trade.params.TradeHistoryParams createTradeHistoryParams() {

    return null;
  }

  @Override
  public Collection<Order> getOrder(String... orderIds)
      throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {
    throw new NotYetImplementedForExchangeException();
  }

}
