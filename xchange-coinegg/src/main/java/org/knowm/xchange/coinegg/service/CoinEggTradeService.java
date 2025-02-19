package org.knowm.xchange.coinegg.service;

import java.io.IOException;
import java.math.BigDecimal;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.coinegg.CoinEggAdapters;
import org.knowm.xchange.dto.Order.OrderType;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.MarketOrder;
import org.knowm.xchange.dto.trade.UserTrades;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.exceptions.NotAvailableFromExchangeException;
import org.knowm.xchange.service.trade.TradeService;
import org.knowm.xchange.service.trade.params.CancelOrderByCurrencyPair;
import org.knowm.xchange.service.trade.params.CancelOrderByIdParams;
import org.knowm.xchange.service.trade.params.CancelOrderParams;
import org.knowm.xchange.service.trade.params.TradeHistoryParamCurrency;
import org.knowm.xchange.service.trade.params.TradeHistoryParamTransactionId;
import org.knowm.xchange.service.trade.params.TradeHistoryParams;
import org.knowm.xchange.service.trade.params.TradeHistoryParamsAll;

public class CoinEggTradeService extends CoinEggTradeServiceRaw implements TradeService {

  public CoinEggTradeService(Exchange exchange) {
    super(exchange);
  }

  @Override
  public String placeLimitOrder(LimitOrder limitOrder) throws IOException {

    BigDecimal amount = limitOrder.getOriginalAmount();
    BigDecimal price = limitOrder.getAveragePrice();
    String type = limitOrder.getType() == OrderType.ASK ? "buy" : "sell";
    String coin = limitOrder.getCurrencyPair().getBase().getCurrencyCode().toLowerCase();

    return CoinEggAdapters.adaptTradeAdd(getCoinEggTradeAdd(amount, price, type, coin));
  }

  @Override
  public boolean cancelOrder(CancelOrderParams orderParams) throws IOException {

    if ((orderParams instanceof CancelOrderByIdParams)
        && (orderParams instanceof CancelOrderByCurrencyPair)) {

      String id = ((CancelOrderByIdParams) orderParams).getOrderId();
      String coin =
          ((CancelOrderByCurrencyPair) orderParams)
              .getCurrencyPair()
              .getBase()
              .getCurrencyCode()
              .toLowerCase();

      return CoinEggAdapters.adaptTradeCancel(getCoinEggTradeCancel(id, coin));
    }

    throw new ExchangeException("Incorrect CancelOrderParams!");
  }

  @Override
  public TradeHistoryParams createTradeHistoryParams() {
    return new TradeHistoryParamsAll();
  }

  @Override
  public UserTrades getTradeHistory(TradeHistoryParams params)
      throws IOException, ExchangeException {

    if ((params instanceof TradeHistoryParamCurrency)
        && (params instanceof TradeHistoryParamTransactionId)) {

      String tradeID = ((TradeHistoryParamTransactionId) params).getTransactionId();
      String coin =
          ((TradeHistoryParamCurrency) params).getCurrency().getCurrencyCode().toLowerCase();

      return CoinEggAdapters.adaptTradeHistory(getCoinEggTradeView(tradeID, coin));
    }

    throw new ExchangeException("Incorrect TradeHistoryParams!");
  }

  @Override
  public String placeMarketOrder(MarketOrder marketOrder) throws IOException {
    throw new NotAvailableFromExchangeException();
  }

  @Override
  public boolean cancelOrder(String orderId) throws IOException {
    throw new NotAvailableFromExchangeException();
  }

  @Override
  public void verifyOrder(MarketOrder marketOrder) {
    throw new NotAvailableFromExchangeException();
  }
}
