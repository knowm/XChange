package org.knowm.xchange.bithumb;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.TimeZone;
import org.junit.Before;
import org.junit.Test;
import org.knowm.xchange.bithumb.dto.BithumbResponse;
import org.knowm.xchange.bithumb.dto.account.BithumbAccount;
import org.knowm.xchange.bithumb.dto.account.BithumbBalance;
import org.knowm.xchange.bithumb.dto.account.BithumbOrder;
import org.knowm.xchange.bithumb.dto.marketdata.BithumbOrderbook;
import org.knowm.xchange.bithumb.dto.marketdata.BithumbTicker;
import org.knowm.xchange.bithumb.dto.marketdata.BithumbTickersReturn;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.dto.account.Balance;
import org.knowm.xchange.dto.account.Wallet;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.trade.LimitOrder;

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
    InputStream is =
        BithumbAdaptersTest.class.getResourceAsStream(
            "/org/knowm/xchange/bithumb/dto/marketdata/example-orderbook.json");

    // when
    final BithumbOrderbook bitstampOrderBook = mapper.readValue(is, BithumbOrderbook.class);
    final OrderBook orderBook = BithumbAdapters.adaptOrderBook(bitstampOrderBook);

    // then
    assertThat(orderBook.getAsks()).hasSize(2);
    assertThat(orderBook.getBids()).hasSize(2);

    final LimitOrder ask = orderBook.getAsks().get(0);
    final LimitOrder bid = orderBook.getBids().get(0);

    assertThat(ask.getLimitPrice()).isEqualTo(new BigDecimal(168500L));
    assertThat(ask.getOriginalAmount()).isEqualTo(new BigDecimal("49.5577"));
    assertThat(bid.getLimitPrice()).isEqualTo(new BigDecimal(168400L));
    assertThat(bid.getOriginalAmount()).isEqualTo(new BigDecimal("28.0241"));
  }

  @Test
  public void testAdaptAccountInfo() throws IOException {

    // Given
    InputStream isAccount =
        BithumbAdaptersTest.class.getResourceAsStream(
            "/org/knowm/xchange/bithumb/dto/account/example-account.json");
    InputStream isBalance =
        BithumbAdaptersTest.class.getResourceAsStream(
            "/org/knowm/xchange/bithumb/dto/account/example-balance.json");

    // When
    final BithumbAccount bithumbAccount = mapper.readValue(isAccount, BithumbAccount.class);
    final BithumbBalance bithumbBalance = mapper.readValue(isBalance, BithumbBalance.class);
    final AccountInfo accountInfo =
        BithumbAdapters.adaptAccountInfo(bithumbAccount, bithumbBalance);

    // Then
    final Wallet wallet = accountInfo.getWallet();
    final Balance balance = wallet.getBalance(Currency.BTC);

    assertThat(accountInfo.getTradingFee()).isEqualTo(new BigDecimal("0.0015"));
    assertThat(wallet.getBalances()).containsKeys(Currency.BTC, Currency.ETH, Currency.XRP);
    assertThat(balance.getTotal()).isEqualTo(new BigDecimal("0.00001971"));
    assertThat(balance.getAvailable()).isEqualTo(new BigDecimal("0.00001971"));
  }

  @Test
  public void testAdaptTicker() throws IOException {

    // given
    InputStream is =
        BithumbAdaptersTest.class.getResourceAsStream(
            "/org/knowm/xchange/bithumb/dto/marketdata/example-ticker.json");

    final BithumbTicker bithumbAccount = mapper.readValue(is, BithumbTicker.class);

    // when
    final Ticker ticker = BithumbAdapters.adaptTicker(bithumbAccount, CurrencyPair.BTC_KRW);

    // then
    assertThat(ticker.getOpen()).isEqualTo(new BigDecimal("151300"));
    assertThat(ticker.getLast()).isEqualTo(new BigDecimal("168900"));
    assertThat(ticker.getBid()).isEqualTo(new BigDecimal("168800"));
    assertThat(ticker.getAsk()).isEqualTo(new BigDecimal("168900"));
    assertThat(ticker.getHigh()).isEqualTo(new BigDecimal("171600"));
    assertThat(ticker.getLow()).isEqualTo(new BigDecimal("148600"));
    assertThat(ticker.getVwap()).isEqualTo(new BigDecimal("161373.9643"));
    assertThat(ticker.getVolume()).isEqualTo(new BigDecimal("294028.02849871"));
    assertThat(ticker.getTimestamp().getTime()).isEqualTo(1546440237614L);
  }

  @Test
  public void testAdaptTickers() throws IOException {

    // given
    InputStream is =
        BithumbAdaptersTest.class.getResourceAsStream(
            "/org/knowm/xchange/bithumb/dto/marketdata/all-ticker-data.json");

    final BithumbTickersReturn bithumbTickersReturn =
        mapper.readValue(is, BithumbTickersReturn.class);

    // when
    final List<Ticker> tickers = BithumbAdapters.adaptTickers(bithumbTickersReturn);

    // then
    assertThat(tickers).hasSize(3);
    final Ticker ticker = tickers.get(0);
    assertThat(ticker.getOpen()).isEqualTo(new BigDecimal("4185000"));
    assertThat(ticker.getLast()).isEqualTo(new BigDecimal("4297000"));
    assertThat(ticker.getBid()).isEqualTo(new BigDecimal("4296000"));
    assertThat(ticker.getAsk()).isEqualTo(new BigDecimal("4297000"));
    assertThat(ticker.getHigh()).isEqualTo(new BigDecimal("4328000"));
    assertThat(ticker.getLow()).isEqualTo(new BigDecimal("4137000"));
    assertThat(ticker.getVwap()).isEqualTo(new BigDecimal("4252435.9159"));
    assertThat(ticker.getVolume()).isEqualTo(new BigDecimal("3815.4174696"));
    assertThat(ticker.getTimestamp().getTime()).isEqualTo(1546440191110L);
  }

  @Test
  public void testAdaptOpenOrder() throws IOException {

    // given
    InputStream is =
        BithumbAdaptersTest.class.getResourceAsStream(
            "/org/knowm/xchange/bithumb/dto/account/example-order.json");

    final BithumbResponse<List<BithumbOrder>> bithumbOrderResponse =
        mapper.readValue(is, new TypeReference<BithumbResponse<List<BithumbOrder>>>() {});

    // when
    final LimitOrder limitOrder =
        BithumbAdapters.adaptOrder(bithumbOrderResponse.getData().stream().findFirst().get());

    // then
    assertThat(limitOrder.getLimitPrice()).isEqualTo(BigDecimal.valueOf(501000));
    assertThat(limitOrder.getType()).isEqualTo(Order.OrderType.BID);
    assertThat(limitOrder.getOriginalAmount()).isEqualTo(BigDecimal.valueOf(5.0));
    assertThat(limitOrder.getCumulativeAmount()).isEqualTo(BigDecimal.valueOf(0.0));
    assertThat(limitOrder.getAveragePrice()).isNull();
    assertThat(limitOrder.getCurrencyPair())
        .isEqualTo(new CurrencyPair(Currency.BTC, Currency.KRW));
    assertThat(limitOrder.getId()).isEqualTo("C0101000007408440032");
    assertThat(limitOrder.getTimestamp()).isNotNull();
    assertThat(limitOrder.getStatus()).isEqualTo(Order.OrderStatus.NEW);
  }
}
