package com.xeiam.xchange.btcchina.service.polling;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.btcchina.BTCChinaAdapters;
import com.xeiam.xchange.btcchina.dto.trade.request.BTCChinaGetOrdersRequest;
import com.xeiam.xchange.btcchina.dto.trade.request.BTCChinaTransactionsRequest;
import com.xeiam.xchange.btcchina.dto.trade.response.BTCChinaBooleanResponse;
import com.xeiam.xchange.btcchina.dto.trade.response.BTCChinaGetOrdersResponse;
import com.xeiam.xchange.btcchina.dto.trade.response.BTCChinaIntegerResponse;
import com.xeiam.xchange.btcchina.dto.trade.response.BTCChinaTransactionsResponse;
import com.xeiam.xchange.dto.Order.OrderType;
import com.xeiam.xchange.dto.marketdata.Trades;
import com.xeiam.xchange.dto.trade.LimitOrder;
import com.xeiam.xchange.dto.trade.MarketOrder;
import com.xeiam.xchange.dto.trade.OpenOrders;
import com.xeiam.xchange.service.polling.PollingTradeService;

/**
 * Implementation of the trade service for BTCChina.
 * <ul>
 * <li>Provides access to trade functions</li>
 * </ul>
 *
 * @author ObsessiveOrange
 */
public class BTCChinaTradeService extends BTCChinaTradeServiceRaw implements PollingTradeService {

  private final Logger log = LoggerFactory.getLogger(BTCChinaTradeService.class);

  /**
   * Constructor
   * 
   * @param exchangeSpecification
   */
  public BTCChinaTradeService(ExchangeSpecification exchangeSpecification) {

    super(exchangeSpecification);

  }

  @Override
  public OpenOrders getOpenOrders() throws IOException {

    final List<LimitOrder> limitOrders = new ArrayList<LimitOrder>();

    List<LimitOrder> page;
    do {
      BTCChinaGetOrdersResponse response = getBTCChinaOrders(true, BTCChinaGetOrdersRequest.ALL_MARKET, null, limitOrders.size());

      page = new ArrayList<LimitOrder>();
      page.addAll(BTCChinaAdapters.adaptOrders(response.getResult(), null));

      limitOrders.addAll(page);
    } while (page.size() >= BTCChinaGetOrdersRequest.DEFAULT_LIMIT);

    return new OpenOrders(limitOrders);
  }

  @Override
  public String placeMarketOrder(MarketOrder marketOrder) throws IOException {

    final BigDecimal amount = marketOrder.getTradableAmount();
    final String market = BTCChinaAdapters.adaptMarket(marketOrder.getCurrencyPair()).toUpperCase();
    final BTCChinaIntegerResponse response;

    if (marketOrder.getType() == OrderType.BID) {
      response = buy(null, amount, market);
    }
    else {
      response = sell(null, amount, market);
    }

    return response.getResult().toString();
  }

  @Override
  public String placeLimitOrder(LimitOrder limitOrder) throws IOException {

    final BigDecimal price = limitOrder.getLimitPrice();
    final BigDecimal amount = limitOrder.getTradableAmount();
    final String market = BTCChinaAdapters.adaptMarket(limitOrder.getCurrencyPair()).toUpperCase();
    final BTCChinaIntegerResponse response;

    if (limitOrder.getType() == OrderType.BID) {
      response = buy(price, amount, market);
    }
    else {
      response = sell(price, amount, market);
    }

    return response.getResult().toString();
  }

  @Override
  public boolean cancelOrder(String orderId) throws IOException {

    BTCChinaBooleanResponse response = cancelBTCChinaOrder(Integer.parseInt(orderId));
    return response.getResult();
  }

  /**
   * Gets trade history for user's account.
   *
   * @param args 2 optional arguments:
   *          <ol>
   *          <li>limit: limit the number of transactions, default value is 10 if null.</li>
   *          <li>offset: start index used for pagination, default value is 0 if null.</li>
   *          </ol>
   */
  @Override
  public Trades getTradeHistory(Object... args) throws IOException {

    final String type = BTCChinaTransactionsRequest.TYPE_ALL;
    final Integer limit = args.length > 0 ? ((Number) args[0]).intValue() : null;
    final Integer offset = args.length > 1 ? ((Number) args[1]).intValue() : null;

    log.debug("type: {}, limit: {}, offset: {}", type, limit, offset);

    final BTCChinaTransactionsResponse response = getTransactions(type, limit, offset);
    return BTCChinaAdapters.adaptTransactions(response.getResult().getTransactions());
  }

}
