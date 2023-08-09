package info.bitrich.xchangestream.gateio;

import io.reactivex.Observable;
import org.junit.jupiter.api.Test;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.OrderBook;

public class GateioStreamingMarketDataServiceIntegration extends GateioStreamingExchangeIT {

  @Test
  void order_book() {
    Observable<OrderBook> observable = exchange
        .getStreamingMarketDataService()
        .getOrderBook(CurrencyPair.BTC_USDT);

    observable.test()
        .assertSubscribed()
        .awaitCount(1)
        .assertNoTimeout()
        .assertValueAt(0, orderBook -> !orderBook.getBids().isEmpty() || !orderBook.getAsks().isEmpty())
        .dispose();
  }

}