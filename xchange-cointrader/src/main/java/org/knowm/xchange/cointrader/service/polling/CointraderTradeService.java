package org.knowm.xchange.cointrader.service.polling;

import static org.knowm.xchange.dto.Order.OrderType.BID;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.cointrader.CointraderAdapters;
import org.knowm.xchange.cointrader.CointraderExchange;
import org.knowm.xchange.cointrader.dto.CointraderException;
import org.knowm.xchange.cointrader.dto.trade.CointraderOrder;
import org.knowm.xchange.cointrader.dto.trade.CointraderSubmitOrderResponse;
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
import org.knowm.xchange.service.polling.trade.params.TradeHistoryParamPaging;
import org.knowm.xchange.service.polling.trade.params.TradeHistoryParams;
import org.knowm.xchange.service.polling.trade.params.TradeHistoryParamsIdSpan;

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

    @Override
    public String getStartId() {
      return startId == null ? null : String.valueOf(startId);
    }

    @Override
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

  @Override
  public Collection<Order> getOrder(String... orderIds)
      throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {
    throw new NotYetImplementedForExchangeException();
  }
}
