package com.xeiam.xchange.justcoin.service.marketdata;

import static org.fest.assertions.api.Assertions.assertThat;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.marketdata.Ticker;
import com.xeiam.xchange.justcoin.JustcoinAdapters;
import com.xeiam.xchange.justcoin.JustcoinUtils;
import com.xeiam.xchange.justcoin.dto.marketdata.JustcoinTicker;

/**
 * @author jamespedwards42
 */
public class JustcoinTickerTest {

  private CurrencyPair currencyPair;
  private BigDecimal high;
  private BigDecimal low;
  private BigDecimal last;
  private BigDecimal bid;
  private BigDecimal ask;
  private BigDecimal volume;
  private JustcoinTicker justcoinTicker;

  @Before
  public void before() {

    // initialize expected values
    currencyPair = CurrencyPair.BTC_XRP;
    high = BigDecimal.valueOf(43998.000);
    low = BigDecimal.valueOf(40782.944);
    last = BigDecimal.valueOf(40900.000);
    bid = BigDecimal.valueOf(40905.000);
    ask = BigDecimal.valueOf(43239.000);
    volume = BigDecimal.valueOf(26.39377);
    justcoinTicker = new JustcoinTicker(JustcoinUtils.getApiMarket(currencyPair), high, low, volume, last, bid, ask, 3);
  }

  @Test
  public void testUnmarshal() throws IOException {

    // Read in the JSON from the example resources
    final InputStream is = JustcoinTickerTest.class.getResourceAsStream("/marketdata/example-ticker-data.json");

    // Use Jackson to parse it
    final ObjectMapper mapper = new ObjectMapper();
    CollectionType collectionType = mapper.getTypeFactory().constructCollectionType(List.class, JustcoinTicker.class);
    final List<JustcoinTicker> tickers = mapper.readValue(is, collectionType);

    // Verify that the example data was unmarshalled correctly
    assertThat(tickers.size()).isEqualTo(5);
    final JustcoinTicker xrpTicker = tickers.get(4);
    assertThat(xrpTicker).isEqualTo(justcoinTicker);
  }

  @Test
  public void testAdapter() {

    final List<JustcoinTicker> tickers = new ArrayList<JustcoinTicker>();
    tickers.add(justcoinTicker);
    final Ticker ticker = JustcoinAdapters.adaptTicker(tickers, currencyPair);

    assertThat(ticker.getLast()).isEqualTo(last);
    assertThat(ticker.getHigh()).isEqualTo(high);
    assertThat(ticker.getLow()).isEqualTo(low);
    assertThat(ticker.getBid()).isEqualTo(bid);
    assertThat(ticker.getAsk()).isEqualTo(ask);
    assertThat(ticker.getVolume()).isEqualTo(volume);
    assertThat(ticker.getTimestamp()).isNull();
    assertThat(ticker.getCurrencyPair()).isEqualTo(currencyPair);
  }
}
