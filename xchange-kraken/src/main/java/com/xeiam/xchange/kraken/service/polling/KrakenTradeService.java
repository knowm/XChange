package com.xeiam.xchange.kraken.service.polling;

import com.xeiam.xchange.ExchangeException;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.NotAvailableFromExchangeException;
import com.xeiam.xchange.NotYetImplementedForExchangeException;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.marketdata.MarketMetadata;
import com.xeiam.xchange.dto.trade.LimitOrder;
import com.xeiam.xchange.dto.trade.MarketOrder;
import com.xeiam.xchange.dto.trade.OpenOrders;
import com.xeiam.xchange.dto.trade.UserTrades;
import com.xeiam.xchange.kraken.KrakenAdapters;
import com.xeiam.xchange.service.polling.PollingTradeService;
import com.xeiam.xchange.service.polling.trade.TradeHistoryParamOffset;
import com.xeiam.xchange.service.polling.trade.TradeHistoryParams;
import com.xeiam.xchange.service.polling.trade.TradeHistoryParamsTimeSpan;
import com.xeiam.xchange.service.polling.trade.DefaultTradeHistoryParamsTimeSpan;
import si.mazi.rescu.SynchronizedValueFactory;

import java.io.IOException;
import java.util.Date;
import java.util.Map;

public class KrakenTradeService extends KrakenTradeServiceRaw implements PollingTradeService {

  public KrakenTradeService(ExchangeSpecification exchangeSpecification, SynchronizedValueFactory<Long> nonceFactory) {

    super(exchangeSpecification, nonceFactory);
  }

  @Override
  public OpenOrders getOpenOrders() throws IOException {

    return KrakenAdapters.adaptOpenOrders(super.getKrakenOpenOrders());
  }

  @Override
  public String placeMarketOrder(MarketOrder marketOrder) throws IOException {

    return KrakenAdapters.adaptOrderId(super.placeKrakenMarketOrder(marketOrder));
  }

  @Override
  public String placeLimitOrder(LimitOrder limitOrder) throws IOException {

    return KrakenAdapters.adaptOrderId(super.placeKrakenLimitOrder(limitOrder));
  }

  @Override
  public boolean cancelOrder(String orderId) throws IOException {

    return super.cancelKrakenOrder(orderId).getCount() > 0;
  }

  @Override
  public UserTrades getTradeHistory(Object... args) throws IOException {

    return KrakenAdapters.adaptTradesHistory(getKrakenTradeHistory());
  }

  /**
   * Required parameters
   * {@link TradeHistoryParamsTimeSpan}
   * {@link TradeHistoryParamOffset}
   */
  @Override
  public UserTrades getTradeHistory(TradeHistoryParams params) throws ExchangeException, IOException {

    TradeHistoryParamsTimeSpan timeSpan = (TradeHistoryParamsTimeSpan) params;
    TradeHistoryParamOffset offset = (TradeHistoryParamOffset) params;

    return KrakenAdapters.adaptTradesHistory(getKrakenTradeHistory(null, false, getTime(timeSpan.getStartTime()), getTime(timeSpan.getEndTime()), offset.getOffset()));
  }

  @Override
  public com.xeiam.xchange.service.polling.trade.TradeHistoryParams createTradeHistoryParams() {

    return new KrakenTradeHistoryParams();
  }

  /**
   * Fetch the {@link com.xeiam.xchange.dto.marketdata.MarketMetadata} from the exchange.
   *
   * @return Map of currency pairs to their corresponding metadata.
   * @see com.xeiam.xchange.dto.marketdata.MarketMetadata
   */
  @Override public Map<CurrencyPair, ? extends MarketMetadata> getMarketMetadata() throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {
    throw new NotAvailableFromExchangeException();
  }

  private static Long getTime(Date date) {

    return date == null ? null : date.getTime();
  }

  public static class KrakenTradeHistoryParams extends DefaultTradeHistoryParamsTimeSpan implements TradeHistoryParamOffset {

    private Long offset;

    @Override
    public void setOffset(Long offset) {
      this.offset = offset;
    }

    @Override
    public Long getOffset() {
      return offset;
    }
  }

}
