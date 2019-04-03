package org.knowm.xchange.oer.service;

import java.io.IOException;
import org.junit.Test;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.oer.OER;
import org.knowm.xchange.oer.OERExchange;
import org.knowm.xchange.oer.dto.marketdata.OERRates;
import org.knowm.xchange.oer.dto.marketdata.OERTickers;
import org.mockito.Mockito;
import org.powermock.reflect.Whitebox;

public class OERMarketDataServiceRawTest {

  @Test
  public void testProxyIsCalledWithCorrectParameters() throws IOException {
    Exchange exchange = ExchangeFactory.INSTANCE.createExchange(OERExchange.class.getName());
    OERMarketDataServiceRaw serviceRaw = new OERMarketDataServiceRaw(exchange);
    OER oerMock = Mockito.mock(OER.class);
    OERTickers tickers = new OERTickers(new OERRates(), 0L);
    tickers.getRates().setAUD(1.23d);
    Mockito.when(oerMock.getTickers(null, Currency.USD.toString(), Currency.AUD.toString()))
        .thenReturn(tickers);
    Whitebox.setInternalState(serviceRaw, "openExchangeRates", oerMock);

    serviceRaw.getOERTicker(CurrencyPair.USD_AUD);

    Mockito.verify(oerMock).getTickers(null, Currency.USD.toString(), Currency.AUD.toString());
  }
}
