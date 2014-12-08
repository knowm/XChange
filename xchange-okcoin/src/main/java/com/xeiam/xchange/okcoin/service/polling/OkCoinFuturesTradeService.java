package com.xeiam.xchange.okcoin.service.polling;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.xeiam.xchange.service.polling.trade.TradeHistoryParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xeiam.xchange.ExchangeException;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.NotAvailableFromExchangeException;
import com.xeiam.xchange.NotYetImplementedForExchangeException;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.Order.OrderType;
import com.xeiam.xchange.dto.trade.LimitOrder;
import com.xeiam.xchange.dto.trade.MarketOrder;
import com.xeiam.xchange.dto.trade.OpenOrders;
import com.xeiam.xchange.dto.trade.UserTrades;
import com.xeiam.xchange.okcoin.FuturesContract;
import com.xeiam.xchange.okcoin.OkCoinAdapters;
import com.xeiam.xchange.okcoin.OkCoinException;
import com.xeiam.xchange.okcoin.dto.trade.OkCoinOrderResult;
import com.xeiam.xchange.okcoin.dto.trade.OkCoinTradeResult;
import com.xeiam.xchange.service.polling.PollingTradeService;

public class OkCoinFuturesTradeService extends OkCoinTradeServiceRaw implements PollingTradeService {
  private static final OpenOrders noOpenOrders = new OpenOrders(Collections.<LimitOrder>emptyList());

  private final Logger log = LoggerFactory.getLogger(OkCoinFuturesTradeService.class);
  private final List<CurrencyPair> exchangeSymbols = (List<CurrencyPair>) getExchangeSymbols();

  private FuturesContract futuresContract = FuturesContract.NextWeek;

  public OkCoinFuturesTradeService(ExchangeSpecification exchangeSpecification) {
    super(exchangeSpecification);

    if(exchangeSpecification.getExchangeSpecificParameters().containsKey("Futures_Contract")) {
      futuresContract = (FuturesContract) exchangeSpecification.getExchangeSpecificParameters().get("Futures_Contract");
      log.info("Using futures contract " + futuresContract);
    } else {
      log.info("Using default futures contract " + futuresContract);
    }
  }

  @Override
  public OpenOrders getOpenOrders() throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {

    List<OkCoinOrderResult> orderResults = new ArrayList<OkCoinOrderResult>(exchangeSymbols.size());

    for (int i = 0; i < exchangeSymbols.size(); i++) {
      CurrencyPair symbol = exchangeSymbols.get(i);
      log.debug("Getting order: {}", symbol);  
      OkCoinOrderResult orderResult = getFuturesOrder(-1, OkCoinAdapters.adaptSymbol(symbol), "1", "50", futuresContract);
      if(orderResult.getOrders().length > 0) {
        orderResults.add(orderResult);
      }
    }

    if(orderResults.size() <= 0) {
      return noOpenOrders;
    }

    return OkCoinAdapters.adaptOpenOrders(orderResults);
  }

  @Override
  public String placeMarketOrder(MarketOrder marketOrder) throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {

    long orderId =
        futuresTrade(OkCoinAdapters.adaptSymbol(marketOrder.getCurrencyPair()), marketOrder.getType() == OrderType.BID ? "1" : "2", "0",
            marketOrder.getTradableAmount().toPlainString(), futuresContract, 1).getOrderId();
    return String.valueOf(orderId);
  }

  @Override
  public String placeLimitOrder(LimitOrder limitOrder) throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {

    long orderId =
        futuresTrade(OkCoinAdapters.adaptSymbol(limitOrder.getCurrencyPair()), limitOrder.getType() == OrderType.BID ? "1" : "2", limitOrder.getLimitPrice().toPlainString(),
            limitOrder.getTradableAmount().toPlainString(), futuresContract, 0).getOrderId();
    return String.valueOf(orderId);
  }

  @Override
  public boolean cancelOrder(String orderId) throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {

    boolean ret = false;
    long id = Long.valueOf(orderId);

    for (int i = 0; i < exchangeSymbols.size(); i++) {
      CurrencyPair symbol = exchangeSymbols.get(i);
      try {
        OkCoinTradeResult cancelResult = futuresCancelOrder(id, OkCoinAdapters.adaptSymbol(symbol), futuresContract);

        if (id == cancelResult.getOrderId()) {
          ret = true;
        }
        break;
      } catch (OkCoinException e) {
        if (e.getErrorCode() == 10009) {
          // order not found.
          continue;
        }
      }
    }
    return ret;
  }

  @Override
  public UserTrades getTradeHistory(Object... arguments) throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {

    CurrencyPair currencyPair = arguments.length > 0 ? (CurrencyPair) arguments[0] : CurrencyPair.BTC_USD;
    Integer page = arguments.length > 1 ? (Integer) arguments[1] : 0;

    OkCoinOrderResult orderHistory = getFuturesOrder(1, OkCoinAdapters.adaptSymbol(currencyPair), page.toString(), "50", futuresContract);
    return OkCoinAdapters.adaptTrades(orderHistory);
  }

  @Override
  public UserTrades getTradeHistory(TradeHistoryParams params) throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {

    throw new NotYetImplementedForExchangeException();
  }

  @Override
  public TradeHistoryParams createTradeHistoryParams() {

    return null;
  }
}
