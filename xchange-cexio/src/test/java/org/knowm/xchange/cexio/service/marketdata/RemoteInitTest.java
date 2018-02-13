package org.knowm.xchange.cexio.service.marketdata;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.cexio.CexIOExchange;
import org.knowm.xchange.cexio.dto.marketdata.CexIOCurrencyLimits;
import org.knowm.xchange.cexio.service.CexIOMarketDataService;

import java.io.IOException;
import java.io.InputStream;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author ujjwal on 13/02/18.
 */
public class RemoteInitTest {
  @Test
  public void unitJsonMapperTest() throws IOException {
    InputStream is = getClass().getResourceAsStream("/marketdata/sample_currency_limits.json");
    ObjectMapper mapper = new ObjectMapper();
    final CexIOCurrencyLimits cexIOCurrencyLimits = mapper.readValue(is, CexIOCurrencyLimits.class);
    assertThat(cexIOCurrencyLimits).isNotNull();
    assertThat(cexIOCurrencyLimits.getData().getPairs().size()).isBetween(1, 30);
    assertThat(cexIOCurrencyLimits.getData().getPairs()).allSatisfy(pair -> {
      assertThat(pair.getSymbol1()).isNotNull();
      assertThat(pair.getSymbol2()).isNotNull();
      assertThat(pair.getMinLotSize()).isPositive();
      // we don't care about the other parameters for now.
    });

  }

  @Test
  public void integrationTest() throws IOException {
    final Exchange exchange = ExchangeFactory.INSTANCE.createExchange(CexIOExchange.class);

    assertThat(exchange).isNotNull();
    assertThat(exchange.getExchangeMetaData().getCurrencyPairs()).isNotEmpty();

    final CexIOCurrencyLimits currencyLimits = ((CexIOMarketDataService) exchange.getMarketDataService()).getCurrencyLimits();
    assertThat(currencyLimits.getE()).isEqualToIgnoringCase("currency_limits");
    assertThat(currencyLimits.getError()).isNullOrEmpty();
    assertThat(currencyLimits.getOk()).isEqualToIgnoringCase("ok");

  }
}
