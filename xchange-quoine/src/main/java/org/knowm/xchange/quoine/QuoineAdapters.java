package org.knowm.xchange.quoine;

import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order.OrderType;
import org.knowm.xchange.dto.account.Balance;
import org.knowm.xchange.dto.account.FundingRecord;
import org.knowm.xchange.dto.account.Wallet;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.OpenOrders;
import org.knowm.xchange.dto.trade.UserTrade;
import org.knowm.xchange.quoine.dto.account.BitcoinAccount;
import org.knowm.xchange.quoine.dto.account.FiatAccount;
import org.knowm.xchange.quoine.dto.account.QuoineAccountInfo;
import org.knowm.xchange.quoine.dto.account.QuoineTradingAccountInfo;
import org.knowm.xchange.quoine.dto.marketdata.QuoineOrderBook;
import org.knowm.xchange.quoine.dto.marketdata.QuoineProduct;
import org.knowm.xchange.quoine.dto.trade.Model;
import org.knowm.xchange.quoine.dto.trade.QuoineExecution;
import org.knowm.xchange.quoine.dto.trade.QuoineOrdersList;
import org.knowm.xchange.quoine.dto.trade.QuoineTransaction;
import org.knowm.xchange.utils.DateUtils;

public class QuoineAdapters {

  public static Ticker adaptTicker(QuoineProduct quoineTicker, CurrencyPair currencyPair) {

    Ticker.Builder builder = new Ticker.Builder();
    builder.ask(quoineTicker.getMarketAsk());
    builder.bid(quoineTicker.getMarketBid());
    builder.last(quoineTicker.getLastTradedPrice());
    builder.volume(quoineTicker.getVolume24h());
    builder.currencyPair(currencyPair);
    return builder.build();
  }

  public static OrderBook adaptOrderBook(
      QuoineOrderBook quoineOrderBook, CurrencyPair currencyPair) {

    List<LimitOrder> asks =
        createOrders(currencyPair, OrderType.ASK, quoineOrderBook.getSellPriceLevels());
    List<LimitOrder> bids =
        createOrders(currencyPair, OrderType.BID, quoineOrderBook.getBuyPriceLevels());
    return new OrderBook(null, asks, bids);
  }

  public static List<LimitOrder> createOrders(
      CurrencyPair currencyPair, OrderType orderType, List<BigDecimal[]> orders) {

    List<LimitOrder> limitOrders = new ArrayList<>();
    for (BigDecimal[] ask : orders) {
      checkArgument(
          ask.length == 2, "Expected a pair (price, amount) but got {0} elements.", ask.length);
      limitOrders.add(createOrder(currencyPair, ask, orderType));
    }
    return limitOrders;
  }

  public static LimitOrder createOrder(
      CurrencyPair currencyPair, BigDecimal[] priceAndAmount, OrderType orderType) {

    return new LimitOrder(orderType, priceAndAmount[1], currencyPair, "", null, priceAndAmount[0]);
  }

  public static void checkArgument(boolean argument, String msgPattern, Object... msgArgs) {

    if (!argument) {
      throw new IllegalArgumentException(MessageFormat.format(msgPattern, msgArgs));
    }
  }

  public static Wallet adaptTradingWallet(QuoineTradingAccountInfo[] quoineWallet) {
    List<Balance> balances = new ArrayList<>(quoineWallet.length);

    for (int i = 0; i < quoineWallet.length; i++) {
      QuoineTradingAccountInfo info = quoineWallet[i];

      balances.add(
          new Balance(Currency.getInstance(info.getFundingCurrency()), info.getFreeMargin()));
    }

    return Wallet.Builder.from(balances).build();
  }

  public static Wallet adaptFiatAccountWallet(FiatAccount[] fiatAccounts) {

    List<Balance> balances = new ArrayList<>();

    for (FiatAccount fiatAccount : fiatAccounts) {
      Balance fiatBalance =
          new Balance(
              Currency.getInstance(fiatAccount.getCurrency()),
              fiatAccount.getBalance(),
              fiatAccount.getBalance());
      balances.add(fiatBalance);
    }

    return Wallet.Builder.from(balances).build();
  }

  public static Wallet adaptWallet(QuoineAccountInfo quoineWallet) {

    List<Balance> balances = new ArrayList<>();

    // Adapt to XChange DTOs
    Balance btcBalance =
        new Balance(
            Currency.getInstance(quoineWallet.getBitcoinAccount().getCurrency()),
            quoineWallet.getBitcoinAccount().getBalance(),
            quoineWallet.getBitcoinAccount().getFreeBalance());
    balances.add(btcBalance);

    for (FiatAccount fiatAccount : quoineWallet.getFiatAccounts()) {
      Balance fiatBalance =
          new Balance(
              Currency.getInstance(fiatAccount.getCurrency()),
              fiatAccount.getBalance(),
              fiatAccount.getBalance());
      balances.add(fiatBalance);
    }

    return Wallet.Builder.from(balances).build();
  }

  public static OpenOrders adapteOpenOrders(QuoineOrdersList quoineOrdersList) {

    List<LimitOrder> openOrders = new ArrayList<>();
    for (Model model : quoineOrdersList.getModels()) {
      if (model.getStatus().equals("live")) {

        // currencey pair
        String baseSymbol = model.getCurrencyPairCode().replace(model.getFundingCurrency(), "");
        String counterSymbol = model.getFundingCurrency();
        CurrencyPair currencyPair = new CurrencyPair(baseSymbol, counterSymbol);

        // OrderType
        OrderType orderType = model.getSide().equals("sell") ? OrderType.ASK : OrderType.BID;

        // Timestamp
        Date timestamp = new Date(model.getCreatedAt().longValue() * 1000L);

        LimitOrder limitOrder =
            new LimitOrder(
                orderType,
                model.getQuantity(),
                model.getFilledQuantity(),
                currencyPair,
                model.getId(),
                timestamp,
                model.getPrice());

        openOrders.add(limitOrder);
      }
    }

    return new OpenOrders(openOrders);
  }

  public static Wallet adapt(FiatAccount[] fiatBalances, BitcoinAccount[] cryptoBalances) {
    List<Balance> balanceList = new ArrayList<>();

    for (FiatAccount nativeBalance : fiatBalances) {
      balanceList.add(
          new Balance(
              Currency.getInstance(nativeBalance.getCurrency()), nativeBalance.getBalance()));
    }
    for (BitcoinAccount cryptoBalance : cryptoBalances) {
      balanceList.add(
          new Balance(
              Currency.getInstance(cryptoBalance.getCurrency()), cryptoBalance.getBalance()));
    }
    return Wallet.Builder.from(balanceList).build();
  }

  //  public static Wallet adapt(BitcoinAccount[] balances) {
  //    List<Balance> balanceList = new ArrayList<>();
  //    for (BitcoinAccount nativeBalance : balances) {
  //     balanceList.add(
  //          new Balance(
  //              Currency.getInstance(nativeBalance.getCurrency()), nativeBalance.getBalance()));
  //    }
  //    return new Wallet("crypto",balanceList);
  //  }

  public static List<UserTrade> adapt(List<QuoineExecution> executions, CurrencyPair currencyPair) {
    List<UserTrade> res = new ArrayList<>();
    for (QuoineExecution execution : executions) {
      res.add(
          new UserTrade.Builder()
              .type(execution.mySide.equals("sell") ? OrderType.ASK : OrderType.BID)
              .originalAmount(execution.quantity)
              .currencyPair(currencyPair)
              .price(execution.price)
              .timestamp(DateUtils.fromUnixTime(execution.createdAt))
              .id(execution.id)
              .orderId(execution.orderId)
              .build());
    }
    return res;
  }

  public static String toPairString(CurrencyPair currencyPair) {
    return currencyPair.base.getCurrencyCode() + currencyPair.counter.getCurrencyCode();
  }

  public static FundingRecord adaptFunding(
      Currency currency, QuoineTransaction transaction, FundingRecord.Type deposit) {
    BigDecimal fee = null;
    if (transaction.exchange_fee != null) fee = transaction.exchange_fee;

    if (transaction.network_fee != null) {
      fee = fee == null ? transaction.network_fee : fee.add(transaction.network_fee);
    }

    return new FundingRecord(
        null,
        DateUtils.fromUnixTime(transaction.createdAt),
        currency,
        transaction.gross_amount,
        transaction.id,
        transaction.transaction_hash,
        deposit,
        FundingRecord.Status.COMPLETE,
        null,
        fee,
        transaction.notes);
  }
}
