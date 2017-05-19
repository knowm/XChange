package org.knowm.xchange.btcc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import org.junit.Test;
import org.knowm.xchange.btcc.dto.marketdata.BTCCTicker;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.Ticker;
import si.mazi.rescu.RestProxyFactory;

import java.io.InputStream;
import java.math.BigDecimal;
import java.util.Map;

import static org.fest.assertions.api.Assertions.assertThat;

public class MarketDataTest {

    @Test
    public void testTickerAdapter() throws Exception {
        InputStream is = MarketDataTest.class.getResourceAsStream("/ticker.json");

        ObjectMapper mapper = new ObjectMapper();
        TypeFactory t = TypeFactory.defaultInstance();
        Map<String, BTCCTicker> response = mapper.readValue(is, t.constructMapType(Map.class, String.class, BTCCTicker.class));
        BTCCTicker btccTicker = response.get("ticker");

        Ticker ticker = BTCCAdapters.adaptTicker(btccTicker, CurrencyPair.BTC_USD);

        assertThat(ticker.getHigh()).isEqualTo(new BigDecimal("1730"));
        assertThat(ticker.getLow()).isEqualTo(new BigDecimal("1688"));
        assertThat(ticker.getLast()).isEqualTo(new BigDecimal("1725"));
        assertThat(ticker.getVolume()).isEqualTo(new BigDecimal("113.3411"));
        assertThat(ticker.getAsk()).isEqualTo(new BigDecimal("1729"));
        assertThat(ticker.getBid()).isEqualTo(new BigDecimal("1725"));
    }

}
