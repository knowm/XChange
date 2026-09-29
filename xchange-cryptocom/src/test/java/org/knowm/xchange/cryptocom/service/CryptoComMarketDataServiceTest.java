package org.knowm.xchange.cryptocom.service;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.client.ResilienceRegistries;
import org.knowm.xchange.cryptocom.CryptoCom;
import org.knowm.xchange.cryptocom.CryptoComExchange;
import org.knowm.xchange.derivative.FuturesContract;
import org.knowm.xchange.exceptions.NotYetImplementedForExchangeException;
import org.knowm.xchange.instrument.Instrument;

public class CryptoComMarketDataServiceTest {

  @Test
  public void getOrderBook_rejectsNonCurrencyPairInstrument() {
    CryptoComMarketDataService service = newService();
    Instrument futures = new FuturesContract("BTC/USD/PERP");

    assertThatThrownBy(() -> service.getOrderBook(futures))
        .isInstanceOf(NotYetImplementedForExchangeException.class);
  }

  @Test
  public void getTrades_rejectsNonCurrencyPairInstrument() {
    CryptoComMarketDataService service = newService();
    Instrument futures = new FuturesContract("BTC/USD/PERP");

    assertThatThrownBy(() -> service.getTrades(futures))
        .isInstanceOf(NotYetImplementedForExchangeException.class);
  }

  private CryptoComMarketDataService newService() {
    CryptoCom cryptoCom = mock(CryptoCom.class);
    CryptoComExchange exchange = mock(CryptoComExchange.class);
    ExchangeSpecification spec = new ExchangeSpecification(CryptoComExchange.class);
    when(exchange.getExchangeSpecification()).thenReturn(spec);
    when(exchange.getCryptoCom()).thenReturn(cryptoCom);
    when(exchange.nextRequestId()).thenReturn(1L);

    return new CryptoComMarketDataService(exchange, new ResilienceRegistries());
  }
}
