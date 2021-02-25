package info.bitrich.xchangestream.kraken;

import com.fasterxml.jackson.databind.JsonNode;
import info.bitrich.xchangestream.service.netty.StreamingObjectMapperHelper;
import org.junit.Assert;
import org.junit.Test;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.trade.LimitOrder;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


public class KrakenStreamingAdaptersTest {

    private static CurrencyPair XBT_EUR = new CurrencyPair(Currency.XBT, Currency.EUR);

    @Test
    public void testOrderBookSnapshot() throws IOException {
        JsonNode jsonNode =
                StreamingObjectMapperHelper.getObjectMapper()
                        .readTree(this.getClass().getResource("/orderBookMessageSnapshot.json").openStream());
        OrderBook beforeUpdate = new OrderBook(null, new ArrayList<>(), new ArrayList<>());
        Assert.assertNotNull(jsonNode);
        List list = StreamingObjectMapperHelper.getObjectMapper().treeToValue(jsonNode, List.class);
        OrderBook afterUpdate = KrakenStreamingAdapters.adaptOrderbookMessage(beforeUpdate, XBT_EUR, list);

        assertThat(afterUpdate).isNotNull();
        assertThat(afterUpdate.getAsks()).isNotNull();
        assertThat(afterUpdate.getBids()).isNotNull();

        assertThat(afterUpdate.getAsks()).hasSize(25);
        assertThat(afterUpdate.getBids()).hasSize(25);

        LimitOrder firstAsk = afterUpdate.getAsks().get(0);
        assertThat(firstAsk.getLimitPrice()).isEqualByComparingTo(new BigDecimal("8692"));
        assertThat(firstAsk.getOriginalAmount()).isEqualByComparingTo(new BigDecimal("2.01122372"));

        LimitOrder firstBid = afterUpdate.getBids().get(0);
        assertThat(firstBid.getLimitPrice()).isEqualByComparingTo(new BigDecimal("8691.9"));
        assertThat(firstBid.getOriginalAmount()).isEqualByComparingTo(new BigDecimal("1.45612927"));
    }

    @Test
    public void testOrderBookUpdate() throws IOException {
        JsonNode jsonNode =
                StreamingObjectMapperHelper.getObjectMapper()
                        .readTree(this.getClass().getResource("/orderBookMessageUpdate.json").openStream());
        Assert.assertNotNull(jsonNode);
        OrderBook beforeUpdate = new OrderBook(null, new ArrayList<>(), new ArrayList<>());
        List list = StreamingObjectMapperHelper.getObjectMapper().treeToValue(jsonNode, List.class);
        OrderBook afterUpdate = KrakenStreamingAdapters.adaptOrderbookMessage(beforeUpdate, XBT_EUR, list);

        assertThat(afterUpdate).isNotNull();
        assertThat(afterUpdate.getAsks()).isNotNull();
        assertThat(afterUpdate.getBids()).isNotNull();

        assertThat(afterUpdate.getAsks()).hasSize(1);
        assertThat(afterUpdate.getBids()).hasSize(0);

        LimitOrder firstAsk = afterUpdate.getAsks().get(0);
        assertThat(firstAsk.getLimitPrice()).isEqualByComparingTo(new BigDecimal("9621"));
        assertThat(firstAsk.getOriginalAmount()).isEqualByComparingTo(new BigDecimal("1.36275258"));

    }
}
