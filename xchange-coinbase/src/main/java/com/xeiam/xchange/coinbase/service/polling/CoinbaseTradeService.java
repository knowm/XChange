package com.xeiam.xchange.coinbase.service.polling;

import java.io.IOException;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.coinbase.CoinbaseAdapters;
import com.xeiam.xchange.coinbase.dto.trade.CoinbaseTransfer;
import com.xeiam.xchange.coinbase.dto.trade.CoinbaseTransfers;
import com.xeiam.xchange.dto.Order.OrderType;
import com.xeiam.xchange.dto.trade.LimitOrder;
import com.xeiam.xchange.dto.trade.MarketOrder;
import com.xeiam.xchange.dto.trade.OpenOrders;
import com.xeiam.xchange.dto.trade.UserTrades;
import com.xeiam.xchange.exceptions.ExchangeException;
import com.xeiam.xchange.exceptions.NotAvailableFromExchangeException;
import com.xeiam.xchange.service.polling.trade.PollingTradeService;
import com.xeiam.xchange.service.polling.trade.params.DefaultTradeHistoryParamPaging;
import com.xeiam.xchange.service.polling.trade.params.TradeHistoryParamPaging;
import com.xeiam.xchange.service.polling.trade.params.TradeHistoryParams;

/**
 * @author jamespedwards42
 */
public final class CoinbaseTradeService extends CoinbaseTradeServiceRaw implements PollingTradeService {

  /**
   * Constructor
   *
   * @param exchange
   */
  public CoinbaseTradeService(Exchange exchange) {

    super(exchange);
  }

  @Override
  public OpenOrders getOpenOrders() throws NotAvailableFromExchangeException {

    throw new NotAvailableFromExchangeException();
  }

  @Override
  public String placeMarketOrder(MarketOrder marketOrder) throws ExchangeException, IOException {

    final CoinbaseTransfer transfer = marketOrder.getType().equals(OrderType.BID) ? super.buy(marketOrder.getTradableAmount())
        : super.sell(marketOrder.getTradableAmount());
    return transfer.getTransactionId();
  }

  @Override
  public String placeLimitOrder(LimitOrder limitOrder) throws NotAvailableFromExchangeException {

    throw new NotAvailableFromExchangeException();
  }

  @Override
  public boolean cancelOrder(String orderId) throws NotAvailableFromExchangeException {

    throw new NotAvailableFromExchangeException();
  }

  /**
   * Authenticated resource which returns the user’s Bitcoin purchases and sells. Sorted in descending order by creation date.
   *
   * @see <a href="https://coinbase.com/api/doc/1.0/transfers/index.html">coinbase.com/api/doc/1.0/transfers/index.html</a>
   */
  @Override
  public UserTrades getTradeHistory(TradeHistoryParams params) throws IOException {

    Integer page = null;
    Integer limit = null;
    if (params instanceof TradeHistoryParamPaging) {
      page = ((TradeHistoryParamPaging) params).getPageNumber() + 1;
      limit = ((TradeHistoryParamPaging) params).getPageLength();
    }

    final CoinbaseTransfers transfers = super.getCoinbaseTransfers(page, limit);
    return CoinbaseAdapters.adaptTrades(transfers);
  }

  @Override
  public TradeHistoryParams createTradeHistoryParams() {

    DefaultTradeHistoryParamPaging params = new DefaultTradeHistoryParamPaging();
    params.setPageNumber(1);
    params.setPageLength(100);
    return params;
  }

}
