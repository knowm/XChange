package com.xeiam.xchange.taurus.service.polling;

import static com.xeiam.xchange.dto.Order.OrderType.BID;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.Order.OrderType;
import com.xeiam.xchange.dto.trade.LimitOrder;
import com.xeiam.xchange.dto.trade.MarketOrder;
import com.xeiam.xchange.dto.trade.OpenOrders;
import com.xeiam.xchange.dto.trade.UserTrades;
import com.xeiam.xchange.exceptions.NotYetImplementedForExchangeException;
import com.xeiam.xchange.service.polling.trade.PollingTradeService;
import com.xeiam.xchange.service.polling.trade.params.DefaultTradeHistoryParamPaging;
import com.xeiam.xchange.service.polling.trade.params.TradeHistoryParamOffset;
import com.xeiam.xchange.service.polling.trade.params.TradeHistoryParamPaging;
import com.xeiam.xchange.service.polling.trade.params.TradeHistoryParams;
import com.xeiam.xchange.service.polling.trade.params.TradeHistoryParamsSorted;
import com.xeiam.xchange.taurus.TaurusAdapters;
import com.xeiam.xchange.taurus.dto.TaurusException;
import com.xeiam.xchange.taurus.dto.trade.TaurusOrder;

/**
 * @author Matija Mazi
 */
public class TaurusTradeService extends TaurusTradeServiceRaw implements PollingTradeService {

  public TaurusTradeService(Exchange exchange) {

    super(exchange);
  }

  @Override
  public OpenOrders getOpenOrders() throws IOException, TaurusException {
    TaurusOrder[] openOrders = getTaurusOpenOrders();

    List<LimitOrder> limitOrders = new ArrayList<LimitOrder>();
    for (TaurusOrder taurusOrder : openOrders) {
      OrderType orderType = taurusOrder.getType();
      String id = taurusOrder.getId();
      BigDecimal price = taurusOrder.getPrice();
      limitOrders.add(new LimitOrder(orderType, taurusOrder.getAmount(), CurrencyPair.BTC_CAD, id, taurusOrder.getDatetime(), price));
    }
    return new OpenOrders(limitOrders);
  }

  @Override
  public String placeMarketOrder(MarketOrder marketOrder) throws IOException, TaurusException {
    throw new NotYetImplementedForExchangeException();
  }

  @Override
  public String placeLimitOrder(LimitOrder limitOrder) throws IOException, TaurusException {
    TaurusOrder taurusOrder = limitOrder.getType() == BID ? buyTaurusOrder(limitOrder.getTradableAmount(), limitOrder.getLimitPrice())
        : sellTaurusOrder(limitOrder.getTradableAmount(), limitOrder.getLimitPrice());

    return taurusOrder.getId();
  }

  @Override
  public boolean cancelOrder(String orderId) throws IOException, TaurusException {
    return cancelTaurusOrder(orderId);
  }

  @Override
  public UserTrades getTradeHistory(Object... args) throws IOException, TaurusException {
    if (args.length == 0) {
      return getTradeHistory(createTradeHistoryParams());
    }
    throw new IllegalArgumentException("Please use getTradeHistory(TradeHistoryParams).");
  }

  @Override
  public UserTrades getTradeHistory(TradeHistoryParams params) throws IOException {
    Integer offset = null;
    Integer limit = null;
    TradeHistoryParamsSorted.Order sort = null;
    if (params instanceof TradeHistoryParamsSorted) {
      sort = ((TradeHistoryParamsSorted) params).getOrder();
    }
    if (params instanceof TradeHistoryParamPaging) {
      final TradeHistoryParamPaging paging = (TradeHistoryParamPaging) params;
      limit = paging.getPageLength();
      offset = paging.getPageLength() * paging.getPageNumber();
    }
    if (params instanceof TradeHistoryParamOffset) {
      final Long longOffset = ((TradeHistoryParamOffset) params).getOffset();
      offset = longOffset == null ? null : longOffset.intValue();
    }
    return TaurusAdapters.adaptTradeHistory(getTaurusUserTransactions(offset, limit, sort));
  }

  @Override
  public TradeHistoryParams createTradeHistoryParams() {
    return new DefaultTradeHistoryParamPaging(1000);
  }

}
