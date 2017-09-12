package org.knowm.xchange.luno;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.knowm.xchange.luno.dto.LunoBoolean;
import org.knowm.xchange.luno.dto.LunoException;
import org.knowm.xchange.luno.dto.account.LunoAccount;
import org.knowm.xchange.luno.dto.account.LunoAccountTransactions;
import org.knowm.xchange.luno.dto.account.LunoBalance;
import org.knowm.xchange.luno.dto.account.LunoFundingAddress;
import org.knowm.xchange.luno.dto.account.LunoPendingTransactions;
import org.knowm.xchange.luno.dto.account.LunoQuote;
import org.knowm.xchange.luno.dto.account.LunoWithdrawals;
import org.knowm.xchange.luno.dto.account.LunoWithdrawals.Withdrawal;
import org.knowm.xchange.luno.dto.marketdata.LunoOrderBook;
import org.knowm.xchange.luno.dto.marketdata.LunoTicker;
import org.knowm.xchange.luno.dto.marketdata.LunoTickers;
import org.knowm.xchange.luno.dto.marketdata.LunoTrades;
import org.knowm.xchange.luno.dto.trade.LunoFeeInfo;
import org.knowm.xchange.luno.dto.trade.LunoOrders;
import org.knowm.xchange.luno.dto.trade.LunoOrders.Order;
import org.knowm.xchange.luno.dto.trade.LunoPostOrder;
import org.knowm.xchange.luno.dto.trade.OrderType;
import org.knowm.xchange.luno.dto.trade.State;

import si.mazi.rescu.BasicAuthCredentials;
import si.mazi.rescu.ClientConfig;
import si.mazi.rescu.RestProxyFactory;

public class LunoAPIImpl implements LunoAPI {

  protected final static String URI = "https://api.mybitx.com";
  private final LunoAuthenticated luno;
  private final BasicAuthCredentials auth;

  public LunoAPIImpl(String key, String secret) {
    this(key, secret, URI, null);
  }

  public LunoAPIImpl(String key, String secret, String uri, ClientConfig config) {
    luno = RestProxyFactory.createProxy(LunoAuthenticated.class, uri, config);
    auth = new BasicAuthCredentials(key, secret);
  }

  @Override
  public LunoTicker ticker(String pair) throws IOException, LunoException {
    return luno.ticker(pair);
  }

  @Override
  public LunoTickers tickers() throws IOException, LunoException {
    return luno.tickers();
  }

  @Override
  public LunoOrderBook orderbook(String pair) throws IOException, LunoException {
    return luno.orderbook(pair);
  }

  @Override
  public LunoTrades trades(String pair, Long since) throws IOException, LunoException {
    return luno.trades(pair, since);
  }

  @Override
  public LunoAccount createAccount(String currency, String name) throws IOException, LunoException {
    return luno.createAccount(this.auth, currency, name);
  }

  @Override
  public LunoBalance balance() throws IOException, LunoException {
    return luno.balance(this.auth);
  }

  @Override
  public LunoAccountTransactions transactions(String id, int minRow, int maxRow) throws IOException,
      LunoException {
    return luno.transactions(this.auth, id, minRow, maxRow);
  }

  @Override
  public LunoPendingTransactions pendingTransactions(String id) throws IOException, LunoException {
    return luno.pendingTransactions(this.auth, id);
  }

  @Override
  public LunoOrders listOrders(State state, String pair) throws IOException, LunoException {
    return luno.listOrders(this.auth, state, pair);
  }

  @Override
  public LunoPostOrder postLimitOrder(String pair, OrderType type, BigDecimal volume, BigDecimal price,
      String baseAccountId, String counterAccountId) throws IOException, LunoException {
    assert type == OrderType.ASK || type == OrderType.BID : "The order type for limit order must be ASK or BID.";
    return luno.postLimitOrder(this.auth, pair, type, volume, price, baseAccountId, counterAccountId);
  }

  @Override
  public LunoPostOrder postMarketOrder(String pair, OrderType type, BigDecimal counterVolume,
      BigDecimal baseVolume, String baseAccountId, String counterAccountId) throws IOException, LunoException {
    assert type == OrderType.BUY || type == OrderType.SELL : "The order type for limit order must be SELL or BUY.";
    return luno.postMarketOrder(this.auth, pair, type, counterVolume, baseVolume, baseAccountId, counterAccountId);
  }

  @Override
  public LunoBoolean stopOrder(String orderId) throws IOException, LunoException {
    return luno.stopOrder(this.auth, orderId);
  }

  @Override
  public Order getOrder(String orderId) throws IOException, LunoException {
    return luno.getOrder(this.auth, orderId);
  }

  @Override
  public org.knowm.xchange.luno.dto.trade.LunoUserTrades listTrades(String pair, Long since, Integer limit) throws IOException,
      LunoException {
    return luno.listTrades(this.auth, pair, since, limit);
  }

  @Override
  public LunoFeeInfo feeInfo(String pair) throws IOException, LunoException {
    return luno.feeInfo(this.auth, pair);
  }

  @Override
  public LunoFundingAddress getFundingAddress(String asset, String address) throws IOException, LunoException {
    return luno.getFundingAddress(this.auth, asset, address);
  }

  @Override
  public LunoFundingAddress createFundingAddress(String asset) throws IOException, LunoException {
    return luno.createFundingAddress(this.auth, asset);
  }

  @Override
  public LunoWithdrawals withdrawals() throws IOException, LunoException {
    return luno.withdrawals(this.auth);
  }

  private static final Set<String> VALID_TYPES = new HashSet<String>(Arrays.asList(
      "ZAR_EFT", "NAD_EFT", "KES_MPESA", "MYR_IBG", "IDR_LLG"));

  @Override
  public Withdrawal requestWithdrawal(String type, BigDecimal amount, String beneficiaryId)
      throws IOException, LunoException {
    assert VALID_TYPES.contains(type) : "Valid withdrawal types are: " + VALID_TYPES;
    return luno.requestWithdrawal(this.auth, type, amount, beneficiaryId);
  }

  @Override
  public Withdrawal getWithdrawal(String withdrawalId) throws IOException, LunoException {
    return luno.getWithdrawal(this.auth, withdrawalId);
  }

  @Override
  public Withdrawal cancelWithdrawal(String withdrawalId) throws IOException, LunoException {
    return luno.cancelWithdrawal(this.auth, withdrawalId);
  }

  @Override
  public LunoBoolean send(BigDecimal amount, String currency, String address, String description,
      String message) throws IOException, LunoException {
    return luno.send(this.auth, amount, currency, address, description, message);
  }

  @Override
  public LunoQuote createQuote(OrderType type, BigDecimal baseAmount, String pair) throws IOException,
      LunoException {
    assert type == OrderType.BUY || type == OrderType.SELL : "The type for quote must be SELL or BUY.";
    return luno.createQuote(this.auth, type, baseAmount, pair);
  }

  @Override
  public LunoQuote getQuote(String quoteId) throws IOException, LunoException {
    return luno.getQuote(this.auth, quoteId);
  }

  @Override
  public LunoQuote exerciseQuote(String quoteId) throws IOException, LunoException {
    return luno.exerciseQuote(this.auth, quoteId);
  }

  @Override
  public LunoQuote discardQuote(String quoteId) throws IOException, LunoException {
    return luno.discardQuote(this.auth, quoteId);
  }
}
