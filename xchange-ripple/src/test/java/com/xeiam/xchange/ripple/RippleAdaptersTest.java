package com.xeiam.xchange.ripple;

import static org.fest.assertions.api.Assertions.assertThat;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.util.Iterator;

import org.junit.Test;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xeiam.xchange.currency.Currencies;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.Order.OrderType;
import com.xeiam.xchange.dto.account.AccountInfo;
import com.xeiam.xchange.dto.marketdata.OrderBook;
import com.xeiam.xchange.dto.trade.LimitOrder;
import com.xeiam.xchange.dto.trade.OpenOrders;
import com.xeiam.xchange.dto.trade.UserTrade;
import com.xeiam.xchange.dto.trade.Wallet;
import com.xeiam.xchange.ripple.dto.account.ITransferFeeSource;
import com.xeiam.xchange.ripple.dto.account.RippleAccountBalances;
import com.xeiam.xchange.ripple.dto.account.RippleAccountSettings;
import com.xeiam.xchange.ripple.dto.marketdata.RippleOrderBook;
import com.xeiam.xchange.ripple.dto.trade.RippleAccountOrders;
import com.xeiam.xchange.ripple.dto.trade.RippleLimitOrder;
import com.xeiam.xchange.ripple.dto.trade.RippleOrderDetails;
import com.xeiam.xchange.ripple.dto.trade.RippleUserTrade;
import com.xeiam.xchange.ripple.service.polling.params.RippleMarketDataParams;
import com.xeiam.xchange.ripple.service.polling.params.RippleTradeHistoryParams;
import com.xeiam.xchange.service.polling.trade.params.TradeHistoryParams;

public class RippleAdaptersTest implements ITransferFeeSource {

  @Test
  public void adaptAccountInfoTest() throws IOException {
    // Read in the JSON from the example resources
    final InputStream is = getClass().getResourceAsStream("/account/example-account-balances.json");

    // Use Jackson to parse it
    final ObjectMapper mapper = new ObjectMapper();
    final RippleAccountBalances rippleAccount = mapper.readValue(is, RippleAccountBalances.class);

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
    final InputStream is = getClass().getResourceAsStream("/marketdata/example-order-book.json");
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
    final int roundingScale = exchange.getRoundingScale();

    // Read in the JSON from the example resources
    final InputStream is = getClass().getResourceAsStream("/trade/example-account-orders.json");
    final ObjectMapper mapper = new ObjectMapper();
    final RippleAccountOrders response = mapper.readValue(is, RippleAccountOrders.class);

    // Convert to XChange orders
    final OpenOrders orders = RippleAdapters.adaptOpenOrders(response, roundingScale);
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
    // Price = 15159.38551342023 / 123.123456
    assertThat(secondOrder.getLimitPrice()).isEqualTo("123.12345677999998635515884154518859509596611713043533");
    assertThat(secondOrder.getTimestamp()).isNull();
    assertThat(secondOrder.getTradableAmount()).isEqualTo("123.123456");
    assertThat(secondOrder.getType()).isEqualTo(OrderType.ASK);
  }

  @Override
  public BigDecimal getTransferFeeRate(final String address) throws IOException {
    final InputStream is = getClass().getResourceAsStream(String.format("/account/example-account-settings-%s.json", address));
    final ObjectMapper mapper = new ObjectMapper();
    return mapper.readValue(is, RippleAccountSettings.class).getSettings().getTransferFeeRate();
  }

  @Test
  public void adaptTrade_BuyXRP_SellBTC() throws JsonParseException, JsonMappingException, IOException, ParseException {
    final RippleExchange exchange = new RippleExchange();
    final int roundingScale = exchange.getRoundingScale();

    // Read the trade JSON from the example resources
    final InputStream is = getClass().getResourceAsStream("/trade/example-trade-buyXRP-sellBTC.json");
    final ObjectMapper mapper = new ObjectMapper();
    final RippleOrderDetails response = mapper.readValue(is, RippleOrderDetails.class);

    final RippleTradeHistoryParams params = new RippleTradeHistoryParams();
    params.addPreferredCounterCurrency(Currencies.BTC);

    final UserTrade trade = RippleAdapters.adaptTrade(response, params, this, roundingScale);
    assertThat(trade.getCurrencyPair()).isEqualTo(CurrencyPair.XRP_BTC);
    assertThat(trade.getFeeAmount()).isEqualTo("0.012");
    assertThat(trade.getFeeCurrency()).isEqualTo(Currencies.XRP);
    assertThat(trade.getId()).isEqualTo("0000000000000000000000000000000000000000000000000000000000000000");
    assertThat(trade.getOrderId()).isEqualTo("0");
    // Price = 0.000029309526038 * 0.998
    assertThat(trade.getPrice()).isEqualTo(new BigDecimal("0.000029250906985924").setScale(roundingScale, RoundingMode.HALF_UP).stripTrailingZeros());
    assertThat(trade.getTimestamp()).isEqualTo(RippleExchange.ToDate("2000-00-00T00:00:00.000Z"));
    assertThat(trade.getTradableAmount()).isEqualTo("1");
    assertThat(trade.getType()).isEqualTo(OrderType.BID);

    assertThat(trade).isInstanceOf(RippleUserTrade.class);
    final RippleUserTrade ripple = (RippleUserTrade) trade;

    assertThat(ripple.getBaseCounterparty()).isEmpty();
    assertThat(ripple.getBaseTransferFee()).isZero();
    assertThat(ripple.getBaseTransferFeeCurrency()).isEqualTo("XRP");
    assertThat(ripple.getBaseTransferFeeCurrency()).isEqualTo(trade.getCurrencyPair().baseSymbol);

    assertThat(ripple.getCounterCounterparty()).isEqualTo("rMwjYedjc7qqtKYVLiAccJSmCwih4LnE2q");
    // Transfer fee = 0.000029309526038 * 0.002
    assertThat(ripple.getCounterTransferFee()).isEqualTo("0.000000058619052076");
    assertThat(ripple.getCounterTransferFeeCurrency()).isEqualTo("BTC");
    assertThat(ripple.getCounterTransferFeeCurrency()).isEqualTo(trade.getCurrencyPair().counterSymbol);
  }

  @Test
  public void adaptTrade_SellBTC_BuyXRP() throws JsonParseException, JsonMappingException, IOException, ParseException {
    final RippleExchange exchange = new RippleExchange();
    final int roundingScale = exchange.getRoundingScale();

    // Read the trade JSON from the example resources
    final InputStream is = getClass().getResourceAsStream("/trade/example-trade-buyXRP-sellBTC.json");
    final ObjectMapper mapper = new ObjectMapper();
    final RippleOrderDetails response = mapper.readValue(is, RippleOrderDetails.class);

    final RippleTradeHistoryParams params = new RippleTradeHistoryParams();
    params.addPreferredBaseCurrency(Currencies.BTC);

    final UserTrade trade = RippleAdapters.adaptTrade(response, params, this, roundingScale);
    assertThat(trade.getCurrencyPair()).isEqualTo(CurrencyPair.BTC_XRP);
    assertThat(trade.getFeeAmount()).isEqualTo("0.012");
    assertThat(trade.getFeeCurrency()).isEqualTo(Currencies.XRP);
    assertThat(trade.getId()).isEqualTo("0000000000000000000000000000000000000000000000000000000000000000");
    assertThat(trade.getOrderId()).isEqualTo("0");
    // Price = 1.0 / (0.000029309526038 * 0.998)
    assertThat(trade.getPrice())
        .isEqualTo(new BigDecimal("34186.97411609205306550363511634115030681332485583111528").setScale(roundingScale, RoundingMode.HALF_UP));
    assertThat(trade.getTimestamp()).isEqualTo(RippleExchange.ToDate("2000-00-00T00:00:00.000Z"));
    // Quantity = 0.000029309526038 * 0.998
    assertThat(trade.getTradableAmount()).isEqualTo("0.000029250906985924");
    assertThat(trade.getType()).isEqualTo(OrderType.ASK);

    assertThat(trade).isInstanceOf(RippleUserTrade.class);
    final RippleUserTrade ripple = (RippleUserTrade) trade;

    assertThat(ripple.getBaseCounterparty()).isEqualTo("rMwjYedjc7qqtKYVLiAccJSmCwih4LnE2q");
    // Transfer fee = 0.000029309526038 * 0.002
    assertThat(ripple.getBaseTransferFee()).isEqualTo("0.000000058619052076");
    assertThat(ripple.getBaseTransferFeeCurrency()).isEqualTo("BTC");
    assertThat(ripple.getBaseTransferFeeCurrency()).isEqualTo(trade.getCurrencyPair().baseSymbol);

    assertThat(ripple.getCounterCounterparty()).isEmpty();
    assertThat(ripple.getCounterTransferFee()).isZero();
    assertThat(ripple.getCounterTransferFeeCurrency()).isEqualTo("XRP");
    assertThat(ripple.getCounterTransferFeeCurrency()).isEqualTo(trade.getCurrencyPair().counterSymbol);
  }

  @Test
  public void adaptTrade_SellXRP_BuyBTC() throws JsonParseException, JsonMappingException, IOException, ParseException {
    final RippleExchange exchange = new RippleExchange();
    final int roundingScale = exchange.getRoundingScale();

    // Read the trade JSON from the example resources
    final InputStream is = getClass().getResourceAsStream("/trade/example-trade-sellXRP-buyBTC.json");
    final ObjectMapper mapper = new ObjectMapper();
    final RippleOrderDetails response = mapper.readValue(is, RippleOrderDetails.class);

    final RippleTradeHistoryParams params = new RippleTradeHistoryParams();
    params.addPreferredCounterCurrency(Currencies.BTC);

    final UserTrade trade = RippleAdapters.adaptTrade(response, params, this, roundingScale);
    assertThat(trade.getCurrencyPair()).isEqualTo(CurrencyPair.XRP_BTC);
    assertThat(trade.getFeeAmount()).isEqualTo("0.012");
    assertThat(trade.getFeeCurrency()).isEqualTo(Currencies.XRP);
    assertThat(trade.getId()).isEqualTo("1111111111111111111111111111111111111111111111111111111111111111");
    assertThat(trade.getOrderId()).isEqualTo("1111");
    assertThat(trade.getPrice()).isEqualTo(new BigDecimal("0.000028572057152").setScale(roundingScale, RoundingMode.HALF_UP).stripTrailingZeros());
    assertThat(trade.getTimestamp()).isEqualTo(RippleExchange.ToDate("2011-11-11T11:11:11.111Z"));
    assertThat(trade.getTradableAmount()).isEqualTo("1");
    assertThat(trade.getType()).isEqualTo(OrderType.ASK);

    assertThat(trade).isInstanceOf(RippleUserTrade.class);
    final RippleUserTrade ripple = (RippleUserTrade) trade;

    assertThat(ripple.getBaseCounterparty()).isEmpty();
    assertThat(ripple.getBaseTransferFee()).isZero();
    assertThat(ripple.getBaseTransferFeeCurrency()).isEqualTo("XRP");
    assertThat(ripple.getBaseTransferFeeCurrency()).isEqualTo(trade.getCurrencyPair().baseSymbol);

    assertThat(ripple.getCounterCounterparty()).isEqualTo("rMwjYedjc7qqtKYVLiAccJSmCwih4LnE2q");
    assertThat(ripple.getCounterTransferFee()).isZero();
    assertThat(ripple.getCounterTransferFeeCurrency()).isEqualTo("BTC");
    assertThat(ripple.getCounterTransferFeeCurrency()).isEqualTo(trade.getCurrencyPair().counterSymbol);
  }

  @Test
  public void adaptTrade_BuyBTC_SellXRP() throws JsonParseException, JsonMappingException, IOException, ParseException {
    final RippleExchange exchange = new RippleExchange();
    final int roundingScale = exchange.getRoundingScale();

    // Read the trade JSON from the example resources
    final InputStream is = getClass().getResourceAsStream("/trade/example-trade-sellXRP-buyBTC.json");
    final ObjectMapper mapper = new ObjectMapper();
    final RippleOrderDetails response = mapper.readValue(is, RippleOrderDetails.class);

    final RippleTradeHistoryParams params = new RippleTradeHistoryParams();
    params.addPreferredBaseCurrency(Currencies.BTC);

    final UserTrade trade = RippleAdapters.adaptTrade(response, params, this, roundingScale);
    assertThat(trade.getCurrencyPair()).isEqualTo(CurrencyPair.BTC_XRP);
    assertThat(trade.getFeeAmount()).isEqualTo("0.012");
    assertThat(trade.getFeeCurrency()).isEqualTo(Currencies.XRP);
    assertThat(trade.getId()).isEqualTo("1111111111111111111111111111111111111111111111111111111111111111");
    assertThat(trade.getOrderId()).isEqualTo("1111");
    // Price = 1.0 / 0.000028572057152
    assertThat(trade.getPrice()).isEqualTo(new BigDecimal("34999.23000574012011552062010939099496310569328655387396")
        .setScale(roundingScale, RoundingMode.HALF_UP).stripTrailingZeros());
    assertThat(trade.getTimestamp()).isEqualTo(RippleExchange.ToDate("2011-11-11T11:11:11.111Z"));
    assertThat(trade.getTradableAmount()).isEqualTo("0.000028572057152");
    assertThat(trade.getType()).isEqualTo(OrderType.BID);

    assertThat(trade).isInstanceOf(RippleUserTrade.class);
    final RippleUserTrade ripple = (RippleUserTrade) trade;

    assertThat(ripple.getBaseCounterparty()).isEqualTo("rMwjYedjc7qqtKYVLiAccJSmCwih4LnE2q");
    assertThat(ripple.getBaseTransferFee()).isZero();
    assertThat(ripple.getBaseTransferFeeCurrency()).isEqualTo("BTC");
    assertThat(ripple.getBaseTransferFeeCurrency()).isEqualTo(trade.getCurrencyPair().baseSymbol);

    assertThat(ripple.getCounterCounterparty()).isEmpty();
    assertThat(ripple.getCounterTransferFee()).isZero();
    assertThat(ripple.getCounterTransferFeeCurrency()).isEqualTo("XRP");
    assertThat(ripple.getCounterTransferFeeCurrency()).isEqualTo(trade.getCurrencyPair().counterSymbol);
  }

  @Test
  public void adaptTrade_BuyBTC_SellBTC() throws JsonParseException, JsonMappingException, IOException, ParseException {
    final RippleExchange exchange = new RippleExchange();
    final int roundingScale = exchange.getRoundingScale();

    // Read the trade JSON from the example resources
    final InputStream is = getClass().getResourceAsStream("/trade/example-trade-buyBTC-sellBTC.json");
    final ObjectMapper mapper = new ObjectMapper();
    final RippleOrderDetails response = mapper.readValue(is, RippleOrderDetails.class);

    final TradeHistoryParams params = new TradeHistoryParams() {
    };

    final UserTrade trade = RippleAdapters.adaptTrade(response, params, this, roundingScale);
    assertThat(trade.getCurrencyPair().baseSymbol).isEqualTo(Currencies.BTC);
    assertThat(trade.getCurrencyPair().counterSymbol).isEqualTo(Currencies.BTC);
    assertThat(trade.getFeeAmount()).isEqualTo("0.012");
    assertThat(trade.getFeeCurrency()).isEqualTo(Currencies.XRP);
    assertThat(trade.getId()).isEqualTo("2222222222222222222222222222222222222222222222222222222222222222");
    assertThat(trade.getOrderId()).isEqualTo("2222");

    // Price = 0.501 * 0.998 / 0.50150835545121407952
    assertThat(trade.getPrice())
        .isEqualTo(new BigDecimal("0.99698837430165008596385145696065600512973847422746").setScale(roundingScale, RoundingMode.HALF_UP));
    assertThat(trade.getTimestamp()).isEqualTo(RippleExchange.ToDate("2022-22-22T22:22:22.222Z"));
    assertThat(trade.getTradableAmount()).isEqualTo("0.50150835545121407952");
    assertThat(trade.getType()).isEqualTo(OrderType.BID);

    assertThat(trade).isInstanceOf(RippleUserTrade.class);
    final RippleUserTrade ripple = (RippleUserTrade) trade;

    assertThat(ripple.getBaseCounterparty()).isEqualTo("rMwjYedjc7qqtKYVLiAccJSmCwih4LnE2q");
    assertThat(ripple.getBaseTransferFee()).isZero();
    assertThat(ripple.getBaseTransferFeeCurrency()).isEqualTo("BTC");
    assertThat(ripple.getBaseTransferFeeCurrency()).isEqualTo(trade.getCurrencyPair().baseSymbol);

    assertThat(ripple.getCounterCounterparty()).isEqualTo("rvYAfWj5gh67oV6fW32ZzP3Aw4Eubs59B");
    // Transfer fee = 0.501 * 0.002
    assertThat(ripple.getCounterTransferFee()).isEqualTo("0.001002");
    assertThat(ripple.getCounterTransferFeeCurrency()).isEqualTo("BTC");
    assertThat(ripple.getCounterTransferFeeCurrency()).isEqualTo(trade.getCurrencyPair().counterSymbol);
  }
}
