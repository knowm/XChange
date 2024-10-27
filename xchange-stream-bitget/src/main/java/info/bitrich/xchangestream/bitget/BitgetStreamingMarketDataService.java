package info.bitrich.xchangestream.bitget;

import info.bitrich.xchangestream.bitget.dto.common.BitgetChannel.ChannelType;
import info.bitrich.xchangestream.bitget.dto.common.BitgetChannel.MarketType;
import info.bitrich.xchangestream.bitget.dto.response.BitgetTickerNotification;
import info.bitrich.xchangestream.core.StreamingMarketDataService;
import io.reactivex.rxjava3.core.Observable;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.Ticker;

public class BitgetStreamingMarketDataService implements StreamingMarketDataService {

  private final BitgetStreamingService service;

  public BitgetStreamingMarketDataService(BitgetStreamingService service) {
    this.service = service;
  }


  @Override
  public Observable<Ticker> getTicker(CurrencyPair currencyPair, Object... args) {
    return service
        .subscribeChannel(service.getSubscriptionUniqueId(ChannelType.TICKER.name(), MarketType.SPOT, currencyPair))
        .map(BitgetTickerNotification.class::cast)
        .map(BitgetStreamingAdapters::toTicker);
  }

}
