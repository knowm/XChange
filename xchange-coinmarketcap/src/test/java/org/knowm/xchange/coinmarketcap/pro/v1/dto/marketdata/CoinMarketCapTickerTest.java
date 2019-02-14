package org.knowm.xchange.coinmarketcap.pro.v1.dto.marketdata;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.knowm.xchange.currency.Currency;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import static org.assertj.core.api.Assertions.assertThat;

public class CoinMarketCapTickerTest {

    @Test
    public void testDeserializeTicker() throws IOException, ParseException {
        //given
        InputStream is = CoinMarketCapCurrencyInfo.class.getResourceAsStream(
                "/org/knowm/xchange/coinmarketcap/pro/v1/dto/marketdata/example-ticker.json");

        //when
        ObjectMapper mapper = new ObjectMapper();
        CoinMarketCapTicker ticker = mapper.readValue(is, CoinMarketCapTicker.class);

        //then
        assertThat(ticker.getId()).isEqualTo(1);
        assertThat(ticker.getName()).isEqualTo(Currency.BTC.getDisplayName());
        assertThat(ticker.getSymbol()).isEqualTo(Currency.BTC.getSymbol());
        assertThat(ticker.getSlug()).isEqualTo("bitcoin");
        assertThat(ticker.getCirculatingSupply()).isEqualTo(17519062);
        assertThat(ticker.getTotalSupply()).isEqualTo(17519062);
        assertThat(ticker.getMaxSupply()).isEqualTo(21000000);

        SimpleDateFormat iso8601Format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        iso8601Format.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date dateAdded = iso8601Format.parse("2013-04-28T00:00:00.000Z");

        assertThat(ticker.getDateAdded()).isEqualTo(dateAdded);
        assertThat(ticker.getNumMarketPairs()).isEqualTo(6513);
        assertThat(ticker.getTags().get(0)).isEqualTo("mineable");
        assertThat(ticker.getCmcRank()).isEqualTo(1);

        Date lastUpdated = iso8601Format.parse("2019-02-04T16:34:24.000Z");
        assertThat(ticker.getLastUpdated()).isEqualTo(lastUpdated);

        CoinMarketCapQuote quote = ticker.getQuote().get("USD");
        assertThat(quote.getPrice()).isEqualTo(new BigDecimal("3463.69103385"));
        assertThat(quote.getVolume24h()).isEqualTo(new BigDecimal("5327785294.41072"));
        assertThat(quote.getPercentChange1h()).isEqualTo(new BigDecimal("0.25934"));
        assertThat(quote.getPercentChange24h()).isEqualTo(new BigDecimal("-0.342853"));
        assertThat(quote.getPercentChange7d()).isEqualTo(new BigDecimal("0.070337"));
        assertThat(quote.getMarketCap()).isEqualTo(new BigDecimal("60680617970.86225"));
        assertThat(quote.getLastUpdated()).isEqualTo(lastUpdated);
    }
}