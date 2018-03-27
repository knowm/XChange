package org.knowm.xchange.ripple;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import org.junit.Test;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order.OrderType;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.dto.account.Balance;
import org.knowm.xchange.dto.account.Wallet;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.OpenOrders;
import org.knowm.xchange.dto.trade.UserTrade;
import org.knowm.xchange.ripple.dto.account.ITransferFeeSource;
import org.knowm.xchange.ripple.dto.account.RippleAccountBalances;
import org.knowm.xchange.ripple.dto.account.RippleAccountSettings;
import org.knowm.xchange.ripple.dto.marketdata.RippleOrderBook;
import org.knowm.xchange.ripple.dto.trade.IRippleTradeTransaction;
import org.knowm.xchange.ripple.dto.trade.RippleAccountOrders;
import org.knowm.xchange.ripple.dto.trade.RippleLimitOrder;
import org.knowm.xchange.ripple.dto.trade.RippleOrderTransaction;
import org.knowm.xchange.ripple.dto.trade.RipplePaymentTransaction;
import org.knowm.xchange.ripple.dto.trade.RippleUserTrade;
import org.knowm.xchange.ripple.service.params.RippleMarketDataParams;
import org.knowm.xchange.ripple.service.params.RippleTradeHistoryParams;
import org.knowm.xchange.service.trade.params.TradeHistoryParams;

public class RippleAdaptersTest implements ITransferFeeSource {

  @Test
  public void adaptAccountInfoTest() throws IOException {
    // Read in the JSON from the example resources
    final InputStream is =
        getClass()
            .getResourceAsStream(
                "/org/knowm/xchange/ripple/dto/account/example-account-balances.json");

    // Use Jackson to parse it
    final ObjectMapper mapper = new ObjectMapper();
    final RippleAccountBalances rippleAccount = mapper.readValue(is, RippleAccountBalances.class);

    // Convert to xchange object and check field values
    final AccountInfo account = RippleAdapters.adaptAccountInfo(rippleAccount, "username");
    assertThat(account.getWallets()).hasSize(2);
    assertThat(account.getUsername()).isEqualTo("username");
    assertThat(account.getTradingFee()).isEqualTo(BigDecimal.ZERO);

    final Wallet counterWallet = account.getWallet("rvYAfWj5gh67oV6fW32ZzP3Aw4Eubs59B");
    assertThat(counterWallet.getId()).isEqualTo("rvYAfWj5gh67oV6fW32ZzP3Aw4Eubs59B");
    assertThat(counterWallet.getBalances()).hasSize(2);

    final Balance btcBalance = counterWallet.getBalance(Currency.BTC);
    assertThat(btcBalance.getTotal()).isEqualTo("0.038777349225374");
    assertThat(btcBalance.getCurrency()).isEqualTo(Currency.BTC);

    final Balance usdBalance = counterWallet.getBalance(Currency.USD);
    assertThat(usdBalance.getTotal()).isEqualTo("10");
    assertThat(usdBalance.getCurrency()).isEqualTo(Currency.USD);

    final Wallet mainWallet = account.getWallet(null);
    assertThat(mainWallet.getBalances()).hasSize(1);

    final Balance xrpBalance = mainWallet.getBalance(Currency.XRP);
    assertThat(xrpBalance.getTotal()).isEqualTo("861.401578");
    assertThat(xrpBalance.getCurrency()).isEqualTo(Currency.XRP);
  }

  @Test
  public void adaptOrderBookTest() throws IOException {
    // Read in the JSON from the example resources
    final InputStream is =
        getClass()
            .getResourceAsStream(
                "/org/knowm/xchange/ripple/dto/marketdata/example-order-book.json");
    final CurrencyPair currencyPair = CurrencyPair.XRP_BTC;

    // Test data uses Bitstamp issued BTC
    final RippleMarketDataParams params = new RippleMarketDataParams();
    params.setCounterCounterparty("rvYAfWj5gh67oV6fW32ZzP3Aw4Eubs59B");

    // Use Jackson to parse it
    final ObjectMapper mapper = new ObjectMapper();
    final RippleOrderBook rippleOrderBook = mapper.readValue(is, RippleOrderBook.class);

    // Convert to xchange object and check field values
    final OrderBook orderBook =
        RippleAdapters.adaptOrderBook(rippleOrderBook, params, currencyPair);
    assertThat(orderBook.getBids()).hasSize(10);
    assertThat(orderBook.getAsks()).hasSize(10);

    final LimitOrder lastBid = orderBook.getBids().get(9);
    assertThat(lastBid).isInstanceOf(RippleLimitOrder.class);
    assertThat(lastBid.getCurrencyPair()).isEqualTo(currencyPair);
    assertThat(((RippleLimitOrder) lastBid).getCounterCounterparty())
        .isEqualTo("rvYAfWj5gh67oV6fW32ZzP3Aw4Eubs59B");
    assertThat(lastBid.getType()).isEqualTo(OrderType.BID);
    assertThat(lastBid.getId()).isEqualTo("1303704");
    assertThat(lastBid.getOriginalAmount()).isEqualTo("66314.537782");
    assertThat(lastBid.getLimitPrice()).isEqualTo("0.00003317721777288062");

    final LimitOrder firstAsk = orderBook.getAsks().get(0);
    assertThat(firstAsk).isInstanceOf(RippleLimitOrder.class);
    assertThat(firstAsk.getCurrencyPair()).isEqualTo(currencyPair);
    assertThat(((RippleLimitOrder) firstAsk).getCounterCounterparty())
        .isEqualTo("rvYAfWj5gh67oV6fW32ZzP3Aw4Eubs59B");
    assertThat(firstAsk.getType()).isEqualTo(OrderType.ASK);
    assertThat(firstAsk.getId()).isEqualTo("1011310");
    assertThat(firstAsk.getOriginalAmount()).isEqualTo("35447.914936");
    assertThat(firstAsk.getLimitPrice()).isEqualTo("0.00003380846624897726");
  }

  @Test
  public void adaptOpenOrdersTest() throws JsonParseException, JsonMappingException, IOException {
    final RippleExchange exchange = new RippleExchange();
    final int roundingScale = exchange.getRoundingScale();

    // Read in the JSON from the example resources
    final InputStream is =
        getClass()
            .getResourceAsStream("/org/knowm/xchange/ripple/dto/trade/example-account-orders.json");
    final ObjectMapper mapper = new ObjectMapper();
    final RippleAccountOrders response = mapper.readValue(is, RippleAccountOrders.class);

    // Convert to XChange orders
    final OpenOrders orders = RippleAdapters.adaptOpenOrders(response, roundingScale);
    assertThat(orders.getOpenOrders()).hasSize(12);

    final LimitOrder firstOrder = orders.getOpenOrders().get(0);
    assertThat(firstOrder).isInstanceOf(RippleLimitOrder.class);
    assertThat(firstOrder.getCurrencyPair()).isEqualTo(CurrencyPair.XRP_BTC);
    assertThat(((RippleLimitOrder) firstOrder).getCounterCounterparty())
        .isEqualTo("rvYAfWj5gh67oV6fW32ZzP3Aw4Eubs59B");
    assertThat(firstOrder.getId()).isEqualTo("5");
    assertThat(firstOrder.getLimitPrice()).isEqualTo("0.00003226");
    assertThat(firstOrder.getTimestamp()).isNull();
    assertThat(firstOrder.getOriginalAmount()).isEqualTo("1");
    assertThat(firstOrder.getType()).isEqualTo(OrderType.BID);

    final LimitOrder secondOrder = orders.getOpenOrders().get(1);
    assertThat(secondOrder).isInstanceOf(RippleLimitOrder.class);
    assertThat(secondOrder.getCurrencyPair()).isEqualTo(CurrencyPair.XRP_BTC);
    assertThat(((RippleLimitOrder) secondOrder).getCounterCounterparty())
        .isEqualTo("rvYAfWj5gh67oV6fW32ZzP3Aw4Eubs59B");
    assertThat(secondOrder.getId()).isEqualTo("7");
    // Price = 15159.38551342023 / 123.123456
    assertThat(secondOrder.getLimitPrice())
        .isEqualTo("123.12345677999998635515884154518859509596611713043533");
    assertThat(secondOrder.getTimestamp()).isNull();
    assertThat(secondOrder.getOriginalAmount()).isEqualTo("123.123456");
    assertThat(secondOrder.getType()).isEqualTo(OrderType.ASK);
  }

  @Override
  public BigDecimal getTransferFeeRate(final String address) throws IOException {
    final InputStream is =
        getClass()
            .getResourceAsStream(
                String.format(
                    "/org/knowm/xchange/ripple/dto/account/example-account-settings-%s.json",
                    address));
    final ObjectMapper mapper = new ObjectMapper();
    return mapper.readValue(is, RippleAccountSettings.class).getSettings().getTransferFeeRate();
  }

  @Test
  public void adaptTrade_BuyXRP_SellBTC()
      throws JsonParseException, JsonMappingException, IOException, ParseException {
    final RippleExchange exchange = new RippleExchange();
    final int roundingScale = exchange.getRoundingScale();

    // Read the trade JSON from the example resources
    final InputStream is =
        getClass()
            .getResourceAsStream(
                "/org/knowm/xchange/ripple/dto/trade/example-trade-buyXRP-sellBTC.json");
    final ObjectMapper mapper = new ObjectMapper();
    final RippleOrderTransaction response = mapper.readValue(is, RippleOrderTransaction.class);

    final RippleTradeHistoryParams params = new RippleTradeHistoryParams();
    params.addPreferredCounterCurrency(Currency.BTC);

    final UserTrade trade = RippleAdapters.adaptTrade(response, params, this, roundingScale);
    assertThat(trade.getCurrencyPair()).isEqualTo(CurrencyPair.XRP_BTC);
    assertThat(trade.getFeeAmount()).isEqualTo("0.012");
    assertThat(trade.getFeeCurrency()).isEqualTo(Currency.XRP);
    assertThat(trade.getId())
        .isEqualTo("0000000000000000000000000000000000000000000000000000000000000000");
    assertThat(trade.getOrderId()).isEqualTo("1010");
    // Price = 0.000029309526038 * 0.998
    assertThat(trade.getPrice())
        .isEqualTo(
            new BigDecimal("0.000029250906985924")
                .setScale(roundingScale, RoundingMode.HALF_UP)
                .stripTrailingZeros());
    assertThat(trade.getTimestamp()).isEqualTo(RippleExchange.ToDate("2000-00-00T00:00:00.000Z"));
    assertThat(trade.getOriginalAmount()).isEqualTo("1");
    assertThat(trade.getType()).isEqualTo(OrderType.BID);

    assertThat(trade).isInstanceOf(RippleUserTrade.class);
    final RippleUserTrade ripple = (RippleUserTrade) trade;

    assertThat(ripple.getBaseCounterparty()).isEmpty();
    assertThat(ripple.getBaseTransferFee()).isZero();
    assertThat(ripple.getBaseTransferFeeCurrency()).isEqualTo(Currency.XRP);
    assertThat(ripple.getBaseTransferFeeCurrency()).isEqualTo(trade.getCurrencyPair().base);

    assertThat(ripple.getCounterCounterparty()).isEqualTo("rMwjYedjc7qqtKYVLiAccJSmCwih4LnE2q");
    // Transfer fee = 0.000029309526038 * 0.002
    assertThat(ripple.getCounterTransferFee()).isEqualTo("0.000000058619052076");
    assertThat(ripple.getCounterTransferFeeCurrency()).isEqualTo(Currency.BTC);
    assertThat(ripple.getCounterTransferFeeCurrency()).isEqualTo(trade.getCurrencyPair().counter);
  }

  @Test
  public void adaptTrade_SellBTC_BuyXRP()
      throws JsonParseException, JsonMappingException, IOException, ParseException {
    final RippleExchange exchange = new RippleExchange();
    final int roundingScale = exchange.getRoundingScale();

    // Read the trade JSON from the example resources
    final InputStream is =
        getClass()
            .getResourceAsStream(
                "/org/knowm/xchange/ripple/dto/trade/example-trade-buyXRP-sellBTC.json");
    final ObjectMapper mapper = new ObjectMapper();
    final RippleOrderTransaction response = mapper.readValue(is, RippleOrderTransaction.class);

    final RippleTradeHistoryParams params = new RippleTradeHistoryParams();
    params.addPreferredBaseCurrency(Currency.BTC);

    final UserTrade trade = RippleAdapters.adaptTrade(response, params, this, roundingScale);
    assertThat(trade.getCurrencyPair()).isEqualTo(CurrencyPair.BTC_XRP);
    assertThat(trade.getFeeAmount()).isEqualTo("0.012");
    assertThat(trade.getFeeCurrency()).isEqualTo(Currency.XRP);
    assertThat(trade.getId())
        .isEqualTo("0000000000000000000000000000000000000000000000000000000000000000");
    assertThat(trade.getOrderId()).isEqualTo("1010");
    // Price = 1.0 / (0.000029309526038 * 0.998)
    assertThat(trade.getPrice())
        .isEqualTo(
            new BigDecimal("34186.97411609205306550363511634115030681332485583111528")
                .setScale(roundingScale, RoundingMode.HALF_UP));
    assertThat(trade.getTimestamp()).isEqualTo(RippleExchange.ToDate("2000-00-00T00:00:00.000Z"));
    // Quantity = 0.000029309526038 * 0.998
    assertThat(trade.getOriginalAmount()).isEqualTo("0.000029250906985924");
    assertThat(trade.getType()).isEqualTo(OrderType.ASK);

    assertThat(trade).isInstanceOf(RippleUserTrade.class);
    final RippleUserTrade ripple = (RippleUserTrade) trade;

    assertThat(ripple.getBaseCounterparty()).isEqualTo("rMwjYedjc7qqtKYVLiAccJSmCwih4LnE2q");
    // Transfer fee = 0.000029309526038 * 0.002
    assertThat(ripple.getBaseTransferFee()).isEqualTo("0.000000058619052076");
    assertThat(ripple.getBaseTransferFeeCurrency()).isEqualTo(Currency.BTC);
    assertThat(ripple.getBaseTransferFeeCurrency()).isEqualTo(trade.getCurrencyPair().base);

    assertThat(ripple.getCounterCounterparty()).isEmpty();
    assertThat(ripple.getCounterTransferFee()).isZero();
    assertThat(ripple.getCounterTransferFeeCurrency()).isEqualTo(Currency.XRP);
    assertThat(ripple.getCounterTransferFeeCurrency()).isEqualTo(trade.getCurrencyPair().counter);
  }

  @Test
  public void adaptTrade_SellXRP_BuyBTC()
      throws JsonParseException, JsonMappingException, IOException, ParseException {
    final RippleExchange exchange = new RippleExchange();
    final int roundingScale = exchange.getRoundingScale();

    // Read the trade JSON from the example resources
    final InputStream is =
        getClass()
            .getResourceAsStream(
                "/org/knowm/xchange/ripple/dto/trade/example-trade-sellXRP-buyBTC.json");
    final ObjectMapper mapper = new ObjectMapper();
    final IRippleTradeTransaction response = mapper.readValue(is, RippleOrderTransaction.class);

    final RippleTradeHistoryParams params = new RippleTradeHistoryParams();
    params.setCurrencyPair(CurrencyPair.XRP_BTC);

    final UserTrade trade = RippleAdapters.adaptTrade(response, params, this, roundingScale);
    assertThat(trade.getCurrencyPair()).isEqualTo(CurrencyPair.XRP_BTC);
    assertThat(trade.getFeeAmount()).isEqualTo("0.012");
    assertThat(trade.getFeeCurrency()).isEqualTo(Currency.XRP);
    assertThat(trade.getId())
        .isEqualTo("1111111111111111111111111111111111111111111111111111111111111111");
    assertThat(trade.getOrderId()).isEqualTo("1111");
    assertThat(trade.getPrice())
        .isEqualTo(
            new BigDecimal("0.000028572057152")
                .setScale(roundingScale, RoundingMode.HALF_UP)
                .stripTrailingZeros());
    assertThat(trade.getTimestamp()).isEqualTo(RippleExchange.ToDate("2011-11-11T11:11:11.111Z"));
    assertThat(trade.getOriginalAmount()).isEqualTo("1");
    assertThat(trade.getType()).isEqualTo(OrderType.ASK);

    assertThat(trade).isInstanceOf(RippleUserTrade.class);
    final RippleUserTrade ripple = (RippleUserTrade) trade;

    assertThat(ripple.getBaseCounterparty()).isEmpty();
    assertThat(ripple.getBaseTransferFee()).isZero();
    assertThat(ripple.getBaseTransferFeeCurrency()).isEqualTo(Currency.XRP);
    assertThat(ripple.getBaseTransferFeeCurrency()).isEqualTo(trade.getCurrencyPair().base);

    assertThat(ripple.getCounterCounterparty()).isEqualTo("rMwjYedjc7qqtKYVLiAccJSmCwih4LnE2q");
    assertThat(ripple.getCounterTransferFee()).isZero();
    assertThat(ripple.getCounterTransferFeeCurrency()).isEqualTo(Currency.BTC);
    assertThat(ripple.getCounterTransferFeeCurrency()).isEqualTo(trade.getCurrencyPair().counter);

    // make sure that if the IRippleTradeTransaction is adapted again it returns the same values
    final UserTrade trade2 = RippleAdapters.adaptTrade(response, params, this, roundingScale);
    assertThat(trade2.getCurrencyPair()).isEqualTo(trade.getCurrencyPair());
    assertThat(trade2.getFeeAmount()).isEqualTo(trade.getFeeAmount());
    assertThat(trade2.getFeeCurrency()).isEqualTo(trade.getFeeCurrency());
    assertThat(trade2.getId()).isEqualTo(trade.getId());
    assertThat(trade2.getOrderId()).isEqualTo(trade.getOrderId());
    assertThat(trade2.getPrice()).isEqualTo(trade.getPrice());
    assertThat(trade2.getTimestamp()).isEqualTo(trade.getTimestamp());
    assertThat(trade2.getOriginalAmount()).isEqualTo(trade.getOriginalAmount());
    assertThat(trade2.getType()).isEqualTo(trade.getType());
  }

  @Test
  public void adaptTrade_BuyBTC_SellXRP()
      throws JsonParseException, JsonMappingException, IOException, ParseException {
    final RippleExchange exchange = new RippleExchange();
    final int roundingScale = exchange.getRoundingScale();

    // Read the trade JSON from the example resources
    final InputStream is =
        getClass()
            .getResourceAsStream(
                "/org/knowm/xchange/ripple/dto/trade/example-trade-sellXRP-buyBTC.json");
    final ObjectMapper mapper = new ObjectMapper();
    final RippleOrderTransaction response = mapper.readValue(is, RippleOrderTransaction.class);

    final RippleTradeHistoryParams params = new RippleTradeHistoryParams();
    params.addPreferredBaseCurrency(Currency.BTC);

    final UserTrade trade = RippleAdapters.adaptTrade(response, params, this, roundingScale);
    assertThat(trade.getCurrencyPair()).isEqualTo(CurrencyPair.BTC_XRP);
    assertThat(trade.getFeeAmount()).isEqualTo("0.012");
    assertThat(trade.getFeeCurrency()).isEqualTo(Currency.XRP);
    assertThat(trade.getId())
        .isEqualTo("1111111111111111111111111111111111111111111111111111111111111111");
    assertThat(trade.getOrderId()).isEqualTo("1111");
    // Price = 1.0 / 0.000028572057152
    assertThat(trade.getPrice())
        .isEqualTo(
            new BigDecimal("34999.23000574012011552062010939099496310569328655387396")
                .setScale(roundingScale, RoundingMode.HALF_UP)
                .stripTrailingZeros());
    assertThat(trade.getTimestamp()).isEqualTo(RippleExchange.ToDate("2011-11-11T11:11:11.111Z"));
    assertThat(trade.getOriginalAmount()).isEqualTo("0.000028572057152");
    assertThat(trade.getType()).isEqualTo(OrderType.BID);

    assertThat(trade).isInstanceOf(RippleUserTrade.class);
    final RippleUserTrade ripple = (RippleUserTrade) trade;

    assertThat(ripple.getBaseCounterparty()).isEqualTo("rMwjYedjc7qqtKYVLiAccJSmCwih4LnE2q");
    assertThat(ripple.getBaseTransferFee()).isZero();
    assertThat(ripple.getBaseTransferFeeCurrency()).isEqualTo(Currency.BTC);
    assertThat(ripple.getBaseTransferFeeCurrency()).isEqualTo(trade.getCurrencyPair().base);

    assertThat(ripple.getCounterCounterparty()).isEmpty();
    assertThat(ripple.getCounterTransferFee()).isZero();
    assertThat(ripple.getCounterTransferFeeCurrency()).isEqualTo(Currency.XRP);
    assertThat(ripple.getCounterTransferFeeCurrency()).isEqualTo(trade.getCurrencyPair().counter);
  }

  @Test
  public void adaptTrade_BuyBTC_SellBTC()
      throws JsonParseException, JsonMappingException, IOException, ParseException {
    final RippleExchange exchange = new RippleExchange();
    final int roundingScale = exchange.getRoundingScale();

    // Read the trade JSON from the example resources
    final InputStream is =
        getClass()
            .getResourceAsStream(
                "/org/knowm/xchange/ripple/dto/trade/example-trade-buyBTC-sellBTC.json");
    final ObjectMapper mapper = new ObjectMapper();
    final RippleOrderTransaction response = mapper.readValue(is, RippleOrderTransaction.class);

    final TradeHistoryParams params = new TradeHistoryParams() {};

    final UserTrade trade = RippleAdapters.adaptTrade(response, params, this, roundingScale);
    assertThat(trade.getCurrencyPair().base).isEqualTo(Currency.BTC);
    assertThat(trade.getCurrencyPair().counter).isEqualTo(Currency.BTC);
    assertThat(trade.getFeeAmount()).isEqualTo("0.012");
    assertThat(trade.getFeeCurrency()).isEqualTo(Currency.XRP);
    assertThat(trade.getId())
        .isEqualTo("2222222222222222222222222222222222222222222222222222222222222222");
    assertThat(trade.getOrderId()).isEqualTo("2222");

    // Price = 0.501 * 0.998 / 0.50150835545121407952
    assertThat(trade.getPrice())
        .isEqualTo(
            new BigDecimal("0.99698837430165008596385145696065600512973847422746")
                .setScale(roundingScale, RoundingMode.HALF_UP));
    assertThat(trade.getTimestamp()).isEqualTo(RippleExchange.ToDate("2022-22-22T22:22:22.222Z"));
    assertThat(trade.getOriginalAmount()).isEqualTo("0.50150835545121407952");
    assertThat(trade.getType()).isEqualTo(OrderType.BID);

    assertThat(trade).isInstanceOf(RippleUserTrade.class);
    final RippleUserTrade ripple = (RippleUserTrade) trade;

    assertThat(ripple.getBaseCounterparty()).isEqualTo("rMwjYedjc7qqtKYVLiAccJSmCwih4LnE2q");
    assertThat(ripple.getBaseTransferFee()).isZero();
    assertThat(ripple.getBaseTransferFeeCurrency()).isEqualTo(Currency.BTC);
    assertThat(ripple.getBaseTransferFeeCurrency()).isEqualTo(trade.getCurrencyPair().base);

    assertThat(ripple.getCounterCounterparty()).isEqualTo("rvYAfWj5gh67oV6fW32ZzP3Aw4Eubs59B");
    // Transfer fee = 0.501 * 0.002
    assertThat(ripple.getCounterTransferFee()).isEqualTo("0.001002");
    assertThat(ripple.getCounterTransferFeeCurrency()).isEqualTo(Currency.BTC);
    assertThat(ripple.getCounterTransferFeeCurrency()).isEqualTo(trade.getCurrencyPair().counter);
  }

  @Test
  public void adaptTrade_PaymentPassthrough()
      throws JsonParseException, JsonMappingException, IOException, ParseException {
    final RippleExchange exchange = new RippleExchange();
    final int roundingScale = exchange.getRoundingScale();

    // Read the trade JSON from the example resources
    final InputStream is =
        getClass()
            .getResourceAsStream(
                "/org/knowm/xchange/ripple/dto/trade/example-payment-passthrough.json");
    final ObjectMapper mapper = new ObjectMapper();
    final RipplePaymentTransaction response = mapper.readValue(is, RipplePaymentTransaction.class);

    final TradeHistoryParams params = new TradeHistoryParams() {};

    final UserTrade trade = RippleAdapters.adaptTrade(response, params, this, roundingScale);
    assertThat(trade.getCurrencyPair().base).isEqualTo(Currency.XRP);
    assertThat(trade.getCurrencyPair().counter).isEqualTo(Currency.BTC);
    assertThat(trade.getFeeAmount()).isEqualTo("0.012");
    assertThat(trade.getFeeCurrency()).isEqualTo(Currency.XRP);
    assertThat(trade.getId())
        .isEqualTo("GHRE072948B95345396B2D9A364363GDE521HRT67QQRGGRTHYTRUP0RRB631107");
    assertThat(trade.getOrderId()).isEqualTo("9338");

    // Price = 0.009941478580724 / (349.559725 - 0.012)
    assertThat(trade.getPrice())
        .isEqualTo(
            new BigDecimal("0.00002844097635229638527900589254299967193321026478")
                .setScale(roundingScale, RoundingMode.HALF_UP));
    assertThat(trade.getTimestamp()).isEqualTo(RippleExchange.ToDate("2015-08-07T03:58:10.000Z"));
    assertThat(trade.getOriginalAmount()).isEqualTo("349.547725");
    assertThat(trade.getType()).isEqualTo(OrderType.ASK);

    assertThat(trade).isInstanceOf(RippleUserTrade.class);
    final RippleUserTrade ripple = (RippleUserTrade) trade;

    assertThat(ripple.getBaseCounterparty()).isEqualTo("");
    assertThat(ripple.getBaseTransferFee()).isZero();
    assertThat(ripple.getBaseTransferFeeCurrency()).isEqualTo(Currency.XRP);
    assertThat(ripple.getBaseTransferFeeCurrency()).isEqualTo(trade.getCurrencyPair().base);

    assertThat(ripple.getCounterCounterparty()).isEqualTo("rMwjYedjc7qqtKYVLiAccJSmCwih4LnE2q");
    // Transfer fee = 0.501 * 0.002
    assertThat(ripple.getCounterTransferFee()).isEqualTo("0");
    assertThat(ripple.getCounterTransferFeeCurrency()).isEqualTo(Currency.BTC);
    assertThat(ripple.getCounterTransferFeeCurrency()).isEqualTo(trade.getCurrencyPair().counter);
  }
}
