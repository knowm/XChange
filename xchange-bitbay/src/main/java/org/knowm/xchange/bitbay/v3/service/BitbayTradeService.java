package org.knowm.xchange.bitbay.v3.service;

import java.io.IOException;
import java.util.Collection;
import java.util.stream.Collectors;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.bitbay.v3.BitbayAdapters;
import org.knowm.xchange.bitbay.v3.dto.trade.BitbayUserTrades;
import org.knowm.xchange.bitbay.v3.dto.trade.BitbayUserTradesQuery;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.trade.UserTrades;
import org.knowm.xchange.service.trade.TradeService;
import org.knowm.xchange.service.trade.params.TradeHistoryParamLimit;
import org.knowm.xchange.service.trade.params.TradeHistoryParamMultiCurrencyPair;
import org.knowm.xchange.service.trade.params.TradeHistoryParamNextPageCursor;
import org.knowm.xchange.service.trade.params.TradeHistoryParams;

/** @author walec51 */
public class BitbayTradeService extends BitbayTradeServiceRaw implements TradeService {

  public BitbayTradeService(Exchange exchange) {
    super(exchange);
  }

  @Override
  public UserTrades getTradeHistory(TradeHistoryParams params) throws IOException {
    final BitbayUserTradesQuery query = new BitbayUserTradesQuery();
    if (params instanceof TradeHistoryParamNextPageCursor) {
      query.setNextPageCursor(((TradeHistoryParamNextPageCursor) params).getNextPageCursor());
    }
    if (params instanceof TradeHistoryParamLimit) {
      Integer limit = ((TradeHistoryParamLimit) params).getLimit();
      query.setLimit(limit != null ? limit.toString() : null);
    }
    if (params instanceof TradeHistoryParamMultiCurrencyPair) {
      final Collection<CurrencyPair> currencyPairs =
          ((TradeHistoryParamMultiCurrencyPair) params).getCurrencyPairs();
      query.setMarkets(
          currencyPairs.stream()
              .map(BitbayAdapters::adaptCurrencyPair)
              .collect(Collectors.toList()));
    }
    final BitbayUserTrades response = getBitbayTransactions(query);
    return BitbayAdapters.adaptUserTrades(response);
  }

  @Override
  public TradeHistoryParams createTradeHistoryParams() {
    return new BitbayTradeHistoryParams();
  }
}
