package org.knowm.xchange.binance.service;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.binance.BinanceAdapters;
import org.knowm.xchange.binance.dto.BinanceException;
import org.knowm.xchange.binance.dto.account.margin.*;
import org.knowm.xchange.binance.dto.trade.*;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

public class BinanceTradeMarginServiceRaw extends BinanceBaseService {

  protected BinanceTradeMarginServiceRaw(Exchange exchange) {
    super(exchange);
  }

  public MarginAccount getAccount() throws IOException {
    Long recvWindow =
        (Long) exchange.getExchangeSpecification().getExchangeSpecificParametersItem("recvWindow");
    return binance.getMarginAccount(recvWindow, getTimestamp(), apiKey, signatureCreator);
  }

  public OperationInfo transfer(Currency asset, BigDecimal amount, TransferType type)
          throws IOException {
    Long recvWindow =
        (Long) exchange.getExchangeSpecification().getExchangeSpecificParametersItem("recvWindow");
    return binance.transfer(
        BinanceAdapters.toSymbol(asset),
        amount,
        type.getCode(),
        recvWindow,
        getTimestamp(),
        apiKey,
        signatureCreator);
  }

  public OperationInfo borrow(Currency asset, BigDecimal amount) throws IOException {
    Long recvWindow =
        (Long) exchange.getExchangeSpecification().getExchangeSpecificParametersItem("recvWindow");
    return binance.borrow(
        BinanceAdapters.toSymbol(asset),
        amount,
        recvWindow,
        getTimestamp(),
        apiKey,
        signatureCreator);
  }

  public OperationInfo repay(Currency asset, BigDecimal amount) throws IOException {
    Long recvWindow =
        (Long) exchange.getExchangeSpecification().getExchangeSpecificParametersItem("recvWindow");
    return binance.repay(
        BinanceAdapters.toSymbol(asset),
        amount,
        recvWindow,
        getTimestamp(),
        apiKey,
        signatureCreator);
  }

  public BinanceNewOrder newMarginOrder(
      CurrencyPair pair,
      OrderSide side,
      OrderType type,
      TimeInForce timeInForce,
      BigDecimal quantity,
      BigDecimal price,
      String newClientOrderId,
      BigDecimal stopPrice,
      BigDecimal icebergQty)
      throws IOException, BinanceException {
    Long recvWindow =
        (Long) exchange.getExchangeSpecification().getExchangeSpecificParametersItem("recvWindow");
    return binance.newMarginOrder(
        BinanceAdapters.toSymbol(pair),
        side,
        type,
        timeInForce,
        quantity,
        price,
        newClientOrderId,
        stopPrice,
        icebergQty,
        recvWindow,
        getTimestamp(),
        super.apiKey,
        super.signatureCreator);
  }

  public BinanceCancelledOrder cancelMarginOrder(
      CurrencyPair pair, Long orderId, String origClientOrderId, String newClientOrderId)
      throws IOException, BinanceException {
    Long recvWindow =
        (Long) exchange.getExchangeSpecification().getExchangeSpecificParametersItem("recvWindow");
    return binance.cancelMarginOrder(
        BinanceAdapters.toSymbol(pair),
        orderId,
        origClientOrderId,
        newClientOrderId,
        recvWindow,
        getTimestamp(),
        super.apiKey,
        super.signatureCreator);
  }

  public LoanRecords getLoanRecords(
      Currency asset, Long txId, Long startTime, Long endTime, Long current, Long size)
      throws IOException, BinanceException {
    Long recvWindow =
        (Long) exchange.getExchangeSpecification().getExchangeSpecificParametersItem("recvWindow");
    return binance.getLoanRecords(
        BinanceAdapters.toSymbol(asset),
        txId,
        startTime,
        endTime,
        current,
        size,
        recvWindow,
        getTimestamp(),
        apiKey,
        signatureCreator);
  }

  public RepayRecords getRepayRecords(
      Currency asset, Long txId, Long startTime, Long endTime, Long current, Long size)
      throws IOException, BinanceException {
    Long recvWindow =
        (Long) exchange.getExchangeSpecification().getExchangeSpecificParametersItem("recvWindow");
    return binance.getRepayRecords(
        BinanceAdapters.toSymbol(asset),
        txId,
        startTime,
        endTime,
        current,
        size,
        recvWindow,
        getTimestamp(),
        apiKey,
        signatureCreator);
  }

  public Transfers getTransferHistory(
      Currency asset,
      HistoryTransferType type,
      Long startTime,
      Long endTime,
      Long current,
      Long size)
      throws IOException, BinanceException {
    Long recvWindow =
        (Long) exchange.getExchangeSpecification().getExchangeSpecificParametersItem("recvWindow");
    return binance.getTransferHistory(
        asset != null ? BinanceAdapters.toSymbol(asset) : null,
        type.toString(),
        startTime,
        endTime,
        current,
        size,
        recvWindow,
        getTimestamp(),
        apiKey,
        signatureCreator);
  }

  public Interests getInterestHistory(
      Currency asset, Long startTime, Long endTime, Long current, Long size)
      throws IOException, BinanceException {
    Long recvWindow =
        (Long) exchange.getExchangeSpecification().getExchangeSpecificParametersItem("recvWindow");
    return binance.getInterestHistory(
        asset != null ? BinanceAdapters.toSymbol(asset) : null,
        startTime,
        endTime,
        current,
        size,
        recvWindow,
        getTimestamp(),
        apiKey,
        signatureCreator);
  }

  public ForceLiquidationRecs getForceLiquidationRecords(
      Long startTime, Long endTime, Long current, Long size) throws IOException, BinanceException {
    Long recvWindow =
        (Long) exchange.getExchangeSpecification().getExchangeSpecificParametersItem("recvWindow");
    return binance.getForceLiquidationRecords(
        startTime, endTime, current, size, recvWindow, getTimestamp(), apiKey, signatureCreator);
  }

  public MarginOrder getMarginOrder(CurrencyPair symbol, String orderId, String origClientOrderId)
      throws IOException, BinanceException {
    Long recvWindow =
        (Long) exchange.getExchangeSpecification().getExchangeSpecificParametersItem("recvWindow");
    return binance.getMarginOrder(
        BinanceAdapters.toSymbol(symbol),
        orderId,
        origClientOrderId,
        recvWindow,
        getTimestamp(),
        apiKey,
        signatureCreator);
  }

  public List<MarginOrder> getOpenOrders(CurrencyPair pair) throws IOException {
    Long recvWindow =
        (Long) exchange.getExchangeSpecification().getExchangeSpecificParametersItem("recvWindow");
    return binance.getOpenMarginOrders(
        pair != null ? BinanceAdapters.toSymbol(pair) : null,
        recvWindow,
        getTimestamp(),
        apiKey,
        signatureCreator);
  }

  public List<ShortMarginOrder> getAllMarginOrders(
      CurrencyPair symbol, String orderId, Long startTime, Long endTime, Integer limit)
      throws IOException, BinanceException {
    Long recvWindow =
        (Long) exchange.getExchangeSpecification().getExchangeSpecificParametersItem("recvWindow");
    return binance.getAllMarginOrders(
        BinanceAdapters.toSymbol(symbol),
        orderId,
        startTime,
        endTime,
        limit,
        recvWindow,
        getTimestamp(),
        apiKey,
        signatureCreator);
  }

  public List<BinanceTrade> getTrades(
      CurrencyPair symbol, Long startTime, Long endTime, Long fromId, Integer limit)
      throws IOException, BinanceException {
    Long recvWindow =
        (Long) exchange.getExchangeSpecification().getExchangeSpecificParametersItem("recvWindow");
    return binance.getTrades(
        BinanceAdapters.toSymbol(symbol),
        startTime,
        endTime,
        fromId,
        limit,
        recvWindow,
        getTimestamp(),
        apiKey,
        signatureCreator);
  }

  public BigDecimal getMaxBorrow(Currency asset) throws IOException, BinanceException {
    Long recvWindow =
        (Long) exchange.getExchangeSpecification().getExchangeSpecificParametersItem("recvWindow");
    return binance.getMaxBorrow(
        BinanceAdapters.toSymbol(asset), recvWindow, getTimestamp(), apiKey, signatureCreator);
  }

  public BigDecimal getMaxTransferOutAmount(Currency asset) throws IOException, BinanceException {
    Long recvWindow =
        (Long) exchange.getExchangeSpecification().getExchangeSpecificParametersItem("recvWindow");
    return binance.getMaxTransferOutAmount(
        BinanceAdapters.toSymbol(asset), recvWindow, getTimestamp(), apiKey, signatureCreator);
  }
}
