package com.xeiam.xchange.bitvc.service.polling;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.xeiam.xchange.ExchangeException;
import com.xeiam.xchange.NotAvailableFromExchangeException;
import com.xeiam.xchange.NotYetImplementedForExchangeException;
import com.xeiam.xchange.bitvc.BitVcAdapters;
import com.xeiam.xchange.bitvc.dto.trade.BitVcCancelOrderResult;
import com.xeiam.xchange.bitvc.dto.trade.BitVcOrder;
import com.xeiam.xchange.bitvc.dto.trade.BitVcPlaceOrderResult;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.marketdata.TradeServiceHelper;
import com.xeiam.xchange.dto.trade.LimitOrder;
import com.xeiam.xchange.dto.trade.MarketOrder;
import com.xeiam.xchange.dto.trade.OpenOrders;
import com.xeiam.xchange.dto.trade.UserTrades;
import com.xeiam.xchange.huobi.service.polling.TradeServiceRaw;
import com.xeiam.xchange.service.polling.PollingTradeService;
import com.xeiam.xchange.service.polling.trade.TradeHistoryParams;

public class BitVcTradeService implements PollingTradeService {
  private final Map<CurrencyPair, Integer> coinTypes;
  private final TradeServiceRaw tradeServiceRaw;
  private static final OpenOrders noOpenOrders = new OpenOrders(Collections.<LimitOrder> emptyList());

  public BitVcTradeService(TradeServiceRaw tradeServiceRaw) {

    this.tradeServiceRaw = tradeServiceRaw; 
    coinTypes = new HashMap<CurrencyPair, Integer>(2);
    coinTypes.put(CurrencyPair.BTC_CNY, 1);
    coinTypes.put(CurrencyPair.LTC_CNY, 2);
  }

  @Override
  public OpenOrders getOpenOrders() throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {

    List<LimitOrder> openOrders = new ArrayList<LimitOrder>();
    for (CurrencyPair currencyPair : getExchangeSymbols()) {
      BitVcOrder[] orders = tradeServiceRaw.getBitVcOrders(coinTypes.get(currencyPair));

      for (int i = 0; i < orders.length; i++) {
        openOrders.add(BitVcAdapters.adaptOpenOrder(orders[i], currencyPair));
      }
    }

    if (openOrders.size() <= 0) {
      return noOpenOrders;
    }

    return new OpenOrders(openOrders);
  }

  @Override
  public String placeMarketOrder(MarketOrder marketOrder) throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {

    BitVcPlaceOrderResult result = tradeServiceRaw.placeBitVcMarketOrder(marketOrder.getType(), coinTypes.get(marketOrder.getCurrencyPair()), marketOrder.getTradableAmount());
    return BitVcAdapters.adaptPlaceOrderResult(result);
  }

  @Override
  public String placeLimitOrder(LimitOrder limitOrder) throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {

    BitVcPlaceOrderResult result = tradeServiceRaw.placeBitVcLimitOrder(limitOrder.getType(), coinTypes.get(limitOrder.getCurrencyPair()), limitOrder.getLimitPrice(), limitOrder.getTradableAmount());
    return BitVcAdapters.adaptPlaceOrderResult(result);
  }

  @Override
  public boolean cancelOrder(String orderId) throws IOException {

    final long id = Long.parseLong(orderId);

    BitVcCancelOrderResult result = null;
    for (CurrencyPair currencyPair : getExchangeSymbols()) {
      result = tradeServiceRaw.cancelBitVcOrder(coinTypes.get(currencyPair), id);

      if (result.getCode() == 0) {
        break;
      }
      else if (result.getCode() == 26) { // Order does not exist
        continue;
      }
      else {
        break;
      }
    }
    return result != null && "success".equals(result.getResult());
  }

  @Override
  public UserTrades getTradeHistory(Object... arguments) {

    throw new NotAvailableFromExchangeException();
  }

  @Override
  public UserTrades getTradeHistory(TradeHistoryParams params) throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {
    throw new NotAvailableFromExchangeException();
  }

  @Override
  public com.xeiam.xchange.service.polling.trade.TradeHistoryParams createTradeHistoryParams() {
    return null;
  }

  @Override
  public Collection<CurrencyPair> getExchangeSymbols() throws IOException {
    return tradeServiceRaw.getExchangeSymbols();
  }

  /**
   * Fetch the {@link com.xeiam.xchange.dto.marketdata.TradeServiceHelper} from the exchange.
   *
   * @return Map of currency pairs to their corresponding metadata.
   * @see com.xeiam.xchange.dto.marketdata.TradeServiceHelper
   */
  @Override public Map<CurrencyPair, ? extends TradeServiceHelper> getTradeServiceHelperMap() throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {
    throw new NotAvailableFromExchangeException();
  }

}
