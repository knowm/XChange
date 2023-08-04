package info.bitrich.xchangestream.binancefuture;

import com.fasterxml.jackson.databind.JsonNode;
import info.bitrich.xchangestream.binance.BinanceStreamingTradeService;
import info.bitrich.xchangestream.binance.BinanceUserDataStreamingService;
import info.bitrich.xchangestream.binance.dto.BaseBinanceWebSocketTransaction;
import info.bitrich.xchangestream.binancefuture.dto.BinanceFuturesOrderUpdateRaw;
import info.bitrich.xchangestream.binancefuture.dto.BinanceFuturesOrderUpdateTransaction;
import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.Subject;
import java.io.IOException;
import org.knowm.xchange.derivative.FuturesContract;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.exceptions.ExchangeSecurityException;

public class BinanceFuturesStreamingTradeService extends BinanceStreamingTradeService {

  private final Subject<BinanceFuturesOrderUpdateTransaction> executionReportsPublisher =
      PublishSubject.<BinanceFuturesOrderUpdateTransaction>create().toSerialized();

  public BinanceFuturesStreamingTradeService(
      BinanceUserDataStreamingService binanceUserDataStreamingService) {
    super(binanceUserDataStreamingService);
  }

  public Observable<BinanceFuturesOrderUpdateRaw> getOrderUpdate() {
    checkConnected();
    return executionReportsPublisher.map(BinanceFuturesOrderUpdateRaw::new);
  }

  public Observable<BinanceFuturesOrderUpdateRaw> getOrderUpdate(FuturesContract contract) {
    checkConnected();
    return getOrderUpdate().filter(t -> t.getContract().equals(contract));
  }

  private void checkConnected() {
    if (binanceUserDataStreamingService == null
        || !binanceUserDataStreamingService.isSocketOpen()) {
      throw new ExchangeSecurityException("Not authenticated");
    }
  }

  @Override
  public void openSubscriptions() {
    if (binanceUserDataStreamingService != null) {
      executionReports =
          binanceUserDataStreamingService
              .subscribeChannel(
                  BaseBinanceWebSocketTransaction.BinanceWebSocketTypes.ORDER_TRADE_UPDATE)
              .map(this::executionReport)
              .subscribe(executionReportsPublisher::onNext);
    }
  }

  private BinanceFuturesOrderUpdateTransaction executionReport(JsonNode json) {
    try {
      return mapper.treeToValue(json, BinanceFuturesOrderUpdateTransaction.class);
    } catch (IOException e) {
      throw new ExchangeException("Unable to parse execution report", e);
    }
  }
}
