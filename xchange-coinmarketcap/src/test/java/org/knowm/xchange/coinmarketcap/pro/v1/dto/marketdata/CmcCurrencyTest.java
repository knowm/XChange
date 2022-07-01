package org.knowm.xchange.coinmarketcap.pro.v1.dto.marketdata;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import org.junit.Test;
import org.knowm.xchange.currency.Currency;

public class CmcCurrencyTest {

  @Test
  public void testDeserializeCurrencyMap() throws Exception {
    // given
    InputStream is =
        CmcCurrency.class.getResourceAsStream(
            "/org/knowm/xchange/coinmarketcap/pro/v1/dto/marketdata/example-currency.json");

    // when
    ObjectMapper mapper = new ObjectMapper();
    CmcCurrency currencyMap = mapper.readValue(is, CmcCurrency.class);

    // then
    assertThat(currencyMap).isNotNull();

    assertThat(currencyMap.getId()).isEqualTo(1);
    assertThat(currencyMap.getName()).isEqualTo(Currency.BTC.getDisplayName());
    assertThat(currencyMap.getSymbol()).isEqualTo(Currency.BTC.getSymbol());
    assertThat(currencyMap.getSlug()).isEqualTo("bitcoin");
    assertThat(currencyMap.getRank()).isEqualTo(1);
    assertThat(currencyMap.isActive()).isTrue();

    SimpleDateFormat iso8601Format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
    iso8601Format.setTimeZone(TimeZone.getTimeZone("UTC"));
    Date firstHistoricalData = iso8601Format.parse("2013-04-28T18:47:21.000Z");
    Date lastHistoricalData = iso8601Format.parse("2021-08-28T16:49:13.000Z");

    assertThat(currencyMap.getFirstHistoricalData()).isEqualTo(firstHistoricalData);
    assertThat(currencyMap.getLastHistoricalData()).isEqualTo(lastHistoricalData);
    assertThat(currencyMap.getPlatform()).isNull();
  }
}
