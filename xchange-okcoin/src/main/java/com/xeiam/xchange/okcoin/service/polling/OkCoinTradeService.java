package com.xeiam.xchange.okcoin.service.polling;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.xeiam.xchange.service.polling.trade.TradeHistoryParamCurrencyPair;
import com.xeiam.xchange.service.polling.trade.TradeHistoryParamPaging;
import com.xeiam.xchange.service.polling.trade.DefaultTradeHistoryParamPaging;
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
import com.xeiam.xchange.okcoin.OkCoinAdapters;
import com.xeiam.xchange.okcoin.OkCoinException;
import com.xeiam.xchange.okcoin.dto.trade.OkCoinOrderResult;
import com.xeiam.xchange.okcoin.dto.trade.OkCoinTradeResult;
import com.xeiam.xchange.service.polling.PollingTradeService;

public class OkCoinTradeService extends OkCoinTradeServiceRaw implements PollingTradeService {
  private static final OpenOrders noOpenOrders = new OpenOrders(Collections.<LimitOrder>emptyList());
  
  private final Logger log = LoggerFactory.getLogger(OkCoinTradeService.class);
  private final List<CurrencyPair> exchangeSymbols = (List<CurrencyPair>) getExchangeSymbols();

  
  public OkCoinTradeService(ExchangeSpecification exchangeSpecification) {
    super(exchangeSpecification);

  }

  @Override
  public OpenOrders getOpenOrders() throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {

    List<OkCoinOrderResult> orderResults = new ArrayList<OkCoinOrderResult>(exchangeSymbols.size());

    for (int i = 0; i < exchangeSymbols.size(); i++) {
      CurrencyPair symbol = exchangeSymbols.get(i);
      log.debug("Getting order: {}", symbol);
      OkCoinOrderResult orderResult = getOrder(-1, OkCoinAdapters.adaptSymbol(symbol));
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

    String marketOrderType = null;
    String rate = null;
    String amount = null;

    if (marketOrder.getType().equals(OrderType.BID)) {
      marketOrderType = "buy_market";
      rate = marketOrder.getTradableAmount().toPlainString();
      amount = "1";
    }
    else {
      marketOrderType = "sell_market";
      rate = "1";
      amount = marketOrder.getTradableAmount().toPlainString();
    }

    long orderId = trade(OkCoinAdapters.adaptSymbol(marketOrder.getCurrencyPair()), marketOrderType, rate, amount).getOrderId();
    return String.valueOf(orderId);
  }

  @Override
  public String placeLimitOrder(LimitOrder limitOrder) throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {

    long orderId =
        trade(OkCoinAdapters.adaptSymbol(limitOrder.getCurrencyPair()), limitOrder.getType() == OrderType.BID ? "buy" : "sell", limitOrder.getLimitPrice().toPlainString(),
            limitOrder.getTradableAmount().toPlainString()).getOrderId();
    return String.valueOf(orderId);
  }

  @Override
  public boolean cancelOrder(String orderId) throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {

    boolean ret = false;
    long id = Long.valueOf(orderId);

    for (int i = 0; i < exchangeSymbols.size(); i++) {
      CurrencyPair symbol = exchangeSymbols.get(i);
      try {
        OkCoinTradeResult cancelResult = cancelOrder(id, OkCoinAdapters.adaptSymbol(symbol));

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

    CurrencyPair currencyPair = arguments.length > 0 ? (CurrencyPair) arguments[0] : (useIntl ? CurrencyPair.BTC_USD : CurrencyPair.BTC_CNY);
    Integer page = arguments.length > 1 ? (Integer) arguments[1] : 0;

    OkCoinOrderResult orderHistory = getOrderHistory(OkCoinAdapters.adaptSymbol(currencyPair), "1", page.toString(), "1000");
    return OkCoinAdapters.adaptTrades(orderHistory);
  }

  /**
   * Required parameters
   * {@link TradeHistoryParamPaging}
   *
   * Supported parameters
   * {@link TradeHistoryParamCurrencyPair}
   */
  @Override
  public UserTrades getTradeHistory(TradeHistoryParams params) throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {

    TradeHistoryParamPaging paging = (TradeHistoryParamPaging) params;
    Integer pageLength = paging.getPageLength();
    Integer pageNumber = paging.getPageNumber();

    // pages supposedly start from 1
    ++pageNumber;

    CurrencyPair pair = ((TradeHistoryParamCurrencyPair) params).getCurrencyPair();
    if (pair == null)
      pair = useIntl ? CurrencyPair.BTC_USD : CurrencyPair.BTC_CNY;

    OkCoinOrderResult orderHistory = getOrderHistory(OkCoinAdapters.adaptSymbol(pair), "1", toString(pageNumber), toString(pageLength));
    return OkCoinAdapters.adaptTrades(orderHistory);
  }

  private static String toString(Object o) {

    return o == null ? null : o.toString();
  }

  @Override
  public com.xeiam.xchange.service.polling.trade.TradeHistoryParams createTradeHistoryParams() {

    return new OkCoinTradeHistoryParams();
  }

  public static class OkCoinTradeHistoryParams extends DefaultTradeHistoryParamPaging implements TradeHistoryParamCurrencyPair {

    private CurrencyPair pair;

    public OkCoinTradeHistoryParams() {
    }

    public OkCoinTradeHistoryParams(Integer pageLength, Integer pageNumber, CurrencyPair pair) {

      super(pageLength, pageNumber);
      this.pair = pair;
    }

    @Override
    public void setCurrencyPair(CurrencyPair pair) {

      this.pair = pair;
    }

    @Override
    public CurrencyPair getCurrencyPair() {

      return pair;
    }
  }
}
