package org.knowm.xchange.truefx.dto.marketdata;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import org.junit.Test;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.truefx.TrueFxExchange;
import org.knowm.xchange.truefx.service.TrueFxMarketDataServiceRaw;

public class TrueFxTickerTest {

  @Test
  public void unmarshalTest1() throws IOException {
    Exchange exchange = ExchangeFactory.INSTANCE.createExchange(TrueFxExchange.class.getName());
    TrueFxMarketDataServiceRaw rawService =
        (TrueFxMarketDataServiceRaw) exchange.getMarketDataService();
    ObjectMapper mapper = rawService.createObjectMapper();

    InputStream is = getClass().getResourceAsStream("/marketdata/example-ticker.csv");
    TrueFxTicker ticker = mapper.readValue(is, TrueFxTicker.class);
    check(ticker);
  }

  @Test
  public void unmarshalTest2() throws IOException {
    CsvMapper mapper = new CsvMapper();
    CsvSchema schema = mapper.schemaFor(TrueFxTicker.class);

    InputStream is = getClass().getResourceAsStream("/marketdata/example-ticker.csv");
    List<Object> tickers =
        mapper.readerFor(TrueFxTicker.class).with(schema).readValues(is).readAll();
    assertThat(tickers).hasSize(1);

    TrueFxTicker ticker = (TrueFxTicker) tickers.get(0);
    check(ticker);
  }

  private void check(TrueFxTicker ticker) {
    assertThat(ticker.getPair()).isEqualTo("GBP/USD");
    assertThat(ticker.getTimestamp()).isEqualTo(1490388297563L);

    assertThat(ticker.getBid()).isEqualTo("1.24");
    assertThat(ticker.getBidBP()).isEqualTo("876");
    assertThat(ticker.calcBid()).isEqualTo("1.24876");
    assertThat(ticker.getAsk()).isEqualTo("1.24");
    assertThat(ticker.getAskBP()).isEqualTo("886");
    assertThat(ticker.calcAsk()).isEqualTo("1.24886");
    assertThat(ticker.getLow()).isEqualTo("1.24689");
    assertThat(ticker.getHigh()).isEqualTo("1.25266");
    assertThat(ticker.getOpen()).isEqualTo("1.25207");
  }
}
