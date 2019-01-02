package org.knowm.xchange.bithumb.dto.marketdata;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class BithumbMarketDataTest {

    private ObjectMapper mapper = new ObjectMapper();

    @Test
    public void testUnmarshallTicker() throws IOException {

        final InputStream is =
                BithumbMarketDataTest.class.getResourceAsStream(
                        "/org/knowm/xchange/bithumb/dto/marketdata/example-ticker.json");

        final BithumbTicker bithumbTicker = mapper.readValue(is, BithumbTicker.class);

        System.out.println(bithumbTicker);
        assertThat(bithumbTicker.getOpeningPrice()).isEqualTo("151300");
        assertThat(bithumbTicker.getClosingPrice()).isEqualTo("168900");
        assertThat(bithumbTicker.getMinPrice()).isEqualTo("148600");
        assertThat(bithumbTicker.getMaxPrice()).isEqualTo("171600");
        assertThat(bithumbTicker.getAveragePrice()).isEqualTo("161373.9643");
        assertThat(bithumbTicker.getUnitsTraded()).isEqualTo("294028.02849871");
        assertThat(bithumbTicker.getVolume1day()).isEqualTo("294028.02849871");
        assertThat(bithumbTicker.getVolume7day()).isEqualTo("1276650.256763659925784183");
        assertThat(bithumbTicker.getBuyPrice()).isEqualTo("168800");
        assertThat(bithumbTicker.getSellPrice()).isEqualTo("168900");
        assertThat(bithumbTicker.get_24HFluctate()).isEqualTo("17600");
        assertThat(bithumbTicker.get_24HFluctateRate()).isEqualTo("11.63");
        assertThat(bithumbTicker.getDate()).isEqualTo(1546440237614L);
    }

    @Test
    public void testUnmarshallOrderbook() throws IOException {

        final InputStream is =
                BithumbMarketDataTest.class.getResourceAsStream(
                        "/org/knowm/xchange/bithumb/dto/marketdata/example-orderbook.json");

        final BithumbOrderbook bithumbOrderbook = mapper.readValue(is, BithumbOrderbook.class);

        assertThat(bithumbOrderbook.getPaymentCurrency()).isEqualTo("KRW");
        assertThat(bithumbOrderbook.getOrderCurrency()).isEqualTo("ETH");

        final List<BithumbOrderbookEntry> bids = bithumbOrderbook.getBids();
        final List<BithumbOrderbookEntry> asks = bithumbOrderbook.getAsks();

        assertThat(bids.size()).isEqualTo(2);
        assertThat(bids.get(0).getQuantity()).isEqualTo("28.0241");
        assertThat(bids.get(0).getPrice()).isEqualTo("168400");

        assertThat(asks.size()).isEqualTo(2);
        assertThat(asks.get(0).getQuantity()).isEqualTo("49.5577");
        assertThat(asks.get(0).getPrice()).isEqualTo("168500");
    }

    @Test
    public void testUnmarshallTransactionHistory() throws IOException {

        final InputStream is =
                BithumbMarketDataTest.class.getResourceAsStream(
                        "/org/knowm/xchange/bithumb/dto/marketdata/example-transaction-history.json");

        final BithumbTransactionHistory bithumbOrderbook = mapper.readValue(is, BithumbTransactionHistory.class);

        assertThat(bithumbOrderbook.getContNo()).isEqualTo(30062545L);
        assertThat(bithumbOrderbook.getTransactionDate()).isEqualTo("2019-01-03 00:54:08");
        assertThat(bithumbOrderbook.getType()).isEqualTo("ask");
        assertThat(bithumbOrderbook.getUnitsTraded()).isEqualTo("0.3215");
        assertThat(bithumbOrderbook.getPrice()).isEqualTo("166900");
        assertThat(bithumbOrderbook.getTotal()).isEqualTo("53658");
    }
}
