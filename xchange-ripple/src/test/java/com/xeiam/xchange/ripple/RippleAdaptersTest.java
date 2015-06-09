package com.xeiam.xchange.ripple;

import static org.fest.assertions.api.Assertions.assertThat;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.Iterator;

import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.Order.OrderType;
import com.xeiam.xchange.dto.account.AccountInfo;
import com.xeiam.xchange.dto.marketdata.OrderBook;
import com.xeiam.xchange.dto.trade.LimitOrder;
import com.xeiam.xchange.dto.trade.Wallet;
import com.xeiam.xchange.ripple.dto.account.RippleAccount;
import com.xeiam.xchange.ripple.dto.marketdata.RippleOrderBook;
import com.xeiam.xchange.ripple.service.polling.params.RippleMarketDataParams;

public class RippleAdaptersTest {

  @Test
  public void testAccountAdapter() throws IOException {
    // Read in the JSON from the example resources
    final InputStream is = RippleAdaptersTest.class.getResourceAsStream("/account/example-account.json");

    // Use Jackson to parse it
    final ObjectMapper mapper = new ObjectMapper();
    final RippleAccount rippleAccount = mapper.readValue(is, RippleAccount.class);

    // Convert to xchange object and check field values
    final AccountInfo account = RippleAdapters.adaptAccountInfo(rippleAccount, "username");
    assertThat(account.getWallets()).hasSize(3);
    assertThat(account.getUsername()).isEqualTo("username");
    assertThat(account.getTradingFee()).isEqualTo(BigDecimal.ZERO);

    final Iterator<Wallet> iterator = account.getWallets().iterator();

    final Wallet wallet1 = iterator.next();
    assertThat(wallet1.getBalance()).isEqualTo("0.038777349225374");
    assertThat(wallet1.getCurrency()).isEqualTo("BTC.rvYAfWj5gh67oV6fW32ZzP3Aw4Eubs59B");

    final Wallet wallet2 = iterator.next();
    assertThat(wallet2.getBalance()).isEqualTo("10");
    assertThat(wallet2.getCurrency()).isEqualTo("USD.rvYAfWj5gh67oV6fW32ZzP3Aw4Eubs59B");

    final Wallet wallet3 = iterator.next();
    assertThat(wallet3.getBalance()).isEqualTo("861.401578");
    assertThat(wallet3.getCurrency()).isEqualTo("XRP");
  }

  @Test
  public void testOrderBookAdapter() throws IOException {
    // Read in the JSON from the example resources
    final InputStream is = RippleAdaptersTest.class.getResourceAsStream("/marketdata/example-order-book.json");
    final CurrencyPair currencyPair = CurrencyPair.XRP_BTC;

    // Test data uses Bitstamp issued BTC
    final RippleMarketDataParams params = new RippleMarketDataParams();
    params.setCounterCounterparty("rvYAfWj5gh67oV6fW32ZzP3Aw4Eubs59B");

    // Use Jackson to parse it
    final ObjectMapper mapper = new ObjectMapper();
    final RippleOrderBook rippleOrderBook = mapper.readValue(is, RippleOrderBook.class);

    // Convert to xchange object and check field values
    final OrderBook orderBook = RippleAdapters.adaptOrderBook(rippleOrderBook, params, currencyPair);
    assertThat(orderBook.getBids()).hasSize(10);
    assertThat(orderBook.getAsks()).hasSize(10);

    final LimitOrder lastBid = orderBook.getBids().get(9);
    assertThat(lastBid.getCurrencyPair()).isEqualTo(currencyPair);
    assertThat(lastBid.getAdditionalData(RippleExchange.DATA_COUNTER_COUNTERPARTY)).isEqualTo("rvYAfWj5gh67oV6fW32ZzP3Aw4Eubs59B");
    assertThat(lastBid.getType()).isEqualTo(OrderType.BID);
    assertThat(lastBid.getId()).isEqualTo("1303704");
    assertThat(lastBid.getTradableAmount()).isEqualTo(new BigDecimal("66314.537782"));
    assertThat(lastBid.getLimitPrice()).isEqualTo(new BigDecimal("0.00003317721777288062"));

    final LimitOrder firstAsk = orderBook.getAsks().get(0);
    assertThat(firstAsk.getCurrencyPair()).isEqualTo(currencyPair);
    assertThat(firstAsk.getAdditionalData(RippleExchange.DATA_COUNTER_COUNTERPARTY)).isEqualTo("rvYAfWj5gh67oV6fW32ZzP3Aw4Eubs59B");
    assertThat(firstAsk.getType()).isEqualTo(OrderType.ASK);
    assertThat(firstAsk.getId()).isEqualTo("1011310");
    assertThat(firstAsk.getTradableAmount()).isEqualTo(new BigDecimal("35447.914936"));
    assertThat(firstAsk.getLimitPrice()).isEqualTo(new BigDecimal("0.00003380846624897726"));
  }
}
