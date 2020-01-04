package org.knowm.xchange.idex;

import static java.util.Arrays.asList;
import static org.knowm.xchange.dto.Order.OrderType.ASK;
import static org.knowm.xchange.dto.Order.OrderType.BID;
import static org.knowm.xchange.idex.IdexExchange.Companion.getCurrencyPair;
import static org.knowm.xchange.idex.IdexExchange.Companion.safeParse;
import static org.knowm.xchange.idex.IdexSignature.generateSignature;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.util.*;
import java.util.stream.Collectors;
import org.bouncycastle.util.encoders.Hex;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.Order.OrderType;
import org.knowm.xchange.dto.marketdata.Trade;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.dto.trade.*;
import org.knowm.xchange.dto.trade.LimitOrder.Builder;
import org.knowm.xchange.exceptions.NotAvailableFromExchangeException;
import org.knowm.xchange.idex.IdexExchange.Companion.IdexCurrencyMeta;
import org.knowm.xchange.idex.dto.*;
import org.knowm.xchange.idex.service.*;
import org.knowm.xchange.service.BaseExchangeService;
import org.knowm.xchange.service.trade.TradeService;
import org.knowm.xchange.service.trade.params.CancelOrderParams;
import org.knowm.xchange.service.trade.params.TradeHistoryParamCurrencyPair;
import org.knowm.xchange.service.trade.params.TradeHistoryParams;
import org.knowm.xchange.service.trade.params.orders.OpenOrdersParams;
import org.web3j.crypto.Sign.SignatureData;
import si.mazi.rescu.RestProxyFactory;

public class IdexTradeService extends BaseExchangeService implements TradeService {

  private final ReturnOpenOrdersApi returnOpenOrdersApi;

  private final CancelApi cancelApi;

  private final ReturnTradeHistoryApi returnTradeHistoryApi;

  private OrderApi orderApi;

  private ReturnContractAddressApi returnContractAddressApi;

  private String apiKey;

  public IdexTradeService(IdexExchange idexExchange) {
    super(idexExchange);

    returnOpenOrdersApi =
        RestProxyFactory.createProxy(
            ReturnOpenOrdersApi.class,
            exchange.getExchangeSpecification().getSslUri(),
            getClientConfig());

    cancelApi =
        RestProxyFactory.createProxy(
            CancelApi.class, exchange.getExchangeSpecification().getSslUri(), getClientConfig());

    returnTradeHistoryApi =
        RestProxyFactory.createProxy(
            ReturnTradeHistoryApi.class,
            exchange.getExchangeSpecification().getSslUri(),
            getClientConfig());

    orderApi =
        RestProxyFactory.createProxy(
            OrderApi.class, exchange.getExchangeSpecification().getSslUri(), getClientConfig());

    returnContractAddressApi =
        RestProxyFactory.createProxy(
            ReturnContractAddressApi.class,
            exchange.getExchangeSpecification().getSslUri(),
            getClientConfig());

    apiKey = exchange.getExchangeSpecification().getApiKey();
  }

  @Override
  public OpenOrders getOpenOrders() {
    ReturnOpenOrdersApi proxy = returnOpenOrdersApi;

    OpenOrders ret = null;
    try {
      ReturnOpenOrdersResponse openOrdersResponse =
          proxy.openOrders(new OpenOrdersReq().address(apiKey));

      ret =
          new OpenOrders(
              openOrdersResponse.stream()
                  .map(
                      responseInner -> {
                        CurrencyPair currencyPair;
                        OrderType orderType =
                            responseInner.getType() == IdexBuySell.BUY ? BID : ASK;

                        {
                          String market = responseInner.getMarket();
                          currencyPair = getCurrencyPair(market);
                        }
                        return new Builder(orderType, currencyPair)
                            .limitPrice(safeParse(responseInner.getPrice()))
                            .originalAmount(safeParse(responseInner.getAmount()))
                            .id(responseInner.getOrderHash())
                            .build();
                      })
                  .collect(Collectors.toList()));
    } catch (Exception e) {
      e.printStackTrace();
    }
    return ret;
  }

  @Override
  public OpenOrders getOpenOrders(OpenOrdersParams openOrdersParams) {
    return getOpenOrders();
  }

  @Override
  public boolean cancelOrder(String s) {
    throw new NotAvailableFromExchangeException();
  }

  @Override
  public boolean cancelOrder(CancelOrderParams cancelOrderParams) {
    throw new NotAvailableFromExchangeException();
  }

  @Override
  public UserTrades getTradeHistory(TradeHistoryParams tradeHistoryParams) {

    CurrencyPair currencyPairTmp = null;

    if (tradeHistoryParams instanceof TradeHistoryParamCurrencyPair) {
      currencyPairTmp = ((TradeHistoryParamCurrencyPair) tradeHistoryParams).getCurrencyPair();
    }

    final CurrencyPair currencyPair = currencyPairTmp;

    UserTrades ret = null;
    try {
      List<UserTrade> userTrades =
          returnTradeHistoryApi.tradeHistory((TradeHistoryReq) tradeHistoryParams).stream()
              .map(
                  tradeHistoryItem ->
                      new UserTrade.Builder()
                          .originalAmount(
                              IdexExchange.Companion.safeParse(tradeHistoryItem.getAmount()))
                          .price(IdexExchange.Companion.safeParse(tradeHistoryItem.getPrice()))
                          .currencyPair(currencyPair)
                          .timestamp(new Date(tradeHistoryItem.getTimestamp().longValue() * 1000))
                          .id((tradeHistoryItem.getTransactionHash()))
                          .type(
                              tradeHistoryItem.getType() == IdexBuySell.BUY
                                  ? Order.OrderType.BID
                                  : Order.OrderType.ASK)
                          .build())
              .sorted(Comparator.comparing(Trade::getTimestamp))
              .collect(Collectors.toList());

      return new UserTrades(userTrades, Trades.TradeSortType.SortByTimestamp);

    } catch (Exception e) {
      e.printStackTrace();
    }
    return ret;
  }

  @Override
  public Collection<Order> getOrder(String... strings) {
    ReturnOpenOrdersApi proxy = returnOpenOrdersApi;
    return Collections.emptyList();
  }

  @Override
  public OpenOrdersParams createOpenOrdersParams() {

    throw new NotAvailableFromExchangeException();
  }

  @Override
  public void verifyOrder(LimitOrder limitOrder) {
    throw new NotAvailableFromExchangeException();
  }

  @Override
  public void verifyOrder(MarketOrder marketOrder) {
    throw new NotAvailableFromExchangeException();
  }

  @Override
  public TradeHistoryParams createTradeHistoryParams() {
    return new IdexTradeHistoryParams(apiKey);
  }

  @Override
  public String placeMarketOrder(MarketOrder marketOrder) {
    throw new NotAvailableFromExchangeException();
  }

  @Override
  public String placeStopOrder(StopOrder stopOrder) {

    throw new NotAvailableFromExchangeException();
  }

  /**
   * returns OrderHash so you can fetch it and cancel it... but there is a OrderNumber that you can
   * intercept if you need to.
   */
  @Override
  public String placeLimitOrder(LimitOrder placeOrder) {
    OrderType type = placeOrder.getType();
    Currency baseCurrency = placeOrder.getCurrencyPair().base;
    Currency counterCurrency = placeOrder.getCurrencyPair().counter;
    BigDecimal originalAmount = placeOrder.getOriginalAmount();
    BigDecimal limitPrice = placeOrder.getLimitPrice();
    OrderReq orderReq =
        createNormalizedLimitOrderReq(
            baseCurrency, counterCurrency, type, limitPrice, originalAmount, null, null, null);
    try {

      orderApi.order(orderReq).getOrderHash();
    } catch (Exception e) {

      e.printStackTrace();
    }
    return null;
  }

  public final OrderReq createNormalizedLimitOrderReq(
      Currency baseCurrency,
      Currency counterCurrency,
      OrderType type,
      BigDecimal limitPrice,
      BigDecimal originalAmount,
      String contractAddress,
      BigInteger nonce,
      BigInteger expires) {
    nonce = nonce == null ? BigInteger.valueOf(exchange.getNonceFactory().createValue()) : nonce;
    contractAddress = contractAddress == null ? contractAddress().getAddress() : contractAddress;
    expires = expires == null ? BigInteger.valueOf(100000) : expires;
    exchange.getExchangeMetaData().getCurrencies().get(baseCurrency);
    exchange.getExchangeMetaData().getCurrencies().get(counterCurrency);
    OrderReq orderReq = null;

    boolean untested = true;
    if (untested) {
      List<Currency> listOfCurrencies = asList(baseCurrency, /*OMG*/ counterCurrency /*ETH*/);
      if (type == BID) Collections.reverse(listOfCurrencies);

      IdexCurrencyMeta buy_currency =
          (IdexCurrencyMeta)
              exchange.getExchangeMetaData().getCurrencies().get(listOfCurrencies.get(0));
      IdexCurrencyMeta sell_currency =
          (IdexCurrencyMeta)
              exchange.getExchangeMetaData().getCurrencies().get(listOfCurrencies.get(1));
      BigDecimal divide = originalAmount.divide(limitPrice, MathContext.DECIMAL128);
      BigDecimal amount_buy =
          divide.multiply(
              new BigDecimal("1e" + buy_currency.getDecimals()), MathContext.DECIMAL128);
      BigDecimal amount_sell =
          originalAmount.multiply(
              new BigDecimal("1e" + sell_currency.getDecimals()), MathContext.DECIMAL128);
      String buyc = buy_currency.getAddress();
      String sellc = sell_currency.getAddress();
      List<List<String>> hash_data =
          asList(
              asList("contractAddress", contractAddress, "address"),
              asList("tokenBuy", buyc, "address"),
              asList("amountBuy", amount_buy.toString(), "uint256"),
              asList("tokenSell", sellc, "address"),
              asList("amountSell", amount_sell.toString(), "uint256"),
              asList("expires", "" + expires, "uint256"),
              asList("nonce", "" + nonce, "uint256"),
              asList("address", "" + getApiKey(), "address"));

      SignatureData sig =
          generateSignature(exchange.getExchangeSpecification().getSecretKey(), hash_data);
      byte[] v = sig.getV();
      byte[] r = sig.getR();
      byte[] s = sig.getS();
      orderReq =
          new OrderReq()
              .address(getApiKey())
              .nonce(nonce)
              .tokenBuy(buyc)
              .amountBuy(amount_buy.toString())
              .tokenSell(sellc)
              .amountSell(amount_sell.toString())
              .expires(expires)
              .r("0x" + new String(Hex.toHexString(r)))
              .s("0x" + new String(Hex.toHexString(s)))
              .v(BigInteger.valueOf(v[0] & 0xffl));
    }
    return orderReq;
  }

  ReturnContractAddressResponse contractAddress() {
    try {

      return returnContractAddressApi.contractAddress();
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }

  public String getApiKey() {
    return apiKey;
  }
}
