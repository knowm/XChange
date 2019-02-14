package org.knowm.xchange.coinmarketcap.pro.v1.dto.marketdata;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.knowm.xchange.currency.Currency;

import java.io.IOException;
import java.io.InputStream;

import static org.assertj.core.api.Assertions.assertThat;

public class CoinMarketCapCurrencyInfoTest {

    @Test
    public void testDeserializeCurrencyInfo() throws IOException {
        //given
        InputStream is = CoinMarketCapCurrencyInfo.class.getResourceAsStream(
                "/org/knowm/xchange/coinmarketcap/pro/v1/dto/marketdata/example-currency-info.json");

        //when
        ObjectMapper mapper = new ObjectMapper();
        CoinMarketCapCurrencyInfo currencyInfo = mapper.readValue(is, CoinMarketCapCurrencyInfo.class);

        //then
        assertThat(currencyInfo.getSymbol()).isEqualTo(Currency.BTC.getSymbol());
        assertThat(currencyInfo.getId()).isEqualTo(1);
        assertThat(currencyInfo.getUrls().getWebsite().get(0)).isEqualTo("https://bitcoin.org/");
    }
}