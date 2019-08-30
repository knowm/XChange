package info.bitrich.xchangestream.kraken;

import org.junit.Before;
import org.junit.Test;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.kraken.KrakenAdapters;
import org.knowm.xchange.kraken.dto.marketdata.KrakenDepth;
import org.knowm.xchange.kraken.dto.marketdata.KrakenPublicOrder;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class KrakenOrderbookVerifyTest {

    List<KrakenPublicOrder> asks = new ArrayList<>();
    List<KrakenPublicOrder> bids = new ArrayList<>();

    @Before
    public void setUp(){
        asks = new ArrayList<>();
        bids = new ArrayList<>();
    }

    @Test
    public void verifyOrderbookWhenAsksHaveNotBeenUpdated() {

        asks.add(new KrakenPublicOrder(BigDecimal.valueOf(9360),BigDecimal.ONE, Date.from(Instant.now()).getTime()));
        asks.add(new KrakenPublicOrder(BigDecimal.valueOf(9350),BigDecimal.ONE, Date.from(Instant.now()).getTime()));
        asks.add(new KrakenPublicOrder(BigDecimal.valueOf(9332),BigDecimal.ONE, Date.from(Instant.now().minusSeconds(1)).getTime()));
        asks.add(new KrakenPublicOrder(BigDecimal.valueOf(9330),BigDecimal.ONE, Date.from(Instant.now().minusSeconds(1)).getTime()));

        bids.add(new KrakenPublicOrder(BigDecimal.valueOf(9341),BigDecimal.ONE, Date.from(Instant.now()).getTime()));
        bids.add(new KrakenPublicOrder(BigDecimal.valueOf(9340),BigDecimal.ONE, Date.from(Instant.now()).getTime()));
        bids.add(new KrakenPublicOrder(BigDecimal.valueOf(9250),BigDecimal.ONE, Date.from(Instant.now()).getTime()));
        bids.add(new KrakenPublicOrder(BigDecimal.valueOf(9240),BigDecimal.ONE, Date.from(Instant.now()).getTime()));

        OrderBook orderBook = KrakenOrderBookUtils.verifyKrakenOrderBook(KrakenAdapters.adaptOrderBook(new KrakenDepth(asks,bids),CurrencyPair.BTC_EUR));

        assertThat(orderBook.getAsks().size()).isEqualTo(2);
        assertThat(orderBook.getBids().size()).isEqualTo(4);

        assertThat(orderBook.getAsks().get(0).getLimitPrice()).isEqualTo(BigDecimal.valueOf(9350));
        assertThat(orderBook.getAsks().get(1).getLimitPrice()).isEqualTo(BigDecimal.valueOf(9360));

        assertThat(orderBook.getBids().get(0).getLimitPrice()).isEqualTo(BigDecimal.valueOf(9341));
        assertThat(orderBook.getBids().get(1).getLimitPrice()).isEqualTo(BigDecimal.valueOf(9340));
        assertThat(orderBook.getBids().get(2).getLimitPrice()).isEqualTo(BigDecimal.valueOf(9250));
        assertThat(orderBook.getBids().get(3).getLimitPrice()).isEqualTo(BigDecimal.valueOf(9240));
    }

    @Test
    public void verifyOrderbookWhenBidsHaveNotBeenUpdated() {

        asks.add(new KrakenPublicOrder(BigDecimal.valueOf(9360),BigDecimal.ONE, Date.from(Instant.now()).getTime()));
        asks.add(new KrakenPublicOrder(BigDecimal.valueOf(9350),BigDecimal.ONE, Date.from(Instant.now()).getTime()));
        asks.add(new KrakenPublicOrder(BigDecimal.valueOf(9332),BigDecimal.ONE, Date.from(Instant.now()).getTime()));
        asks.add(new KrakenPublicOrder(BigDecimal.valueOf(9330),BigDecimal.ONE, Date.from(Instant.now()).getTime()));

        bids.add(new KrakenPublicOrder(BigDecimal.valueOf(9341),BigDecimal.ONE, Date.from(Instant.now().minusSeconds(1)).getTime()));
        bids.add(new KrakenPublicOrder(BigDecimal.valueOf(9340),BigDecimal.ONE, Date.from(Instant.now().minusSeconds(1)).getTime()));
        bids.add(new KrakenPublicOrder(BigDecimal.valueOf(9250),BigDecimal.ONE, Date.from(Instant.now()).getTime()));
        bids.add(new KrakenPublicOrder(BigDecimal.valueOf(9240),BigDecimal.ONE, Date.from(Instant.now()).getTime()));

        OrderBook orderBook = KrakenOrderBookUtils.verifyKrakenOrderBook(KrakenAdapters.adaptOrderBook(new KrakenDepth(asks,bids),CurrencyPair.BTC_EUR));

        assertThat(orderBook.getAsks().size()).isEqualTo(4);
        assertThat(orderBook.getBids().size()).isEqualTo(2);

        assertThat(orderBook.getAsks().get(0).getLimitPrice()).isEqualTo(BigDecimal.valueOf(9330));
        assertThat(orderBook.getAsks().get(1).getLimitPrice()).isEqualTo(BigDecimal.valueOf(9332));
        assertThat(orderBook.getAsks().get(2).getLimitPrice()).isEqualTo(BigDecimal.valueOf(9350));
        assertThat(orderBook.getAsks().get(3).getLimitPrice()).isEqualTo(BigDecimal.valueOf(9360));

        assertThat(orderBook.getBids().get(0).getLimitPrice()).isEqualTo(BigDecimal.valueOf(9250));
        assertThat(orderBook.getBids().get(1).getLimitPrice()).isEqualTo(BigDecimal.valueOf(9240));
    }
}
