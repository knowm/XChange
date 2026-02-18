package info.bitrich.xchangestream.binance.examples;

import static org.knowm.xchange.binance.BinanceExchange.EXCHANGE_TYPE;
import static org.knowm.xchange.binance.dto.ExchangeType.PORTFOLIO_MARGIN;

import info.bitrich.xchangestream.binancefuture.BinanceFutureStreamingExchange;
import info.bitrich.xchangestream.core.StreamingExchange;
import info.bitrich.xchangestream.core.StreamingExchangeFactory;
import java.io.IOException;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.derivative.FuturesContract;
import org.knowm.xchange.instrument.Instrument;
import org.knowm.xchange.utils.AuthUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Ignore
public class BinancePortfolioMarginStreamPrivateTest {
  private static final Logger LOG =
      LoggerFactory.getLogger(BinancePortfolioMarginStreamPrivateTest.class);
  private static StreamingExchange exchange;
  BinanceFutureStreamingExchange binanceFutureStreamingExchange;
  private static final Instrument instrument = new FuturesContract("XRP/USDT/PERP");
  private static final Instrument instrument2 = new FuturesContract("SOL/USDT/PERP");

  @Before
  public void setUp() {
    ExchangeSpecification spec = new ExchangeSpecification(BinanceFutureStreamingExchange.class);
    // The most convenient way. Can store all keys in .ssh folder
    AuthUtils.setApiAndSecretKey(spec, "binance-main");
    spec.setExchangeSpecificParametersItem(EXCHANGE_TYPE, PORTFOLIO_MARGIN);
    // not ready yet
    exchange = StreamingExchangeFactory.INSTANCE.createExchange(spec);
    binanceFutureStreamingExchange = (BinanceFutureStreamingExchange) exchange;
  }

  // Warning - only main net is supported
  @Test
  public void getOrderAndPositionChanges() throws IOException {}
}
