package com.xeiam.xchange.cointrader.service.polling;

import static com.xeiam.xchange.dto.Order.OrderType.BID;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.cointrader.CointraderAdapters;
import com.xeiam.xchange.cointrader.CointraderExchange;
import com.xeiam.xchange.cointrader.dto.CointraderException;
import com.xeiam.xchange.cointrader.dto.trade.CointraderOrder;
import com.xeiam.xchange.cointrader.dto.trade.CointraderSubmitOrderResponse;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.Order;
import com.xeiam.xchange.dto.trade.LimitOrder;
import com.xeiam.xchange.dto.trade.MarketOrder;
import com.xeiam.xchange.dto.trade.OpenOrders;
import com.xeiam.xchange.dto.trade.UserTrades;
import com.xeiam.xchange.service.polling.trade.PollingTradeService;
import com.xeiam.xchange.service.polling.trade.params.TradeHistoryParamPaging;
import com.xeiam.xchange.service.polling.trade.params.TradeHistoryParams;
import com.xeiam.xchange.service.polling.trade.params.TradeHistoryParamsIdSpan;

/**
 * @author Matija Mazi
 */
public class CointraderTradeService extends CointraderTradeServiceRaw implements PollingTradeService {

  private final CurrencyPair currencyPair;

  public CointraderTradeService(Exchange exchange) {
    super(exchange);
    CurrencyPair cp = null;
    try {
      cp = (CurrencyPair) exchange.getExchangeSpecification().getExchangeSpecificParameters().get(CointraderExchange.CURRENCY_PAIR);
    } catch (ClassCastException ignored) {
    }
    if (cp == null) {
      throw new IllegalArgumentException("The CURRENCY_PAIR exchange-specific parameter must be set in the exchange specification.");
    }
    currencyPair = cp;
  }

  @Override
  public String placeMarketOrder(MarketOrder order) throws IOException, CointraderException {
    // Note that there are also specialized market order API calls that immediately return order execution data such as average price and fee.
    return placeOrder(order.getCurrencyPair(), order.getType(), order.getTradableAmount(), null);
  }

  @Override
  public String placeLimitOrder(LimitOrder order) throws IOException, CointraderException {
    return placeOrder(order.getCurrencyPair(), order.getType(), order.getTradableAmount(), order.getLimitPrice());
  }

  private String placeOrder(CurrencyPair currencyPair, Order.OrderType type, BigDecimal amount, BigDecimal price) throws IOException {
    CointraderSubmitOrderResponse cointraderOrder = BID.equals(type) ? placeCointraderBuyOrder(currencyPair, amount, price)
        : placeCointraderSellOrder(currencyPair, amount, price);
    return Long.toString(cointraderOrder.getData().getId());
  }

  @Override
  public OpenOrders getOpenOrders() throws IOException, CointraderException {
    CointraderOrder[] openOrders = getCointraderOpenOrders(currencyPair);

    List<LimitOrder> limitOrders = new ArrayList<LimitOrder>();
    for (CointraderOrder cointraderOrder : openOrders) {
      limitOrders.add(CointraderAdapters.adaptOrder(cointraderOrder));
    }
    return new OpenOrders(limitOrders);
  }

  @Override
  public boolean cancelOrder(String orderId) throws IOException, CointraderException {
    return cancelCointraderOrder(currencyPair, Long.parseLong(orderId)).getStatus();
  }

  @Override
  public UserTrades getTradeHistory(Object... args) throws IOException, CointraderException {
    return CointraderAdapters.adaptTradeHistory(getCointraderUserTransactions((CurrencyPair) args[0], 0, 1000, 0L));
  }

  @Override
  public UserTrades getTradeHistory(TradeHistoryParams params) throws IOException {
    Integer offset = null;
    Integer limit = null;
    Long sinceTradeId = null;
    if (params instanceof TradeHistoryParamPaging) {
      final TradeHistoryParamPaging paging = (TradeHistoryParamPaging) params;
      limit = paging.getPageLength();
      if (limit != null && paging.getPageNumber() != null) {
        offset = limit * paging.getPageNumber();
      }
    }
    if (params instanceof TradeHistoryParamsIdSpan) {
      String startId = ((TradeHistoryParamsIdSpan) params).getStartId();
      if (startId != null) {
        sinceTradeId = Long.valueOf(startId);
      }
    }
    return CointraderAdapters.adaptTradeHistory(getCointraderUserTransactions(currencyPair, offset, limit, sinceTradeId));
  }

  @Override
  public HistoryParams createTradeHistoryParams() {
    return new HistoryParams();
  }

  public static class HistoryParams implements TradeHistoryParamPaging, TradeHistoryParamsIdSpan {
    private Integer pageLength;
    private Integer pageNumber;
    private Integer startId;

    @Override
    public Integer getPageLength() {
      return pageLength;
    }

    @Override
    public void setPageLength(Integer pageLength) {
      this.pageLength = pageLength;
    }

    @Override
    public Integer getPageNumber() {
      return pageNumber;
    }

    @Override
    public void setPageNumber(Integer pageNumber) {
      this.pageNumber = pageNumber;
    }

    public String getStartId() {
      return startId == null ? null : String.valueOf(startId);
    }

    public void setStartId(String startId) {
      this.startId = startId == null ? null : Integer.valueOf(startId);
    }

    @Override
    public void setEndId(String endId) {
      throw new UnsupportedOperationException("End id not supported by Cointrader.");
    }

    @Override
    public String getEndId() {
      throw new UnsupportedOperationException("End id not supported by Cointrader.");
    }
  }
}
