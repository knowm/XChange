package info.bitrich.xchangestream.binance;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.knowm.xchange.Exchange.USE_SANDBOX;
import static org.knowm.xchange.binance.BinanceExchange.EXCHANGE_TYPE;
import static org.knowm.xchange.binance.dto.ExchangeType.FUTURES;
import static org.knowm.xchange.binance.dto.ExchangeType.SPOT;

import info.bitrich.xchangestream.binancefuture.BinanceFutureStreamingExchange;
import info.bitrich.xchangestream.core.ProductSubscription;
import info.bitrich.xchangestream.core.StreamingExchange;
import info.bitrich.xchangestream.core.StreamingExchangeFactory;
import io.reactivex.disposables.Disposable;
import java.io.IOException;
import org.junit.Ignore;
import org.junit.Test;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.binance.dto.ExchangeType;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.derivative.FuturesContract;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trade;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.instrument.Instrument;

// Github build give http 451 error(Unavailable For Legal Reasons)
@Ignore
public class BinanceStreamExchangeTypeIntegration {

  @Test
  public void testConnections() throws InterruptedException, IOException {
    testConnection(new CurrencyPair("ETH/USDT"),  getSpec(SPOT, false));
    testConnection(new CurrencyPair("ETH/USDT"), getSpec1(SPOT, true));
    testConnection(new FuturesContract("ETH/USDT/PERP"), getSpec1(FUTURES, false));
    testConnection(new FuturesContract("ETH/USDT/PERP"),  getSpec(FUTURES, true));
  }

  private static void testConnection(Instrument instrument, ExchangeSpecification spec)
      throws InterruptedException, IOException {
    StreamingExchange exchange =
        StreamingExchangeFactory.INSTANCE.createExchange(spec);
    ProductSubscription subscription = ProductSubscription.create()
        .addOrderbook(instrument)
        .addTicker(instrument)
        .addFundingRates(instrument)
        .addTrades(instrument)
        .build();
    exchange.connect(subscription).blockingAwait();
    Trades trades = exchange.getMarketDataService().getTrades(instrument);
    for(Trade trade : trades.getTrades())
     tradeCheck(trade,instrument);
    Disposable disposable = exchange.getStreamingMarketDataService().getTicker(instrument)
        .subscribe(t -> tickerCheck(t, instrument));
    Thread.sleep(3000L);
    disposable.dispose();
    Thread.sleep(500L);
    exchange.disconnect().blockingAwait();
  }

  private static void tickerCheck(Ticker ticker, Instrument   instrument) {
    assertThat(ticker.getInstrument()).isEqualTo(instrument);
    assertThat(ticker.getHigh()).isNotNull();
    assertThat(ticker.getVolume()).isNotNull();
    assertThat(ticker.getTimestamp()).isNotNull();
  }

  private static void tradeCheck(Trade trade, Instrument   instrument) {
    assertThat(trade.getInstrument()).isEqualTo(instrument);
    assertThat(trade.getOriginalAmount()).isNotNull();
    assertThat(trade.getPrice()).isNotNull();
    assertThat(trade.getTimestamp()).isNotNull();
  }

  private static ExchangeSpecification getSpec(ExchangeType exchangeType, boolean useSandbox) {
    ExchangeSpecification exchangeSpecification;
    if (exchangeType == SPOT) {
      exchangeSpecification = new BinanceStreamingExchange().getDefaultExchangeSpecification();
    } else {
      exchangeSpecification = new BinanceFutureStreamingExchange().getDefaultExchangeSpecification();
    }
    exchangeSpecification.setExchangeSpecificParametersItem(EXCHANGE_TYPE, exchangeType);
    if (useSandbox) {
      exchangeSpecification.setExchangeSpecificParametersItem(USE_SANDBOX, true);
    }
    return exchangeSpecification;
  }

  private static ExchangeSpecification getSpec1(ExchangeType exchangeType, boolean useSandbox) {
    ExchangeSpecification exchangeSpecification;
    if (exchangeType == SPOT) {
      exchangeSpecification =
          new ExchangeSpecification(BinanceStreamingExchange.class);
    } else {
      exchangeSpecification =
          new ExchangeSpecification(BinanceFutureStreamingExchange.class);
    }
    exchangeSpecification.setExchangeSpecificParametersItem(EXCHANGE_TYPE, exchangeType);
    if (useSandbox) {
      exchangeSpecification.setExchangeSpecificParametersItem(USE_SANDBOX, true);
    }
    return exchangeSpecification;
  }
}
