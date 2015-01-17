package com.xeiam.xchange.kraken.service.polling;

import com.xeiam.xchange.ExchangeException;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.NotAvailableFromExchangeException;
import com.xeiam.xchange.NotYetImplementedForExchangeException;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.marketdata.BaseTradeServiceHelper;
import com.xeiam.xchange.dto.marketdata.TradeServiceHelper;
import com.xeiam.xchange.dto.trade.LimitOrder;
import com.xeiam.xchange.dto.trade.MarketOrder;
import com.xeiam.xchange.dto.trade.OpenOrders;
import com.xeiam.xchange.dto.trade.UserTrades;
import com.xeiam.xchange.kraken.KrakenAdapters;
import com.xeiam.xchange.kraken.dto.marketdata.KrakenAssetPair;
import com.xeiam.xchange.service.polling.PollingTradeService;
import com.xeiam.xchange.service.polling.trade.TradeHistoryParamOffset;
import com.xeiam.xchange.service.polling.trade.TradeHistoryParams;
import com.xeiam.xchange.service.polling.trade.TradeHistoryParamsTimeSpan;
import com.xeiam.xchange.service.polling.trade.DefaultTradeHistoryParamsTimeSpan;
import si.mazi.rescu.SynchronizedValueFactory;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static com.xeiam.xchange.utils.TradeServiceHelperConfigurer.CFG;

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
   * Fetch the {@link com.xeiam.xchange.dto.marketdata.TradeServiceHelper} from the exchange.
   *
   * @return Map of currency pairs to their corresponding metadata.
   * @see com.xeiam.xchange.dto.marketdata.TradeServiceHelper
   */
  @Override public Map<CurrencyPair, ? extends TradeServiceHelper> getTradeServiceHelperMap() throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {
    Map<CurrencyPair, BaseTradeServiceHelper> result = new HashMap<CurrencyPair, BaseTradeServiceHelper>();

    Map<String, KrakenAssetPair> assetPairs = getKrakenAssetPairs().getAssetPairMap();
    for (Map.Entry<String, KrakenAssetPair> e : assetPairs.entrySet()) {
      String krakenPair = e.getKey();
      CurrencyPair pair = KrakenAdapters.adaptCurrencyPair(krakenPair);

      KrakenAssetPair assetPair = e.getValue();
      BigDecimal amountMinimum = CFG.getBigDecimalProperty(KEY_ORDER_SIZE_MIN_DEFAULT).setScale(assetPair.getVolumeLotScale(), BigDecimal.ROUND_UNNECESSARY);
      BaseTradeServiceHelper tradeServiceHelper = new BaseTradeServiceHelper(amountMinimum, assetPair.getPairScale());

      result.put(pair, tradeServiceHelper);
    }

    return result;
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
