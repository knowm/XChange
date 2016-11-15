package org.knowm.xchange.bitstamp.service.polling;

import static org.knowm.xchange.dto.Order.OrderType.BID;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.bitstamp.BitstampAdapters;
import org.knowm.xchange.bitstamp.BitstampAuthenticatedV2;
import org.knowm.xchange.bitstamp.BitstampExchange;
import org.knowm.xchange.bitstamp.dto.BitstampException;
import org.knowm.xchange.bitstamp.dto.trade.BitstampOrder;
import org.knowm.xchange.bitstamp.dto.trade.BitstampUserTransaction;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.Order.OrderType;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.MarketOrder;
import org.knowm.xchange.dto.trade.OpenOrders;
import org.knowm.xchange.dto.trade.UserTrades;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.exceptions.NotAvailableFromExchangeException;
import org.knowm.xchange.exceptions.NotYetImplementedForExchangeException;
import org.knowm.xchange.service.polling.trade.PollingTradeService;
import org.knowm.xchange.service.polling.trade.params.TradeHistoryParamCurrencyPair;
import org.knowm.xchange.service.polling.trade.params.TradeHistoryParamOffset;
import org.knowm.xchange.service.polling.trade.params.TradeHistoryParamPaging;
import org.knowm.xchange.service.polling.trade.params.TradeHistoryParams;
import org.knowm.xchange.service.polling.trade.params.TradeHistoryParamsSorted;

/**
 * @author Matija Mazi
 */
public class BitstampTradeService extends BitstampTradeServiceRaw implements PollingTradeService {

  private static final List<CurrencyPair> ALL_PAIRS = Arrays.asList(CurrencyPair.BTC_USD, CurrencyPair.BTC_EUR, CurrencyPair.EUR_USD);

  public BitstampTradeService(Exchange exchange) {
    super(exchange);
  }

  @Override
  public OpenOrders getOpenOrders() throws IOException, BitstampException {
    CurrencyPair cp = (CurrencyPair) exchange.getExchangeSpecification().getExchangeSpecificParameters().get(BitstampExchange.CURRENCY_PAIR);
    Collection<CurrencyPair> pairs = cp != null ? Collections.singleton(cp) : ALL_PAIRS;
    List<LimitOrder> limitOrders = new ArrayList<>();
    for (CurrencyPair pair : pairs) {
      BitstampOrder[] openOrders = getBitstampOpenOrders(pair);
      for (BitstampOrder bitstampOrder : openOrders) {
        OrderType orderType = bitstampOrder.getType() == 0 ? OrderType.BID : OrderType.ASK;
        String id = Integer.toString(bitstampOrder.getId());
        BigDecimal price = bitstampOrder.getPrice();
        limitOrders.add(new LimitOrder(orderType, bitstampOrder.getAmount(), pair, id, bitstampOrder.getTime(), price));
      }
    }
    return new OpenOrders(limitOrders);
  }

  @Override
  public String placeMarketOrder(MarketOrder marketOrder) throws IOException, BitstampException {

    throw new NotAvailableFromExchangeException();
  }

  @Override
  public String placeLimitOrder(LimitOrder order) throws IOException, BitstampException {
    BitstampAuthenticatedV2.Side side = order.getType().equals(BID) ? BitstampAuthenticatedV2.Side.buy : BitstampAuthenticatedV2.Side.sell;
    BitstampOrder bitstampOrder = placeBitstampOrder(order.getCurrencyPair(), side, order.getTradableAmount(), order.getLimitPrice());
    if (bitstampOrder.getErrorMessage() != null) {
      throw new ExchangeException(bitstampOrder.getErrorMessage());
    }
    return Integer.toString(bitstampOrder.getId());
  }

  @Override
  public boolean cancelOrder(String orderId) throws IOException, BitstampException {

    return cancelBitstampOrder(Integer.parseInt(orderId));
  }

  /**
   * Required parameter types: {@link TradeHistoryParamPaging#getPageLength()}
   */
  @Override
  public UserTrades getTradeHistory(TradeHistoryParams params) throws IOException {
    Long limit = null;
    CurrencyPair currencyPair = null;
    Long offset = null;
    TradeHistoryParamsSorted.Order sort = null;
    if (params instanceof TradeHistoryParamPaging) {
      limit = Long.valueOf(((TradeHistoryParamPaging) params).getPageLength());
    }
    if (params instanceof TradeHistoryParamCurrencyPair) {
      currencyPair = ((TradeHistoryParamCurrencyPair) params).getCurrencyPair();
    }
    if (params instanceof TradeHistoryParamOffset) {
      offset = ((TradeHistoryParamOffset) params).getOffset();
    }
    if (params instanceof TradeHistoryParamsSorted) {
      sort = ((TradeHistoryParamsSorted) params).getOrder();
    }
    BitstampUserTransaction[] txs = getBitstampUserTransactions(limit, currencyPair, offset, sort == null ? null : sort.toString());
    return BitstampAdapters.adaptTradeHistory(txs);
  }

  @Override
  public TradeHistoryParams createTradeHistoryParams() {

    return new BitstampTradeHistoryParams(CurrencyPair.BTC_USD, 1000);
  }

  @Override
  public Collection<Order> getOrder(String... orderIds)
      throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {
    throw new NotYetImplementedForExchangeException();
  }

}
