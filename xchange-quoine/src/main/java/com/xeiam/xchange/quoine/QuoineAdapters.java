package com.xeiam.xchange.quoine;

import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.xeiam.xchange.currency.Currency;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.Order;
import com.xeiam.xchange.dto.Order.OrderType;
import com.xeiam.xchange.dto.account.Balance;
import com.xeiam.xchange.dto.account.Wallet;
import com.xeiam.xchange.dto.marketdata.OrderBook;
import com.xeiam.xchange.dto.marketdata.Ticker;
import com.xeiam.xchange.dto.trade.LimitOrder;
import com.xeiam.xchange.dto.trade.OpenOrders;
import com.xeiam.xchange.quoine.dto.account.FiatAccount;
import com.xeiam.xchange.quoine.dto.account.QuoineAccountInfo;
import com.xeiam.xchange.quoine.dto.account.QuoineTradingAccountInfo;
import com.xeiam.xchange.quoine.dto.marketdata.QuoineOrderBook;
import com.xeiam.xchange.quoine.dto.marketdata.QuoineProduct;
import com.xeiam.xchange.quoine.dto.trade.Model;
import com.xeiam.xchange.quoine.dto.trade.QuoineOrdersList;

public class QuoineAdapters {

  public static Ticker adaptTicker(QuoineProduct quoineTicker, CurrencyPair currencyPair) {

    Ticker.Builder builder = new Ticker.Builder();
    builder.ask(quoineTicker.getMarketAsk());
    builder.bid(quoineTicker.getMarketBid());
    builder.last(quoineTicker.getLastPrice24h());
    builder.volume(quoineTicker.getVolume24h());
    builder.currencyPair(currencyPair);
    return builder.build();
  }

  public static OrderBook adaptOrderBook(QuoineOrderBook quoineOrderBook, CurrencyPair currencyPair) {

    List<LimitOrder> asks = createOrders(currencyPair, Order.OrderType.ASK, quoineOrderBook.getSellPriceLevels());
    List<LimitOrder> bids = createOrders(currencyPair, Order.OrderType.BID, quoineOrderBook.getBuyPriceLevels());
    return new OrderBook(null, asks, bids);
  }

  public static List<LimitOrder> createOrders(CurrencyPair currencyPair, Order.OrderType orderType, List<List<BigDecimal>> orders) {

    List<LimitOrder> limitOrders = new ArrayList<LimitOrder>();
    for (List<BigDecimal> ask : orders) {
      checkArgument(ask.size() == 2, "Expected a pair (price, amount) but got {0} elements.", ask.size());
      limitOrders.add(createOrder(currencyPair, ask, orderType));
    }
    return limitOrders;
  }

  public static LimitOrder createOrder(CurrencyPair currencyPair, List<BigDecimal> priceAndAmount, Order.OrderType orderType) {

    return new LimitOrder(orderType, priceAndAmount.get(1), currencyPair, "", null, priceAndAmount.get(0));
  }

  public static void checkArgument(boolean argument, String msgPattern, Object... msgArgs) {

    if (!argument) {
      throw new IllegalArgumentException(MessageFormat.format(msgPattern, msgArgs));
    }
  }

  public static Wallet adaptTradingWallet(QuoineTradingAccountInfo[] quoineWallet) {
    List<Balance> balances = new ArrayList<Balance>(quoineWallet.length);

    // btc position is sum of all positions in margin. Asuming all currencies are using the same margin level.
    BigDecimal btcPosition = BigDecimal.ZERO;

    for (int i = 0; i < quoineWallet.length; i++) {
      QuoineTradingAccountInfo info = quoineWallet[i];

      balances.add(new Balance(Currency.getInstance(info.getCollateralCurrency()), info.getFreeMargin()));

      btcPosition = btcPosition.add(info.getPosition());
    }

    balances.add(new Balance(Currency.BTC, btcPosition));

    return new Wallet(balances);
  }

  public static Wallet adaptWallet(QuoineAccountInfo quoineWallet) {

    List<Balance> balances = new ArrayList<Balance>();

    // Adapt to XChange DTOs
    Balance btcBalance = new Balance(Currency.getInstance(quoineWallet.getBitcoinAccount().getCurrency()),
        quoineWallet.getBitcoinAccount().getBalance(),
        quoineWallet.getBitcoinAccount().getFreeBalance());
    balances.add(btcBalance);

    for (FiatAccount fiatAccount : quoineWallet.getFiatAccounts()) {
      Balance fiatBalance = new Balance(Currency.getInstance(fiatAccount.getCurrency()), fiatAccount.getBalance(), fiatAccount.getFreeBalance());
      balances.add(fiatBalance);
    }

    return new Wallet(balances);

  }

  public static OpenOrders adapteOpenOrders(QuoineOrdersList quoineOrdersList) {

    List<LimitOrder> openOrders = new ArrayList<LimitOrder>();
    for (Model model : quoineOrdersList.getModels()) {
      if (model.getStatus().equals("live")) {

        // currencey pair
        String baseSymbol = model.getCurrencyPairCode().substring(0, 3);
        String counterSymbol = model.getCurrencyPairCode().substring(3, 6);
        CurrencyPair currencyPair = new CurrencyPair(baseSymbol, counterSymbol);

        // OrderType
        OrderType orderType = model.getSide().equals("sell") ? OrderType.ASK : OrderType.BID;

        // Timestamp
        Date timestamp = new Date(model.getCreatedAt().longValue() * 1000L);

        LimitOrder limitOrder = new LimitOrder(orderType, model.getQuantity(), currencyPair, model.getId(), timestamp, model.getPrice());

        openOrders.add(limitOrder);
      }
    }

    return new OpenOrders(openOrders);
  }
}
