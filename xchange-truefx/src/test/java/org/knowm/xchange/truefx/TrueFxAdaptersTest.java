package org.knowm.xchange.truefx;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import org.junit.Test;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.truefx.dto.marketdata.TrueFxTicker;
import org.knowm.xchange.truefx.service.TrueFxMarketDataServiceRaw;

public class TrueFxAdaptersTest {
  @Test
  public void adaptTickerTest() throws JsonParseException, JsonMappingException, IOException {
    InputStream is = getClass().getResourceAsStream("/marketdata/example-ticker.csv");

    Exchange exchange = ExchangeFactory.INSTANCE.createExchange(TrueFxExchange.class.getName());
    TrueFxMarketDataServiceRaw rawService =
        (TrueFxMarketDataServiceRaw) exchange.getMarketDataService();
    ObjectMapper mapper = rawService.createObjectMapper();

    TrueFxTicker rawTicker = mapper.readValue(is, TrueFxTicker.class);
    Ticker ticker = TrueFxAdapters.adaptTicker(rawTicker);

    assertThat(ticker.getCurrencyPair()).isEqualTo(CurrencyPair.GBP_USD);
    assertThat(ticker.getTimestamp().getTime()).isEqualTo(1490388297563L);
    assertThat(ticker.getBid()).isEqualTo("1.24876");
    assertThat(ticker.getAsk()).isEqualTo("1.24886");
    assertThat(ticker.getLow()).isEqualTo("1.24689");
    assertThat(ticker.getHigh()).isEqualTo("1.25266");
  }
}
