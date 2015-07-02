package com.xeiam.xchange.ripple;

import static org.fest.assertions.api.Assertions.assertThat;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.Iterator;

import org.junit.Test;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.Order.OrderType;
import com.xeiam.xchange.dto.account.AccountInfo;
import com.xeiam.xchange.dto.marketdata.OrderBook;
import com.xeiam.xchange.dto.trade.LimitOrder;
import com.xeiam.xchange.dto.trade.OpenOrders;
import com.xeiam.xchange.dto.trade.Wallet;
import com.xeiam.xchange.ripple.dto.account.RippleAccount;
import com.xeiam.xchange.ripple.dto.marketdata.RippleOrderBook;
import com.xeiam.xchange.ripple.dto.trade.RippleAccountOrders;
import com.xeiam.xchange.ripple.dto.trade.RippleLimitOrder;
import com.xeiam.xchange.ripple.service.polling.params.RippleMarketDataParams;

public class RippleAdaptersTest {

  @Test
  public void addaptAccountInfoTest() throws IOException {
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
  public void adaptOrderBookTest() throws IOException {
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
    assertThat(lastBid).isInstanceOf(RippleLimitOrder.class);
    assertThat(lastBid.getCurrencyPair()).isEqualTo(currencyPair);
    assertThat(((RippleLimitOrder) lastBid).getCounterCounterparty()).isEqualTo("rvYAfWj5gh67oV6fW32ZzP3Aw4Eubs59B");
    assertThat(lastBid.getType()).isEqualTo(OrderType.BID);
    assertThat(lastBid.getId()).isEqualTo("1303704");
    assertThat(lastBid.getTradableAmount()).isEqualTo("66314.537782");
    assertThat(lastBid.getLimitPrice()).isEqualTo("0.00003317721777288062");

    final LimitOrder firstAsk = orderBook.getAsks().get(0);
    assertThat(firstAsk).isInstanceOf(RippleLimitOrder.class);
    assertThat(firstAsk.getCurrencyPair()).isEqualTo(currencyPair);
    assertThat(((RippleLimitOrder) firstAsk).getCounterCounterparty()).isEqualTo("rvYAfWj5gh67oV6fW32ZzP3Aw4Eubs59B");
    assertThat(firstAsk.getType()).isEqualTo(OrderType.ASK);
    assertThat(firstAsk.getId()).isEqualTo("1011310");
    assertThat(firstAsk.getTradableAmount()).isEqualTo("35447.914936");
    assertThat(firstAsk.getLimitPrice()).isEqualTo("0.00003380846624897726");
  }

  @Test
  public void adaptOpenOrdersTest() throws JsonParseException, JsonMappingException, IOException {

    final RippleExchange exchange = new RippleExchange();
    final Integer scale = (Integer) exchange.getDefaultExchangeSpecification().getExchangeSpecificParametersItem(RippleExchange.ROUNDING_SCALE);

    // Read in the JSON from the example resources
    final InputStream is = RippleAccount.class.getResourceAsStream("/trade/example-account-orders.json");
    final ObjectMapper mapper = new ObjectMapper();
    final RippleAccountOrders response = mapper.readValue(is, RippleAccountOrders.class);

    // Convert to XChange orders
    final OpenOrders orders = RippleAdapters.adaptOpenOrders(response, scale);
    assertThat(orders.getOpenOrders()).hasSize(12);

    final LimitOrder firstOrder = orders.getOpenOrders().get(0);
    assertThat(firstOrder).isInstanceOf(RippleLimitOrder.class);
    assertThat(firstOrder.getCurrencyPair()).isEqualTo(CurrencyPair.XRP_BTC);
    assertThat(((RippleLimitOrder) firstOrder).getCounterCounterparty()).isEqualTo("rvYAfWj5gh67oV6fW32ZzP3Aw4Eubs59B");
    assertThat(firstOrder.getId()).isEqualTo("5");
    assertThat(firstOrder.getLimitPrice()).isEqualTo("0.00003226");
    assertThat(firstOrder.getTimestamp()).isNull();
    assertThat(firstOrder.getTradableAmount()).isEqualTo("1");
    assertThat(firstOrder.getType()).isEqualTo(OrderType.BID);

    final LimitOrder secondOrder = orders.getOpenOrders().get(1);
    assertThat(secondOrder).isInstanceOf(RippleLimitOrder.class);
    assertThat(secondOrder.getCurrencyPair()).isEqualTo(CurrencyPair.XRP_BTC);
    assertThat(((RippleLimitOrder) secondOrder).getCounterCounterparty()).isEqualTo("rvYAfWj5gh67oV6fW32ZzP3Aw4Eubs59B");
    assertThat(secondOrder.getId()).isEqualTo("7");
    assertThat(secondOrder.getLimitPrice()).isEqualTo("123.12345678");
    assertThat(secondOrder.getTimestamp()).isNull();
    assertThat(secondOrder.getTradableAmount()).isEqualTo("123.123456");
    assertThat(secondOrder.getType()).isEqualTo(OrderType.ASK);
  }
}
