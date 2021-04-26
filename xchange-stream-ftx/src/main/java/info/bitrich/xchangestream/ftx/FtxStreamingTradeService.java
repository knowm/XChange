package info.bitrich.xchangestream.ftx;

import info.bitrich.xchangestream.core.StreamingTradeService;
import io.reactivex.Observable;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.trade.UserTrade;

public class FtxStreamingTradeService implements StreamingTradeService {

  private final FtxStreamingService service;

  public FtxStreamingTradeService(FtxStreamingService service) {
    this.service = service;
  }

  @Override
  public Observable<UserTrade> getUserTrades(CurrencyPair currencyPair, Object... args) {

    return service
        .subscribeChannel("fills")
        .filter(
            jsonNode ->
                jsonNode.hasNonNull("data")
                    && new CurrencyPair(jsonNode.get("data").get("market").asText())
                        .equals(currencyPair))
        .map(FtxStreamingAdapters::adaptUserTrade);
  }
}
