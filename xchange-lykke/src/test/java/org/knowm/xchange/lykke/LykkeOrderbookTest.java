package org.knowm.xchange.lykke;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.lykke.dto.marketdata.LykkeOrderBook;

import java.io.IOException;
import java.io.InputStream;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class LykkeOrderbookTest {

    private ObjectMapper mapper = new ObjectMapper();

    @Test
    public void orderbookUnmarshalTest() throws IOException {
        InputStream is =
                LykkeAssetsTest.class.getResourceAsStream(
                        "/org/knowm/xchange/lykke/example-lykkeOrderbook.json");
        LykkeOrderBook[] lykkeOrderBooks = mapper.readValue(is, LykkeOrderBook[].class);

        assertThat(lykkeOrderBooks[0].getAssetPair()).isEqualTo("AEBTC");
        assertThat(lykkeOrderBooks[0].isBuy()).isEqualTo(false);
        assertThat(lykkeOrderBooks[0].getTimestamp()).isEqualTo("2018-07-18T22:55:11.283+00:00");
        assertThat(lykkeOrderBooks[0].getPrices().get(0).getVolume()).isEqualTo(-2.5);
        assertThat(lykkeOrderBooks[0].getPrices().get(0).getPrice()).isEqualTo(0.000349);

    }

    @Test
    public void test() throws IOException {
        Exchange exchange = LykkeKeys.getExchange();
        OrderBook orderBook = exchange.getMarketDataService().getOrderBook(CurrencyPair.ETH_BTC);
        System.out.println(orderBook.getBids().get(0));
        System.out.println(orderBook.getAsks().get(0));
    }
}
