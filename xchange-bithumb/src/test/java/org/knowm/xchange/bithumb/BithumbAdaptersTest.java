package org.knowm.xchange.bithumb;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.knowm.xchange.bithumb.dto.account.BithumbAccount;
import org.knowm.xchange.bithumb.dto.account.BithumbBalance;
import org.knowm.xchange.bithumb.dto.account.BithumbOrder;
import org.knowm.xchange.bithumb.dto.marketdata.BithumbOrderbook;
import org.knowm.xchange.bithumb.dto.marketdata.BithumbTicker;
import org.knowm.xchange.bithumb.dto.marketdata.BithumbTickersReturn;
import org.knowm.xchange.bithumb.dto.marketdata.BithumbTransactionHistory;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trade;
import org.knowm.xchange.dto.trade.LimitOrder;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.TimeZone;

import static org.assertj.core.api.Assertions.assertThat;

public class BithumbAdaptersTest {

    private final ObjectMapper mapper = new ObjectMapper();
    private SimpleDateFormat isoFormat;

    @Before
    public void init() {

        isoFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        isoFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
    }

    @Test
    public void testAdaptOrderBook() throws IOException {

        // given
        InputStream is = BithumbAdaptersTest.class.getResourceAsStream(
                "/org/knowm/xchange/bithumb/dto/marketdata/example-orderbook.json");

        // when
        final BithumbOrderbook bitstampOrderBook = mapper.readValue(is, BithumbOrderbook.class);
        final OrderBook orderBook = BithumbAdapters.adaptOrderBook(bitstampOrderBook);

        // then
        System.out.println(orderBook);
        System.out.println(isoFormat.format(orderBook.getTimeStamp()));
    }

    @Test
    public void testAdaptAccountInfo() throws IOException {

        InputStream isAccount = BithumbAdaptersTest.class.getResourceAsStream(
                "/org/knowm/xchange/bithumb/dto/account/example-account.json");
        InputStream isBalance = BithumbAdaptersTest.class.getResourceAsStream(
                "/org/knowm/xchange/bithumb/dto/account/example-balance.json");

        final BithumbAccount bithumbAccount = mapper.readValue(isAccount, BithumbAccount.class);
        final BithumbBalance bithumbBalance = mapper.readValue(isBalance, BithumbBalance.class);
        final AccountInfo accountInfo = BithumbAdapters.adaptAccountInfo(bithumbAccount, bithumbBalance);

        System.out.println(accountInfo);
    }

    @Test
    public void testAdaptTicker() throws IOException {

        // given
        InputStream is = BithumbAdaptersTest.class.getResourceAsStream(
                "/org/knowm/xchange/bithumb/dto/marketdata/example-ticker.json");

        final BithumbTicker bithumbAccount = mapper.readValue(is, BithumbTicker.class);

        // when
        final Ticker ticker = BithumbAdapters.adaptTicker(bithumbAccount, CurrencyPair.BTC_KRW);

        // then
        System.out.println(ticker);
    }

    @Test
    public void testAdaptTickers() throws IOException {

        // given
        InputStream is = BithumbAdaptersTest.class.getResourceAsStream(
                "/org/knowm/xchange/bithumb/dto/marketdata/all-ticker-data.json");

        final BithumbTickersReturn bithumbTickersReturn =
                mapper.readValue(is, BithumbTickersReturn.class);

        // when
        final List<Ticker> tickers = BithumbAdapters.adaptTickers(bithumbTickersReturn);

        // then
        System.out.println(tickers);
    }

    @Test
    public void testAdaptTransactionHistory() throws IOException {

        // given
        InputStream is = BithumbAdaptersTest.class.getResourceAsStream(
                "/org/knowm/xchange/bithumb/dto/marketdata/example-transaction-history.json");

        final BithumbTransactionHistory transactionHistory =
                mapper.readValue(is, BithumbTransactionHistory.class);

        // when
        final Trade trade = BithumbAdapters.adaptTrade(transactionHistory, CurrencyPair.BTC_KRW);

        // then
        assertThat(trade.getType()).isEqualTo(Order.OrderType.ASK);
        assertThat(trade.getOriginalAmount()).isEqualTo(BigDecimal.valueOf(0.3215));
        assertThat(trade.getCurrencyPair()).isEqualTo(new CurrencyPair(Currency.BTC, Currency.KRW));
        assertThat(trade.getPrice()).isEqualTo(BigDecimal.valueOf(166900));
        assertThat(trade.getTimestamp()).isNotNull();
        assertThat(trade.getId()).isEqualTo("30062545");
    }

    @Test
    public void testAdaptOpenOrder() throws IOException {

        // given
        InputStream is = BithumbAdaptersTest.class.getResourceAsStream(
                "/org/knowm/xchange/bithumb/dto/account/example-order.json");

        final BithumbOrder bithumbOrder = mapper.readValue(is, BithumbOrder.class);

        // when
        final LimitOrder limitOrder = BithumbAdapters.adaptOrder(bithumbOrder);

        // then
        assertThat(limitOrder.getLimitPrice()).isEqualTo(BigDecimal.valueOf(700));
        assertThat(limitOrder.getType()).isEqualTo(Order.OrderType.ASK);
        assertThat(limitOrder.getOriginalAmount()).isEqualTo(BigDecimal.valueOf(1.0));
        assertThat(limitOrder.getCumulativeAmount()).isEqualTo(BigDecimal.valueOf(0.0));
        assertThat(limitOrder.getAveragePrice()).isNull();
        assertThat(limitOrder.getCurrencyPair())
                .isEqualTo(new CurrencyPair(Currency.XRP, Currency.KRW));
        assertThat(limitOrder.getId()).isEqualTo("1546705688665840");
        assertThat(limitOrder.getTimestamp()).isNotNull();
        assertThat(limitOrder.getStatus()).isEqualTo(Order.OrderStatus.NEW);
    }
}
