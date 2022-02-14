package org.knowm.xchange.quoine.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.spy;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.quoine.QuoineExchange;
import org.knowm.xchange.quoine.dto.marketdata.QuoineProduct;
import org.knowm.xchange.service.marketdata.params.CurrencyPairsParam;
import org.knowm.xchange.service.marketdata.params.Params;

public class QuoineMarketDataServiceTest {
  private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

  private Exchange exchange;

  private QuoineMarketDataService service;

  @Before
  public void setUp() throws IOException {
    OBJECT_MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    ExchangeSpecification specification = new ExchangeSpecification(QuoineExchange.class);

    specification.setShouldLoadRemoteMetaData(false);

    exchange = ExchangeFactory.INSTANCE.createExchange(specification);
    service = spy((QuoineMarketDataService) exchange.getMarketDataService());

    doReturn(buildProducts()).when(service).getQuoineProducts();
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetTickersNullParam() throws IOException {
    service.getTickers(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGetTickersWrongParamType() throws IOException {
    Params params = new Params() {};

    service.getTickers(params);
  }

  @Test
  public void testGetTickers() throws IOException {
    CurrencyPairsParam params = () -> Arrays.asList(CurrencyPair.BTC_USD, CurrencyPair.ETH_USD);

    List<Ticker> tickers = service.getTickers(params);

    assertEquals(params.getCurrencyPairs().size(), tickers.size());

    Ticker btcUsd =
        tickers.stream()
            .filter(ticker -> ticker.getCurrencyPair().equals(CurrencyPair.BTC_USD))
            .findFirst()
            .orElseThrow(() -> new NullPointerException("Required Ticker not found!"));

    Ticker ethUsd =
        tickers.stream()
            .filter(ticker -> ticker.getCurrencyPair().equals(CurrencyPair.ETH_USD))
            .findFirst()
            .orElseThrow(() -> new NullPointerException("Required Ticker not found!"));

    assertNotNull(btcUsd);
    assertNotNull(ethUsd);
  }

  private QuoineProduct[] buildProducts() throws IOException {
    InputStream is =
        QuoineMarketDataServiceTest.class
            .getClassLoader()
            .getResourceAsStream("org/knowm/xchange/quoine/service/example-products.json");

    assertNotNull(is);

    return OBJECT_MAPPER.readValue(is, QuoineProduct[].class);
  }
}
