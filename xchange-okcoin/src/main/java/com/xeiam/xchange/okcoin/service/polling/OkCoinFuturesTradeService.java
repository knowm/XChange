package com.xeiam.xchange.okcoin.service.polling;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.Order.OrderType;
import com.xeiam.xchange.dto.trade.LimitOrder;
import com.xeiam.xchange.dto.trade.MarketOrder;
import com.xeiam.xchange.dto.trade.OpenOrders;
import com.xeiam.xchange.dto.trade.UserTrades;
import com.xeiam.xchange.exceptions.ExchangeException;
import com.xeiam.xchange.exceptions.NotYetImplementedForExchangeException;
import com.xeiam.xchange.okcoin.FuturesContract;
import com.xeiam.xchange.okcoin.OkCoinAdapters;
import com.xeiam.xchange.okcoin.OkCoinUtils;
import com.xeiam.xchange.okcoin.dto.trade.OkCoinFuturesOrderResult;
import com.xeiam.xchange.okcoin.dto.trade.OkCoinTradeResult;
import com.xeiam.xchange.service.polling.trade.PollingTradeService;
import com.xeiam.xchange.service.polling.trade.params.TradeHistoryParams;

public class OkCoinFuturesTradeService extends OkCoinTradeServiceRaw implements PollingTradeService {

  private static final OpenOrders noOpenOrders = new OpenOrders(Collections.<LimitOrder> emptyList());
  private final Logger log = LoggerFactory.getLogger(OkCoinFuturesTradeService.class);
  
  private final int leverRate;
  private final FuturesContract futuresContract;

  /**
   * Constructor
   * 
   * @param exchange
   */
  public OkCoinFuturesTradeService(Exchange exchange, FuturesContract futuresContract, int leverRate) {

    super(exchange);
    
    this.leverRate = leverRate;
    this.futuresContract = futuresContract;
  }

  @Override
  public OpenOrders getOpenOrders() throws IOException {
    List<CurrencyPair> exchangeSymbols = getExchangeSymbols();

    List<OkCoinFuturesOrderResult> orderResults = new ArrayList<OkCoinFuturesOrderResult>(exchangeSymbols.size());

    for (int i = 0; i < exchangeSymbols.size(); i++) {
      CurrencyPair symbol = exchangeSymbols.get(i);
      log.debug("Getting order: {}", symbol);

      OkCoinFuturesOrderResult orderResult = getFuturesOrder(-1, OkCoinAdapters.adaptSymbol(symbol), "0", "50", futuresContract);
      if (orderResult.getOrders().length > 0) {
        orderResults.add(orderResult);
      }
    }

    if (orderResults.size() <= 0) {
      return noOpenOrders;
    }

    return OkCoinAdapters.adaptOpenOrdersFutures(orderResults);
  }

  @Override
  public String placeMarketOrder(MarketOrder marketOrder) throws IOException {

    long orderId = futuresTrade(OkCoinAdapters.adaptSymbol(marketOrder.getCurrencyPair()), marketOrder.getType() == OrderType.BID ? "1" : "2", "0",
        marketOrder.getTradableAmount().toPlainString(), futuresContract, 1, leverRate).getOrderId();
    return String.valueOf(orderId);
  }
  
  /** Liquidate long or short contract (depending on market order order type) using a market order */
  public String liquidateMarketOrder(MarketOrder marketOrder) throws IOException {

    long orderId = futuresTrade(OkCoinAdapters.adaptSymbol(marketOrder.getCurrencyPair()), marketOrder.getType() == OrderType.BID ? "3" : "4", "0",
        marketOrder.getTradableAmount().toPlainString(), futuresContract, 1, leverRate).getOrderId();
    return String.valueOf(orderId);
  }


  @Override
  public String placeLimitOrder(LimitOrder limitOrder) throws IOException {

    long orderId = futuresTrade(OkCoinAdapters.adaptSymbol(limitOrder.getCurrencyPair()), limitOrder.getType() == OrderType.BID ? "1" : "2",
        limitOrder.getLimitPrice().toPlainString(), limitOrder.getTradableAmount().toPlainString(), futuresContract, 0, leverRate).getOrderId();
    return String.valueOf(orderId);
  }
  
  /** Liquidate long or short contract using a limit order */
  public String liquidateLimitOrder(LimitOrder limitOrder) throws IOException {

    long orderId = futuresTrade(OkCoinAdapters.adaptSymbol(limitOrder.getCurrencyPair()), limitOrder.getType() == OrderType.BID ? "3" : "4",
        limitOrder.getLimitPrice().toPlainString(), limitOrder.getTradableAmount().toPlainString(), futuresContract, 0, leverRate).getOrderId();
    return String.valueOf(orderId);
  }


  @Override
  public boolean cancelOrder(String orderId) throws IOException {

    boolean ret = false;
    long id = Long.valueOf(orderId);

    List<CurrencyPair> exchangeSymbols = getExchangeSymbols();
    List<FuturesContract> exchangeContracts = getExchangeContracts();

    for (int i = 0; i < exchangeSymbols.size(); i++) {
      CurrencyPair symbol = exchangeSymbols.get(i);
      for (FuturesContract futuresContract : exchangeContracts) {

        try {
          OkCoinTradeResult cancelResult = futuresCancelOrder(id, OkCoinAdapters.adaptSymbol(symbol), futuresContract);

          if (id == cancelResult.getOrderId()) {
            ret = true;
          }
          break;

        } catch (ExchangeException e) {
          if (e.getMessage().equals(OkCoinUtils.getErrorMessage(1009)) || e.getMessage().equals(OkCoinUtils.getErrorMessage(20015))) {
            // order not found.
            continue;
          }
        }
      }
    }
    return ret;
  }

  @Override
  public UserTrades getTradeHistory(Object... arguments) throws IOException {

    CurrencyPair currencyPair = arguments.length > 0 ? (CurrencyPair) arguments[0] : CurrencyPair.BTC_USD;
    FuturesContract reqFuturesContract = arguments.length > 0 ? (FuturesContract) arguments[1] : futuresContract;

    Integer page = arguments.length > 1 ? (Integer) arguments[2] : 0;

    OkCoinFuturesOrderResult orderHistory = getFuturesOrder(1, OkCoinAdapters.adaptSymbol(currencyPair), page.toString(), "50", reqFuturesContract);
    return OkCoinAdapters.adaptTradesFutures(orderHistory);
  }

  @Override
  public UserTrades getTradeHistory(TradeHistoryParams params) throws IOException {

    throw new NotYetImplementedForExchangeException();
  }

  public List<FuturesContract> getExchangeContracts() {
    return java.util.Arrays.asList(FuturesContract.values());
  }

  @Override
  public TradeHistoryParams createTradeHistoryParams() {

    throw new NotYetImplementedForExchangeException();
  }

}
